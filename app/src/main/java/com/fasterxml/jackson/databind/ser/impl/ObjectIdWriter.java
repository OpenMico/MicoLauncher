package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;

/* loaded from: classes.dex */
public final class ObjectIdWriter {
    public final boolean alwaysAsId;
    public final ObjectIdGenerator<?> generator;
    public final JavaType idType;
    public final SerializableString propertyName;
    public final JsonSerializer<Object> serializer;

    protected ObjectIdWriter(JavaType javaType, SerializableString serializableString, ObjectIdGenerator<?> objectIdGenerator, JsonSerializer<?> jsonSerializer, boolean z) {
        this.idType = javaType;
        this.propertyName = serializableString;
        this.generator = objectIdGenerator;
        this.serializer = jsonSerializer;
        this.alwaysAsId = z;
    }

    public static ObjectIdWriter construct(JavaType javaType, PropertyName propertyName, ObjectIdGenerator<?> objectIdGenerator, boolean z) {
        SerializedString serializedString = null;
        String simpleName = propertyName == null ? null : propertyName.getSimpleName();
        if (simpleName != null) {
            serializedString = new SerializedString(simpleName);
        }
        return new ObjectIdWriter(javaType, serializedString, objectIdGenerator, null, z);
    }

    public ObjectIdWriter withSerializer(JsonSerializer<?> jsonSerializer) {
        return new ObjectIdWriter(this.idType, this.propertyName, this.generator, jsonSerializer, this.alwaysAsId);
    }

    public ObjectIdWriter withAlwaysAsId(boolean z) {
        return z == this.alwaysAsId ? this : new ObjectIdWriter(this.idType, this.propertyName, this.generator, this.serializer, z);
    }
}
