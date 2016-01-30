package pl.mobilewarsaw.meetupchief.ui.events

import android.content.Intent


interface EventsListingView {

    open fun getIntent(): Intent

    fun showInToolbar(meeupGroupInitData: MeetuGroupInitData)
}