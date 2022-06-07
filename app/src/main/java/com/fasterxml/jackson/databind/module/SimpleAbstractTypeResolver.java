package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/* loaded from: classes.dex */
public class SimpleAbstractTypeResolver extends AbstractTypeResolver implements Serializable {
    private static final long serialVersionUID = 8635483102371490919L;
    protected final HashMap<ClassKey, Class<?>> _mappings = new HashMap<>();

    @Override // com.fasterxml.jackson.databind.AbstractTypeResolver
    public JavaType resolveAbstractType(DeserializationConfig deserializationConfig, BeanDescription beanDescription) {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.AbstractTypeResolver
    @Deprecated
    public JavaType resolveAbstractType(DeserializationConfig deserializationConfig, JavaType javaType) {
        return null;
    }

    public <T> SimpleAbstractTypeResolver addMapping(Class<T> cls, Class<? extends T> cls2) {
        if (cls == cls2) {
            throw new IllegalArgumentException("Cannot add mapping from class to itself");
        } else if (!cls.isAssignableFrom(cls2)) {
            throw new IllegalArgumentException("Cannot add mapping from class " + cls.getName() + " to " + cls2.getName() + ", as latter is not a subtype of former");
        } else if (Modifier.isAbstract(cls.getModifiers())) {
            this._mappings.put(new ClassKey(cls), cls2);
            return this;
        } else {
            throw new IllegalArgumentException("Cannot add mapping from class " + cls.getName() + " since it is not abstract");
        }
    }

    @Override // com.fasterxml.jackson.databind.AbstractTypeResolver
    public JavaType findTypeMapping(DeserializationConfig deserializationConfig, JavaType javaType) {
        Class<?> cls = this._mappings.get(new ClassKey(javaType.getRawClass()));
        if (cls == null) {
            return null;
        }
        return deserializationConfig.getTypeFactory().constructSpecializedType(javaType, cls);
    }
}
