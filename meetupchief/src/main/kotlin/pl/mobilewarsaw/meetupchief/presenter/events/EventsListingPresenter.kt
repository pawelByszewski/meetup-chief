package pl.mobilewarsaw.meetupchief.presenter.events

import android.content.Context
import android.content.Intent
import android.os.Bundle
import pl.mobilewarsaw.meetupchief.ui.events.EventsListingView


interface EventsListingPresenter {

    fun bind(eventsListingView: EventsListingView,
             context: Context,
             savedInstanceState: Bundle?,
             intent: Intent)

    open fun refreshEvents()

    fun saveState(outState: Bundle)
}