package pl.mobilewarsaw.meetupchief.config.injekt.module

import android.content.Context
import com.squareup.picasso.Picasso
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType


class PicassoModule(val context: Context) : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        addFactory(fullType<Picasso>()) { Picasso.with(context) }
    }
}