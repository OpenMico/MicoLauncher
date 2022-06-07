package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public abstract class Java7Support {
    private static final Java7Support IMPL;

    public abstract PropertyName findConstructorName(AnnotatedParameter annotatedParameter);

    public abstract Boolean findTransient(Annotated annotated);

    public abstract Class<?> getClassJavaNioFilePath();

    public abstract JsonDeserializer<?> getDeserializerForJavaNioFilePath(Class<?> cls);

    public abstract JsonSerializer<?> getSerializerForJavaNioFilePath(Class<?> cls);

    public abstract Boolean hasCreatorAnnotation(Annotated annotated);

    static {
        Java7Support java7Support;
        try {
            java7Support = (Java7Support) ClassUtil.createInstance(Class.forName("com.fasterxml.jackson.databind.ext.Java7SupportImpl"), false);
        } catch (Throwable unused) {
            Logger.getLogger(Java7Support.class.getName()).warning("Unable to load JDK7 types (annotations, java.nio.file.Path): no Java7 support added");
            java7Support = null;
        }
        IMPL = java7Support;
    }

    public static Java7Support instance() {
        return IMPL;
    }
}
