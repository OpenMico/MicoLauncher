package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.Collections;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SimpleBeanPropertyDefinition extends BeanPropertyDefinition {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final PropertyName _fullName;
    protected final JsonInclude.Value _inclusion;
    protected final AnnotatedMember _member;
    protected final PropertyMetadata _metadata;

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isExplicitlyIncluded() {
        return false;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isExplicitlyNamed() {
        return false;
    }

    protected SimpleBeanPropertyDefinition(AnnotationIntrospector annotationIntrospector, AnnotatedMember annotatedMember, PropertyName propertyName, PropertyMetadata propertyMetadata, JsonInclude.Value value) {
        this._annotationIntrospector = annotationIntrospector;
        this._member = annotatedMember;
        this._fullName = propertyName;
        this._metadata = propertyMetadata == null ? PropertyMetadata.STD_OPTIONAL : propertyMetadata;
        this._inclusion = value;
    }

    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember) {
        return new SimpleBeanPropertyDefinition(mapperConfig.getAnnotationIntrospector(), annotatedMember, PropertyName.construct(annotatedMember.getName()), null, EMPTY_INCLUDE);
    }

    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, PropertyName propertyName) {
        return construct(mapperConfig, annotatedMember, propertyName, (PropertyMetadata) null, EMPTY_INCLUDE);
    }

    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, PropertyName propertyName, PropertyMetadata propertyMetadata, JsonInclude.Include include) {
        JsonInclude.Value value;
        if (include == null || include == JsonInclude.Include.USE_DEFAULTS) {
            value = EMPTY_INCLUDE;
        } else {
            value = JsonInclude.Value.construct(include, null);
        }
        return new SimpleBeanPropertyDefinition(mapperConfig.getAnnotationIntrospector(), annotatedMember, propertyName, propertyMetadata, value);
    }

    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, PropertyName propertyName, PropertyMetadata propertyMetadata, JsonInclude.Value value) {
        return new SimpleBeanPropertyDefinition(mapperConfig.getAnnotationIntrospector(), annotatedMember, propertyName, propertyMetadata, value);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public BeanPropertyDefinition withSimpleName(String str) {
        return (!this._fullName.hasSimpleName(str) || this._fullName.hasNamespace()) ? new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, new PropertyName(str), this._metadata, this._inclusion) : this;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public BeanPropertyDefinition withName(PropertyName propertyName) {
        return this._fullName.equals(propertyName) ? this : new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, propertyName, this._metadata, this._inclusion);
    }

    public BeanPropertyDefinition withMetadata(PropertyMetadata propertyMetadata) {
        return propertyMetadata.equals(this._metadata) ? this : new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, this._fullName, propertyMetadata, this._inclusion);
    }

    public BeanPropertyDefinition withInclusion(JsonInclude.Value value) {
        return this._inclusion == value ? this : new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, this._fullName, this._metadata, value);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition, com.fasterxml.jackson.databind.util.Named
    public String getName() {
        return this._fullName.getSimpleName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public PropertyName getFullName() {
        return this._fullName;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasName(PropertyName propertyName) {
        return this._fullName.equals(propertyName);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public String getInternalName() {
        return getName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public PropertyName getWrapperName() {
        AnnotatedMember annotatedMember;
        AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
        if (annotationIntrospector == null || (annotatedMember = this._member) == null) {
            return null;
        }
        return annotationIntrospector.findWrapperName(annotatedMember);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public PropertyMetadata getMetadata() {
        return this._metadata;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public JavaType getPrimaryType() {
        AnnotatedMember annotatedMember = this._member;
        if (annotatedMember == null) {
            return TypeFactory.unknownType();
        }
        return annotatedMember.getType();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public Class<?> getRawPrimaryType() {
        AnnotatedMember annotatedMember = this._member;
        if (annotatedMember == null) {
            return Object.class;
        }
        return annotatedMember.getRawType();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public JsonInclude.Value findInclusion() {
        return this._inclusion;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasGetter() {
        return getGetter() != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasSetter() {
        return getSetter() != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasField() {
        return this._member instanceof AnnotatedField;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasConstructorParameter() {
        return this._member instanceof AnnotatedParameter;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMethod getGetter() {
        AnnotatedMember annotatedMember = this._member;
        if (!(annotatedMember instanceof AnnotatedMethod) || ((AnnotatedMethod) annotatedMember).getParameterCount() != 0) {
            return null;
        }
        return (AnnotatedMethod) this._member;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMethod getSetter() {
        AnnotatedMember annotatedMember = this._member;
        if (!(annotatedMember instanceof AnnotatedMethod) || ((AnnotatedMethod) annotatedMember).getParameterCount() != 1) {
            return null;
        }
        return (AnnotatedMethod) this._member;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedField getField() {
        AnnotatedMember annotatedMember = this._member;
        if (annotatedMember instanceof AnnotatedField) {
            return (AnnotatedField) annotatedMember;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedParameter getConstructorParameter() {
        AnnotatedMember annotatedMember = this._member;
        if (annotatedMember instanceof AnnotatedParameter) {
            return (AnnotatedParameter) annotatedMember;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public Iterator<AnnotatedParameter> getConstructorParameters() {
        AnnotatedParameter constructorParameter = getConstructorParameter();
        if (constructorParameter == null) {
            return ClassUtil.emptyIterator();
        }
        return Collections.singleton(constructorParameter).iterator();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMember getPrimaryMember() {
        return this._member;
    }
}
