package com.faendir.minecraft.hadron.processor.processors;

import com.squareup.javapoet.TypeSpec;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author lukas
 * @since 01.07.19
 */
public abstract class BaseProcessor {
    protected final ProcessingEnvironment processingEnv;

    public BaseProcessor(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public abstract void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder registry) throws Exception;

    public interface AnnotatedElementSupplier {
        <T extends Annotation> List<Pair<Element, T>> getElementsAnnotatedWith(Class<T> clazz);
        <T extends Annotation, U extends Annotation> List<Pair<Element, T>> getElementsAnnotatedWithRepeatable(Class<T> clazz, Class<U> repeatable);
    }
}
