package pl.mobilewarsaw.meetupchief.resource.local.meetup.repository

import android.content.Context
import android.database.Cursor
import pl.touk.android.basil.query
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupGroupContentProvider
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class GroupRepository(val context: Context) {

     fun fetchAllGroups(action: (Cursor) -> Unit) {
        Observable.just(context.contentResolver.query(MeetupGroupContentProvider.CONTENT_URI))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action)
    }
}