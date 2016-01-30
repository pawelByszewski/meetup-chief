package pl.mobilewarsaw.meetupchief.presenter.events

import android.content.Context
import pl.mobilewarsaw.meetupchief.ui.events.EventsListingView


interface EventsListingPresenter {
    fun bind(eventsListingView: EventsListingView, context: Context)

}