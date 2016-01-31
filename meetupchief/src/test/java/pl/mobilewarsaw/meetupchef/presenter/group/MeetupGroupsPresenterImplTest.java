package pl.mobilewarsaw.meetupchef.presenter.group;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.robolectric.RobolectricTestRunner;

import pl.mobilewarsaw.meetupchef.presenter.groups.MeetupGroupsPresenter;
import pl.mobilewarsaw.meetupchef.presenter.groups.MeetupGroupsPresenterImpl;
import pl.mobilewarsaw.meetupchef.resource.local.meetup.repository.GroupRepository;
import pl.mobilewarsaw.meetupchef.ui.groups.MeetupGroupsView;
import uy.kohesive.injekt.InjektMain;
import uy.kohesive.injekt.api.FullTypeReference;
import uy.kohesive.injekt.api.InjektRegistrar;

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

    MeetupGroupsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        InjektMain injektMain = new InjektMain() {
            @Override
            public void registerInjectables(InjektRegistrar injektRegistrar) {
                injektRegistrar.addSingleton(new FullTypeReference<GroupRepository>(){}, mock(GroupRepository.class));
            }
        };
        presenter = new MeetupGroupsPresenterImpl();
    }

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

    private Cursor mockContentResolverMechanism(Context context) {
        ContentResolver contentResolver = mock(ContentResolver.class);
        Cursor cursor = mock(Cursor.class);
        when(context.getContentResolver()).thenReturn(contentResolver);
        when(contentResolver.query(any(Uri.class), any(String[].class), any(String.class),
                any(String[].class), any(String.class))).thenReturn(cursor);
        return cursor;
    }
}
