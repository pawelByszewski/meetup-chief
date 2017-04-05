package pl.mobilewarsaw.meetupchef.service

import android.app.Service
import android.content.ContentProviderOperation
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import pl.mobilewarsaw.meetupchef.database.EventTable
import pl.mobilewarsaw.meetupchef.database.MeetupGroupTable
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupContentProvider
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.MeetupRemoteResource
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.findGroup
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.getEvents
import pl.mobilewarsaw.meetupchef.service.model.MeetupSynchronizerQuery
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

        return START_STICKY
    }

    private fun fetchEvents(query: MeetupSynchronizerQuery.Events) {
        launch(CommonPool) {
            val databaseOperation = arrayListOf<ContentProviderOperation>()
            meetupRemoteResource.getEvents(query).events
                    .forEach { databaseOperation.add(EventTable.createInsertOperation(it)) }
            databaseOperation.add(0, EventTable.createDeleteForGroupOperations(query.urlName))
            contentResolver.applyBatch(MeetupContentProvider.AUTHORITY, databaseOperation)
        }
    }

    private fun fetchGroups(query: MeetupSynchronizerQuery.Groups) {
        launch(CommonPool) {
            val databaseOperation = arrayListOf<ContentProviderOperation>()
            meetupRemoteResource.findGroup(query)
                    .forEach { databaseOperation.add(MeetupGroupTable.createContentValues(it)) }
            databaseOperation.add(0, MeetupGroupTable.createDeleteAllOperation())
            contentResolver.applyBatch(MeetupContentProvider.AUTHORITY, databaseOperation)
        }
    }
}