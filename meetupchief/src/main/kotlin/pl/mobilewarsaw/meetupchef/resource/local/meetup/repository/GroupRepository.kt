package pl.mobilewarsaw.meetupchef.resource.local.meetup.repository

import android.content.Context
import android.util.Log
import io.requery.Persistable
import io.requery.query.Result
import io.requery.sql.Configuration
import io.requery.sql.KotlinEntityDataStore
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import pl.mobilewarsaw.meetupchef.database.model.MeetupGroup
import pl.mobilewarsaw.meetupchef.database.model.MeetupGroupEntity
import uy.kohesive.injekt.injectValue

open class GroupRepository(val androidContext: Context) {

    private val TAG = GroupRepository::class.java.name

    private val _configuration: Configuration by injectValue()
    private val database: KotlinEntityDataStore<Persistable> by lazy { KotlinEntityDataStore<Persistable>(_configuration) }

    open fun fetchAllGroupsAsync(): Deferred<Result<MeetupGroup>>
        = async(CommonPool) {
            database.select(MeetupGroup::class).get()
        }

    open fun fetchAllGroups(): Result<MeetupGroup>
        = database.select(MeetupGroup::class).get()

    fun insert(groups: List<MeetupGroupEntity>) {
        Log.d(TAG, "insert groups")
        groups.forEach { Log.d(TAG, "\t--\t  ${it.groupId} \t ${it.name}") }
        database.insert(groups)
    }

    fun clear() {
        database.delete(MeetupGroup::class).get().value()
    }
}