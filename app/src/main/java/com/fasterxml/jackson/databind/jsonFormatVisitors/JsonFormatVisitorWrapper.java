package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;

/* loaded from: classes.dex */
public interface JsonFormatVisitorWrapper extends JsonFormatVisitorWithSerializerProvider {
    JsonAnyFormatVisitor expectAnyFormat(JavaType javaType) throws JsonMappingException;

    JsonArrayFormatVisitor expectArrayFormat(JavaType javaType) throws JsonMappingException;

    JsonBooleanFormatVisitor expectBooleanFormat(JavaType javaType) throws JsonMappingException;

    JsonIntegerFormatVisitor expectIntegerFormat(JavaType javaType) throws JsonMappingException;

    JsonMapFormatVisitor expectMapFormat(JavaType javaType) throws JsonMappingException;

    JsonNullFormatVisitor expectNullFormat(JavaType javaType) throws JsonMappingException;

    JsonNumberFormatVisitor expectNumberFormat(JavaType javaType) throws JsonMappingException;

    JsonObjectFormatVisitor expectObjectFormat(JavaType javaType) throws JsonMappingException;

    JsonStringFormatVisitor expectStringFormat(JavaType javaType) throws JsonMappingException;

    /* loaded from: classes.dex */
    public static class Base implements JsonFormatVisitorWrapper {
        protected SerializerProvider _provider;

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
        public JsonAnyFormatVisitor expectAnyFormat(JavaType javaType) throws JsonMappingException {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
        public JsonArrayFormatVisitor expectArrayFormat(JavaType javaType) throws JsonMappingException {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
        public JsonBooleanFormatVisitor expectBooleanFormat(JavaType javaType) throws JsonMappingException {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
        public JsonIntegerFormatVisitor expectIntegerFormat(JavaType javaType) throws JsonMappingException {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
        public JsonMapFormatVisitor expectMapFormat(JavaType javaType) throws JsonMappingException {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
        public JsonNullFormatVisitor expectNullFormat(JavaType javaType) throws JsonMappingException {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
        public JsonNumberFormatVisitor expectNumberFormat(JavaType javaType) throws JsonMappingException {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
        public JsonObjectFormatVisitor expectObjectFormat(JavaType javaType) throws JsonMappingException {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
        public JsonStringFormatVisitor expectStringFormat(JavaType javaType) throws JsonMappingException {
            return null;
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
