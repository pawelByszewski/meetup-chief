package pl.mobilewarsaw.meetupchef.service.model

import android.content.Intent

private const val TYPE_KEY = "type"
private const val QUERY_KEY = "query"
private const val URL_NAME_KEY = "urlName"

sealed class MeetupSynchronizerQuery() {

    enum class Type {
        GROUPS, EVENTS;

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

    companion object {
        fun extract(intent: Intent?) : MeetupSynchronizerQuery? {
            val type = Type.extract(intent)
            return when(type) {
                Type.GROUPS -> intent?.toGroupsQuery()
                Type.EVENTS -> intent?.toEventsQuery()
                else -> null
            }
        }

        private fun Intent.toGroupsQuery()
                = MeetupSynchronizerQuery.Groups(getStringExtra(QUERY_KEY))

        private fun Intent.toEventsQuery()
                = MeetupSynchronizerQuery.Events(getStringExtra(URL_NAME_KEY))
    }
}

