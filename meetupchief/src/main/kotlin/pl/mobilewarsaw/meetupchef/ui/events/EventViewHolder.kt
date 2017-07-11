package pl.mobilewarsaw.meetupchef.ui.events

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.database.model.Event
import pl.mobilewarsaw.meetupchef.database.model.isOpen


class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var eventName: TextView
    var attends: TextView
    var statusIcon: ImageView

    init {
        //TODO use kotlin power(kotterknife extension)
        eventName = view.findViewById(R.id.event_title) as TextView
        attends = view.findViewById(R.id.attends) as TextView
        statusIcon = view.findViewById(R.id.status_icon) as ImageView
    }

    fun setup(meetupEvent: Event) {
        eventName.text = meetupEvent.name
        attends.text = "${meetupEvent.attends}"
        val status = extractStatusIcon(meetupEvent)
        statusIcon.setImageResource(status.icon)
        statusIcon.alpha = status.aplha
    }

    private fun extractStatusIcon(meetupEvent: Event): StatusIcon {
        val status = if (meetupEvent.isOpen()) {
            StatusIcon.OPEN
        } else {
            StatusIcon.CLOSED
        }
        return status
    }

    enum class StatusIcon(val icon: Int, val aplha: Float) {
        OPEN(R.mipmap.ic_open, 0.8f),
        CLOSED(R.mipmap.ic_closed, 0.2f);
    }

}