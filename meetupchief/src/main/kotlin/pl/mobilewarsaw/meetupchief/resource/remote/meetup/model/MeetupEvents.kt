package pl.mobilewarsaw.meetupchief.resource.meetup

import com.google.gson.annotations.SerializedName

data class MeetupEvents(@SerializedName("results") val events: List<MeetupEvent>)

data class MeetupEvent(val name: String,
                       val id: String,
                       @SerializedName("yes_rsvp_count") val yesRsvpCount: Int)

