package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;

/* loaded from: classes.dex */
public class NumberDeserializers {
    private static final HashSet<String> a = new HashSet<>();

    static {
        for (Class cls : new Class[]{Boolean.class, Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class, Number.class, BigDecimal.class, BigInteger.class}) {
            a.add(cls.getName());
        }
    }

    public static JsonDeserializer<?> find(Class<?> cls, String str) {
        if (cls.isPrimitive()) {
            if (cls == Integer.TYPE) {
                return IntegerDeserializer.a;
            }
            if (cls == Boolean.TYPE) {
                return BooleanDeserializer.a;
            }
            if (cls == Long.TYPE) {
                return LongDeserializer.a;
            }
            if (cls == Double.TYPE) {
                return DoubleDeserializer.a;
            }
            if (cls == Character.TYPE) {
                return CharacterDeserializer.a;
            }
            if (cls == Byte.TYPE) {
                return ByteDeserializer.a;
            }
            if (cls == Short.TYPE) {
                return ShortDeserializer.a;
            }
            if (cls == Float.TYPE) {
                return FloatDeserializer.a;
            }
        } else if (!a.contains(str)) {
            return null;
        } else {
            if (cls == Integer.class) {
                return IntegerDeserializer.b;
            }
            if (cls == Boolean.class) {
                return BooleanDeserializer.b;
            }
            if (cls == Long.class) {
                return LongDeserializer.b;
            }
            if (cls == Double.class) {
                return DoubleDeserializer.b;
            }
            if (cls == Character.class) {
                return CharacterDeserializer.b;
            }
            if (cls == Byte.class) {
                return ByteDeserializer.b;
            }
            if (cls == Short.class) {
                return ShortDeserializer.b;
            }
            if (cls == Float.class) {
                return FloatDeserializer.b;
            }
            if (cls == Number.class) {
                return NumberDeserializer.instance;
            }
            if (cls == BigDecimal.class) {
                return BigDecimalDeserializer.instance;
            }
            if (cls == BigInteger.class) {
                return BigIntegerDeserializer.instance;
            }
        }
        throw new IllegalArgumentException("Internal error: can't find deserializer for " + cls.getName());
    }

    /* loaded from: classes.dex */
    protected static abstract class PrimitiveOrWrapperDeserializer<T> extends StdScalarDeserializer<T> {
        private static final long serialVersionUID = 1;
        protected final T _emptyValue;
        protected final T _nullValue;
        protected final boolean _primitive;

