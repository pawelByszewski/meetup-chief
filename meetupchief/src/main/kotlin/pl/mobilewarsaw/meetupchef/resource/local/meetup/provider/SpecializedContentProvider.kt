package pl.mobilewarsaw.meetupchef.resource.local.meetup.provider

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import pl.mobilewarsaw.meetupchef.database.Database
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupContentProvider
import uy.kohesive.injekt.injectLazy


abstract class SpecializedContentProvider : ContentProvider() {

    abstract protected val path: String
    abstract val uriToNotify: Uri

    protected val databse: Database by injectLazy()

    val uriMatcher: UriMatcher
        get() {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(MeetupContentProvider.AUTHORITY,
                    "$path", MeetupContentProvider.DIR_PATH)
            uriMatcher.addURI(MeetupContentProvider.AUTHORITY, "$path/#",
                    MeetupContentProvider.ITEM_PATH)
            return uriMatcher
        }

    fun canHandle(uri: Uri)
            = uriMatcher.match(uri) != MeetupContentProvider.URI_NOT_MATCH

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
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

    //TODO use kotlin
    override fun getType(uri: Uri?): String? {
        return when (uriMatcher.match(uri)) {
            MeetupContentProvider.DIR_PATH -> "${ContentResolver.CURSOR_DIR_BASE_TYPE}/vnd.com.mobilewarsaw.meetupchef.provider.$path"
            MeetupContentProvider.ITEM_PATH -> "${ContentResolver.CURSOR_ITEM_BASE_TYPE}/vnd.com.mobilewarsaw.meetupchef.provider.$path"
            else -> null
        }
    }
}