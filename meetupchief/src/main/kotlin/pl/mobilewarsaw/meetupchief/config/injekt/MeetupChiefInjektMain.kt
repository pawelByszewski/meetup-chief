package pl.mobilewarsaw.meetupchief.config.injekt

import android.content.Context
import pl.mobilewarsaw.meetupchief.config.injekt.module.DatabaseModule
import pl.mobilewarsaw.meetupchief.config.injekt.module.MeetupApiModule
import pl.mobilewarsaw.meetupchief.config.injekt.module.PicassoModule
import pl.mobilewarsaw.meetupchief.config.injekt.module.PresentersModule
import uy.kohesive.injekt.InjektMain
import uy.kohesive.injekt.api.InjektRegistrar

class MeetupChiefInjektMain(val context: Context){

    companion object {
        fun init(context: Context) {
            (object : InjektMain() {
                override fun InjektRegistrar.registerInjectables() {
                    importModule(MeetupApiModule())
                    importModule(PresentersModule())
                    importModule(DatabaseModule(context))
                    importModule(PicassoModule(context))
                }
            })
        }
    }
}