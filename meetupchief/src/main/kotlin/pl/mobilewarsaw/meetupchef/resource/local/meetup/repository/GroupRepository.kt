package pl.mobilewarsaw.meetupchef.resource.local.meetup.repository

import android.content.Context
import android.database.Cursor
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupGroupContentProvider
import pl.touk.basil.query

open class GroupRepository(val androidContext: Context) {

     open fun fetchAllGroupsAsync() =
        async(CommonPool) {
            androidContext.contentResolver.query(MeetupGroupContentProvider.CONTENT_URI)
        }

}