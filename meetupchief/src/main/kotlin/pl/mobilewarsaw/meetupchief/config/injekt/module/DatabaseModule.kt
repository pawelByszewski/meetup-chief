package pl.mobilewarsaw.meetupchief.config.injekt.module

import android.content.Context
import pl.mobilewarsaw.meetupchief.database.Database
import pl.mobilewarsaw.meetupchief.presenter.events.EventListPresenter
import pl.mobilewarsaw.meetupchief.presenter.events.EventsListPresenterImpl
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType


class DatabaseModule(val context: Context) : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        addFactory(fullType<Database>()) { Database.getInstance(context); }
    }
}