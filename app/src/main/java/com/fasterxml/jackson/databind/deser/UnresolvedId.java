package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.util.ClassUtil;

/* loaded from: classes.dex */
public class UnresolvedId {
    private final Object a;
    private final JsonLocation b;
    private final Class<?> c;

    public UnresolvedId(Object obj, Class<?> cls, JsonLocation jsonLocation) {
        this.a = obj;
        this.c = cls;
        this.b = jsonLocation;
    }

    public Object getId() {
        return this.a;
    }

    public Class<?> getType() {
        return this.c;
    }

    public JsonLocation getLocation() {
        return this.b;
    }

    public String toString() {
        return String.format("Object id [%s] (for %s) at %s", this.a, ClassUtil.nameOf(this.c), this.b);
    }
}
