package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/* loaded from: classes.dex */
public class PropertyBasedObjectIdGenerator extends ObjectIdGenerators.PropertyGenerator {
    private static final long serialVersionUID = 1;

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator<Object> newForSerialization(Object obj) {
        return this;
    }

    public PropertyBasedObjectIdGenerator(Class<?> cls) {
        super(cls);
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.a, com.fasterxml.jackson.annotation.ObjectIdGenerator
    public Object generateId(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator<Object> forScope(Class<?> cls) {
        return cls == this._scope ? this : new PropertyBasedObjectIdGenerator(cls);
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator.IdKey key(Object obj) {
        if (obj == null) {
            return null;
        }
        return new ObjectIdGenerator.IdKey(getClass(), this._scope, obj);
    }
}
