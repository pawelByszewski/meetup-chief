package pl.mobilewarsaw.meetupchief.presenter.events

import android.content.Context
import pl.mobilewarsaw.meetupchief.ui.events.EventsListView

interface EventListPresenter {
    fun bind(context: Context, eventsListView: EventsListView)

    fun findMeetups(query: String)
}