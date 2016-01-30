package pl.mobilewarsaw.meetupchief.presenter.events

import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchief.resource.local.meetup.repository.EventRepository
import pl.mobilewarsaw.meetupchief.service.MeetupSynchronizer
import pl.mobilewarsaw.meetupchief.service.model.MeetupSynchronizerQuery
import pl.mobilewarsaw.meetupchief.ui.events.EventsListingView
import pl.mobilewarsaw.meetupchief.ui.events.GROUP_NAME_KEY
import pl.mobilewarsaw.meetupchief.ui.events.GROUP_URL_NAME_KEY
import pl.mobilewarsaw.meetupchief.ui.events.MeetuGroupInitData
import uy.kohesive.injekt.injectValue


class EventListingPresenterImpl : EventsListingPresenter {

    private lateinit var eventsListingView: EventsListingView
    private lateinit var context: Context
    private lateinit var meetuGroupInitData: MeetuGroupInitData

    private val eventRepository: EventRepository by injectValue()

    override fun bind(eventsListingView: EventsListingView, context: Context) {
        this.eventsListingView = eventsListingView
        this.context = context

        meetuGroupInitData = extractInitData()
        eventsListingView.showInToolbar(meetuGroupInitData)

        registerUriObserver(MeetupEventContentProvider.CONTENT_URI) {
            showEvents()
        }
        eventsListingView.showProgressBar()
        synchronizeEvents()
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

    private fun extractInitData()
        = MeetuGroupInitData(urlName = eventsListingView.getIntent().getStringExtra(GROUP_URL_NAME_KEY),
                             name = eventsListingView.getIntent().getStringExtra(GROUP_NAME_KEY))

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