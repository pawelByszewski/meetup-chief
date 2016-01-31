package pl.mobilewarsaw.meetupchef.ui.groups;

import android.database.Cursor;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import pl.mobilewarsaw.meetupchef.config.injekt.MeetupGroupsActivityTestInjekt;
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.GroupRepository;
import uy.kohesive.injekt.InjektMain;
import uy.kohesive.injekt.api.FullTypeReference;
import uy.kohesive.injekt.api.InjektRegistrar;
import uy.kohesive.injekt.api.TypeReference;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(RobolectricGradleTestRunner.class)
@Ignore
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
