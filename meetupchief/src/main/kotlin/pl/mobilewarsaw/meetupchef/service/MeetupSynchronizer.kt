package pl.mobilewarsaw.meetupchef.service

import android.app.Service
import android.content.ContentProviderOperation
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.squareup.otto.Bus
import pl.mobilewarsaw.meetupchef.database.EventTable
import pl.mobilewarsaw.meetupchef.database.GroupTable
import pl.mobilewarsaw.meetupchef.database.ParticipantTable
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.GroupRepository
import pl.mobilewarsaw.meetupchef.resource.meetup.MeetupEvent
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.MeetupRemoteResource
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.model.Meetup
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.model.Participant
import pl.mobilewarsaw.meetupchef.service.error.NetworkError
import pl.mobilewarsaw.meetupchef.service.error.UnknownSynchronizeError
import pl.mobilewarsaw.meetupchef.service.model.MeetupSynchronizerQuery
import pl.touk.basil.postFromBackThread
import rx.Observable
import rx.schedulers.Schedulers
import uy.kohesive.injekt.injectValue
import java.net.UnknownHostException

class MeetupSynchronizer : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private val meetupRemoteResource: MeetupRemoteResource by injectValue()
    private val groupRepository: GroupRepository by injectValue()
    private val bus: Bus by injectValue()

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val query = MeetupSynchronizerQuery.extract(intent)

        when(query) {
            is MeetupSynchronizerQuery.Groups -> fetchGroups(query)
            is MeetupSynchronizerQuery.Events -> fetchEvents(query)
            is MeetupSynchronizerQuery.Participants -> fetchParticipants(query)
        }

        return START_STICKY;
    }

    // SRP!! add dispatcher and extract classes for each fetch
    private fun fetchEvents(query: MeetupSynchronizerQuery.Events) {
        val databaseOperation = arrayListOf<ContentProviderOperation>()
        meetupRemoteResource.getEvents(query.urlName)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap { meetupEvents -> Observable.from(meetupEvents.events) }
                .subscribe({  event: MeetupEvent -> databaseOperation.add(EventTable.createInsertOperation(event)) },
                        { throwable ->
                            Log.e("MeetupSynchronizer", "Fail to find Events", throwable)
                        },
                        {
                            databaseOperation.add(0, EventTable.createDeleteForGroupOperations(query.urlName))
                            contentResolver.applyBatch(MeetupContentProvider.AUTHORITY, databaseOperation)
                            groupRepository.markCached(query.urlName)
                        }
                )
    }

    private fun fetchParticipants(query: MeetupSynchronizerQuery.Participants) {
        val databaseOperation = arrayListOf<ContentProviderOperation>()
        meetupRemoteResource.getAttendance(query.urlName, query.eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap { participants -> Observable.from(participants) }
                .subscribe({  participant: Participant ->
                                databaseOperation.add(ParticipantTable.createInsertOperation(query.eventId, participant)) },
                        { throwable ->
                            Log.e("MeetupSynchronizer", "Fail to find Events", throwable)
                        },
                        {
                            databaseOperation.add(0, ParticipantTable.createDeleteForGroupOperations(query.eventId))
                            contentResolver.applyBatch(MeetupContentProvider.AUTHORITY, databaseOperation)
//                            groupRepository.markCached(query.urlName)
                        }
                )
    }

    private fun fetchGroups(query: MeetupSynchronizerQuery.Groups) {
        val databaseOperation = arrayListOf<ContentProviderOperation>()
        meetupRemoteResource.findGroup(query.query)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap { meetups -> Observable.from(meetups) }
                .subscribe({  meetup: Meetup ->
                                databaseOperation.add(GroupTable.createInsertOperation(query.query, meetup))
                            },
                            { throwable ->
                                Log.e("MeetupSynchronizer", "Fail to find groups", throwable)
                                when (throwable) {
                                    is UnknownHostException -> bus.postFromBackThread(NetworkError())
                                    else -> bus.postFromBackThread(UnknownSynchronizeError())
                                }
                            },
                            {
                                databaseOperation.add(0, GroupTable.createDeleteNotCachedOperation())
                                contentResolver.applyBatch(MeetupContentProvider.AUTHORITY, databaseOperation)
                            }
                )
    }

}