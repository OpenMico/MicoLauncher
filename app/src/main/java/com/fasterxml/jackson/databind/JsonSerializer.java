package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class JsonSerializer<T> implements JsonFormatVisitable {

    /* loaded from: classes.dex */
    public static abstract class None extends JsonSerializer<Object> {
    }

    public JsonSerializer<?> getDelegatee() {
        return null;
    }

    public Class<T> handledType() {
        return null;
    }

    public boolean isEmpty(SerializerProvider serializerProvider, T t) {
        return t == null;
    }

    public boolean isUnwrappingSerializer() {
        return false;
    }

    public abstract void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;

    public JsonSerializer<T> unwrappingSerializer(NameTransformer nameTransformer) {
        return this;
    }

    public boolean usesObjectId() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public JsonSerializer<?> withFilterId(Object obj) {
        return this;
    }

    public JsonSerializer<T> replaceDelegatee(JsonSerializer<?> jsonSerializer) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void serializeWithType(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        Class handledType = handledType();
        if (handledType == null) {
            handledType = t.getClass();
        }
        serializerProvider.reportBadDefinition(handledType, String.format("Type id handling not implemented for type %s (by serializer of type %s)", handledType.getName(), getClass().getName()));
    }

    @Deprecated
    public boolean isEmpty(T t) {
        return isEmpty(null, t);
    }

    public Iterator<PropertyWriter> properties() {
        return ClassUtil.emptyIterator();
    }

    @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        jsonFormatVisitorWrapper.expectAnyFormat(javaType);
    }
}
