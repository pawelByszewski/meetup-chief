package pl.mobilewarsaw.meetupchief.presenter.groups

import android.content.Context
import android.os.Bundle
import pl.mobilewarsaw.meetupchief.resource.local.meetup.model.MeetupGroup
import pl.mobilewarsaw.meetupchief.ui.groups.MeetupGroupsView

const val RESTORE_KEY = "shouldRestore"
const val RESTORE_QUERY = "restoreLastQuery"

interface MeetupGroupsPresenter {

    fun bind(context: Context, meetupGroupsView: MeetupGroupsView, savedInstanceState: Bundle?)

    fun findMeetups(query: String)

    fun refreshGroups()

    fun onGroupClicked(meetupGroup: MeetupGroup)
}