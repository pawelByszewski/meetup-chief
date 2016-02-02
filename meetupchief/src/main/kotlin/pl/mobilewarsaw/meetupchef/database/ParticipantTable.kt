package pl.mobilewarsaw.meetupchef.database


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


}