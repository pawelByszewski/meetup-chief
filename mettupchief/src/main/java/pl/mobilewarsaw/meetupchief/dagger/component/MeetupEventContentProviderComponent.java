package pl.mobilewarsaw.meetupchief.dagger.component;

import android.content.Context;

import dagger.Component;
import pl.mobilewarsaw.meetupchief.dagger.module.EventListModule;
import pl.mobilewarsaw.meetupchief.resource.local.meetup.MeetupEventContentProvider;
import pl.mobilewarsaw.meetupchief.ui.events.EventsListActivity;

@Component(
        dependencies = DatabaseComponent.class
)
public interface MeetupEventContentProviderComponent {

    class Initializer {
        public static MeetupEventContentProviderComponent init(Context context) {
            return DaggerMeetupEventContentProviderComponent.builder()
                    .databaseComponent(DatabaseComponent.Initializer.init(context))
                    .build();
        }
    }


    void inject(MeetupEventContentProvider meetupEventContentProvider);
}
