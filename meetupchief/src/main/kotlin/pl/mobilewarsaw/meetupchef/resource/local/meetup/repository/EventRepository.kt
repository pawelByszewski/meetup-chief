package pl.mobilewarsaw.meetupchef.resource.local.meetup.repository

import android.content.Context
import android.database.Cursor
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import pl.mobilewarsaw.meetupchef.database.EventTable
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupEventContentProvider



class EventRepository(val androidContext: Context) {

     fun fetchEventsAsync(grupUrlName: String): Deferred<Cursor> =
         async(CommonPool) {
             val where = EventTable.GROUP_URL_NAME + "=?"
             androidContext.contentResolver.query(MeetupEventContentProvider.CONTENT_URI,
                     null, where,
                     arrayOf(grupUrlName), null )
         }


}

