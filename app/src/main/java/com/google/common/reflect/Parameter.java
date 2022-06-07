package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public final class Parameter implements AnnotatedElement {
    private final Invokable<?, ?> a;
    private final int b;
    private final TypeToken<?> c;
    private final ImmutableList<Annotation> d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Parameter(Invokable<?, ?> invokable, int i, TypeToken<?> typeToken, Annotation[] annotationArr) {
        this.a = invokable;
        this.b = i;
        this.c = typeToken;
        this.d = ImmutableList.copyOf(annotationArr);
    }

    public TypeToken<?> getType() {
        return this.c;
    }

    public Invokable<?, ?> getDeclaringInvokable() {
        return this.a;
    }

    @Override // java.lang.reflect.AnnotatedElement
    public boolean isAnnotationPresent(Class<? extends Annotation> cls) {
        return getAnnotation(cls) != null;
    }

    @Override // java.lang.reflect.AnnotatedElement
    @NullableDecl
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        Preconditions.checkNotNull(cls);
        UnmodifiableIterator<Annotation> it = this.d.iterator();
        while (it.hasNext()) {
            Annotation next = it.next();
            if (cls.isInstance(next)) {
                return cls.cast(next);
            }
        }
        return null;
    }

    @Override // java.lang.reflect.AnnotatedElement
    public Annotation[] getAnnotations() {
        return getDeclaredAnnotations();
    }

    @Override // java.lang.reflect.AnnotatedElement
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> cls) {
        return (A[]) getDeclaredAnnotationsByType(cls);
    }

    @Override // java.lang.reflect.AnnotatedElement
    public Annotation[] getDeclaredAnnotations() {
        ImmutableList<Annotation> immutableList = this.d;
        return (Annotation[]) immutableList.toArray(new Annotation[immutableList.size()]);
    }

    @Override // java.lang.reflect.AnnotatedElement
    @NullableDecl
    public <A extends Annotation> A getDeclaredAnnotation(Class<A> cls) {
        Preconditions.checkNotNull(cls);
        return (A) ((Annotation) FluentIterable.from(this.d).filter(cls).first().orNull());
    }

    @Override // java.lang.reflect.AnnotatedElement
    public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> cls) {
        return (A[]) ((Annotation[]) FluentIterable.from(this.d).filter(cls).toArray(cls));
    }

    public boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof Parameter)) {
            return false;
        }
        Parameter parameter = (Parameter) obj;
        return this.b == parameter.b && this.a.equals(parameter.a);
    }

    public int hashCode() {
        return this.b;
    }

    public String toString() {
        return this.c + " arg" + this.b;
    }
}
