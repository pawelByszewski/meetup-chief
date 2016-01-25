package pl.mobilewarsaw.meetupchief.resource.remote.meetup

import pl.mobilewarsaw.meetupchief.resource.meetup.MeetupEvents
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable


interface MeetupResource {

    @GET("events")
    fun getEvents(@Query("group_urlname") groupName: String,
                  @Query("page") page: String = "20",
                  @Query("status") status: String = "past,upcoming",
                  @Query("photo-host") photoHost: String = "public",
                  @Query("sign") sign: String = "true",
                  @Query("key") key: String = "655f2a4d796d187c673a5f7af684760",
                  @Query("desc") desc: String = "true")
            : Observable<MeetupEvents>
}