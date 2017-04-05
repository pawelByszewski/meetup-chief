package pl.mobilewarsaw.meetupchef.experimental

import android.os.Handler
import android.os.Looper
import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.ContinuationInterceptor


private class UiThreadContinuation<in T>(val cont: Continuation<T>) : Continuation<T> by cont {

    private val looper: Looper = Looper.getMainLooper()

    override fun resume(value: T) {
        Handler(looper).post { cont.resume(value) }
    }

    override fun resumeWithException(exception: Throwable) {
        Handler(looper).post { cont.resumeWithException(exception) }
    }
}

object Android : AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
            UiThreadContinuation(continuation)
}