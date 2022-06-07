package com.xiaomi.ext;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ReferenceTypeSerializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.xiaomi.common.Optional;

/* loaded from: classes3.dex */
public class OptionalSerializer extends ReferenceTypeSerializer<Optional<?>> {
    private static final long serialVersionUID = 1;

    /* JADX INFO: Access modifiers changed from: protected */
    public OptionalSerializer(ReferenceType referenceType, boolean z, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        super(referenceType, z, typeSerializer, jsonSerializer);
    }

    protected OptionalSerializer(OptionalSerializer optionalSerializer, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, NameTransformer nameTransformer, Object obj, boolean z) {
        super(optionalSerializer, beanProperty, typeSerializer, jsonSerializer, nameTransformer, obj, z);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.ReferenceTypeSerializer
    protected ReferenceTypeSerializer<Optional<?>> withResolved(BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, NameTransformer nameTransformer) {
        return new OptionalSerializer(this, beanProperty, typeSerializer, jsonSerializer, nameTransformer, this._suppressableValue, this._suppressNulls);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.ReferenceTypeSerializer
    public ReferenceTypeSerializer<Optional<?>> withContentInclusion(Object obj, boolean z) {
        return new OptionalSerializer(this, this._property, this._valueTypeSerializer, this._valueSerializer, this._unwrapper, obj, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean _isValuePresent(Optional<?> optional) {
        return optional.isPresent();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object _getReferenced(Optional<?> optional) {
        return optional.get();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object _getReferencedIfPresent(Optional<?> optional) {
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
