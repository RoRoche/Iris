package fr.guddy.iris;

public abstract class AbstractEventQueryDidFinish<QueryType> {
    //region Fields
    public final QueryType query;
    //endregion

    //region Constructor
    public AbstractEventQueryDidFinish(final QueryType pQuery) {
        query = pQuery;
    }
    //endregion
}
