package pl.mobilewarsaw.meetupchief.config.injekt.module

import android.content.Context
import pl.mobilewarsaw.meetupchief.presenter.groups.MeetupGroupsPresenter
import pl.mobilewarsaw.meetupchief.presenter.groups.MeetupGroupsPresenterImpl
import pl.mobilewarsaw.meetupchief.resource.local.meetup.repository.GroupRepository
import pl.mobilewarsaw.meetupchief.ui.groups.MeetupGroupsCursorAdapter
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType


class RepositoryModule(val context: Context) : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addFactory(fullType<GroupRepository>()) { GroupRepository(context) }
    }
}