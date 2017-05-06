# Iris

Convenient wrapper library to perform network queries using Retrofit and Android Priority Job Queue (Job Manager)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Iris-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5703)

**STILL IN DEVELOPMENT**

![logo](https://raw.githubusercontent.com/RoRoche/Iris/master/assets/logo.png)

## Dependency

In your project `build.gradle` file:

```groovy
repositories {
    maven {
        url 'https://dl.bintray.com/guddy/maven/'
    }
}

dependencies {
    compile 'fr.guddy.iris:iris:0.0.2'
    annotationProcessor 'fr.guddy.iris:compiler:0.0.2'
}
```

## How to use compiler

- Create your retrofit interface as usual

```java
public interface ApiService {
    @GET("users/{user}/repos")
    Call<List<RepoDTO>> listRepos(@NonNull @Path("user") final String user);
}
```

- The annotation processor will generate the following class:

```java
public abstract class AbstractQueryListRepos extends AbstractQuery {
  public final String user;

  private transient List<RepoDTO> mResult;

  protected AbstractQueryListRepos(final Params params, final String user) {
    super(params);
    this.user = user;
  }

  public List<RepoDTO> getResult() {
    return mResult;
  }

  @Override
  protected void execute() throws Throwable {
    mResult = getApiService().listRepos(user).execute().body();}

  @Override
  protected void onQueryDidFinish() {
  }

  protected abstract ApiService getApiService();

  public static final class EventQueryListReposDidFinish extends AbstractEventQueryDidFinish<AbstractQueryListRepos> {
    public EventQueryListReposDidFinish(final AbstractQueryListRepos query) {
      super(query);
    }
  }
}
```

- now just subclass `AbstractQueryListRepos` as follows to provide the `ApiService` instance and deal with query ending:

```java
public class QueryListRepos extends AbstractQueryListRepos {

    public QueryListRepos(@NonNull final String pUser) {
        super(new Params(1), pUser);
    }

    @Override
    protected ApiService getApiService() {
        return IrisApplication.getInstance()
                .getApplicationComponent()
                .apiService();
    }

    @Override
    protected void onQueryDidFinish() {
        IrisApplication.getInstance()
                .getApplicationComponent()
                .eventBus()
                .post(new EventQueryListReposDidFinish(this));
    }
}
```

## How to use `AbstractQuery` standalone

- Subclass `AbstractQuery`

```java
public class QueryGetRepos extends AbstractQuery {

    public final String user;

    public QueryGetRepos(@NonNull final String pUser) {
        super(new Params(1));
        user = pUser;
    }

    @Override
    protected void execute() throws Throwable {
        // TODO perform network query thanks to retrofit
    }

    @Override
    protected void onQueryDidFinish() {
        // TODO deal with results
    }
}
```

- Subclass `AbstractEventQueryDidFinish`

```java
public static final class EventQueryGetReposDidFinish extends AbstractEventQueryDidFinish<QueryGetRepos> {
    public EventQueryGetReposDidFinish(final QueryGetRepos pQuery) {
        super(pQuery);
    }
}
```

- Add query to a `JobManager`:

```java
jobManager.addJobInBackground(new QueryGetRepos("RoRoche"));
```

## Benefits

- DI ready
- Event-buses ready

## TODO

- support `@POST` and `@PUT` from retrofit

## Logo credits

Nature graphic by <a href="http://www.flaticon.com/authors/freepik">Freepik</a> from <a href="http://www.flaticon.com/">Flaticon</a> is licensed under <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a>. Made with <a href="http://logomakr.com" title="Logo Maker">Logo Maker</a>
