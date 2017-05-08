package fr.guddy.iris.sample.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import fr.guddy.iris.sample.BuildConfig;
import fr.guddy.iris.sample.IrisApplication;
import fr.guddy.iris.sample.R;
import fr.guddy.iris.sample.networking.AbstractQueryListRepos;
import fr.guddy.iris.sample.networking.ApiServiceQueryFactory;
import fr.guddy.iris.sample.networking.queries.QueryListRepos;
import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity {
    //region Constants
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final boolean DEBUG = true;
    //endregion

    //region Injected fields
    @Inject
    public EventBus eventBus;
    @Inject
    public ApiServiceQueryFactory queryFactory;
    //endregion

    //region Lifecycle
    @Override
    protected void onCreate(final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_main);
        IrisApplication.getInstance()
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (queryFactory.startAbstractQueryListRepos(new QueryListRepos("RoRoche"))) {
            Snackbar.make(findViewById(R.id.MainActivity_ConstraintLayout), R.string.query_started, Snackbar.LENGTH_LONG)
                    .show();
        } else {
            Snackbar.make(findViewById(R.id.MainActivity_ConstraintLayout), R.string.network_unavailable, Snackbar.LENGTH_LONG)
                    .show();
        }
    }
    //endregion

    //region Event management
    @DebugLog
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(@NonNull final AbstractQueryListRepos.EventQueryListReposDidFinish pEvent) {
        if (BuildConfig.DEBUG && DEBUG) {
            Logger.t(TAG).d("%s", pEvent.query.getResult());
        }
    }
    //endregion
}
