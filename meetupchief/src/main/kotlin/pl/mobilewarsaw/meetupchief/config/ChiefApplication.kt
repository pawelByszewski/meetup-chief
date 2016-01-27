package pl.mobilewarsaw.meetupchief.config

import android.app.Application
import android.content.Intent
import pl.mobilewarsaw.meetupchief.config.injekt.MeetupChiefInjektMain
import pl.mobilewarsaw.meetupchief.database.Database
import pl.mobilewarsaw.meetupchief.service.events.MeetupSynchronizer


class ChiefApplication: Application() {

    override fun onCreate() {
        initInjektConfig()
        deleteDatabase(Database.DATABASE_NAME);
        startService(Intent(this, MeetupSynchronizer::class.java))
    }

    private fun initInjektConfig() {
        MeetupChiefInjektMain.init(this)
    }
}