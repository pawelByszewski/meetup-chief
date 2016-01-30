package pl.mobilewarsaw.meetupchief.resource.local.meetup.model

import android.database.Cursor
import pl.mobilewarsaw.meetupchief.database.MeetupGroupTable
import pl.touk.android.basil.getString
import pl.touk.android.basil.getInt


data class MeetupGroup(val groupId: String,
                       val name: String,
                       val members: Int,
                       val photoUrl: String?,
                       val category: String,
                       val urlName: String) {

    companion object {
        fun fromCursor(cursor: Cursor): MeetupGroup {
            return MeetupGroup(
                    groupId = cursor.getString(MeetupGroupTable.GROUP_ID),
                    name = cursor.getString(MeetupGroupTable.NAME),
                    members = cursor.getInt(MeetupGroupTable.MEMBERS),
                    photoUrl = cursor.getString(MeetupGroupTable.PHOTO),
                    category = cursor.getString(MeetupGroupTable.CATEGORY),
                    urlName = cursor.getString(MeetupGroupTable.URL_NAME))
        }
    }
}