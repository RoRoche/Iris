package assets;

import fr.guddy.iris.api.AbstractEventQueryDidFinish;

public class EventQueryWithMultipleResultsDidFinish extends AbstractEventQueryDidFinish<QueryWithMultipleResults> {
    public final boolean mSuccess;
    public final int mResult;

    public EventQueryWithMultipleResultsDidFinish(final QueryWithMultipleResults pQuery, final Throwable pThrowable, final boolean pSuccess, final int pResult) {
        super(pQuery, pThrowable);
        mSuccess = pSuccess;
        mResult = pResult;
    }
}
