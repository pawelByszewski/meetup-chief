package pl.mobilewarsaw.meetupchief.config.injekt.module

import pl.mobilewarsaw.meetupchief.presenter.events.EventListPresenter
import pl.mobilewarsaw.meetupchief.presenter.events.EventsListPresenterImpl
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType


class PresentersModule : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addFactory(fullType<EventListPresenter>()) { EventsListPresenterImpl() }
    }
}