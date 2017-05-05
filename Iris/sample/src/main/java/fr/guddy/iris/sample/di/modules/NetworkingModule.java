package fr.guddy.iris.sample.di.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.guddy.iris.sample.networking.ApiService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class NetworkingModule {
    //region Provides methods
    @Singleton
    @Provides
    public Retrofit providesRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public ApiService providesApiService(@NonNull final Retrofit pRetrofit) {
        return pRetrofit.create(ApiService.class);
    }
    //endregion
}
