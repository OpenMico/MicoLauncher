package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public abstract class StdScalarSerializer<T> extends StdSerializer<T> {
    public StdScalarSerializer(Class<T> cls) {
        super(cls);
    }

    public StdScalarSerializer(Class<?> cls, boolean z) {
        super(cls);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        WritableTypeId writeTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(t, JsonToken.VALUE_STRING));
        serialize(t, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffix(jsonGenerator, writeTypePrefix);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        return createSchemaNode("string", true);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        visitStringFormat(jsonFormatVisitorWrapper, javaType);
    }
}
