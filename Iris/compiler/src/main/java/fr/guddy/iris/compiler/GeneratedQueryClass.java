package fr.guddy.iris.compiler;

import android.support.annotation.NonNull;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.lang3.text.WordUtils;

import java.util.Iterator;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

public class GeneratedQueryClass {
    //region Fields
    private final ExecutableElement mMethod;
    private final String mClassName;
    private final TypeSpec.Builder mClassBuilder;
    private final String mEventClassName;
    private final TypeName mResultType;
    //endregion

    //region Constructor
    public GeneratedQueryClass(final ExecutableElement pMethod) {
        mMethod = pMethod;
        mClassName = String.format("AbstractQuery%s", WordUtils.capitalize(pMethod.getSimpleName().toString()));
        mEventClassName = String.format("EventQuery%sDidFinish", WordUtils.capitalize(mMethod.getSimpleName().toString()));
        mResultType = resultType(mMethod);

        final ParameterizedTypeName lSuperClass = ParameterizedTypeName.get(ClassName.get("fr.guddy.iris", "AbstractQuery"), resultType(pMethod));

        mClassBuilder = TypeSpec.classBuilder(mClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .superclass(lSuperClass);
    }
    //endregion

    //region Visible API
    public String getClassName() {
        return mClassName;
    }

    public TypeSpec buildTypeSpec() {
        appendParametersAsFinalPublicFields();
        appendResultAsFieldAndGetter();

        appendConstructor();

        appendOverriddenMethodExecute();
        appendOverriddenMethodOnQueryDidFinish();

        appendAbstractMethodGetApiService();

        appendEventClass();
        return mClassBuilder.build();
    }
    //endregion

    //region Specific job
    private TypeName resultType(final ExecutableElement pMethod) {
        final DeclaredType lReturnType = (DeclaredType) pMethod.getReturnType();
        final TypeMirror lTypeMirror = lReturnType.getTypeArguments().get(0);
        return TypeName.get(lTypeMirror);
    }

    private void appendParametersAsFinalPublicFields() {
        for (final VariableElement lParamElement : mMethod.getParameters()) {
            final FieldSpec lFieldSpec = FieldSpec.builder(
                    TypeName.get(lParamElement.asType()),
                    lParamElement.getSimpleName().toString(),
                    Modifier.PUBLIC,
                    Modifier.FINAL)
                    .build();
            mClassBuilder.addField(lFieldSpec);
        }
    }

    private void appendResultAsFieldAndGetter() {
        if(mResultType.equals(TypeName.VOID.box())) {
            return;
        }

        // Field
        final FieldSpec lFieldSpec = FieldSpec.builder(
                mResultType,
                "mResult",
                Modifier.PRIVATE, Modifier.TRANSIENT)
                .build();
        mClassBuilder.addField(lFieldSpec);

        // Getter
        final MethodSpec.Builder lConstructorSpec = MethodSpec.methodBuilder("getResult")
                .addModifiers(Modifier.PUBLIC)
                .returns(mResultType)
                .addStatement("return mResult");

        mClassBuilder.addMethod(lConstructorSpec.build());
    }

    private void appendConstructor() {
        final MethodSpec.Builder lConstructorSpec = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PROTECTED);

        final ParameterSpec lParameterSpec = ParameterSpec.builder(
                ClassName.get("com.birbit.android.jobqueue", "Params"),
                "params",
                Modifier.FINAL).addAnnotation(NonNull.class)
                .build();

        lConstructorSpec.addParameter(lParameterSpec);

        lConstructorSpec.addStatement("super($N)", "params");

        for (final VariableElement lParamElement : mMethod.getParameters()) {
            lConstructorSpec.addParameter(ParameterSpec.get(lParamElement));
            lConstructorSpec.addStatement("this.$N = $N", lParamElement.getSimpleName(), lParamElement.getSimpleName());
        }

        mClassBuilder.addMethod(lConstructorSpec.build());
    }

    private void appendOverriddenMethodExecute() {
        final MethodSpec.Builder lMethodSpec = MethodSpec.methodBuilder("execute")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addException(Throwable.class);

        final CodeBlock.Builder lCodeBlock = CodeBlock.builder();

        lCodeBlock.add("mResponse = get$N().$N(", mMethod.getEnclosingElement().getSimpleName(), mMethod.getSimpleName());
        final Iterator<? extends VariableElement> lIterator = mMethod.getParameters().iterator();
        while (lIterator.hasNext()) {
            final VariableElement lParamElement = lIterator.next();
            lCodeBlock.add("$N", lParamElement.getSimpleName());
            if (lIterator.hasNext()) {
                lCodeBlock.add(", ");
            }
        }
        lCodeBlock.add(").execute();\n");
        if(mResultType.equals(TypeName.VOID.box())) {
            lCodeBlock.addStatement("mResponse.body()");
        } else {
            lCodeBlock.addStatement("mResult = mResponse.body()");
        }

        lMethodSpec.addCode(lCodeBlock.build());

        mClassBuilder.addMethod(lMethodSpec
                .build());
    }

    private void appendOverriddenMethodOnQueryDidFinish() {
        mClassBuilder.addMethod(MethodSpec.methodBuilder("onQueryDidFinish")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .build());
    }

    private void appendAbstractMethodGetApiService() {
        mClassBuilder.addMethod(MethodSpec.methodBuilder(String.format("get%s", mMethod.getEnclosingElement().getSimpleName()))
                .addModifiers(Modifier.PROTECTED, Modifier.ABSTRACT)
                .returns(TypeName.get(mMethod.getEnclosingElement().asType()))
                .build());
    }

    private void appendEventClass() {
        final ParameterizedTypeName lSuperClass = ParameterizedTypeName.get(ClassName.get("fr.guddy.iris", "AbstractEventQueryDidFinish"), ClassName.bestGuess(mClassName));
        final TypeSpec.Builder lClassBuilder = TypeSpec.classBuilder(mEventClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .superclass(lSuperClass);

        final MethodSpec.Builder lConstructorSpec = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        final ParameterSpec lParameterSpec = ParameterSpec.builder(
                ClassName.bestGuess(mClassName),
                "query",
                Modifier.FINAL)
                .addAnnotation(NonNull.class)
                .build();
        lConstructorSpec.addParameter(lParameterSpec);

        lConstructorSpec.addStatement("super($N)", "query");

        lClassBuilder.addMethod(lConstructorSpec.build());

        mClassBuilder.addType(lClassBuilder.build());
    }
    //endregion
}
