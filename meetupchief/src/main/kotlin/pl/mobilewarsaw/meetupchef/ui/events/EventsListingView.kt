package pl.mobilewarsaw.meetupchef.ui.events

import android.content.Intent
import android.database.Cursor

interface EventsListingView {

    open fun getIntent(): Intent

    fun showInToolbar(meeupGroupInitData: MeetuGroupInitData)

    fun showEvents(cursor: Cursor)

    fun showProgressBar()

    fun showGroupPhoto(photoUrl: String?)
}