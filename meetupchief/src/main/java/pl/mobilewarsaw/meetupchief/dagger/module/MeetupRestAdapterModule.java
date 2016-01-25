package pl.mobilewarsaw.meetupchief.dagger.module;


import pl.mobilewarsaw.meetupchief.resource.remote.meetup.MeetupResource;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

public class MeetupRestAdapterModule {

    MeetupResource provideMeetupResource(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://api.meetup.com/2")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(MeetupResource.class);
    }

    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);
        return client;
    }
}
