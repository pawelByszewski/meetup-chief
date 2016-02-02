package pl.mobilewarsaw.meetupchef.ui.groups.listingadapter

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.otto.Bus
import com.squareup.picasso.Picasso
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupGroup
import pl.mobilewarsaw.meetupchef.ui.groups.listingadapter.BlankViewHolder
import pl.mobilewarsaw.meetupchef.ui.groups.bus.GroupClicked
import pl.mobilewarsaw.meetupchef.ui.recycleview.BASIC_VIEW_TYPE
import pl.mobilewarsaw.meetupchef.ui.recycleview.CursorRecyclerViewAdapter
import pl.touk.basil.clear
import uy.kohesive.injekt.injectValue
import kotlin.properties.Delegates


class MeetupGroupsCursorAdapter(cursor: Cursor? = null)
        : CursorRecyclerViewAdapter<RecyclerView.ViewHolder>(cursor) {

    private val picasso: Picasso by injectValue()
    private val bus: Bus by injectValue()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
        = when (viewType) {
            BASIC_VIEW_TYPE -> createEventViewHolder(parent)
            else -> createLastViewHolder(parent)
        }

    private fun createEventViewHolder(parent: ViewGroup): GroupViewHolder {
        val itemView = inflateView(R.layout.meetup_group_item, parent)
        val viewHolder = GroupViewHolder(itemView)
        viewHolder.container.setOnClickListener { clickedView: View ->
            val clickedViewHolder = clickedView.tag as GroupViewHolder
            bus.post(GroupClicked(clickedViewHolder.meetupGroup))
        }
        return viewHolder
    }

    private fun inflateView(meetup_group_item: Int, parent: ViewGroup): View {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(meetup_group_item, parent, false)
        return itemView
    }

    private fun createLastViewHolder(parent: ViewGroup): BlankViewHolder {
        val lastView = inflateView(R.layout.meetup_group_after_last_item, parent)
        return BlankViewHolder(lastView)
    }

    override fun onBindViewHolder(eventViewHolder: RecyclerView.ViewHolder, cursor: Cursor) {
        if (eventViewHolder is GroupViewHolder) {
            val meetupGroup = MeetupGroup.fromCursor(cursor)
            eventViewHolder.setup(meetupGroup)
            showImage(eventViewHolder, meetupGroup)
        } else {
            throw IllegalArgumentException("Must provide custom ViewHolder")
        }
    }

    private fun showImage(viewHolder: GroupViewHolder, meetupGroup: MeetupGroup) {
        viewHolder.image.clear()
        if (meetupGroup.photoUrl.isNullOrBlank()) {
            showPlaceHolder(viewHolder)
        } else {
            showImage(viewHolder, meetupGroup.photoUrl as String)
        }
    }

    private fun showPlaceHolder(viewHolder: GroupViewHolder) {
        picasso.load(R.drawable.card_image_empty_view)
                .fit()
                .centerCrop()
                .into(viewHolder.image)
    }

    private fun showImage(viewHolder: GroupViewHolder, pictureUrl: String) {
        picasso.load(pictureUrl)
                .fit()
                .centerCrop()
                .into(viewHolder.image)
    }
}