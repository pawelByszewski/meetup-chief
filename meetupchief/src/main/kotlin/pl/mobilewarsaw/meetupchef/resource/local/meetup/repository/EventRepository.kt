package pl.mobilewarsaw.meetupchef.resource.local.meetup.repository

import android.util.Log
import io.requery.Persistable
import io.requery.query.Result
import io.requery.sql.Configuration
import io.requery.sql.KotlinEntityDataStore
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import pl.mobilewarsaw.meetupchef.database.model.EventEntity
import uy.kohesive.injekt.injectValue


class EventRepository {

    private val TAG = EventRepository::class.java.name

    private val _configuration: Configuration by injectValue()
    private val database: KotlinEntityDataStore<Persistable> by lazy { KotlinEntityDataStore<Persistable>(_configuration) }

    open fun fetchAllEventsAsync(): Deferred<Result<EventEntity>>
            = async(CommonPool) {
                 database.select(EventEntity::class).get()
            }

    open fun fetchAllEvents(): Result<EventEntity>
            = database.select(EventEntity::class).get()

    fun insert(events: List<EventEntity>) {
        Log.d(TAG, "insert events")
        events.forEach { Log.d(TAG, "\t--\t  ${it.eventId} \t ${it.name}") }
        database.insert(events)
    }

    fun clear() {
        database.delete(EventEntity::class).get().value()
    }

}

