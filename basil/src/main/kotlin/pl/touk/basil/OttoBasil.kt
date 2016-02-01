package pl.touk.basil

import android.os.Handler
import android.os.Looper
import com.squareup.otto.Bus

inline fun Bus.postFromBackThread(event: Any) {
    Handler(Looper.getMainLooper()).post {
        this@postFromBackThread.post(event)
    }
}


