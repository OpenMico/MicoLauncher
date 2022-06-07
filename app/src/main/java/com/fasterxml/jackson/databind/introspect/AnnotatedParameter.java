package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public final class AnnotatedParameter extends AnnotatedMember {
    private static final long serialVersionUID = 1;
    protected final int _index;
    protected final AnnotatedWithParams _owner;
    protected final JavaType _type;

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public AnnotatedElement getAnnotated() {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public String getName() {
        return "";
    }

    public AnnotatedParameter(AnnotatedWithParams annotatedWithParams, JavaType javaType, TypeResolutionContext typeResolutionContext, AnnotationMap annotationMap, int i) {
        super(typeResolutionContext, annotationMap);
        this._owner = annotatedWithParams;
        this._type = javaType;
        this._index = i;
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public AnnotatedParameter withAnnotations(AnnotationMap annotationMap) {
        return annotationMap == this._annotations ? this : this._owner.replaceParameterAnnotations(this._index, annotationMap);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public int getModifiers() {
        return this._owner.getModifiers();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Class<?> getRawType() {
        return this._type.getRawClass();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public JavaType getType() {
        return this._type;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    @Deprecated
    public Type getGenericType() {
        return this._owner.getGenericParameterType(this._index);
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Class<?> getDeclaringClass() {
        return this._owner.getDeclaringClass();
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Member getMember() {
        return this._owner.getMember();
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public void setValue(Object obj, Object obj2) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call setValue() on constructor parameter of " + getDeclaringClass().getName());
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Object getValue(Object obj) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call getValue() on constructor parameter of " + getDeclaringClass().getName());
    }

    public Type getParameterType() {
        return this._type;
    }

    public AnnotatedWithParams getOwner() {
        return this._owner;
    }

    public int getIndex() {
        return this._index;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public int hashCode() {
        return this._owner.hashCode() + this._index;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!ClassUtil.hasClass(obj, getClass())) {
            return false;
        }
        AnnotatedParameter annotatedParameter = (AnnotatedParameter) obj;
        return annotatedParameter._owner.equals(this._owner) && annotatedParameter._index == this._index;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public String toString() {
        return "[parameter #" + getIndex() + ", annotations: " + this._annotations + "]";
    }
}
