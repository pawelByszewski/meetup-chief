package pl.mobilewarsaw.meetupchief

import android.content.Context
import org.junit.Test
import org.mockito.Mockito.mock
import pl.mobilewarsaw.meetupchief.ui.searchView.SearchView
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchViewTest {

    @Ignore("Support roboletric")
    @Test
    fun `should track query`() {
        //given
//        val context = mock(Context::class.java)
        val searchView = SearchView(Robolectric.application)

        //when
        searchView.setSearchText("kabum")

        //then
        assertThat(searchView.currentQuery).isEqualTo("kabum")
    }
}