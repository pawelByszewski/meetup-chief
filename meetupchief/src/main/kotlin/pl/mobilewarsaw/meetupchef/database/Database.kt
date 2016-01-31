package pl.mobilewarsaw.meetupchef.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import pl.mobilewarsaw.meetupchef.Settings
import pl.touk.basil.dropTableIfExists


class Database : SQLiteOpenHelper {

    private constructor(context: Context) : super(context, Settings.databse.name, null, Settings.databse.version)

    override fun onCreate(db: SQLiteDatabase?) {
        dropAllTables(db)
        db?.execSQL(EventTable.CREATE_STATEMENT)
        db?.execSQL(MeetupGroupTable.CREATE_STATEMENT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }

    private fun dropAllTables(db: SQLiteDatabase?) {
        db?.dropTableIfExists(EventTable.TABLE)
        db?.dropTableIfExists(MeetupGroupTable.TABLE)
    }

    companion object {
        private var INSTANCE: Database? = null

        @Synchronized
        @JvmStatic
        fun getInstance(context: Context): Database {
            if (INSTANCE == null) {
                INSTANCE = Database(context)
            }
            return INSTANCE as Database
        }

    }
}