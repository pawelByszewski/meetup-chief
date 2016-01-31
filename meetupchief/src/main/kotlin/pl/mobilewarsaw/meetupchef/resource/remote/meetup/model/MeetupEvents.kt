package pl.mobilewarsaw.meetupchef.resource.meetup

import com.google.gson.annotations.SerializedName

data class MeetupEvents(@SerializedName("results") val events: List<MeetupEvent>)

//    https://api.meetup.com/Mobile-Warsaw/events?&sign=true&photo-host=public&desc=true&only=id,status,name,yes_rsvp_count,venue.name,description

data class MeetupEvent(val name: String,
                       val id: String,
                       val status: String,
                       val description: String,
                       val venue: Venue,
                       val group: Group,
                       @SerializedName("yes_rsvp_count") val attends: Int)

data class Venue(val name: String)
data class Group(val urlname: String)

