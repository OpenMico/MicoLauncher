package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

/* loaded from: classes.dex */
public class StdArraySerializers {
    protected static final HashMap<String, JsonSerializer<?>> _arraySerializers = new HashMap<>();

    static {
        _arraySerializers.put(boolean[].class.getName(), new BooleanArraySerializer());
        _arraySerializers.put(byte[].class.getName(), new ByteArraySerializer());
        _arraySerializers.put(char[].class.getName(), new CharArraySerializer());
        _arraySerializers.put(short[].class.getName(), new ShortArraySerializer());
        _arraySerializers.put(int[].class.getName(), new IntArraySerializer());
        _arraySerializers.put(long[].class.getName(), new LongArraySerializer());
        _arraySerializers.put(float[].class.getName(), new FloatArraySerializer());
        _arraySerializers.put(double[].class.getName(), new DoubleArraySerializer());
    }

    protected StdArraySerializers() {
    }

    public static JsonSerializer<?> findStandardImpl(Class<?> cls) {
        return _arraySerializers.get(cls.getName());
    }

    /* loaded from: classes.dex */
    protected static abstract class TypedPrimitiveArraySerializer<T> extends ArraySerializerBase<T> {
        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public final ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return this;
        }

        protected TypedPrimitiveArraySerializer(Class<T> cls) {
            super(cls);
        }

