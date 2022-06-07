package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/* loaded from: classes.dex */
public class NumberSerializers {
    protected NumberSerializers() {
    }

    public static void addAll(Map<String, JsonSerializer<?>> map) {
        map.put(Integer.class.getName(), new IntegerSerializer(Integer.class));
        map.put(Integer.TYPE.getName(), new IntegerSerializer(Integer.TYPE));
        map.put(Long.class.getName(), new LongSerializer(Long.class));
        map.put(Long.TYPE.getName(), new LongSerializer(Long.TYPE));
        map.put(Byte.class.getName(), IntLikeSerializer.a);
        map.put(Byte.TYPE.getName(), IntLikeSerializer.a);
        map.put(Short.class.getName(), ShortSerializer.a);
        map.put(Short.TYPE.getName(), ShortSerializer.a);
        map.put(Double.class.getName(), new DoubleSerializer(Double.class));
        map.put(Double.TYPE.getName(), new DoubleSerializer(Double.TYPE));
        map.put(Float.class.getName(), FloatSerializer.a);
        map.put(Float.TYPE.getName(), FloatSerializer.a);
    }

    /* loaded from: classes.dex */
    protected static abstract class Base<T> extends StdScalarSerializer<T> implements ContextualSerializer {
        protected final boolean _isInt;
        protected final JsonParser.NumberType _numberType;
        protected final String _schemaType;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected Base(Class<?> cls, JsonParser.NumberType numberType, String str) {
            super(cls, false);
            boolean z = false;
            this._numberType = numberType;
            this._schemaType = str;
            this._isInt = (numberType == JsonParser.NumberType.INT || numberType == JsonParser.NumberType.LONG || numberType == JsonParser.NumberType.BIG_INTEGER) ? true : z;
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return createSchemaNode(this._schemaType, true);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            if (this._isInt) {
                visitIntFormat(jsonFormatVisitorWrapper, javaType, this._numberType);
            } else {
                visitFloatFormat(jsonFormatVisitorWrapper, javaType, this._numberType);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
        public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
            JsonFormat.Value findFormatOverrides = findFormatOverrides(serializerProvider, beanProperty, handledType());
            return (findFormatOverrides == null || AnonymousClass1.a[findFormatOverrides.getShape().ordinal()] != 1) ? this : ToStringSerializer.instance;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.fasterxml.jackson.databind.ser.std.NumberSerializers$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[JsonFormat.Shape.values().length];

        static {
            try {
                a[JsonFormat.Shape.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static final class ShortSerializer extends Base<Object> {
        static final ShortSerializer a = new ShortSerializer();

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public /* bridge */ /* synthetic */ void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            super.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.ContextualSerializer
        public /* bridge */ /* synthetic */ JsonSerializer createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
            return super.createContextual(serializerProvider, beanProperty);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public /* bridge */ /* synthetic */ JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return super.getSchema(serializerProvider, type);
        }

        public ShortSerializer() {
            super(Short.class, JsonParser.NumberType.INT, "number");
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(((Short) obj).shortValue());
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static final class IntegerSerializer extends Base<Object> {
        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public /* bridge */ /* synthetic */ void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            super.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.ContextualSerializer
        public /* bridge */ /* synthetic */ JsonSerializer createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
            return super.createContextual(serializerProvider, beanProperty);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public /* bridge */ /* synthetic */ JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return super.getSchema(serializerProvider, type);
        }

        public IntegerSerializer(Class<?> cls) {
            super(cls, JsonParser.NumberType.INT, "integer");
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(((Integer) obj).intValue());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serializeWithType(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
            serialize(obj, jsonGenerator, serializerProvider);
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static final class IntLikeSerializer extends Base<Object> {
        static final IntLikeSerializer a = new IntLikeSerializer();

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public /* bridge */ /* synthetic */ void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            super.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.ContextualSerializer
        public /* bridge */ /* synthetic */ JsonSerializer createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
            return super.createContextual(serializerProvider, beanProperty);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public /* bridge */ /* synthetic */ JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return super.getSchema(serializerProvider, type);
        }

        public IntLikeSerializer() {
            super(Number.class, JsonParser.NumberType.INT, "integer");
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(((Number) obj).intValue());
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static final class LongSerializer extends Base<Object> {
        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public /* bridge */ /* synthetic */ void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            super.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.ContextualSerializer
        public /* bridge */ /* synthetic */ JsonSerializer createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
            return super.createContextual(serializerProvider, beanProperty);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public /* bridge */ /* synthetic */ JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return super.getSchema(serializerProvider, type);
        }

        public LongSerializer(Class<?> cls) {
            super(cls, JsonParser.NumberType.LONG, "number");
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(((Long) obj).longValue());
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static final class FloatSerializer extends Base<Object> {
        static final FloatSerializer a = new FloatSerializer();

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public /* bridge */ /* synthetic */ void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            super.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.ContextualSerializer
        public /* bridge */ /* synthetic */ JsonSerializer createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
            return super.createContextual(serializerProvider, beanProperty);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public /* bridge */ /* synthetic */ JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return super.getSchema(serializerProvider, type);
        }

        public FloatSerializer() {
            super(Float.class, JsonParser.NumberType.FLOAT, "number");
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(((Float) obj).floatValue());
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static final class DoubleSerializer extends Base<Object> {
        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public /* bridge */ /* synthetic */ void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            super.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.ContextualSerializer
        public /* bridge */ /* synthetic */ JsonSerializer createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
            return super.createContextual(serializerProvider, beanProperty);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.NumberSerializers.Base, com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public /* bridge */ /* synthetic */ JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return super.getSchema(serializerProvider, type);
        }

        public DoubleSerializer(Class<?> cls) {
            super(cls, JsonParser.NumberType.DOUBLE, "number");
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(((Double) obj).doubleValue());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serializeWithType(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
            serialize(obj, jsonGenerator, serializerProvider);
        }
    }
}
