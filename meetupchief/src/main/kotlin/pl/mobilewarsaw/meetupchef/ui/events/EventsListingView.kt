package pl.mobilewarsaw.meetupchef.ui.events

import android.content.Intent
import android.database.Cursor
import pl.mobilewarsaw.meetupchef.presenter.events.State

interface EventsListingView {

    open fun getIntent(): Intent

    fun showInToolbar(meeupGroupInitData: State)

    fun showEvents(cursor: Cursor)

    fun showProgressBar()

    fun showGroupPhoto(photoUrl: String?)
}