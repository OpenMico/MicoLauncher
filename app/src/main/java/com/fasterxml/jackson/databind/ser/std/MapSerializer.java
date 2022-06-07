package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@JacksonStdImpl
/* loaded from: classes.dex */
public class MapSerializer extends ContainerSerializer<Map<?, ?>> implements ContextualSerializer {
    private static final long serialVersionUID = 1;
    protected PropertySerializerMap _dynamicValueSerializers;
    protected final Object _filterId;
    protected final Set<String> _ignoredEntries;
    protected JsonSerializer<Object> _keySerializer;
    protected final JavaType _keyType;
    protected final BeanProperty _property;
    protected final boolean _sortKeys;
    protected final boolean _suppressNulls;
    protected final Object _suppressableValue;
    protected JsonSerializer<Object> _valueSerializer;
    protected final JavaType _valueType;
    protected final boolean _valueTypeIsStatic;
    protected final TypeSerializer _valueTypeSerializer;
    protected static final JavaType UNSPECIFIED_TYPE = TypeFactory.unknownType();
    public static final Object MARKER_FOR_EMPTY = JsonInclude.Include.NON_EMPTY;

    protected MapSerializer(Set<String> set, JavaType javaType, JavaType javaType2, boolean z, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2) {
        super(Map.class, false);
        this._ignoredEntries = (set == null || set.isEmpty()) ? null : set;
        this._keyType = javaType;
        this._valueType = javaType2;
        this._valueTypeIsStatic = z;
        this._valueTypeSerializer = typeSerializer;
        this._keySerializer = jsonSerializer;
        this._valueSerializer = jsonSerializer2;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
        this._property = null;
        this._filterId = null;
        this._sortKeys = false;
        this._suppressableValue = null;
        this._suppressNulls = false;
    }

    protected MapSerializer(MapSerializer mapSerializer, BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, Set<String> set) {
        super(Map.class, false);
        this._ignoredEntries = (set == null || set.isEmpty()) ? null : set;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = mapSerializer._valueTypeSerializer;
        this._keySerializer = jsonSerializer;
        this._valueSerializer = jsonSerializer2;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
        this._property = beanProperty;
        this._filterId = mapSerializer._filterId;
        this._sortKeys = mapSerializer._sortKeys;
        this._suppressableValue = mapSerializer._suppressableValue;
        this._suppressNulls = mapSerializer._suppressNulls;
    }

    protected MapSerializer(MapSerializer mapSerializer, TypeSerializer typeSerializer, Object obj, boolean z) {
        super(Map.class, false);
        this._ignoredEntries = mapSerializer._ignoredEntries;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = typeSerializer;
        this._keySerializer = mapSerializer._keySerializer;
        this._valueSerializer = mapSerializer._valueSerializer;
        this._dynamicValueSerializers = mapSerializer._dynamicValueSerializers;
        this._property = mapSerializer._property;
        this._filterId = mapSerializer._filterId;
        this._sortKeys = mapSerializer._sortKeys;
        this._suppressableValue = obj;
        this._suppressNulls = z;
    }

