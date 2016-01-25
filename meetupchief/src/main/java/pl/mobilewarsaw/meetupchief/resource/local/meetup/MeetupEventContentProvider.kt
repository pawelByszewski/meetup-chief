package pl.mobilewarsaw.meetupchief.resource.local.meetup

import android.content.*
import android.database.Cursor
import android.net.Uri
import android.util.Log
import pl.mobilewarsaw.meetupchief.database.Database
import pl.mobilewarsaw.meetupchief.database.EventTable


class MeetupEventContentProvider : ContentProvider() {

    companion object {
        val AUTHORITY = "pl.mobilewarsaw.meetupchief.events";
        val CONTENT_URI = Uri.parse("content://$AUTHORITY")
    }

    val DIR_PATH = 1
    val ITEM_PATH = 2

    lateinit var databse: Database

    val uriMatcher: UriMatcher
        get() {
            val urimatcher = UriMatcher(UriMatcher.NO_MATCH)
            urimatcher.addURI(AUTHORITY, "events", DIR_PATH)
            urimatcher.addURI(AUTHORITY, "events/#", ITEM_PATH)
            return uriMatcher
        }



    override fun onCreate(): Boolean {
        return true
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        Log.e("kabum", "insert")
        val id = databse.writableDatabase.insert(EventTable.TABLE, null, values)
        context.contentResolver.notifyChange(CONTENT_URI, null);
        return Uri.parse("content://$AUTHORITY/events/$id")
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
       return databse.writableDatabase.query(EventTable.TABLE, null, null, null, null, null, null)
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri?): String? {
       return when (uriMatcher.match(uri)) {
            DIR_PATH -> "${ContentResolver.CURSOR_DIR_BASE_TYPE}/vnd.com.mobilewarsaw.meetupchief.provider.events"
            ITEM_PATH -> "${ContentResolver.CURSOR_ITEM_BASE_TYPE}/vnd.com.mobilewarsaw.meetupchief.provider.events"
            else -> null
        }
    }
}