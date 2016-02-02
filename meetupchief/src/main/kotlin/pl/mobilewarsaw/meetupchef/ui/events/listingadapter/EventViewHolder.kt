package pl.mobilewarsaw.meetupchef.ui.events.listingadapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupEvent
import kotlin.properties.Delegates

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    enum class StatusIcon(val icon: Int, val aplha: Float) {
        OPEN(R.mipmap.ic_open, 0.8f),
        CLOSED(R.mipmap.ic_closed, 0.2f);
    }

    var event: MeetupEvent by Delegates.notNull()

    var eventName: TextView
    var attends: TextView
    var statusIcon: ImageView
    var container: View

    init {
        //TODO use kotlin power(kotterknife extension)
        eventName = view.findViewById(R.id.event_title) as TextView
        attends = view.findViewById(R.id.attends) as TextView
        statusIcon = view.findViewById(R.id.status_icon) as ImageView
        container = view.findViewById(R.id.card_view)
    }

    fun setup(event: MeetupEvent) {
        this.event = event
        eventName.text = event.name
        attends.text = "${event.attends}"
        val status = extractStatusIcon(event)
        statusIcon.setImageResource(status.icon)
        statusIcon.alpha = status.aplha
        container.tag = this
    }

    private fun extractStatusIcon(meetupEvent: MeetupEvent): StatusIcon
        = when {
            meetupEvent.isOpen() -> StatusIcon.OPEN
            else ->  StatusIcon.CLOSED
        }
}