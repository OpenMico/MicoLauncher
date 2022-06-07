package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Set;

/* loaded from: classes.dex */
public class BeanAsArrayBuilderDeserializer extends BeanDeserializerBase {
    private static final long serialVersionUID = 1;
    protected final AnnotatedMethod _buildMethod;
    protected final BeanDeserializerBase _delegate;
    protected final SettableBeanProperty[] _orderedProperties;
    protected final JavaType _targetType;

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    protected BeanDeserializerBase asArrayDeserializer() {
        return this;
    }

    public BeanAsArrayBuilderDeserializer(BeanDeserializerBase beanDeserializerBase, JavaType javaType, SettableBeanProperty[] settableBeanPropertyArr, AnnotatedMethod annotatedMethod) {
        super(beanDeserializerBase);
        this._delegate = beanDeserializerBase;
        this._targetType = javaType;
        this._orderedProperties = settableBeanPropertyArr;
        this._buildMethod = annotatedMethod;
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase, com.fasterxml.jackson.databind.JsonDeserializer
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer nameTransformer) {
        return this._delegate.unwrappingDeserializer(nameTransformer);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializerBase withObjectIdReader(ObjectIdReader objectIdReader) {
        return new BeanAsArrayBuilderDeserializer(this._delegate.withObjectIdReader(objectIdReader), this._targetType, this._orderedProperties, this._buildMethod);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializerBase withIgnorableProperties(Set<String> set) {
        return new BeanAsArrayBuilderDeserializer(this._delegate.withIgnorableProperties(set), this._targetType, this._orderedProperties, this._buildMethod);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializerBase withBeanProperties(BeanPropertyMap beanPropertyMap) {
        return new BeanAsArrayBuilderDeserializer(this._delegate.withBeanProperties(beanPropertyMap), this._targetType, this._orderedProperties, this._buildMethod);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase, com.fasterxml.jackson.databind.JsonDeserializer
    public Boolean supportsUpdate(DeserializationConfig deserializationConfig) {
        return Boolean.FALSE;
    }

    protected final Object finishBuild(DeserializationContext deserializationContext, Object obj) throws IOException {
        try {
            return this._buildMethod.getMember().invoke(obj, null);
        } catch (Exception e) {
            return wrapInstantiationProblem(e, deserializationContext);
        }
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (!jsonParser.isExpectedStartArrayToken()) {
            return finishBuild(deserializationContext, _deserializeFromNonArray(jsonParser, deserializationContext));
        }
        if (!this._vanillaProcessing) {
            return finishBuild(deserializationContext, _deserializeNonVanilla(jsonParser, deserializationContext));
        }
        Object createUsingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
        SettableBeanProperty[] settableBeanPropertyArr = this._orderedProperties;
        int length = settableBeanPropertyArr.length;
        int i = 0;
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            if (i == length) {
                if (!this._ignoreAllUnknown && deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
                    deserializationContext.reportInputMismatch(handledType(), "Unexpected JSON values; expected at most %d properties (in JSON Array)", Integer.valueOf(length));
                }
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    jsonParser.skipChildren();
                }
                return finishBuild(deserializationContext, createUsingDefault);
            }
            SettableBeanProperty settableBeanProperty = settableBeanPropertyArr[i];
            if (settableBeanProperty != null) {
                try {
                    createUsingDefault = settableBeanProperty.deserializeSetAndReturn(jsonParser, deserializationContext, createUsingDefault);
                } catch (Exception e) {
                    wrapAndThrow(e, createUsingDefault, settableBeanProperty.getName(), deserializationContext);
                }
            } else {
                jsonParser.skipChildren();
            }
            i++;
        }
        return finishBuild(deserializationContext, createUsingDefault);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
        return this._delegate.deserialize(jsonParser, deserializationContext, obj);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public Object deserializeFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return _deserializeFromNonArray(jsonParser, deserializationContext);
    }

    protected Object _deserializeNonVanilla(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (this._nonStandardCreation) {
            return deserializeFromObjectUsingNonDefault(jsonParser, deserializationContext);
        }
        Object createUsingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
        if (this._injectables != null) {
            injectValues(deserializationContext, createUsingDefault);
        }
        Class<?> activeView = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        SettableBeanProperty[] settableBeanPropertyArr = this._orderedProperties;
        int length = settableBeanPropertyArr.length;
        int i = 0;
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            if (i == length) {
                if (!this._ignoreAllUnknown && deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
                    deserializationContext.reportWrongTokenException(this, JsonToken.END_ARRAY, "Unexpected JSON value(s); expected at most %d properties (in JSON Array)", Integer.valueOf(length));
                }
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    jsonParser.skipChildren();
                }
                return createUsingDefault;
            }
            SettableBeanProperty settableBeanProperty = settableBeanPropertyArr[i];
            i++;
            if (settableBeanProperty == null || (activeView != null && !settableBeanProperty.visibleInView(activeView))) {
                jsonParser.skipChildren();
            } else {
                try {
                    settableBeanProperty.deserializeSetAndReturn(jsonParser, deserializationContext, createUsingDefault);
                } catch (Exception e) {
                    wrapAndThrow(e, createUsingDefault, settableBeanProperty.getName(), deserializationContext);
                }
            }
        }
        return createUsingDefault;
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    protected final Object _deserializeUsingPropertyBased(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer startBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        SettableBeanProperty[] settableBeanPropertyArr = this._orderedProperties;
        int length = settableBeanPropertyArr.length;
        Class<?> activeView = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        Object obj = null;
        int i = 0;
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            SettableBeanProperty settableBeanProperty = i < length ? settableBeanPropertyArr[i] : null;
            if (settableBeanProperty == null) {
                jsonParser.skipChildren();
            } else if (activeView != null && !settableBeanProperty.visibleInView(activeView)) {
                jsonParser.skipChildren();
            } else if (obj != null) {
                try {
                    obj = settableBeanProperty.deserializeSetAndReturn(jsonParser, deserializationContext, obj);
                } catch (Exception e) {
                    wrapAndThrow(e, obj, settableBeanProperty.getName(), deserializationContext);
                }
            } else {
                String name = settableBeanProperty.getName();
                SettableBeanProperty findCreatorProperty = propertyBasedCreator.findCreatorProperty(name);
                if (findCreatorProperty != null) {
                    if (startBuilding.assignParameter(findCreatorProperty, findCreatorProperty.deserialize(jsonParser, deserializationContext))) {
                        try {
                            obj = propertyBasedCreator.build(deserializationContext, startBuilding);
                            if (obj.getClass() != this._beanType.getRawClass()) {
                                return deserializationContext.reportBadDefinition(this._beanType, String.format("Cannot support implicit polymorphic deserialization for POJOs-as-Arrays style: nominal type %s, actual type %s", this._beanType.getRawClass().getName(), obj.getClass().getName()));
                            }
                        } catch (Exception e2) {
                            wrapAndThrow(e2, this._beanType.getRawClass(), name, deserializationContext);
                        }
                    } else {
                        continue;
                    }
                } else if (!startBuilding.readIdProperty(name)) {
                    startBuilding.bufferProperty(settableBeanProperty, settableBeanProperty.deserialize(jsonParser, deserializationContext));
                }
            }
            i++;
        }
        if (obj != null) {
            return obj;
        }
        try {
            return propertyBasedCreator.build(deserializationContext, startBuilding);
        } catch (Exception e3) {
            return wrapInstantiationProblem(e3, deserializationContext);
        }
    }

    protected Object _deserializeFromNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializationContext.handleUnexpectedToken(handledType(), jsonParser.getCurrentToken(), jsonParser, "Cannot deserialize a POJO (of type %s) from non-Array representation (token: %s): type/property designed to be serialized as JSON Array", this._beanType.getRawClass().getName(), jsonParser.getCurrentToken());
    }
}
