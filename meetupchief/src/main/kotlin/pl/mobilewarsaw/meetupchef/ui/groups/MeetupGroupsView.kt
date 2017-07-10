package pl.mobilewarsaw.meetupchef.ui.groups

import io.requery.query.Result
import pl.mobilewarsaw.meetupchef.database.model.MeetupGroup


interface MeetupGroupsView {

    fun showMeetupGroups(groups: Result<MeetupGroup>)

    fun showProgressBar()
}