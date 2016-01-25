package pl.mobilewarsaw.meetupchief.ui.events

import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import pl.mobilewarsaw.meetupchief.R
import pl.mobilewarsaw.meetupchief.presenter.events.EventListPresenter
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupEventContentProvider
import pl.mobilewarsaw.meetupchief.service.events.MeetupEventsSynchronizer
import uy.kohesive.injekt.injectValue

class EventsListActivity : AppCompatActivity(), EventsListView {

    val eventListPresenter: EventListPresenter by injectValue()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventListPresenter.bind(this)

//        startService(Intent(this, MeetupEventsSynchronizer::class.java))

////        testButton.setOnClickListener {
//            val cursor = contentResolver.query(MeetupEventContentProvider.CONTENT_URI, null, null, null, null)
//            cursor.setNotificationUri(contentResolver, MeetupEventContentProvider.CONTENT_URI)
//        Log.e("kabum", "1 ${cursor.count}")
//        cursor.registerContentObserver( object: ContentObserver(Handler()) {
//                override fun onChange(selfChange: Boolean) {
//                    super.onChange(selfChange)
//                    Log.e("kabum", "2 ${cursor.count}")
//                }
//
//                override fun onChange(selfChange: Boolean, uri: Uri?) {
//                    super.onChange(selfChange, uri)
//                    val cursor = contentResolver.query(MeetupEventContentProvider.CONTENT_URI, null, null, null, null)
//                    Log.e("kabum", "3 ${cursor.count}")
//                }
//            })
////        }
    }

}
