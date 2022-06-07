package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

/* loaded from: classes.dex */
public final class CreatorCandidate {
    protected final AnnotatedWithParams _creator;
    protected final AnnotationIntrospector _intr;
    protected final int _paramCount;
    protected final Param[] _params;

    protected CreatorCandidate(AnnotationIntrospector annotationIntrospector, AnnotatedWithParams annotatedWithParams, Param[] paramArr, int i) {
        this._intr = annotationIntrospector;
        this._creator = annotatedWithParams;
        this._params = paramArr;
        this._paramCount = i;
    }

    public static CreatorCandidate construct(AnnotationIntrospector annotationIntrospector, AnnotatedWithParams annotatedWithParams, BeanPropertyDefinition[] beanPropertyDefinitionArr) {
        int parameterCount = annotatedWithParams.getParameterCount();
        Param[] paramArr = new Param[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            AnnotatedParameter parameter = annotatedWithParams.getParameter(i);
            paramArr[i] = new Param(parameter, beanPropertyDefinitionArr == null ? null : beanPropertyDefinitionArr[i], annotationIntrospector.findInjectableValue(parameter));
        }
        return new CreatorCandidate(annotationIntrospector, annotatedWithParams, paramArr, parameterCount);
    }

    public AnnotatedWithParams creator() {
        return this._creator;
    }

    public int paramCount() {
        return this._paramCount;
    }

    public JacksonInject.Value injection(int i) {
        return this._params[i].injection;
    }

    public AnnotatedParameter parameter(int i) {
        return this._params[i].annotated;
    }

    public BeanPropertyDefinition propertyDef(int i) {
        return this._params[i].propDef;
    }

    public PropertyName paramName(int i) {
        BeanPropertyDefinition beanPropertyDefinition = this._params[i].propDef;
        if (beanPropertyDefinition != null) {
            return beanPropertyDefinition.getFullName();
        }
        return null;
    }

    public PropertyName explicitParamName(int i) {
        BeanPropertyDefinition beanPropertyDefinition = this._params[i].propDef;
        if (beanPropertyDefinition == null || !beanPropertyDefinition.isExplicitlyNamed()) {
            return null;
        }
        return beanPropertyDefinition.getFullName();
    }

    public PropertyName findImplicitParamName(int i) {
        String findImplicitPropertyName = this._intr.findImplicitPropertyName(this._params[i].annotated);
        if (findImplicitPropertyName == null || findImplicitPropertyName.isEmpty()) {
            return null;
        }
        return PropertyName.construct(findImplicitPropertyName);
    }

    public int findOnlyParamWithoutInjection() {
        int i = -1;
        for (int i2 = 0; i2 < this._paramCount; i2++) {
            if (this._params[i2].injection == null) {
                if (i >= 0) {
                    return -1;
                }
                i = i2;
            }
        }
        return i;
    }

    public String toString() {
        return this._creator.toString();
    }

    /* loaded from: classes.dex */
    public static final class Param {
        public final AnnotatedParameter annotated;
        public final JacksonInject.Value injection;
        public final BeanPropertyDefinition propDef;

        public Param(AnnotatedParameter annotatedParameter, BeanPropertyDefinition beanPropertyDefinition, JacksonInject.Value value) {
            this.annotated = annotatedParameter;
            this.propDef = beanPropertyDefinition;
            this.injection = value;
        }

        public PropertyName fullName() {
            BeanPropertyDefinition beanPropertyDefinition = this.propDef;
            if (beanPropertyDefinition == null) {
                return null;
            }
            return beanPropertyDefinition.getFullName();
        }

        public boolean hasFullName() {
            BeanPropertyDefinition beanPropertyDefinition = this.propDef;
            if (beanPropertyDefinition == null) {
                return false;
            }
            return beanPropertyDefinition.getFullName().hasSimpleName();
        }
    }
}
