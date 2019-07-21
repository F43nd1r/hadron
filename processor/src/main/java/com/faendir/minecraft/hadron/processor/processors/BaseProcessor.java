package com.faendir.minecraft.hadron.processor.processors;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author lukas
 * @since 01.07.19
 */
public abstract class BaseProcessor {
    protected final ProcessingEnvironment processingEnv;

    public BaseProcessor(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public abstract void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv) throws Exception;

    @FunctionalInterface
    public interface AnnotatedElementSupplier {
        <T extends Annotation> Map<Element, T> getElementsAnnotatedWith(Class<T> clazz);
    }
}
