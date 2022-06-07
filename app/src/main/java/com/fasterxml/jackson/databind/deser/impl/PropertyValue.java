package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class PropertyValue {
    public final PropertyValue next;
    public final Object value;

    public abstract void assign(Object obj) throws IOException, JsonProcessingException;

    protected PropertyValue(PropertyValue propertyValue, Object obj) {
        this.next = propertyValue;
        this.value = obj;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class c extends PropertyValue {
        final SettableBeanProperty a;

        public c(PropertyValue propertyValue, Object obj, SettableBeanProperty settableBeanProperty) {
            super(propertyValue, obj);
            this.a = settableBeanProperty;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.PropertyValue
        public void assign(Object obj) throws IOException, JsonProcessingException {
            this.a.set(obj, this.value);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class a extends PropertyValue {
        final SettableAnyProperty a;
        final String b;

        public a(PropertyValue propertyValue, Object obj, SettableAnyProperty settableAnyProperty, String str) {
            super(propertyValue, obj);
            this.a = settableAnyProperty;
            this.b = str;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.PropertyValue
        public void assign(Object obj) throws IOException, JsonProcessingException {
            this.a.set(obj, this.b, this.value);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class b extends PropertyValue {
        final Object a;

        public b(PropertyValue propertyValue, Object obj, Object obj2) {
            super(propertyValue, obj);
            this.a = obj2;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.PropertyValue
        public void assign(Object obj) throws IOException, JsonProcessingException {
            ((Map) obj).put(this.a, this.value);
        }
    }
}
