package pl.mobilewarsaw.meetupchef.resource.local.meetup

import android.content.*
import android.database.Cursor
import android.net.Uri
import pl.mobilewarsaw.meetupchef.Settings
import pl.mobilewarsaw.meetupchef.config.injekt.MeetupChefInjektMain
import pl.mobilewarsaw.meetupchef.database.Database
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.SpecializedContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupGroupContentProvider
import uy.kohesive.injekt.injectLazy
import java.util.*


class MeetupContentProvider : ContentProvider() {

    companion object {
        val AUTHORITY = Settings.contentProvider.authority
        val DIR_PATH = 1
        val ITEM_PATH = 2
        val URI_NOT_MATCH = -1
    }

    val contentProviders = arrayListOf<SpecializedContentProvider>()

    override fun onCreate(): Boolean {
        MeetupChefInjektMain.init(context)
        contentProviders.add(MeetupGroupContentProvider())
        contentProviders.add(MeetupEventContentProvider())
        return true
    }

    override fun applyBatch(operations: ArrayList<ContentProviderOperation>)
            : Array<out ContentProviderResult>? {
        val results = super.applyBatch(operations)
        notifyOperationsUris(operations)
        return results
    }

    private fun notifyOperationsUris(operations: ArrayList<ContentProviderOperation>) {
        operations.map { pickPartialContentProvider(it.uri)?.uriToNotify }
                .distinct()
                .filterNot { it == null }
                .forEach { context.contentResolver.notifyChange(it, null) }
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        val partialContentProvider = pickPartialContentProvider(uri!!)
                ?: throw UnsupportedOperationException()

        return partialContentProvider.insert(uri, values)
    }

    private fun pickPartialContentProvider(uri: Uri)
            = contentProviders.firstOrNull { it.canHandle(uri) }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?,
                       selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val partialContentProvider = pickPartialContentProvider(uri!!)
                ?: throw UnsupportedOperationException()

       return partialContentProvider.query(uri, projection, selection, selectionArgs, sortOrder)
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?,
                        selectionArgs: Array<out String>?): Int {
        val partialContentProvider = pickPartialContentProvider(uri!!)
                ?: throw UnsupportedOperationException()

        return partialContentProvider.update(uri, values, selection, selectionArgs)
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        val partialContentProvider = pickPartialContentProvider(uri!!)
                ?: throw UnsupportedOperationException()

        return partialContentProvider.delete(uri, selection, selectionArgs)
    }

    override fun getType(uri: Uri?): String?
            = pickPartialContentProvider(uri!!)?.getType(uri)

}