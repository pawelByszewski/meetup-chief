package pl.mobilewarsaw.meetupchief.ui.groups

import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.bindView

import pl.mobilewarsaw.meetupchief.R
import pl.mobilewarsaw.meetupchief.ui.searchview.SearchView
import pl.mobilewarsaw.meetupchief.presenter.groups.MeetupGroupsPresenter
import pl.mobilewarsaw.meetupchief.presenter.groups.RESTORE_KEY
import pl.mobilewarsaw.meetupchief.presenter.groups.RESTORE_QUERY
import uy.kohesive.injekt.injectValue

class MeetupGroupsActivity : AppCompatActivity(), MeetupGroupsView {

    protected val emptyView: View by bindView(R.id.empty_view)
    protected val errorView: View by bindView(R.id.error_view)

    private val meetupGroupsPresenter: MeetupGroupsPresenter by injectValue()
    private val searchView: SearchView by bindView(R.id.search_view)
    private val groupsRecycleView: RecyclerView by bindView(R.id.meetup_groups_list)
    private val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.swipe_container)

    private val meetupGroupsRecycleViewAdapter: MeetupGroupsCursorAdapter by injectValue()

    private var showedQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meetup_groups)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)

        meetupGroupsPresenter.bind(this, this, savedInstanceState)
        setupRecycleView()

        searchView.onSearch = { query ->
            showedQuery = query
            meetupGroupsPresenter.findMeetups(query) }

        swipeRefreshLayout.setOnRefreshListener {
            meetupGroupsPresenter.refreshGroups()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean(RESTORE_KEY, true)
        outState?.putString(RESTORE_QUERY, showedQuery)
        super.onSaveInstanceState(outState)
    }

    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        groupsRecycleView.layoutManager = layoutManager
        groupsRecycleView.adapter = meetupGroupsRecycleViewAdapter
    }

    override fun showMeetupGroups(cursor: Cursor) {
        if (cursor.count == 0) {
            errorView.show()
            swipeRefreshLayout.hide()
            emptyView.hide()
        } else {
            emptyView.hide()
            errorView.hide()
            swipeRefreshLayout.show()
        }
        meetupGroupsRecycleViewAdapter!!.changeCursor(cursor)
        swipeRefreshLayout.isRefreshing = false
    }
}

//TODO to basil

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}
