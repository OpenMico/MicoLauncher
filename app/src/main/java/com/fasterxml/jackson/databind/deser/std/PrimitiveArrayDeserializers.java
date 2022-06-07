package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.fasterxml.jackson.databind.deser.impl.NullsFailProvider;
import com.fasterxml.jackson.databind.exc.InvalidNullException;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class PrimitiveArrayDeserializers<T> extends StdDeserializer<T> implements ContextualDeserializer {
    protected final NullValueProvider _nuller;
    protected final Boolean _unwrapSingle;
    private transient Object a;

    protected abstract T _concat(T t, T t2);

    protected abstract T _constructEmpty();

    protected abstract T handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException;

    protected abstract PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool);

    protected PrimitiveArrayDeserializers(Class<T> cls) {
        super((Class<?>) cls);
        this._unwrapSingle = null;
        this._nuller = null;
    }

    protected PrimitiveArrayDeserializers(PrimitiveArrayDeserializers<?> primitiveArrayDeserializers, NullValueProvider nullValueProvider, Boolean bool) {
        super(primitiveArrayDeserializers._valueClass);
        this._unwrapSingle = bool;
        this._nuller = nullValueProvider;
    }

    public static JsonDeserializer<?> forType(Class<?> cls) {
        if (cls == Integer.TYPE) {
            return f.a;
        }
        if (cls == Long.TYPE) {
            return g.a;
        }
        if (cls == Byte.TYPE) {
            return new b();
        }
        if (cls == Short.TYPE) {
            return new h();
        }
        if (cls == Float.TYPE) {
            return new e();
        }
        if (cls == Double.TYPE) {
            return new d();
        }
        if (cls == Boolean.TYPE) {
            return new a();
        }
        if (cls == Character.TYPE) {
            return new c();
        }
        throw new IllegalStateException();
    }

    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        NullValueProvider nullValueProvider;
        Boolean findFormatFeature = findFormatFeature(deserializationContext, beanProperty, this._valueClass, JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        Nulls findContentNullStyle = findContentNullStyle(deserializationContext, beanProperty);
        if (findContentNullStyle == Nulls.SKIP) {
            nullValueProvider = NullsConstantProvider.skipper();
        } else if (findContentNullStyle != Nulls.FAIL) {
            nullValueProvider = null;
        } else if (beanProperty == null) {
            nullValueProvider = NullsFailProvider.constructForRootValue(deserializationContext.constructType(this._valueClass));
        } else {
            nullValueProvider = NullsFailProvider.constructForProperty(beanProperty);
        }
        return (findFormatFeature == this._unwrapSingle && nullValueProvider == this._nuller) ? this : withResolved(nullValueProvider, findFormatFeature);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Boolean supportsUpdate(DeserializationConfig deserializationConfig) {
        return Boolean.TRUE;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public AccessPattern getEmptyAccessPattern() {
        return AccessPattern.CONSTANT;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object getEmptyValue(DeserializationContext deserializationContext) throws JsonMappingException {
        Object obj = this.a;
        if (obj != null) {
            return obj;
        }
        T _constructEmpty = _constructEmpty();
        this.a = _constructEmpty;
        return _constructEmpty;
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, T t) throws IOException {
        T deserialize = deserialize(jsonParser, deserializationContext);
        return (t == null || Array.getLength(t) == 0) ? deserialize : _concat(t, deserialize);
    }

    protected T handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_STRING) && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
            return null;
        }
        if (this._unwrapSingle == Boolean.TRUE || (this._unwrapSingle == null && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY))) {
            return handleSingleElementUnwrapped(jsonParser, deserializationContext);
        }
        return (T) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
    }

    protected void _failOnNull(DeserializationContext deserializationContext) throws IOException {
        throw InvalidNullException.from(deserializationContext, (PropertyName) null, deserializationContext.constructType(this._valueClass));
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    static final class c extends PrimitiveArrayDeserializers<char[]> {
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers
        protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return this;
        }

        public c() {
            super(char[].class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public char[] _constructEmpty() {
            return new char[0];
        }

        /* renamed from: a */
        public char[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String text;
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_STRING) {
                char[] textCharacters = jsonParser.getTextCharacters();
                int textOffset = jsonParser.getTextOffset();
                int textLength = jsonParser.getTextLength();
                char[] cArr = new char[textLength];
                System.arraycopy(textCharacters, textOffset, cArr, 0, textLength);
                return cArr;
            } else if (jsonParser.isExpectedStartArrayToken()) {
                StringBuilder sb = new StringBuilder(64);
                while (true) {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return sb.toString().toCharArray();
                    }
                    if (nextToken == JsonToken.VALUE_STRING) {
                        text = jsonParser.getText();
                    } else if (nextToken != JsonToken.VALUE_NULL) {
                        text = ((CharSequence) deserializationContext.handleUnexpectedToken(Character.TYPE, jsonParser)).toString();
                    } else if (this._nuller != null) {
                        this._nuller.getNullValue(deserializationContext);
                    } else {
                        _verifyNullForPrimitive(deserializationContext);
                        text = "\u0000";
                    }
                    if (text.length() != 1) {
                        deserializationContext.reportInputMismatch(this, "Cannot convert a JSON String of length %d into a char element of char array", Integer.valueOf(text.length()));
                    }
                    sb.append(text.charAt(0));
                }
            } else {
                if (currentToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
                    Object embeddedObject = jsonParser.getEmbeddedObject();
                    if (embeddedObject == null) {
                        return null;
                    }
                    if (embeddedObject instanceof char[]) {
                        return (char[]) embeddedObject;
                    }
                    if (embeddedObject instanceof String) {
                        return ((String) embeddedObject).toCharArray();
                    }
                    if (embeddedObject instanceof byte[]) {
                        return Base64Variants.getDefaultVariant().encode((byte[]) embeddedObject, false).toCharArray();
                    }
                }
                return (char[]) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public char[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return (char[]) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public char[] _concat(char[] cArr, char[] cArr2) {
            int length = cArr.length;
            int length2 = cArr2.length;
            char[] copyOf = Arrays.copyOf(cArr, length + length2);
            System.arraycopy(cArr2, 0, copyOf, length, length2);
            return copyOf;
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    static final class a extends PrimitiveArrayDeserializers<boolean[]> {
        private static final long serialVersionUID = 1;

        public a() {
            super(boolean[].class);
        }

        protected a(a aVar, NullValueProvider nullValueProvider, Boolean bool) {
            super(aVar, nullValueProvider, bool);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers
        protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new a(this, nullValueProvider, bool);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public boolean[] _constructEmpty() {
            return new boolean[0];
        }

        /* renamed from: a */
        public boolean[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Exception e;
            boolean z;
            int i;
            if (!jsonParser.isExpectedStartArrayToken()) {
                return handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.BooleanBuilder booleanBuilder = deserializationContext.getArrayBuilders().getBooleanBuilder();
            boolean[] resetAndStart = booleanBuilder.resetAndStart();
            int i2 = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return booleanBuilder.completeAndClearBuffer(resetAndStart, i2);
                    }
                    try {
                        if (nextToken == JsonToken.VALUE_TRUE) {
                            z = true;
                        } else if (nextToken == JsonToken.VALUE_FALSE) {
                            z = false;
                        } else if (nextToken != JsonToken.VALUE_NULL) {
                            z = _parseBooleanPrimitive(jsonParser, deserializationContext);
                        } else if (this._nuller != null) {
                            this._nuller.getNullValue(deserializationContext);
                        } else {
                            _verifyNullForPrimitive(deserializationContext);
                            z = false;
                        }
                        resetAndStart[i2] = z;
                        i2 = i;
                    } catch (Exception e2) {
                        e = e2;
                        i2 = i;
                        throw JsonMappingException.wrapWithPath(e, resetAndStart, booleanBuilder.bufferedSize() + i2);
                    }
                    if (i2 >= resetAndStart.length) {
                        resetAndStart = booleanBuilder.appendCompletedChunk(resetAndStart, i2);
                        i2 = 0;
                    }
                    i = i2 + 1;
                } catch (Exception e3) {
                    e = e3;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public boolean[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new boolean[]{_parseBooleanPrimitive(jsonParser, deserializationContext)};
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public boolean[] _concat(boolean[] zArr, boolean[] zArr2) {
            int length = zArr.length;
            int length2 = zArr2.length;
            boolean[] copyOf = Arrays.copyOf(zArr, length + length2);
            System.arraycopy(zArr2, 0, copyOf, length, length2);
            return copyOf;
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    static final class b extends PrimitiveArrayDeserializers<byte[]> {
        private static final long serialVersionUID = 1;

        public b() {
            super(byte[].class);
        }

        protected b(b bVar, NullValueProvider nullValueProvider, Boolean bool) {
            super(bVar, nullValueProvider, bool);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers
        protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new b(this, nullValueProvider, bool);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public byte[] _constructEmpty() {
            return new byte[0];
        }

        /* JADX WARN: Removed duplicated region for block: B:42:0x008d A[Catch: Exception -> 0x00a5, TRY_LEAVE, TryCatch #2 {Exception -> 0x00a5, blocks: (B:25:0x005d, B:27:0x0065, B:29:0x0069, B:32:0x006e, B:34:0x0072, B:36:0x0076, B:37:0x007c, B:38:0x0081, B:39:0x0086, B:40:0x008a, B:42:0x008d), top: B:58:0x005d }] */
        /* renamed from: a */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public byte[] deserialize(com.fasterxml.jackson.core.JsonParser r7, com.fasterxml.jackson.databind.DeserializationContext r8) throws java.io.IOException {
            /*
                r6 = this;
                com.fasterxml.jackson.core.JsonToken r0 = r7.getCurrentToken()
                com.fasterxml.jackson.core.JsonToken r1 = com.fasterxml.jackson.core.JsonToken.VALUE_STRING
                r2 = 0
                if (r0 != r1) goto L_0x002e
                com.fasterxml.jackson.core.Base64Variant r1 = r8.getBase64Variant()     // Catch: JsonParseException -> 0x0012
                byte[] r7 = r7.getBinaryValue(r1)     // Catch: JsonParseException -> 0x0012
                return r7
            L_0x0012:
                r1 = move-exception
                java.lang.String r1 = r1.getOriginalMessage()
                java.lang.String r3 = "base64"
                boolean r3 = r1.contains(r3)
                if (r3 == 0) goto L_0x002e
                java.lang.Class<byte[]> r0 = byte[].class
                java.lang.String r7 = r7.getText()
                java.lang.Object[] r2 = new java.lang.Object[r2]
                java.lang.Object r7 = r8.handleWeirdStringValue(r0, r7, r1, r2)
                byte[] r7 = (byte[]) r7
                return r7
            L_0x002e:
                com.fasterxml.jackson.core.JsonToken r1 = com.fasterxml.jackson.core.JsonToken.VALUE_EMBEDDED_OBJECT
                if (r0 != r1) goto L_0x0041
                java.lang.Object r0 = r7.getEmbeddedObject()
                if (r0 != 0) goto L_0x003a
                r7 = 0
                return r7
            L_0x003a:
                boolean r1 = r0 instanceof byte[]
                if (r1 == 0) goto L_0x0041
                byte[] r0 = (byte[]) r0
                return r0
            L_0x0041:
                boolean r0 = r7.isExpectedStartArrayToken()
                if (r0 != 0) goto L_0x004e
                java.lang.Object r7 = r6.handleNonArray(r7, r8)
                byte[] r7 = (byte[]) r7
                return r7
            L_0x004e:
                com.fasterxml.jackson.databind.util.ArrayBuilders r0 = r8.getArrayBuilders()
                com.fasterxml.jackson.databind.util.ArrayBuilders$ByteBuilder r0 = r0.getByteBuilder()
                java.lang.Object r1 = r0.resetAndStart()
                byte[] r1 = (byte[]) r1
                r3 = r2
            L_0x005d:
                com.fasterxml.jackson.core.JsonToken r4 = r7.nextToken()     // Catch: Exception -> 0x00a5
                com.fasterxml.jackson.core.JsonToken r5 = com.fasterxml.jackson.core.JsonToken.END_ARRAY     // Catch: Exception -> 0x00a5
                if (r4 == r5) goto L_0x009e
                com.fasterxml.jackson.core.JsonToken r5 = com.fasterxml.jackson.core.JsonToken.VALUE_NUMBER_INT     // Catch: Exception -> 0x00a5
                if (r4 == r5) goto L_0x0086
                com.fasterxml.jackson.core.JsonToken r5 = com.fasterxml.jackson.core.JsonToken.VALUE_NUMBER_FLOAT     // Catch: Exception -> 0x00a5
                if (r4 != r5) goto L_0x006e
                goto L_0x0086
            L_0x006e:
                com.fasterxml.jackson.core.JsonToken r5 = com.fasterxml.jackson.core.JsonToken.VALUE_NULL     // Catch: Exception -> 0x00a5
                if (r4 != r5) goto L_0x0081
                com.fasterxml.jackson.databind.deser.NullValueProvider r4 = r6._nuller     // Catch: Exception -> 0x00a5
                if (r4 == 0) goto L_0x007c
                com.fasterxml.jackson.databind.deser.NullValueProvider r4 = r6._nuller     // Catch: Exception -> 0x00a5
                r4.getNullValue(r8)     // Catch: Exception -> 0x00a5
                goto L_0x005d
            L_0x007c:
                r6._verifyNullForPrimitive(r8)     // Catch: Exception -> 0x00a5
                r4 = r2
                goto L_0x008a
            L_0x0081:
                byte r4 = r6._parseBytePrimitive(r7, r8)     // Catch: Exception -> 0x00a5
                goto L_0x008a
            L_0x0086:
                byte r4 = r7.getByteValue()     // Catch: Exception -> 0x00a5
            L_0x008a:
                int r5 = r1.length     // Catch: Exception -> 0x00a5
                if (r3 < r5) goto L_0x0095
                java.lang.Object r5 = r0.appendCompletedChunk(r1, r3)     // Catch: Exception -> 0x00a5
                byte[] r5 = (byte[]) r5     // Catch: Exception -> 0x00a5
                r3 = r2
                r1 = r5
            L_0x0095:
                int r5 = r3 + 1
                r1[r3] = r4     // Catch: Exception -> 0x009b
                r3 = r5
                goto L_0x005d
            L_0x009b:
                r7 = move-exception
                r3 = r5
                goto L_0x00a6
            L_0x009e:
                java.lang.Object r7 = r0.completeAndClearBuffer(r1, r3)
                byte[] r7 = (byte[]) r7
                return r7
            L_0x00a5:
                r7 = move-exception
            L_0x00a6:
                int r8 = r0.bufferedSize()
                int r8 = r8 + r3
                com.fasterxml.jackson.databind.JsonMappingException r7 = com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(r7, r1, r8)
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers.b.deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext):byte[]");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public byte[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            byte b;
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_NUMBER_INT || currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
                b = jsonParser.getByteValue();
            } else if (currentToken != JsonToken.VALUE_NULL) {
                b = ((Number) deserializationContext.handleUnexpectedToken(this._valueClass.getComponentType(), jsonParser)).byteValue();
            } else if (this._nuller != null) {
                this._nuller.getNullValue(deserializationContext);
                return (byte[]) getEmptyValue(deserializationContext);
            } else {
                _verifyNullForPrimitive(deserializationContext);
                return null;
            }
            return new byte[]{b};
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public byte[] _concat(byte[] bArr, byte[] bArr2) {
            int length = bArr.length;
            int length2 = bArr2.length;
            byte[] copyOf = Arrays.copyOf(bArr, length + length2);
            System.arraycopy(bArr2, 0, copyOf, length, length2);
            return copyOf;
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    static final class h extends PrimitiveArrayDeserializers<short[]> {
        private static final long serialVersionUID = 1;

        public h() {
            super(short[].class);
        }

        protected h(h hVar, NullValueProvider nullValueProvider, Boolean bool) {
            super(hVar, nullValueProvider, bool);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers
        protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new h(this, nullValueProvider, bool);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public short[] _constructEmpty() {
            return new short[0];
        }

        /* renamed from: a */
        public short[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Exception e;
            short _parseShortPrimitive;
            int i;
            if (!jsonParser.isExpectedStartArrayToken()) {
                return handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.ShortBuilder shortBuilder = deserializationContext.getArrayBuilders().getShortBuilder();
            short[] resetAndStart = shortBuilder.resetAndStart();
            int i2 = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return shortBuilder.completeAndClearBuffer(resetAndStart, i2);
                    }
                    try {
                        if (nextToken != JsonToken.VALUE_NULL) {
                            _parseShortPrimitive = _parseShortPrimitive(jsonParser, deserializationContext);
                        } else if (this._nuller != null) {
                            this._nuller.getNullValue(deserializationContext);
                        } else {
                            _verifyNullForPrimitive(deserializationContext);
                            _parseShortPrimitive = 0;
                        }
                        resetAndStart[i2] = _parseShortPrimitive;
                        i2 = i;
                    } catch (Exception e2) {
                        e = e2;
                        i2 = i;
                        throw JsonMappingException.wrapWithPath(e, resetAndStart, shortBuilder.bufferedSize() + i2);
                    }
                    if (i2 >= resetAndStart.length) {
                        resetAndStart = shortBuilder.appendCompletedChunk(resetAndStart, i2);
                        i2 = 0;
                    }
                    i = i2 + 1;
                } catch (Exception e3) {
                    e = e3;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public short[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new short[]{_parseShortPrimitive(jsonParser, deserializationContext)};
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public short[] _concat(short[] sArr, short[] sArr2) {
            int length = sArr.length;
            int length2 = sArr2.length;
            short[] copyOf = Arrays.copyOf(sArr, length + length2);
            System.arraycopy(sArr2, 0, copyOf, length, length2);
            return copyOf;
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    static final class f extends PrimitiveArrayDeserializers<int[]> {
        public static final f a = new f();
        private static final long serialVersionUID = 1;

        public f() {
            super(int[].class);
        }

        protected f(f fVar, NullValueProvider nullValueProvider, Boolean bool) {
            super(fVar, nullValueProvider, bool);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers
        protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new f(this, nullValueProvider, bool);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public int[] _constructEmpty() {
            return new int[0];
        }

        /* renamed from: a */
        public int[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Exception e;
            int intValue;
            int i;
            if (!jsonParser.isExpectedStartArrayToken()) {
                return handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.IntBuilder intBuilder = deserializationContext.getArrayBuilders().getIntBuilder();
            int[] resetAndStart = intBuilder.resetAndStart();
            int i2 = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return intBuilder.completeAndClearBuffer(resetAndStart, i2);
                    }
                    try {
                        if (nextToken == JsonToken.VALUE_NUMBER_INT) {
                            intValue = jsonParser.getIntValue();
                        } else if (nextToken != JsonToken.VALUE_NULL) {
                            intValue = _parseIntPrimitive(jsonParser, deserializationContext);
                        } else if (this._nuller != null) {
                            this._nuller.getNullValue(deserializationContext);
                        } else {
                            _verifyNullForPrimitive(deserializationContext);
                            intValue = 0;
                        }
                        resetAndStart[i2] = intValue;
                        i2 = i;
                    } catch (Exception e2) {
                        e = e2;
                        i2 = i;
                        throw JsonMappingException.wrapWithPath(e, resetAndStart, intBuilder.bufferedSize() + i2);
                    }
                    if (i2 >= resetAndStart.length) {
                        resetAndStart = intBuilder.appendCompletedChunk(resetAndStart, i2);
                        i2 = 0;
                    }
                    i = i2 + 1;
                } catch (Exception e3) {
                    e = e3;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public int[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new int[]{_parseIntPrimitive(jsonParser, deserializationContext)};
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public int[] _concat(int[] iArr, int[] iArr2) {
            int length = iArr.length;
            int length2 = iArr2.length;
            int[] copyOf = Arrays.copyOf(iArr, length + length2);
            System.arraycopy(iArr2, 0, copyOf, length, length2);
            return copyOf;
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    static final class g extends PrimitiveArrayDeserializers<long[]> {
        public static final g a = new g();
        private static final long serialVersionUID = 1;

        public g() {
            super(long[].class);
        }

        protected g(g gVar, NullValueProvider nullValueProvider, Boolean bool) {
            super(gVar, nullValueProvider, bool);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers
        protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new g(this, nullValueProvider, bool);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public long[] _constructEmpty() {
            return new long[0];
        }

        /* renamed from: a */
        public long[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Exception e;
            long longValue;
            int i;
            if (!jsonParser.isExpectedStartArrayToken()) {
                return handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.LongBuilder longBuilder = deserializationContext.getArrayBuilders().getLongBuilder();
            long[] resetAndStart = longBuilder.resetAndStart();
            int i2 = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return longBuilder.completeAndClearBuffer(resetAndStart, i2);
                    }
                    try {
                        if (nextToken == JsonToken.VALUE_NUMBER_INT) {
                            longValue = jsonParser.getLongValue();
                        } else if (nextToken != JsonToken.VALUE_NULL) {
                            longValue = _parseLongPrimitive(jsonParser, deserializationContext);
                        } else if (this._nuller != null) {
                            this._nuller.getNullValue(deserializationContext);
                        } else {
                            _verifyNullForPrimitive(deserializationContext);
                            longValue = 0;
                        }
                        resetAndStart[i2] = longValue;
                        i2 = i;
                    } catch (Exception e2) {
                        e = e2;
                        i2 = i;
                        throw JsonMappingException.wrapWithPath(e, resetAndStart, longBuilder.bufferedSize() + i2);
                    }
                    if (i2 >= resetAndStart.length) {
                        resetAndStart = longBuilder.appendCompletedChunk(resetAndStart, i2);
                        i2 = 0;
                    }
                    i = i2 + 1;
                } catch (Exception e3) {
                    e = e3;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public long[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new long[]{_parseLongPrimitive(jsonParser, deserializationContext)};
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public long[] _concat(long[] jArr, long[] jArr2) {
            int length = jArr.length;
            int length2 = jArr2.length;
            long[] copyOf = Arrays.copyOf(jArr, length + length2);
            System.arraycopy(jArr2, 0, copyOf, length, length2);
            return copyOf;
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    static final class e extends PrimitiveArrayDeserializers<float[]> {
        private static final long serialVersionUID = 1;

        public e() {
            super(float[].class);
        }

        protected e(e eVar, NullValueProvider nullValueProvider, Boolean bool) {
            super(eVar, nullValueProvider, bool);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers
        protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new e(this, nullValueProvider, bool);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public float[] _constructEmpty() {
            return new float[0];
        }

        /* renamed from: a */
        public float[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Exception e;
            if (!jsonParser.isExpectedStartArrayToken()) {
                return handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.FloatBuilder floatBuilder = deserializationContext.getArrayBuilders().getFloatBuilder();
            float[] resetAndStart = floatBuilder.resetAndStart();
            int i = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return floatBuilder.completeAndClearBuffer(resetAndStart, i);
                    }
                    if (nextToken != JsonToken.VALUE_NULL || this._nuller == null) {
                        float _parseFloatPrimitive = _parseFloatPrimitive(jsonParser, deserializationContext);
                        if (i >= resetAndStart.length) {
                            resetAndStart = floatBuilder.appendCompletedChunk(resetAndStart, i);
                            i = 0;
                        }
                        int i2 = i + 1;
                        try {
                            resetAndStart[i] = _parseFloatPrimitive;
                            i = i2;
                        } catch (Exception e2) {
                            e = e2;
                            i = i2;
                            throw JsonMappingException.wrapWithPath(e, resetAndStart, floatBuilder.bufferedSize() + i);
                        }
                    } else {
                        this._nuller.getNullValue(deserializationContext);
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public float[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new float[]{_parseFloatPrimitive(jsonParser, deserializationContext)};
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public float[] _concat(float[] fArr, float[] fArr2) {
            int length = fArr.length;
            int length2 = fArr2.length;
            float[] copyOf = Arrays.copyOf(fArr, length + length2);
            System.arraycopy(fArr2, 0, copyOf, length, length2);
            return copyOf;
        }
    }

    @JacksonStdImpl
    /* loaded from: classes.dex */
    static final class d extends PrimitiveArrayDeserializers<double[]> {
        private static final long serialVersionUID = 1;

        public d() {
            super(double[].class);
        }

        protected d(d dVar, NullValueProvider nullValueProvider, Boolean bool) {
            super(dVar, nullValueProvider, bool);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers
        protected PrimitiveArrayDeserializers<?> withResolved(NullValueProvider nullValueProvider, Boolean bool) {
            return new d(this, nullValueProvider, bool);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public double[] _constructEmpty() {
            return new double[0];
        }

        /* renamed from: a */
        public double[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Exception e;
            if (!jsonParser.isExpectedStartArrayToken()) {
                return handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.DoubleBuilder doubleBuilder = deserializationContext.getArrayBuilders().getDoubleBuilder();
            double[] resetAndStart = doubleBuilder.resetAndStart();
            int i = 0;
            while (true) {
                try {
                    JsonToken nextToken = jsonParser.nextToken();
                    if (nextToken == JsonToken.END_ARRAY) {
                        return doubleBuilder.completeAndClearBuffer(resetAndStart, i);
                    }
                    if (nextToken != JsonToken.VALUE_NULL || this._nuller == null) {
                        double _parseDoublePrimitive = _parseDoublePrimitive(jsonParser, deserializationContext);
                        if (i >= resetAndStart.length) {
                            resetAndStart = doubleBuilder.appendCompletedChunk(resetAndStart, i);
                            i = 0;
                        }
                        int i2 = i + 1;
                        try {
                            resetAndStart[i] = _parseDoublePrimitive;
                            i = i2;
                        } catch (Exception e2) {
                            e = e2;
                            i = i2;
                            throw JsonMappingException.wrapWithPath(e, resetAndStart, doubleBuilder.bufferedSize() + i);
                        }
                    } else {
                        this._nuller.getNullValue(deserializationContext);
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: b */
        public double[] handleSingleElementUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new double[]{_parseDoublePrimitive(jsonParser, deserializationContext)};
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public double[] _concat(double[] dArr, double[] dArr2) {
            int length = dArr.length;
            int length2 = dArr2.length;
            double[] copyOf = Arrays.copyOf(dArr, length + length2);
            System.arraycopy(dArr2, 0, copyOf, length, length2);
            return copyOf;
        }
    }
}
