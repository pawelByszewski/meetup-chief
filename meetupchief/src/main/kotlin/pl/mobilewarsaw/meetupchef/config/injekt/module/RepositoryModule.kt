package pl.mobilewarsaw.meetupchef.config.injekt.module

import android.content.Context
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.EventRepository
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.GroupRepository
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.ParticipantRepository
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType


class RepositoryModule(val context: Context) : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addSingletonFactory(fullType<GroupRepository>()) { GroupRepository(context) }
        addSingletonFactory(fullType<EventRepository>()) { EventRepository(context) }
        addSingletonFactory(fullType<ParticipantRepository>()) { ParticipantRepository(context) }
    }
}