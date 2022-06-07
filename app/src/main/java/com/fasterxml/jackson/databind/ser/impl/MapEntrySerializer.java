package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.BeanUtil;
import java.io.IOException;
import java.util.Map;

@JacksonStdImpl
/* loaded from: classes.dex */
public class MapEntrySerializer extends ContainerSerializer<Map.Entry<?, ?>> implements ContextualSerializer {
    public static final Object MARKER_FOR_EMPTY = JsonInclude.Include.NON_EMPTY;
    protected PropertySerializerMap _dynamicValueSerializers;
    protected final JavaType _entryType;
    protected JsonSerializer<Object> _keySerializer;
    protected final JavaType _keyType;
    protected final BeanProperty _property;
    protected final boolean _suppressNulls;
    protected final Object _suppressableValue;
    protected JsonSerializer<Object> _valueSerializer;
    protected final JavaType _valueType;
    protected final boolean _valueTypeIsStatic;
    protected final TypeSerializer _valueTypeSerializer;

    public boolean hasSingleElement(Map.Entry<?, ?> entry) {
        return true;
    }

    public MapEntrySerializer(JavaType javaType, JavaType javaType2, JavaType javaType3, boolean z, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        super(javaType);
        this._entryType = javaType;
        this._keyType = javaType2;
        this._valueType = javaType3;
        this._valueTypeIsStatic = z;
        this._valueTypeSerializer = typeSerializer;
        this._property = beanProperty;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
        this._suppressableValue = null;
        this._suppressNulls = false;
    }

    @Deprecated
    protected MapEntrySerializer(MapEntrySerializer mapEntrySerializer, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2) {
        this(mapEntrySerializer, beanProperty, typeSerializer, jsonSerializer, jsonSerializer2, mapEntrySerializer._suppressableValue, mapEntrySerializer._suppressNulls);
    }

    protected MapEntrySerializer(MapEntrySerializer mapEntrySerializer, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, Object obj, boolean z) {
        super(Map.class, false);
        this._entryType = mapEntrySerializer._entryType;
        this._keyType = mapEntrySerializer._keyType;
        this._valueType = mapEntrySerializer._valueType;
        this._valueTypeIsStatic = mapEntrySerializer._valueTypeIsStatic;
        this._valueTypeSerializer = mapEntrySerializer._valueTypeSerializer;
        this._keySerializer = jsonSerializer;
        this._valueSerializer = jsonSerializer2;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
        this._property = mapEntrySerializer._property;
        this._suppressableValue = obj;
        this._suppressNulls = z;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new MapEntrySerializer(this, this._property, typeSerializer, this._keySerializer, this._valueSerializer, this._suppressableValue, this._suppressNulls);
    }

    public MapEntrySerializer withResolved(BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, Object obj, boolean z) {
        return new MapEntrySerializer(this, beanProperty, this._valueTypeSerializer, jsonSerializer, jsonSerializer2, obj, z);
    }

