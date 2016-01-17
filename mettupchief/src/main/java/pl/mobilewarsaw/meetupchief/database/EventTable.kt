package pl.mobilewarsaw.meetupchief.database


object EventTable {

    val TABLE = "meetup_event"
    val ID = "id"
    val EVENT_ID = "event_id"
    val NAME = "name"
    val ATTENDS = "attends"


    val CREATE_STATEMENT: String = "create table $TABLE ($ID integer primary key autoincrement, $EVENT_ID text not null, $NAME text not null, $ATTENDS integer not null);"

}