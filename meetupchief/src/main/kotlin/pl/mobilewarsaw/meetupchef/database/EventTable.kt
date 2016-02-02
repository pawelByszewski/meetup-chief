package pl.mobilewarsaw.meetupchef.database

import android.content.ContentProviderOperation
import android.content.ContentValues
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupGroupContentProvider
import pl.mobilewarsaw.meetupchef.resource.meetup.MeetupEvent
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.model.Meetup


object EventTable: Table {

    override val TABLE_NAME = "meetup_event"
    val ID = "_id"
    val EVENT_ID = "event_id"
    val STATUS = "status"
    val DESCRIPTION = "description"
    val NAME = "name"
    val ATTENDS = "yes_rsvp_count"
    val VENUE = "venue"
    val GROUP_URL_NAME = "groupUrlName"


    //TODO use the Kotlin Luke
    override val CREATE_STATEMENT: String = "create table $TABLE_NAME ($ID integer primary key autoincrement, " +
                                                        "$EVENT_ID text not null unique, " +
                                                        "$NAME text not null, " +
                                                        "$DESCRIPTION text not null, " +
                                                        "$STATUS text not null, " +
                                                        "$VENUE text, " +
                                                        "$GROUP_URL_NAME text not null, " +
                                                        "$ATTENDS integer not null);"

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