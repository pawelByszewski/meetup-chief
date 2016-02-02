package pl.mobilewarsaw.meetupchef.resource.local.meetup.provider

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import pl.mobilewarsaw.meetupchef.database.ParticipantTable
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupContentProvider


class MeetupParticipantContentProvider : SpecializedContentProvider() {

    companion object {
        val PATH = "participants"
        val CONTENT_URI = createContentUri()

        private fun createContentUri(eventId: Long? = null)
                = Uri.parse("content://${MeetupContentProvider.AUTHORITY}/$PATH/${eventId ?: ""}")
    }

    override val path: String
        get() = PATH

    override val uriToNotify: Uri
        get() = CONTENT_URI

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        val id = databse.writableDatabase.insertWithOnConflict(ParticipantTable.TABLE_NAME, null,
                values, SQLiteDatabase.CONFLICT_REPLACE)
        return createContentUri(id)
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        throw UnsupportedOperationException()
    }

}