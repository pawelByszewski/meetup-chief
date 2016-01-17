package pl.mobilewarsaw.meetupchief.dagger.module;

import dagger.Module;
import dagger.Provides;
import pl.mobilewarsaw.meetupchief.presenter.events.EventListPresenter;
import pl.mobilewarsaw.meetupchief.presenter.events.EventsListPresenterImpl;

@Module
public class EventListModule {

    @Provides
    EventListPresenter provideEventListPresenter() {
        return new EventsListPresenterImpl();
    }
}
