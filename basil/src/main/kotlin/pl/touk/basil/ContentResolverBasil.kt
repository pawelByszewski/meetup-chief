package pl.touk.basil

import android.content.ContentResolver
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler


inline fun ContentResolver.registerUriObserver(uri: Uri, noinline  action: () -> Unit) {
    registerContentObserver(uri, true,
            object : ContentObserver(Handler()) {
                override fun onChange(selfChange: Boolean) = action()
            })
}

inline fun ContentResolver.query(uri: Uri): Cursor
        = query(uri, null, null, null, null)