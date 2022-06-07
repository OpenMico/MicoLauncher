package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;

@JacksonStdImpl
/* loaded from: classes.dex */
public final class StringArrayDeserializer extends StdDeserializer<String[]> implements ContextualDeserializer {
    private static final String[] a = new String[0];
    public static final StringArrayDeserializer instance = new StringArrayDeserializer();
    private static final long serialVersionUID = 2;
    protected JsonDeserializer<String> _elementDeserializer;
    protected final NullValueProvider _nullProvider;
    protected final boolean _skipNullValues;
    protected final Boolean _unwrapSingle;

    public StringArrayDeserializer() {
        this(null, null, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected StringArrayDeserializer(JsonDeserializer<?> jsonDeserializer, NullValueProvider nullValueProvider, Boolean bool) {
        super(String[].class);
        this._elementDeserializer = jsonDeserializer;
        this._nullProvider = nullValueProvider;
        this._unwrapSingle = bool;
        this._skipNullValues = NullsConstantProvider.isSkipper(nullValueProvider);
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
        return a;
    }

    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer<?> jsonDeserializer;
        JsonDeserializer<?> findConvertingContentDeserializer = findConvertingContentDeserializer(deserializationContext, beanProperty, this._elementDeserializer);
        JavaType constructType = deserializationContext.constructType(String.class);
        if (findConvertingContentDeserializer == null) {
            jsonDeserializer = deserializationContext.findContextualValueDeserializer(constructType, beanProperty);
        } else {
            jsonDeserializer = deserializationContext.handleSecondaryContextualization(findConvertingContentDeserializer, beanProperty, constructType);
        }
        Boolean findFormatFeature = findFormatFeature(deserializationContext, beanProperty, String[].class, JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        NullValueProvider findContentNullProvider = findContentNullProvider(deserializationContext, beanProperty, jsonDeserializer);
        if (jsonDeserializer != null && isDefaultDeserializer(jsonDeserializer)) {
            jsonDeserializer = null;
        }
        return (this._elementDeserializer == jsonDeserializer && this._unwrapSingle == findFormatFeature && this._nullProvider == findContentNullProvider) ? this : new StringArrayDeserializer(jsonDeserializer, findContentNullProvider, findFormatFeature);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public String[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Exception e;
        String nextTextValue;
        int i;
        if (!jsonParser.isExpectedStartArrayToken()) {
            return a(jsonParser, deserializationContext);
        }
        if (this._elementDeserializer != null) {
            return _deserializeCustom(jsonParser, deserializationContext, null);
        }
        ObjectBuffer leaseObjectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] resetAndStart = leaseObjectBuffer.resetAndStart();
        int i2 = 0;
        while (true) {
            try {
                nextTextValue = jsonParser.nextTextValue();
            } catch (Exception e2) {
                e = e2;
            }
            try {
                if (nextTextValue == null) {
                    JsonToken currentToken = jsonParser.getCurrentToken();
                    if (currentToken == JsonToken.END_ARRAY) {
                        String[] strArr = (String[]) leaseObjectBuffer.completeAndClearBuffer(resetAndStart, i2, String.class);
                        deserializationContext.returnObjectBuffer(leaseObjectBuffer);
                        return strArr;
                    } else if (currentToken != JsonToken.VALUE_NULL) {
                        nextTextValue = _parseString(jsonParser, deserializationContext);
                    } else if (!this._skipNullValues) {
                        nextTextValue = (String) this._nullProvider.getNullValue(deserializationContext);
                    }
                }
                resetAndStart[i2] = nextTextValue;
                i2 = i;
            } catch (Exception e3) {
                e = e3;
                i2 = i;
                throw JsonMappingException.wrapWithPath(e, resetAndStart, leaseObjectBuffer.bufferedSize() + i2);
            }
            if (i2 >= resetAndStart.length) {
                resetAndStart = leaseObjectBuffer.appendCompletedChunk(resetAndStart);
                i2 = 0;
            }
            i = i2 + 1;
        }
    }

    protected final String[] _deserializeCustom(JsonParser jsonParser, DeserializationContext deserializationContext, String[] strArr) throws IOException {
        Object[] objArr;
        int i;
        Exception e;
        String deserialize;
        int i2;
        ObjectBuffer leaseObjectBuffer = deserializationContext.leaseObjectBuffer();
        if (strArr == null) {
            objArr = leaseObjectBuffer.resetAndStart();
            i = 0;
        } else {
            i = strArr.length;
            objArr = leaseObjectBuffer.resetAndStart(strArr, i);
        }
        JsonDeserializer<String> jsonDeserializer = this._elementDeserializer;
        while (true) {
            try {
            } catch (Exception e2) {
                e = e2;
            }
            try {
                if (jsonParser.nextTextValue() == null) {
                    JsonToken currentToken = jsonParser.getCurrentToken();
                    if (currentToken == JsonToken.END_ARRAY) {
                        String[] strArr2 = (String[]) leaseObjectBuffer.completeAndClearBuffer(objArr, i, String.class);
                        deserializationContext.returnObjectBuffer(leaseObjectBuffer);
                        return strArr2;
                    } else if (currentToken != JsonToken.VALUE_NULL) {
                        deserialize = jsonDeserializer.deserialize(jsonParser, deserializationContext);
                    } else if (!this._skipNullValues) {
                        deserialize = (String) this._nullProvider.getNullValue(deserializationContext);
                    }
                } else {
                    deserialize = jsonDeserializer.deserialize(jsonParser, deserializationContext);
                }
                objArr[i] = deserialize;
                i = i2;
            } catch (Exception e3) {
                e = e3;
                i = i2;
                throw JsonMappingException.wrapWithPath(e, String.class, i);
            }
            if (i >= objArr.length) {
                objArr = leaseObjectBuffer.appendCompletedChunk(objArr);
                i = 0;
            }
            i2 = i + 1;
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    public String[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, String[] strArr) throws IOException {
        Exception e;
        String nextTextValue;
        int i;
        if (!jsonParser.isExpectedStartArrayToken()) {
            String[] a2 = a(jsonParser, deserializationContext);
            if (a2 == null) {
                return strArr;
            }
            int length = strArr.length;
            String[] strArr2 = new String[a2.length + length];
            System.arraycopy(strArr, 0, strArr2, 0, length);
            System.arraycopy(a2, 0, strArr2, length, a2.length);
            return strArr2;
        } else if (this._elementDeserializer != null) {
            return _deserializeCustom(jsonParser, deserializationContext, strArr);
        } else {
            ObjectBuffer leaseObjectBuffer = deserializationContext.leaseObjectBuffer();
            int length2 = strArr.length;
            Object[] resetAndStart = leaseObjectBuffer.resetAndStart(strArr, length2);
            while (true) {
                try {
                    nextTextValue = jsonParser.nextTextValue();
                    if (nextTextValue == null) {
                        JsonToken currentToken = jsonParser.getCurrentToken();
                        if (currentToken == JsonToken.END_ARRAY) {
                            String[] strArr3 = (String[]) leaseObjectBuffer.completeAndClearBuffer(resetAndStart, length2, String.class);
                            deserializationContext.returnObjectBuffer(leaseObjectBuffer);
                            return strArr3;
                        } else if (currentToken != JsonToken.VALUE_NULL) {
                            nextTextValue = _parseString(jsonParser, deserializationContext);
                        } else if (this._skipNullValues) {
                            return a;
                        } else {
                            nextTextValue = (String) this._nullProvider.getNullValue(deserializationContext);
                        }
                    }
                    if (length2 >= resetAndStart.length) {
                        resetAndStart = leaseObjectBuffer.appendCompletedChunk(resetAndStart);
                        length2 = 0;
                    }
                    i = length2 + 1;
                } catch (Exception e2) {
                    e = e2;
                }
                try {
                    resetAndStart[length2] = nextTextValue;
                    length2 = i;
                } catch (Exception e3) {
                    e = e3;
                    length2 = i;
                    throw JsonMappingException.wrapWithPath(e, resetAndStart, leaseObjectBuffer.bufferedSize() + length2);
                }
            }
        }
    }

    private final String[] a(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String str;
        if (this._unwrapSingle == Boolean.TRUE || (this._unwrapSingle == null && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY))) {
            if (jsonParser.hasToken(JsonToken.VALUE_NULL)) {
                str = (String) this._nullProvider.getNullValue(deserializationContext);
            } else {
                str = _parseString(jsonParser, deserializationContext);
            }
            return new String[]{str};
        } else if (!jsonParser.hasToken(JsonToken.VALUE_STRING) || !deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) || jsonParser.getText().length() != 0) {
            return (String[]) deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
        } else {
            return null;
        }
    }
}
