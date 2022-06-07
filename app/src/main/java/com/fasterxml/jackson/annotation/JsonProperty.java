package com.fasterxml.jackson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotation
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes.dex */
public @interface JsonProperty {
    public static final int INDEX_UNKNOWN = -1;
    public static final String USE_DEFAULT_NAME = "";

    /* loaded from: classes.dex */
    public enum Access {
        AUTO,
        READ_ONLY,
        WRITE_ONLY,
        READ_WRITE
    }

    Access access() default Access.AUTO;

    String defaultValue() default "";

    int index() default -1;

    boolean required() default false;

    String value() default "";
}
