package pl.mobilewarsaw.meetupchef.presenter.groups

import android.content.Context
import android.os.Bundle
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import pl.mobilewarsaw.meetupchef.database.model.MeetupGroup
import pl.mobilewarsaw.meetupchef.resource.GroupsManager
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.GroupRepository
import pl.mobilewarsaw.meetupchef.ui.events.EventsListingActivity
import pl.mobilewarsaw.meetupchef.ui.groups.MeetupGroupsView
import pl.mobilewarsaw.meetupchef.ui.groups.bus.GroupClicked
import uy.kohesive.injekt.injectValue


class MeetupGroupsPresenterImpl : MeetupGroupsPresenter {

    private val groupRepository: GroupRepository by injectValue()
    private val bus: Bus by injectValue()
    private val groupsManager: GroupsManager by injectValue()

    private val state = State()
    private var meetupGroupsView: MeetupGroupsView? = null
    private var context: Context? = null

    override fun bind(meetupGroupsView: MeetupGroupsView,
                      context: Context,
                      savedInstanceState: Bundle?) {
        this.context = context
        this.meetupGroupsView = meetupGroupsView

        restoreState(savedInstanceState)
        bus.register(this)
    }

    private fun showAllGroups() {
        launch(UI) {
            val groupsResult = groupRepository.fetchAllGroupsAsync().await()
            meetupGroupsView?.showMeetupGroups(groupsResult)
        }
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        state.setup(savedInstanceState)
        if (state.isDetermined) {
            showAllGroups()
        }
    }

    override fun findMeetups(query: String) {
        checkViewBinding()
        state.query = query
        meetupGroupsView?.showProgressBar()

        launch(UI) {
            val groupsResult = groupsManager.updateEventsAsync(query).await()
            meetupGroupsView?.showMeetupGroups(groupsResult)
        }
    }

    private fun checkViewBinding() {
        if (meetupGroupsView == null) {
            throw IllegalStateException("A view must be binded to the presenter")
        }
    }

    @Subscribe
    fun onMeetupGroupClick(groupClicked: GroupClicked) {
        onGroupClicked(groupClicked.meetupGroup)
    }

    override fun refreshGroups() {
        if (state.isDetermined) {
            findMeetups(state.query)
        }
    }

    override fun onGroupClicked(meetupGroup: MeetupGroup) {
        val intent = EventsListingActivity.createIntent(context!!, meetupGroup)
        context?.startActivity(intent)
    }

    override fun saveState(outState: Bundle)
        = state.save(outState)

    override fun unbind() {
        bus.unregister(this)
        meetupGroupsView = null
        context = null
    }
}

