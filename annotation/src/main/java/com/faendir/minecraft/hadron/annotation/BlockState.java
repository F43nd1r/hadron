package com.faendir.minecraft.hadron.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lukas
 * @since 01.07.19
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface BlockState {
    String id();
    Variant[] variants() default {};
    Multipart[] multipart() default {};


    @interface Variant {
        String id();
        Model model();
    }

    @interface Model {
        String value() default "";
        int x() default 0;
        int y() default 0;
        boolean uvlock() default false;
    }

    @interface Multipart {
        Model apply();
        Conditions[] when() default {};

    }

    @interface Conditions {
       Condition[] value();
    }

    @interface Condition {
        String name();
        String value();
    }
}
