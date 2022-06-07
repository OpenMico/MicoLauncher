package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.Serializable;

/* loaded from: classes.dex */
public class NullsConstantProvider implements NullValueProvider, Serializable {
    private static final NullsConstantProvider a = new NullsConstantProvider(null);
    private static final NullsConstantProvider b = new NullsConstantProvider(null);
    private static final long serialVersionUID = 1;
    protected final AccessPattern _access;
    protected final Object _nullValue;

    protected NullsConstantProvider(Object obj) {
        this._nullValue = obj;
        this._access = this._nullValue == null ? AccessPattern.ALWAYS_NULL : AccessPattern.CONSTANT;
    }

    public static NullsConstantProvider skipper() {
        return a;
    }

    public static NullsConstantProvider nuller() {
        return b;
    }

    public static NullsConstantProvider forValue(Object obj) {
        if (obj == null) {
            return b;
        }
        return new NullsConstantProvider(obj);
    }

    public static boolean isSkipper(NullValueProvider nullValueProvider) {
        return nullValueProvider == a;
    }

    public static boolean isNuller(NullValueProvider nullValueProvider) {
        return nullValueProvider == b;
    }

    @Override // com.fasterxml.jackson.databind.deser.NullValueProvider
    public AccessPattern getNullAccessPattern() {
        return this._access;
    }

    @Override // com.fasterxml.jackson.databind.deser.NullValueProvider
    public Object getNullValue(DeserializationContext deserializationContext) {
        return this._nullValue;
    }
}
