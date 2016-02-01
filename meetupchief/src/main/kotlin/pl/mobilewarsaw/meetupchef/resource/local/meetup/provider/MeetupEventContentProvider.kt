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


class MeetupEventContentProvider : ContentProvider(), PartialContentProvider {

    companion object {
        val PATH = "events"
        val CONTENT_URI = createContentUri()

        private fun createContentUri(eventId: Long? = null)
                = Uri.parse("content://${MeetupContentProvider.AUTHORITY}/$PATH/${eventId ?: ""}")
    }

    val databse: Database by injectLazy()

    override val uriMatcher: UriMatcher
        get() {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(MeetupContentProvider.AUTHORITY,
                                PATH, MeetupContentProvider.DIR_PATH)
            uriMatcher.addURI(MeetupContentProvider.AUTHORITY, "$PATH/#",
                                MeetupContentProvider.ITEM_PATH)
            return uriMatcher
        }

    override val uriToNotify: Uri
        get() = CONTENT_URI

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        val id = databse.writableDatabase.insertWithOnConflict(EventTable.TABLE, null,
                values, SQLiteDatabase.CONFLICT_REPLACE)
        return createContentUri(id)
    }

    override fun query(uri: Uri?, projection: Array<out String>?,
                       selection: String?, selectionArgs: Array<out String>?,
                       sortOrder: String?): Cursor?
        = databse.writableDatabase.query(EventTable.TABLE, projection, selection, selectionArgs, null, null, null)

    fun delete(uri: Uri?): Int {
        val groupUrlName = uri?.getQueryParameter(EventTable.GROUP_URL_NAME)
        var where: String? = null
        var args: Array<String>? = null
        if (groupUrlName  != null) {
            where = EventTable.GROUP_URL_NAME + "=?"
            args = arrayOf(groupUrlName)
        }
        return  delete(uri, where, args)
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        val groupUrlName = uri?.getQueryParameter(EventTable.GROUP_URL_NAME)
        var where: String? = null
        var args: Array<String>? = null
        if (groupUrlName  != null) {
            where = EventTable.GROUP_URL_NAME + "=?"
            args = arrayOf(groupUrlName)
        }
        return databse.writableDatabase.delete(EventTable.TABLE, where, args)
    }

    //TODO use kotlin
    override fun getType(uri: Uri?): String? {
        return when (uriMatcher.match(uri)) {
            MeetupContentProvider.DIR_PATH -> "${ContentResolver.CURSOR_DIR_BASE_TYPE}/vnd.com.mobilewarsaw.meetupchef.provider.$PATH"
            MeetupContentProvider.ITEM_PATH -> "${ContentResolver.CURSOR_ITEM_BASE_TYPE}/vnd.com.mobilewarsaw.meetupchef.provider.$PATH"
            else -> null
        }
    }

    override fun onCreate(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }
}