    public MapEntrySerializer withContentInclusion(Object obj, boolean z) {
        return (this._suppressableValue == obj && this._suppressNulls == z) ? this : new MapEntrySerializer(this, this._property, this._valueTypeSerializer, this._keySerializer, this._valueSerializer, obj, z);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<?> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2;
        JsonSerializer<?> jsonSerializer3;
        boolean z;
        JsonInclude.Value findPropertyInclusion;
        JsonInclude.Include contentInclusion;
        AnnotationIntrospector annotationIntrospector = serializerProvider.getAnnotationIntrospector();
        Object obj = null;
        AnnotatedMember member = beanProperty == null ? null : beanProperty.getMember();
        if (member == null || annotationIntrospector == null) {
            jsonSerializer2 = null;
            jsonSerializer = null;
        } else {
            Object findKeySerializer = annotationIntrospector.findKeySerializer(member);
            jsonSerializer = findKeySerializer != null ? serializerProvider.serializerInstance(member, findKeySerializer) : null;
            Object findContentSerializer = annotationIntrospector.findContentSerializer(member);
            jsonSerializer2 = findContentSerializer != null ? serializerProvider.serializerInstance(member, findContentSerializer) : null;
        }
        if (jsonSerializer2 == null) {
            jsonSerializer2 = this._valueSerializer;
        }
        JsonSerializer<?> findContextualConvertingSerializer = findContextualConvertingSerializer(serializerProvider, beanProperty, jsonSerializer2);
        JsonSerializer<?> findValueSerializer = (findContextualConvertingSerializer != null || !this._valueTypeIsStatic || this._valueType.isJavaLangObject()) ? findContextualConvertingSerializer : serializerProvider.findValueSerializer(this._valueType, beanProperty);
        if (jsonSerializer == null) {
            jsonSerializer = this._keySerializer;
        }
        if (jsonSerializer == null) {
            jsonSerializer3 = serializerProvider.findKeySerializer(this._keyType, beanProperty);
        } else {
            jsonSerializer3 = serializerProvider.handleSecondaryContextualization(jsonSerializer, beanProperty);
        }
        Object obj2 = this._suppressableValue;
        boolean z2 = this._suppressNulls;
        if (beanProperty != null && (findPropertyInclusion = beanProperty.findPropertyInclusion(serializerProvider.getConfig(), null)) != null && (contentInclusion = findPropertyInclusion.getContentInclusion()) != JsonInclude.Include.USE_DEFAULTS) {
            switch (contentInclusion) {
                case NON_DEFAULT:
                    Object defaultValue = BeanUtil.getDefaultValue(this._valueType);
                    if (defaultValue != null && defaultValue.getClass().isArray()) {
                        obj = ArrayBuilders.getArrayComparator(defaultValue);
                        z = true;
                        break;
                    } else {
                        obj = defaultValue;
                        z = true;
                        break;
                    }
                    break;
                case NON_ABSENT:
                    if (this._valueType.isReferenceType()) {
                        obj = MARKER_FOR_EMPTY;
                    }
                    z = true;
                    break;
                case NON_EMPTY:
                    obj = MARKER_FOR_EMPTY;
                    z = true;
                    break;
                case CUSTOM:
                    Object includeFilterInstance = serializerProvider.includeFilterInstance(null, findPropertyInclusion.getContentFilter());
                    if (includeFilterInstance != null) {
                        z = serializerProvider.includeFilterSuppressNulls(includeFilterInstance);
                        obj = includeFilterInstance;
                        break;
                    } else {
                        obj = includeFilterInstance;
                        z = true;
                        break;
                    }
                case NON_NULL:
                    obj = null;
                    z = true;
                    break;
                default:
                    z = false;
                    obj = null;
                    break;
            }
        } else {
            obj = obj2;
            z = z2;
        }
        return withResolved(beanProperty, jsonSerializer3, findValueSerializer, obj, z);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JavaType getContentType() {
        return this._valueType;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JsonSerializer<?> getContentSerializer() {
        return this._valueSerializer;
    }

    public boolean isEmpty(SerializerProvider serializerProvider, Map.Entry<?, ?> entry) {
        Object value = entry.getValue();
        if (value == null) {
            return this._suppressNulls;
        }
        if (this._suppressableValue == null) {
            return false;
        }
        JsonSerializer<Object> jsonSerializer = this._valueSerializer;
        if (jsonSerializer == null) {
            Class<?> cls = value.getClass();
            JsonSerializer<Object> serializerFor = this._dynamicValueSerializers.serializerFor(cls);
            if (serializerFor == null) {
                try {
                    jsonSerializer = _findAndAddDynamic(this._dynamicValueSerializers, cls, serializerProvider);
                } catch (JsonMappingException unused) {
                    return false;
                }
            } else {
                jsonSerializer = serializerFor;
            }
        }
        Object obj = this._suppressableValue;
        if (obj == MARKER_FOR_EMPTY) {
            return jsonSerializer.isEmpty(serializerProvider, value);
        }
        return obj.equals(value);
    }

    public void serialize(Map.Entry<?, ?> entry, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject(entry);
        serializeDynamic(entry, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndObject();
    }

    public void serializeWithType(Map.Entry<?, ?> entry, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        jsonGenerator.setCurrentValue(entry);
        WritableTypeId writeTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(entry, JsonToken.START_OBJECT));
        serializeDynamic(entry, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffix(jsonGenerator, writeTypePrefix);
    }

    protected void serializeDynamic(Map.Entry<?, ?> entry, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2;
        TypeSerializer typeSerializer = this._valueTypeSerializer;
        Object key = entry.getKey();
        if (key == null) {
            jsonSerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
        } else {
            jsonSerializer = this._keySerializer;
        }
        Object value = entry.getValue();
        if (value != null) {
            jsonSerializer2 = this._valueSerializer;
            if (jsonSerializer2 == null) {
                Class<?> cls = value.getClass();
                JsonSerializer<Object> serializerFor = this._dynamicValueSerializers.serializerFor(cls);
                if (serializerFor != null) {
                    jsonSerializer2 = serializerFor;
                } else if (this._valueType.hasGenericTypes()) {
                    jsonSerializer2 = _findAndAddDynamic(this._dynamicValueSerializers, serializerProvider.constructSpecializedType(this._valueType, cls), serializerProvider);
                } else {
                    jsonSerializer2 = _findAndAddDynamic(this._dynamicValueSerializers, cls, serializerProvider);
                }
            }
            Object obj = this._suppressableValue;
            if (obj != null && ((obj == MARKER_FOR_EMPTY && jsonSerializer2.isEmpty(serializerProvider, value)) || this._suppressableValue.equals(value))) {
                return;
            }
        } else if (!this._suppressNulls) {
            jsonSerializer2 = serializerProvider.getDefaultNullValueSerializer();
        } else {
            return;
        }
        jsonSerializer.serialize(key, jsonGenerator, serializerProvider);
        try {
            if (typeSerializer == null) {
                jsonSerializer2.serialize(value, jsonGenerator, serializerProvider);
            } else {
                jsonSerializer2.serializeWithType(value, jsonGenerator, serializerProvider, typeSerializer);
            }
        } catch (Exception e) {
            wrapAndThrow(serializerProvider, e, entry, "" + key);
        }
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> cls, SerializerProvider serializerProvider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult findAndAddSecondarySerializer = propertySerializerMap.findAndAddSecondarySerializer(cls, serializerProvider, this._property);
        if (propertySerializerMap != findAndAddSecondarySerializer.map) {
            this._dynamicValueSerializers = findAndAddSecondarySerializer.map;
        }
        return findAndAddSecondarySerializer.serializer;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, JavaType javaType, SerializerProvider serializerProvider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult findAndAddSecondarySerializer = propertySerializerMap.findAndAddSecondarySerializer(javaType, serializerProvider, this._property);
        if (propertySerializerMap != findAndAddSecondarySerializer.map) {
            this._dynamicValueSerializers = findAndAddSecondarySerializer.map;
        }
        return findAndAddSecondarySerializer.serializer;
    }
}
