package pl.mobilewarsaw.meetupchef.config

import android.app.Application
import android.content.Intent
import com.facebook.stetho.Stetho
import pl.mobilewarsaw.meetupchef.config.injekt.MeetupChefInjektMain
import pl.mobilewarsaw.meetupchef.database.DatabaseEnvironment
import timber.log.Timber


class ChefApplication : Application() {

    private val databaseEnvironment = DatabaseEnvironment()

    override fun onCreate() {
        initInjektConfig()
        databaseEnvironment.prepare(this)
        Stetho.initializeWithDefaults(this)
        Timber.plant(Timber.DebugTree())
    }

    private fun initInjektConfig() {
        MeetupChefInjektMain.init(this)
    }

}