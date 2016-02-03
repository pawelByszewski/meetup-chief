package pl.mobilewarsaw.meetupchef.resource.remote.meetup.model

import com.google.gson.annotations.SerializedName



data class Participant(private val member: Member,
                       private val rsvp: Rsvp) {
    val name: String
        get() = member.name

    val id: Int
        get() = member.id

    val photoUrl: String
        get() = member?.photo?.url ?: ""
}

data class Member(val name: String,
                  val id: Int,
                  val photo: Photo?)

data class Photo(@SerializedName("thumb") val url: String)

data class Rsvp(val response: String,
                @SerializedName("guests") val guestCount: Int)