package pl.mobilewarsaw.meetupchief.config.injekt

import pl.mobilewarsaw.meetupchief.config.injekt.module.MeetupApiModule
import pl.mobilewarsaw.meetupchief.config.injekt.module.PresentersModule
import uy.kohesive.injekt.InjektMain
import uy.kohesive.injekt.api.InjektRegistrar


class MeetupChiefInjektMain : InjektMain() {

    companion object {
        fun init() {
            MeetupChiefInjektMain()
        }
    }

    override fun InjektRegistrar.registerInjectables() {
        importModule(MeetupApiModule())
        importModule(PresentersModule())
    }
}