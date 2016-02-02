package pl.mobilewarsaw.meetupchef.presenter.groups

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupGroupContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupGroup
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.GroupRepository
import pl.mobilewarsaw.meetupchef.service.MeetupSynchronizer
import pl.mobilewarsaw.meetupchef.service.error.NetworkError
import pl.mobilewarsaw.meetupchef.service.model.MeetupSynchronizerQuery
import pl.mobilewarsaw.meetupchef.ui.events.EventsListingActivity
import pl.mobilewarsaw.meetupchef.ui.groups.MeetupGroupsView
import pl.mobilewarsaw.meetupchef.ui.groups.bus.GroupClicked
import pl.touk.basil.registerUriObserver
import uy.kohesive.injekt.injectValue


class MeetupGroupsPresenterImpl : MeetupGroupsPresenter {

    private val groupRepository: GroupRepository by injectValue()
    private val bus: Bus by injectValue()

    private val state = State()
    private var meetupGroupsView: MeetupGroupsView? = null
    private var context: Context? = null

    override fun bind(meetupGroupsView: MeetupGroupsView,
                      context: Context,
                      savedInstanceState: Bundle?) {
        this.context = context
        this.meetupGroupsView = meetupGroupsView

        contentResolver.registerUriObserver(MeetupGroupContentProvider.CONTENT_URI) {
            showAllGroups()
        }

        restoreState(savedInstanceState)
        bus.register(this)
    }

    private val contentResolver: ContentResolver
        get() = context!!.contentResolver

    private fun showAllGroups()
        = groupRepository.fetchForQuery(state.query) { cursor: Cursor ->
                                                    meetupGroupsView?.showMeetupGroups(cursor)
                                                }

    private fun showCachedGroups()
        = groupRepository.fetchCached { cursor: Cursor -> meetupGroupsView?.showMeetupGroups(cursor) }

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

        val intent = Intent(context, MeetupSynchronizer::class.java)
        val synchronizerQuery = MeetupSynchronizerQuery.Groups(query)
        synchronizerQuery.toIntent(intent)
        context?.startService(intent)
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

    @Subscribe
    fun onNetworkError(error: NetworkError) {
         meetupGroupsView?.showNetworkError()
         showCachedGroups()
    }


    override fun refreshGroups() {
        if (state.isDetermined) {
            findMeetups(state.query)
            meetupGroupsView?.showProgressBar()
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

