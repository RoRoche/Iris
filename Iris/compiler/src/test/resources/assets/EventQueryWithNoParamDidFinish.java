package assets;

import fr.guddy.iris.api.AbstractEventQueryDidFinish;

public class EventQueryWithNoParamDidFinish extends AbstractEventQueryDidFinish<QueryWithNoParam> {
    public EventQueryWithNoParamDidFinish(final QueryWithNoParam pQuery, final Throwable pThrowable) {
        super(pQuery, pThrowable);
    }
}
