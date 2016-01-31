package pl.mobilewarsaw.meetupchef.presenter.events

import android.content.Intent
import android.os.Bundle
import pl.mobilewarsaw.meetupchef.ui.events.GROUP_NAME_KEY
import pl.mobilewarsaw.meetupchef.ui.events.GROUP_PHOTO
import pl.mobilewarsaw.meetupchef.ui.events.GROUP_URL_NAME_KEY

class State {

    private var backingUrlName: String? = null
    private var backingName: kotlin.String? = null
    private var backingPhotoUrl: kotlin.String? = null

    val urlName: String
        get() = backingUrlName ?: throw IllegalStateException("Undetermined state.")

    val name: String
        get() = backingName ?: throw IllegalStateException("Undetermined state.")

    val photoUrl: String
        get() = backingPhotoUrl ?: throw IllegalStateException("Undetermined state.")


    //TODO use Parcelable
    fun saveIn(outState: Bundle) {
        outState.putString(GROUP_URL_NAME_KEY, backingUrlName)
        outState.putString(GROUP_NAME_KEY, backingName)
        outState.putString(GROUP_PHOTO, backingPhotoUrl)
    }

    fun setup(intent: Intent, bundle: Bundle?) {
        backingUrlName = intent.getStringExtra(GROUP_URL_NAME_KEY)
                    ?: bundle?.getString(GROUP_URL_NAME_KEY)
        backingName = intent.getStringExtra(GROUP_NAME_KEY)
                    ?: bundle?.getString(GROUP_NAME_KEY)
        backingPhotoUrl = intent.getStringExtra(GROUP_PHOTO)
                    ?: bundle?.getString(GROUP_PHOTO)
    }
}
