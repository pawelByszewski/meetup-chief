package pl.mobilewarsaw.meetupchief.config

import android.app.Application
import android.content.Context
import android.content.Intent
import pl.mobilewarsaw.meetupchief.config.injekt.MeetupChiefInjektMain
import pl.mobilewarsaw.meetupchief.database.DatabaseEnvironment
import pl.mobilewarsaw.meetupchief.service.events.MeetupSynchronizer


class ChiefApplication: Application() {

    private val databaseEnvironment = DatabaseEnvironment()

    override fun onCreate() {
        initInjektConfig()
        databaseEnvironment.prepare(this)
        startService(Intent(this, MeetupSynchronizer::class.java))
    }

    private fun initInjektConfig() {
        MeetupChiefInjektMain.init(this)
    }

}