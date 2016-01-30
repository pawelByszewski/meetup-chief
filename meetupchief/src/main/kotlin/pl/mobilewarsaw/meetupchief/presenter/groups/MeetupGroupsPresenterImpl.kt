package pl.mobilewarsaw.meetupchief.presenter.groups

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupGroupContentProvider
import pl.mobilewarsaw.meetupchief.resource.local.meetup.model.MeetupGroup
import pl.mobilewarsaw.meetupchief.resource.local.meetup.repository.GroupRepository
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.MeetupRemoteResource
import pl.mobilewarsaw.meetupchief.service.events.MeetupSynchronizer
import pl.mobilewarsaw.meetupchief.ui.events.EventsListingActivity
import pl.mobilewarsaw.meetupchief.ui.groups.MeetupGroupsView
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import uy.kohesive.injekt.injectValue
import kotlin.properties.Delegates

const val QUERY_KEY ="query"

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

    private fun restoreState(savedInstanceState: Bundle?) {
        val shouldRestoreQuery = savedInstanceState?.getBoolean(RESTORE_KEY, false) ?: false
        if (shouldRestoreQuery) {
            currentQuery = savedInstanceState?.getString(RESTORE_QUERY)
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
        val intent = Intent(context, MeetupSynchronizer::class.java)
        intent.putExtra(QUERY_KEY, query)
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

