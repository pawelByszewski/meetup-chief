package pl.mobilewarsaw.meetupchief.dagger.component;

import dagger.Component;
import pl.mobilewarsaw.meetupchief.dagger.module.MeetupRestAdapterModule;
import pl.mobilewarsaw.meetupchief.service.events.MeetupEventsSynchronizer;

@Component(
        modules = {MeetupRestAdapterModule.class}
)
public interface MeetupEventsSynchronizerComponent {

    class Initializer {
        public static MeetupEventsSynchronizerComponent init() {
            return DaggerMeetupEventsSynchronizerComponent.builder()
                    .meetupRestAdapterModule(new MeetupRestAdapterModule())
                    .build();

        }
    }

    void inject(MeetupEventsSynchronizer meetupEventsSynchronizer);
}
