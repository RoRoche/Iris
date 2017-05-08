package fr.guddy.iris.sample;

import android.app.Application;

import fr.guddy.iris.sample.di.ApplicationComponent;
import fr.guddy.iris.sample.di.DaggerApplicationComponent;
import fr.guddy.iris.sample.di.modules.ApplicationModule;
import fr.guddy.iris.sample.di.modules.BusModule;
import fr.guddy.iris.sample.di.modules.NetworkingModule;

public class IrisApplication extends Application {

    //region Singleton
    private static IrisApplication sInstance;

    public static IrisApplication getInstance() {
        return sInstance;
    }
    //endregion

    //region Lifecycle
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkingModule(new NetworkingModule())
                .busModule(new BusModule())
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sInstance = null;
    }
    //endregion

    //region Component
    private ApplicationComponent mApplicationComponent;

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
    //endregion
}
