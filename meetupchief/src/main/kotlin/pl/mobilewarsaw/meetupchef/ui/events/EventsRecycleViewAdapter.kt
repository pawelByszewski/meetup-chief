package pl.mobilewarsaw.meetupchef.ui.groups.listingadapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.requery.query.Result
import io.requery.sql.ResultSetIterator
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.database.model.Event
import pl.mobilewarsaw.meetupchef.ui.events.EventViewHolder


class EventsRecycleViewAdapter
        : RecyclerView.Adapter<EventViewHolder>() {

    override fun getItemCount(): Int
        = count

    private var count = 0
    private var eventResultSet: ResultSetIterator<Event>? = null


    override fun onBindViewHolder(viewHolder: EventViewHolder, position: Int) {
        val meetupGroup = eventResultSet!![position]
        viewHolder.setup(meetupGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.meetup_event_item, parent, false)
        return EventViewHolder(itemView)
    }

    fun updateEvents(events: Result<Event>) {
        this.count = events.count()
        eventResultSet = events.iterator() as ResultSetIterator<Event>
    }
}