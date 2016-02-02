package pl.mobilewarsaw.meetupchef.database

import android.content.ContentProviderOperation
import android.content.ContentValues
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupGroupContentProvider
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.model.Meetup


const val CACHED = 1
const val UNCACHED = 0

object GroupTable : Table {

    override val TABLE_NAME = "meetupGroups"
    val ID = "_id"
    val GROUP_ID = "group_id"
    val NAME = "name"
    val MEMBERS = "members"
    val PHOTO = "photo"
    val CATEGORY = "category"
    val URL_NAME = "url_name"
    val CACHE = "CACHE"
    val QUERY = "QUERY"


    //TODO use the Kotlin Luke
    override val CREATE_STATEMENT: String = "create table $TABLE_NAME " +
                                    "($ID integer primary key autoincrement, " +
                                    "$GROUP_ID text not null unique, " +
                                    "$NAME text not null, " +
                                    "$PHOTO text not null, " +
                                    "$CATEGORY text not null, " +
                                    "$QUERY text not null, " +
                                    "$MEMBERS integer not null, " +
                                    "$CACHE integer not null DEFAULT 0, " +
                                    "$URL_NAME text not null);"

    fun createInsertOperation(query: String, meetup: Meetup): ContentProviderOperation {
        return ContentProviderOperation.newInsert(MeetupGroupContentProvider.CONTENT_URI)
                .withValue(GroupTable.NAME, meetup.name)
                .withValue(GroupTable.GROUP_ID, meetup.id)
                .withValue(GroupTable.MEMBERS, meetup.members)
                .withValue(GroupTable.PHOTO, meetup.photo?.url ?: "")
                .withValue(GroupTable.CATEGORY, meetup.category?.name ?: "")
                .withValue(GroupTable.URL_NAME, meetup.urlName)
                .withValue(GroupTable.QUERY, query)
                .build()
    }

    fun createDeleteNotCachedOperation(): ContentProviderOperation
        = ContentProviderOperation.newDelete(MeetupGroupContentProvider.CONTENT_URI)
            .withSelection("$CACHE = ?", arrayOf("$UNCACHED"))
            .build()

}