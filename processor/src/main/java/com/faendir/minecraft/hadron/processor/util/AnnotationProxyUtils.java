package com.faendir.minecraft.hadron.processor.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author lukas
 * @since 25.07.19
 */
public class AnnotationProxyUtils {

    public static <T extends Annotation> T getAnnotationProxy(Class<T> clazz, ExpandedAnnotationMirror mirror) {
        return getAnnotationProxy(clazz, mirror.getOwner().getAnnotation(clazz), mirror);
    }

    private static <T extends Annotation> T getAnnotationProxy(Class<T> clazz, T annotation, ExpandedAnnotationMirror mirror) {
        return clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new AnnotationInvocationHandler<>(annotation, mirror.getReplacements())));
    }

    private static class AnnotationInvocationHandler<T> implements InvocationHandler {

        private final T annotation;
        private final Map<String, Object> replacements;

        AnnotationInvocationHandler(T annotation, Map<String, Object> replacements) {
            this.annotation = annotation;
            this.replacements = replacements;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (replacements.containsKey(method.getName())) {
                Object result = replacements.get(method.getName());
                if (result instanceof ExpandedAnnotationMirror) {
                    Annotation original = (Annotation) getOriginal(method, args);
                    result = getAnnotationProxy((Class<Annotation>) method.getReturnType(), original, (ExpandedAnnotationMirror) result);
                } else if (result instanceof ExpandedAnnotationMirror[]) {
                    Annotation[] original = (Annotation[]) getOriginal(method, args);
                    ExpandedAnnotationMirror[] mirrors = (ExpandedAnnotationMirror[]) result;
                    Annotation[] out = (Annotation[]) Array.newInstance(method.getReturnType().getComponentType(), original.length);
                    for (int i = 0; i < original.length; i++) {
                        out[i] = getAnnotationProxy((Class<Annotation>) ((Class<Annotation[]>) method.getReturnType()).getComponentType(), original[i], mirrors[i]);
                    }
                    result = out;
                }
                return result;
            } else {
                return getOriginal(method, args);
            }
        }

        private Object getOriginal(Method method, Object[] args) throws Throwable {
            try {
                return method.invoke(annotation, args);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }
}