        protected TypedPrimitiveArraySerializer(TypedPrimitiveArraySerializer<T> typedPrimitiveArraySerializer, BeanProperty beanProperty, Boolean bool) {
            super(typedPrimitiveArraySerializer, beanProperty, bool);
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class BooleanArraySerializer extends ArraySerializerBase<boolean[]> {
        private static final JavaType a = TypeFactory.defaultInstance().uncheckedSimpleType(Boolean.class);

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return this;
        }

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        public BooleanArraySerializer() {
            super(boolean[].class);
        }

        protected BooleanArraySerializer(BooleanArraySerializer booleanArraySerializer, BeanProperty beanProperty, Boolean bool) {
            super(booleanArraySerializer, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.ArraySerializerBase
        public JsonSerializer<?> _withResolved(BeanProperty beanProperty, Boolean bool) {
            return new BooleanArraySerializer(this, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JavaType getContentType() {
            return a;
        }

        public boolean isEmpty(SerializerProvider serializerProvider, boolean[] zArr) {
            return zArr.length == 0;
        }

        public boolean hasSingleElement(boolean[] zArr) {
            return zArr.length == 1;
        }

        public final void serialize(boolean[] zArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            int length = zArr.length;
            if (length != 1 || !_shouldUnwrapSingle(serializerProvider)) {
                jsonGenerator.writeStartArray(length);
                jsonGenerator.setCurrentValue(zArr);
                serializeContents(zArr, jsonGenerator, serializerProvider);
                jsonGenerator.writeEndArray();
                return;
            }
            serializeContents(zArr, jsonGenerator, serializerProvider);
        }

        public void serializeContents(boolean[] zArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            for (boolean z : zArr) {
                jsonGenerator.writeBoolean(z);
            }
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            ObjectNode createSchemaNode = createSchemaNode("array", true);
            createSchemaNode.set("items", createSchemaNode("boolean"));
            return createSchemaNode;
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            visitArrayFormat(jsonFormatVisitorWrapper, javaType, JsonFormatTypes.BOOLEAN);
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class ShortArraySerializer extends TypedPrimitiveArraySerializer<short[]> {
        private static final JavaType a = TypeFactory.defaultInstance().uncheckedSimpleType(Short.TYPE);

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        public ShortArraySerializer() {
            super(short[].class);
        }

        public ShortArraySerializer(ShortArraySerializer shortArraySerializer, BeanProperty beanProperty, Boolean bool) {
            super(shortArraySerializer, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.ArraySerializerBase
        public JsonSerializer<?> _withResolved(BeanProperty beanProperty, Boolean bool) {
            return new ShortArraySerializer(this, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JavaType getContentType() {
            return a;
        }

        public boolean isEmpty(SerializerProvider serializerProvider, short[] sArr) {
            return sArr.length == 0;
        }

        public boolean hasSingleElement(short[] sArr) {
            return sArr.length == 1;
        }

        public final void serialize(short[] sArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            int length = sArr.length;
            if (length != 1 || !_shouldUnwrapSingle(serializerProvider)) {
                jsonGenerator.writeStartArray(length);
                jsonGenerator.setCurrentValue(sArr);
                serializeContents(sArr, jsonGenerator, serializerProvider);
                jsonGenerator.writeEndArray();
                return;
            }
            serializeContents(sArr, jsonGenerator, serializerProvider);
        }

        public void serializeContents(short[] sArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            for (short s : sArr) {
                jsonGenerator.writeNumber((int) s);
            }
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return createSchemaNode("array", true).set("items", createSchemaNode("integer"));
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            visitArrayFormat(jsonFormatVisitorWrapper, javaType, JsonFormatTypes.INTEGER);
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class CharArraySerializer extends StdSerializer<char[]> {
        public CharArraySerializer() {
            super(char[].class);
        }

        public boolean isEmpty(SerializerProvider serializerProvider, char[] cArr) {
            return cArr.length == 0;
        }

        public void serialize(char[] cArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (serializerProvider.isEnabled(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)) {
                jsonGenerator.writeStartArray(cArr.length);
                jsonGenerator.setCurrentValue(cArr);
                a(jsonGenerator, cArr);
                jsonGenerator.writeEndArray();
                return;
            }
            jsonGenerator.writeString(cArr, 0, cArr.length);
        }

        public void serializeWithType(char[] cArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
            WritableTypeId writableTypeId;
            if (serializerProvider.isEnabled(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)) {
                writableTypeId = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(cArr, JsonToken.START_ARRAY));
                a(jsonGenerator, cArr);
            } else {
                writableTypeId = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(cArr, JsonToken.VALUE_STRING));
                jsonGenerator.writeString(cArr, 0, cArr.length);
            }
            typeSerializer.writeTypeSuffix(jsonGenerator, writableTypeId);
        }

        private final void a(JsonGenerator jsonGenerator, char[] cArr) throws IOException {
            int length = cArr.length;
            for (int i = 0; i < length; i++) {
                jsonGenerator.writeString(cArr, i, 1);
            }
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            ObjectNode createSchemaNode = createSchemaNode("array", true);
            ObjectNode createSchemaNode2 = createSchemaNode("string");
            createSchemaNode2.put("type", "string");
            return createSchemaNode.set("items", createSchemaNode2);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            visitArrayFormat(jsonFormatVisitorWrapper, javaType, JsonFormatTypes.STRING);
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class IntArraySerializer extends ArraySerializerBase<int[]> {
        private static final JavaType a = TypeFactory.defaultInstance().uncheckedSimpleType(Integer.TYPE);

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return this;
        }

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        public IntArraySerializer() {
            super(int[].class);
        }

        protected IntArraySerializer(IntArraySerializer intArraySerializer, BeanProperty beanProperty, Boolean bool) {
            super(intArraySerializer, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.ArraySerializerBase
        public JsonSerializer<?> _withResolved(BeanProperty beanProperty, Boolean bool) {
            return new IntArraySerializer(this, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JavaType getContentType() {
            return a;
        }

        public boolean isEmpty(SerializerProvider serializerProvider, int[] iArr) {
            return iArr.length == 0;
        }

        public boolean hasSingleElement(int[] iArr) {
            return iArr.length == 1;
        }

        public final void serialize(int[] iArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (iArr.length != 1 || !_shouldUnwrapSingle(serializerProvider)) {
                jsonGenerator.setCurrentValue(iArr);
                jsonGenerator.writeArray(iArr, 0, iArr.length);
                return;
            }
            serializeContents(iArr, jsonGenerator, serializerProvider);
        }

        public void serializeContents(int[] iArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            for (int i : iArr) {
                jsonGenerator.writeNumber(i);
            }
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return createSchemaNode("array", true).set("items", createSchemaNode("integer"));
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            visitArrayFormat(jsonFormatVisitorWrapper, javaType, JsonFormatTypes.INTEGER);
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class LongArraySerializer extends TypedPrimitiveArraySerializer<long[]> {
        private static final JavaType a = TypeFactory.defaultInstance().uncheckedSimpleType(Long.TYPE);

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        public LongArraySerializer() {
            super(long[].class);
        }

        public LongArraySerializer(LongArraySerializer longArraySerializer, BeanProperty beanProperty, Boolean bool) {
            super(longArraySerializer, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.ArraySerializerBase
        public JsonSerializer<?> _withResolved(BeanProperty beanProperty, Boolean bool) {
            return new LongArraySerializer(this, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JavaType getContentType() {
            return a;
        }

        public boolean isEmpty(SerializerProvider serializerProvider, long[] jArr) {
            return jArr.length == 0;
        }

        public boolean hasSingleElement(long[] jArr) {
            return jArr.length == 1;
        }

        public final void serialize(long[] jArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (jArr.length != 1 || !_shouldUnwrapSingle(serializerProvider)) {
                jsonGenerator.setCurrentValue(jArr);
                jsonGenerator.writeArray(jArr, 0, jArr.length);
                return;
            }
            serializeContents(jArr, jsonGenerator, serializerProvider);
        }

        public void serializeContents(long[] jArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            for (long j : jArr) {
                jsonGenerator.writeNumber(j);
            }
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return createSchemaNode("array", true).set("items", createSchemaNode("number", true));
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            visitArrayFormat(jsonFormatVisitorWrapper, javaType, JsonFormatTypes.NUMBER);
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class FloatArraySerializer extends TypedPrimitiveArraySerializer<float[]> {
        private static final JavaType a = TypeFactory.defaultInstance().uncheckedSimpleType(Float.TYPE);

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        public FloatArraySerializer() {
            super(float[].class);
        }

        public FloatArraySerializer(FloatArraySerializer floatArraySerializer, BeanProperty beanProperty, Boolean bool) {
            super(floatArraySerializer, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.ArraySerializerBase
        public JsonSerializer<?> _withResolved(BeanProperty beanProperty, Boolean bool) {
            return new FloatArraySerializer(this, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JavaType getContentType() {
            return a;
        }

        public boolean isEmpty(SerializerProvider serializerProvider, float[] fArr) {
            return fArr.length == 0;
        }

        public boolean hasSingleElement(float[] fArr) {
            return fArr.length == 1;
        }

        public final void serialize(float[] fArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            int length = fArr.length;
            if (length != 1 || !_shouldUnwrapSingle(serializerProvider)) {
                jsonGenerator.writeStartArray(length);
                jsonGenerator.setCurrentValue(fArr);
                serializeContents(fArr, jsonGenerator, serializerProvider);
                jsonGenerator.writeEndArray();
                return;
            }
            serializeContents(fArr, jsonGenerator, serializerProvider);
        }

        public void serializeContents(float[] fArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            for (float f : fArr) {
                jsonGenerator.writeNumber(f);
            }
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return createSchemaNode("array", true).set("items", createSchemaNode("number"));
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            visitArrayFormat(jsonFormatVisitorWrapper, javaType, JsonFormatTypes.NUMBER);
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class DoubleArraySerializer extends ArraySerializerBase<double[]> {
        private static final JavaType a = TypeFactory.defaultInstance().uncheckedSimpleType(Double.TYPE);

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return this;
        }

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JsonSerializer<?> getContentSerializer() {
            return null;
        }

        public DoubleArraySerializer() {
            super(double[].class);
        }

        protected DoubleArraySerializer(DoubleArraySerializer doubleArraySerializer, BeanProperty beanProperty, Boolean bool) {
            super(doubleArraySerializer, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.ArraySerializerBase
        public JsonSerializer<?> _withResolved(BeanProperty beanProperty, Boolean bool) {
            return new DoubleArraySerializer(this, beanProperty, bool);
        }

        @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
        public JavaType getContentType() {
            return a;
        }

        public boolean isEmpty(SerializerProvider serializerProvider, double[] dArr) {
            return dArr.length == 0;
        }

        public boolean hasSingleElement(double[] dArr) {
            return dArr.length == 1;
        }

        public final void serialize(double[] dArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (dArr.length != 1 || !_shouldUnwrapSingle(serializerProvider)) {
                jsonGenerator.setCurrentValue(dArr);
                jsonGenerator.writeArray(dArr, 0, dArr.length);
                return;
            }
            serializeContents(dArr, jsonGenerator, serializerProvider);
        }

        public void serializeContents(double[] dArr, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            for (double d : dArr) {
                jsonGenerator.writeNumber(d);
            }
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return createSchemaNode("array", true).set("items", createSchemaNode("number"));
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            visitArrayFormat(jsonFormatVisitorWrapper, javaType, JsonFormatTypes.NUMBER);
        }
    }
}
