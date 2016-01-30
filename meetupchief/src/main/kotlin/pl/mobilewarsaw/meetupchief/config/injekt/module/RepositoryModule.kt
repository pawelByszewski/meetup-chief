package pl.mobilewarsaw.meetupchief.config.injekt.module

import android.content.Context
import pl.mobilewarsaw.meetupchief.resource.local.meetup.repository.EventRepository
import pl.mobilewarsaw.meetupchief.resource.local.meetup.repository.GroupRepository
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType


class RepositoryModule(val context: Context) : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addSingletonFactory(fullType<GroupRepository>()) { GroupRepository(context) }
        addSingletonFactory(fullType<EventRepository>()) { EventRepository(context) }
    }
}