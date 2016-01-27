package pl.mobilewarsaw.meetupchief.resource.remote.meetup.model

import com.google.gson.annotations.SerializedName


data class Meetup(val id: String,
                  val name: String,
                  val members: Int,
                  @SerializedName("group_photo") val photo: MeetupPhoto) {
}

data class MeetupPhoto(@SerializedName("photo_link") val url: String)