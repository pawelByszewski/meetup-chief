package pl.mobilewarsaw.meetupchief.ui.events

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pl.mobilewarsaw.meetupchief.R
import pl.mobilewarsaw.meetupchief.resource.local.meetup.model.MeetupGroup

private const val GROUP_ID_KEY = "groupId"
private const val GROUP_NAME_KEY = "groupName"

class EventsListingActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context, meetupGroup: MeetupGroup): Intent {
            val intent = Intent(context, EventsListingActivity::class.java)
            intent.putExtra(GROUP_ID_KEY, meetupGroup.groupId)
            intent.putExtra(GROUP_NAME_KEY, meetupGroup.name)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_listing)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.activity_slide_in_from_left, R.anim.activity_slide_out_to_right)
    }
}