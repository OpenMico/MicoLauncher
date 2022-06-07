package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;
import java.util.HashMap;

/* loaded from: classes.dex */
public class SimpleKeyDeserializers implements KeyDeserializers, Serializable {
    private static final long serialVersionUID = 1;
    protected HashMap<ClassKey, KeyDeserializer> _classMappings = null;

    public SimpleKeyDeserializers addDeserializer(Class<?> cls, KeyDeserializer keyDeserializer) {
        if (this._classMappings == null) {
            this._classMappings = new HashMap<>();
        }
        this._classMappings.put(new ClassKey(cls), keyDeserializer);
        return this;
    }

    @Override // com.fasterxml.jackson.databind.deser.KeyDeserializers
    public KeyDeserializer findKeyDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, BeanDescription beanDescription) {
        HashMap<ClassKey, KeyDeserializer> hashMap = this._classMappings;
        if (hashMap == null) {
            return null;
        }
        return hashMap.get(new ClassKey(javaType.getRawClass()));
    }
}
