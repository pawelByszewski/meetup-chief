package pl.mobilewarsaw.meetupchef.ui.groups

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.otto.Bus
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupEvent
import pl.mobilewarsaw.meetupchef.ui.events.listingadapter.EventClicked
import pl.mobilewarsaw.meetupchef.ui.events.listingadapter.EventViewHolder
import pl.mobilewarsaw.meetupchef.ui.groups.bus.GroupClicked
import pl.mobilewarsaw.meetupchef.ui.groups.listingadapter.GroupViewHolder
import pl.mobilewarsaw.meetupchef.ui.recycleview.CursorRecyclerViewAdapter
import uy.kohesive.injekt.injectValue


class MeetupEventCursorAdapter(cursor: Cursor? = null)
        : CursorRecyclerViewAdapter<EventViewHolder>(cursor) {

    private val bus: Bus by injectValue()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                                     .inflate(R.layout.meetup_event_item, parent, false)

        val eventHolder = EventViewHolder(itemView)
        eventHolder.container.setOnClickListener { clickedView: View ->
            val clickedViewHolder = clickedView.tag as EventViewHolder
            bus.post(EventClicked(clickedViewHolder.event))
        }
        return eventHolder
    }

    override fun onBindViewHolder(eventViewHolder: EventViewHolder, cursor: Cursor) {
        val meetupEvent = MeetupEvent.fromCursor(cursor)
        eventViewHolder.setup(meetupEvent)
    }
}
