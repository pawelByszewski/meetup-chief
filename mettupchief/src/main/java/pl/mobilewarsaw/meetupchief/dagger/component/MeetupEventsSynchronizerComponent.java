package pl.mobilewarsaw.meetupchief.dagger.component;

import android.content.Context;

import dagger.Component;
import pl.mobilewarsaw.meetupchief.dagger.module.MeetupRestAdapterModule;
import pl.mobilewarsaw.meetupchief.service.events.MeetupEventsSynchronizer;

@Component(
        dependencies = DatabaseComponent.class,
        modules = {MeetupRestAdapterModule.class}
)
public interface MeetupEventsSynchronizerComponent {

    class Initializer {
        public static MeetupEventsSynchronizerComponent init(Context context) {
            return DaggerMeetupEventsSynchronizerComponent.builder()
                    .databaseComponent(DatabaseComponent.Initializer.init(context))
                    .meetupRestAdapterModule(new MeetupRestAdapterModule())
                    .build();

        }
    }

    void inject(MeetupEventsSynchronizer meetupEventsSynchronizer);
}
