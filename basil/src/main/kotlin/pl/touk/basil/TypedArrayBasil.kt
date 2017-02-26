package pl.touk.basil

import android.content.res.TypedArray

inline fun TypedArray.recycleAfter(action: TypedArray.() -> Unit) {
    try {
        this.action()
    } finally {
        recycle()
    }
}
