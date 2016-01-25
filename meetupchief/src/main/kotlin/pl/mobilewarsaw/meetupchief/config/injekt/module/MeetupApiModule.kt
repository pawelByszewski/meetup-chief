package pl.mobilewarsaw.meetupchief.config.injekt.module

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.logging.HttpLoggingInterceptor
import pl.mobilewarsaw.meetupchief.Settings
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.MeetupResource
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
        addFactory(fullType<MeetupResource>()) {
            Retrofit.Builder()
                    .baseUrl("${Settings.meetup.url}/${Settings.meetup.version}/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient())
                    .build()
                    .create(MeetupResource::class.java)
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