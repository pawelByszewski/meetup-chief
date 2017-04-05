package pl.mobilewarsaw.meetupchef.presenter.events

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.coroutines.experimental.launch
import pl.mobilewarsaw.meetupchef.experimental.Android
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.EventRepository
import pl.mobilewarsaw.meetupchef.service.MeetupSynchronizer
import pl.mobilewarsaw.meetupchef.service.model.MeetupSynchronizerQuery
import pl.mobilewarsaw.meetupchef.ui.events.EventsListingView
import pl.touk.basil.registerUriObserver
import uy.kohesive.injekt.injectValue


class EventListingPresenterImpl : EventsListingPresenter {

    private lateinit var eventsListingView: EventsListingView
    private lateinit var context: Context
    private val state = State()

    private val eventRepository: EventRepository by injectValue()

    override fun bind(eventsListingView: EventsListingView,
                      context: Context,
                      savedInstanceState: Bundle?,
                      intent: Intent) {
        this.eventsListingView = eventsListingView
        this.context = context

        state.setup(intent, savedInstanceState)
        eventsListingView.showInToolbar(state)

        contentResolver.registerUriObserver(MeetupEventContentProvider.CONTENT_URI) {
            showEvents()
        }

        showMeetupGroupImage()
        if (savedInstanceState == null) {
            eventsListingView.showProgressBar()
            synchronizeEvents()
        } else {
            showEvents()
        }
    }

    private val contentResolver: ContentResolver
        get() = context.contentResolver

    private fun showMeetupGroupImage() {
        eventsListingView.showGroupPhoto(state.photoUrl)
    }

    override fun saveState(outState: Bundle) {
        state.saveIn(outState)
    }

    private fun synchronizeEvents() {
        val query = MeetupSynchronizerQuery.Events(state.urlName)
        val intent = Intent(context, MeetupSynchronizer::class.java)
        query.toIntent(intent)
        context.startService(intent)
    }

    override fun refreshEvents() {
        synchronizeEvents()
    }

    private fun showEvents() {
        launch(Android) {
            val eventsCursor = eventRepository.fetchEventsAsync(state.urlName).await()
            eventsListingView?.showEvents(eventsCursor)
        }
    }
}