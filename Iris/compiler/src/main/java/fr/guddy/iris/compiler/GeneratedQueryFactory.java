package fr.guddy.iris.compiler;

import android.support.annotation.NonNull;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class GeneratedQueryFactory {

    //region Fields
    private final TypeElement mEnclosingClass;
    private final List<GeneratedQueryClass> mQueryClasses;
    private final String mClassName;
    private final TypeSpec.Builder mClassBuilder;
    //endregion

    //region Constructor
    public GeneratedQueryFactory(final TypeElement pEnclosingClass) {
        mEnclosingClass = pEnclosingClass;
        mQueryClasses = new ArrayList<>();
        mClassName = String.format("%sQueryFactory", mEnclosingClass.getSimpleName().toString());
        final ClassName lSuperClass =ClassName.get("fr.guddy.iris", "AbstractQueryFactory");

        mClassBuilder = TypeSpec.classBuilder(mClassName)
                .addModifiers(Modifier.PUBLIC)
                .superclass(lSuperClass);
    }
    //endregion

    //region Specific job
    private void appendConstructor() {
        final MethodSpec.Builder lConstructorSpec = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        final ParameterSpec lParameterSpec1 = ParameterSpec.builder(
                ClassName.get("com.birbit.android.jobqueue", "JobManager"),
                "pJobManager",
                Modifier.FINAL)
                .addAnnotation(NonNull.class)
                .build();
        lConstructorSpec.addParameter(lParameterSpec1);

        final ParameterSpec lParameterSpec2 = ParameterSpec.builder(
                ClassName.get("com.novoda.merlin", "MerlinsBeard"),
                "pMerlinsBeard",
                Modifier.FINAL)
                .addAnnotation(NonNull.class)
                .build();
        lConstructorSpec.addParameter(lParameterSpec2);

        lConstructorSpec.addStatement("super($N, $N)", "pJobManager", "pMerlinsBeard");

        mClassBuilder.addMethod(lConstructorSpec.build());
    }

    private void appendStartMethods() {
        for (final GeneratedQueryClass lQueryClass: mQueryClasses) {
            final MethodSpec.Builder lMethodSpec = MethodSpec.methodBuilder(String.format("start%s", lQueryClass.getClassName()))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ParameterSpec.builder(ClassName.bestGuess(lQueryClass.getClassName()), "pQuery", Modifier.FINAL).addAnnotation(NonNull.class).build())
                    .addStatement("return startQuery(pQuery)")
                    .returns(TypeName.BOOLEAN);
            mClassBuilder.addMethod(lMethodSpec.build());
        }
    }
    //endregion

    //region Visible API
    public void addQueryClass(final GeneratedQueryClass pGeneratedQueryClass) {
        mQueryClasses.add(pGeneratedQueryClass);
    }

    public TypeSpec buildTypeSpec() {
        appendConstructor();
        appendStartMethods();
        return mClassBuilder.build();
    }
    //endregion
}
