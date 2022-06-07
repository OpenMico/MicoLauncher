package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;

/* loaded from: classes.dex */
public class AsArrayTypeSerializer extends TypeSerializerBase {
    public AsArrayTypeSerializer(TypeIdResolver typeIdResolver, BeanProperty beanProperty) {
        super(typeIdResolver, beanProperty);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public AsArrayTypeSerializer forProperty(BeanProperty beanProperty) {
        return this._property == beanProperty ? this : new AsArrayTypeSerializer(this._idResolver, beanProperty);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.TypeSerializerBase, com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_ARRAY;
    }
}
