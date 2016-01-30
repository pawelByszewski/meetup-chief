package pl.mobilewarsaw.meetupchief.ui.groups

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import pl.mobilewarsaw.meetupchief.R
import pl.mobilewarsaw.meetupchief.resource.local.meetup.model.MeetupEvent
import pl.mobilewarsaw.meetupchief.ui.recycleview.CursorRecyclerViewAdapter
import uy.kohesive.injekt.injectValue


class MeetupEventCursorAdapter(cursor: Cursor? = null)
        : CursorRecyclerViewAdapter<MeetupEventCursorAdapter.ViewHolder>(cursor) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var eventName: TextView

        init {
            //TODO use kotlin power(kotterknife extension)
            eventName = view.findViewById(R.id.event_name) as TextView

        }

        fun setup(meetupEvent: MeetupEvent) {
            eventName.text = meetupEvent.name
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
