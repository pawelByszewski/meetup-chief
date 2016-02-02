package pl.mobilewarsaw.meetupchef.presenter.events

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import pl.mobilewarsaw.meetupchef.presenter.participants.ParticipantsPresenter
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupEvent
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupGroup
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.EventRepository
import pl.mobilewarsaw.meetupchef.service.MeetupSynchronizer
import pl.mobilewarsaw.meetupchef.service.model.MeetupSynchronizerQuery
import pl.mobilewarsaw.meetupchef.ui.events.*
import pl.mobilewarsaw.meetupchef.ui.events.listingadapter.EventClicked
import pl.mobilewarsaw.meetupchef.ui.groups.bus.GroupClicked
import pl.mobilewarsaw.meetupchef.ui.participants.ParticipantsActivity
import pl.touk.basil.registerUriObserver
import uy.kohesive.injekt.injectValue


class EventListingPresenterImpl : EventsListingPresenter {

    private lateinit var eventsListingView: EventsListingView
    private lateinit var context: Context
    private val state = State()

    private val eventRepository: EventRepository by injectValue()
    private val bus: Bus by injectValue()

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

        bus.register(this)
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

    @Subscribe
    fun onMeetupEventClick(eventCLicked: EventClicked) {
        onGroupClicked(eventCLicked.event)
    }

    private fun onGroupClicked(meetupEvent: MeetupEvent) {
        val intent = ParticipantsActivity.createIntent(context!!, meetupEvent)
        context?.startActivity(intent)
    }
}