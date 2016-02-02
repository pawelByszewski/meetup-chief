package pl.mobilewarsaw.meetupchef.ui.recycleview

import android.database.Cursor
import android.database.DataSetObserver
import android.support.v7.widget.RecyclerView



private const val ANDROID_ID_COLUMN = "_id"
private const val MISSING_ITEM_ID: Int = -1

const val BASIC_VIEW_TYPE = 0
const val LAST_VIEW_TYPE = 1

abstract class CursorRecyclerViewAdapter<VH : RecyclerView.ViewHolder>
    (cursor: Cursor?)
    : RecyclerView.Adapter<VH>() {

    var lastViewMode = false
        set(value) {
            field = value
            cursorWrapper.lastViewMode = field
        }

    private var idColumnIndex: Int = 0

    private val dataSetObserver: DataSetObserver?
    private var cursorWrapper: CursorWrapper

    private val countCorrection: Int
        get() = if (lastViewMode) 1 else 0

    fun isAfterLast(position: Int): Boolean {
        return lastViewMode && position == cursorWrapper.count
    }

    init {
        this.cursorWrapper = CursorWrapper(cursor)
        idColumnIndex = cursorWrapper.getColumnIndex(ANDROID_ID_COLUMN)
        dataSetObserver = NotifyingDataSetObserver()
        cursorWrapper.registerDataSetObserver(dataSetObserver)
    }

    abstract fun onBindViewHolder(eventViewHolder: VH, cursor: Cursor)

    open fun onBindViewHolderAfterLast(viewHolder: VH) {}

    override fun getItemViewType(position: Int): Int
        = when {
            !lastViewMode -> BASIC_VIEW_TYPE
            isAfterLast(position) -> LAST_VIEW_TYPE
            else -> BASIC_VIEW_TYPE
        }

    override fun getItemCount(): Int
            = cursorWrapper.count + countCorrection

    override fun getItemId(position: Int): Long {
        //TODO add this case to "kotlin, why?"
        if (cursorWrapper.moveToPosition(position)) {
            return cursorWrapper.getLong(idColumnIndex)
        }

        throw IllegalArgumentException("Cursor does not contain position: $position")
    }

    //???
    override fun setHasStableIds(hasStableIds: Boolean)
            = super.setHasStableIds(true)

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        if (!cursorWrapper.moveToPosition(position) ) {
            throw IllegalStateException("couldn't move cursor to position $position")
        }
        if(!isAfterLast(position)) {
            onBindViewHolder(viewHolder, cursorWrapper.extractCursor())
        } else {
            onBindViewHolderAfterLast(viewHolder)
        }
    }

    fun changeCursor(newCursor: Cursor) {
        val oldCursorWrapper = swapCursor(newCursor)
        oldCursorWrapper?.close()
    }

    private fun swapCursor(newCursor: Cursor): CursorWrapper? {
        if (cursorWrapper.contains(newCursor)) {
            return null
        }
        val oldCursorWrapper = cursorWrapper
        if (dataSetObserver != null) {
            oldCursorWrapper.unregisterDataSetObserver(dataSetObserver)
        }
        cursorWrapper = CursorWrapper(newCursor)

        if (dataSetObserver != null) {
            cursorWrapper.registerDataSetObserver(dataSetObserver)
        }
        idColumnIndex = newCursor.getColumnIndexOrThrow(ANDROID_ID_COLUMN)
        notifyDataSetChanged()

        return oldCursorWrapper
    }

    private inner class NotifyingDataSetObserver : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()
            notifyDataSetChanged()
        }
    }
}

private class CursorWrapper(private val cursor: Cursor?) {

    var lastViewMode = false

    val count: Int
        get() = cursor?.count ?: 0

    private val correctedCount: Int
        get() = count + countCorrection

    private val countCorrection: Int
            = if (lastViewMode) 1 else 0

    fun getColumnIndex(columnName: String) : Int
            = cursor?.getColumnIndex(columnName) ?: MISSING_ITEM_ID

    fun registerDataSetObserver(dataSetObserver: DataSetObserver)
            = cursor?.registerDataSetObserver(dataSetObserver)

    fun unregisterDataSetObserver(dataSetObserver: DataSetObserver)
            = cursor?.unregisterDataSetObserver(dataSetObserver)

    fun moveToPosition(position: Int): Boolean
        = when {
            cursor == null              -> false
            position == correctedCount  -> true
            else                        -> cursor.moveToPosition(position)
        }


    fun getLong(columnIndex: Int): Long
            = cursor?.getLong(columnIndex)
            ?: throw IllegalArgumentException("Cursor does not contain column: $columnIndex")

    fun close()
            = cursor?.close()

    fun extractCursor(): Cursor
            = cursor!!

    fun contains(newCursor: Cursor): Boolean
            = newCursor === cursor
}