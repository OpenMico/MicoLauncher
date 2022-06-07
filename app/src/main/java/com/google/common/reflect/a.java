package com.google.common.reflect;

import com.google.common.base.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Element.java */
/* loaded from: classes2.dex */
public class a extends AccessibleObject implements Member {
    private final AccessibleObject a;
    private final Member b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public <M extends AccessibleObject & Member> a(M m) {
        Preconditions.checkNotNull(m);
        this.a = m;
        this.b = m;
    }

    public TypeToken<?> getOwnerType() {
        return TypeToken.of((Class) getDeclaringClass());
    }

    @Override // java.lang.reflect.AccessibleObject, java.lang.reflect.AnnotatedElement
    public final boolean isAnnotationPresent(Class<? extends Annotation> cls) {
        return this.a.isAnnotationPresent(cls);
    }

    @Override // java.lang.reflect.AccessibleObject, java.lang.reflect.AnnotatedElement
    public final <A extends Annotation> A getAnnotation(Class<A> cls) {
        return (A) this.a.getAnnotation(cls);
    }

    @Override // java.lang.reflect.AccessibleObject, java.lang.reflect.AnnotatedElement
    public final Annotation[] getAnnotations() {
        return this.a.getAnnotations();
    }

    @Override // java.lang.reflect.AccessibleObject, java.lang.reflect.AnnotatedElement
    public final Annotation[] getDeclaredAnnotations() {
        return this.a.getDeclaredAnnotations();
    }

    @Override // java.lang.reflect.AccessibleObject
    public final void setAccessible(boolean z) throws SecurityException {
        this.a.setAccessible(z);
    }

    @Override // java.lang.reflect.AccessibleObject
    public final boolean isAccessible() {
        return this.a.isAccessible();
    }

    public Class<?> getDeclaringClass() {
        return this.b.getDeclaringClass();
    }

    @Override // java.lang.reflect.Member
    public final String getName() {
        return this.b.getName();
    }

    @Override // java.lang.reflect.Member
    public final int getModifiers() {
        return this.b.getModifiers();
    }

    @Override // java.lang.reflect.Member
    public final boolean isSynthetic() {
        return this.b.isSynthetic();
    }

    public final boolean isPublic() {
        return Modifier.isPublic(getModifiers());
    }

    public final boolean isProtected() {
        return Modifier.isProtected(getModifiers());
    }

    public final boolean isPackagePrivate() {
        return !isPrivate() && !isPublic() && !isProtected();
    }

    public final boolean isPrivate() {
        return Modifier.isPrivate(getModifiers());
    }

    public final boolean isStatic() {
        return Modifier.isStatic(getModifiers());
    }

    public final boolean isFinal() {
        return Modifier.isFinal(getModifiers());
    }

    public final boolean isAbstract() {
        return Modifier.isAbstract(getModifiers());
    }

    public final boolean isNative() {
        return Modifier.isNative(getModifiers());
    }

    public final boolean isSynchronized() {
        return Modifier.isSynchronized(getModifiers());
    }

    public boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return getOwnerType().equals(aVar.getOwnerType()) && this.b.equals(aVar.b);
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public String toString() {
        return this.b.toString();
    }
}
