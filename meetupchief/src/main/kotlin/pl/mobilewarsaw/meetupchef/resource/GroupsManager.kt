package pl.mobilewarsaw.meetupchef.resource

import io.requery.query.Result
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import pl.mobilewarsaw.meetupchef.database.model.MeetupGroup
import pl.mobilewarsaw.meetupchef.database.model.MeetupGroupEntity
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.GroupRepository
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.MeetupRemoteResource
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.findGroup
import pl.mobilewarsaw.meetupchef.service.model.MeetupSynchronizerQuery
import uy.kohesive.injekt.injectValue


class GroupsManager {

    val meetupRemoteResource: MeetupRemoteResource by injectValue()
    private val groupRepository: GroupRepository by injectValue()

    fun updateEventsAsync(query: String): Deferred<Result<MeetupGroup>>
        = async (CommonPool) {
            meetupRemoteResource
            .findGroup(MeetupSynchronizerQuery.Groups(query))
            .map {
                 MeetupGroupEntity().apply {
                    setGroupId(it.id)
                    setName(it.name)
                    setUrlName(it.urlName)
                    setCategory(it.category.name)
                    setPhotoUrl(it.photo?.url)
                    setMembers(it.members)
                }
            }.also {
                groupRepository.clear()
                groupRepository.insert(it)
            }

            groupRepository.fetchAllGroups()
        }
}