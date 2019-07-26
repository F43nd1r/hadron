package com.faendir.minecraft.hadron.processor.processors;

import com.faendir.minecraft.hadron.processor.util.AnnotationProxyUtils;
import com.faendir.minecraft.hadron.processor.util.ExpandedAnnotationMirror;
import com.google.common.collect.Multimap;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lukas
 * @since 01.07.19
 */
public abstract class BaseProcessor {
    private static final Collection<Modifier> PSF_MODIFIERS = Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
    protected final ProcessingEnvironment processingEnv;

    public BaseProcessor(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public abstract void process(AnnotatedElementSupplier supplier, RoundEnvironment roundEnv, TypeSpec.Builder modObjects) throws Exception;

    public static class AnnotatedElementSupplier {
        private final ProcessingEnvironment processingEnv;
        private final Multimap<Element, ExpandedAnnotationMirror> elements;

        public AnnotatedElementSupplier(ProcessingEnvironment processingEnv, Multimap<Element, ExpandedAnnotationMirror> elements) {
            this.processingEnv = processingEnv;
            this.elements = elements;
        }

        public <T extends Annotation> List<Pair<Element, T>> getElementsAnnotatedWith(Class<T> clazz) {
            return elements.entries().stream()
                    .filter(e -> e.getValue().getMirror().getAnnotationType().toString().equals(clazz.getName().replace('$', '.')))
                    .filter(e -> {
                        if (e.getKey().getKind() != ElementKind.FIELD || e.getKey().getModifiers().containsAll(PSF_MODIFIERS)) {
                            return true;
                        } else {
                            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                    "Elements annotated with @" + e.getValue().getMirror().getAnnotationType().asElement().getSimpleName() + " have to be public static final", e.getKey());
                            return false;
                        }
                    })
                    .map(e -> Pair.of(e.getKey(), AnnotationProxyUtils.getAnnotationProxy(clazz, e.getValue())))
                    .collect(Collectors.toList());
        }

        public <T extends Annotation, U extends Annotation> List<Pair<Element, T>> getElementsAnnotatedWithRepeatable(Class<T> clazz, Class<U> repeatable) {
            Method valueMethod;
            try {
                valueMethod = repeatable.getMethod("value");
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            return Stream.concat(getElementsAnnotatedWith(clazz).stream().map(e -> Pair.of(e.getKey(), e.getValue())),
                    getElementsAnnotatedWith(repeatable).stream().flatMap(e -> {
                        try {
                            //noinspection unchecked
                            return Stream.of((T[]) valueMethod.invoke(e.getValue())).map(v -> Pair.of(e.getKey(), v));
                        } catch (IllegalAccessException | InvocationTargetException ex) {
                            ex.printStackTrace();
                            return Stream.empty();
                        }
                    })).collect(Collectors.toList());
        }
    }
}
