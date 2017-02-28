package pl.mobilewarsaw.meetupchef.config.injekt.module

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.mobilewarsaw.meetupchef.Settings
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.MeetupRemoteResource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.fullType


class MeetupApiModule : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        prepareCustomerAdapter()
    }

    private fun InjektRegistrar.prepareCustomerAdapter() {
        addFactory(fullType<MeetupRemoteResource>()) {
            Retrofit.Builder()
                    .baseUrl("${Settings.meetup.url}/${Settings.meetup.version}/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient())
                    .build()
                    .create(MeetupRemoteResource::class.java)
        }

    }

     fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
         val client = OkHttpClient.Builder()
                 .addInterceptor(logging)
                 .build()
        return client
    }
}