package pl.touk.basil

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


fun <V> Activity.bindSystemService(serviceName: String)
        : ReadOnlyProperty<Activity, V> = fetchSystemService(serviceName, Activity::getSystemService)

fun <V> Fragment.bindSystemService(serviceName: String)
        : ReadOnlyProperty<Fragment, V> = fetchSystemService(serviceName, Fragment::serviceBinder)

inline fun <V> bindSystemService(serviceName: String)
        : ReadOnlyProperty<View, V> = fetchSystemService(serviceName, View::getSystemService)

fun Fragment.serviceBinder(serviceName: String): Any
        = activity.getSystemService(serviceName)

fun View.getSystemService(serviceName: String): Any
        = context.getSystemService(serviceName)


@Suppress("UNCHECKED_CAST")
inline fun <T, V> fetchSystemService(serviceName: String, noinline  serviceBinder: T.(String) -> Any)
        = Lazy { t: T, property: KProperty<*> ->
    t.serviceBinder(serviceName) as V? ?: systemServiceNotFound(serviceName, property)
}

fun systemServiceNotFound(serviceName: String, property: KProperty<*>): Nothing
        = throw IllegalStateException("System service $serviceName for '${property.name}' not found.")

class Lazy<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {

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