package com.faendir.minecraft.hadron.processor.util;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lukas
 * @since 25.07.19
 */
public class ExpandedAnnotationMirror {
    private final AnnotationMirror mirror;
    private final Element owner;
    private final Map<String, Object> replacements;

    public ExpandedAnnotationMirror(AnnotationMirror mirror, Element owner) {
        this.mirror = mirror;
        this.owner = owner;
        this.replacements = new HashMap<>();
    }

    public void putReplacement(String method, Object value) {
        replacements.put(method, value);
    }

    public <T> T getReplacement(String method, T defaultValue) {
        //noinspection unchecked
        return (T) replacements.getOrDefault(method, defaultValue);
    }

    public AnnotationMirror getMirror() {
        return mirror;
    }

    public Element getOwner() {
        return owner;
    }

    Map<String, Object> getReplacements() {
        return replacements;
    }

    @Override
    public String toString() {
        String s = mirror.toString();
        for (Map.Entry<String, Object> e : replacements.entrySet()) {
            String replacement = e.getKey() + "=" + (e.getValue() instanceof String ? "\"" + e.getValue() + "\"" : e.getValue());
            s = s.replaceAll(e.getKey() + "=\"[^\"]*\"", replacement);
        }
        return s;
    }
}
