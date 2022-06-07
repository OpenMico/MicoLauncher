package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;
import com.fasterxml.jackson.databind.util.TypeKey;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class SerializerCache {
    private final HashMap<TypeKey, JsonSerializer<Object>> a = new HashMap<>(64);
    private final AtomicReference<ReadOnlyClassToSerializerMap> b = new AtomicReference<>();

    public ReadOnlyClassToSerializerMap getReadOnlyLookupMap() {
        ReadOnlyClassToSerializerMap readOnlyClassToSerializerMap = this.b.get();
        return readOnlyClassToSerializerMap != null ? readOnlyClassToSerializerMap : a();
    }

    private final synchronized ReadOnlyClassToSerializerMap a() {
        ReadOnlyClassToSerializerMap readOnlyClassToSerializerMap;
        readOnlyClassToSerializerMap = this.b.get();
        if (readOnlyClassToSerializerMap == null) {
            readOnlyClassToSerializerMap = ReadOnlyClassToSerializerMap.from(this.a);
            this.b.set(readOnlyClassToSerializerMap);
        }
        return readOnlyClassToSerializerMap;
    }

    public synchronized int size() {
        return this.a.size();
    }

    public JsonSerializer<Object> untypedValueSerializer(Class<?> cls) {
        JsonSerializer<Object> jsonSerializer;
        synchronized (this) {
            jsonSerializer = this.a.get(new TypeKey(cls, false));
        }
        return jsonSerializer;
    }

    public JsonSerializer<Object> untypedValueSerializer(JavaType javaType) {
        JsonSerializer<Object> jsonSerializer;
        synchronized (this) {
            jsonSerializer = this.a.get(new TypeKey(javaType, false));
        }
        return jsonSerializer;
    }

    public JsonSerializer<Object> typedValueSerializer(JavaType javaType) {
        JsonSerializer<Object> jsonSerializer;
        synchronized (this) {
            jsonSerializer = this.a.get(new TypeKey(javaType, true));
        }
        return jsonSerializer;
    }

    public JsonSerializer<Object> typedValueSerializer(Class<?> cls) {
        JsonSerializer<Object> jsonSerializer;
        synchronized (this) {
            jsonSerializer = this.a.get(new TypeKey(cls, true));
        }
        return jsonSerializer;
    }

    public void addTypedSerializer(JavaType javaType, JsonSerializer<Object> jsonSerializer) {
        synchronized (this) {
            if (this.a.put(new TypeKey(javaType, true), jsonSerializer) == null) {
                this.b.set(null);
            }
        }
    }

    public void addTypedSerializer(Class<?> cls, JsonSerializer<Object> jsonSerializer) {
        synchronized (this) {
            if (this.a.put(new TypeKey(cls, true), jsonSerializer) == null) {
                this.b.set(null);
            }
        }
    }

    public void addAndResolveNonTypedSerializer(Class<?> cls, JsonSerializer<Object> jsonSerializer, SerializerProvider serializerProvider) throws JsonMappingException {
        synchronized (this) {
            if (this.a.put(new TypeKey(cls, false), jsonSerializer) == null) {
                this.b.set(null);
            }
            if (jsonSerializer instanceof ResolvableSerializer) {
                ((ResolvableSerializer) jsonSerializer).resolve(serializerProvider);
            }
        }
    }

    public void addAndResolveNonTypedSerializer(JavaType javaType, JsonSerializer<Object> jsonSerializer, SerializerProvider serializerProvider) throws JsonMappingException {
        synchronized (this) {
            if (this.a.put(new TypeKey(javaType, false), jsonSerializer) == null) {
                this.b.set(null);
            }
            if (jsonSerializer instanceof ResolvableSerializer) {
                ((ResolvableSerializer) jsonSerializer).resolve(serializerProvider);
            }
        }
    }

    public void addAndResolveNonTypedSerializer(Class<?> cls, JavaType javaType, JsonSerializer<Object> jsonSerializer, SerializerProvider serializerProvider) throws JsonMappingException {
        synchronized (this) {
            JsonSerializer<Object> put = this.a.put(new TypeKey(cls, false), jsonSerializer);
            JsonSerializer<Object> put2 = this.a.put(new TypeKey(javaType, false), jsonSerializer);
            if (put == null || put2 == null) {
                this.b.set(null);
            }
            if (jsonSerializer instanceof ResolvableSerializer) {
                ((ResolvableSerializer) jsonSerializer).resolve(serializerProvider);
            }
        }
    }

    public synchronized void flush() {
        this.a.clear();
    }
}
