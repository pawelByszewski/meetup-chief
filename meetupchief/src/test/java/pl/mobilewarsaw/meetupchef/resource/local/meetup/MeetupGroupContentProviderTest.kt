package pl.mobilewarsaw.meetupchef.resource.local.meetup

import android.net.Uri
import org.junit.Test
import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.MeetupGroupContentProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MeetupGroupContentProviderTest {

    var meetupGroupContentProvider = MeetupGroupContentProvider()

    @Test
    fun `should handle given uri`() {
        assertThat(canHandleUriWithPath("/${MeetupGroupContentProvider.PATH}")).isTrue()
        assertThat(canHandleUriWithPath("/${MeetupGroupContentProvider.PATH}/")).isTrue()
        assertThat(canHandleUriWithPath("/${MeetupGroupContentProvider.PATH}/13")).isTrue()
    }

    @Test
    fun `should not handle given uri`() {
        assertThat(canHandleUriWithPath("/groupss")).isFalse()
        assertThat(canHandleUriWithPath("")).isFalse()
    }

    fun canHandleUriWithPath(sufix: String): Boolean {
        val uri = Uri.parse("content://pl.mobilewarsaw.meetupchef$sufix")
        return meetupGroupContentProvider.canHandle(uri)
    }
}