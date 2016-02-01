package pl.mobilewarsaw.meetupchef.ui.groups

import android.database.Cursor


interface MeetupGroupsView {

    fun showMeetupGroups(cursor: Cursor)

    fun showProgressBar()

    fun showNetworkError()
}