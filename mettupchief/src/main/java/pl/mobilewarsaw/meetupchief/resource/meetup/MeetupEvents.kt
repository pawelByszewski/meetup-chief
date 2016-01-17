package pl.mobilewarsaw.meetupchief.resource.meetup

import com.google.gson.annotations.SerializedName
import pl.mobilewarsaw.meetupchief.resource.meetup.model.MeetupEvent

data class MeetupEvents(@SerializedName("results") val events: List<MeetupEvent>)

