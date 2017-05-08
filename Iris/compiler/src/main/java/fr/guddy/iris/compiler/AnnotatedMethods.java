package fr.guddy.iris.compiler;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public final class AnnotatedMethods {
    //region Fields
    private final RoundEnvironment mRoundEnvironment;
    //endregion

    //region Constructor
    public AnnotatedMethods(final RoundEnvironment pRoundEnvironment) {
        mRoundEnvironment = pRoundEnvironment;
    }
    //endregion

    //region Visible API
    public AnnotatedClasses enclosingClasses() {
        Set<? extends Element> lElements = new HashSet<>();

        lElements = Sets.union(lElements, mRoundEnvironment.getElementsAnnotatedWith(DELETE.class));
        lElements = Sets.union(lElements, mRoundEnvironment.getElementsAnnotatedWith(GET.class));
        lElements = Sets.union(lElements, mRoundEnvironment.getElementsAnnotatedWith(HEAD.class));
        lElements = Sets.union(lElements, mRoundEnvironment.getElementsAnnotatedWith(HTTP.class));
        lElements = Sets.union(lElements, mRoundEnvironment.getElementsAnnotatedWith(PATCH.class));
        lElements = Sets.union(lElements, mRoundEnvironment.getElementsAnnotatedWith(POST.class));
        lElements = Sets.union(lElements, mRoundEnvironment.getElementsAnnotatedWith(PUT.class));

        final AnnotatedClasses lAnnotatedClasses = new AnnotatedClasses();

        for (final Element lElement : lElements) {
            if (lElement instanceof ExecutableElement) {
                final ExecutableElement lMethod = (ExecutableElement) lElement;
                final TypeElement lClass = (TypeElement) lMethod.getEnclosingElement();
                lAnnotatedClasses.appendMethodToClass(lClass, lMethod);
            }
        }

        return lAnnotatedClasses;
    }
    //endregion

}
