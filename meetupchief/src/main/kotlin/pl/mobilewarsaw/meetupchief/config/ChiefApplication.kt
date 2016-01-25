package pl.mobilewarsaw.meetupchief.config

import android.app.Application
import pl.mobilewarsaw.meetupchief.config.injekt.MeetupChiefInjektMain


class ChiefApplication: Application() {

    override fun onCreate() {
        initInjektConfig()
    }

    private fun initInjektConfig() {
        MeetupChiefInjektMain.init()
    }
}