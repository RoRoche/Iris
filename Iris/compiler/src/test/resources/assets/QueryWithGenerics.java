package assets;

import fr.guddy.iris.api.annotations.Query;
import fr.guddy.iris.api.annotations.Result;

@Query
public class QueryWithGenerics<TParam, TResult> {
    private final TParam mParam;

    @Result
    private TResult mResult;

    //region Constructor
    public QueryWithGenerics(final TParam pParam) {
        mParam = pParam;
    }
    //endregion
}
