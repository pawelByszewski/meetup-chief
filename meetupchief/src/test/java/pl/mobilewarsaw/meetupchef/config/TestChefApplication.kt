package pl.mobilewarsaw.meetupchef.config

import android.app.Application
import android.content.Intent
import pl.mobilewarsaw.meetupchef.config.injekt.MeetupGroupsActivityTestInjekt
import pl.mobilewarsaw.meetupchef.database.DatabaseEnvironment
import pl.mobilewarsaw.meetupchef.service.MeetupSynchronizer


class TestChefApplication : Application() {

    private val databaseEnvironment = DatabaseEnvironment()

    override fun onCreate() {
    }

    private fun initInjektConfig() {
    }
}