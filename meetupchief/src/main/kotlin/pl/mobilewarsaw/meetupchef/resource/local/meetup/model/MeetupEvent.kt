package pl.mobilewarsaw.meetupchef.resource.local.meetup.model

import android.database.Cursor
import pl.mobilewarsaw.meetupchef.database.EventTable
import pl.touk.android.basil.getInt
import pl.touk.android.basil.getString

private const val STATUS_CLOSED = "past"

data class MeetupEvent(val eventId: String,
                       val name: String,
                       val attends: Int,
                       val status: String?,
                       val description: String,
                       val groupUrlName: String) {

    companion object {
        fun fromCursor(cursor: Cursor): MeetupEvent {
            return MeetupEvent(
                    eventId = cursor.getString(EventTable.EVENT_ID),
                    name = cursor.getString(EventTable.NAME),
                    attends = cursor.getInt(EventTable.ATTENDS),
                    status = cursor.getString(EventTable.STATUS),
                    description = cursor.getString(EventTable.DESCRIPTION),
                    groupUrlName = cursor.getString(EventTable.GROUP_URL_NAME))
        }
    }

    fun isOpen() = status != STATUS_CLOSED

}