package pl.mobilewarsaw.meetupchief.presenter.group;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.robolectric.RobolectricTestRunner;

import pl.mobilewarsaw.meetupchief.presenter.groups.MeetupGroupsPresenter;
import pl.mobilewarsaw.meetupchief.presenter.groups.MeetupGroupsPresenterImpl;
import pl.mobilewarsaw.meetupchief.ui.groups.MeetupGroupsView;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class MeetupGroupsPresenterImplTest {

    MeetupGroupsPresenter presenter = new MeetupGroupsPresenterImpl();

    @Test
    public void shouldThrowExceptionWhenTryToUseBeforeViewBinding() throws Exception {
        //when
        catchException(presenter).findMeetups("test");

        //then
        assertThat(caughtException()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void shouldStartServiceWithGivenQueryOnFindMeetupRequest() throws Exception {
        //given
        Context context = mock(Context.class);
        MeetupGroupsView meetupGroupView = mock(MeetupGroupsView.class);
        mockContentResolverMechanism(context);

        presenter.bind(context, meetupGroupView, null);

        //when
        presenter.findMeetups("mobile");

        //then
        verify(context).startService(argThat(new ArgumentMatcher<Intent>() {

            @Override
            public boolean matches(Object argument) {
                if (argument instanceof Intent) {
                    Intent intent = (Intent) argument;
                    String query = intent.getStringExtra("query");
                    assertThat(query).isEqualTo("mobile");
                    return true;
                }
                return false;
            }
        }));
    }

    @Test
    public void shouldNotifyViewWhenNewDataInDatabase() throws Exception {
        //given
        Context context = mock(Context.class);
        MeetupGroupsView meetupGroupView = mock(MeetupGroupsView.class);
        Cursor curosr = mockContentResolverMechanism(context);

        presenter.bind(context, meetupGroupView, null);
        presenter.findMeetups("mobile");

        //when
        //TODO test it
    }


    private Cursor mockContentResolverMechanism(Context context) {
        ContentResolver contentResolver = mock(ContentResolver.class);
        Cursor cursor = mock(Cursor.class);
        when(context.getContentResolver()).thenReturn(contentResolver);
        when(contentResolver.query(any(Uri.class), any(String[].class), any(String.class),
                any(String[].class), any(String.class))).thenReturn(cursor);
        return cursor;
    }
}
