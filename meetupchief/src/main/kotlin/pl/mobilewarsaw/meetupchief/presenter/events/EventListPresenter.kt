package pl.mobilewarsaw.meetupchief.presenter.events

import pl.mobilewarsaw.meetupchief.ui.events.EventsListView


interface EventListPresenter {
    fun bind(eventsListView: EventsListView)
}