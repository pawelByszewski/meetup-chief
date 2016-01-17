package pl.mobilewarsaw.meetupchief.dagger.component;

import dagger.Component;
import pl.mobilewarsaw.meetupchief.dagger.module.EventListModule;
import pl.mobilewarsaw.meetupchief.ui.events.EventsListActivity;

@Component(
        modules = {EventListModule.class}
)
public interface EventListComponent {

    class Initializer {
        public static EventListComponent init() {
            return DaggerEventListComponent.builder()
                    .eventListModule(new EventListModule())
                    .build();
        }
    }


    void inject(EventsListActivity eventsListActivity);
}
