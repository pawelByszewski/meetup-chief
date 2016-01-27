package pl.mobilewarsaw.meetupchief.ui.recycleview

import android.database.Cursor
import android.database.DataSetObserver
import android.support.v7.widget.RecyclerView



private const val ANDROID_ID_COLUMN = "_id"

abstract class CursorRecyclerViewAdapter<VH : RecyclerView.ViewHolder>(
        private var cursor: Cursor)
    : RecyclerView.Adapter<VH>() {

    private var rowIdColumn: Int = 0

    private val dataSetObserver: DataSetObserver?

    init {
        rowIdColumn = this.cursor.getColumnIndex(ANDROID_ID_COLUMN)
        dataSetObserver = NotifyingDataSetObserver()
        this.cursor.registerDataSetObserver(dataSetObserver)
    }

    abstract fun onBindViewHolder(viewHolder: VH, cursor: Cursor)

    override fun getItemCount(): Int
            = cursor.count

    override fun getItemId(position: Int): Long {
        if (cursor.moveToPosition(position)) {
            return cursor.getLong(rowIdColumn)
        }
        throw IllegalArgumentException("Cursor does not contain position: $position")
    }

    override fun setHasStableIds(hasStableIds: Boolean)
            = super.setHasStableIds(true)

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        if (!cursor.moveToPosition(position)) {
            throw IllegalStateException("couldn't move cursor to position $position")
        }
        onBindViewHolder(viewHolder, cursor)
    }

    fun changeCursor(newCursor: Cursor) {
        val oldCursor = swapCursor(newCursor)
        oldCursor?.close()
    }

    private fun swapCursor(newCursor: Cursor): Cursor? {
        if (newCursor === cursor) {
            return null
        }
        val oldCursor = cursor
        if (dataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(dataSetObserver)
        }
        cursor = newCursor

        if (dataSetObserver != null) {
            cursor.registerDataSetObserver(dataSetObserver)
        }
        rowIdColumn = newCursor.getColumnIndexOrThrow(ANDROID_ID_COLUMN)
        notifyDataSetChanged()

        return oldCursor
    }

    private inner class NotifyingDataSetObserver : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()
            notifyDataSetChanged()
        }
    }
}