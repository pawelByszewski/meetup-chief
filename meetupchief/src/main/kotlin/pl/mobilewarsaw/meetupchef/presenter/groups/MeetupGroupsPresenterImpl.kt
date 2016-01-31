package pl.mobilewarsaw.meetupchef.presenter.groups

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupGroupContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupGroup
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.GroupRepository
import pl.mobilewarsaw.meetupchef.service.MeetupSynchronizer
import pl.mobilewarsaw.meetupchef.service.model.MeetupSynchronizerQuery
import pl.mobilewarsaw.meetupchef.ui.events.EventsListingActivity
import pl.mobilewarsaw.meetupchef.ui.groups.MeetupGroupsView
import pl.touk.basil.registerUriObserver
import uy.kohesive.injekt.injectValue


class MeetupGroupsPresenterImpl : MeetupGroupsPresenter {

    private val groupRepository: GroupRepository by injectValue()
    private val state = State()
    private var meetupGroupsView: MeetupGroupsView? = null
    lateinit private var context: Context

    override fun bind(meetupGroupsView: MeetupGroupsView,
                      context: Context,
                      savedInstanceState: Bundle?) {
        this.context = context
        this.meetupGroupsView = meetupGroupsView

        contentResolver.registerUriObserver(MeetupGroupContentProvider.CONTENT_URI) {
            showAllGroups()
        }

        restoreState(savedInstanceState)
    }

    private val contentResolver: ContentResolver
        get() = context.contentResolver

    private fun showAllGroups()
        = groupRepository.fetchAllGroups { cursor: Cursor -> meetupGroupsView?.showMeetupGroups(cursor) }

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
        context.startService(intent)
    }

    private fun checkViewBinding() {
        if (meetupGroupsView == null) {
            throw IllegalStateException("A view must be binded to the presenter")
        }
    }

    override fun refreshGroups() {
        if (state.isDetermined) {
            findMeetups(state.query)
        }
    }

    override fun onGroupClicked(meetupGroup: MeetupGroup) {
        val intent = EventsListingActivity.createIntent(context, meetupGroup)
        context.startActivity(intent)
    }

    override fun saveState(outState: Bundle)
        = state.save(outState)
}

