package com.xiaomi.ext;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.ReferenceTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.xiaomi.common.Optional;

/* compiled from: OptionalDeserializer.java */
/* loaded from: classes3.dex */
final class a extends ReferenceTypeDeserializer<Optional<?>> {
    private static final long serialVersionUID = 1;

    public a(JavaType javaType, ValueInstantiator valueInstantiator, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) {
        super(javaType, valueInstantiator, typeDeserializer, jsonDeserializer);
    }

    /* renamed from: a */
    public a withResolved(TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) {
        return new a(this._fullType, this._valueInstantiator, typeDeserializer, jsonDeserializer);
    }

    /* renamed from: a */
    public Optional<?> getNullValue(DeserializationContext deserializationContext) {
        return Optional.empty();
    }

    /* renamed from: a */
    public Optional<?> referenceValue(Object obj) {
        return Optional.ofNullable(obj);
    }

    /* renamed from: a */
    public Object getReferenced(Optional<?> optional) {
        return optional.get();
    }

    /* renamed from: a */
    public Optional<?> updateReference(Optional<?> optional, Object obj) {
        return Optional.ofNullable(obj);
    }
}
