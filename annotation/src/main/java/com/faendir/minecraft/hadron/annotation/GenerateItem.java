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
public @interface GenerateItem {
    String value();

    /**
     * defaults to {@link #value()}
     *
     * @return the model parent
     */
    String parent() default "";

    String category() default "building_blocks";

    Texture[] textures() default {};
}
