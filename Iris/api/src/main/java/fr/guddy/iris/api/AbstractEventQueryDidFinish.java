package fr.guddy.iris.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AbstractEventQueryDidFinish<QueryType extends AbstractQuery> {
    //region Fields
    public final QueryType query;
    public final Throwable throwable;
    //endregion

    //region Constructor
    public AbstractEventQueryDidFinish(@NonNull final QueryType pQuery, @Nullable final Throwable pThrowable) {
        query = pQuery;
        throwable = pThrowable;
    }
    //endregion
}
