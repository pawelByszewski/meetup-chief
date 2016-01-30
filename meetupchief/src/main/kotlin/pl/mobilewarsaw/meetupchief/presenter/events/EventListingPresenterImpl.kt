package pl.mobilewarsaw.meetupchief.presenter.events

import android.content.Context
import android.content.Intent
import pl.mobilewarsaw.meetupchief.service.MeetupSynchronizer
import pl.mobilewarsaw.meetupchief.service.model.MeetupSynchronizerQuery
import pl.mobilewarsaw.meetupchief.ui.events.EventsListingView
import pl.mobilewarsaw.meetupchief.ui.events.GROUP_NAME_KEY
import pl.mobilewarsaw.meetupchief.ui.events.GROUP_URL_NAME_KEY
import pl.mobilewarsaw.meetupchief.ui.events.MeetuGroupInitData


class EventListingPresenterImpl : EventsListingPresenter {

    private lateinit var eventsListingView: EventsListingView
    private lateinit var context: Context

    override fun bind(eventsListingView: EventsListingView, context: Context) {
        this.eventsListingView = eventsListingView
        this.context = context

        val meetuGroupInitData = extractInitData()
        eventsListingView.showInToolbar(meetuGroupInitData)

        val query = MeetupSynchronizerQuery.Events(meetuGroupInitData.urlName)

        val intent = Intent(context, MeetupSynchronizer::class.java)
        query.toIntent(intent)
        context.startService(intent)

    }

    private fun extractInitData()
        = MeetuGroupInitData(urlName = eventsListingView.getIntent().getStringExtra(GROUP_URL_NAME_KEY),
                             name = eventsListingView.getIntent().getStringExtra(GROUP_NAME_KEY))
}