package pl.mobilewarsaw.meetupchef.presenter.groups

import android.content.Context
import android.content.Intent
import android.os.Bundle
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupGroup
import pl.mobilewarsaw.meetupchef.ui.groups.MeetupGroupsView

interface MeetupGroupsPresenter {

    fun bind(meetupGroupsView: MeetupGroupsView,
             context: Context,
             savedInstanceState: Bundle?)

    fun findMeetups(query: String)

    fun saveState(outState: Bundle)

    fun refreshGroups()

    fun onGroupClicked(meetupGroup: MeetupGroup)

    fun unbind()
}