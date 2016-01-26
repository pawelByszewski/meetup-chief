package pl.mobilewarsaw.meetupchief.service.events

import android.app.IntentService
import android.app.Service
import android.content.ContentProviderOperation
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import pl.mobilewarsaw.meetupchief.database.EventTable
import pl.mobilewarsaw.meetupchief.database.MeetupGroupTable
import pl.mobilewarsaw.meetupchief.presenter.events.QUERY_KEY
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupContentProvider
import pl.mobilewarsaw.meetupchief.resource.meetup.MeetupEvent
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.MeetupRemoteResource
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.model.Meetup
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
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

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val query = intent.getStringExtra(QUERY_KEY) ?: ""
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
                            { Log.e("kabum", "Something goes wrong", it) },
                        { contentResolver.applyBatch(MeetupContentProvider.AUTHORITY, inserts) }
                )
    }

    //    override fun onHandleIntent(intent: Intent?) {
//        val inserts = arrayListOf<ContentProviderOperation>()
//        meetupRemoteResource.getEvents("Mobile-Warsaw")
//            .flatMap { events -> Observable.from(events.events) }
//            .subscribe(
//                    { event: MeetupEvent ->
//                        Log.e("kabum   ", "$event")
//                        val co: ContentProviderOperation
//                                = ContentProviderOperation.newInsert(MeetupEventContentProvider.CONTENT_URI)
//                                        .withValue(EventTable.NAME, event.name)
//                                        .withValue(EventTable.EVENT_ID, event.id)
//                                        .withValue(EventTable.ATTENDS, event.yesRsvpCount)
//                                        .build()
//                        inserts.add(co)
//                    },
//                    { Log.e("kabum", "Something goes wrong", it)},
//                    { contentResolver.applyBatch(MeetupEventContentProvider.AUTHORITY, inserts) }
//            )
//    }
}