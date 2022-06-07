package com.xiaomi.ext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;

/* loaded from: classes3.dex */
public class Jdk8UnwrappingOptionalBeanPropertyWriter extends UnwrappingBeanPropertyWriter {
    private static final long serialVersionUID = 1;
    protected final Object _empty;

    public Jdk8UnwrappingOptionalBeanPropertyWriter(BeanPropertyWriter beanPropertyWriter, NameTransformer nameTransformer, Object obj) {
        super(beanPropertyWriter, nameTransformer);
        this._empty = obj;
    }

    protected Jdk8UnwrappingOptionalBeanPropertyWriter(Jdk8UnwrappingOptionalBeanPropertyWriter jdk8UnwrappingOptionalBeanPropertyWriter, NameTransformer nameTransformer, SerializedString serializedString) {
        super(jdk8UnwrappingOptionalBeanPropertyWriter, nameTransformer, serializedString);
        this._empty = jdk8UnwrappingOptionalBeanPropertyWriter._empty;
    }

    @Override // com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter
    protected UnwrappingBeanPropertyWriter _new(NameTransformer nameTransformer, SerializedString serializedString) {
        return new Jdk8UnwrappingOptionalBeanPropertyWriter(this, nameTransformer, serializedString);
    }

    @Override // com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter, com.fasterxml.jackson.databind.ser.BeanPropertyWriter, com.fasterxml.jackson.databind.ser.PropertyWriter
    public void serializeAsField(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        Object obj2;
        if (this._nullSerializer != null || ((obj2 = get(obj)) != null && !obj2.equals(this._empty))) {
            super.serializeAsField(obj, jsonGenerator, serializerProvider);
        }
    }
}
