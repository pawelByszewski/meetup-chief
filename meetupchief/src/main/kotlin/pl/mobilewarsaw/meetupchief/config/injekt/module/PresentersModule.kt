package pl.mobilewarsaw.meetupchief.config.injekt.module

import pl.mobilewarsaw.meetupchief.presenter.events.EventListingPresenterImpl
import pl.mobilewarsaw.meetupchief.presenter.events.EventsListingPresenter
import pl.mobilewarsaw.meetupchief.presenter.groups.MeetupGroupsPresenter
import pl.mobilewarsaw.meetupchief.presenter.groups.MeetupGroupsPresenterImpl
import pl.mobilewarsaw.meetupchief.ui.groups.MeetupEventCursorAdapter
import pl.mobilewarsaw.meetupchief.ui.groups.MeetupGroupsCursorAdapter
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType


class PresentersModule : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addFactory(fullType<MeetupGroupsPresenter>()) { MeetupGroupsPresenterImpl() }
        addFactory(fullType<MeetupGroupsCursorAdapter>()) { MeetupGroupsCursorAdapter() }

        addFactory(fullType<EventsListingPresenter>()) { EventListingPresenterImpl() }
        addFactory(fullType<MeetupEventCursorAdapter>()) { MeetupEventCursorAdapter() }
    }
}