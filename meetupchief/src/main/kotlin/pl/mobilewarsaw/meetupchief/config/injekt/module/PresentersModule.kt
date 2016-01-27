package pl.mobilewarsaw.meetupchief.config.injekt.module

import pl.mobilewarsaw.meetupchief.presenter.events.MeetupGroupsPresenter
import pl.mobilewarsaw.meetupchief.presenter.events.MeetupGroupsPresenterImpl
import pl.mobilewarsaw.meetupchief.ui.events.MeetupGroupsCursorAdapter
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType


class PresentersModule : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addFactory(fullType<MeetupGroupsPresenter>()) { MeetupGroupsPresenterImpl() }
        addFactory(fullType<MeetupGroupsCursorAdapter>()) { MeetupGroupsCursorAdapter() }
    }
}