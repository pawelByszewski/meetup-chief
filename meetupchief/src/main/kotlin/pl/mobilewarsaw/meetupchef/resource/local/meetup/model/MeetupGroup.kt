package pl.mobilewarsaw.meetupchef.resource.local.meetup.model

import android.database.Cursor
import pl.mobilewarsaw.meetupchef.database.MeetupGroupTable
import pl.touk.basil.getString
import pl.touk.basil.getInt


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