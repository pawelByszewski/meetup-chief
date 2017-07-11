package pl.mobilewarsaw.meetupchef.ui.events

import android.content.Intent
import io.requery.query.Result
import pl.mobilewarsaw.meetupchef.database.model.EventEntity
import pl.mobilewarsaw.meetupchef.presenter.events.State

interface EventsListingView {

    open fun getIntent(): Intent

    fun showInToolbar(meeupGroupInitData: State)

    fun showEvents(eventsResult: Result<EventEntity>)

    fun showProgressBar()

    fun showGroupPhoto(photoUrl: String?)
}