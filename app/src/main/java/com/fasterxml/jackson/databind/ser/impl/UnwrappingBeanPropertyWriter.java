package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class UnwrappingBeanPropertyWriter extends BeanPropertyWriter implements Serializable {
    private static final long serialVersionUID = 1;
    protected final NameTransformer _nameTransformer;

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter
    public boolean isUnwrapping() {
        return true;
    }

    public UnwrappingBeanPropertyWriter(BeanPropertyWriter beanPropertyWriter, NameTransformer nameTransformer) {
        super(beanPropertyWriter);
        this._nameTransformer = nameTransformer;
    }

    public UnwrappingBeanPropertyWriter(UnwrappingBeanPropertyWriter unwrappingBeanPropertyWriter, NameTransformer nameTransformer, SerializedString serializedString) {
        super(unwrappingBeanPropertyWriter, serializedString);
        this._nameTransformer = nameTransformer;
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter
    public UnwrappingBeanPropertyWriter rename(NameTransformer nameTransformer) {
        return _new(NameTransformer.chainedTransformer(nameTransformer, this._nameTransformer), new SerializedString(nameTransformer.transform(this._name.getValue())));
    }

    protected UnwrappingBeanPropertyWriter _new(NameTransformer nameTransformer, SerializedString serializedString) {
        return new UnwrappingBeanPropertyWriter(this, nameTransformer, serializedString);
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter, com.fasterxml.jackson.databind.ser.PropertyWriter
    public void serializeAsField(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        Object obj2 = get(obj);
        if (obj2 != null) {
            JsonSerializer<?> jsonSerializer = this._serializer;
            if (jsonSerializer == null) {
                Class<?> cls = obj2.getClass();
                PropertySerializerMap propertySerializerMap = this._dynamicSerializers;
                JsonSerializer<?> serializerFor = propertySerializerMap.serializerFor(cls);
                jsonSerializer = serializerFor == null ? _findAndAddDynamic(propertySerializerMap, cls, serializerProvider) : serializerFor;
            }
            if (this._suppressableValue != null) {
                if (MARKER_FOR_EMPTY == this._suppressableValue) {
                    if (jsonSerializer.isEmpty(serializerProvider, obj2)) {
                        return;
                    }
                } else if (this._suppressableValue.equals(obj2)) {
                    return;
                }
            }
            if (obj2 != obj || !_handleSelfReference(obj, jsonGenerator, serializerProvider, jsonSerializer)) {
                if (!jsonSerializer.isUnwrappingSerializer()) {
                    jsonGenerator.writeFieldName(this._name);
                }
                if (this._typeSerializer == null) {
                    jsonSerializer.serialize(obj2, jsonGenerator, serializerProvider);
                } else {
                    jsonSerializer.serializeWithType(obj2, jsonGenerator, serializerProvider, this._typeSerializer);
                }
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter
    public void assignSerializer(JsonSerializer<Object> jsonSerializer) {
        if (jsonSerializer != null) {
            NameTransformer nameTransformer = this._nameTransformer;
            if (jsonSerializer.isUnwrappingSerializer() && (jsonSerializer instanceof UnwrappingBeanSerializer)) {
                nameTransformer = NameTransformer.chainedTransformer(nameTransformer, ((UnwrappingBeanSerializer) jsonSerializer)._nameTransformer);
            }
            jsonSerializer = jsonSerializer.unwrappingSerializer(nameTransformer);
        }
        super.assignSerializer(jsonSerializer);
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter, com.fasterxml.jackson.databind.ser.PropertyWriter, com.fasterxml.jackson.databind.BeanProperty
    public void depositSchemaProperty(final JsonObjectFormatVisitor jsonObjectFormatVisitor, SerializerProvider serializerProvider) throws JsonMappingException {
        JsonSerializer<Object> unwrappingSerializer = serializerProvider.findValueSerializer(getType(), this).unwrappingSerializer(this._nameTransformer);
        if (unwrappingSerializer.isUnwrappingSerializer()) {
            unwrappingSerializer.acceptJsonFormatVisitor(new JsonFormatVisitorWrapper.Base(serializerProvider) { // from class: com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter.1
                @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper.Base, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper
                public JsonObjectFormatVisitor expectObjectFormat(JavaType javaType) throws JsonMappingException {
                    return jsonObjectFormatVisitor;
                }
            }, getType());
        } else {
            super.depositSchemaProperty(jsonObjectFormatVisitor, serializerProvider);
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter
    protected void _depositSchemaProperty(ObjectNode objectNode, JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode.get("properties");
        if (jsonNode2 != null) {
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode2.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> next = fields.next();
                String key = next.getKey();
                NameTransformer nameTransformer = this._nameTransformer;
                if (nameTransformer != null) {
                    key = nameTransformer.transform(key);
                }
                objectNode.set(key, next.getValue());
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter
    protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> cls, SerializerProvider serializerProvider) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        if (this._nonTrivialBaseType != null) {
            jsonSerializer = serializerProvider.findValueSerializer(serializerProvider.constructSpecializedType(this._nonTrivialBaseType, cls), this);
        } else {
            jsonSerializer = serializerProvider.findValueSerializer(cls, this);
        }
        NameTransformer nameTransformer = this._nameTransformer;
        if (jsonSerializer.isUnwrappingSerializer() && (jsonSerializer instanceof UnwrappingBeanSerializer)) {
            nameTransformer = NameTransformer.chainedTransformer(nameTransformer, ((UnwrappingBeanSerializer) jsonSerializer)._nameTransformer);
        }
        JsonSerializer<Object> unwrappingSerializer = jsonSerializer.unwrappingSerializer(nameTransformer);
        this._dynamicSerializers = this._dynamicSerializers.newWith(cls, unwrappingSerializer);
        return unwrappingSerializer;
    }
}
