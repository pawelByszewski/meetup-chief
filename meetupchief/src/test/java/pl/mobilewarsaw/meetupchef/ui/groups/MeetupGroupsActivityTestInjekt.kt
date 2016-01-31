package pl.mobilewarsaw.meetupchef.config.injekt

import android.content.Context
import pl.mobilewarsaw.meetupchef.config.injekt.module.*
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.GroupRepository
import uy.kohesive.injekt.InjektMain
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType
import org.mockito.Mockito.mock


class MeetupGroupsActivityTestInjekt(){

    companion object {
        @JvmStatic
        fun init() {
            (object : InjektMain() {
                override fun InjektRegistrar.registerInjectables() {

                    addSingletonFactory(fullType<GroupRepository>()) { mock(GroupRepository::class.java) }
                }
            })
        }
    }
}