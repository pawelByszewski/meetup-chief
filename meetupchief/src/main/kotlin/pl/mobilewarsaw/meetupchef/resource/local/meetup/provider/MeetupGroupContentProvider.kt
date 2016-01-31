package pl.mobilewarsaw.meetupchef.resource.local.meetup.provider

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import pl.mobilewarsaw.meetupchef.database.Database
import pl.mobilewarsaw.meetupchef.database.MeetupGroupTable
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupContentProvider
import uy.kohesive.injekt.injectLazy


class MeetupGroupContentProvider : ContentProvider(), PartialContentProvider {

    companion object {
        val PATH = "groups"
        val CONTENT_URI = createContentUri()

        private fun createContentUri(groupId: Long? = null)
            = Uri.parse("content://${MeetupContentProvider.AUTHORITY}/$PATH/${groupId ?: ""}")
    }

    private val databse: Database by injectLazy()

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
        val id = databse.writableDatabase.insertWithOnConflict(MeetupGroupTable.TABLE, null,
                values, SQLiteDatabase.CONFLICT_REPLACE)
        return createContentUri(id)
    }

    override fun query(uri: Uri?, projection: Array<out String>?,
                       selection: String?, selectionArgs: Array<out String>?,
                       sortOrder: String?): Cursor?
        = databse.writableDatabase.query(MeetupGroupTable.TABLE, projection, selection,
                                            selectionArgs, null, null, sortOrder)

    override fun delete(uri: Uri?): Int
        =  delete(uri, null, null)

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int
        = databse.writableDatabase.delete(MeetupGroupTable.TABLE, selection, selectionArgs)


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