package pl.mobilewarsaw.meetupchef.resource.remote.meetup

import pl.mobilewarsaw.meetupchef.Settings
import pl.mobilewarsaw.meetupchef.resource.meetup.MeetupEvent
import pl.mobilewarsaw.meetupchef.resource.meetup.MeetupEvents
import pl.mobilewarsaw.meetupchef.resource.remote.meetup.model.Meetup
import pl.mobilewarsaw.meetupchef.service.model.MeetupSynchronizerQuery
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber

interface MeetupRemoteResource {

    @GET("/2/events")
    fun getEventsCallable(@Query("group_urlname") urlName: String,
                          @Query("only") only: String = "id,status,name,yes_rsvp_count,venue.name,description,group.urlname",
                          @Query("sign") sign: String = "true",
                          @Query("status") status: String = "past,upcoming",
                          @Query("photo-host") photoHost: String = "public",
                          @Query("key") key: String = Settings.meetup.apiKey,
                          @Query("desc") desc: String = "true")
            : Call<MeetupEvents>

    @GET("/find/groups")
    fun findGroupCallable(@Query("text") query: String,
                  @Query("page") page: String = "20",
                  @Query("photo-host") photoHost: String = "public",
                  @Query("only") only: String = "id,name,members,group_photo.photo_link,category.name,urlname",
                  @Query("sign") sign: String = "true",
                  @Query("key") key: String = Settings.meetup.apiKey)
        : Call<List<Meetup>>
}

fun MeetupRemoteResource.getEvents(query: String): List<MeetupEvent>
        = getEventsCallable(query).execute().body().events

fun MeetupRemoteResource.findGroup(query: MeetupSynchronizerQuery.Groups): List<Meetup> {
    Timber.d("find group with query: $query")
    return findGroupCallable(query.query).execute().body()
}