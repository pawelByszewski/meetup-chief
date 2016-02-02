package pl.mobilewarsaw.meetupchef.ui.groups

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupEvent
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.Participant
import pl.mobilewarsaw.meetupchef.ui.groups.listingadapter.GroupViewHolder
import pl.mobilewarsaw.meetupchef.ui.recycleview.CursorRecyclerViewAdapter
import uy.kohesive.injekt.injectValue


class MeetupParticipantsCursorAdapter(cursor: Cursor? = null)
        : CursorRecyclerViewAdapter<MeetupParticipantsCursorAdapter.ViewHolder>(cursor) {

    private val picasso: Picasso by injectValue()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var name: TextView
        var photo: ImageView

        init {
            //TODO use kotlin power(kotterknife extension)
            name = view.findViewById(R.id.participant_name) as TextView
            photo = view.findViewById(R.id.participant_photo) as ImageView
        }

        fun setup(participant: Participant) {
            name.text = participant.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                                     .inflate(R.layout.meetup_participant_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(eventViewHolder: ViewHolder, cursor: Cursor) {
        val participant = Participant.fromCursor(cursor)
        eventViewHolder.setup(participant)
        showImage(eventViewHolder, participant.photoUrl)
    }

    private fun showImage(viewHolder: ViewHolder, pictureUrl: String) {
        picasso.load(pictureUrl)
                .fit()
                .centerCrop()
                .into(viewHolder.photo)
    }
}
