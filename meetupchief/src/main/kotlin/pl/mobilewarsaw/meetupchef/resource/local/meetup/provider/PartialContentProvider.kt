package pl.mobilewarsaw.meetupchef.resource.local.meetup.provider

import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import pl.mobilewarsaw.meetupchef.resource.local.meetup.MeetupContentProvider


interface PartialContentProvider {

    val uriToNotify: Uri
    val uriMatcher: UriMatcher

    fun canHandle(uri: Uri)
            = uriMatcher.match(uri) != MeetupContentProvider.URI_NOT_MATCH

    fun insert(uri: Uri?, values: ContentValues?): Uri?

    fun delete(uri: Uri?): Int

    fun query(uri: Uri?,
              projection: Array<out String>? = null,
              selection: String? = null,
              selectionArgs: Array<out String>? = null,
              sortOrder: String? = null): Cursor?

    fun getType(uri: Uri?): String?

}