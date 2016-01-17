package pl.mobilewarsaw.meetupchief.ui.events

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import pl.mobilewarsaw.meetupchief.R
import pl.mobilewarsaw.meetupchief.dagger.component.EventListComponent
import pl.mobilewarsaw.meetupchief.presenter.events.EventListPresenter
import pl.mobilewarsaw.meetupchief.service.events.MeetupEventsSynchronizer
import javax.inject.Inject

class EventsListActivity : AppCompatActivity(), EventsListView {

    @Inject
    lateinit var eventListPresenter: EventListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EventListComponent.Initializer.init().inject(this)

        startService(Intent(this, MeetupEventsSynchronizer::class.java))
    }

}
