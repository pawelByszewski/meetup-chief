package pl.mobilewarsaw.meetupchief.presenter.events

import android.content.Context
import pl.mobilewarsaw.meetupchief.ui.events.MeetupGroupsView

interface MeetupGroupsPresenter {
    fun bind(context: Context, meetupGroupsView: MeetupGroupsView)

    fun findMeetups(query: String)
}