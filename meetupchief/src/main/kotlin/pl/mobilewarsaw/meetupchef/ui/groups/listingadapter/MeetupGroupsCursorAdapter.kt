package pl.mobilewarsaw.meetupchef.ui.groups.listingadapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.otto.Bus
import com.squareup.picasso.Picasso
import io.requery.query.Result
import io.requery.sql.ResultSetIterator
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.database.model.MeetupGroup
import pl.mobilewarsaw.meetupchef.ui.groups.bus.GroupClicked
import pl.mobilewarsaw.meetupchef.ui.recycleview.BASIC_VIEW_TYPE
import pl.touk.basil.clear
import uy.kohesive.injekt.injectValue


class MeetupGroupsCursorAdapter
        : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return groups!!.count()
    }

    private val picasso: Picasso by injectValue()
    private val bus: Bus by injectValue()
    private var groups: Result<MeetupGroup>? = null
    private var groupsResultSet: ResultSetIterator<MeetupGroup>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == BASIC_VIEW_TYPE) {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.meetup_group_item, parent, false)
            val viewHolder = GroupViewHolder(itemView)
            viewHolder.container.setOnClickListener { clickedView: View ->
                val clickedViewHolder = clickedView.tag as GroupViewHolder
                bus.post(GroupClicked(clickedViewHolder.meetupGroup))
            }
            return viewHolder
        } else {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.meetup_group_after_last_item, parent, false)
            val viewHolder = BlankViewHolder(itemView)
            return viewHolder
        }
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is GroupViewHolder) {
            val meetupGroup = groupsResultSet!![position]
            viewHolder.setup(meetupGroup)
            showImage(viewHolder, meetupGroup)
        } else {
            throw IllegalArgumentException("Must provide custom ViewHolder")
        }
    }

    private fun showImage(viewHolder: GroupViewHolder, meetupGroup: MeetupGroup) {
        viewHolder.image.clear()
        if (meetupGroup.photoUrl.isNullOrBlank()) {
            showPlaceHolder(viewHolder)
        } else {
            showImage(viewHolder, meetupGroup.photoUrl!!)
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

    fun updateGroups(groups: Result<MeetupGroup>) {
        this.groups = groups
        groupsResultSet = groups.iterator() as ResultSetIterator<MeetupGroup>
    }
}