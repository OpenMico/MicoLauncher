package com.xiaomi.ext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;

/* loaded from: classes3.dex */
public class Jdk8OptionalBeanPropertyWriter extends BeanPropertyWriter {
    private static final long serialVersionUID = 1;
    protected final Object _empty;

    /* JADX INFO: Access modifiers changed from: protected */
    public Jdk8OptionalBeanPropertyWriter(BeanPropertyWriter beanPropertyWriter, Object obj) {
        super(beanPropertyWriter);
        this._empty = obj;
    }

    protected Jdk8OptionalBeanPropertyWriter(Jdk8OptionalBeanPropertyWriter jdk8OptionalBeanPropertyWriter, PropertyName propertyName) {
        super(jdk8OptionalBeanPropertyWriter, propertyName);
        this._empty = jdk8OptionalBeanPropertyWriter._empty;
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter
    protected BeanPropertyWriter _new(PropertyName propertyName) {
        return new Jdk8OptionalBeanPropertyWriter(this, propertyName);
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter
    public BeanPropertyWriter unwrappingWriter(NameTransformer nameTransformer) {
        return new Jdk8UnwrappingOptionalBeanPropertyWriter(this, nameTransformer, this._empty);
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter, com.fasterxml.jackson.databind.ser.PropertyWriter
    public void serializeAsField(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        Object obj2;
        if (this._nullSerializer != null || ((obj2 = get(obj)) != null && !obj2.equals(this._empty))) {
            super.serializeAsField(obj, jsonGenerator, serializerProvider);
        }
    }
}
