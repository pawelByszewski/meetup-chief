package pl.mobilewarsaw.meetupchief.database

import android.content.ContentProviderOperation
import android.content.ContentValues
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupContentProvider
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupGroupContentProvider
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.model.Meetup


object MeetupGroupTable {

    val TABLE = "meetupGroups"
    val ID = "_id"
    val GROUP_ID = "group_id"
    val NAME = "name"
    val MEMBERS = "members"
    val PHOTO = "photo"


    val CREATE_STATEMENT: String = "create table $TABLE " +
                                    "($ID integer primary key autoincrement, " +
                                    "$GROUP_ID text not null, " +
                                    "$NAME text not null, " +
                                    "$PHOTO text not null, " +
                                    "$MEMBERS integer not null);"

    fun createContentValues(meetup: Meetup): ContentProviderOperation {
        return ContentProviderOperation.newInsert(MeetupGroupContentProvider.CONTENT_URI)
                .withValue(MeetupGroupTable.NAME, meetup.name)
                .withValue(MeetupGroupTable.GROUP_ID, meetup.id)
                .withValue(MeetupGroupTable.MEMBERS, meetup.members)
                .withValue(MeetupGroupTable.PHOTO, meetup.photo?.url ?: "")
                .build()
    }

    fun createInsertStatement(groupId: String, name: String, members: Int, photoUrl: String)
            = "INSERT INTO TABLE $TABLE ($GROUP_ID, $NAME, $MEMBERS, $PHOTO) " +
                "VALUES ($groupId, $name, $members, $photoUrl);"

    fun createInsertStatement(values: ContentValues)
        = createInsertStatement(groupId = values.getAsString(GROUP_ID),
                                    name = values.getAsString(NAME),
                                    members = values.getAsInteger(MEMBERS),
                                    photoUrl = values.getAsString(PHOTO))


}