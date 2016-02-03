package pl.mobilewarsaw.meetupchef.ui.participants

import android.database.Cursor


interface ParticipantsView {
    fun showEventName(eventName: String)

    fun showProgressBar()

    fun showParticipants(cursor: Cursor)
}