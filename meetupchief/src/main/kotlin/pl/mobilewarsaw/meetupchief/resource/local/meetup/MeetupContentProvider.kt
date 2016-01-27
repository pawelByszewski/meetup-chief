package pl.mobilewarsaw.meetupchief.resource.local.meetup

import android.content.*
import android.database.Cursor
import android.net.Uri
import pl.mobilewarsaw.meetupchief.config.injekt.MeetupChiefInjektMain
import pl.mobilewarsaw.meetupchief.database.Database
import pl.mobilewarsaw.meetupchief.database.EventTable
import uy.kohesive.injekt.injectLazy


class MeetupContentProvider : ContentProvider() {

    companion object {
        val AUTHORITY = "pl.mobilewarsaw.meetupchief";
        val DIR_PATH = 1
        val ITEM_PATH = 2
        val URI_NOT_MATCH = -1
    }

    val databse: Database by injectLazy()

    val contentProviders = arrayListOf<PartialContentProvider>()

    override fun onCreate(): Boolean {
        MeetupChiefInjektMain.init(context)
        contentProviders.add(MeetupGroupContentProvider())
        return true
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        val partialContentProvider = pickPartialContentProvider(uri!!)
                ?: throw UnsupportedOperationException()

        val uri = partialContentProvider.insert(uri, values)
        context.contentResolver.notifyChange(partialContentProvider.uriToNotify, null);
        return uri
    }

    private fun pickPartialContentProvider(uri: Uri)
            = contentProviders.firstOrNull { it.canHandle(uri) }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val partialContentProvider = pickPartialContentProvider(uri!!)
                ?: throw UnsupportedOperationException()

       return partialContentProvider.query(uri)

    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri?): String?
            = pickPartialContentProvider(uri!!)?.getType(uri)

}