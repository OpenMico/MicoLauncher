package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public abstract class Annotated {
    @Deprecated
    public abstract Iterable<Annotation> annotations();

    public abstract boolean equals(Object obj);

    public abstract AnnotatedElement getAnnotated();

    public abstract <A extends Annotation> A getAnnotation(Class<A> cls);

    protected abstract int getModifiers();

    public abstract String getName();

    public abstract Class<?> getRawType();

    public abstract JavaType getType();

    public abstract boolean hasAnnotation(Class<?> cls);

    public abstract boolean hasOneOf(Class<? extends Annotation>[] clsArr);

    public abstract int hashCode();

    public abstract String toString();

    public boolean isPublic() {
        return Modifier.isPublic(getModifiers());
    }

    @Deprecated
    public final JavaType getType(TypeBindings typeBindings) {
        return getType();
    }

    @Deprecated
    public Type getGenericType() {
        return getRawType();
    }
}
