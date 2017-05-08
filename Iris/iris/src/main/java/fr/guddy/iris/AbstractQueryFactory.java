package fr.guddy.iris;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.novoda.merlin.MerlinsBeard;

public abstract class AbstractQueryFactory {
    //region Fields
    private final JobManager mJobManager;
    private final MerlinsBeard mMerlinsBeard;
    //endregion

    //region Constructor
    protected AbstractQueryFactory(@NonNull final JobManager pJobManager, @NonNull final MerlinsBeard pMerlinsBeard) {
        mJobManager = pJobManager;
        mMerlinsBeard = pMerlinsBeard;
    }
    //endregion

    //region Visible API
    /**
     *
     * @param pQuery The query to add in background
     * @return <code>true</code> if query has been added to JobManager (if network is available, or if query is persistent),
     * else <code>false</code> (if network is unavailable and query is not persistent).
     */
    protected boolean startQuery(@NonNull final AbstractQuery<?> pQuery) {
        if (mMerlinsBeard.isConnected() || pQuery.isPersistent()) {
            mJobManager.addJobInBackground(pQuery);
            return true;
        }
        return false;
    }
    //endregion
}
