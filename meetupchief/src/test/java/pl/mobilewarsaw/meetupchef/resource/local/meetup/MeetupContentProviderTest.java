package pl.mobilewarsaw.meetupchef.resource.local.meetup;

import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import pl.mobilewarsaw.meetupchef.resource.local.meetup.provider.PartialContentProvider;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class MeetupContentProviderTest {

    private MeetupContentProvider meetupContentProvider;
    private PartialContentProvider partialContentProvider;
    private Uri uri = Uri.parse("www.mobilewarsaw.pl");

    @Before
    public void setUp() throws Exception {
        meetupContentProvider = new MeetupContentProvider();
        partialContentProvider = mock(PartialContentProvider.class);
        meetupContentProvider.getContentProviders().add(partialContentProvider);
    }

    @Test
    public void shouldDelegateInsertToValidPartialProvider() throws Exception {
        //given
        when(partialContentProvider.canHandle(any(Uri.class))).thenReturn(true);

        //when
        meetupContentProvider.insert(uri, null);

        //then
        verify(partialContentProvider).insert(uri, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowExceptionWhen() throws Exception {
        //given
        when(partialContentProvider.canHandle(any(Uri.class))).thenReturn(false);

        //when
        meetupContentProvider.insert(uri, null);
    }
}
