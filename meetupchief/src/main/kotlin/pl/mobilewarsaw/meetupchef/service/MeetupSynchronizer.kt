package pl.mobilewarsaw.meetupchef.service

import android.app.Service
import android.content.ContentProviderOperation
import android.content.Intent
import android.os.IBinder
import android.util.Log
import pl.mobilewarsaw.meetupchef.database.EventTable
import pl.mobilewarsaw.meetupchef.database.MeetupGroupTable
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupContentProvider
import pl.mobilewarsaw.meetupchef.resource.meetup.MeetupEvent
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.MeetupRemoteResource
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.model.Meetup
import pl.mobilewarsaw.meetupchef.service.model.MeetupSynchronizerQuery
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

        val query = MeetupSynchronizerQuery.extract(intent)

        when(query) {
            is MeetupSynchronizerQuery.Groups -> fetchGroups(query)
            is MeetupSynchronizerQuery.Events -> fetchEvents(query)
        }

        return START_STICKY;
    }

    private fun fetchEvents(query: MeetupSynchronizerQuery.Events) {
        val databaseOperation = arrayListOf<ContentProviderOperation>()
        meetupRemoteResource.getEvents(query.urlName)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap { meetupEvents -> Observable.from(meetupEvents.events) }
                .subscribe({  event: MeetupEvent -> databaseOperation.add(EventTable.createInsertOperation(event)) },
                        { Log.e("MeetupSynchronizer", "Fail to find Events", it) },
                        {
                            databaseOperation.add(0, EventTable.createDeleteForGroupOperations(query.urlName))
                            contentResolver.applyBatch(MeetupContentProvider.AUTHORITY, databaseOperation)
                        }
                )
    }

    private fun fetchGroups(query: MeetupSynchronizerQuery.Groups) {
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