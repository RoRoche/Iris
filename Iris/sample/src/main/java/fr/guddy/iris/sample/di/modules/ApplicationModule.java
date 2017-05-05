package fr.guddy.iris.sample.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    //region Fields
    private final Context mContext;
    //endregion

    //region Constructor
    public ApplicationModule(@NonNull final Context pContext) {
        mContext = pContext;
    }
    //endregion

    //region Provides methods
    @Provides
    public Context providesContext() {
        return mContext;
    }
    //endregion
}
