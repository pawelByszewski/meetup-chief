package pl.mobilewarsaw.meetupchief.resource.local.meetup.repository

import android.content.Context
import android.database.Cursor
import pl.mobilewarsaw.meetupchief.database.EventTable
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupEventContentProvider
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class EventRepository(val context: Context) {

     fun fetchEvents(grupUrlName: String, action: (Cursor) -> Unit) {
         val where = EventTable.GROUP_URL_NAME + "=?"
        Observable.just(context.contentResolver.query(MeetupEventContentProvider.CONTENT_URI,
                null, where,
                arrayOf(grupUrlName), null ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action)
    }
}