package fr.guddy.iris.test;

import fr.guddy.iris.api.annotations.Query;
import fr.guddy.iris.api.annotations.Result;

@Query
public class QueryWithMultipleResults {

    @Result
    private boolean mSuccess;
    @Result
    private int mResult;

    //region Constructor
    public QueryWithMultipleResults() {
    }
    //endregion
}
