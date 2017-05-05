package fr.guddy.iris.test;

import java.util.List;

import fr.guddy.iris.api.AbstractEventQueryDidFinish;

public class EventQueryWithParamAndResultDidFinish extends AbstractEventQueryDidFinish<QueryWithParamAndResult> {
    public final List<Object> mRepos;

    public EventQueryWithParamAndResultDidFinish(final QueryWithParamAndResult pQuery, final Throwable pThrowable, final List<Object> pRepos) {
        super(pQuery, pThrowable);
        mRepos = pRepos;
    }
}
