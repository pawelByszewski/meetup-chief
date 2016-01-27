package pl.mobilewarsaw.meetupchief.presenter.events

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.util.Log
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupGroupContentProvider
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.MeetupRemoteResource
import pl.mobilewarsaw.meetupchief.service.events.MeetupSynchronizer
import pl.mobilewarsaw.meetupchief.ui.events.MeetupGroupsView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import uy.kohesive.injekt.injectValue
import kotlin.properties.Delegates

const val QUERY_KEY ="query"

class MeetupGroupsPresenterImpl : MeetupGroupsPresenter {

    lateinit private var context: Context
    private var meetupGroupsView: MeetupGroupsView by Delegates.notNull()

    override fun findMeetups(query: String) {
        checkViewBinding()
        val cursor = context.contentResolver.query(MeetupGroupContentProvider.CONTENT_URI)
        meetupGroupsView.showMeetupGroups(cursor)
        cursor.setNotificationUri(context.contentResolver, MeetupGroupContentProvider.CONTENT_URI)
        cursor.registerContentObserver( object: ContentObserver(Handler()) {
            override fun onChange(selfChange: Boolean) {
                meetupGroupsView.showMeetupGroups(context.contentResolver.query(MeetupGroupContentProvider.CONTENT_URI))
            }
        })

        val intent = Intent(context, MeetupSynchronizer::class.java)
        intent.putExtra(QUERY_KEY, query)
        context.startService(intent)
    }

    private fun checkViewBinding() {
        if (meetupGroupsView == null) {
            throw IllegalStateException("A view must be binded to the presenter")
        }
    }

    override fun bind(context: Context, meetupGroupsView: MeetupGroupsView) {
        this.context = context
        this.meetupGroupsView = meetupGroupsView
    }
}

//TODO to basil
inline fun ContentResolver.query(uri: Uri): Cursor
        = query(uri, null, null, null, null)