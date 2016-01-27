package pl.mobilewarsaw.meetupchief.service.events

import android.app.IntentService
import android.app.Service
import android.content.ContentProviderOperation
import android.content.Intent
import android.os.IBinder
import android.util.Log
import pl.mobilewarsaw.meetupchief.database.MeetupGroupTable
import pl.mobilewarsaw.meetupchief.presenter.events.QUERY_KEY
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupContentProvider
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.MeetupRemoteResource
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.model.Meetup
import rx.Observable
import rx.schedulers.Schedulers
import uy.kohesive.injekt.injectValue

class MeetupSynchronizer : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    val meetupRemoteResource: MeetupRemoteResource by injectValue()

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val query = intent?.getStringExtra(QUERY_KEY) ?: ""
        if (query.isNotBlank()) {
            handleRequest(query)
        }
        return START_STICKY;
    }

    private fun handleRequest(query: String) {
        val inserts = arrayListOf<ContentProviderOperation>()
        meetupRemoteResource.findGroup(query)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap { meetups -> Observable.from(meetups) }
                .subscribe({  meetup: Meetup -> inserts.add(MeetupGroupTable.createContentValues(meetup)) },
                        { Log.e("MeetupSynchronizer", "Fail to find groups", it) },
                        { contentResolver.applyBatch(MeetupContentProvider.AUTHORITY, inserts) }
                )
    }

}