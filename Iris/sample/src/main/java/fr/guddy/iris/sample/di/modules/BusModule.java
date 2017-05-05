package fr.guddy.iris.sample.di.modules;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BusModule {
    //region Provides methods
    @Provides
    @Singleton
    public EventBus providesEventBus() {
        return EventBus.getDefault();
    }
    //endregion
}
