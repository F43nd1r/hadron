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

    public AnnotationMirror getMirror() {
        return mirror;
    }

    public Element getOwner() {
        return owner;
    }

    Map<String, Object> getReplacements() {
        return replacements;
    }
}
