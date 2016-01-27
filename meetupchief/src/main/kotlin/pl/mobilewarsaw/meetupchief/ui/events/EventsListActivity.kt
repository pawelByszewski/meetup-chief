package pl.mobilewarsaw.meetupchief.ui.events

import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.bindView
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

import pl.mobilewarsaw.meetupchief.R
import pl.mobilewarsaw.meetupchief.presenter.events.EventListPresenter
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupGroupContentProvider
import pl.mobilewarsaw.meetupchief.ui.events.MeetupGroupsCursorAdapter
import pl.mobilewarsaw.meetupchief.ui.searchView.SearchView
import uy.kohesive.injekt.injectValue

class EventsListActivity : AppCompatActivity(), EventsListView {

    val eventListPresenter: EventListPresenter by injectValue()

    private val floatingSearchView: SearchView by bindView(R.id.floating_search_view)
    private val groupsRecycleView: RecyclerView by bindView(R.id.meetup_groups_list)

    private var meetupGroupsRecycleViewAdapter: MeetupGroupsCursorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventListPresenter.bind(this, this)

        floatingSearchView.onSearchListener = { query -> eventListPresenter.findMeetups(query) }

        val layoutManager = LinearLayoutManager(this)
        groupsRecycleView.layoutManager = layoutManager

        eventListPresenter.findMeetups("mobile warsaw")
    }

    override fun showMeetupGroups(cursor: Cursor) {
        if (meetupGroupsRecycleViewAdapter  == null) {
            meetupGroupsRecycleViewAdapter = MeetupGroupsCursorAdapter(cursor)
            groupsRecycleView.adapter = meetupGroupsRecycleViewAdapter
        } else {
            meetupGroupsRecycleViewAdapter!!.changeCursor(cursor)
        }
    }

}
