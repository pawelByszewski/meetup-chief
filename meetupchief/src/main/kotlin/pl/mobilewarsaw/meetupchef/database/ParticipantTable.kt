package pl.mobilewarsaw.meetupchef.database

import android.content.ContentProviderOperation
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupGroupContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupParticipantContentProvider
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.model.Meetup
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.model.Participant


object ParticipantTable: Table {

    override val TABLE_NAME = "Participants"
    val ID = "_id"
    val PARTICIPANT_ID = "participant_id"
    val EVENT_ID = "event_id"
    val NAME = "name"
    val PHOTO = "photo"


    //TODO use the Kotlin Luke
    override val CREATE_STATEMENT: String = "create table $TABLE_NAME " +
            "($ID integer primary key autoincrement, " +
            "$PARTICIPANT_ID text not null unique, " +
            "$NAME text not null, " +
            "$PHOTO text not null, " +
            "$EVENT_ID text not null," +
            " FOREIGN KEY ($EVENT_ID) REFERENCES ${EventTable.TABLE_NAME}(${EventTable.EVENT_ID}));";

    fun createInsertOperation(eventId: Int, participant: Participant): ContentProviderOperation {
        return ContentProviderOperation.newInsert(MeetupParticipantContentProvider.CONTENT_URI)
                .withValue(NAME, participant.name)
                .withValue(EVENT_ID, eventId)
                .withValue(PARTICIPANT_ID, participant.id)
                .withValue(PHOTO, participant.photoUrl ?: "")
                .build()
    }

    fun createDeleteForGroupOperations(eventId: Int): ContentProviderOperation {
        val deleteUri = MeetupParticipantContentProvider.CONTENT_URI
                .buildUpon()
                .appendQueryParameter(EVENT_ID, "$eventId")
                .build()
        return ContentProviderOperation.newDelete(deleteUri).build()
    }


}