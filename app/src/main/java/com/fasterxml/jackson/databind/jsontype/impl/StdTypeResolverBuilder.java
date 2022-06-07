package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.NoClass;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.util.Collection;

/* loaded from: classes.dex */
public class StdTypeResolverBuilder implements TypeResolverBuilder<StdTypeResolverBuilder> {
    protected TypeIdResolver _customIdResolver;
    protected Class<?> _defaultImpl;
    protected JsonTypeInfo.Id _idType;
    protected JsonTypeInfo.As _includeAs;
    protected boolean _typeIdVisible = false;
    protected String _typeProperty;

    public StdTypeResolverBuilder() {
    }

    protected StdTypeResolverBuilder(JsonTypeInfo.Id id, JsonTypeInfo.As as, String str) {
        this._idType = id;
        this._includeAs = as;
        this._typeProperty = str;
    }

    public static StdTypeResolverBuilder noTypeInfoBuilder() {
        return new StdTypeResolverBuilder().init(JsonTypeInfo.Id.NONE, (TypeIdResolver) null);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder init(JsonTypeInfo.Id id, TypeIdResolver typeIdResolver) {
        if (id != null) {
            this._idType = id;
            this._customIdResolver = typeIdResolver;
            this._typeProperty = id.getDefaultPropertyName();
            return this;
        }
        throw new IllegalArgumentException("idType cannot be null");
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public TypeSerializer buildTypeSerializer(SerializationConfig serializationConfig, JavaType javaType, Collection<NamedType> collection) {
        if (this._idType == JsonTypeInfo.Id.NONE || javaType.isPrimitive()) {
            return null;
        }
        TypeIdResolver idResolver = idResolver(serializationConfig, javaType, collection, true, false);
        switch (this._includeAs) {
            case WRAPPER_ARRAY:
                return new AsArrayTypeSerializer(idResolver, null);
            case PROPERTY:
                return new AsPropertyTypeSerializer(idResolver, null, this._typeProperty);
            case WRAPPER_OBJECT:
                return new AsWrapperTypeSerializer(idResolver, null);
            case EXTERNAL_PROPERTY:
                return new AsExternalTypeSerializer(idResolver, null, this._typeProperty);
            case EXISTING_PROPERTY:
                return new AsExistingPropertyTypeSerializer(idResolver, null, this._typeProperty);
            default:
                throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
        }
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public TypeDeserializer buildTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, Collection<NamedType> collection) {
        if (this._idType == JsonTypeInfo.Id.NONE || javaType.isPrimitive()) {
            return null;
        }
        TypeIdResolver idResolver = idResolver(deserializationConfig, javaType, collection, false, true);
        JavaType defineDefaultImpl = defineDefaultImpl(deserializationConfig, javaType);
        switch (this._includeAs) {
            case WRAPPER_ARRAY:
                return new AsArrayTypeDeserializer(javaType, idResolver, this._typeProperty, this._typeIdVisible, defineDefaultImpl);
            case PROPERTY:
            case EXISTING_PROPERTY:
                return new AsPropertyTypeDeserializer(javaType, idResolver, this._typeProperty, this._typeIdVisible, defineDefaultImpl, this._includeAs);
            case WRAPPER_OBJECT:
                return new AsWrapperTypeDeserializer(javaType, idResolver, this._typeProperty, this._typeIdVisible, defineDefaultImpl);
            case EXTERNAL_PROPERTY:
                return new AsExternalTypeDeserializer(javaType, idResolver, this._typeProperty, this._typeIdVisible, defineDefaultImpl);
            default:
                throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
        }
    }

    protected JavaType defineDefaultImpl(DeserializationConfig deserializationConfig, JavaType javaType) {
        Class<?> cls = this._defaultImpl;
        if (cls == null) {
            if (!deserializationConfig.isEnabled(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL) || javaType.isAbstract()) {
                return null;
            }
            return javaType;
        } else if (cls == Void.class || cls == NoClass.class) {
            return deserializationConfig.getTypeFactory().constructType(this._defaultImpl);
        } else {
            if (javaType.hasRawClass(cls)) {
                return javaType;
            }
            if (javaType.isTypeOrSuperTypeOf(this._defaultImpl)) {
                return deserializationConfig.getTypeFactory().constructSpecializedType(javaType, this._defaultImpl);
            }
            return null;
        }
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder inclusion(JsonTypeInfo.As as) {
        if (as != null) {
            this._includeAs = as;
            return this;
        }
        throw new IllegalArgumentException("includeAs cannot be null");
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder typeProperty(String str) {
        if (str == null || str.length() == 0) {
            str = this._idType.getDefaultPropertyName();
        }
        this._typeProperty = str;
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    /* renamed from: defaultImpl  reason: avoid collision after fix types in other method */
    public StdTypeResolverBuilder defaultImpl2(Class<?> cls) {
        this._defaultImpl = cls;
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder typeIdVisibility(boolean z) {
        this._typeIdVisible = z;
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public Class<?> getDefaultImpl() {
        return this._defaultImpl;
    }

    public String getTypeProperty() {
        return this._typeProperty;
    }

    public boolean isTypeIdVisible() {
        return this._typeIdVisible;
    }

    protected TypeIdResolver idResolver(MapperConfig<?> mapperConfig, JavaType javaType, Collection<NamedType> collection, boolean z, boolean z2) {
        TypeIdResolver typeIdResolver = this._customIdResolver;
        if (typeIdResolver != null) {
            return typeIdResolver;
        }
        if (this._idType != null) {
            switch (this._idType) {
                case CLASS:
                    return new ClassNameIdResolver(javaType, mapperConfig.getTypeFactory());
                case MINIMAL_CLASS:
                    return new MinimalClassNameIdResolver(javaType, mapperConfig.getTypeFactory());
                case NAME:
                    return TypeNameIdResolver.construct(mapperConfig, javaType, collection, z, z2);
                case NONE:
                    return null;
                default:
                    throw new IllegalStateException("Do not know how to construct standard type id resolver for idType: " + this._idType);
            }
        } else {
            throw new IllegalStateException("Cannot build, 'init()' not yet called");
        }
    }
}
