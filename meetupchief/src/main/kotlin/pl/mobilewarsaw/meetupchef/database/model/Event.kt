package pl.mobilewarsaw.meetupchef.database.model

import android.os.Parcelable
import io.requery.*

private const val STATUS_CLOSED = "past"

@Entity
interface Event : Parcelable, Persistable {

    @get:Key
    @get:Generated
    var id: Int

    @get:Column(nullable = false)
    val eventId: String

    @get:Column(nullable = false)
    val status: String

    @get:Column(nullable = false)
    val description: String

    @get:Column(nullable = false)
    val name: String

    @get:Column(nullable = true)
    val venue: String

    @get:Column(nullable = true)
    val groupUrlName: String

    @get:Column(nullable = false)
    val attends: Int

    @get:Column(nullable = true)
    @get:ManyToOne
    val groupqwe: MeetupGroup?
}

fun Event.isOpen() = status != STATUS_CLOSED