package pl.touk.basil

import android.database.Cursor

inline fun Cursor.isEmpty() = count == 0

inline fun Cursor.getString(columnName: String) = getString(getColumnIndex(columnName))

inline fun Cursor.getInt(columnName: String) = getInt(getColumnIndex(columnName))