package pl.mobilewarsaw.meetupchef.resource

import io.requery.query.Result
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import pl.mobilewarsaw.meetupchef.database.model.EventEntity
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.EventRepository
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.MeetupRemoteResource
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.getEvents
import uy.kohesive.injekt.injectValue


class EventsManager {

    val meetupRemoteResource: MeetupRemoteResource by injectValue()
    private val groupRepository: EventRepository by injectValue()

    fun updateEventsAsync(query: String): Deferred<Result<EventEntity>>
        = async (CommonPool) {
            meetupRemoteResource
            .getEvents(query)
            .map {
                EventEntity().apply {
                    setStatus(it.status)
                    setName(it.name)
                    setEventId(it.id)
                    setVenue(it.venue?.name ?: "")
                    setGroupUrlName(it.group.urlname)
                    setAttends(it.attends)
                    setDescription(it.description)
                }
            }.also {
                groupRepository.clear()
                groupRepository.insert(it)
            }

            groupRepository.fetchAllEvents()
        }
}