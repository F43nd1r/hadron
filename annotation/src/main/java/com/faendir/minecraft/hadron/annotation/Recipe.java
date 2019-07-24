package com.faendir.minecraft.hadron.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lukas
 * @since 01.07.19
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface Recipe {

    @interface Key {
        String key();
        String value();
    }

    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
    @interface Shaped {
        String[] pattern();
        Key[] keys();
        String id();
        int count() default 1;
    }

    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
    @interface Shapeless {
        String[] ingredients();
        String id();
        int count() default 1;
    }
}
