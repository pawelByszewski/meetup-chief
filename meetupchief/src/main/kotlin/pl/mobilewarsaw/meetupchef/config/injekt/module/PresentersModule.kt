package pl.mobilewarsaw.meetupchef.config.injekt.module

import pl.mobilewarsaw.meetupchef.presenter.events.EventListingPresenterImpl
import pl.mobilewarsaw.meetupchef.presenter.events.EventsListingPresenter
import pl.mobilewarsaw.meetupchef.presenter.groups.MeetupGroupsPresenter
import pl.mobilewarsaw.meetupchef.presenter.groups.MeetupGroupsPresenterImpl
import pl.mobilewarsaw.meetupchef.ui.groups.MeetupEventCursorAdapter
import pl.mobilewarsaw.meetupchef.ui.groups.listingadapter.MeetupGroupsCursorAdapter
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