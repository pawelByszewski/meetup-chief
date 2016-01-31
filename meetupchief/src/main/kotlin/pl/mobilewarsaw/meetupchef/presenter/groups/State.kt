package pl.mobilewarsaw.meetupchef.presenter.groups

import android.os.Bundle

class State {

    private val RESTORE_QUERY = "restoreLastQuery"
    private var realQuery: String? = null

    val isDetermined: Boolean
        get() = !query.isNullOrBlank()

    var query: String
        get() = realQuery ?: ""
        set(value) {
            realQuery = value
        }

    fun save(bundle: Bundle)
            = bundle.putString(RESTORE_QUERY, query)


    fun setup(bundle: Bundle?) {
        realQuery = bundle?.getString(RESTORE_QUERY)
    }
}