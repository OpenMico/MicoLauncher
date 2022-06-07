package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;

/* loaded from: classes.dex */
public class AsPropertyTypeDeserializer extends AsArrayTypeDeserializer {
    private static final long serialVersionUID = 1;
    protected final JsonTypeInfo.As _inclusion;

    public AsPropertyTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, String str, boolean z, JavaType javaType2) {
        this(javaType, typeIdResolver, str, z, javaType2, JsonTypeInfo.As.PROPERTY);
    }

    public AsPropertyTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, String str, boolean z, JavaType javaType2, JsonTypeInfo.As as) {
        super(javaType, typeIdResolver, str, z, javaType2);
        this._inclusion = as;
    }

    public AsPropertyTypeDeserializer(AsPropertyTypeDeserializer asPropertyTypeDeserializer, BeanProperty beanProperty) {
        super(asPropertyTypeDeserializer, beanProperty);
        this._inclusion = asPropertyTypeDeserializer._inclusion;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer, com.fasterxml.jackson.databind.jsontype.impl.TypeDeserializerBase, com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public TypeDeserializer forProperty(BeanProperty beanProperty) {
        return beanProperty == this._property ? this : new AsPropertyTypeDeserializer(this, beanProperty);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer, com.fasterxml.jackson.databind.jsontype.impl.TypeDeserializerBase, com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public JsonTypeInfo.As getTypeInclusion() {
        return this._inclusion;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer, com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public Object deserializeTypedFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object typeId;
        if (jsonParser.canReadTypeId() && (typeId = jsonParser.getTypeId()) != null) {
            return _deserializeWithNativeTypeId(jsonParser, deserializationContext, typeId);
        }
        JsonToken currentToken = jsonParser.getCurrentToken();
        TokenBuffer tokenBuffer = null;
        if (currentToken == JsonToken.START_OBJECT) {
            currentToken = jsonParser.nextToken();
        } else if (currentToken != JsonToken.FIELD_NAME) {
            return _deserializeTypedUsingDefaultImpl(jsonParser, deserializationContext, null);
        }
        while (currentToken == JsonToken.FIELD_NAME) {
            String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if (currentName.equals(this._typePropertyName)) {
                return _deserializeTypedForId(jsonParser, deserializationContext, tokenBuffer);
            }
            if (tokenBuffer == null) {
                tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
            }
            tokenBuffer.writeFieldName(currentName);
            tokenBuffer.copyCurrentStructure(jsonParser);
            currentToken = jsonParser.nextToken();
        }
        return _deserializeTypedUsingDefaultImpl(jsonParser, deserializationContext, tokenBuffer);
    }

    protected Object _deserializeTypedForId(JsonParser jsonParser, DeserializationContext deserializationContext, TokenBuffer tokenBuffer) throws IOException {
        String text = jsonParser.getText();
        JsonDeserializer<Object> _findDeserializer = _findDeserializer(deserializationContext, text);
        if (this._typeIdVisible) {
            if (tokenBuffer == null) {
                tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
            }
            tokenBuffer.writeFieldName(jsonParser.getCurrentName());
            tokenBuffer.writeString(text);
        }
        if (tokenBuffer != null) {
            jsonParser.clearCurrentToken();
            jsonParser = JsonParserSequence.createFlattened(false, tokenBuffer.asParser(jsonParser), jsonParser);
        }
        jsonParser.nextToken();
        return _findDeserializer.deserialize(jsonParser, deserializationContext);
    }

    protected Object _deserializeTypedUsingDefaultImpl(JsonParser jsonParser, DeserializationContext deserializationContext, TokenBuffer tokenBuffer) throws IOException {
        JsonDeserializer<Object> _findDefaultImplDeserializer = _findDefaultImplDeserializer(deserializationContext);
        if (_findDefaultImplDeserializer == null) {
            Object deserializeIfNatural = TypeDeserializer.deserializeIfNatural(jsonParser, deserializationContext, this._baseType);
            if (deserializeIfNatural != null) {
                return deserializeIfNatural;
            }
            if (jsonParser.isExpectedStartArrayToken()) {
                return super.deserializeTypedFromAny(jsonParser, deserializationContext);
            }
            if (jsonParser.hasToken(JsonToken.VALUE_STRING) && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().trim().isEmpty()) {
                return null;
            }
            String format = String.format("missing type id property '%s'", this._typePropertyName);
            if (this._property != null) {
                format = String.format("%s (for POJO property '%s')", format, this._property.getName());
            }
            JavaType _handleMissingTypeId = _handleMissingTypeId(deserializationContext, format);
            if (_handleMissingTypeId == null) {
                return null;
            }
            _findDefaultImplDeserializer = deserializationContext.findContextualValueDeserializer(_handleMissingTypeId, this._property);
        }
        if (tokenBuffer != null) {
            tokenBuffer.writeEndObject();
            jsonParser = tokenBuffer.asParser(jsonParser);
            jsonParser.nextToken();
        }
        return _findDefaultImplDeserializer.deserialize(jsonParser, deserializationContext);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer, com.fasterxml.jackson.databind.jsontype.TypeDeserializer
    public Object deserializeTypedFromAny(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
            return super.deserializeTypedFromArray(jsonParser, deserializationContext);
        }
        return deserializeTypedFromObject(jsonParser, deserializationContext);
    }
}
