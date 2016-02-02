package pl.mobilewarsaw.meetupchef.resource.remote.meetup.model

import com.google.gson.annotations.SerializedName


data class Participant(private val member: Member,
                       private val rsvp: Rsvp)

data class Member(val name: String,
                  val id: Int,
                  @SerializedName("thumb") val photo: Photo)

data class Photo(val url: String)

data class Rsvp(val response: String,
                @SerializedName("guests") val guestCount: Int)