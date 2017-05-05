package fr.guddy.iris.test;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import javax.tools.JavaFileObject;

import fr.guddy.iris.compiler.IrisProcessor;

import static com.google.common.truth.Truth.assert_;

public class IrisProcessorTest {
    @Test
    public void no_visible_constructor() throws IOException {
        final JavaFileObject lInput = JavaFileObjects.forResource("assets/NoVisibleConstructor.java");

        assert_()
                .about(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(lInput))
                .processedWith(new IrisProcessor())
                .failsToCompile()
                .withErrorContaining("no visible constructor");
    }

    @Test
    public void query_with_param_and_result() {
        final JavaFileObject lInput = JavaFileObjects.forResource("assets/QueryWithParamAndResult.java");
        final JavaFileObject lOutput = JavaFileObjects.forResource("assets/EventQueryWithParamAndResultDidFinish.java");

        assert_()
                .about(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(lInput))
                .processedWith(new IrisProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(lOutput);
    }

    @Test
    public void query_with_param_and_no_result() {
        final JavaFileObject lInput = JavaFileObjects.forResource("assets/QueryWithParamAndNoResult.java");
        final JavaFileObject lOutput = JavaFileObjects.forResource("assets/EventQueryWithParamAndNoResultDidFinish.java");

        assert_()
                .about(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(lInput))
                .processedWith(new IrisProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(lOutput);
    }

    @Test
    public void query_with_no_param() {
        final JavaFileObject lInput = JavaFileObjects.forResource("assets/QueryWithNoParam.java");
        final JavaFileObject lOutput = JavaFileObjects.forResource("assets/EventQueryWithNoParamDidFinish.java");

        assert_()
                .about(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(lInput))
                .processedWith(new IrisProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(lOutput);
    }

    @Test
    public void query_with_multiple_results() {
        final JavaFileObject lInput = JavaFileObjects.forResource("assets/QueryWithMultipleResults.java");
        final JavaFileObject lOutput = JavaFileObjects.forResource("assets/EventQueryWithMultipleResultsDidFinish.java");

        assert_()
                .about(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(lInput))
                .processedWith(new IrisProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(lOutput);
    }

    @Test
    public void query_with_generics() {
        final JavaFileObject lInput = JavaFileObjects.forResource("assets/QueryWithGenerics.java");
        final JavaFileObject lOutput = JavaFileObjects.forResource("assets/EventQueryWithGenericsDidFinish.java");

        assert_()
                .about(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(lInput))
                .processedWith(new IrisProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(lOutput);
    }
}
