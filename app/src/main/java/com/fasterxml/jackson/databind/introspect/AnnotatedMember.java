package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.Collections;

/* loaded from: classes.dex */
public abstract class AnnotatedMember extends Annotated implements Serializable {
    private static final long serialVersionUID = 1;
    protected final transient AnnotationMap _annotations;
    protected final transient TypeResolutionContext _typeContext;

    public abstract Class<?> getDeclaringClass();

    public abstract Member getMember();

    public abstract Object getValue(Object obj) throws UnsupportedOperationException, IllegalArgumentException;

    public abstract void setValue(Object obj, Object obj2) throws UnsupportedOperationException, IllegalArgumentException;

    public abstract Annotated withAnnotations(AnnotationMap annotationMap);

    public AnnotatedMember(TypeResolutionContext typeResolutionContext, AnnotationMap annotationMap) {
        this._typeContext = typeResolutionContext;
        this._annotations = annotationMap;
    }

    public AnnotatedMember(AnnotatedMember annotatedMember) {
        this._typeContext = annotatedMember._typeContext;
        this._annotations = annotatedMember._annotations;
    }

    public String getFullName() {
        return getDeclaringClass().getName() + "#" + getName();
    }

    @Deprecated
    public TypeResolutionContext getTypeContext() {
        return this._typeContext;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public final <A extends Annotation> A getAnnotation(Class<A> cls) {
        AnnotationMap annotationMap = this._annotations;
        if (annotationMap == null) {
            return null;
        }
        return (A) annotationMap.get(cls);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public final boolean hasAnnotation(Class<?> cls) {
        AnnotationMap annotationMap = this._annotations;
        if (annotationMap == null) {
            return false;
        }
        return annotationMap.has(cls);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public boolean hasOneOf(Class<? extends Annotation>[] clsArr) {
        AnnotationMap annotationMap = this._annotations;
        if (annotationMap == null) {
            return false;
        }
        return annotationMap.hasOneOf(clsArr);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    @Deprecated
    public Iterable<Annotation> annotations() {
        AnnotationMap annotationMap = this._annotations;
        if (annotationMap == null) {
            return Collections.emptyList();
        }
        return annotationMap.annotations();
    }

    public AnnotationMap getAllAnnotations() {
        return this._annotations;
    }

    public final void fixAccess(boolean z) {
        Member member = getMember();
        if (member != null) {
            ClassUtil.checkAndFixAccess(member, z);
        }
    }
}
