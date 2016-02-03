package pl.mobilewarsaw.meetupchef.presenter.participants

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupParticipantContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.ParticipantRepository
import pl.mobilewarsaw.meetupchef.service.MeetupSynchronizer
import pl.mobilewarsaw.meetupchef.service.model.MeetupSynchronizerQuery
import pl.mobilewarsaw.meetupchef.ui.participants.EVENT_ID_KEY
import pl.mobilewarsaw.meetupchef.ui.participants.EVENT_NAME_KEY
import pl.mobilewarsaw.meetupchef.ui.participants.GROUP_URL_NAME_KEY
import pl.mobilewarsaw.meetupchef.ui.participants.ParticipantsView
import pl.touk.basil.registerUriObserver
import uy.kohesive.injekt.injectValue
import kotlin.properties.Delegates


class ParticipantsPresenterImpl : ParticipantsPresenter {

    lateinit private var context: Context
    lateinit private var participantsView: ParticipantsView
    val state = State()

    private val participantsRepository: ParticipantRepository by injectValue()


    override fun bind(context: Context,
                      participantsView: ParticipantsView,
                      savedInstanceState: Bundle?,
                      intent: Intent?) {
        this.context = context
        this.participantsView = participantsView

        state.init(savedInstanceState, intent)

        contentResolver.registerUriObserver(MeetupParticipantContentProvider.CONTENT_URI) {
            showParticipants()
        }

        participantsView.showEventName(state.eventName!!)

        participantsView.showProgressBar()
        synchronizeParticipants()

    }

    private val contentResolver: ContentResolver
        get() = context.contentResolver

    private fun showParticipants() {
        participantsRepository.fetchParticipants(state.eventId) { cursor: Cursor ->
            participantsView?.showParticipants(cursor) }
    }

    private fun synchronizeParticipants() {
        val query = MeetupSynchronizerQuery.Participants(state.groupUrlName!!, state.eventId)
        val intent = Intent(context, MeetupSynchronizer::class.java)
        query.toIntent(intent)
        context.startService(intent)
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