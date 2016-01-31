package pl.mobilewarsaw.meetupchef.resource.remote.meetup

import pl.mobilewarsaw.meetupchef.Settings
import pl.mobilewarsaw.meetupchef.resource.meetup.MeetupEvents
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.model.Meetup
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

interface MeetupRemoteResource {

    @GET("/2/events")
    fun getEvents(@Query("group_urlname") urlName: String,
                  @Query("only") only: String = "id,status,name,yes_rsvp_count,venue.name,description,group.urlname",
                  @Query("sign") sign: String = "true",
                  @Query("status") status: String = "past,upcoming",
                  @Query("photo-host") photoHost: String = "public",
                  @Query("key") key: String = Settings.meetup.apiKey,
                  @Query("desc") desc: String = "true")
            : Observable<MeetupEvents>

    @GET("/find/groups")
    fun findGroup(@Query("text") query: String,
                  @Query("page") page: String = "20",
                  @Query("photo-host") photoHost: String = "public",
                  @Query("only") only: String = "id,name,members,group_photo.photo_link,category.name,urlname",
                  @Query("sign") sign: String = "true",
                  @Query("key") key: String = Settings.meetup.apiKey)
        : Observable<List<Meetup>>
}