package com.faendir.minecraft.hadron.processor;

import com.faendir.minecraft.hadron.annotation.Composite;
import com.faendir.minecraft.hadron.processor.processors.BaseProcessor;
import com.faendir.minecraft.hadron.processor.processors.BlockStateProcessor;
import com.faendir.minecraft.hadron.processor.processors.GenerateItemProcessor;
import com.faendir.minecraft.hadron.processor.processors.GenerateSlabsProcessor;
import com.faendir.minecraft.hadron.processor.processors.GenerateStairsProcessor;
import com.faendir.minecraft.hadron.processor.processors.GenerateWallProcessor;
import com.faendir.minecraft.hadron.processor.processors.ModelProcessor;
import com.faendir.minecraft.hadron.processor.processors.RecipeProcessor;
import com.faendir.minecraft.hadron.processor.processors.RegisterProcessor;
import com.faendir.minecraft.hadron.processor.processors.TagProcessor;
import com.google.auto.service.AutoService;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lukas
 * @since 01.07.19
 */
@AutoService(javax.annotation.processing.Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class Processor extends AbstractProcessor {
    private List<BaseProcessor> processors = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        processors.add(new RegisterProcessor(processingEnv));
        processors.add(new GenerateItemProcessor(processingEnv));
        processors.add(new RecipeProcessor(processingEnv));
        processors.add(new GenerateStairsProcessor(processingEnv));
        processors.add(new BlockStateProcessor(processingEnv));
        processors.add(new ModelProcessor(processingEnv));
        processors.add(new GenerateSlabsProcessor(processingEnv));
        processors.add(new GenerateWallProcessor(processingEnv));
        processors.add(new TagProcessor(processingEnv));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            Multimap<Element, ExpandedAnnotationMirror> elements = HashMultimap.create();
            for (Element e : roundEnv.getElementsAnnotatedWithAny(annotations.toArray(new TypeElement[0]))) {
                List<AnnotationMirror> toProcess = new ArrayList<>(e.getAnnotationMirrors());
                while (!toProcess.isEmpty()) {
                    AnnotationMirror process = toProcess.remove(0);
                    if (elements.entries().stream().noneMatch(entry -> entry.getKey().equals(e) && entry.getValue().getMirror().equals(process))) {
                        elements.put(e, new ExpandedAnnotationMirror(process));
                        if (process.getAnnotationType().getAnnotation(Composite.class) != null) {
                            Map<String, String> replacements = process.getElementValues().entrySet().stream()
                                    .filter(entry -> entry.getKey().getReturnType().toString().equals(String.class.getName()))
                                    .collect(Collectors.toMap(entry -> "{" + entry.getKey().getSimpleName() + "}", entry -> (String) entry.getValue().getValue()));
                            List<? extends AnnotationMirror> toExpand = process.getAnnotationType().getAnnotationMirrors();
                            for (AnnotationMirror expand : toExpand) {
                                ExpandedAnnotationMirror expanded = new ExpandedAnnotationMirror(expand);
                                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : expand.getElementValues().entrySet()) {
                                    if (entry.getKey().getReturnType().toString().equals(String.class.getName())) {
                                        String value = (String) entry.getValue().getValue();
                                        for (Map.Entry<String, String> replacement : replacements.entrySet()) {
                                            value = value.replace(replacement.getKey(), replacement.getValue());
                                        }
                                        expanded.putReplacement(entry.getKey().getSimpleName().toString(), value);
                                    }
                                }
                                toProcess.add(expanded.getMirror());
                            }
                        }
                    }
                }
            }
            BaseProcessor.AnnotatedElementSupplier supplier = new BaseProcessor.AnnotatedElementSupplier() {
                @Override
                public <T extends Annotation> Map<Element, T> getElementsAnnotatedWith(Class<T> clazz) {
                    return elements.entries().stream().filter(e -> e.getValue().getMirror().getAnnotationType().toString().equals(clazz.getName()))
                            .collect(Collectors.toMap(Map.Entry::getKey, e -> clazz.cast(Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{clazz},
                                    new AnnotationInvocationHandler<>(e.getKey().getAnnotation(clazz), e.getValue().getReplacements())))));
                }
            };
            for (BaseProcessor processor : processors) {
                processor.process(supplier, roundEnv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static class ExpandedAnnotationMirror {
        private final AnnotationMirror mirror;
        private final Map<String, String> replacements;

        private ExpandedAnnotationMirror(AnnotationMirror mirror) {
            this.mirror = mirror;
            this.replacements = new HashMap<>();
        }

        void putReplacement(String method, String value) {
            replacements.put(method, value);
        }

        AnnotationMirror getMirror() {
            return mirror;
        }

        Map<String, String> getReplacements() {
            return replacements;
        }
    }

    private static class AnnotationInvocationHandler<T> implements InvocationHandler {

        private final T annotation;
        private final Map<String, String> replacements;

        AnnotationInvocationHandler(T annotation, Map<String, String> replacements) {
            this.annotation = annotation;
            this.replacements = replacements;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getReturnType().equals(String.class) && replacements.containsKey(method.getName())) {
                return replacements.get(method.getName());
            } else {
                try {
                    return method.invoke(annotation, args);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
        }
    }
}
