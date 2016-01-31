package pl.mobilewarsaw.meetupchef.config

import android.app.Application
import android.content.Intent
import pl.mobilewarsaw.meetupchef.config.injekt.MeetupChefInjektMain
import pl.mobilewarsaw.meetupchef.database.DatabaseEnvironment
import pl.mobilewarsaw.meetupchef.service.MeetupSynchronizer


class ChefApplication : Application() {

    private val databaseEnvironment = DatabaseEnvironment()

    override fun onCreate() {
        initInjektConfig()
        databaseEnvironment.prepare(this)
        startService(Intent(this, MeetupSynchronizer::class.java))
    }

    private fun initInjektConfig() {
        MeetupChefInjektMain.init(this)
    }

}