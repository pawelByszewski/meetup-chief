package pl.mobilewarsaw.meetupchief.database

import android.content.ContentValues


object EventTable {

    val TABLE = "meetup_event"
    val ID = "id"
    val EVENT_ID = "event_id"
    val NAME = "name"
    val ATTENDS = "attends"


    val CREATE_STATEMENT: String = "create table $TABLE ($ID integer primary key autoincrement, $EVENT_ID text not null, $NAME text not null, $ATTENDS integer not null);"

    fun createInsertStatement(eventId: String, name: String, attends: Int)
            = "INSERT INTO TABLE $TABLE ($EVENT_ID, $NAME, $ATTENDS) VALUES ($eventId, $name, $attends);"

    fun createInsertStatement(values: ContentValues)
        = createInsertStatement(eventId = values.getAsString(EVENT_ID),
                                    name = values.getAsString(NAME),
                                    attends = values.getAsInteger(ATTENDS))


}