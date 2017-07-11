package pl.mobilewarsaw.meetupchef.database.model

import android.os.Parcelable
import io.requery.*


@Entity
interface MeetupGroup : Parcelable, Persistable {

    @get:Key
    @get:Column(nullable = false)
    val groupId: String

    @get:Column(nullable = false)
    val name: String

    @get:Column(nullable = true)
    val photoUrl: String?

    @get:Column(nullable = false)
    val category: String

    @get:Column(nullable = false)
    val urlName: String

    @get:Column(nullable = false)
    val members: Int

    @get:OneToMany
    val events: Set<Event>
}