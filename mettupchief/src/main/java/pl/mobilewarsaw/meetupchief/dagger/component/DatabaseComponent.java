package pl.mobilewarsaw.meetupchief.dagger.component;

import android.content.Context;

import dagger.Component;
import pl.mobilewarsaw.meetupchief.dagger.module.DatabaseModule;
import pl.mobilewarsaw.meetupchief.dagger.module.EventListModule;
import pl.mobilewarsaw.meetupchief.ui.events.EventsListActivity;

@Component(
        modules = {DatabaseModule.class}
)
public interface DatabaseComponent {

    class Initializer {
        public static DatabaseComponent init(Context context) {
            return DaggerDatabaseComponent.builder()
                    .databaseModule(new DatabaseModule(context))
                    .build();
        }
    }
}
