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
import pl.mobilewarsaw.meetupchief.ui.events.EventsListView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import uy.kohesive.injekt.injectValue

const val QUERY_KEY ="sdf"

class EventsListPresenterImpl: EventListPresenter {

    lateinit private var context: Context
    lateinit private var eventsListView: EventsListView

    override fun findMeetups(query: String) {
        val cursor = context.contentResolver.query(MeetupGroupContentProvider.CONTENT_URI)
        eventsListView.showMeetupGroups(cursor)
        cursor.setNotificationUri(context.contentResolver, MeetupGroupContentProvider.CONTENT_URI)
        cursor.registerContentObserver( object: ContentObserver(Handler()) {
            override fun onChange(selfChange: Boolean) {
                eventsListView.showMeetupGroups(context.contentResolver.query(MeetupGroupContentProvider.CONTENT_URI))
            }
        })

        val intent = Intent(context, MeetupSynchronizer::class.java)
        intent.putExtra(QUERY_KEY, query)
        context.startService(intent)
    }

    override fun bind(context: Context, eventsListView: EventsListView) {
        this.context = context
        this.eventsListView = eventsListView
    }
}

//TODO to basil
inline fun ContentResolver.query(uri: Uri): Cursor
        = query(uri, null, null, null, null)