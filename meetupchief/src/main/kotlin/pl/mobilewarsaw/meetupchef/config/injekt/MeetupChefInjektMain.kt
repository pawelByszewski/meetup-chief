package pl.mobilewarsaw.meetupchef.config.injekt

import android.content.Context
import pl.mobilewarsaw.meetupchef.config.injekt.module.*
import uy.kohesive.injekt.InjektMain
import uy.kohesive.injekt.api.InjektRegistrar

class MeetupChefInjektMain(val context: Context){

    companion object {
        fun init(context: Context) {
            (object : InjektMain() {
                override fun InjektRegistrar.registerInjectables() {
                    importModule(MeetupApiModule())
                    importModule(PresentersModule())
                    importModule(OttoModule())
                    importModule(DatabaseModule(context))
                    importModule(PicassoModule(context))
                    importModule(RepositoryModule(context))
                }
            })
        }
    }
}