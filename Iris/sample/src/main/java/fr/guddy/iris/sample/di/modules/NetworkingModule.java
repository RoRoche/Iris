package fr.guddy.iris.sample.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;

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

    @Singleton
    @Provides
    public JobManager providesJobManager(@NonNull final Context pContext) {
        final Configuration loConfiguration = new Configuration.Builder(pContext)
                .minConsumerCount(1) //always keep at least one consumer alive
                .maxConsumerCount(3) //up to 3 consumers at a time
                .loadFactor(3) //3 jobs per consumer
                .consumerKeepAlive(120) //wait 2 minutes
                .build();
        return new JobManager(loConfiguration);
    }
    //endregion
}
