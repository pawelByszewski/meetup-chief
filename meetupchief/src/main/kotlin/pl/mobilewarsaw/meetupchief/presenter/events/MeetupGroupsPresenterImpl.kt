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
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import uy.kohesive.injekt.injectValue
import kotlin.properties.Delegates

const val QUERY_KEY ="query"

class MeetupGroupsPresenterImpl : MeetupGroupsPresenter {

    lateinit private var context: Context
    private var meetupGroupsView: MeetupGroupsView? = null

    override fun bind(context: Context, meetupGroupsView: MeetupGroupsView) {
        this.context = context
        this.meetupGroupsView = meetupGroupsView

        registerUriObserver(MeetupGroupContentProvider.CONTENT_URI) {
            Observable.just(context.contentResolver.query(MeetupGroupContentProvider.CONTENT_URI))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { cursor -> meetupGroupsView.showMeetupGroups(cursor) }
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
        val intent = Intent(context, MeetupSynchronizer::class.java)
        intent.putExtra(QUERY_KEY, query)
        context.startService(intent)
    }

    private fun checkViewBinding() {
        if (meetupGroupsView == null) {
            throw IllegalStateException("A view must be binded to the presenter")
        }
    }
}

//TODO to basil
inline fun ContentResolver.query(uri: Uri): Cursor
        = query(uri, null, null, null, null)