    protected MapSerializer(MapSerializer mapSerializer, Object obj, boolean z) {
        super(Map.class, false);
        this._ignoredEntries = mapSerializer._ignoredEntries;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = mapSerializer._valueTypeSerializer;
        this._keySerializer = mapSerializer._keySerializer;
        this._valueSerializer = mapSerializer._valueSerializer;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
        this._property = mapSerializer._property;
        this._filterId = obj;
        this._sortKeys = z;
        this._suppressableValue = mapSerializer._suppressableValue;
        this._suppressNulls = mapSerializer._suppressNulls;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public MapSerializer _withValueTypeSerializer(TypeSerializer typeSerializer) {
        if (this._valueTypeSerializer == typeSerializer) {
            return this;
        }
        _ensureOverride("_withValueTypeSerializer");
        return new MapSerializer(this, typeSerializer, this._suppressableValue, this._suppressNulls);
    }

    public MapSerializer withResolved(BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, Set<String> set, boolean z) {
        _ensureOverride("withResolved");
        MapSerializer mapSerializer = new MapSerializer(this, beanProperty, jsonSerializer, jsonSerializer2, set);
        return z != mapSerializer._sortKeys ? new MapSerializer(mapSerializer, this._filterId, z) : mapSerializer;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public MapSerializer withFilterId(Object obj) {
        if (this._filterId == obj) {
            return this;
        }
        _ensureOverride("withFilterId");
        return new MapSerializer(this, obj, this._sortKeys);
    }

    public MapSerializer withContentInclusion(Object obj, boolean z) {
        if (obj == this._suppressableValue && z == this._suppressNulls) {
            return this;
        }
        _ensureOverride("withContentInclusion");
        return new MapSerializer(this, this._valueTypeSerializer, obj, z);
    }

    public static MapSerializer construct(Set<String> set, JavaType javaType, boolean z, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer, JsonSerializer<Object> jsonSerializer2, Object obj) {
        JavaType javaType2;
        JavaType javaType3;
        if (javaType == null) {
            javaType3 = UNSPECIFIED_TYPE;
            javaType2 = javaType3;
        } else {
            JavaType keyType = javaType.getKeyType();
            javaType2 = javaType.getContentType();
            javaType3 = keyType;
        }
        boolean z2 = false;
        if (z) {
            z2 = javaType2.getRawClass() == Object.class ? false : z;
        } else if (javaType2 != null && javaType2.isFinal()) {
            z2 = true;
        }
        MapSerializer mapSerializer = new MapSerializer(set, javaType3, javaType2, z2, typeSerializer, jsonSerializer, jsonSerializer2);
        return obj != null ? mapSerializer.withFilterId(obj) : mapSerializer;
    }

    protected void _ensureOverride(String str) {
        ClassUtil.verifyMustOverride(MapSerializer.class, this, str);
    }

    @Deprecated
    protected void _ensureOverride() {
        _ensureOverride("N/A");
    }

    @Deprecated
    protected MapSerializer(MapSerializer mapSerializer, TypeSerializer typeSerializer, Object obj) {
        this(mapSerializer, typeSerializer, obj, false);
    }

    @Deprecated
    public MapSerializer withContentInclusion(Object obj) {
        return new MapSerializer(this, this._valueTypeSerializer, obj, this._suppressNulls);
    }

    @Deprecated
    public static MapSerializer construct(String[] strArr, JavaType javaType, boolean z, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer, JsonSerializer<Object> jsonSerializer2, Object obj) {
        return construct(ArrayBuilders.arrayToSet(strArr), javaType, z, typeSerializer, jsonSerializer, jsonSerializer2, obj);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<?> jsonSerializer2;
        JsonSerializer<?> jsonSerializer3;
        boolean z;
        JsonInclude.Include contentInclusion;
        Object findFilterId;
        Boolean feature;
        AnnotationIntrospector annotationIntrospector = serializerProvider.getAnnotationIntrospector();
        Object obj = null;
        AnnotatedMember member = beanProperty == null ? null : beanProperty.getMember();
        if (_neitherNull(member, annotationIntrospector)) {
            Object findKeySerializer = annotationIntrospector.findKeySerializer(member);
            jsonSerializer2 = findKeySerializer != null ? serializerProvider.serializerInstance(member, findKeySerializer) : null;
            Object findContentSerializer = annotationIntrospector.findContentSerializer(member);
            jsonSerializer = findContentSerializer != null ? serializerProvider.serializerInstance(member, findContentSerializer) : null;
        } else {
            jsonSerializer2 = null;
            jsonSerializer = null;
        }
        if (jsonSerializer == null) {
            jsonSerializer = this._valueSerializer;
        }
        JsonSerializer<?> findContextualConvertingSerializer = findContextualConvertingSerializer(serializerProvider, beanProperty, jsonSerializer);
        JsonSerializer<?> findValueSerializer = (findContextualConvertingSerializer != null || !this._valueTypeIsStatic || this._valueType.isJavaLangObject()) ? findContextualConvertingSerializer : serializerProvider.findValueSerializer(this._valueType, beanProperty);
        if (jsonSerializer2 == null) {
            jsonSerializer2 = this._keySerializer;
        }
        if (jsonSerializer2 == null) {
            jsonSerializer3 = serializerProvider.findKeySerializer(this._keyType, beanProperty);
        } else {
            jsonSerializer3 = serializerProvider.handleSecondaryContextualization(jsonSerializer2, beanProperty);
        }
        Set<String> set = this._ignoredEntries;
        if (_neitherNull(member, annotationIntrospector)) {
            JsonIgnoreProperties.Value findPropertyIgnorals = annotationIntrospector.findPropertyIgnorals(member);
            if (findPropertyIgnorals != null) {
                Set<String> findIgnoredForSerialization = findPropertyIgnorals.findIgnoredForSerialization();
                if (_nonEmpty(findIgnoredForSerialization)) {
                    set = set == null ? new HashSet<>() : new HashSet<>(set);
                    for (String str : findIgnoredForSerialization) {
                        set.add(str);
                    }
                }
            }
            z = Boolean.TRUE.equals(annotationIntrospector.findSerializationSortAlphabetically(member));
        } else {
            set = set;
            z = false;
        }
        JsonFormat.Value findFormatOverrides = findFormatOverrides(serializerProvider, beanProperty, Map.class);
        if (findFormatOverrides != null && (feature = findFormatOverrides.getFeature(JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES)) != null) {
            z = feature.booleanValue();
        }
        MapSerializer withResolved = withResolved(beanProperty, jsonSerializer3, findValueSerializer, set, z);
        if (beanProperty == null) {
            return withResolved;
        }
        AnnotatedMember member2 = beanProperty.getMember();
        if (!(member2 == null || (findFilterId = annotationIntrospector.findFilterId(member2)) == null)) {
            withResolved = withResolved.withFilterId(findFilterId);
        }
        JsonInclude.Value findPropertyInclusion = beanProperty.findPropertyInclusion(serializerProvider.getConfig(), null);
        if (findPropertyInclusion == null || (contentInclusion = findPropertyInclusion.getContentInclusion()) == JsonInclude.Include.USE_DEFAULTS) {
            return withResolved;
        }
        boolean z2 = true;
        switch (contentInclusion) {
            case NON_DEFAULT:
                obj = BeanUtil.getDefaultValue(this._valueType);
                if (obj != null && obj.getClass().isArray()) {
                    obj = ArrayBuilders.getArrayComparator(obj);
                    break;
                }
                break;
            case NON_ABSENT:
                if (this._valueType.isReferenceType()) {
                    obj = MARKER_FOR_EMPTY;
                    break;
                }
                break;
            case NON_EMPTY:
                obj = MARKER_FOR_EMPTY;
                break;
            case CUSTOM:
                obj = serializerProvider.includeFilterInstance(null, findPropertyInclusion.getContentFilter());
                if (obj != null) {
                    z2 = serializerProvider.includeFilterSuppressNulls(obj);
                    break;
                }
                break;
            case NON_NULL:
                break;
            default:
                z2 = false;
                break;
        }
        return withResolved.withContentInclusion(obj, z2);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JavaType getContentType() {
        return this._valueType;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JsonSerializer<?> getContentSerializer() {
        return this._valueSerializer;
    }

    public boolean isEmpty(SerializerProvider serializerProvider, Map<?, ?> map) {
        if (map.isEmpty()) {
            return true;
        }
        Object obj = this._suppressableValue;
        if (obj == null && !this._suppressNulls) {
            return false;
        }
        JsonSerializer<Object> jsonSerializer = this._valueSerializer;
        boolean z = MARKER_FOR_EMPTY == obj;
        if (jsonSerializer != null) {
            for (Object obj2 : map.values()) {
                if (obj2 == null) {
                    if (!this._suppressNulls) {
                        return false;
                    }
                } else if (z) {
                    if (!jsonSerializer.isEmpty(serializerProvider, obj2)) {
                        return false;
                    }
                } else if (obj == null || !obj.equals(map)) {
                    return false;
                }
            }
            return true;
        }
        for (Object obj3 : map.values()) {
            if (obj3 != null) {
                try {
                    JsonSerializer<Object> a = a(serializerProvider, obj3);
                    if (z) {
                        if (!a.isEmpty(serializerProvider, obj3)) {
                            return false;
                        }
                    } else if (obj == null || !obj.equals(map)) {
                        return false;
                    }
                } catch (JsonMappingException unused) {
                    return false;
                }
            } else if (!this._suppressNulls) {
                return false;
            }
        }
        return true;
    }

    public boolean hasSingleElement(Map<?, ?> map) {
        return map.size() == 1;
    }

    public JsonSerializer<?> getKeySerializer() {
        return this._keySerializer;
    }

    public void serialize(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        PropertyFilter findPropertyFilter;
        jsonGenerator.writeStartObject(map);
        if (!map.isEmpty()) {
            Map<?, ?> _orderEntries = (this._sortKeys || serializerProvider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) ? _orderEntries(map, jsonGenerator, serializerProvider) : map;
            Object obj = this._filterId;
            if (obj != null && (findPropertyFilter = findPropertyFilter(serializerProvider, obj, _orderEntries)) != null) {
                serializeFilteredFields(_orderEntries, jsonGenerator, serializerProvider, findPropertyFilter, this._suppressableValue);
            } else if (this._suppressableValue != null || this._suppressNulls) {
                serializeOptionalFields(_orderEntries, jsonGenerator, serializerProvider, this._suppressableValue);
            } else {
                JsonSerializer<Object> jsonSerializer = this._valueSerializer;
                if (jsonSerializer != null) {
                    serializeFieldsUsing(_orderEntries, jsonGenerator, serializerProvider, jsonSerializer);
                } else {
                    serializeFields(_orderEntries, jsonGenerator, serializerProvider);
                }
            }
        }
        jsonGenerator.writeEndObject();
    }

    public void serializeWithType(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        PropertyFilter findPropertyFilter;
        jsonGenerator.setCurrentValue(map);
        WritableTypeId writeTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(map, JsonToken.START_OBJECT));
        if (!map.isEmpty()) {
            Map<?, ?> _orderEntries = (this._sortKeys || serializerProvider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) ? _orderEntries(map, jsonGenerator, serializerProvider) : map;
            Object obj = this._filterId;
            if (obj != null && (findPropertyFilter = findPropertyFilter(serializerProvider, obj, _orderEntries)) != null) {
                serializeFilteredFields(_orderEntries, jsonGenerator, serializerProvider, findPropertyFilter, this._suppressableValue);
            } else if (this._suppressableValue != null || this._suppressNulls) {
                serializeOptionalFields(_orderEntries, jsonGenerator, serializerProvider, this._suppressableValue);
            } else {
                JsonSerializer<Object> jsonSerializer = this._valueSerializer;
                if (jsonSerializer != null) {
                    serializeFieldsUsing(_orderEntries, jsonGenerator, serializerProvider, jsonSerializer);
                } else {
                    serializeFields(_orderEntries, jsonGenerator, serializerProvider);
                }
            }
        }
        typeSerializer.writeTypeSuffix(jsonGenerator, writeTypePrefix);
    }

    public void serializeFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Exception e;
        Object obj;
        if (this._valueTypeSerializer != null) {
            serializeTypedFields(map, jsonGenerator, serializerProvider, null);
            return;
        }
        JsonSerializer<Object> jsonSerializer = this._keySerializer;
        Set<String> set = this._ignoredEntries;
        try {
            obj = null;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                try {
                    Object value = entry.getValue();
                    obj = entry.getKey();
                    if (obj == null) {
                        serializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, jsonGenerator, serializerProvider);
                    } else if (set == null || !set.contains(obj)) {
                        jsonSerializer.serialize(obj, jsonGenerator, serializerProvider);
                    }
                    if (value == null) {
                        serializerProvider.defaultSerializeNull(jsonGenerator);
                    } else {
                        JsonSerializer<Object> jsonSerializer2 = this._valueSerializer;
                        if (jsonSerializer2 == null) {
                            jsonSerializer2 = a(serializerProvider, value);
                        }
                        jsonSerializer2.serialize(value, jsonGenerator, serializerProvider);
                    }
                } catch (Exception e2) {
                    e = e2;
                    wrapAndThrow(serializerProvider, e, map, String.valueOf(obj));
                    return;
                }
            }
        } catch (Exception e3) {
            e = e3;
            obj = null;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:13|(2:41|15)(1:(1:19)(2:50|49))|20|(3:46|22|(2:51|49)(2:45|24))(4:44|25|(1:27)|(3:48|29|(2:52|49))(2:47|(2:53|49)))|38|34|54|49|11) */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0070, code lost:
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0071, code lost:
        wrapAndThrow(r11, r3, r9, java.lang.String.valueOf(r4));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void serializeOptionalFields(java.util.Map<?, ?> r9, com.fasterxml.jackson.core.JsonGenerator r10, com.fasterxml.jackson.databind.SerializerProvider r11, java.lang.Object r12) throws java.io.IOException {
        /*
            r8 = this;
            com.fasterxml.jackson.databind.jsontype.TypeSerializer r0 = r8._valueTypeSerializer
            if (r0 == 0) goto L_0x0008
            r8.serializeTypedFields(r9, r10, r11, r12)
            return
        L_0x0008:
            java.util.Set<java.lang.String> r0 = r8._ignoredEntries
            java.lang.Object r1 = com.fasterxml.jackson.databind.ser.std.MapSerializer.MARKER_FOR_EMPTY
            if (r1 != r12) goto L_0x0010
            r1 = 1
            goto L_0x0011
        L_0x0010:
            r1 = 0
        L_0x0011:
            java.util.Set r2 = r9.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x0019:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0079
            java.lang.Object r3 = r2.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r4 = r3.getKey()
            if (r4 != 0) goto L_0x0034
            com.fasterxml.jackson.databind.JavaType r5 = r8._keyType
            com.fasterxml.jackson.databind.BeanProperty r6 = r8._property
            com.fasterxml.jackson.databind.JsonSerializer r5 = r11.findNullKeySerializer(r5, r6)
            goto L_0x003f
        L_0x0034:
            if (r0 == 0) goto L_0x003d
            boolean r5 = r0.contains(r4)
            if (r5 == 0) goto L_0x003d
            goto L_0x0019
        L_0x003d:
            com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r5 = r8._keySerializer
        L_0x003f:
            java.lang.Object r3 = r3.getValue()
            if (r3 != 0) goto L_0x004f
            boolean r6 = r8._suppressNulls
            if (r6 == 0) goto L_0x004a
            goto L_0x0019
        L_0x004a:
            com.fasterxml.jackson.databind.JsonSerializer r6 = r11.getDefaultNullValueSerializer()
            goto L_0x0069
        L_0x004f:
            com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r6 = r8._valueSerializer
            if (r6 != 0) goto L_0x0057
            com.fasterxml.jackson.databind.JsonSerializer r6 = r8.a(r11, r3)
        L_0x0057:
            if (r1 == 0) goto L_0x0060
            boolean r7 = r6.isEmpty(r11, r3)
            if (r7 == 0) goto L_0x0069
            goto L_0x0019
        L_0x0060:
            if (r12 == 0) goto L_0x0069
            boolean r7 = r12.equals(r3)
            if (r7 == 0) goto L_0x0069
            goto L_0x0019
        L_0x0069:
            r5.serialize(r4, r10, r11)     // Catch: Exception -> 0x0070
            r6.serialize(r3, r10, r11)     // Catch: Exception -> 0x0070
            goto L_0x0019
        L_0x0070:
            r3 = move-exception
            java.lang.String r4 = java.lang.String.valueOf(r4)
            r8.wrapAndThrow(r11, r3, r9, r4)
            goto L_0x0019
        L_0x0079:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeOptionalFields(java.util.Map, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider, java.lang.Object):void");
    }

    public void serializeFieldsUsing(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, JsonSerializer<Object> jsonSerializer) throws IOException {
        JsonSerializer<Object> jsonSerializer2 = this._keySerializer;
        Set<String> set = this._ignoredEntries;
        TypeSerializer typeSerializer = this._valueTypeSerializer;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            if (set == null || !set.contains(key)) {
                if (key == null) {
                    serializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, jsonGenerator, serializerProvider);
                } else {
                    jsonSerializer2.serialize(key, jsonGenerator, serializerProvider);
                }
                Object value = entry.getValue();
                if (value == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                } else if (typeSerializer == null) {
                    try {
                        jsonSerializer.serialize(value, jsonGenerator, serializerProvider);
                    } catch (Exception e) {
                        wrapAndThrow(serializerProvider, e, map, String.valueOf(key));
                    }
                } else {
                    jsonSerializer.serializeWithType(value, jsonGenerator, serializerProvider, typeSerializer);
                }
            }
        }
    }

    public void serializeFilteredFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, PropertyFilter propertyFilter, Object obj) throws IOException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> defaultNullValueSerializer;
        Set<String> set = this._ignoredEntries;
        MapProperty mapProperty = new MapProperty(this._valueTypeSerializer, this._property);
        boolean z = MARKER_FOR_EMPTY == obj;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            if (set == null || !set.contains(key)) {
                if (key == null) {
                    jsonSerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
                } else {
                    jsonSerializer = this._keySerializer;
                }
                Object value = entry.getValue();
                if (value != null) {
                    defaultNullValueSerializer = this._valueSerializer;
                    if (defaultNullValueSerializer == null) {
                        defaultNullValueSerializer = a(serializerProvider, value);
                    }
                    if (z) {
                        if (defaultNullValueSerializer.isEmpty(serializerProvider, value)) {
                        }
                    } else if (obj != null && obj.equals(value)) {
                    }
                } else if (!this._suppressNulls) {
                    defaultNullValueSerializer = serializerProvider.getDefaultNullValueSerializer();
                }
                mapProperty.reset(key, value, jsonSerializer, defaultNullValueSerializer);
                try {
                    propertyFilter.serializeAsField(map, jsonGenerator, serializerProvider, mapProperty);
                } catch (Exception e) {
                    wrapAndThrow(serializerProvider, e, map, String.valueOf(key));
                }
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:9|(2:37|11)(1:(1:15)(2:50|49))|16|(3:45|18|(2:51|49)(2:42|20))(4:41|21|(1:23)|(3:46|25|(2:52|49)(1:44))(2:43|(2:53|49)))|30|35|31|54|49|7) */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x006a, code lost:
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x006b, code lost:
        wrapAndThrow(r11, r3, r9, java.lang.String.valueOf(r4));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void serializeTypedFields(java.util.Map<?, ?> r9, com.fasterxml.jackson.core.JsonGenerator r10, com.fasterxml.jackson.databind.SerializerProvider r11, java.lang.Object r12) throws java.io.IOException {
        /*
            r8 = this;
            java.util.Set<java.lang.String> r0 = r8._ignoredEntries
            java.lang.Object r1 = com.fasterxml.jackson.databind.ser.std.MapSerializer.MARKER_FOR_EMPTY
            if (r1 != r12) goto L_0x0008
            r1 = 1
            goto L_0x0009
        L_0x0008:
            r1 = 0
        L_0x0009:
            java.util.Set r2 = r9.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x0011:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0073
            java.lang.Object r3 = r2.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r4 = r3.getKey()
            if (r4 != 0) goto L_0x002c
            com.fasterxml.jackson.databind.JavaType r5 = r8._keyType
            com.fasterxml.jackson.databind.BeanProperty r6 = r8._property
            com.fasterxml.jackson.databind.JsonSerializer r5 = r11.findNullKeySerializer(r5, r6)
            goto L_0x0037
        L_0x002c:
            if (r0 == 0) goto L_0x0035
            boolean r5 = r0.contains(r4)
            if (r5 == 0) goto L_0x0035
            goto L_0x0011
        L_0x0035:
            com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r5 = r8._keySerializer
        L_0x0037:
            java.lang.Object r3 = r3.getValue()
            if (r3 != 0) goto L_0x0047
            boolean r6 = r8._suppressNulls
            if (r6 == 0) goto L_0x0042
            goto L_0x0011
        L_0x0042:
            com.fasterxml.jackson.databind.JsonSerializer r6 = r11.getDefaultNullValueSerializer()
            goto L_0x0061
        L_0x0047:
            com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r6 = r8._valueSerializer
            if (r6 != 0) goto L_0x004f
            com.fasterxml.jackson.databind.JsonSerializer r6 = r8.a(r11, r3)
        L_0x004f:
            if (r1 == 0) goto L_0x0058
            boolean r7 = r6.isEmpty(r11, r3)
            if (r7 == 0) goto L_0x0061
            goto L_0x0011
        L_0x0058:
            if (r12 == 0) goto L_0x0061
            boolean r7 = r12.equals(r3)
            if (r7 == 0) goto L_0x0061
            goto L_0x0011
        L_0x0061:
            r5.serialize(r4, r10, r11)
            com.fasterxml.jackson.databind.jsontype.TypeSerializer r5 = r8._valueTypeSerializer     // Catch: Exception -> 0x006a
            r6.serializeWithType(r3, r10, r11, r5)     // Catch: Exception -> 0x006a
            goto L_0x0011
        L_0x006a:
            r3 = move-exception
            java.lang.String r4 = java.lang.String.valueOf(r4)
            r8.wrapAndThrow(r11, r3, r9, r4)
            goto L_0x0011
        L_0x0073:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeTypedFields(java.util.Map, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider, java.lang.Object):void");
    }

    public void serializeFilteredAnyProperties(SerializerProvider serializerProvider, JsonGenerator jsonGenerator, Object obj, Map<?, ?> map, PropertyFilter propertyFilter, Object obj2) throws IOException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> defaultNullValueSerializer;
        Set<String> set = this._ignoredEntries;
        MapProperty mapProperty = new MapProperty(this._valueTypeSerializer, this._property);
        boolean z = MARKER_FOR_EMPTY == obj2;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            if (set == null || !set.contains(key)) {
                if (key == null) {
                    jsonSerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
                } else {
                    jsonSerializer = this._keySerializer;
                }
                Object value = entry.getValue();
                if (value != null) {
                    defaultNullValueSerializer = this._valueSerializer;
                    if (defaultNullValueSerializer == null) {
                        defaultNullValueSerializer = a(serializerProvider, value);
                    }
                    if (z) {
                        if (defaultNullValueSerializer.isEmpty(serializerProvider, value)) {
                        }
                    } else if (obj2 != null && obj2.equals(value)) {
                    }
                } else if (!this._suppressNulls) {
                    defaultNullValueSerializer = serializerProvider.getDefaultNullValueSerializer();
                }
                mapProperty.reset(key, value, jsonSerializer, defaultNullValueSerializer);
                try {
                    propertyFilter.serializeAsField(obj, jsonGenerator, serializerProvider, mapProperty);
                } catch (Exception e) {
                    wrapAndThrow(serializerProvider, e, map, String.valueOf(key));
                }
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return createSchemaNode("object", true);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        JsonMapFormatVisitor expectMapFormat = jsonFormatVisitorWrapper.expectMapFormat(javaType);
        if (expectMapFormat != null) {
            expectMapFormat.keyFormat(this._keySerializer, this._keyType);
            JsonSerializer<Object> jsonSerializer = this._valueSerializer;
            if (jsonSerializer == null) {
                jsonSerializer = _findAndAddDynamic(this._dynamicValueSerializers, this._valueType, jsonFormatVisitorWrapper.getProvider());
            }
            expectMapFormat.valueFormat(jsonSerializer, this._valueType);
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

    protected Map<?, ?> _orderEntries(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (map instanceof SortedMap) {
            return map;
        }
        if (!_hasNullKey(map)) {
            return new TreeMap(map);
        }
        TreeMap treeMap = new TreeMap();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            if (key == null) {
                _writeNullKeyedEntry(jsonGenerator, serializerProvider, entry.getValue());
            } else {
                treeMap.put(key, entry.getValue());
            }
        }
        return treeMap;
    }

    protected boolean _hasNullKey(Map<?, ?> map) {
        return (map instanceof HashMap) && map.containsKey(null);
    }

    protected void _writeNullKeyedEntry(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, Object obj) throws IOException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> findNullKeySerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
        if (obj != null) {
            jsonSerializer = this._valueSerializer;
            if (jsonSerializer == null) {
                jsonSerializer = a(serializerProvider, obj);
            }
            Object obj2 = this._suppressableValue;
            if (obj2 == MARKER_FOR_EMPTY) {
                if (jsonSerializer.isEmpty(serializerProvider, obj)) {
                    return;
                }
            } else if (obj2 != null && obj2.equals(obj)) {
                return;
            }
        } else if (!this._suppressNulls) {
            jsonSerializer = serializerProvider.getDefaultNullValueSerializer();
        } else {
            return;
        }
        try {
            findNullKeySerializer.serialize(null, jsonGenerator, serializerProvider);
            jsonSerializer.serialize(obj, jsonGenerator, serializerProvider);
        } catch (Exception e) {
            wrapAndThrow(serializerProvider, e, obj, "");
        }
    }

    private final JsonSerializer<Object> a(SerializerProvider serializerProvider, Object obj) throws JsonMappingException {
        Class<?> cls = obj.getClass();
        JsonSerializer<Object> serializerFor = this._dynamicValueSerializers.serializerFor(cls);
        if (serializerFor != null) {
            return serializerFor;
        }
        if (this._valueType.hasGenericTypes()) {
            return _findAndAddDynamic(this._dynamicValueSerializers, serializerProvider.constructSpecializedType(this._valueType, cls), serializerProvider);
        }
        return _findAndAddDynamic(this._dynamicValueSerializers, cls, serializerProvider);
    }
}
