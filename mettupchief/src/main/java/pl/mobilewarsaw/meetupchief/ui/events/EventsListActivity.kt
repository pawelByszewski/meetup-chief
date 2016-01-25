package pl.mobilewarsaw.meetupchief.ui.events

import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import butterknife.bindView
import pl.mobilewarsaw.meetupchief.R
import pl.mobilewarsaw.meetupchief.dagger.component.EventListComponent
import pl.mobilewarsaw.meetupchief.presenter.events.EventListPresenter
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchief.service.events.MeetupEventsSynchronizer
import javax.inject.Inject

class EventsListActivity : AppCompatActivity(), EventsListView {

    @Inject
    lateinit var eventListPresenter: EventListPresenter

    val testButton: Button by bindView(R.id.testButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EventListComponent.Initializer.init().inject(this)

        startService(Intent(this, MeetupEventsSynchronizer::class.java))

//        testButton.setOnClickListener {
            val cursor = contentResolver.query(MeetupEventContentProvider.CONTENT_URI, null, null, null, null)
            cursor.setNotificationUri(contentResolver, MeetupEventContentProvider.CONTENT_URI)
        Log.e("kabum", "1 ${cursor.count}")
        cursor.registerContentObserver( object: ContentObserver(Handler()) {
                override fun onChange(selfChange: Boolean) {
                    super.onChange(selfChange)
                    Log.e("kabum", "2 ${cursor.count}")
                }

                override fun onChange(selfChange: Boolean, uri: Uri?) {
                    super.onChange(selfChange, uri)
                    val cursor = contentResolver.query(MeetupEventContentProvider.CONTENT_URI, null, null, null, null)
                    Log.e("kabum", "3 ${cursor.count}")
                }
            })
//        }
    }

}
