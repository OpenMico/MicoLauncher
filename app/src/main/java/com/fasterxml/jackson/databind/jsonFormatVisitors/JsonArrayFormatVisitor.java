package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;

/* loaded from: classes.dex */
public interface JsonArrayFormatVisitor extends JsonFormatVisitorWithSerializerProvider {
    void itemsFormat(JsonFormatTypes jsonFormatTypes) throws JsonMappingException;

    void itemsFormat(JsonFormatVisitable jsonFormatVisitable, JavaType javaType) throws JsonMappingException;

    /* loaded from: classes.dex */
    public static class Base implements JsonArrayFormatVisitor {
        protected SerializerProvider _provider;

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor
        public void itemsFormat(JsonFormatTypes jsonFormatTypes) throws JsonMappingException {
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor
        public void itemsFormat(JsonFormatVisitable jsonFormatVisitable, JavaType javaType) throws JsonMappingException {
        }

        public Base() {
        }

        public Base(SerializerProvider serializerProvider) {
            this._provider = serializerProvider;
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWithSerializerProvider
        public SerializerProvider getProvider() {
            return this._provider;
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWithSerializerProvider
        public void setProvider(SerializerProvider serializerProvider) {
            this._provider = serializerProvider;
        }
    }
}
