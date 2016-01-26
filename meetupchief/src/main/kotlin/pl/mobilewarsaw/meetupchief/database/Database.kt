package pl.mobilewarsaw.meetupchief.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class Database : SQLiteOpenHelper {

    private constructor(context: Context) : super(context, DATABASE_NAME, null, DATABASE_VERSION)

    override fun onCreate(db: SQLiteDatabase?) {
        dropAllTables(db)
        db?.execSQL(EventTable.CREATE_STATEMENT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dropAllTables(db)
    }

    private fun dropAllTables(db: SQLiteDatabase?) {
        db?.execSQL("DROP TABLE IF EXISTS " + EventTable.TABLE)
        db?.execSQL("DROP TABLE IF EXISTS " + MeetupGroupTable.TABLE)
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "meetup_chief"

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