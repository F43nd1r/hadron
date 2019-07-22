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
@Target(ElementType.TYPE)
public @interface Recipe {
    String[] pattern();
    Key[] keys();
    String id();
    int count() default 1;

    @interface Key {
        String key();
        String value();
    }
}
