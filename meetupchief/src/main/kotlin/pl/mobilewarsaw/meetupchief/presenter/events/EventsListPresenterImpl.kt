package pl.mobilewarsaw.meetupchief.presenter.events

import android.content.Context
import android.content.Intent
import android.util.Log
import pl.mobilewarsaw.meetupchief.resource.remote.meetup.MeetupRemoteResource
import pl.mobilewarsaw.meetupchief.service.events.MeetupSynchronizer
import pl.mobilewarsaw.meetupchief.ui.events.EventsListView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import uy.kohesive.injekt.injectValue

const val QUERY_KEY ="sdf"

class EventsListPresenterImpl: EventListPresenter {

    val meetupRemoteResource: MeetupRemoteResource by injectValue()

    lateinit private var context: Context
    lateinit private var eventsListView: EventsListView

    override fun findMeetups(query: String) {
        val intent = Intent(context, MeetupSynchronizer::class.java)
        intent.putExtra(QUERY_KEY, query)
        context.startService(intent)
//        meetupRemoteResource.findGroup("mobile Warsaw")
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ meetups ->
//                    Log.e("adsf", "sadf")
//                }, { error ->
//                    Log.e("adsf", "ssdfasdfsdfadf", error)
//                }, {
//                    Log.e("adsf", "ssdfasdfsdfadf")
//                })

    }

    override fun bind(context: Context, eventsListView: EventsListView) {
        this.context = context
        this.eventsListView = eventsListView
    }
}