package fr.guddy.iris.compiler;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import retrofit2.http.GET;

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
        final Set<? extends Element> lElements = mRoundEnvironment.getElementsAnnotatedWith(GET.class);

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
