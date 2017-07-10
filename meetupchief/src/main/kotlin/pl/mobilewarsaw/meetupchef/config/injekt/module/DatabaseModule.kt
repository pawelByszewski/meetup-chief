package pl.mobilewarsaw.meetupchef.config.injekt.module

import android.content.Context
import io.requery.android.sqlite.DatabaseSource
import io.requery.sql.Configuration
import io.requery.sql.TableCreationMode
import pl.mobilewarsaw.meetupchef.BuildConfig
import pl.mobilewarsaw.meetupchef.database.Database
import pl.mobilewarsaw.meetupchef.database.model.Models
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType


class DatabaseModule(val context: Context) : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        addFactory(fullType<Database>()) { Database.getInstance(context) }
        addFactory(fullType<Configuration>()) {
            DatabaseConfigurationProvider(context).provide("meetupchef")
        }
    }
}

val DATABASE_VERSION = 1

class DatabaseConfigurationProvider constructor(private val context: Context) {

    fun provide(databaseName: String): Configuration
            = DatabaseSource(context, Models.DEFAULT, databaseName, DATABASE_VERSION)
            .apply {
                setTableCreationMode(TableCreationMode.DROP_CREATE)

                if (BuildConfig.DEBUG) {
                    setLoggingEnabled(false)
                }
            }
            .configuration
}

