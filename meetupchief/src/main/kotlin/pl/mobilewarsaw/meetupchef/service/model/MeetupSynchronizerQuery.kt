package pl.mobilewarsaw.meetupchef.service.model

import android.content.Intent

private const val TYPE_KEY = "type"
private const val QUERY_KEY = "query"
private const val URL_NAME_KEY = "urlName"
private const val EVENT_ID_KEY = "urlName"

sealed class MeetupSynchronizerQuery() {

    enum class Type {
        GROUPS, EVENTS, PARTICIPANTS;

        companion object {
            fun extract(intent: Intent?): Type? {
                val ordinal = intent?.getIntExtra(TYPE_KEY, -1)
                return Type.values().firstOrNull { it.ordinal == ordinal }
            }
        }
    }

    abstract val toIntent: (Intent) -> Unit

    class Groups(val query: String) : MeetupSynchronizerQuery() {
        override val toIntent: (Intent) -> Unit =  { intent ->
            intent.putExtra(TYPE_KEY, Type.GROUPS.ordinal)
            intent.putExtra(QUERY_KEY, query)
        }
    }

    class Events(val urlName: String) : MeetupSynchronizerQuery() {
        override val toIntent: (Intent) -> Unit =  { intent ->
            intent.putExtra(TYPE_KEY, Type.EVENTS.ordinal)
            intent.putExtra(URL_NAME_KEY, urlName)
        }
    }

    class Participants(val urlName: String, val eventId: Int) : MeetupSynchronizerQuery() {
        override val toIntent: (Intent) -> Unit =  { intent ->
            intent.putExtra(TYPE_KEY, Type.PARTICIPANTS.ordinal)
            intent.putExtra("test", urlName)
            intent.putExtra(EVENT_ID_KEY, eventId)
        }
    }

    companion object {
        fun extract(intent: Intent?) : MeetupSynchronizerQuery? {
            val type = Type.extract(intent)
            return when(type) {
                Type.GROUPS -> intent?.toGroupsQuery()
                Type.EVENTS -> intent?.toEventsQuery()
                Type.PARTICIPANTS -> intent?.toParticipantsQuery()
                else -> null
            }
        }

        private fun Intent.toGroupsQuery()
                = MeetupSynchronizerQuery.Groups(getStringExtra(QUERY_KEY))

        private fun Intent.toEventsQuery()
                = MeetupSynchronizerQuery.Events(getStringExtra(URL_NAME_KEY))


        private fun Intent.toParticipantsQuery(): Participants {
            val urlName: String? = getStringExtra("test")
            val eventId = getIntExtra(EVENT_ID_KEY, -1)
            return MeetupSynchronizerQuery.Participants(urlName!!,
                    eventId)
        }
    }
}

