package fr.guddy.iris.sample.networking.queries;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.Params;

import fr.guddy.iris.sample.IrisApplication;
import fr.guddy.iris.sample.networking.AbstractQueryListRepos;
import fr.guddy.iris.sample.networking.ApiService;

public class QueryListRepos extends AbstractQueryListRepos {

    //region Constructor
    public QueryListRepos(@NonNull final String pUser) {
        super(new Params(1), pUser);
    }
    //endregion

    //region Overridden methods from AbstractQueryListRepos
    @Override
    protected ApiService getApiService() {
        return IrisApplication.getInstance()
                .getApplicationComponent()
                .apiService();
    }

    @Override
    protected void onQueryDidFinish() {
        IrisApplication.getInstance()
                .getApplicationComponent()
                .eventBus()
                .post(new EventQueryListReposDidFinish(this));
    }
    //endregion
}
