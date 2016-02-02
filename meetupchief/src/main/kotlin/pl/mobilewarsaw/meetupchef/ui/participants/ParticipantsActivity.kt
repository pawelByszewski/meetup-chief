package pl.mobilewarsaw.meetupchef.ui.participants

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import butterknife.bindView
import pl.mobilewarsaw.meetupchef.R
import pl.mobilewarsaw.meetupchef.presenter.participants.ParticipantsPresenter
import pl.mobilewarsaw.meetupchef.resource.local.meetup.model.MeetupEvent
import pl.mobilewarsaw.meetupchef.ui.groups.MeetupParticipantsCursorAdapter
import pl.mobilewarsaw.meetupchef.widget.progressbar.ChefProgressBar
import uy.kohesive.injekt.injectValue

private const val COLUMNS_COUT = 2
const val GROUP_URL_NAME_KEY = "groupName"
const val EVENT_ID_KEY = "eventID"
const val EVENT_NAME_KEY = "groupPhoto"

class ParticipantsActivity: AppCompatActivity(), ParticipantsView {
    private val participantsRecycleView: RecyclerView by bindView(R.id.meetup_participants_list)
//    private val participantsRecycleView: RecyclerView by bindView(R.id.meetup_events_list)

    private val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.swipe_container)
    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val progressBar: ChefProgressBar by bindView(R.id.progress_bar)
    private val presenter: ParticipantsPresenter by injectValue()

    private val recycleViewAdapter: MeetupParticipantsCursorAdapter by injectValue()

    companion object {

        fun createIntent(context: Context, meetupEvent: MeetupEvent): Intent {
            val intent = Intent(context, ParticipantsActivity::class.java)
            intent.putExtra(GROUP_URL_NAME_KEY, meetupEvent.groupUrlName)
            intent.putExtra(EVENT_NAME_KEY, meetupEvent.name)
            intent.putExtra(EVENT_ID_KEY, meetupEvent.eventId)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.participants_listing_activity)

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)

        setupRecycleView()
        setupToolbar()

        presenter.bind(this, this, savedInstanceState, intent)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private fun setupRecycleView() {
        participantsRecycleView.layoutManager = GridLayoutManager(this, COLUMNS_COUT)
        participantsRecycleView.adapter = recycleViewAdapter
    }

    override fun showEventName(eventName: String) {
        supportActionBar.title = eventName
    }
}
