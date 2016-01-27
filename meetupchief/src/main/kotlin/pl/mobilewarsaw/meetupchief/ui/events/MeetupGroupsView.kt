package pl.mobilewarsaw.meetupchief.ui.events

import android.database.Cursor


interface MeetupGroupsView {

    fun showMeetupGroups(cursor: Cursor)
}