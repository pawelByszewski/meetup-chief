package pl.mobilewarsaw.meetupchief.service.events

import android.app.IntentService
import android.content.Intent
import android.util.Log
import pl.mobilewarsaw.meetupchief.dagger.component.MeetupEventsSynchronizerComponent
import pl.mobilewarsaw.meetupchief.resource.meetup.MeetupResource
import rx.Observable
import javax.inject.Inject

class MeetupEventsSynchronizer : IntentService("EventsSynchronizer") {

    @Inject
    lateinit var meetupResource: MeetupResource

    override fun onCreate() {
        super.onCreate()
        MeetupEventsSynchronizerComponent.Initializer.init().inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        meetupResource.getEvents("Mobile-Warsaw")
            .flatMap { events -> Observable.from(events.events) }
            .subscribe(
                    { Log.e("kabum   ", "$it") },
                    { Log.e("kabum", "Something goes wrong", it)}
            )
    }
}