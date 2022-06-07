package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public abstract class DefaultSerializerProvider extends SerializerProvider implements Serializable {
    private static final long serialVersionUID = 1;
    protected transient JsonGenerator _generator;
    protected transient ArrayList<ObjectIdGenerator<?>> _objectIdGenerators;
    protected transient Map<Object, WritableObjectId> _seenObjectIds;

    public abstract DefaultSerializerProvider createInstance(SerializationConfig serializationConfig, SerializerFactory serializerFactory);

    protected DefaultSerializerProvider() {
    }

    protected DefaultSerializerProvider(SerializerProvider serializerProvider, SerializationConfig serializationConfig, SerializerFactory serializerFactory) {
        super(serializerProvider, serializationConfig, serializerFactory);
    }

    protected DefaultSerializerProvider(DefaultSerializerProvider defaultSerializerProvider) {
        super(defaultSerializerProvider);
    }

    public DefaultSerializerProvider copy() {
        throw new IllegalStateException("DefaultSerializerProvider sub-class not overriding copy()");
    }

    @Override // com.fasterxml.jackson.databind.SerializerProvider
    public JsonSerializer<Object> serializerInstance(Annotated annotated, Object obj) throws JsonMappingException {
        JsonSerializer<?> jsonSerializer;
        JsonSerializer<?> jsonSerializer2 = null;
        if (obj == null) {
            return null;
        }
        if (obj instanceof JsonSerializer) {
            jsonSerializer = (JsonSerializer) obj;
        } else {
            if (!(obj instanceof Class)) {
                JavaType type = annotated.getType();
                reportBadDefinition(type, "AnnotationIntrospector returned serializer definition of type " + obj.getClass().getName() + "; expected type JsonSerializer or Class<JsonSerializer> instead");
            }
            Class<?> cls = (Class) obj;
            if (cls == JsonSerializer.None.class || ClassUtil.isBogusClass(cls)) {
                return null;
            }
            if (!JsonSerializer.class.isAssignableFrom(cls)) {
                JavaType type2 = annotated.getType();
                reportBadDefinition(type2, "AnnotationIntrospector returned Class " + cls.getName() + "; expected Class<JsonSerializer>");
            }
            HandlerInstantiator handlerInstantiator = this._config.getHandlerInstantiator();
            if (handlerInstantiator != null) {
                jsonSerializer2 = handlerInstantiator.serializerInstance(this._config, annotated, cls);
            }
            jsonSerializer = jsonSerializer2 == null ? (JsonSerializer) ClassUtil.createInstance(cls, this._config.canOverrideAccessModifiers()) : jsonSerializer2;
        }
        return _handleResolvable(jsonSerializer);
    }

    @Override // com.fasterxml.jackson.databind.SerializerProvider
    public Object includeFilterInstance(BeanPropertyDefinition beanPropertyDefinition, Class<?> cls) {
        Object obj = null;
        if (cls == null) {
            return null;
        }
        HandlerInstantiator handlerInstantiator = this._config.getHandlerInstantiator();
        if (handlerInstantiator != null) {
            obj = handlerInstantiator.includeFilterInstance(this._config, beanPropertyDefinition, cls);
        }
        return obj == null ? ClassUtil.createInstance(cls, this._config.canOverrideAccessModifiers()) : obj;
    }

    @Override // com.fasterxml.jackson.databind.SerializerProvider
    public boolean includeFilterSuppressNulls(Object obj) throws JsonMappingException {
        if (obj == null) {
            return true;
        }
        try {
            return obj.equals(null);
        } catch (Throwable th) {
            reportBadDefinition(obj.getClass(), String.format("Problem determining whether filter of type '%s' should filter out `null` values: (%s) %s", obj.getClass().getName(), th.getClass().getName(), ClassUtil.exceptionMessage(th)), th);
            return false;
        }
    }

    @Override // com.fasterxml.jackson.databind.SerializerProvider
    public WritableObjectId findObjectId(Object obj, ObjectIdGenerator<?> objectIdGenerator) {
        Map<Object, WritableObjectId> map = this._seenObjectIds;
        if (map == null) {
            this._seenObjectIds = _createObjectIdMap();
        } else {
            WritableObjectId writableObjectId = map.get(obj);
            if (writableObjectId != null) {
                return writableObjectId;
            }
        }
        ObjectIdGenerator<?> objectIdGenerator2 = null;
        ArrayList<ObjectIdGenerator<?>> arrayList = this._objectIdGenerators;
        if (arrayList != null) {
            int i = 0;
            int size = arrayList.size();
            while (true) {
                if (i >= size) {
                    break;
                }
                ObjectIdGenerator<?> objectIdGenerator3 = this._objectIdGenerators.get(i);
                if (objectIdGenerator3.canUseFor(objectIdGenerator)) {
                    objectIdGenerator2 = objectIdGenerator3;
                    break;
                }
                i++;
            }
        } else {
            this._objectIdGenerators = new ArrayList<>(8);
        }
        if (objectIdGenerator2 == null) {
            objectIdGenerator2 = objectIdGenerator.newForSerialization(this);
            this._objectIdGenerators.add(objectIdGenerator2);
        }
        WritableObjectId writableObjectId2 = new WritableObjectId(objectIdGenerator2);
        this._seenObjectIds.put(obj, writableObjectId2);
        return writableObjectId2;
    }

    protected Map<Object, WritableObjectId> _createObjectIdMap() {
        if (isEnabled(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID)) {
            return new HashMap();
        }
        return new IdentityHashMap();
    }

    public boolean hasSerializerFor(Class<?> cls, AtomicReference<Throwable> atomicReference) {
        if (cls == Object.class && !this._config.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            return true;
        }
        try {
            return _findExplicitUntypedSerializer(cls) != null;
        } catch (JsonMappingException e) {
            if (atomicReference != null) {
                atomicReference.set(e);
            }
            return false;
        } catch (RuntimeException e2) {
            if (atomicReference != null) {
                atomicReference.set(e2);
                return false;
            }
            throw e2;
        }
    }

    @Override // com.fasterxml.jackson.databind.SerializerProvider
    public JsonGenerator getGenerator() {
        return this._generator;
    }

    public void serializeValue(JsonGenerator jsonGenerator, Object obj) throws IOException {
        this._generator = jsonGenerator;
        if (obj == null) {
            _serializeNull(jsonGenerator);
            return;
        }
        Class<?> cls = obj.getClass();
        JsonSerializer<Object> findTypedValueSerializer = findTypedValueSerializer(cls, true, (BeanProperty) null);
        PropertyName fullRootName = this._config.getFullRootName();
        if (fullRootName == null) {
            if (this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE)) {
                a(jsonGenerator, obj, findTypedValueSerializer, this._config.findRootName(cls));
                return;
            }
        } else if (!fullRootName.isEmpty()) {
            a(jsonGenerator, obj, findTypedValueSerializer, fullRootName);
            return;
        }
        a(jsonGenerator, obj, findTypedValueSerializer);
    }

    public void serializeValue(JsonGenerator jsonGenerator, Object obj, JavaType javaType) throws IOException {
        this._generator = jsonGenerator;
        if (obj == null) {
            _serializeNull(jsonGenerator);
            return;
        }
        if (!javaType.getRawClass().isAssignableFrom(obj.getClass())) {
            _reportIncompatibleRootType(obj, javaType);
        }
        JsonSerializer<Object> findTypedValueSerializer = findTypedValueSerializer(javaType, true, (BeanProperty) null);
        PropertyName fullRootName = this._config.getFullRootName();
        if (fullRootName == null) {
            if (this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE)) {
                a(jsonGenerator, obj, findTypedValueSerializer, this._config.findRootName(javaType));
                return;
            }
        } else if (!fullRootName.isEmpty()) {
            a(jsonGenerator, obj, findTypedValueSerializer, fullRootName);
            return;
        }
        a(jsonGenerator, obj, findTypedValueSerializer);
    }

    public void serializeValue(JsonGenerator jsonGenerator, Object obj, JavaType javaType, JsonSerializer<Object> jsonSerializer) throws IOException {
        PropertyName propertyName;
        this._generator = jsonGenerator;
        if (obj == null) {
            _serializeNull(jsonGenerator);
            return;
        }
        if (javaType != null && !javaType.getRawClass().isAssignableFrom(obj.getClass())) {
            _reportIncompatibleRootType(obj, javaType);
        }
        if (jsonSerializer == null) {
            jsonSerializer = findTypedValueSerializer(javaType, true, (BeanProperty) null);
        }
        PropertyName fullRootName = this._config.getFullRootName();
        if (fullRootName == null) {
            if (this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE)) {
                if (javaType == null) {
                    propertyName = this._config.findRootName(obj.getClass());
                } else {
                    propertyName = this._config.findRootName(javaType);
                }
                a(jsonGenerator, obj, jsonSerializer, propertyName);
                return;
            }
        } else if (!fullRootName.isEmpty()) {
            a(jsonGenerator, obj, jsonSerializer, fullRootName);
            return;
        }
        a(jsonGenerator, obj, jsonSerializer);
    }

    public void serializePolymorphic(JsonGenerator jsonGenerator, Object obj, JavaType javaType, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer) throws IOException {
        boolean z;
        this._generator = jsonGenerator;
        if (obj == null) {
            _serializeNull(jsonGenerator);
            return;
        }
        if (javaType != null && !javaType.getRawClass().isAssignableFrom(obj.getClass())) {
            _reportIncompatibleRootType(obj, javaType);
        }
        if (jsonSerializer == null) {
            if (javaType == null || !javaType.isContainerType()) {
                jsonSerializer = findValueSerializer(obj.getClass(), (BeanProperty) null);
            } else {
                jsonSerializer = findValueSerializer(javaType, (BeanProperty) null);
            }
        }
        PropertyName fullRootName = this._config.getFullRootName();
        if (fullRootName == null) {
            z = this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
            if (z) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeFieldName(this._config.findRootName(obj.getClass()).simpleAsEncoded(this._config));
            }
        } else if (fullRootName.isEmpty()) {
            z = false;
        } else {
            z = true;
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName(fullRootName.getSimpleName());
        }
        try {
            jsonSerializer.serializeWithType(obj, jsonGenerator, this, typeSerializer);
            if (z) {
                jsonGenerator.writeEndObject();
            }
        } catch (Exception e) {
            throw a(jsonGenerator, e);
        }
    }

    private final void a(JsonGenerator jsonGenerator, Object obj, JsonSerializer<Object> jsonSerializer, PropertyName propertyName) throws IOException {
        try {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName(propertyName.simpleAsEncoded(this._config));
            jsonSerializer.serialize(obj, jsonGenerator, this);
            jsonGenerator.writeEndObject();
        } catch (Exception e) {
            throw a(jsonGenerator, e);
        }
    }

    private final void a(JsonGenerator jsonGenerator, Object obj, JsonSerializer<Object> jsonSerializer) throws IOException {
        try {
            jsonSerializer.serialize(obj, jsonGenerator, this);
        } catch (Exception e) {
            throw a(jsonGenerator, e);
        }
    }

    protected void _serializeNull(JsonGenerator jsonGenerator) throws IOException {
        try {
            getDefaultNullValueSerializer().serialize(null, jsonGenerator, this);
        } catch (Exception e) {
            throw a(jsonGenerator, e);
        }
    }

    private IOException a(JsonGenerator jsonGenerator, Exception exc) {
        if (exc instanceof IOException) {
            return (IOException) exc;
        }
        String exceptionMessage = ClassUtil.exceptionMessage(exc);
        if (exceptionMessage == null) {
            exceptionMessage = "[no message for " + exc.getClass().getName() + "]";
        }
        return new JsonMappingException(jsonGenerator, exceptionMessage, exc);
    }

    public int cachedSerializersCount() {
        return this._serializerCache.size();
    }

    public void flushCachedSerializers() {
        this._serializerCache.flush();
    }

    public void acceptJsonFormatVisitor(JavaType javaType, JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        if (javaType != null) {
            jsonFormatVisitorWrapper.setProvider(this);
            findValueSerializer(javaType, (BeanProperty) null).acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
            return;
        }
        throw new IllegalArgumentException("A class must be provided");
    }

    @Deprecated
    public JsonSchema generateJsonSchema(Class<?> cls) throws JsonMappingException {
        JsonSerializer<Object> findValueSerializer = findValueSerializer(cls, (BeanProperty) null);
        JsonNode schema = findValueSerializer instanceof SchemaAware ? ((SchemaAware) findValueSerializer).getSchema(this, null) : JsonSchema.getDefaultSchemaNode();
        if (schema instanceof ObjectNode) {
            return new JsonSchema((ObjectNode) schema);
        }
        throw new IllegalArgumentException("Class " + cls.getName() + " would not be serialized as a JSON object and therefore has no schema");
    }

    /* loaded from: classes.dex */
    public static final class Impl extends DefaultSerializerProvider {
        private static final long serialVersionUID = 1;

        public Impl() {
        }

        public Impl(Impl impl) {
            super(impl);
        }

        protected Impl(SerializerProvider serializerProvider, SerializationConfig serializationConfig, SerializerFactory serializerFactory) {
            super(serializerProvider, serializationConfig, serializerFactory);
        }

        @Override // com.fasterxml.jackson.databind.ser.DefaultSerializerProvider
        public DefaultSerializerProvider copy() {
            if (getClass() != Impl.class) {
                return DefaultSerializerProvider.super.copy();
            }
            return new Impl(this);
        }

        @Override // com.fasterxml.jackson.databind.ser.DefaultSerializerProvider
        public Impl createInstance(SerializationConfig serializationConfig, SerializerFactory serializerFactory) {
            return new Impl(this, serializationConfig, serializerFactory);
        }
    }
}
