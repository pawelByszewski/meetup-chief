package pl.mobilewarsaw.meetupchief.resource.local.meetup

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri


interface PartialContentProvider {
    val uriToNotify: Uri?

    fun canHandle(uri: Uri): Boolean

    fun insert(uri: Uri?, values: ContentValues?): Uri?

    fun delete(uri: Uri?): Int

    fun query(uri: Uri?,
              projection: Array<out String>? = null,
              selection: String? = null,
              selectionArgs: Array<out String>? = null,
              sortOrder: String? = null): Cursor?

    fun getType(uri: Uri?): String?
}