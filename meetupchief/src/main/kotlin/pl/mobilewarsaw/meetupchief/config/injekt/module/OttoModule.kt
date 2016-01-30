package pl.mobilewarsaw.meetupchief.config.injekt.module

import android.content.Context
import com.squareup.otto.Bus
import com.squareup.picasso.Picasso
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType


class OttoModule() : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        addSingletonFactory(fullType<Bus>()) { Bus() }
    }
}