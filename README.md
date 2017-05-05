# Iris

Android annotation processor to perform network queries

**STILL UNDER DEVELOPMENT**

## Annotations

- `Query`: annotate a query class
    - generate an associated event class
    - add the query to the generated query factory
- `Result`: annotate fields of a query class
    - populate the associated event class with the considered field

## Benefits

- `AbstractQuery` is usable standalone (i.e., can be subclassed and used without `@Query` and `@Result`)
- default behavior of the `AbstractQuery` class can be overridden
- Event-bus ready
- DI ready

## Logo credits

Nature graphic by <a href="http://www.flaticon.com/authors/freepik">Freepik</a> from <a href="http://www.flaticon.com/">Flaticon</a> is licensed under <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a>. Made with <a href="http://logomakr.com" title="Logo Maker">Logo Maker</a>