        protected PrimitiveOrWrapperDeserializer(Class<T> cls, T t, T t2) {
            super((Class<?>) cls);
            this._nullValue = t;
            this._emptyValue = t2;
            this._primitive = cls.isPrimitive();
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer, com.fasterxml.jackson.databind.deser.NullValueProvider
        public AccessPattern getNullAccessPattern() {
            if (this._primitive) {
                return AccessPattern.DYNAMIC;
            }
            if (this._nullValue == null) {
                return AccessPattern.ALWAYS_NULL;
            }
            return AccessPattern.CONSTANT;
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer, com.fasterxml.jackson.databind.deser.NullValueProvider
        public final T getNullValue(DeserializationContext deserializationContext) throws JsonMappingException {
            if (this._primitive && deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
                deserializationContext.reportInputMismatch(this, "Cannot map `null` into type %s (set DeserializationConfig.DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES to 'false' to allow)", handledType().toString());
            }
            return this._nullValue;
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Object getEmptyValue(DeserializationContext deserializationContext) throws JsonMappingException {
            return this._emptyValue;
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static final class BooleanDeserializer extends PrimitiveOrWrapperDeserializer<Boolean> {
        static final BooleanDeserializer a = new BooleanDeserializer(Boolean.TYPE, Boolean.FALSE);
        static final BooleanDeserializer b = new BooleanDeserializer(Boolean.class, null);
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public /* bridge */ /* synthetic */ Object getEmptyValue(DeserializationContext deserializationContext) throws JsonMappingException {
            return super.getEmptyValue(deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer, com.fasterxml.jackson.databind.deser.NullValueProvider
        public /* bridge */ /* synthetic */ AccessPattern getNullAccessPattern() {
            return super.getNullAccessPattern();
        }

        public BooleanDeserializer(Class<Boolean> cls, Boolean bool) {
            super(cls, bool, Boolean.FALSE);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (currentToken == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            return _parseBoolean(jsonParser, deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public Boolean deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (currentToken == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            return _parseBoolean(jsonParser, deserializationContext);
        }

        protected final Boolean _parseBoolean(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_NULL) {
                return (Boolean) _coerceNullToken(deserializationContext, this._primitive);
            }
            if (currentToken == JsonToken.START_ARRAY) {
                return _deserializeFromArray(jsonParser, deserializationContext);
            }
            if (currentToken == JsonToken.VALUE_NUMBER_INT) {
                return Boolean.valueOf(_parseBooleanFromInt(jsonParser, deserializationContext));
            }
            if (currentToken == JsonToken.VALUE_STRING) {
                String trim = jsonParser.getText().trim();
                if ("true".equals(trim) || "True".equals(trim)) {
                    _verifyStringForScalarCoercion(deserializationContext, trim);
                    return Boolean.TRUE;
                } else if ("false".equals(trim) || "False".equals(trim)) {
                    _verifyStringForScalarCoercion(deserializationContext, trim);
                    return Boolean.FALSE;
                } else if (trim.length() == 0) {
                    return (Boolean) _coerceEmptyString(deserializationContext, this._primitive);
                } else {
                    if (_hasTextualNull(trim)) {
                        return (Boolean) _coerceTextualNull(deserializationContext, this._primitive);
                    }
                    return (Boolean) deserializationContext.handleWeirdStringValue(this._valueClass, trim, "only \"true\" or \"false\" recognized", new Object[0]);
                }
            } else if (currentToken == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            } else {
                if (currentToken == JsonToken.VALUE_FALSE) {
                    return Boolean.FALSE;
                }
                return (Boolean) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class ByteDeserializer extends PrimitiveOrWrapperDeserializer<Byte> {
        static final ByteDeserializer a = new ByteDeserializer(Byte.TYPE, (byte) 0);
        static final ByteDeserializer b = new ByteDeserializer(Byte.class, null);
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public /* bridge */ /* synthetic */ Object getEmptyValue(DeserializationContext deserializationContext) throws JsonMappingException {
            return super.getEmptyValue(deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer, com.fasterxml.jackson.databind.deser.NullValueProvider
        public /* bridge */ /* synthetic */ AccessPattern getNullAccessPattern() {
            return super.getNullAccessPattern();
        }

        public ByteDeserializer(Class<Byte> cls, Byte b2) {
            super(cls, b2, (byte) 0);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Byte deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
                return Byte.valueOf(jsonParser.getByteValue());
            }
            return _parseByte(jsonParser, deserializationContext);
        }

        protected Byte _parseByte(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_STRING) {
                String trim = jsonParser.getText().trim();
                if (_hasTextualNull(trim)) {
                    return (Byte) _coerceTextualNull(deserializationContext, this._primitive);
                }
                if (trim.length() == 0) {
                    return (Byte) _coerceEmptyString(deserializationContext, this._primitive);
                }
                _verifyStringForScalarCoercion(deserializationContext, trim);
                try {
                    int parseInt = NumberInput.parseInt(trim);
                    if (_byteOverflow(parseInt)) {
                        return (Byte) deserializationContext.handleWeirdStringValue(this._valueClass, trim, "overflow, value cannot be represented as 8-bit value", new Object[0]);
                    }
                    return Byte.valueOf((byte) parseInt);
                } catch (IllegalArgumentException unused) {
                    return (Byte) deserializationContext.handleWeirdStringValue(this._valueClass, trim, "not a valid Byte value", new Object[0]);
                }
            } else if (currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
                if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
                    _failDoubleToIntCoercion(jsonParser, deserializationContext, "Byte");
                }
                return Byte.valueOf(jsonParser.getByteValue());
            } else if (currentToken == JsonToken.VALUE_NULL) {
                return (Byte) _coerceNullToken(deserializationContext, this._primitive);
            } else {
                if (currentToken == JsonToken.START_ARRAY) {
                    return _deserializeFromArray(jsonParser, deserializationContext);
                }
                if (currentToken == JsonToken.VALUE_NUMBER_INT) {
                    return Byte.valueOf(jsonParser.getByteValue());
                }
                return (Byte) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class ShortDeserializer extends PrimitiveOrWrapperDeserializer<Short> {
        static final ShortDeserializer a = new ShortDeserializer(Short.TYPE, 0);
        static final ShortDeserializer b = new ShortDeserializer(Short.class, null);
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public /* bridge */ /* synthetic */ Object getEmptyValue(DeserializationContext deserializationContext) throws JsonMappingException {
            return super.getEmptyValue(deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer, com.fasterxml.jackson.databind.deser.NullValueProvider
        public /* bridge */ /* synthetic */ AccessPattern getNullAccessPattern() {
            return super.getNullAccessPattern();
        }

        public ShortDeserializer(Class<Short> cls, Short sh) {
            super(cls, sh, (short) 0);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Short deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return _parseShort(jsonParser, deserializationContext);
        }

        protected Short _parseShort(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_NUMBER_INT) {
                return Short.valueOf(jsonParser.getShortValue());
            }
            if (currentToken == JsonToken.VALUE_STRING) {
                String trim = jsonParser.getText().trim();
                if (trim.length() == 0) {
                    return (Short) _coerceEmptyString(deserializationContext, this._primitive);
                }
                if (_hasTextualNull(trim)) {
                    return (Short) _coerceTextualNull(deserializationContext, this._primitive);
                }
                _verifyStringForScalarCoercion(deserializationContext, trim);
                try {
                    int parseInt = NumberInput.parseInt(trim);
                    if (_shortOverflow(parseInt)) {
                        return (Short) deserializationContext.handleWeirdStringValue(this._valueClass, trim, "overflow, value cannot be represented as 16-bit value", new Object[0]);
                    }
                    return Short.valueOf((short) parseInt);
                } catch (IllegalArgumentException unused) {
                    return (Short) deserializationContext.handleWeirdStringValue(this._valueClass, trim, "not a valid Short value", new Object[0]);
                }
            } else if (currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
                if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
                    _failDoubleToIntCoercion(jsonParser, deserializationContext, "Short");
                }
                return Short.valueOf(jsonParser.getShortValue());
            } else if (currentToken == JsonToken.VALUE_NULL) {
                return (Short) _coerceNullToken(deserializationContext, this._primitive);
            } else {
                if (currentToken == JsonToken.START_ARRAY) {
                    return _deserializeFromArray(jsonParser, deserializationContext);
                }
                return (Short) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class CharacterDeserializer extends PrimitiveOrWrapperDeserializer<Character> {
        static final CharacterDeserializer a = new CharacterDeserializer(Character.TYPE, 0);
        static final CharacterDeserializer b = new CharacterDeserializer(Character.class, null);
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public /* bridge */ /* synthetic */ Object getEmptyValue(DeserializationContext deserializationContext) throws JsonMappingException {
            return super.getEmptyValue(deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer, com.fasterxml.jackson.databind.deser.NullValueProvider
        public /* bridge */ /* synthetic */ AccessPattern getNullAccessPattern() {
            return super.getNullAccessPattern();
        }

        public CharacterDeserializer(Class<Character> cls, Character ch) {
            super(cls, ch, (char) 0);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Character deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            int currentTokenId = jsonParser.getCurrentTokenId();
            if (currentTokenId == 3) {
                return _deserializeFromArray(jsonParser, deserializationContext);
            }
            if (currentTokenId == 11) {
                return (Character) _coerceNullToken(deserializationContext, this._primitive);
            }
            switch (currentTokenId) {
                case 6:
                    String text = jsonParser.getText();
                    if (text.length() == 1) {
                        return Character.valueOf(text.charAt(0));
                    }
                    if (text.length() == 0) {
                        return (Character) _coerceEmptyString(deserializationContext, this._primitive);
                    }
                    break;
                case 7:
                    _verifyNumberForScalarCoercion(deserializationContext, jsonParser);
                    int intValue = jsonParser.getIntValue();
                    if (intValue >= 0 && intValue <= 65535) {
                        return Character.valueOf((char) intValue);
                    }
                    break;
            }
            return (Character) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static final class IntegerDeserializer extends PrimitiveOrWrapperDeserializer<Integer> {
        static final IntegerDeserializer a = new IntegerDeserializer(Integer.TYPE, 0);
        static final IntegerDeserializer b = new IntegerDeserializer(Integer.class, null);
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public boolean isCachable() {
            return true;
        }

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public /* bridge */ /* synthetic */ Object getEmptyValue(DeserializationContext deserializationContext) throws JsonMappingException {
            return super.getEmptyValue(deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer, com.fasterxml.jackson.databind.deser.NullValueProvider
        public /* bridge */ /* synthetic */ AccessPattern getNullAccessPattern() {
            return super.getNullAccessPattern();
        }

        public IntegerDeserializer(Class<Integer> cls, Integer num) {
            super(cls, num, 0);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
                return Integer.valueOf(jsonParser.getIntValue());
            }
            return _parseInteger(jsonParser, deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public Integer deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
            if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
                return Integer.valueOf(jsonParser.getIntValue());
            }
            return _parseInteger(jsonParser, deserializationContext);
        }

        protected final Integer _parseInteger(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            int currentTokenId = jsonParser.getCurrentTokenId();
            if (currentTokenId == 3) {
                return _deserializeFromArray(jsonParser, deserializationContext);
            }
            if (currentTokenId == 11) {
                return (Integer) _coerceNullToken(deserializationContext, this._primitive);
            }
            switch (currentTokenId) {
                case 6:
                    String trim = jsonParser.getText().trim();
                    int length = trim.length();
                    if (length == 0) {
                        return (Integer) _coerceEmptyString(deserializationContext, this._primitive);
                    }
                    if (_hasTextualNull(trim)) {
                        return (Integer) _coerceTextualNull(deserializationContext, this._primitive);
                    }
                    _verifyStringForScalarCoercion(deserializationContext, trim);
                    try {
                        if (length <= 9) {
                            return Integer.valueOf(NumberInput.parseInt(trim));
                        }
                        long parseLong = Long.parseLong(trim);
                        if (_intOverflow(parseLong)) {
                            return (Integer) deserializationContext.handleWeirdStringValue(this._valueClass, trim, String.format("Overflow: numeric value (%s) out of range of Integer (%d - %d)", trim, Integer.MIN_VALUE, Integer.MAX_VALUE), new Object[0]);
                        }
                        return Integer.valueOf((int) parseLong);
                    } catch (IllegalArgumentException unused) {
                        return (Integer) deserializationContext.handleWeirdStringValue(this._valueClass, trim, "not a valid Integer value", new Object[0]);
                    }
                case 7:
                    return Integer.valueOf(jsonParser.getIntValue());
                case 8:
                    if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
                        _failDoubleToIntCoercion(jsonParser, deserializationContext, "Integer");
                    }
                    return Integer.valueOf(jsonParser.getValueAsInt());
                default:
                    return (Integer) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static final class LongDeserializer extends PrimitiveOrWrapperDeserializer<Long> {
        static final LongDeserializer a = new LongDeserializer(Long.TYPE, 0L);
        static final LongDeserializer b = new LongDeserializer(Long.class, null);
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public boolean isCachable() {
            return true;
        }

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public /* bridge */ /* synthetic */ Object getEmptyValue(DeserializationContext deserializationContext) throws JsonMappingException {
            return super.getEmptyValue(deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer, com.fasterxml.jackson.databind.deser.NullValueProvider
        public /* bridge */ /* synthetic */ AccessPattern getNullAccessPattern() {
            return super.getNullAccessPattern();
        }

        public LongDeserializer(Class<Long> cls, Long l) {
            super(cls, l, 0L);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
                return Long.valueOf(jsonParser.getLongValue());
            }
            return _parseLong(jsonParser, deserializationContext);
        }

        protected final Long _parseLong(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            int currentTokenId = jsonParser.getCurrentTokenId();
            if (currentTokenId == 3) {
                return _deserializeFromArray(jsonParser, deserializationContext);
            }
            if (currentTokenId == 11) {
                return (Long) _coerceNullToken(deserializationContext, this._primitive);
            }
            switch (currentTokenId) {
                case 6:
                    String trim = jsonParser.getText().trim();
                    if (trim.length() == 0) {
                        return (Long) _coerceEmptyString(deserializationContext, this._primitive);
                    }
                    if (_hasTextualNull(trim)) {
                        return (Long) _coerceTextualNull(deserializationContext, this._primitive);
                    }
                    _verifyStringForScalarCoercion(deserializationContext, trim);
                    try {
                        return Long.valueOf(NumberInput.parseLong(trim));
                    } catch (IllegalArgumentException unused) {
                        return (Long) deserializationContext.handleWeirdStringValue(this._valueClass, trim, "not a valid Long value", new Object[0]);
                    }
                case 7:
                    return Long.valueOf(jsonParser.getLongValue());
                case 8:
                    if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
                        _failDoubleToIntCoercion(jsonParser, deserializationContext, "Long");
                    }
                    return Long.valueOf(jsonParser.getValueAsLong());
                default:
                    return (Long) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class FloatDeserializer extends PrimitiveOrWrapperDeserializer<Float> {
        static final FloatDeserializer a = new FloatDeserializer(Float.TYPE, Float.valueOf(0.0f));
        static final FloatDeserializer b = new FloatDeserializer(Float.class, null);
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public /* bridge */ /* synthetic */ Object getEmptyValue(DeserializationContext deserializationContext) throws JsonMappingException {
            return super.getEmptyValue(deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer, com.fasterxml.jackson.databind.deser.NullValueProvider
        public /* bridge */ /* synthetic */ AccessPattern getNullAccessPattern() {
            return super.getNullAccessPattern();
        }

        public FloatDeserializer(Class<Float> cls, Float f) {
            super(cls, f, Float.valueOf(0.0f));
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Float deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return _parseFloat(jsonParser, deserializationContext);
        }

        protected final Float _parseFloat(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_NUMBER_FLOAT || currentToken == JsonToken.VALUE_NUMBER_INT) {
                return Float.valueOf(jsonParser.getFloatValue());
            }
            if (currentToken == JsonToken.VALUE_STRING) {
                String trim = jsonParser.getText().trim();
                if (trim.length() == 0) {
                    return (Float) _coerceEmptyString(deserializationContext, this._primitive);
                }
                if (_hasTextualNull(trim)) {
                    return (Float) _coerceTextualNull(deserializationContext, this._primitive);
                }
                char charAt = trim.charAt(0);
                if (charAt != '-') {
                    if (charAt != 'I') {
                        if (charAt == 'N' && _isNaN(trim)) {
                            return Float.valueOf(Float.NaN);
                        }
                    } else if (_isPosInf(trim)) {
                        return Float.valueOf(Float.POSITIVE_INFINITY);
                    }
                } else if (_isNegInf(trim)) {
                    return Float.valueOf(Float.NEGATIVE_INFINITY);
                }
                _verifyStringForScalarCoercion(deserializationContext, trim);
                try {
                    return Float.valueOf(Float.parseFloat(trim));
                } catch (IllegalArgumentException unused) {
                    return (Float) deserializationContext.handleWeirdStringValue(this._valueClass, trim, "not a valid Float value", new Object[0]);
                }
            } else if (currentToken == JsonToken.VALUE_NULL) {
                return (Float) _coerceNullToken(deserializationContext, this._primitive);
            } else {
                if (currentToken == JsonToken.START_ARRAY) {
                    return _deserializeFromArray(jsonParser, deserializationContext);
                }
                return (Float) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class DoubleDeserializer extends PrimitiveOrWrapperDeserializer<Double> {
        static final DoubleDeserializer a = new DoubleDeserializer(Double.TYPE, Double.valueOf(0.0d));
        static final DoubleDeserializer b = new DoubleDeserializer(Double.class, null);
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public /* bridge */ /* synthetic */ Object getEmptyValue(DeserializationContext deserializationContext) throws JsonMappingException {
            return super.getEmptyValue(deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.NumberDeserializers.PrimitiveOrWrapperDeserializer, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer, com.fasterxml.jackson.databind.deser.NullValueProvider
        public /* bridge */ /* synthetic */ AccessPattern getNullAccessPattern() {
            return super.getNullAccessPattern();
        }

        public DoubleDeserializer(Class<Double> cls, Double d) {
            super(cls, d, Double.valueOf(0.0d));
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return _parseDouble(jsonParser, deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public Double deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
            return _parseDouble(jsonParser, deserializationContext);
        }

        protected final Double _parseDouble(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_NUMBER_INT || currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
                return Double.valueOf(jsonParser.getDoubleValue());
            }
            if (currentToken == JsonToken.VALUE_STRING) {
                String trim = jsonParser.getText().trim();
                if (trim.length() == 0) {
                    return (Double) _coerceEmptyString(deserializationContext, this._primitive);
                }
                if (_hasTextualNull(trim)) {
                    return (Double) _coerceTextualNull(deserializationContext, this._primitive);
                }
                char charAt = trim.charAt(0);
                if (charAt != '-') {
                    if (charAt != 'I') {
                        if (charAt == 'N' && _isNaN(trim)) {
                            return Double.valueOf(Double.NaN);
                        }
                    } else if (_isPosInf(trim)) {
                        return Double.valueOf(Double.POSITIVE_INFINITY);
                    }
                } else if (_isNegInf(trim)) {
                    return Double.valueOf(Double.NEGATIVE_INFINITY);
                }
                _verifyStringForScalarCoercion(deserializationContext, trim);
                try {
                    return Double.valueOf(parseDouble(trim));
                } catch (IllegalArgumentException unused) {
                    return (Double) deserializationContext.handleWeirdStringValue(this._valueClass, trim, "not a valid Double value", new Object[0]);
                }
            } else if (currentToken == JsonToken.VALUE_NULL) {
                return (Double) _coerceNullToken(deserializationContext, this._primitive);
            } else {
                if (currentToken == JsonToken.START_ARRAY) {
                    return _deserializeFromArray(jsonParser, deserializationContext);
                }
                return (Double) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class NumberDeserializer extends StdScalarDeserializer<Object> {
        public static final NumberDeserializer instance = new NumberDeserializer();

        public NumberDeserializer() {
            super(Number.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            int currentTokenId = jsonParser.getCurrentTokenId();
            if (currentTokenId == 3) {
                return _deserializeFromArray(jsonParser, deserializationContext);
            }
            switch (currentTokenId) {
                case 6:
                    String trim = jsonParser.getText().trim();
                    if (trim.length() == 0) {
                        return getNullValue(deserializationContext);
                    }
                    if (_hasTextualNull(trim)) {
                        return getNullValue(deserializationContext);
                    }
                    if (_isPosInf(trim)) {
                        return Double.valueOf(Double.POSITIVE_INFINITY);
                    }
                    if (_isNegInf(trim)) {
                        return Double.valueOf(Double.NEGATIVE_INFINITY);
                    }
                    if (_isNaN(trim)) {
                        return Double.valueOf(Double.NaN);
                    }
                    _verifyStringForScalarCoercion(deserializationContext, trim);
                    try {
                        if (!_isIntNumber(trim)) {
                            if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                                return new BigDecimal(trim);
                            }
                            return Double.valueOf(trim);
                        } else if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                            return new BigInteger(trim);
                        } else {
                            long parseLong = Long.parseLong(trim);
                            if (deserializationContext.isEnabled(DeserializationFeature.USE_LONG_FOR_INTS) || parseLong > 2147483647L || parseLong < -2147483648L) {
                                return Long.valueOf(parseLong);
                            }
                            return Integer.valueOf((int) parseLong);
                        }
                    } catch (IllegalArgumentException unused) {
                        return deserializationContext.handleWeirdStringValue(this._valueClass, trim, "not a valid number", new Object[0]);
                    }
                case 7:
                    if (deserializationContext.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
                        return _coerceIntegral(jsonParser, deserializationContext);
                    }
                    return jsonParser.getNumberValue();
                case 8:
                    if (!deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS) || jsonParser.isNaN()) {
                        return jsonParser.getNumberValue();
                    }
                    return jsonParser.getDecimalValue();
                default:
                    return deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
            switch (jsonParser.getCurrentTokenId()) {
                case 6:
                case 7:
                case 8:
                    return deserialize(jsonParser, deserializationContext);
                default:
                    return typeDeserializer.deserializeTypedFromScalar(jsonParser, deserializationContext);
            }
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class BigIntegerDeserializer extends StdScalarDeserializer<BigInteger> {
        public static final BigIntegerDeserializer instance = new BigIntegerDeserializer();

        public BigIntegerDeserializer() {
            super(BigInteger.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Object getEmptyValue(DeserializationContext deserializationContext) {
            return BigInteger.ZERO;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public BigInteger deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            int currentTokenId = jsonParser.getCurrentTokenId();
            if (currentTokenId == 3) {
                return _deserializeFromArray(jsonParser, deserializationContext);
            }
            switch (currentTokenId) {
                case 6:
                    String trim = jsonParser.getText().trim();
                    if (_isEmptyOrTextualNull(trim)) {
                        _verifyNullForScalarCoercion(deserializationContext, trim);
                        return getNullValue(deserializationContext);
                    }
                    _verifyStringForScalarCoercion(deserializationContext, trim);
                    try {
                        return new BigInteger(trim);
                    } catch (IllegalArgumentException unused) {
                        return (BigInteger) deserializationContext.handleWeirdStringValue(this._valueClass, trim, "not a valid representation", new Object[0]);
                    }
                case 7:
                    switch (jsonParser.getNumberType()) {
                        case INT:
                        case LONG:
                        case BIG_INTEGER:
                            return jsonParser.getBigIntegerValue();
                    }
                case 8:
                    if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
                        _failDoubleToIntCoercion(jsonParser, deserializationContext, "java.math.BigInteger");
                    }
                    return jsonParser.getDecimalValue().toBigInteger();
            }
            return (BigInteger) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    public static class BigDecimalDeserializer extends StdScalarDeserializer<BigDecimal> {
        public static final BigDecimalDeserializer instance = new BigDecimalDeserializer();

        public BigDecimalDeserializer() {
            super(BigDecimal.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Object getEmptyValue(DeserializationContext deserializationContext) {
            return BigDecimal.ZERO;
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            int currentTokenId = jsonParser.getCurrentTokenId();
            if (currentTokenId == 3) {
                return _deserializeFromArray(jsonParser, deserializationContext);
            }
            switch (currentTokenId) {
                case 6:
                    String trim = jsonParser.getText().trim();
                    if (_isEmptyOrTextualNull(trim)) {
                        _verifyNullForScalarCoercion(deserializationContext, trim);
                        return getNullValue(deserializationContext);
                    }
                    _verifyStringForScalarCoercion(deserializationContext, trim);
                    try {
                        return new BigDecimal(trim);
                    } catch (IllegalArgumentException unused) {
                        return (BigDecimal) deserializationContext.handleWeirdStringValue(this._valueClass, trim, "not a valid representation", new Object[0]);
                    }
                case 7:
                case 8:
                    return jsonParser.getDecimalValue();
                default:
                    return (BigDecimal) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }
    }
}
