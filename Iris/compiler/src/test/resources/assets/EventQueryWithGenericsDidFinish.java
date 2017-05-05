package assets;

import fr.guddy.iris.api.AbstractEventQueryDidFinish;

public class EventQueryWithGenericsDidFinish<TParam, TResult> extends AbstractEventQueryDidFinish<QueryWithGenerics<TParam, TResult>> {
    private TResult mResult;

    public EventQueryWithGenericsDidFinish(final QueryWithGenerics pQuery, final Throwable pThrowable) {
        super(pQuery, pThrowable);
    }
}
