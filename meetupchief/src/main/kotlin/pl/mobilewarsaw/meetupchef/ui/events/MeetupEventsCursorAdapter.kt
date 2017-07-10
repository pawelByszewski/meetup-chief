package pl.mobilewarsaw.meetupchef.ui.groups

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupEvent
import pl.mobilewarsaw.meetupchef.ui.recycleview.CursorRecyclerViewAdapter


class MeetupEventCursorAdapter(cursor: Cursor? = null)
        : CursorRecyclerViewAdapter<MeetupEventCursorAdapter.ViewHolder>(cursor) {
    override fun updateGroups(cursor: Cursor) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    enum class StatusIcon(val icon: Int, val aplha: Float) {
        OPEN(R.mipmap.ic_open, 0.8f),
        CLOSED(R.mipmap.ic_closed, 0.2f);
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var eventName: TextView
        var attends: TextView
        var statusIcon: ImageView

        init {
            //TODO use kotlin power(kotterknife extension)
            eventName = view.findViewById(R.id.event_title) as TextView
            attends = view.findViewById(R.id.attends) as TextView
            statusIcon = view.findViewById(R.id.status_icon) as ImageView
        }

        fun setup(meetupEvent: MeetupEvent) {
            eventName.text = meetupEvent.name
            attends.text = "${meetupEvent.attends}"
            val status = extractStatusIcon(meetupEvent)
            statusIcon.setImageResource(status.icon)
            statusIcon.alpha = status.aplha
        }

        private fun extractStatusIcon(meetupEvent: MeetupEvent): StatusIcon {
            val status = if (meetupEvent.isOpen()) {
                StatusIcon.OPEN
            } else {
                StatusIcon.CLOSED
            }
            return status
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                                     .inflate(R.layout.meetup_event_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, cursor: Cursor) {
        val meetupEvent = MeetupEvent.fromCursor(cursor)
        viewHolder.setup(meetupEvent)
    }
}
