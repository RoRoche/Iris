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
import javax.management.Query;

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
        lAnnotations.add(Query.class.getCanonicalName());
        return lAnnotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    //endregion

    //region AbstractProcessor method to implement
    @Override
    public boolean process(final Set<? extends TypeElement> pAnnotations, final RoundEnvironment pRoundEnv) {
        //  TODO
        return false;
    }
    //endregion
}
