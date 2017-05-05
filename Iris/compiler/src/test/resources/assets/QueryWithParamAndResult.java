package fr.guddy.iris.test;

import java.util.List;

import fr.guddy.iris.api.annotations.Query;
import fr.guddy.iris.api.annotations.Result;

@Query
public class QueryWithParamAndResult {

    //region Query parameters
    public final String user;
    //endregion

    //region Results
    @Result
    private List<Object> mRepos;
    //endregion

    //region Constructor
    public QueryWithParamAndResult(final String pUser) {
        user = pUser;
    }
    //endregion
}
