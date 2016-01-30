package pl.mobilewarsaw.meetupchief.database

import android.content.ContentProviderOperation
import android.content.ContentValues
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupGroupContentProvider
import pl.mobilewarsaw.meetupchief.resource.meetup.MeetupEvent
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.model.Meetup


object EventTable {

    val TABLE = "meetup_event"
    val ID = "_id"
    val EVENT_ID = "event_id"
    val STATUS = "status"
    val DESCRIPTION = "description"
    val NAME = "name"
    val ATTENDS = "yes_rsvp_count"
    val VENUE = "venue"
    val GROUP_URL_NAME = "groupUrlName"


    val CREATE_STATEMENT: String = "create table $TABLE ($ID integer primary key autoincrement, " +
                                                        "$EVENT_ID text not null unique, " +
                                                        "$NAME text not null, " +
                                                        "$DESCRIPTION text not null, " +
                                                        "$STATUS text not null, " +
                                                        "$VENUE text, " +
                                                        "$GROUP_URL_NAME text not null, " +
                                                        "$ATTENDS integer not null);"

//    fun createInsertStatement(eventId: String, name: String, attends: Int,
//                              description: String, status: String, venue: String,
//                              groupUrlName: String)
//            = "INSERT INTO TABLE $TABLE ($EVENT_ID, $NAME, $DESCRIPTION, $STATUS, $VENUE $ATTENDS, $GROUP_URL_NAME) VALUES " +
//                "($eventId, $name, $description, $status, $venue, $attends, $groupUrlName);"

//    fun createInsertStatement(values: ContentValues)
//            = EventTable.createInsertStatement(eventId = values.getAsString(EventTable.EVENT_ID),
//                                                    name = values.getAsString(EventTable.NAME),
//                                                    attends = values.getAsInteger(EventTable.ATTENDS),
//                                                    description = values.getAsString(EventTable.DESCRIPTION),
//                                                    status = values.getAsString(EventTable.STATUS),
//                                                    groupUrlName = values.getAsString(EventTable.GROUP_URL_NAME),
//                                                    venue = values.getAsString(EventTable.VENUE ))

    fun createInsertOperation(event: MeetupEvent): ContentProviderOperation {
        return ContentProviderOperation.newInsert(MeetupEventContentProvider.CONTENT_URI)
                .withValue(EventTable.NAME, event.name)
                .withValue(EventTable.EVENT_ID, event.id)
                .withValue(EventTable.ATTENDS, event.attends)
                .withValue(EventTable.DESCRIPTION, event.description)
                .withValue(EventTable.STATUS, event.status)
                .withValue(EventTable.VENUE, event.venue?.name)
                .withValue(EventTable.GROUP_URL_NAME, event.group.urlname)
                .build()
    }

    fun createDeleteForGroupOperations(groupUrlName: String): ContentProviderOperation {
        val deleteUri = MeetupEventContentProvider.CONTENT_URI
                .buildUpon()
                .appendQueryParameter(EventTable.GROUP_URL_NAME, groupUrlName)
                .build()
        return ContentProviderOperation.newDelete(deleteUri).build()
    }


}