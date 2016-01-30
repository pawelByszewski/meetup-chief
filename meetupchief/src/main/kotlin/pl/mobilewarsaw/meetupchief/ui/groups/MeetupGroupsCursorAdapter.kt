package pl.mobilewarsaw.meetupchief.ui.groups

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.otto.Bus
import com.squareup.picasso.Picasso
import pl.mobilewarsaw.meetupchief.R
import pl.mobilewarsaw.meetupchief.resource.local.meetup.model.MeetupGroup
import pl.mobilewarsaw.meetupchief.ui.groups.bus.GroupClicked
import pl.mobilewarsaw.meetupchief.ui.recycleview.CursorRecyclerViewAdapter
import uy.kohesive.injekt.injectValue
import kotlin.properties.Delegates


class MeetupGroupsCursorAdapter(cursor: Cursor? = null)
        : CursorRecyclerViewAdapter<MeetupGroupsCursorAdapter.ViewHolder>(cursor) {

    private val picasso: Picasso by injectValue()
    private val bus: Bus by injectValue()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var meetupGroup: MeetupGroup by Delegates.notNull()

        var groupName: TextView
        var groupCategory: TextView
        var members: TextView
        var image: ImageView
        var container: View

        init {
            //TODO use kotlin power(kotterknife extension)
            groupName = view.findViewById(R.id.group_name) as TextView
            groupCategory = view.findViewById(R.id.group_category) as TextView
            members = view.findViewById(R.id.members) as TextView
            image = view.findViewById(R.id.group_image) as ImageView
            container = view.findViewById(R.id.card_view)
            container.tag = this
        }

        fun setup(meetupGroup: MeetupGroup) {
            this.meetupGroup = meetupGroup
            groupName.text = meetupGroup.name
            groupCategory.text = meetupGroup.category
            members.text = "${meetupGroup.members}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                                     .inflate(R.layout.meetup_group_item, parent, false)
        val viewHolder = ViewHolder(itemView)
        viewHolder.container.setOnClickListener { clickedView: View ->
            val clickedViewHolder = clickedView.tag as ViewHolder
            bus.post(GroupClicked(clickedViewHolder.meetupGroup))
        }
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, cursor: Cursor) {
        val meetupGroup = MeetupGroup.fromCursor(cursor)
        viewHolder.setup(meetupGroup)
        showImage(viewHolder, meetupGroup)
    }

    private fun showImage(viewHolder: ViewHolder, meetupGroup: MeetupGroup) {
        viewHolder.image.clear()
        if (meetupGroup.photoUrl.isNullOrBlank()) {
            showPlaceHolder(viewHolder)
        } else {
            showImage(viewHolder, meetupGroup.photoUrl as String)
        }
    }

    private fun showPlaceHolder(viewHolder: ViewHolder) {
        picasso.load(R.drawable.card_image_empty_view)
                .fit()
                .centerCrop()
                .into(viewHolder.image)
    }

    private fun showImage(viewHolder: ViewHolder, pictureUrl: String) {
        picasso.load(pictureUrl)
                .fit()
                .centerCrop()
                .into(viewHolder.image)
    }
}

//TODO to basil
fun ImageView.clear() = setImageResource(0)