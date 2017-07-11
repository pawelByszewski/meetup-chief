package pl.mobilewarsaw.meetupchef.presenter.events

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import pl.mobilewarsaw.meetupchef.resource.EventsManager
import pl.mobilewarsaw.meetupchef.ui.events.EventsListingView
import uy.kohesive.injekt.injectValue


class EventListingPresenterImpl : EventsListingPresenter {

    private lateinit var eventsListingView: EventsListingView
    private lateinit var context: Context
    private val state = State()

    private val eventsManager: EventsManager by injectValue()

    override fun bind(eventsListingView: EventsListingView,
                      context: Context,
                      savedInstanceState: Bundle?,
                      intent: Intent) {
        this.eventsListingView = eventsListingView
        this.context = context

        state.setup(intent, savedInstanceState)
        eventsListingView.showInToolbar(state)

        showMeetupGroupImage()

        eventsListingView.showProgressBar()
        showEvents()

    }

    private fun showMeetupGroupImage() {
        eventsListingView.showGroupPhoto(state.photoUrl)
    }

    override fun saveState(outState: Bundle) {
        state.saveIn(outState)
    }

    override fun refreshEvents() {
        showEvents()
    }

    private fun showEvents() {
        launch(UI) {
            val events = eventsManager.updateEventsAsync(state.urlName).await()
            eventsListingView?.showEvents(events)
        }
    }
}