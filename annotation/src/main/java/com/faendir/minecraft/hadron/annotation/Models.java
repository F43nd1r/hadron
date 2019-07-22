package com.faendir.minecraft.hadron.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lukas
 * @since 02.07.19
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Models {
    Model[] value();
}
