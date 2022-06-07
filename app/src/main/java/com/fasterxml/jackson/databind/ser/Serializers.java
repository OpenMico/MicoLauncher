package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;

/* loaded from: classes.dex */
public interface Serializers {
    JsonSerializer<?> findArraySerializer(SerializationConfig serializationConfig, ArrayType arrayType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer);

    JsonSerializer<?> findCollectionLikeSerializer(SerializationConfig serializationConfig, CollectionLikeType collectionLikeType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer);

    JsonSerializer<?> findCollectionSerializer(SerializationConfig serializationConfig, CollectionType collectionType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer);

    JsonSerializer<?> findMapLikeSerializer(SerializationConfig serializationConfig, MapLikeType mapLikeType, BeanDescription beanDescription, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2);

    JsonSerializer<?> findMapSerializer(SerializationConfig serializationConfig, MapType mapType, BeanDescription beanDescription, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2);

    JsonSerializer<?> findReferenceSerializer(SerializationConfig serializationConfig, ReferenceType referenceType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer);

    JsonSerializer<?> findSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription);

    /* loaded from: classes.dex */
    public static class Base implements Serializers {
        @Override // com.fasterxml.jackson.databind.ser.Serializers
        public JsonSerializer<?> findArraySerializer(SerializationConfig serializationConfig, ArrayType arrayType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.ser.Serializers
        public JsonSerializer<?> findCollectionLikeSerializer(SerializationConfig serializationConfig, CollectionLikeType collectionLikeType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.ser.Serializers
        public JsonSerializer<?> findCollectionSerializer(SerializationConfig serializationConfig, CollectionType collectionType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.ser.Serializers
        public JsonSerializer<?> findMapLikeSerializer(SerializationConfig serializationConfig, MapLikeType mapLikeType, BeanDescription beanDescription, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2) {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.ser.Serializers
        public JsonSerializer<?> findMapSerializer(SerializationConfig serializationConfig, MapType mapType, BeanDescription beanDescription, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2) {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.ser.Serializers
        public JsonSerializer<?> findSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription) {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.ser.Serializers
        public JsonSerializer<?> findReferenceSerializer(SerializationConfig serializationConfig, ReferenceType referenceType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
            return findSerializer(serializationConfig, referenceType, beanDescription);
        }
    }
}
