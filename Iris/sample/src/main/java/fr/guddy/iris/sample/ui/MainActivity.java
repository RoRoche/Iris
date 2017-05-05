package fr.guddy.iris.sample.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import fr.guddy.iris.sample.IrisApplication;
import fr.guddy.iris.sample.R;
import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity {

    //region Injected fields
    @Inject
    public EventBus eventBus;
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
    protected void onResume() {
        super.onResume();
        // TODO
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }
    //endregion

    //region Event management
    @DebugLog
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(@NonNull final Object pEvent) {
    }
    //endregion
}
