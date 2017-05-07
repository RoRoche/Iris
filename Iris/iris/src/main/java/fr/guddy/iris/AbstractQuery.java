package fr.guddy.iris;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import java.net.HttpURLConnection;

import retrofit2.Response;

public abstract class AbstractQuery<TypeResult> extends Job {

    //region Fields
    transient protected Throwable mThrowable;
    transient protected Response<TypeResult> mResponse;
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

    //region Method to override
    protected abstract void execute() throws Throwable;

    protected abstract void onQueryDidFinish();
    //endregion

    //region Visible API
    public boolean isResponseCached() {
        if (mResponse.isSuccessful() &&
                (
                        (mResponse.raw().networkResponse() != null && mResponse.raw().networkResponse().code() == HttpURLConnection.HTTP_NOT_MODIFIED)
                                ||
                                (mResponse.raw().networkResponse() == null && mResponse.raw().cacheResponse() != null))
                ) {
            return true;
        }
        return false;
    }

    public Response<TypeResult> getResponse() {
        return mResponse;
    }

    public Throwable getThrowable() {
        return mThrowable;
    }
    //endregion
}
