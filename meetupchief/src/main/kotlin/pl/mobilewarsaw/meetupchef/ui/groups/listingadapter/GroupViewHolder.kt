package pl.mobilewarsaw.meetupchef.ui.groups.listingadapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.database.model.MeetupGroup
import kotlin.properties.Delegates

class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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