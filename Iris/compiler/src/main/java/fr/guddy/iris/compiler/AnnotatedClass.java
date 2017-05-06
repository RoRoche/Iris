package fr.guddy.iris.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public final class AnnotatedClass {
    //region Fields
    private final TypeElement mEnclosingClass;
    private final List<ExecutableElement> mMethods;
    //endregion

    //region Constructor
    public AnnotatedClass(final TypeElement pEnclosingClass) {
        this(pEnclosingClass, new ArrayList<ExecutableElement>());
    }

    public AnnotatedClass(final TypeElement pEnclosingClass, final List<ExecutableElement> pMethods) {
        mEnclosingClass = pEnclosingClass;
        mMethods = pMethods;
    }
    //endregion

    //region Visible API
    public TypeElement enclosingClass() {
        return mEnclosingClass;
    }

    public void addMethod(final ExecutableElement pMethod) {
        mMethods.add(pMethod);
    }

    public void writeInto(final Filer pFiler, final Messager pMessager) {
        // prepare generated class
        final GeneratedClasses lGeneratedClass = new GeneratedClasses(mEnclosingClass, mMethods);

        final List<TypeSpec> lTypeSpecGeneratedClasses = lGeneratedClass.buildTypeSpec();

        for (final TypeSpec lTypeSpec: lTypeSpecGeneratedClasses) {
            final String lPackageName = lGeneratedClass.packageName();

            // create generated class to a file
            try {
                JavaFile.builder(lPackageName, lTypeSpec)
                        .build()
                        .writeTo(pFiler);
            } catch (IOException pE) {
                logError(pMessager, mEnclosingClass, "error while writing generated class");
            }
        }
    }
    //endregion

    //region Specific job
    private void logError(final Messager pMessager, final Element pElement, final String pMessage, final Object... pArgs) {
        pMessager.printMessage(Diagnostic.Kind.ERROR, String.format(pMessage, pArgs), pElement);
    }
    //endregion
}
