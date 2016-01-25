package pl.mobilewarsaw.meetupchief.service.events

import android.app.IntentService
import android.content.ContentProviderOperation
import android.content.Intent
import android.util.Log
import pl.mobilewarsaw.meetupchief.database.EventTable
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.MeetupResource
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.model.MeetupEvent
import rx.Observable

class MeetupEventsSynchronizer : IntentService("EventsSynchronizer") {

    lateinit var meetupResource: MeetupResource


    override fun onCreate() {
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        val inserts = arrayListOf<ContentProviderOperation>()
        meetupResource.getEvents("Mobile-Warsaw")
            .flatMap { events -> Observable.from(events.events) }
            .subscribe(
                    { event: MeetupEvent ->
                        Log.e("kabum   ", "$event")
                        val co: ContentProviderOperation
                                = ContentProviderOperation.newInsert(MeetupEventContentProvider.CONTENT_URI)
                                        .withValue(EventTable.NAME, event.name)
                                        .withValue(EventTable.EVENT_ID, event.id)
                                        .withValue(EventTable.ATTENDS, event.yesRsvpCount)
                                        .build()
                        inserts.add(co)
                    },
                    { Log.e("kabum", "Something goes wrong", it)},
                    { contentResolver.applyBatch(MeetupEventContentProvider.AUTHORITY, inserts) }
            )
    }
}