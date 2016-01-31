package pl.mobilewarsaw.meetupchef.resource.local.meetup.repository

import android.content.Context
import android.database.Cursor
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupGroupContentProvider
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import pl.touk.basil.query

open class GroupRepository(val context: Context) {

     open fun fetchAllGroups(action: (Cursor) -> Unit) {
        Observable.just(context.contentResolver.query(MeetupGroupContentProvider.CONTENT_URI))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action)
    }
}