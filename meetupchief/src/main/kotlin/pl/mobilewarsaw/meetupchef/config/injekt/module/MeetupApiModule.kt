package pl.mobilewarsaw.meetupchef.config.injekt.module

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.logging.HttpLoggingInterceptor
import pl.mobilewarsaw.meetupchef.Settings
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.MeetupRemoteResource
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
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
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient())
                    .build()
                    .create(MeetupRemoteResource::class.java)
        }

    }

     fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.interceptors().add(interceptor)
        return client
    }
}