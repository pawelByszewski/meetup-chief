package pl.mobilewarsaw.meetupchief.ui.events;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(RobolectricGradleTestRunner.class)
public class MeetupGroupsActivityTest {

    private MeetupGroupsActivity meetupGroupsActivity;

    @Before
    public void setUp() {
        meetupGroupsActivity = Robolectric.buildActivity(MeetupGroupsActivity.class).create().resume().start().get();
    }

    @Test
    public void shouldShowEmptyViewAtStart() {
        //then
        assertThat(meetupGroupsActivity.getEmptyView()).isVisible();
    }

    @Test
    public void shouldShowErrorViewWhenEmptyCursorProvided() {
        //given
        Cursor cursor = mockCursor(0);

        //when
        meetupGroupsActivity.showMeetupGroups(cursor);

        //then
        assertThat(meetupGroupsActivity.getErrorView()).isVisible();
    }

    @Test
    public void shouldHideEmptyViewWhenNotEmptyCursorProvided() {
        //given
        Cursor cursor = mockCursor(1);

        //when
        meetupGroupsActivity.showMeetupGroups(cursor);

        //then
        assertThat(meetupGroupsActivity.getEmptyView()).isNotVisible();
    }

    @NonNull
    private Cursor mockCursor(int rowCount) {
        Cursor cursor = mock(Cursor.class);
        when(cursor.getCount()).thenReturn(rowCount);
        return cursor;
    }
}
