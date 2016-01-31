package pl.mobilewarsaw.meetupchef.database

import android.content.Context

class DatabaseEnvironment {

    fun prepare(context: Context) {
        context.deleteDatabase(Database.DATABASE_NAME)
    }
}