package pl.mobilewarsaw.meetupchief.resource.local.meetup

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import pl.mobilewarsaw.meetupchief.database.Database
import pl.mobilewarsaw.meetupchief.database.MeetupGroupTable
import uy.kohesive.injekt.injectLazy
import uy.kohesive.injekt.injectValue


class MeetupGroupContentProvider : ContentProvider(), PartialContentProvider {

    companion object {
        val PATH = "groups"
        val CONTENT_URI = Uri.parse("content://${MeetupContentProvider.AUTHORITY}/$PATH")
    }

    override val uriToNotify: Uri?
        get() = throw UnsupportedOperationException()

    val databse: Database by injectLazy()

    val uriMatcher: UriMatcher
        get() {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(MeetupContentProvider.AUTHORITY,
                                "groups", MeetupContentProvider.DIR_PATH)
            uriMatcher.addURI(MeetupContentProvider.AUTHORITY, "$PATH/#",
                                MeetupContentProvider.ITEM_PATH)
            return uriMatcher
        }

    //TODO to basil
    override fun canHandle(uri: Uri) = uriMatcher.match(uri) != MeetupContentProvider.URI_NOT_MATCH

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        val id = databse.writableDatabase.insert(MeetupGroupTable.TABLE, null, values)
        return Uri.parse("content://${MeetupContentProvider.AUTHORITY}/$PATH/$id")
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        throw UnsupportedOperationException()
    }

    override fun onCreate(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri?): String? {
        return when (uriMatcher.match(uri)) {
            MeetupContentProvider.DIR_PATH -> "${ContentResolver.CURSOR_DIR_BASE_TYPE}/vnd.com.mobilewarsaw.meetupchief.provider.$PATH"
            MeetupContentProvider.ITEM_PATH -> "${ContentResolver.CURSOR_ITEM_BASE_TYPE}/vnd.com.mobilewarsaw.meetupchief.provider.$PATH"
            else -> null
        }
    }
}