package pl.mobilewarsaw.meetupchief.ui.searchView

import android.content.Context
import android.util.AttributeSet
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion


class SearchView(context: Context?, attrs: AttributeSet?) : FloatingSearchView(context, attrs) {

    var onSearchListener: ((String) -> Unit)? = null
        set(value) {
            synchronized(this) {
                field = value
            }
        }

    var currentQuery = ""
        private set

    init {
        trackCurrentQuery()
        setupSearchListener()
    }

    private fun trackCurrentQuery() {
        setOnQueryChangeListener { oldQuery, newQuery -> currentQuery = newQuery }
    }

    private fun setupSearchListener() {
        setOnSearchListener(object : OnSearchListener {
            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {}

            override fun onSearchAction() {
                synchronized(this@SearchView) {
                    if (onSearchListener != null) {
                        (onSearchListener!!)(currentQuery)
                    }
                }
            }
        })
    }

    constructor(context: Context) : this(context, null)

}