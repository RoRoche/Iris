package fr.guddy.iris.test;

import fr.guddy.iris.api.annotations.Query;

@Query
public class QueryWithParamAndNoResult {

    //region Query parameters
    public final String user;
    //endregion

    //region Constructor
    public QueryWithParamAndNoResult(final String pUser) {
        user = pUser;
    }
    //endregion
}
