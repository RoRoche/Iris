package fr.guddy.iris.api;

public abstract class AbstractEventQueryDidFinish<QueryType> {
    //region Fields
    public final QueryType query;
    public final Throwable throwable;
    //endregion

    //region Constructor
    public AbstractEventQueryDidFinish(final QueryType pQuery, final Throwable pThrowable) {
        query = pQuery;
        throwable = pThrowable;
    }
    //endregion
}
