package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.impl.BeanAsArrayDeserializer;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class BeanDeserializer extends BeanDeserializerBase implements Serializable {
    private static final long serialVersionUID = 1;
    protected transient Exception _nullFromCreator;
    private volatile transient NameTransformer a;

    public BeanDeserializer(BeanDeserializerBuilder beanDeserializerBuilder, BeanDescription beanDescription, BeanPropertyMap beanPropertyMap, Map<String, SettableBeanProperty> map, HashSet<String> hashSet, boolean z, boolean z2) {
        super(beanDeserializerBuilder, beanDescription, beanPropertyMap, map, hashSet, z, z2);
    }

    public BeanDeserializer(BeanDeserializerBase beanDeserializerBase) {
        super(beanDeserializerBase, beanDeserializerBase._ignoreAllUnknown);
    }

    protected BeanDeserializer(BeanDeserializerBase beanDeserializerBase, boolean z) {
        super(beanDeserializerBase, z);
    }

    public BeanDeserializer(BeanDeserializerBase beanDeserializerBase, NameTransformer nameTransformer) {
        super(beanDeserializerBase, nameTransformer);
    }

    public BeanDeserializer(BeanDeserializerBase beanDeserializerBase, ObjectIdReader objectIdReader) {
        super(beanDeserializerBase, objectIdReader);
    }

    public BeanDeserializer(BeanDeserializerBase beanDeserializerBase, Set<String> set) {
        super(beanDeserializerBase, set);
    }

    public BeanDeserializer(BeanDeserializerBase beanDeserializerBase, BeanPropertyMap beanPropertyMap) {
        super(beanDeserializerBase, beanPropertyMap);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase, com.fasterxml.jackson.databind.JsonDeserializer
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer nameTransformer) {
        if (getClass() != BeanDeserializer.class || this.a == nameTransformer) {
            return this;
        }
        this.a = nameTransformer;
        try {
            return new BeanDeserializer(this, nameTransformer);
        } finally {
            this.a = null;
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializer withObjectIdReader(ObjectIdReader objectIdReader) {
        return new BeanDeserializer(this, objectIdReader);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializer withIgnorableProperties(Set<String> set) {
        return new BeanDeserializer(this, set);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializerBase withBeanProperties(BeanPropertyMap beanPropertyMap) {
        return new BeanDeserializer(this, beanPropertyMap);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    protected BeanDeserializerBase asArrayDeserializer() {
        return new BeanAsArrayDeserializer(this, this._beanProperties.getPropertiesInInsertionOrder());
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (!jsonParser.isExpectedStartObjectToken()) {
            return _deserializeOther(jsonParser, deserializationContext, jsonParser.getCurrentToken());
        }
        if (this._vanillaProcessing) {
            return a(jsonParser, deserializationContext, jsonParser.nextToken());
        }
        jsonParser.nextToken();
        if (this._objectIdReader != null) {
            return deserializeWithObjectId(jsonParser, deserializationContext);
        }
        return deserializeFromObject(jsonParser, deserializationContext);
    }

    protected final Object _deserializeOther(JsonParser jsonParser, DeserializationContext deserializationContext, JsonToken jsonToken) throws IOException {
        if (jsonToken != null) {
            switch (jsonToken) {
                case VALUE_STRING:
                    return deserializeFromString(jsonParser, deserializationContext);
                case VALUE_NUMBER_INT:
                    return deserializeFromNumber(jsonParser, deserializationContext);
                case VALUE_NUMBER_FLOAT:
                    return deserializeFromDouble(jsonParser, deserializationContext);
                case VALUE_EMBEDDED_OBJECT:
                    return deserializeFromEmbedded(jsonParser, deserializationContext);
                case VALUE_TRUE:
                case VALUE_FALSE:
                    return deserializeFromBoolean(jsonParser, deserializationContext);
                case VALUE_NULL:
                    return deserializeFromNull(jsonParser, deserializationContext);
                case START_ARRAY:
                    return deserializeFromArray(jsonParser, deserializationContext);
                case FIELD_NAME:
                case END_OBJECT:
                    if (this._vanillaProcessing) {
                        return a(jsonParser, deserializationContext, jsonToken);
                    }
                    if (this._objectIdReader != null) {
                        return deserializeWithObjectId(jsonParser, deserializationContext);
                    }
                    return deserializeFromObject(jsonParser, deserializationContext);
            }
        }
        return deserializationContext.handleUnexpectedToken(handledType(), jsonParser);
    }

    @Deprecated
    protected Object _missingToken(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        throw deserializationContext.endOfInputException(handledType());
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
        String str;
        Class<?> activeView;
        jsonParser.setCurrentValue(obj);
        if (this._injectables != null) {
            injectValues(deserializationContext, obj);
        }
        if (this._unwrappedPropertyHandler != null) {
            return deserializeWithUnwrapped(jsonParser, deserializationContext, obj);
        }
        if (this._externalTypeIdHandler != null) {
            return deserializeWithExternalTypeId(jsonParser, deserializationContext, obj);
        }
        if (jsonParser.isExpectedStartObjectToken()) {
            str = jsonParser.nextFieldName();
            if (str == null) {
                return obj;
            }
        } else if (!jsonParser.hasTokenId(5)) {
            return obj;
        } else {
            str = jsonParser.getCurrentName();
        }
        if (this._needViewProcesing && (activeView = deserializationContext.getActiveView()) != null) {
            return deserializeWithView(jsonParser, deserializationContext, obj, activeView);
        }
        do {
            jsonParser.nextToken();
            SettableBeanProperty find = this._beanProperties.find(str);
            if (find != null) {
                try {
                    find.deserializeAndSet(jsonParser, deserializationContext, obj);
                } catch (Exception e) {
                    wrapAndThrow(e, obj, str, deserializationContext);
                }
            } else {
                handleUnknownVanilla(jsonParser, deserializationContext, obj, str);
            }
            str = jsonParser.nextFieldName();
        } while (str != null);
        return obj;
    }

    private final Object a(JsonParser jsonParser, DeserializationContext deserializationContext, JsonToken jsonToken) throws IOException {
        Object createUsingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
        jsonParser.setCurrentValue(createUsingDefault);
        if (!jsonParser.hasTokenId(5)) {
            return createUsingDefault;
        }
        String currentName = jsonParser.getCurrentName();
        do {
            jsonParser.nextToken();
            SettableBeanProperty find = this._beanProperties.find(currentName);
            if (find != null) {
                try {
                    find.deserializeAndSet(jsonParser, deserializationContext, createUsingDefault);
                } catch (Exception e) {
                    wrapAndThrow(e, createUsingDefault, currentName, deserializationContext);
                }
            } else {
                handleUnknownVanilla(jsonParser, deserializationContext, createUsingDefault, currentName);
            }
            currentName = jsonParser.nextFieldName();
        } while (currentName != null);
        return createUsingDefault;
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public Object deserializeFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Class<?> activeView;
        Object objectId;
        if (this._objectIdReader != null && this._objectIdReader.maySerializeAsObject() && jsonParser.hasTokenId(5) && this._objectIdReader.isValidReferencePropertyName(jsonParser.getCurrentName(), jsonParser)) {
            return deserializeFromObjectId(jsonParser, deserializationContext);
        }
        if (!this._nonStandardCreation) {
            Object createUsingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
            jsonParser.setCurrentValue(createUsingDefault);
            if (jsonParser.canReadObjectId() && (objectId = jsonParser.getObjectId()) != null) {
                _handleTypedObjectId(jsonParser, deserializationContext, createUsingDefault, objectId);
            }
            if (this._injectables != null) {
                injectValues(deserializationContext, createUsingDefault);
            }
            if (this._needViewProcesing && (activeView = deserializationContext.getActiveView()) != null) {
                return deserializeWithView(jsonParser, deserializationContext, createUsingDefault, activeView);
            }
            if (!jsonParser.hasTokenId(5)) {
                return createUsingDefault;
            }
            String currentName = jsonParser.getCurrentName();
            do {
                jsonParser.nextToken();
                SettableBeanProperty find = this._beanProperties.find(currentName);
                if (find != null) {
                    try {
                        find.deserializeAndSet(jsonParser, deserializationContext, createUsingDefault);
                    } catch (Exception e) {
                        wrapAndThrow(e, createUsingDefault, currentName, deserializationContext);
                    }
                } else {
                    handleUnknownVanilla(jsonParser, deserializationContext, createUsingDefault, currentName);
                }
                currentName = jsonParser.nextFieldName();
            } while (currentName != null);
            return createUsingDefault;
        } else if (this._unwrappedPropertyHandler != null) {
            return deserializeWithUnwrapped(jsonParser, deserializationContext);
        } else {
            if (this._externalTypeIdHandler != null) {
                return deserializeWithExternalTypeId(jsonParser, deserializationContext);
            }
            Object deserializeFromObjectUsingNonDefault = deserializeFromObjectUsingNonDefault(jsonParser, deserializationContext);
            if (this._injectables != null) {
                injectValues(deserializationContext, deserializeFromObjectUsingNonDefault);
            }
            return deserializeFromObjectUsingNonDefault;
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    protected Object _deserializeUsingPropertyBased(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object obj;
        Object obj2;
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer startBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        Class<?> activeView = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        JsonToken currentToken = jsonParser.getCurrentToken();
        ArrayList<a> arrayList = null;
        TokenBuffer tokenBuffer = null;
        while (currentToken == JsonToken.FIELD_NAME) {
            String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if (!startBuilding.readIdProperty(currentName)) {
                SettableBeanProperty findCreatorProperty = propertyBasedCreator.findCreatorProperty(currentName);
                if (findCreatorProperty == null) {
                    SettableBeanProperty find = this._beanProperties.find(currentName);
                    if (find != null) {
                        try {
                            startBuilding.bufferProperty(find, _deserializeWithErrorWrapping(jsonParser, deserializationContext, find));
                        } catch (UnresolvedForwardReference e) {
                            a a2 = a(deserializationContext, find, startBuilding, e);
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(a2);
                        }
                    } else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                        handleIgnoredProperty(jsonParser, deserializationContext, handledType(), currentName);
                    } else if (this._anySetter != null) {
                        try {
                            startBuilding.bufferAnyProperty(this._anySetter, currentName, this._anySetter.deserialize(jsonParser, deserializationContext));
                        } catch (Exception e2) {
                            wrapAndThrow(e2, this._beanType.getRawClass(), currentName, deserializationContext);
                        }
                    } else {
                        if (tokenBuffer == null) {
                            tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
                        }
                        tokenBuffer.writeFieldName(currentName);
                        tokenBuffer.copyCurrentStructure(jsonParser);
                    }
                } else if (activeView != null && !findCreatorProperty.visibleInView(activeView)) {
                    jsonParser.skipChildren();
                } else if (startBuilding.assignParameter(findCreatorProperty, _deserializeWithErrorWrapping(jsonParser, deserializationContext, findCreatorProperty))) {
                    jsonParser.nextToken();
                    try {
                        obj2 = propertyBasedCreator.build(deserializationContext, startBuilding);
                    } catch (Exception e3) {
                        obj2 = wrapInstantiationProblem(e3, deserializationContext);
                    }
                    if (obj2 == null) {
                        return deserializationContext.handleInstantiationProblem(handledType(), null, _creatorReturnedNullException());
                    }
                    jsonParser.setCurrentValue(obj2);
                    if (obj2.getClass() != this._beanType.getRawClass()) {
                        return handlePolymorphic(jsonParser, deserializationContext, obj2, tokenBuffer);
                    }
                    if (tokenBuffer != null) {
                        obj2 = handleUnknownProperties(deserializationContext, obj2, tokenBuffer);
                    }
                    return deserialize(jsonParser, deserializationContext, obj2);
                }
            }
            currentToken = jsonParser.nextToken();
        }
        try {
            obj = propertyBasedCreator.build(deserializationContext, startBuilding);
        } catch (Exception e4) {
            wrapInstantiationProblem(e4, deserializationContext);
            obj = null;
        }
        if (arrayList != null) {
            for (a aVar : arrayList) {
                aVar.a(obj);
            }
        }
        if (tokenBuffer == null) {
            return obj;
        }
        if (obj.getClass() != this._beanType.getRawClass()) {
            return handlePolymorphic(null, deserializationContext, obj, tokenBuffer);
        }
        return handleUnknownProperties(deserializationContext, obj, tokenBuffer);
    }

    private a a(DeserializationContext deserializationContext, SettableBeanProperty settableBeanProperty, PropertyValueBuffer propertyValueBuffer, UnresolvedForwardReference unresolvedForwardReference) throws JsonMappingException {
        a aVar = new a(deserializationContext, unresolvedForwardReference, settableBeanProperty.getType(), propertyValueBuffer, settableBeanProperty);
        unresolvedForwardReference.getRoid().appendReferring(aVar);
        return aVar;
    }

    protected final Object _deserializeWithErrorWrapping(JsonParser jsonParser, DeserializationContext deserializationContext, SettableBeanProperty settableBeanProperty) throws IOException {
        try {
            return settableBeanProperty.deserialize(jsonParser, deserializationContext);
        } catch (Exception e) {
            wrapAndThrow(e, this._beanType.getRawClass(), settableBeanProperty.getName(), deserializationContext);
            return null;
        }
    }

    protected Object deserializeFromNull(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (!jsonParser.requiresCustomCodec()) {
            return deserializationContext.handleUnexpectedToken(handledType(), jsonParser);
        }
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        tokenBuffer.writeEndObject();
        JsonParser asParser = tokenBuffer.asParser(jsonParser);
        asParser.nextToken();
        Object a2 = this._vanillaProcessing ? a(asParser, deserializationContext, JsonToken.END_OBJECT) : deserializeFromObject(asParser, deserializationContext);
        asParser.close();
        return a2;
    }

    protected final Object deserializeWithView(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj, Class<?> cls) throws IOException {
        if (!jsonParser.hasTokenId(5)) {
            return obj;
        }
        String currentName = jsonParser.getCurrentName();
        do {
            jsonParser.nextToken();
            SettableBeanProperty find = this._beanProperties.find(currentName);
            if (find == null) {
                handleUnknownVanilla(jsonParser, deserializationContext, obj, currentName);
            } else if (!find.visibleInView(cls)) {
                jsonParser.skipChildren();
            } else {
                try {
                    find.deserializeAndSet(jsonParser, deserializationContext, obj);
                } catch (Exception e) {
                    wrapAndThrow(e, obj, currentName, deserializationContext);
                }
            }
            currentName = jsonParser.nextFieldName();
        } while (currentName != null);
        return obj;
    }

    protected Object deserializeWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (this._propertyBasedCreator != null) {
            return deserializeUsingPropertyBasedWithUnwrapped(jsonParser, deserializationContext);
        }
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        tokenBuffer.writeStartObject();
        Object createUsingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
        jsonParser.setCurrentValue(createUsingDefault);
        if (this._injectables != null) {
            injectValues(deserializationContext, createUsingDefault);
        }
        String str = null;
        Class<?> activeView = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        if (jsonParser.hasTokenId(5)) {
            str = jsonParser.getCurrentName();
        }
        while (str != null) {
            jsonParser.nextToken();
            SettableBeanProperty find = this._beanProperties.find(str);
            if (find != null) {
                if (activeView == null || find.visibleInView(activeView)) {
                    try {
                        find.deserializeAndSet(jsonParser, deserializationContext, createUsingDefault);
                    } catch (Exception e) {
                        wrapAndThrow(e, createUsingDefault, str, deserializationContext);
                    }
                } else {
                    jsonParser.skipChildren();
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(str)) {
                handleIgnoredProperty(jsonParser, deserializationContext, createUsingDefault, str);
            } else if (this._anySetter == null) {
                tokenBuffer.writeFieldName(str);
                tokenBuffer.copyCurrentStructure(jsonParser);
            } else {
                TokenBuffer asCopyOfValue = TokenBuffer.asCopyOfValue(jsonParser);
                tokenBuffer.writeFieldName(str);
                tokenBuffer.append(asCopyOfValue);
                try {
                    this._anySetter.deserializeAndSet(asCopyOfValue.asParserOnFirstToken(), deserializationContext, createUsingDefault, str);
                } catch (Exception e2) {
                    wrapAndThrow(e2, createUsingDefault, str, deserializationContext);
                }
            }
            str = jsonParser.nextFieldName();
        }
        tokenBuffer.writeEndObject();
        this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, createUsingDefault, tokenBuffer);
        return createUsingDefault;
    }

    protected Object deserializeWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.START_OBJECT) {
            currentToken = jsonParser.nextToken();
        }
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        tokenBuffer.writeStartObject();
        Class<?> activeView = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        while (currentToken == JsonToken.FIELD_NAME) {
            String currentName = jsonParser.getCurrentName();
            SettableBeanProperty find = this._beanProperties.find(currentName);
            jsonParser.nextToken();
            if (find != null) {
                if (activeView == null || find.visibleInView(activeView)) {
                    try {
                        find.deserializeAndSet(jsonParser, deserializationContext, obj);
                    } catch (Exception e) {
                        wrapAndThrow(e, obj, currentName, deserializationContext);
                    }
                } else {
                    jsonParser.skipChildren();
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                handleIgnoredProperty(jsonParser, deserializationContext, obj, currentName);
            } else if (this._anySetter == null) {
                tokenBuffer.writeFieldName(currentName);
                tokenBuffer.copyCurrentStructure(jsonParser);
            } else {
                TokenBuffer asCopyOfValue = TokenBuffer.asCopyOfValue(jsonParser);
                tokenBuffer.writeFieldName(currentName);
                tokenBuffer.append(asCopyOfValue);
                try {
                    this._anySetter.deserializeAndSet(asCopyOfValue.asParserOnFirstToken(), deserializationContext, obj, currentName);
                } catch (Exception e2) {
                    wrapAndThrow(e2, obj, currentName, deserializationContext);
                }
            }
            currentToken = jsonParser.nextToken();
        }
        tokenBuffer.writeEndObject();
        this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, obj, tokenBuffer);
        return obj;
    }

    protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object obj;
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer startBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        tokenBuffer.writeStartObject();
        JsonToken currentToken = jsonParser.getCurrentToken();
        while (currentToken == JsonToken.FIELD_NAME) {
            String currentName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            SettableBeanProperty findCreatorProperty = propertyBasedCreator.findCreatorProperty(currentName);
            if (findCreatorProperty != null) {
                if (startBuilding.assignParameter(findCreatorProperty, _deserializeWithErrorWrapping(jsonParser, deserializationContext, findCreatorProperty))) {
                    JsonToken nextToken = jsonParser.nextToken();
                    try {
                        obj = propertyBasedCreator.build(deserializationContext, startBuilding);
                    } catch (Exception e) {
                        obj = wrapInstantiationProblem(e, deserializationContext);
                    }
                    jsonParser.setCurrentValue(obj);
                    while (nextToken == JsonToken.FIELD_NAME) {
                        tokenBuffer.copyCurrentStructure(jsonParser);
                        nextToken = jsonParser.nextToken();
                    }
                    if (nextToken != JsonToken.END_OBJECT) {
                        deserializationContext.reportWrongTokenException(this, JsonToken.END_OBJECT, "Attempted to unwrap '%s' value", handledType().getName());
                    }
                    tokenBuffer.writeEndObject();
                    if (obj.getClass() == this._beanType.getRawClass()) {
                        return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, obj, tokenBuffer);
                    }
                    deserializationContext.reportInputMismatch(findCreatorProperty, "Cannot create polymorphic instances with unwrapped values", new Object[0]);
                    return null;
                }
            } else if (!startBuilding.readIdProperty(currentName)) {
                SettableBeanProperty find = this._beanProperties.find(currentName);
                if (find != null) {
                    startBuilding.bufferProperty(find, _deserializeWithErrorWrapping(jsonParser, deserializationContext, find));
                } else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                    handleIgnoredProperty(jsonParser, deserializationContext, handledType(), currentName);
                } else if (this._anySetter == null) {
                    tokenBuffer.writeFieldName(currentName);
                    tokenBuffer.copyCurrentStructure(jsonParser);
                } else {
                    TokenBuffer asCopyOfValue = TokenBuffer.asCopyOfValue(jsonParser);
                    tokenBuffer.writeFieldName(currentName);
                    tokenBuffer.append(asCopyOfValue);
                    try {
                        startBuilding.bufferAnyProperty(this._anySetter, currentName, this._anySetter.deserialize(asCopyOfValue.asParserOnFirstToken(), deserializationContext));
                    } catch (Exception e2) {
                        wrapAndThrow(e2, this._beanType.getRawClass(), currentName, deserializationContext);
                    }
                }
            }
            currentToken = jsonParser.nextToken();
        }
        try {
            return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, propertyBasedCreator.build(deserializationContext, startBuilding), tokenBuffer);
        } catch (Exception e3) {
            wrapInstantiationProblem(e3, deserializationContext);
            return null;
        }
    }

    protected Object deserializeWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (this._propertyBasedCreator != null) {
            return deserializeUsingPropertyBasedWithExternalTypeId(jsonParser, deserializationContext);
        }
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        return deserializeWithExternalTypeId(jsonParser, deserializationContext, this._valueInstantiator.createUsingDefault(deserializationContext));
    }

    protected Object deserializeWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
        Class<?> activeView = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        ExternalTypeHandler start = this._externalTypeIdHandler.start();
        JsonToken currentToken = jsonParser.getCurrentToken();
        while (currentToken == JsonToken.FIELD_NAME) {
            String currentName = jsonParser.getCurrentName();
            JsonToken nextToken = jsonParser.nextToken();
            SettableBeanProperty find = this._beanProperties.find(currentName);
            if (find != null) {
                if (nextToken.isScalarValue()) {
                    start.handleTypePropertyValue(jsonParser, deserializationContext, currentName, obj);
                }
                if (activeView == null || find.visibleInView(activeView)) {
                    try {
                        find.deserializeAndSet(jsonParser, deserializationContext, obj);
                    } catch (Exception e) {
                        wrapAndThrow(e, obj, currentName, deserializationContext);
                    }
                } else {
                    jsonParser.skipChildren();
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(currentName)) {
                handleIgnoredProperty(jsonParser, deserializationContext, obj, currentName);
            } else if (!start.handlePropertyValue(jsonParser, deserializationContext, currentName, obj)) {
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jsonParser, deserializationContext, obj, currentName);
                    } catch (Exception e2) {
                        wrapAndThrow(e2, obj, currentName, deserializationContext);
                    }
                } else {
                    handleUnknownProperty(jsonParser, deserializationContext, obj, currentName);
                }
            }
            currentToken = jsonParser.nextToken();
        }
        return start.complete(jsonParser, deserializationContext, obj);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0048, code lost:
        if (r5 != com.fasterxml.jackson.core.JsonToken.FIELD_NAME) goto L_0x0055;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004a, code lost:
        r8.nextToken();
        r3.copyCurrentStructure(r8);
        r5 = r8.nextToken();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x005f, code lost:
        if (r1.getClass() == r7._beanType.getRawClass()) goto L_0x007d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x007c, code lost:
        return r9.reportBadDefinition(r7._beanType, java.lang.String.format("Cannot create polymorphic instances with external type ids (%s -> %s)", r7._beanType, r1.getClass()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0081, code lost:
        return r0.complete(r8, r9, r1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.lang.Object deserializeUsingPropertyBasedWithExternalTypeId(com.fasterxml.jackson.core.JsonParser r8, com.fasterxml.jackson.databind.DeserializationContext r9) throws java.io.IOException {
        /*
            r7 = this;
            com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler r0 = r7._externalTypeIdHandler
            com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler r0 = r0.start()
            com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator r1 = r7._propertyBasedCreator
            com.fasterxml.jackson.databind.deser.impl.ObjectIdReader r2 = r7._objectIdReader
            com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer r2 = r1.startBuilding(r8, r9, r2)
            com.fasterxml.jackson.databind.util.TokenBuffer r3 = new com.fasterxml.jackson.databind.util.TokenBuffer
            r3.<init>(r8, r9)
            r3.writeStartObject()
            com.fasterxml.jackson.core.JsonToken r4 = r8.getCurrentToken()
        L_0x001a:
            com.fasterxml.jackson.core.JsonToken r5 = com.fasterxml.jackson.core.JsonToken.FIELD_NAME
            if (r4 != r5) goto L_0x00d4
            java.lang.String r4 = r8.getCurrentName()
            r8.nextToken()
            com.fasterxml.jackson.databind.deser.SettableBeanProperty r5 = r1.findCreatorProperty(r4)
            r6 = 0
            if (r5 == 0) goto L_0x008d
            boolean r6 = r0.handlePropertyValue(r8, r9, r4, r6)
            if (r6 == 0) goto L_0x0034
            goto L_0x00ce
        L_0x0034:
            java.lang.Object r6 = r7._deserializeWithErrorWrapping(r8, r9, r5)
            boolean r5 = r2.assignParameter(r5, r6)
            if (r5 == 0) goto L_0x00ce
            com.fasterxml.jackson.core.JsonToken r5 = r8.nextToken()
            java.lang.Object r1 = r1.build(r9, r2)     // Catch: Exception -> 0x0082
        L_0x0046:
            com.fasterxml.jackson.core.JsonToken r2 = com.fasterxml.jackson.core.JsonToken.FIELD_NAME
            if (r5 != r2) goto L_0x0055
            r8.nextToken()
            r3.copyCurrentStructure(r8)
            com.fasterxml.jackson.core.JsonToken r5 = r8.nextToken()
            goto L_0x0046
        L_0x0055:
            java.lang.Class r2 = r1.getClass()
            com.fasterxml.jackson.databind.JavaType r3 = r7._beanType
            java.lang.Class r3 = r3.getRawClass()
            if (r2 == r3) goto L_0x007d
            com.fasterxml.jackson.databind.JavaType r8 = r7._beanType
            java.lang.String r0 = "Cannot create polymorphic instances with external type ids (%s -> %s)"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            com.fasterxml.jackson.databind.JavaType r4 = r7._beanType
            r2[r3] = r4
            r3 = 1
            java.lang.Class r1 = r1.getClass()
            r2[r3] = r1
            java.lang.String r0 = java.lang.String.format(r0, r2)
            java.lang.Object r8 = r9.reportBadDefinition(r8, r0)
            return r8
        L_0x007d:
            java.lang.Object r8 = r0.complete(r8, r9, r1)
            return r8
        L_0x0082:
            r5 = move-exception
            com.fasterxml.jackson.databind.JavaType r6 = r7._beanType
            java.lang.Class r6 = r6.getRawClass()
            r7.wrapAndThrow(r5, r6, r4, r9)
            goto L_0x00ce
        L_0x008d:
            boolean r5 = r2.readIdProperty(r4)
            if (r5 == 0) goto L_0x0094
            goto L_0x00ce
        L_0x0094:
            com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap r5 = r7._beanProperties
            com.fasterxml.jackson.databind.deser.SettableBeanProperty r5 = r5.find(r4)
            if (r5 == 0) goto L_0x00a4
            java.lang.Object r4 = r5.deserialize(r8, r9)
            r2.bufferProperty(r5, r4)
            goto L_0x00ce
        L_0x00a4:
            boolean r5 = r0.handlePropertyValue(r8, r9, r4, r6)
            if (r5 == 0) goto L_0x00ab
            goto L_0x00ce
        L_0x00ab:
            java.util.Set r5 = r7._ignorableProps
            if (r5 == 0) goto L_0x00bf
            java.util.Set r5 = r7._ignorableProps
            boolean r5 = r5.contains(r4)
            if (r5 == 0) goto L_0x00bf
            java.lang.Class r5 = r7.handledType()
            r7.handleIgnoredProperty(r8, r9, r5, r4)
            goto L_0x00ce
        L_0x00bf:
            com.fasterxml.jackson.databind.deser.SettableAnyProperty r5 = r7._anySetter
            if (r5 == 0) goto L_0x00ce
            com.fasterxml.jackson.databind.deser.SettableAnyProperty r5 = r7._anySetter
            com.fasterxml.jackson.databind.deser.SettableAnyProperty r6 = r7._anySetter
            java.lang.Object r6 = r6.deserialize(r8, r9)
            r2.bufferAnyProperty(r5, r4, r6)
        L_0x00ce:
            com.fasterxml.jackson.core.JsonToken r4 = r8.nextToken()
            goto L_0x001a
        L_0x00d4:
            r3.writeEndObject()
            java.lang.Object r8 = r0.complete(r8, r9, r2, r1)     // Catch: Exception -> 0x00dc
            return r8
        L_0x00dc:
            r8 = move-exception
            java.lang.Object r8 = r7.wrapInstantiationProblem(r8, r9)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeUsingPropertyBasedWithExternalTypeId(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext):java.lang.Object");
    }

    protected Exception _creatorReturnedNullException() {
        if (this._nullFromCreator == null) {
            this._nullFromCreator = new NullPointerException("JSON Creator returned null");
        }
        return this._nullFromCreator;
    }

    /* loaded from: classes.dex */
    public static class a extends ReadableObjectId.Referring {
        private final DeserializationContext a;
        private final SettableBeanProperty b;
        private Object c;

        a(DeserializationContext deserializationContext, UnresolvedForwardReference unresolvedForwardReference, JavaType javaType, PropertyValueBuffer propertyValueBuffer, SettableBeanProperty settableBeanProperty) {
            super(unresolvedForwardReference, javaType);
            this.a = deserializationContext;
            this.b = settableBeanProperty;
        }

        public void a(Object obj) {
            this.c = obj;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.ReadableObjectId.Referring
        public void handleResolvedForwardReference(Object obj, Object obj2) throws IOException {
            if (this.c == null) {
                DeserializationContext deserializationContext = this.a;
                SettableBeanProperty settableBeanProperty = this.b;
                deserializationContext.reportInputMismatch(settableBeanProperty, "Cannot resolve ObjectId forward reference using property '%s' (of type %s): Bean not yet resolved", settableBeanProperty.getName(), this.b.getDeclaringClass().getName());
            }
            this.b.set(this.c, obj2);
        }
    }
}
