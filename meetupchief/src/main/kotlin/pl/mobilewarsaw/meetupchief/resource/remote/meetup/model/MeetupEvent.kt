package pl.mobilewarsaw.meetupchief.resource.remote.meetup.model

import com.google.gson.annotations.SerializedName

data class MeetupEvent(val name: String,
                       val id: String,
                       @SerializedName("yes_rsvp_count") val yesRsvpCount: Int)


