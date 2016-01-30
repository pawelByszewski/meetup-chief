package pl.mobilewarsaw.meetupchief.presenter.groups

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupGroupContentProvider
import pl.mobilewarsaw.meetupchief.resource.local.meetup.model.MeetupGroup
import pl.mobilewarsaw.meetupchief.resource.local.meetup.repository.GroupRepository
import pl.mobilewarsaw.meetupchief.service.MeetupSynchronizer
import pl.mobilewarsaw.meetupchief.service.model.MeetupSynchronizerQuery
import pl.mobilewarsaw.meetupchief.ui.events.EventsListingActivity
import pl.mobilewarsaw.meetupchief.ui.groups.MeetupGroupsView
import uy.kohesive.injekt.injectValue


class MeetupGroupsPresenterImpl : MeetupGroupsPresenter {

    lateinit private var context: Context
    private val groupRepository: GroupRepository by injectValue()

    private var meetupGroupsView: MeetupGroupsView? = null
    private var currentQuery: String? = null

    override fun bind(context: Context, meetupGroupsView: MeetupGroupsView,
                      savedInstanceState: Bundle?) {
        this.context = context
        this.meetupGroupsView = meetupGroupsView

        registerUriObserver(MeetupGroupContentProvider.CONTENT_URI) {
            showAllGroups()
        }

        restoreState(savedInstanceState)
    }

    private fun showAllGroups() {
        groupRepository.fetchAllGroups { cursor: Cursor -> meetupGroupsView?.showMeetupGroups(cursor) }
    }

    private fun restoreState(savedInstanceState: Bundle? = null) {
        currentQuery = savedInstanceState?.getString(RESTORE_QUERY) ?: currentQuery
        if (!currentQuery.isNullOrBlank()) {
            showAllGroups()
        }
    }

    private fun registerUriObserver(uri: Uri, action: () -> Unit) {
        this.context.contentResolver.registerContentObserver(uri, true,
                object : ContentObserver(Handler()) {
                    override fun onChange(selfChange: Boolean) = action()
                })
    }

    override fun findMeetups(query: String) {
        checkViewBinding()
        currentQuery = query

        val synchronizerQuery = MeetupSynchronizerQuery.Groups(query)
        val intent = Intent(context, MeetupSynchronizer::class.java)
        synchronizerQuery.toIntent(intent)
        context.startService(intent)
    }

    private fun checkViewBinding() {
        if (meetupGroupsView == null) {
            throw IllegalStateException("A view must be binded to the presenter")
        }
    }

    override fun refreshGroups() {
        val query = currentQuery ?: null
        if (query != null) {
            findMeetups(query)
        }
    }

    override fun onGroupClicked(meetupGroup: MeetupGroup) {
        val intent = EventsListingActivity.createIntent(context, meetupGroup)
        context.startActivity(intent)
    }
}

