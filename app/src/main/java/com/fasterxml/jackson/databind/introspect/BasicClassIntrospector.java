package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.LRUMap;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes.dex */
public class BasicClassIntrospector extends ClassIntrospector implements Serializable {
    private static final long serialVersionUID = 1;
    protected final LRUMap<JavaType, BasicBeanDescription> _cachedFCA = new LRUMap<>(16, 64);
    protected static final BasicBeanDescription STRING_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(String.class), AnnotatedClassResolver.a(String.class));
    protected static final BasicBeanDescription BOOLEAN_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Boolean.TYPE), AnnotatedClassResolver.a(Boolean.TYPE));
    protected static final BasicBeanDescription INT_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Integer.TYPE), AnnotatedClassResolver.a(Integer.TYPE));
    protected static final BasicBeanDescription LONG_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Long.TYPE), AnnotatedClassResolver.a(Long.TYPE));

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public ClassIntrospector copy() {
        return new BasicClassIntrospector();
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forSerialization(SerializationConfig serializationConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription _findStdTypeDesc = _findStdTypeDesc(javaType);
        if (_findStdTypeDesc == null) {
            _findStdTypeDesc = _findStdJdkCollectionDesc(serializationConfig, javaType);
            if (_findStdTypeDesc == null) {
                _findStdTypeDesc = BasicBeanDescription.forSerialization(collectProperties(serializationConfig, javaType, mixInResolver, true, BluetoothConstants.SET));
            }
            this._cachedFCA.putIfAbsent(javaType, _findStdTypeDesc);
        }
        return _findStdTypeDesc;
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forDeserialization(DeserializationConfig deserializationConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription _findStdTypeDesc = _findStdTypeDesc(javaType);
        if (_findStdTypeDesc == null) {
            _findStdTypeDesc = _findStdJdkCollectionDesc(deserializationConfig, javaType);
            if (_findStdTypeDesc == null) {
                _findStdTypeDesc = BasicBeanDescription.forDeserialization(collectProperties(deserializationConfig, javaType, mixInResolver, false, BluetoothConstants.SET));
            }
            this._cachedFCA.putIfAbsent(javaType, _findStdTypeDesc);
        }
        return _findStdTypeDesc;
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forDeserializationWithBuilder(DeserializationConfig deserializationConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription forDeserialization = BasicBeanDescription.forDeserialization(collectPropertiesWithBuilder(deserializationConfig, javaType, mixInResolver, false));
        this._cachedFCA.putIfAbsent(javaType, forDeserialization);
        return forDeserialization;
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forCreation(DeserializationConfig deserializationConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription _findStdTypeDesc = _findStdTypeDesc(javaType);
        if (_findStdTypeDesc != null) {
            return _findStdTypeDesc;
        }
        BasicBeanDescription _findStdJdkCollectionDesc = _findStdJdkCollectionDesc(deserializationConfig, javaType);
        return _findStdJdkCollectionDesc == null ? BasicBeanDescription.forDeserialization(collectProperties(deserializationConfig, javaType, mixInResolver, false, BluetoothConstants.SET)) : _findStdJdkCollectionDesc;
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forClassAnnotations(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription _findStdTypeDesc = _findStdTypeDesc(javaType);
        if (_findStdTypeDesc != null) {
            return _findStdTypeDesc;
        }
        BasicBeanDescription basicBeanDescription = this._cachedFCA.get(javaType);
        if (basicBeanDescription != null) {
            return basicBeanDescription;
        }
        BasicBeanDescription forOtherUse = BasicBeanDescription.forOtherUse(mapperConfig, javaType, _resolveAnnotatedClass(mapperConfig, javaType, mixInResolver));
        this._cachedFCA.put(javaType, forOtherUse);
        return forOtherUse;
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forDirectClassAnnotations(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription _findStdTypeDesc = _findStdTypeDesc(javaType);
        return _findStdTypeDesc == null ? BasicBeanDescription.forOtherUse(mapperConfig, javaType, _resolveAnnotatedWithoutSuperTypes(mapperConfig, javaType, mixInResolver)) : _findStdTypeDesc;
    }

    protected POJOPropertiesCollector collectProperties(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver, boolean z, String str) {
        return constructPropertyCollector(mapperConfig, _resolveAnnotatedClass(mapperConfig, javaType, mixInResolver), javaType, z, str);
    }

    protected POJOPropertiesCollector collectPropertiesWithBuilder(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver, boolean z) {
        AnnotatedClass _resolveAnnotatedClass = _resolveAnnotatedClass(mapperConfig, javaType, mixInResolver);
        JsonPOJOBuilder.Value value = null;
        AnnotationIntrospector annotationIntrospector = mapperConfig.isAnnotationProcessingEnabled() ? mapperConfig.getAnnotationIntrospector() : null;
        if (annotationIntrospector != null) {
            value = annotationIntrospector.findPOJOBuilderConfig(_resolveAnnotatedClass);
        }
        return constructPropertyCollector(mapperConfig, _resolveAnnotatedClass, javaType, z, value == null ? JsonPOJOBuilder.DEFAULT_WITH_PREFIX : value.withPrefix);
    }

    protected POJOPropertiesCollector constructPropertyCollector(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, JavaType javaType, boolean z, String str) {
        return new POJOPropertiesCollector(mapperConfig, z, javaType, annotatedClass, str);
    }

    protected BasicBeanDescription _findStdTypeDesc(JavaType javaType) {
        Class<?> rawClass = javaType.getRawClass();
        if (rawClass.isPrimitive()) {
            if (rawClass == Boolean.TYPE) {
                return BOOLEAN_DESC;
            }
            if (rawClass == Integer.TYPE) {
                return INT_DESC;
            }
            if (rawClass == Long.TYPE) {
                return LONG_DESC;
            }
            return null;
        } else if (rawClass == String.class) {
            return STRING_DESC;
        } else {
            return null;
        }
    }

    protected boolean _isStdJDKCollection(JavaType javaType) {
        Class<?> rawClass;
        String packageName;
        return javaType.isContainerType() && !javaType.isArrayType() && (packageName = ClassUtil.getPackageName((rawClass = javaType.getRawClass()))) != null && (packageName.startsWith("java.lang") || packageName.startsWith("java.util")) && (Collection.class.isAssignableFrom(rawClass) || Map.class.isAssignableFrom(rawClass));
    }

    protected BasicBeanDescription _findStdJdkCollectionDesc(MapperConfig<?> mapperConfig, JavaType javaType) {
        if (_isStdJDKCollection(javaType)) {
            return BasicBeanDescription.forOtherUse(mapperConfig, javaType, _resolveAnnotatedClass(mapperConfig, javaType, mapperConfig));
        }
        return null;
    }

    protected AnnotatedClass _resolveAnnotatedClass(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        return AnnotatedClassResolver.resolve(mapperConfig, javaType, mixInResolver);
    }

    protected AnnotatedClass _resolveAnnotatedWithoutSuperTypes(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        return AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, javaType, mixInResolver);
    }
}
