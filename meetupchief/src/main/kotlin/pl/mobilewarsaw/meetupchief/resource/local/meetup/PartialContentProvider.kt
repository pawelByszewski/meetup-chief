package pl.mobilewarsaw.meetupchief.resource.local.meetup

import android.content.ContentValues
import android.net.Uri


interface PartialContentProvider {
    val uriToNotify: Uri?

    fun canHandle(uri: Uri): Boolean

    fun insert(uri: Uri?, values: ContentValues?): Uri?

    fun getType(uri: Uri?): String?
}