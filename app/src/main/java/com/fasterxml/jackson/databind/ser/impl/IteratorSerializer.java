package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
import java.io.IOException;
import java.util.Iterator;

@JacksonStdImpl
/* loaded from: classes.dex */
public class IteratorSerializer extends AsArraySerializerBase<Iterator<?>> {
    public boolean hasSingleElement(Iterator<?> it) {
        return false;
    }

    public IteratorSerializer(JavaType javaType, boolean z, TypeSerializer typeSerializer) {
        super(Iterator.class, javaType, z, typeSerializer, (JsonSerializer<Object>) null);
    }

    public IteratorSerializer(IteratorSerializer iteratorSerializer, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, Boolean bool) {
        super(iteratorSerializer, beanProperty, typeSerializer, jsonSerializer, bool);
    }

    public boolean isEmpty(SerializerProvider serializerProvider, Iterator<?> it) {
        return !it.hasNext();
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new IteratorSerializer(this, this._property, typeSerializer, this._elementSerializer, this._unwrapSingle);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase
    public AsArraySerializerBase<Iterator<?>> withResolved(BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, Boolean bool) {
        return new IteratorSerializer(this, beanProperty, typeSerializer, jsonSerializer, bool);
    }

    public final void serialize(Iterator<?> it, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        serializeContents(it, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndArray();
    }

    public void serializeContents(Iterator<?> it, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (it.hasNext()) {
            JsonSerializer jsonSerializer = this._elementSerializer;
            if (jsonSerializer == null) {
                _serializeDynamicContents(it, jsonGenerator, serializerProvider);
                return;
            }
            TypeSerializer typeSerializer = this._valueTypeSerializer;
            do {
                Object next = it.next();
                if (next == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                } else if (typeSerializer == null) {
                    jsonSerializer.serialize(next, jsonGenerator, serializerProvider);
                } else {
                    jsonSerializer.serializeWithType(next, jsonGenerator, serializerProvider, typeSerializer);
                }
            } while (it.hasNext());
        }
    }

    protected void _serializeDynamicContents(Iterator<?> it, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        TypeSerializer typeSerializer = this._valueTypeSerializer;
        PropertySerializerMap propertySerializerMap = this._dynamicSerializers;
        do {
            Object next = it.next();
            if (next == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
            } else {
                Class<?> cls = next.getClass();
                JsonSerializer<Object> serializerFor = propertySerializerMap.serializerFor(cls);
                if (serializerFor == null) {
                    if (this._elementType.hasGenericTypes()) {
                        serializerFor = _findAndAddDynamic(propertySerializerMap, serializerProvider.constructSpecializedType(this._elementType, cls), serializerProvider);
                    } else {
                        serializerFor = _findAndAddDynamic(propertySerializerMap, cls, serializerProvider);
                    }
                    propertySerializerMap = this._dynamicSerializers;
                }
                if (typeSerializer == null) {
                    serializerFor.serialize(next, jsonGenerator, serializerProvider);
                } else {
                    serializerFor.serializeWithType(next, jsonGenerator, serializerProvider, typeSerializer);
                }
            }
        } while (it.hasNext());
    }
}
