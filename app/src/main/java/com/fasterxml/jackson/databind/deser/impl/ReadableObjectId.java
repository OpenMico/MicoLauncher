package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class ReadableObjectId {
    protected Object _item;
    protected final ObjectIdGenerator.IdKey _key;
    protected LinkedList<Referring> _referringProperties;
    protected ObjectIdResolver _resolver;

    public boolean tryToResolveUnresolved(DeserializationContext deserializationContext) {
        return false;
    }

    public ReadableObjectId(ObjectIdGenerator.IdKey idKey) {
        this._key = idKey;
    }

    public void setResolver(ObjectIdResolver objectIdResolver) {
        this._resolver = objectIdResolver;
    }

    public ObjectIdGenerator.IdKey getKey() {
        return this._key;
    }

    public void appendReferring(Referring referring) {
        if (this._referringProperties == null) {
            this._referringProperties = new LinkedList<>();
        }
        this._referringProperties.add(referring);
    }

    public void bindItem(Object obj) throws IOException {
        this._resolver.bindItem(this._key, obj);
        this._item = obj;
        Object obj2 = this._key.key;
        LinkedList<Referring> linkedList = this._referringProperties;
        if (linkedList != null) {
            Iterator<Referring> it = linkedList.iterator();
            this._referringProperties = null;
            while (it.hasNext()) {
                it.next().handleResolvedForwardReference(obj2, obj);
            }
        }
    }

    public Object resolve() {
        Object resolveId = this._resolver.resolveId(this._key);
        this._item = resolveId;
        return resolveId;
    }

    public boolean hasReferringProperties() {
        LinkedList<Referring> linkedList = this._referringProperties;
        return linkedList != null && !linkedList.isEmpty();
    }

    public Iterator<Referring> referringProperties() {
        LinkedList<Referring> linkedList = this._referringProperties;
        if (linkedList == null) {
            return Collections.emptyList().iterator();
        }
        return linkedList.iterator();
    }

    public ObjectIdResolver getResolver() {
        return this._resolver;
    }

    public String toString() {
        return String.valueOf(this._key);
    }

    /* loaded from: classes.dex */
    public static abstract class Referring {
        private final UnresolvedForwardReference a;
        private final Class<?> b;

        public abstract void handleResolvedForwardReference(Object obj, Object obj2) throws IOException;

        public Referring(UnresolvedForwardReference unresolvedForwardReference, Class<?> cls) {
            this.a = unresolvedForwardReference;
            this.b = cls;
        }

        public Referring(UnresolvedForwardReference unresolvedForwardReference, JavaType javaType) {
            this.a = unresolvedForwardReference;
            this.b = javaType.getRawClass();
        }

        public JsonLocation getLocation() {
            return this.a.getLocation();
        }

        public Class<?> getBeanType() {
            return this.b;
        }

        public boolean hasId(Object obj) {
            return obj.equals(this.a.getUnresolvedId());
        }
    }
}
