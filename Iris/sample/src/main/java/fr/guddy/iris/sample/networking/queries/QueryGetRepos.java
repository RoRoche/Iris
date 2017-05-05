package fr.guddy.iris.sample.networking.queries;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.Params;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import fr.guddy.iris.api.annotations.Query;
import fr.guddy.iris.api.annotations.Result;
import fr.guddy.iris.library.AbstractQuery;
import fr.guddy.iris.sample.IrisApplication;
import fr.guddy.iris.sample.networking.ApiService;
import fr.guddy.iris.sample.networking.dto.RepoDTO;

@Query
public class QueryGetRepos extends AbstractQuery {

    //region Query parameters
    public final String user;
    //endregion

    //region Injected fields
    transient public ApiService apiService;
    transient public EventBus eventBus;
    //endregion

    //region Results
    @Result
    private List<RepoDTO> mRepos;
    //endregion

    //region Constructor
    public QueryGetRepos(@NonNull final String pUser) {
        super(new Params(1));
        user = pUser;
    }
    //endregion

    //region Overridden methods from AbstractQuery
    @Override
    protected void execute() throws Throwable {
        IrisApplication.getInstance()
                .getApplicationComponent()
                .inject(this);
        mRepos = apiService.listRepos(user).execute().body();
    }

    @Override
    protected void onQueryDidFinish() {
        // TODO
    }
    //endregion
}
