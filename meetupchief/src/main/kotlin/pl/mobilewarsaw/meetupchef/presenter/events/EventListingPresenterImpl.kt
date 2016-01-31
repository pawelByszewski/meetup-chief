package pl.mobilewarsaw.meetupchef.presenter.events

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
import uy.kohesive.injekt.injectValue


class EventListingPresenterImpl : EventsListingPresenter {

    private lateinit var eventsListingView: EventsListingView
    private lateinit var context: Context
    private lateinit var intent: Intent
    private lateinit var meetuGroupInitData: MeetuGroupInitData

    private val eventRepository: EventRepository by injectValue()

    override fun bind(eventsListingView: EventsListingView,
                      context: Context,
                      savedInstanceState: Bundle?,
                      intent: Intent) {
        this.eventsListingView = eventsListingView
        this.context = context
        this.intent = intent

        meetuGroupInitData = extractInitData(savedInstanceState)
        eventsListingView.showInToolbar(meetuGroupInitData)

        registerUriObserver(MeetupEventContentProvider.CONTENT_URI) {
            showEvents()
        }
        showMeetupGroupImage()
        eventsListingView.showProgressBar()
        synchronizeEvents()
    }

    private fun showMeetupGroupImage() {
        eventsListingView.showGroupPhoto(meetuGroupInitData.photoUrl)
    }

    override fun saveState(outState: Bundle) {
        meetuGroupInitData.saveIn(outState)
    }

    private fun synchronizeEvents() {
        val query = MeetupSynchronizerQuery.Events(meetuGroupInitData.urlName)
        val intent = Intent(context, MeetupSynchronizer::class.java)
        query.toIntent(intent)
        context.startService(intent)
    }

    override fun refreshEvents() {
        synchronizeEvents()
    }

    private fun extractInitData(savedInstanceState: Bundle?)
        = if (savedInstanceState != null) {
            MeetuGroupInitData.createFrom(savedInstanceState)
        } else {
            MeetuGroupInitData.Companion.createFrom(intent)
        }


    private fun showEvents() {
        eventRepository.fetchEvents(meetuGroupInitData.urlName) { cursor: Cursor -> eventsListingView?.showEvents(cursor) }
    }

    private fun registerUriObserver(uri: Uri, action: () -> Unit) {
        this.context.contentResolver.registerContentObserver(uri, true,
                object : ContentObserver(Handler()) {
                    override fun onChange(selfChange: Boolean) = action()
                })
    }
}