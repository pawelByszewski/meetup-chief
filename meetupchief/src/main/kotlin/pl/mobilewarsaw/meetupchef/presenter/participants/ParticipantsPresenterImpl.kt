package pl.mobilewarsaw.meetupchef.presenter.participants

import android.content.Context
import android.content.Intent
import android.os.Bundle
import pl.mobilewarsaw.meetupchef.ui.participants.EVENT_ID_KEY
import pl.mobilewarsaw.meetupchef.ui.participants.EVENT_NAME_KEY
import pl.mobilewarsaw.meetupchef.ui.participants.GROUP_URL_NAME_KEY
import pl.mobilewarsaw.meetupchef.ui.participants.ParticipantsView
import kotlin.properties.Delegates


class ParticipantsPresenterImpl : ParticipantsPresenter {

    lateinit private var context: Context
    lateinit private var participantsView: ParticipantsView
    val state = State()


    override fun bind(context: Context,
                      participantsView: ParticipantsView,
                      savedInstanceState: Bundle?,
                      intent: Intent?) {
        this.context = context
        this.participantsView = participantsView

        state.init(savedInstanceState, intent)

        participantsView.showEventName(state.eventName!!)
    }

    override fun unbind() {
        throw UnsupportedOperationException()
    }

    class State {

        var groupUrlName: String? = null

            private set
        var eventName: String? = null
            private set
        var eventId: Int = -1
            private set

        val isDetermined: Boolean
            get() = !(groupUrlName.isNullOrBlank() || eventName.isNullOrBlank() || eventId == -1)

        fun init(savedInstanceState: Bundle?, intent: Intent?){
            if (intent != null) {
                groupUrlName = intent.getStringExtra(GROUP_URL_NAME_KEY)
                eventName = intent.getStringExtra(EVENT_NAME_KEY)
                eventId = intent.getIntExtra(EVENT_ID_KEY, -1)
            }
        }
    }
}