package pl.touk.basil

import android.view.View

inline fun View.show(show: Boolean = true) {
    visibility = if (show) View.VISIBLE else View.GONE
}

inline fun View.hide() {
    show(false)
}
