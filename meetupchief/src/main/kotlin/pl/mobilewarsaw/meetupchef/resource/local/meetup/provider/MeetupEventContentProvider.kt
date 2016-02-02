package pl.mobilewarsaw.meetupchef.resource.local.meetup.provider

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import pl.mobilewarsaw.meetupchef.database.Database
import pl.mobilewarsaw.meetupchef.database.EventTable
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupContentProvider
import uy.kohesive.injekt.injectLazy


class MeetupEventContentProvider : SpecializedContentProvider() {

    companion object {
        val PATH = "events"
        val CONTENT_URI = createContentUri()

        private fun createContentUri(eventId: Long? = null)
                = Uri.parse("content://${MeetupContentProvider.AUTHORITY}/$PATH/${eventId ?: ""}")
    }

    override val path: String
        get() = PATH

    override val uriToNotify: Uri
        get() = CONTENT_URI

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        val id = databse.writableDatabase.insertWithOnConflict(EventTable.TABLE_NAME, null,
                values, SQLiteDatabase.CONFLICT_REPLACE)
        return createContentUri(id)
    }

    override fun query(uri: Uri?, projection: Array<out String>?,
                       selection: String?, selectionArgs: Array<out String>?,
                       sortOrder: String?): Cursor?
        = databse.writableDatabase.query(EventTable.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        val groupUrlName = uri?.getQueryParameter(EventTable.GROUP_URL_NAME)
        var where: String? = null
        var args: Array<String>? = null
        if (groupUrlName  != null) {
            where = EventTable.GROUP_URL_NAME + "=?"
            args = arrayOf(groupUrlName)
        }
        return databse.writableDatabase.delete(EventTable.TABLE_NAME, where, args)
    }
}