package fr.guddy.iris.compiler;

import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public final class GeneratedClasses {
    //region Fields
    private final TypeElement mEnclosingClass;
    private final List<ExecutableElement> mMethodElements;
    //endregion

    //region Constructor
    public GeneratedClasses(final TypeElement pEnclosingClass, final List<ExecutableElement> pMethodElements) {
        mEnclosingClass = pEnclosingClass;
        mMethodElements = pMethodElements;
    }
    //endregion

    //region Visible API
    public List<TypeSpec> buildTypeSpec() {
        final List<TypeSpec> lTypeSpecs = new ArrayList<>();

        final GeneratedQueryFactory lGeneratedQueryFactory = new GeneratedQueryFactory(mEnclosingClass);

        for (final ExecutableElement lMethod: mMethodElements) {
            final GeneratedQueryClass lGeneratedQueryClass = new GeneratedQueryClass(lMethod);
            lGeneratedQueryFactory.addQueryClass(lGeneratedQueryClass);
            lTypeSpecs.add(lGeneratedQueryClass.buildTypeSpec());
        }

        lTypeSpecs.add(lGeneratedQueryFactory.buildTypeSpec());

        return lTypeSpecs;
    }

    public String packageName() {
        final String lQualifiedName = mEnclosingClass.getQualifiedName().toString();
        return lQualifiedName.substring(0, lQualifiedName.lastIndexOf("."));
    }
    //endregion
}
