package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import java.io.IOException;
import java.util.Iterator;

@JacksonStdImpl
/* loaded from: classes.dex */
public class IterableSerializer extends AsArraySerializerBase<Iterable<?>> {
    public IterableSerializer(JavaType javaType, boolean z, TypeSerializer typeSerializer) {
        super(Iterable.class, javaType, z, typeSerializer, (JsonSerializer<Object>) null);
    }

    public IterableSerializer(IterableSerializer iterableSerializer, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, Boolean bool) {
        super(iterableSerializer, beanProperty, typeSerializer, jsonSerializer, bool);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new IterableSerializer(this, this._property, typeSerializer, this._elementSerializer, this._unwrapSingle);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase
    /* renamed from: withResolved  reason: avoid collision after fix types in other method */
    public AsArraySerializerBase<Iterable<?>> withResolved2(BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, Boolean bool) {
        return new IterableSerializer(this, beanProperty, typeSerializer, jsonSerializer, bool);
    }

    public boolean isEmpty(SerializerProvider serializerProvider, Iterable<?> iterable) {
        return !iterable.iterator().hasNext();
    }

    public boolean hasSingleElement(Iterable<?> iterable) {
        if (iterable == null) {
            return false;
        }
        Iterator<?> it = iterable.iterator();
        if (!it.hasNext()) {
            return false;
        }
        it.next();
        return !it.hasNext();
    }

    public final void serialize(Iterable<?> iterable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (((this._unwrapSingle != null || !serializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) && this._unwrapSingle != Boolean.TRUE) || !hasSingleElement(iterable)) {
            jsonGenerator.writeStartArray();
            serializeContents(iterable, jsonGenerator, serializerProvider);
            jsonGenerator.writeEndArray();
            return;
        }
        serializeContents(iterable, jsonGenerator, serializerProvider);
    }

    public void serializeContents(Iterable<?> iterable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Iterator<?> it = iterable.iterator();
        if (it.hasNext()) {
            TypeSerializer typeSerializer = this._valueTypeSerializer;
            Class<?> cls = null;
            JsonSerializer<Object> jsonSerializer = null;
            do {
                Object next = it.next();
                if (next == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                } else {
                    JsonSerializer<Object> jsonSerializer2 = this._elementSerializer;
                    if (jsonSerializer2 == null) {
                        cls = next.getClass();
                        if (cls == cls) {
                            cls = cls;
                            jsonSerializer = jsonSerializer;
                        } else {
                            jsonSerializer = serializerProvider.findValueSerializer(cls, this._property);
                            jsonSerializer = jsonSerializer;
                        }
                    } else {
                        cls = cls;
                        jsonSerializer = jsonSerializer;
                        jsonSerializer = jsonSerializer2;
                    }
                    if (typeSerializer == null) {
                        jsonSerializer.serialize(next, jsonGenerator, serializerProvider);
                    } else {
                        jsonSerializer.serializeWithType(next, jsonGenerator, serializerProvider, typeSerializer);
                    }
                }
            } while (it.hasNext());
        }
    }
}
