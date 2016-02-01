package pl.mobilewarsaw.meetupchef.resource.local.meetup.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import pl.mobilewarsaw.meetupchef.database.CACHED
import pl.mobilewarsaw.meetupchef.database.EventTable
import pl.mobilewarsaw.meetupchef.database.MeetupGroupTable
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupGroupContentProvider
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import pl.touk.basil.query

open class GroupRepository(val context: Context) {

     open fun fetchForQuery(query: String, action: (Cursor) -> Unit) {
         val where = MeetupGroupTable.QUERY + "=?"
        Observable.just(context.contentResolver.query(MeetupGroupContentProvider.CONTENT_URI,
                null, where,
                arrayOf(query), null ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action)
    }

    open fun fetchCached(action: (Cursor) -> Unit) {
        val where = MeetupGroupTable.CACHE + "=?"
        Observable.just(context.contentResolver.query(MeetupGroupContentProvider.CONTENT_URI,
                null, where,
                arrayOf("$CACHED"), null ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action)
    }

    fun markCached(urlName: String) {
        val contentValues = ContentValues()
        contentValues.put(MeetupGroupTable.CACHE, 1)
        val where = "${MeetupGroupTable.URL_NAME}=?"
        context.contentResolver.update(MeetupGroupContentProvider.CONTENT_URI, contentValues,
                                        where, arrayOf(urlName))
    }

}