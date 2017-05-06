# Iris

Convenient wrapper library to perform network queries using Retrofit and Android Priority Job Queue (Job Manager)

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
}
```

## How to use

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

## Logo credits

Nature graphic by <a href="http://www.flaticon.com/authors/freepik">Freepik</a> from <a href="http://www.flaticon.com/">Flaticon</a> is licensed under <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a>. Made with <a href="http://logomakr.com" title="Logo Maker">Logo Maker</a>
