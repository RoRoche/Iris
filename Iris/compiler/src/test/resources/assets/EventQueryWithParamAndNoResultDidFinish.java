package assets;

import fr.guddy.iris.api.AbstractEventQueryDidFinish;

public class EventQueryWithParamAndNoResultDidFinish extends AbstractEventQueryDidFinish<QueryWithParamAndNoResult> {
    public EventQueryWithParamAndNoResultDidFinish(final QueryWithParamAndNoResult pQuery, final Throwable pThrowable) {
        super(pQuery, pThrowable);
    }
}
