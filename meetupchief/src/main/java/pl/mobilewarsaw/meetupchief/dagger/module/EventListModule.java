package pl.mobilewarsaw.meetupchief.dagger.module;

import pl.mobilewarsaw.meetupchief.presenter.events.EventListPresenter;
import pl.mobilewarsaw.meetupchief.presenter.events.EventsListPresenterImpl;

public class EventListModule {

    EventListPresenter provideEventListPresenter() {
        return new EventsListPresenterImpl();
    }
}
