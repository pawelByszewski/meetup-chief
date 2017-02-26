package pl.mobilewarsaw.meetupchef.ui.groups

import android.database.Cursor
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.bindView
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.presenter.groups.MeetupGroupsPresenter
import pl.mobilewarsaw.meetupchef.ui.groups.listingadapter.MeetupGroupsCursorAdapter
import pl.mobilewarsaw.meetupchef.widget.progressbar.ChefProgressBar
import pl.mobilewarsaw.meetupchef.widget.searchview.SearchView
import pl.touk.basil.hide
import pl.touk.basil.isEmpty
import pl.touk.basil.show
import uy.kohesive.injekt.injectValue

class MeetupGroupsActivity : AppCompatActivity(), MeetupGroupsView {

    protected val emptyView: View by bindView(R.id.empty_view)
    protected val errorView: View by bindView(R.id.error_view)
    private val searchView: SearchView by bindView(R.id.search_view)
    private val groupsRecycleView: RecyclerView by bindView(R.id.meetup_groups_list)
    private val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.swipe_container)
    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val progressBar: ChefProgressBar by bindView(R.id.progress_bar)

    private val presenter: MeetupGroupsPresenter by injectValue()
    private val meetupGroupsRecycleViewAdapter: MeetupGroupsCursorAdapter by injectValue()

    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meetup_groups)
        this.savedInstanceState = savedInstanceState
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)

        setupRecycleView()
        setupToolbar()

        searchView.onSearch = { query ->
            presenter.findMeetups(query)
        }

        swipeRefreshLayout.setOnRefreshListener {
            presenter.refreshGroups()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.bind(this, this, savedInstanceState)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar);
        supportActionBar!!.setDisplayHomeAsUpEnabled(false);
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    private fun setupRecycleView() {
        groupsRecycleView.layoutManager = LinearLayoutManager(this)
        meetupGroupsRecycleViewAdapter.lastViewMode = true
        groupsRecycleView.adapter = meetupGroupsRecycleViewAdapter
    }

    override fun showMeetupGroups(cursor: Cursor) {
        if (cursor.isEmpty()) {
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
        progressBar.hide()
    }

    override fun showProgressBar() {
        emptyView.hide()
        errorView.hide()
        progressBar.show()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.activity_slide_in_from_right, R.anim.activity_slide_out_to_left)
        presenter.unbind()
    }
}
