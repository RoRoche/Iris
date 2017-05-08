# Iris

Convenient wrapper library to perform network queries using Retrofit and Android Priority Job Queue (Job Manager)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Iris-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5703)

**STILL IN DEVELOPMENT**

![logo](https://raw.githubusercontent.com/RoRoche/Iris/master/assets/logo.png)

<!-- run the following command line: markdown-toc -i README.md -->

<!-- toc -->

- [Dependency](#dependency)
- [How to use the Iris compiler](#how-to-use-the-iris-compiler)
- [How to use Iris standalone (i.e., without its compiler)](#how-to-use-iris-standalone-ie-without-its-compiler)
- [Benefits](#benefits)
- [Logo credits](#logo-credits)

<!-- tocstop -->

## Dependency

In the top-level `build.gradle` file:

```groovy
buildscript {
    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}
```

In the project `build.gradle` file:

```groovy
apply plugin: 'com.neenbedankt.android-apt'

repositories {
    maven {
        url 'https://dl.bintray.com/guddy/maven/'
    }
}

dependencies {
    compile 'fr.guddy.iris:iris:0.0.7'
    apt 'fr.guddy.iris:compiler:0.0.7'
}
```

## How to use the Iris compiler

- Create a retrofit interface as usual

```java
public interface ApiService {
    @GET("users/{user}/repos")
    Call<List<RepoDTO>> listRepos(@NonNull @Path("user") final String user);
}
```

Supported annotations are `@DELETE`, `@GET`, `@HEADER`, `@HTTP`, `@PATCH`, `@POST`, `@PUT`.

- The annotation processor will generate the following query class:

```java
public abstract class AbstractQueryListRepos extends AbstractQuery<List<RepoDTO>> {
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
    mResponse = getApiService().listRepos(user).execute();
    mResult = mResponse.body();
  }

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

- The annotation processor will generate the following query class:

```java
public class ApiServiceQueryFactory extends AbstractQueryFactory {
  public ApiServiceQueryFactory(final JobManager pJobManager, final MerlinsBeard pMerlinsBeard) {
    super(pJobManager, pMerlinsBeard);
  }

  public boolean startAbstractQueryListRepos(final AbstractQueryListRepos pQuery) {
    return startQuery(pQuery);
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

- start the query using the factory and test if it's return `true` or `false`:

```java
if(queryFactory.startAbstractQueryListRepos(new QueryListRepos("RoRoche"))) {
  // query started
} else {
  // no network and not a persistent query
}
```

## How to use Iris standalone (i.e., without its compiler)

- Subclass `AbstractQuery`

```java
public class QueryGetRepos extends AbstractQuery<List<RepoDTO>> {

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

- Subclass `AbstractQueryFactory` :

```java
public class QueryFactory extends AbstractQueryFactory {
    public QueryFactory(final JobManager pJobManager, final MerlinsBeard pMerlinsBeard) {
        super(pJobManager, pMerlinsBeard);
    }

    public boolean startQueryListRepos(@NonNull final String pUser) {
        return startQuery(new QueryListRepos(pUser));
    }
}
```

## Benefits

- processing the retrofit interface, the compiler generates all of the boilerplate code
- developer can focus on the specific job to do when the HTTP request is finished (for example: save data to local storage, by overriding the `execute` method)
- the "Iris" library can be used without its compiler
- it improves the network layer with a normalized structure
- it's DI ready (cf. sample app using Dagger2)
- it's Event-buses ready (cf. sample app using greenrobot's EventBus)

## Logo credits

Nature graphic by <a href="http://www.flaticon.com/authors/freepik">Freepik</a> from <a href="http://www.flaticon.com/">Flaticon</a> is licensed under <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a>. Made with <a href="http://logomakr.com" title="Logo Maker">Logo Maker</a>
