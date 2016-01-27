package pl.mobilewarsaw.meetupchief.resource.local.meetup.model

import android.database.Cursor
import com.google.gson.annotations.SerializedName
import pl.mobilewarsaw.meetupchief.database.MeetupGroupTable
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.model.MeetupPhoto


data class MeetupGroup(val groupId: String,
                       val name: String,
                       val members: Int,
                       val photoUrl: String?) {

    companion object {
        fun fromCursor(cursor: Cursor): MeetupGroup {
            return MeetupGroup(
                    groupId = cursor.getString(MeetupGroupTable.GROUP_ID),
                    name = cursor.getString(MeetupGroupTable.NAME),
                    members = cursor.getInt(MeetupGroupTable.MEMBERS),
                    photoUrl = cursor.getString(MeetupGroupTable.PHOTO))
        }
    }
}

//TODO to basil
fun Cursor.getString(columnName: String) = getString(getColumnIndex(columnName))
fun Cursor.getInt(columnName: String) = getInt(getColumnIndex(columnName))