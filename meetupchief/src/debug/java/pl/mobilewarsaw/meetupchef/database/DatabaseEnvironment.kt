package pl.mobilewarsaw.meetupchef.database

import android.content.Context
import pl.mobilewarsaw.meetupchef.Settings

class DatabaseEnvironment {

    fun prepare(context: Context) {
        context.deleteDatabase(Settings.databse.name)
    }
}