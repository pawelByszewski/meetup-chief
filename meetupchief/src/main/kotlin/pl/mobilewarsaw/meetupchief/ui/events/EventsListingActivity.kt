package pl.mobilewarsaw.meetupchief.ui.events

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import butterknife.bindView
import pl.mobilewarsaw.meetupchief.R
import pl.mobilewarsaw.meetupchief.presenter.events.EventsListingPresenter
import pl.mobilewarsaw.meetupchief.resource.local.meetup.model.MeetupGroup
import uy.kohesive.injekt.injectValue

const val GROUP_URL_NAME_KEY = "urlname"
const val GROUP_NAME_KEY = "groupName"

class EventsListingActivity : AppCompatActivity(), EventsListingView {

    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val presenter: EventsListingPresenter by injectValue()

    companion object {
        fun createIntent(context: Context, meetupGroup: MeetupGroup): Intent {
            val intent = Intent(context, EventsListingActivity::class.java)
            intent.putExtra(GROUP_URL_NAME_KEY, meetupGroup.photoUrl)
            intent.putExtra(GROUP_NAME_KEY, meetupGroup.name)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_listing)
        setupToolbar()

        presenter.bind(this, this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    override fun showInToolbar(meeupGroupInitData: MeetuGroupInitData) {
        supportActionBar.title = meeupGroupInitData.name;
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.activity_slide_in_from_left, R.anim.activity_slide_out_to_right)
    }
}