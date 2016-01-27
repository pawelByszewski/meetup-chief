package pl.mobilewarsaw.meetupchief.ui.events

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import pl.mobilewarsaw.meetupchief.R
import pl.mobilewarsaw.meetupchief.resource.local.meetup.model.MeetupGroup
import pl.mobilewarsaw.meetupchief.ui.recycleview.CursorRecyclerViewAdapter


class MeetupGroupsCursorAdapter(cursor: Cursor)
        : CursorRecyclerViewAdapter<MeetupGroupsCursorAdapter.ViewHolder>(cursor) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var groupName: TextView
        var photoLink: TextView

        init {
            //TODO use kotlin power(kotterknife extension)
            groupName = view.findViewById(R.id.label) as TextView
            photoLink = view.findViewById(R.id.link) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meetup_group_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, cursor: Cursor) {
        val meetupGroup = MeetupGroup.fromCursor(cursor)
        viewHolder.groupName.text = meetupGroup.name
        viewHolder.photoLink.text = meetupGroup.photoUrl ?: ""
    }
}