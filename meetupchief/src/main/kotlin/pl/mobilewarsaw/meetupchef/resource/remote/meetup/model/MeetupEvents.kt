package pl.mobilewarsaw.meetupchef.resource.meetup

import com.google.gson.annotations.SerializedName

data class MeetupEvents(@SerializedName("results") val events: List<MeetupEvent>)


data class MeetupEvent(val name: String,
                       val id: String,
                       val status: String,
                       val description: String,
                       val venue: Venue?,
                       val group: Group,
                       @SerializedName("yes_rsvp_count") val attends: Int)

data class Venue(val name: String)

data class Group(val urlname: String)

