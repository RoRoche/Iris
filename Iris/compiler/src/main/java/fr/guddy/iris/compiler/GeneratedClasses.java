package fr.guddy.iris.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

public final class GeneratedClasses {
    //region Fields
    private final TypeElement mClassElement;
    private final List<ExecutableElement> mMethodElements;
    //endregion

    //region Constructor
    public GeneratedClasses(final TypeElement pClassElement, final List<ExecutableElement> pMethodElements) {
        mClassElement = pClassElement;
        mMethodElements = pMethodElements;
    }
    //endregion

    //region Specific job
    private void appendParametersAsFinalPublicFields(final TypeSpec.Builder pClassBuilder, final ExecutableElement pMethod) {
        for (final VariableElement lParamElement : pMethod.getParameters()) {
            final FieldSpec lFieldSpec = FieldSpec.builder(
                    TypeName.get(lParamElement.asType()),
                    lParamElement.getSimpleName().toString(),
                    Modifier.PUBLIC,
                    Modifier.FINAL)
                    .build();
            pClassBuilder.addField(lFieldSpec);
        }
    }

    private void appendResultAsFieldAndGetter(final TypeSpec.Builder pClassBuilder, final ExecutableElement pMethod) {
        // Field
        final DeclaredType lReturnType = (DeclaredType)pMethod.getReturnType();
        final TypeMirror lTypeMirror = lReturnType.getTypeArguments().get(0);
        final FieldSpec lFieldSpec = FieldSpec.builder(
                TypeName.get(lTypeMirror),
                "mResult",
                Modifier.PRIVATE, Modifier.TRANSIENT)
                .build();
        pClassBuilder.addField(lFieldSpec);

        // Getter
        final MethodSpec.Builder lConstructorSpec = MethodSpec.methodBuilder("getResult")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(lTypeMirror))
                .addStatement("return mResult");

        pClassBuilder.addMethod(lConstructorSpec.build());
    }

    private void appendConstructor(final TypeSpec.Builder pClassBuilder, final ExecutableElement pMethod) {
        final MethodSpec.Builder lConstructorSpec = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PROTECTED);

        final ParameterSpec lParameterSpec = ParameterSpec.builder(
                ClassName.get("com.birbit.android.jobqueue", "Params"),
                "params",
                Modifier.FINAL
        ).build();
        lConstructorSpec.addParameter(lParameterSpec);

        lConstructorSpec.addStatement("super($N)", "params");

        for (final VariableElement lParamElement : pMethod.getParameters()) {
            lConstructorSpec.addParameter(ParameterSpec.get(lParamElement));
            lConstructorSpec.addStatement("this.$N = $N", lParamElement.getSimpleName(), lParamElement.getSimpleName());
        }

        pClassBuilder.addMethod(lConstructorSpec.build());
    }

    private void appendOverriddenMethodExecute(final TypeSpec.Builder pClassBuilder, final ExecutableElement pMethod) {
        final MethodSpec.Builder lMethodSpec = MethodSpec.methodBuilder("execute")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addException(Throwable.class);

        final CodeBlock.Builder lCodeBlock = CodeBlock.builder();

        lCodeBlock.add("mResult = get$N().$N(", pMethod.getEnclosingElement().getSimpleName(), pMethod.getSimpleName());
        final Iterator<? extends VariableElement> lIterator = pMethod.getParameters().iterator();
        while (lIterator.hasNext()) {
            final VariableElement lParamElement = lIterator.next();
            lCodeBlock.add("$N", lParamElement.getSimpleName());
            if(lIterator.hasNext()) {
                lCodeBlock.add(", ");
            }
        }
        lCodeBlock.add(").execute().body();");

        lMethodSpec.addCode(lCodeBlock.build());

        pClassBuilder.addMethod(lMethodSpec
                .build());
    }

    private void appendOverriddenMethodOnQueryDidFinish(final TypeSpec.Builder pClassBuilder) {
        pClassBuilder.addMethod(MethodSpec.methodBuilder("onQueryDidFinish")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .build());
    }

    private void appendAbstractMethodGetApiService(final TypeSpec.Builder pClassBuilder, final ExecutableElement pMethod) {
        pClassBuilder.addMethod(MethodSpec.methodBuilder(String.format("get%s", pMethod.getEnclosingElement().getSimpleName()))
                .addModifiers(Modifier.PROTECTED, Modifier.ABSTRACT)
                .returns(TypeName.get(pMethod.getEnclosingElement().asType()))
                .build());
    }

    private void appendEventClass(final String pClassName, final TypeSpec.Builder pClassBuilder, final ExecutableElement pMethod) {
        final String lClassName = String.format("EventQuery%sDidFinish", WordUtils.capitalize(pMethod.getSimpleName().toString()));
        final TypeSpec.Builder lClassBuilder = TypeSpec.classBuilder(lClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .superclass(ParameterizedTypeName.get(ClassName.get("fr.guddy.iris", "AbstractEventQueryDidFinish"), ClassName.bestGuess(pClassName)));

        appendEventClassConstructor(pClassName, lClassBuilder, pMethod);

        pClassBuilder.addType(lClassBuilder.build());
    }

    private void appendEventClassConstructor(final String pEnclosingClassName, final TypeSpec.Builder pClassBuilder, final ExecutableElement pMethod) {
        final MethodSpec.Builder lConstructorSpec = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        final ParameterSpec lParameterSpec = ParameterSpec.builder(
                ClassName.bestGuess(pEnclosingClassName),
                "query",
                Modifier.FINAL
        ).build();
        lConstructorSpec.addParameter(lParameterSpec);

        lConstructorSpec.addStatement("super($N)", "query");

        pClassBuilder.addMethod(lConstructorSpec.build());
    }
    //endregion

    //region Visible API
    public List<TypeSpec> buildTypeSpec() {
        final List<TypeSpec> lTypeSpecs = new ArrayList<>();

        for (final ExecutableElement lMethod: mMethodElements) {
            final String lClassName = String.format("AbstractQuery%s", WordUtils.capitalize(lMethod.getSimpleName().toString()));
            final TypeSpec.Builder lClassBuilder = TypeSpec.classBuilder(lClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .superclass(ClassName.get("fr.guddy.iris", "AbstractQuery"));

            appendParametersAsFinalPublicFields(lClassBuilder, lMethod);
            appendResultAsFieldAndGetter(lClassBuilder, lMethod);

            appendConstructor(lClassBuilder, lMethod);

            appendOverriddenMethodExecute(lClassBuilder, lMethod);
            appendOverriddenMethodOnQueryDidFinish(lClassBuilder);

            appendAbstractMethodGetApiService(lClassBuilder, lMethod);

            appendEventClass(lClassName, lClassBuilder, lMethod);

            lTypeSpecs.add(lClassBuilder.build());
        }

        return lTypeSpecs;
    }

    public String packageName() {
        final String lQualifiedName = mClassElement.getQualifiedName().toString();
        return lQualifiedName.substring(0, lQualifiedName.lastIndexOf("."));
    }
    //endregion
}
