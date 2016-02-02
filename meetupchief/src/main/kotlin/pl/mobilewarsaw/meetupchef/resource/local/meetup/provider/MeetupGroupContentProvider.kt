package pl.mobilewarsaw.meetupchef.resource.local.meetup.provider

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import pl.mobilewarsaw.meetupchef.database.Database
import pl.mobilewarsaw.meetupchef.database.GroupTable
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupContentProvider
import uy.kohesive.injekt.injectLazy


class MeetupGroupContentProvider : SpecializedContentProvider() {

    companion object {
        val PATH = "groups"
        val CONTENT_URI = createContentUri()

        private fun createContentUri(groupId: Long? = null)
            = Uri.parse("content://${MeetupContentProvider.AUTHORITY}/$PATH/${groupId ?: ""}")
    }

    override val path: String
        get() = PATH

    override val uriToNotify: Uri
        get() = CONTENT_URI

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {

        //TODO use the kotlin Luke
        var id =  databse.writableDatabase.insertWithOnConflict(GroupTable.TABLE_NAME, null,
                                values, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1L) {
            id = databse.writableDatabase.update(GroupTable.TABLE_NAME, values,
                    "${GroupTable.GROUP_ID}=?", arrayOf(values?.getAsString(GroupTable.GROUP_ID))).toLong()
        }

        return createContentUri(id)
    }

    override fun query(uri: Uri?, projection: Array<out String>?,
                       selection: String?, selectionArgs: Array<out String>?,
                       sortOrder: String?): Cursor?
        = databse.writableDatabase.query(GroupTable.TABLE_NAME, projection, selection,
                                            selectionArgs, null, null, sortOrder)

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int
        = databse.writableDatabase.delete(GroupTable.TABLE_NAME, selection, selectionArgs)

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int
        = databse.writableDatabase.update(GroupTable.TABLE_NAME, values, selection, selectionArgs)
}