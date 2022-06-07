package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.Converter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class BeanDescription {
    protected final JavaType _type;

    @Deprecated
    public abstract TypeBindings bindingsForBeanType();

    public abstract AnnotatedMember findAnyGetter();

    public abstract AnnotatedMember findAnySetterAccessor();

    @Deprecated
    public abstract Map<String, AnnotatedMember> findBackReferenceProperties();

    public abstract List<BeanPropertyDefinition> findBackReferences();

    public String findClassDescription() {
        return null;
    }

    public abstract AnnotatedConstructor findDefaultConstructor();

    public abstract Class<?>[] findDefaultViews();

    public abstract Converter<Object, Object> findDeserializationConverter();

    public abstract JsonFormat.Value findExpectedFormat(JsonFormat.Value value);

    public abstract Method findFactoryMethod(Class<?>... clsArr);

    public abstract Map<Object, AnnotatedMember> findInjectables();

    public abstract AnnotatedMember findJsonValueAccessor();

    @Deprecated
    public abstract AnnotatedMethod findJsonValueMethod();

    public abstract AnnotatedMethod findMethod(String str, Class<?>[] clsArr);

    public abstract Class<?> findPOJOBuilder();

    public abstract JsonPOJOBuilder.Value findPOJOBuilderConfig();

    public abstract List<BeanPropertyDefinition> findProperties();

    public abstract JsonInclude.Value findPropertyInclusion(JsonInclude.Value value);

    public abstract Converter<Object, Object> findSerializationConverter();

    public abstract Constructor<?> findSingleArgConstructor(Class<?>... clsArr);

    public abstract Annotations getClassAnnotations();

    public abstract AnnotatedClass getClassInfo();

    public abstract List<AnnotatedConstructor> getConstructors();

    public abstract List<AnnotatedMethod> getFactoryMethods();

    public abstract Set<String> getIgnoredPropertyNames();

    public abstract ObjectIdInfo getObjectIdInfo();

    public abstract boolean hasKnownClassAnnotations();

    public abstract Object instantiateBean(boolean z);

    @Deprecated
    public abstract JavaType resolveType(Type type);

    public BeanDescription(JavaType javaType) {
        this._type = javaType;
    }

    public JavaType getType() {
        return this._type;
    }

    public Class<?> getBeanClass() {
        return this._type.getRawClass();
    }

    public boolean isNonStaticInnerClass() {
        return getClassInfo().isNonStaticInnerClass();
    }

    @Deprecated
    public AnnotatedMethod findAnySetter() {
        AnnotatedMember findAnySetterAccessor = findAnySetterAccessor();
        if (findAnySetterAccessor instanceof AnnotatedMethod) {
            return (AnnotatedMethod) findAnySetterAccessor;
        }
        return null;
    }

    @Deprecated
    public AnnotatedMember findAnySetterField() {
        AnnotatedMember findAnySetterAccessor = findAnySetterAccessor();
        if (findAnySetterAccessor instanceof AnnotatedField) {
            return findAnySetterAccessor;
        }
        return null;
    }
}
