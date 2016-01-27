package pl.mobilewarsaw.meetupchief.ui.events

import android.database.Cursor


interface EventsListView {

    fun showMeetupGroups(cursor: Cursor)
}