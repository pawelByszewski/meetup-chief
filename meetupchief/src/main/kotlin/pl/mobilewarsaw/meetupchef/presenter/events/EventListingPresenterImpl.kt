package pl.mobilewarsaw.meetupchef.presenter.events

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.EventRepository
import pl.mobilewarsaw.meetupchef.service.MeetupSynchronizer
import pl.mobilewarsaw.meetupchef.service.model.MeetupSynchronizerQuery
import pl.mobilewarsaw.meetupchef.ui.events.*
import pl.touk.android.basil.registerUriObserver
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
        eventRepository.fetchEvents(state.urlName) { cursor: Cursor -> eventsListingView?.showEvents(cursor) }
    }
}