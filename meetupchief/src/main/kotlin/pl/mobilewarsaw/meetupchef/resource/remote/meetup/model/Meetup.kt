package pl.mobilewarsaw.meetupchef.resource.remote.meetup.model

import com.google.gson.annotations.SerializedName


data class Meetup(val id: String,
                  val name: String,
                  val members: Int,
                  @SerializedName("urlname") val urlName: String,
                  @SerializedName("group_photo") val photo: MeetupPhoto,
                  val category: GroupCategory) {
}

data class MeetupPhoto(@SerializedName("photo_link") val url: String)

data class GroupCategory(val name: String)