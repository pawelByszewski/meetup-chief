package pl.mobilewarsaw.meetupchief.presenter.groups

import android.content.Context
import android.os.Bundle
import pl.mobilewarsaw.meetupchief.ui.groups.MeetupGroupsView

const val RESTORE_KEY = "restoreLastQuery"

interface MeetupGroupsPresenter {

    fun bind(context: Context, meetupGroupsView: MeetupGroupsView, savedInstanceState: Bundle?)

    fun findMeetups(query: String)
}