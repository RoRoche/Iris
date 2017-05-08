package fr.guddy.iris.compiler;

import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

@AutoService(Processor.class)
public class IrisProcessor extends AbstractProcessor {
    //region Fields
    private Messager mMessager;
    private Filer mFiler;
    //endregion

    //region Overridden methods
    @Override
    public synchronized void init(final ProcessingEnvironment pProcessingEnvironment) {
        super.init(pProcessingEnvironment);
        mMessager = pProcessingEnvironment.getMessager();
        mFiler = pProcessingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> lAnnotations = new HashSet<>();
        lAnnotations.add(DELETE.class.getCanonicalName());
        lAnnotations.add(GET.class.getCanonicalName());
        lAnnotations.add(HEAD.class.getCanonicalName());
        lAnnotations.add(HTTP.class.getCanonicalName());
        lAnnotations.add(PATCH.class.getCanonicalName());
        lAnnotations.add(POST.class.getCanonicalName());
        lAnnotations.add(PUT.class.getCanonicalName());
        return lAnnotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    //endregion

    //region abstract methods declared in AbstractProcessor
    @Override
    public boolean process(final Set<? extends TypeElement> pSet, final RoundEnvironment pRoundEnvironment) {
        final AnnotatedClasses lAnnotatedClasses = new AnnotatedMethods(pRoundEnvironment).enclosingClasses();

        for (final AnnotatedClass lClass : lAnnotatedClasses) {
            lClass.writeInto(mFiler, mMessager);
        }

        return false;
    }
    //endregion
}
