package pl.mobilewarsaw.meetupchief.ui.groups

import android.database.Cursor


interface MeetupGroupsView {

    fun showMeetupGroups(cursor: Cursor)

    fun showProgressBar()
}