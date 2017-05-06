package fr.guddy.iris.library;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import java.net.HttpURLConnection;

import retrofit2.Response;

public abstract class AbstractQuery extends Job {

    //region Fields
    protected Throwable mThrowable;
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

    @Override
    public void onRun() throws Throwable {
        execute();
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

    //region Protected helper method
    protected <T> boolean isCached(@NonNull final Response<T> poResponse) {
        if (poResponse.isSuccessful() &&
                (
                        (poResponse.raw().networkResponse() != null && poResponse.raw().networkResponse().code() == HttpURLConnection.HTTP_NOT_MODIFIED)
                                ||
                                (poResponse.raw().networkResponse() == null && poResponse.raw().cacheResponse() != null))
                ) {
            return true;
        }
        return false;
    }
    //endregion

    //region Method to override
    protected abstract void execute() throws Throwable;

    protected abstract void onQueryDidFinish();
    //endregion

    //region Getters
    public Throwable getThrowable() {
        return mThrowable;
    }
    //endregion
}
