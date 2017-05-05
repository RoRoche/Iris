package fr.guddy.iris.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import java.io.IOException;

public abstract class AbstractQuery extends Job {

    //region Fields
    private Throwable mThrowable;
    //endregion

    //region Constructor
    protected AbstractQuery(final Params pParams) {
        super(pParams.requireNetwork());
    }
    //endregion

    //region Overridden methods from Job
    @Override
    public void onAdded() {
    }

    /**
     * Default behavior that can be overridden if needed (to not catch {@link Throwable} for example).
     *
     * @throws Throwable that can occurs during the execute method call.
     */
    @Override
    public void onRun() throws Throwable {
        try {
            execute();
        } catch (final Throwable lThrowable) {
            mThrowable = lThrowable;
        }

        onQueryDidFinish();

    }

    @Override
    protected void onCancel(final int pCancelReason, @Nullable final Throwable pThrowable) {
        mThrowable = pThrowable;
        onQueryDidFinish();
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull final Throwable pThrowable, final int pRunCount, final int pMaxRunCount) {
        return RetryConstraint.CANCEL;
    }
    //endregion

    //region Method to override
    protected abstract void execute() throws IOException;
    protected abstract void onQueryDidFinish();
    //endregion
}
