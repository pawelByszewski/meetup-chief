package pl.touk.android.basil

import android.app.Activity
import android.content.ContentResolver
import android.content.res.TypedArray
import android.database.Cursor
import android.net.Uri
import android.support.v4.app.Fragment
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


public fun <V> Activity.bindSystemService(serviceName: String)
        : ReadOnlyProperty<Activity, V> = fetchSystemService(serviceName, Activity::getSystemService)

public fun <V> View.bindSystemService(serviceName: String)
        : ReadOnlyProperty<View, V> = fetchSystemService(serviceName, View::getSystemService)

public fun <V> Fragment.bindSystemService(serviceName: String)
        : ReadOnlyProperty<Fragment, V> = fetchSystemService(serviceName, Fragment::serviceBinder)

private fun Fragment.serviceBinder(serviceName: String): Any
        = activity.getSystemService(serviceName)

private fun View.getSystemService(serviceName: String): Any
        = context.getSystemService(serviceName)


@Suppress("UNCHECKED_CAST")
private fun <T, V> fetchSystemService(serviceName: String, serviceBinder: T.(String) -> Any)
            = Lazy { t: T, property: KProperty<*> ->
                t.serviceBinder(serviceName) as V? ?: systemServiceNotFound(serviceName, property)
            }

private fun systemServiceNotFound(serviceName: String, property: KProperty<*>): Nothing
        = throw IllegalStateException("System service $serviceName for '${property.name}' not found.")

private class Lazy<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {

    private object EMPTY
    private var value: Any? = EMPTY

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }
}






inline fun TypedArray.recycleAfter(operation: TypedArray.() -> Unit) {
    try {
        operation()
    } finally {
        recycle()
    }
}


inline fun ContentResolver.query(uri: Uri): Cursor
        = query(uri, null, null, null, null)


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

