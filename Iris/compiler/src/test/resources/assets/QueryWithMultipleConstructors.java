package assets;

import fr.guddy.iris.api.annotations.Query;
import fr.guddy.iris.api.annotations.Result;

@Query
public class QueryWithMultipleConstructors {

    //region Constructor
    public QueryWithMultipleConstructors(final int pBool) {
    }

    public QueryWithMultipleConstructors(final boolean pB) {
    }
    //endregion
}
