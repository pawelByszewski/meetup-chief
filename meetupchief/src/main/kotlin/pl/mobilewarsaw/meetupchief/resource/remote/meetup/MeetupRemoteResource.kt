package pl.mobilewarsaw.meetupchief.resource.remote.meetup

import pl.mobilewarsaw.meetupchief.Settings
import pl.mobilewarsaw.meetupchief.resource.meetup.MeetupEvents
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.model.Meetup
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable


interface MeetupRemoteResource {

    @GET("events")
    fun getEvents(@Query("group_urlname") groupName: String,
                  @Query("page") page: String = "20",
                  @Query("status") status: String = "past,upcoming",
                  @Query("photo-host") photoHost: String = "public",
                  @Query("sign") sign: String = "true",
                  @Query("key") key: String = Settings.meetup.apiKey,
                  @Query("desc") desc: String = "true")
            : Observable<MeetupEvents>


    @GET("/find/groups")
    fun findGroup(@Query("text") query: String,
                  @Query("page") page: String = "20",
                  @Query("photo-host") photoHost: String = "public",
                  @Query("sign") sign: String = "true",
                  @Query("key") key: String = Settings.meetup.apiKey)
        : Observable<List<Meetup>>
}