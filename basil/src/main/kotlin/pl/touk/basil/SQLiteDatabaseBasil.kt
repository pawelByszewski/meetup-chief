package pl.touk.basil

import android.database.sqlite.SQLiteDatabase

inline fun SQLiteDatabase.dropTableIfExists(tableName: String) {
    execSQL("DROP TABLE IF EXISTS $tableName;")
}


