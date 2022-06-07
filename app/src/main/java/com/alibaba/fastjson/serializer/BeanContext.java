package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.FieldInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public final class BeanContext {
    private final Class<?> beanClass;
    private final FieldInfo fieldInfo;
    private final String format;

    public BeanContext(Class<?> cls, FieldInfo fieldInfo) {
        this.beanClass = cls;
        this.fieldInfo = fieldInfo;
        this.format = fieldInfo.getFormat();
    }

    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    public Method getMethod() {
        return this.fieldInfo.method;
    }

    public Field getField() {
        return this.fieldInfo.field;
    }

    public String getName() {
        return this.fieldInfo.name;
    }

    public String getLabel() {
        return this.fieldInfo.label;
    }

    public Class<?> getFieldClass() {
        return this.fieldInfo.fieldClass;
    }

    public Type getFieldType() {
        return this.fieldInfo.fieldType;
    }

    public int getFeatures() {
        return this.fieldInfo.serialzeFeatures;
    }

    public boolean isJsonDirect() {
        return this.fieldInfo.jsonDirect;
    }

    public <T extends Annotation> T getAnnation(Class<T> cls) {
        return (T) this.fieldInfo.getAnnation(cls);
    }

    public String getFormat() {
        return this.format;
    }
}
