package pl.mobilewarsaw.meetupchef.resource.local.meetup.repository

import android.content.Context
import android.database.Cursor
import pl.mobilewarsaw.meetupchef.database.EventTable
import pl.mobilewarsaw.meetupchef.database.ParticipantTable
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupParticipantContentProvider
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class ParticipantRepository(val context: Context) {

     fun fetchParticipants(eventId: Int, action: (Cursor) -> Unit) {
         val where = ParticipantTable.EVENT_ID + "=?"
        Observable.just(context.contentResolver.query(MeetupParticipantContentProvider.CONTENT_URI,
                null, where,
                arrayOf("$eventId"), null ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action)
    }
}