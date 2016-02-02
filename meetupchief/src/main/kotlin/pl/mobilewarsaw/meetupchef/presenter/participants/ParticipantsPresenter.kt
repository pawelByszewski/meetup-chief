package pl.mobilewarsaw.meetupchef.presenter.participants

import android.content.Context
import android.content.Intent
import android.os.Bundle
import pl.mobilewarsaw.meetupchef.ui.participants.ParticipantsView


interface ParticipantsPresenter {
    fun bind(context: Context,
             participantsView: ParticipantsView,
             savedInstanceState: Bundle?,
             intent: Intent?)

    fun unbind()
}