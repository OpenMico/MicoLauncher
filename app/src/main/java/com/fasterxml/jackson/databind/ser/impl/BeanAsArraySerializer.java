package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Set;

/* loaded from: classes.dex */
public class BeanAsArraySerializer extends BeanSerializerBase {
    private static final long serialVersionUID = 1;
    protected final BeanSerializerBase _defaultSerializer;

    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase
    protected BeanSerializerBase asArraySerializer() {
        return this;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public boolean isUnwrappingSerializer() {
        return false;
    }

    public BeanAsArraySerializer(BeanSerializerBase beanSerializerBase) {
        super(beanSerializerBase, (ObjectIdWriter) null);
        this._defaultSerializer = beanSerializerBase;
    }

    protected BeanAsArraySerializer(BeanSerializerBase beanSerializerBase, Set<String> set) {
        super(beanSerializerBase, set);
        this._defaultSerializer = beanSerializerBase;
    }

    protected BeanAsArraySerializer(BeanSerializerBase beanSerializerBase, ObjectIdWriter objectIdWriter, Object obj) {
        super(beanSerializerBase, objectIdWriter, obj);
        this._defaultSerializer = beanSerializerBase;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public JsonSerializer<Object> unwrappingSerializer(NameTransformer nameTransformer) {
        return this._defaultSerializer.unwrappingSerializer(nameTransformer);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase
    public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
        return this._defaultSerializer.withObjectIdWriter(objectIdWriter);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase, com.fasterxml.jackson.databind.JsonSerializer
    public BeanSerializerBase withFilterId(Object obj) {
        return new BeanAsArraySerializer(this, this._objectIdWriter, obj);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase
    protected BeanAsArraySerializer withIgnorals(Set<String> set) {
        return new BeanAsArraySerializer(this, set);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase, com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        if (this._objectIdWriter != null) {
            _serializeWithObjectId(obj, jsonGenerator, serializerProvider, typeSerializer);
            return;
        }
        jsonGenerator.setCurrentValue(obj);
        WritableTypeId _typeIdDef = _typeIdDef(typeSerializer, obj, JsonToken.START_ARRAY);
        typeSerializer.writeTypePrefix(jsonGenerator, _typeIdDef);
        serializeAsArray(obj, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffix(jsonGenerator, _typeIdDef);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public final void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (!serializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) || !a(serializerProvider)) {
            jsonGenerator.writeStartArray();
            jsonGenerator.setCurrentValue(obj);
            serializeAsArray(obj, jsonGenerator, serializerProvider);
            jsonGenerator.writeEndArray();
            return;
        }
        serializeAsArray(obj, jsonGenerator, serializerProvider);
    }

    private boolean a(SerializerProvider serializerProvider) {
        BeanPropertyWriter[] beanPropertyWriterArr;
        if (this._filteredProps == null || serializerProvider.getActiveView() == null) {
            beanPropertyWriterArr = this._props;
        } else {
            beanPropertyWriterArr = this._filteredProps;
        }
        return beanPropertyWriterArr.length == 1;
    }

    protected final void serializeAsArray(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        BeanPropertyWriter[] beanPropertyWriterArr;
        if (this._filteredProps == null || serializerProvider.getActiveView() == null) {
            beanPropertyWriterArr = this._props;
        } else {
            beanPropertyWriterArr = this._filteredProps;
        }
        int i = 0;
        try {
            int length = beanPropertyWriterArr.length;
            while (i < length) {
                BeanPropertyWriter beanPropertyWriter = beanPropertyWriterArr[i];
                if (beanPropertyWriter == null) {
                    jsonGenerator.writeNull();
                } else {
                    beanPropertyWriter.serializeAsElement(obj, jsonGenerator, serializerProvider);
                }
                i++;
            }
        } catch (Exception e) {
            wrapAndThrow(serializerProvider, e, obj, i == beanPropertyWriterArr.length ? "[anySetter]" : beanPropertyWriterArr[i].getName());
        } catch (StackOverflowError e2) {
            JsonMappingException from = JsonMappingException.from(jsonGenerator, "Infinite recursion (StackOverflowError)", e2);
            from.prependPath(new JsonMappingException.Reference(obj, i == beanPropertyWriterArr.length ? "[anySetter]" : beanPropertyWriterArr[i].getName()));
            throw from;
        }
    }

    public String toString() {
        return "BeanAsArraySerializer for " + handledType().getName();
    }
}
