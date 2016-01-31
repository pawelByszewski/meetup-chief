package pl.mobilewarsaw.meetupchef.ui.events

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.NavUtils
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import butterknife.bindView
import com.squareup.picasso.Picasso
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.presenter.events.EventsListingPresenter
import pl.mobilewarsaw.meetupchef.presenter.events.State
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupGroup
import pl.mobilewarsaw.meetupchef.ui.groups.MeetupEventCursorAdapter
import pl.mobilewarsaw.meetupchef.widget.progressbar.ChefProgressBar
import pl.touk.basil.isEmpty
import pl.touk.basil.show
import uy.kohesive.injekt.injectValue

const val GROUP_URL_NAME_KEY = "urlname"
const val GROUP_NAME_KEY = "groupName"
const val GROUP_PHOTO = "groupPhoto"

class EventsListingActivity : AppCompatActivity(), EventsListingView {

    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val eventsListing: RecyclerView by bindView(R.id.meetup_events_list)
    private val toolbarImage: ImageView by bindView(R.id.toolbar_image)
    private val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.swipe_container)
    private val progressBar: ChefProgressBar by bindView(R.id.progress_bar)
    private val emptyView: View by bindView(R.id.empty_view)

    private val presenter: EventsListingPresenter by injectValue()
    private val picasso: Picasso by injectValue()

    private val eventsRecycleViewAdapter: MeetupEventCursorAdapter by injectValue()

    companion object {
        fun createIntent(context: Context, meetupGroup: MeetupGroup): Intent {
            val intent = Intent(context, EventsListingActivity::class.java)
            intent.putExtra(GROUP_URL_NAME_KEY, meetupGroup.urlName)
            intent.putExtra(GROUP_NAME_KEY, meetupGroup.name)
            intent.putExtra(GROUP_PHOTO, meetupGroup.photoUrl)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_listing)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)

        setupToolbar()
        setupRecycleView()

        swipeRefreshLayout.setOnRefreshListener {
            presenter.refreshEvents()
        }

        presenter.bind(this, this, savedInstanceState, intent)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (outState != null) {
            presenter.saveState(outState)
        }
        super.onSaveInstanceState(outState)
    }

    override fun showGroupPhoto(photoUrl: String?) {
        if (photoUrl.isNullOrBlank()) {
            showPlaceHolder()
        } else {
            showImage(photoUrl!!    )
        }
    }

    private fun showPlaceHolder() {
        picasso.load(R.drawable.card_image_empty_view)
                .fit()
                .centerCrop()
                .into(toolbarImage)
    }

    private fun showImage(pictureUrl: String) {
        picasso.load(pictureUrl)
                .fit()
                .centerCrop()
                .into(toolbarImage)
    }

    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        eventsListing.layoutManager = layoutManager
        eventsListing.adapter = eventsRecycleViewAdapter
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    override fun showInToolbar(meeupGroupInitData: State) {
        supportActionBar.title = meeupGroupInitData.name;
    }

    override fun showEvents(cursor: Cursor) {
        eventsRecycleViewAdapter!!.changeCursor(cursor)
        swipeRefreshLayout.isRefreshing = false
        progressBar.hide()
        emptyView.show(cursor.isEmpty() )
    }

    override fun showProgressBar() {
        progressBar.show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.activity_slide_in_from_left, R.anim.activity_slide_out_to_right)
    }
}