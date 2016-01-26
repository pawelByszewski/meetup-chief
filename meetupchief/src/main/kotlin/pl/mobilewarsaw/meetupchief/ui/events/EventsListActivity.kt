package pl.mobilewarsaw.meetupchief.ui.events

import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import butterknife.bindView
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

import pl.mobilewarsaw.meetupchief.R
import pl.mobilewarsaw.meetupchief.presenter.events.EventListPresenter
import pl.mobilewarsaw.meetupchief.ui.searchView.SearchView
import uy.kohesive.injekt.injectValue

class EventsListActivity : AppCompatActivity(), EventsListView {

    val eventListPresenter: EventListPresenter by injectValue()

    val floatingSearchView: SearchView by bindView(R.id.floating_search_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventListPresenter.bind(this, this)


        floatingSearchView.onSearchListener = { query -> eventListPresenter.findMeetups(query) }

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
