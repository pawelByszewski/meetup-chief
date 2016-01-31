package pl.mobilewarsaw.meetupchief.ui.events

import android.content.Intent
import android.os.Bundle

data class MeetuGroupInitData(val urlName: String,
                              val name: String,
                              val photoUrl: String?) {

    //TODO use Parcelable
    fun saveIn(outState: Bundle) {
        outState.putString(GROUP_URL_NAME_KEY, urlName)
        outState.putString(GROUP_NAME_KEY, name)
        outState.putString(GROUP_PHOTO, photoUrl)
    }

    companion object {
        fun createFrom(intent: Intent)
            = MeetuGroupInitData(urlName = intent.getStringExtra(GROUP_URL_NAME_KEY),
                                 name = intent.getStringExtra(GROUP_NAME_KEY),
                                 photoUrl = intent.getStringExtra(GROUP_PHOTO))

        fun createFrom(savedState: Bundle)
            = MeetuGroupInitData(urlName = savedState.getString(GROUP_URL_NAME_KEY),
                                 name = savedState.getString(GROUP_NAME_KEY),
                                 photoUrl = savedState.getString(GROUP_PHOTO))
    }
}
