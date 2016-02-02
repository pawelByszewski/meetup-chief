package pl.mobilewarsaw.meetupchef.resource.local.meetup.model

import android.database.Cursor
import pl.mobilewarsaw.meetupchef.database.GroupTable
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
                    groupId = cursor.getString(GroupTable.GROUP_ID),
                    name = cursor.getString(GroupTable.NAME),
                    members = cursor.getInt(GroupTable.MEMBERS),
                    photoUrl = cursor.getString(GroupTable.PHOTO),
                    category = cursor.getString(GroupTable.CATEGORY),
                    urlName = cursor.getString(GroupTable.URL_NAME))
        }
    }
}