package pl.mobilewarsaw.meetupchief.ui.searchview

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.RelativeLayout
import butterknife.bindView
import pl.mobilewarsaw.meetupchief.R
import pl.touk.android.basil.bindSystemService
import pl.touk.android.basil.recycleAfter


class SearchView(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var onSearch: ((String) -> Unit)? = null

    private val queryEditText: EditText by bindView(R.id.search_bar_text)
    private val inputManager: InputMethodManager by bindSystemService(Activity.INPUT_METHOD_SERVICE)

    init {
        RelativeLayout.inflate(context, R.layout.search_view_layout, this)

        val typedArray: TypedArray? = extractAttributes(attrs)
        typedArray?.recycleAfter {
            queryEditText.hint = getString(R.styleable.SearchView_hint)
        }

        queryEditText.setOnEditorActionListener { v, actionId, event ->
            //TODO write in Kotlin!!
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearch?.invoke(queryEditText.text.toString())
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun hideKeyboard()
        = inputManager.hideSoftInputFromWindow(windowToken, 0)


    private fun extractAttributes(attrs: AttributeSet?): TypedArray?
        = context.theme?.obtainStyledAttributes(attrs, R.styleable.SearchView, 0, 0)
}