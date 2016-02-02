package pl.mobilewarsaw.meetupchef.resource.local.meetup.model

import android.database.Cursor
import pl.mobilewarsaw.meetupchef.database.ParticipantTable
import pl.touk.basil.getString


data class Participant( val photoUrl: String,
                        val name: String) {
    companion object {
        fun fromCursor(cursor: Cursor): Participant {
            return Participant(
                    name = cursor.getString(ParticipantTable.NAME),
                    photoUrl = cursor.getString(ParticipantTable.PHOTO))
        }
    }
}