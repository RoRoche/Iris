package assets;

import fr.guddy.iris.api.AbstractEventQueryDidFinish;
import fr.guddy.iris.test.*;

public class EventQueryWithMultipleResultsDidFinish extends AbstractEventQueryDidFinish<fr.guddy.iris.test.QueryWithMultipleResults> {
    public final boolean mSuccess;
    public final int mResult;

    public EventQueryWithMultipleResultsDidFinish(final fr.guddy.iris.test.QueryWithMultipleResults pQuery, final Throwable pThrowable, final boolean pSuccess, final int pResult) {
        super(pQuery, pThrowable);
        mSuccess = pSuccess;
        mResult = pResult;
    }
}
