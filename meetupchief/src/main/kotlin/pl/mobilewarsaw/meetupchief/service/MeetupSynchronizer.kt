package pl.mobilewarsaw.meetupchief.service

import android.app.Service
import android.content.ContentProviderOperation
import android.content.Intent
import android.os.IBinder
import android.util.Log
import pl.mobilewarsaw.meetupchief.database.MeetupGroupTable
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupContentProvider
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.MeetupRemoteResource
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.model.Meetup
import pl.mobilewarsaw.meetupchief.service.model.MeetupSynchronizerQuery
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

        val query = MeetupSynchronizerQuery.extract(intent!!)

        when(query) {
            is MeetupSynchronizerQuery.Groups -> featchGroups(query)
            is MeetupSynchronizerQuery.Events -> featchEvents(query)
        }

        return START_STICKY;
    }

    private fun featchEvents(query: MeetupSynchronizerQuery.Events) {

    }

    private fun featchGroups(query: MeetupSynchronizerQuery.Groups) {
        val databaseOperation = arrayListOf<ContentProviderOperation>()
        meetupRemoteResource.findGroup(query.query)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap { meetups -> Observable.from(meetups) }
                .subscribe({  meetup: Meetup -> databaseOperation.add(MeetupGroupTable.createContentValues(meetup)) },
                        { Log.e("MeetupSynchronizer", "Fail to find groups", it) },
                        {
                            databaseOperation.add(0, MeetupGroupTable.createDeleteAllOperation())
                            contentResolver.applyBatch(MeetupContentProvider.AUTHORITY, databaseOperation)
                        }
                )
    }

}