package fr.guddy.iris.sample.di;

import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Component;
import fr.guddy.iris.sample.di.modules.ApplicationModule;
import fr.guddy.iris.sample.di.modules.BusModule;
import fr.guddy.iris.sample.di.modules.NetworkingModule;
import fr.guddy.iris.sample.networking.ApiService;
import fr.guddy.iris.sample.ui.MainActivity;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class, BusModule.class})
public interface ApplicationComponent {
    void inject(@NonNull final MainActivity pTarget);

    ApiService apiService();

    EventBus eventBus();
}
