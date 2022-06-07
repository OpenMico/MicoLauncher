package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.util.ClassUtil;

/* loaded from: classes.dex */
public class ObjectIdInfo {
    private static final ObjectIdInfo a = new ObjectIdInfo(PropertyName.NO_NAME, Object.class, null, false, null);
    protected final boolean _alwaysAsId;
    protected final Class<? extends ObjectIdGenerator<?>> _generator;
    protected final PropertyName _propertyName;
    protected final Class<? extends ObjectIdResolver> _resolver;
    protected final Class<?> _scope;

    public ObjectIdInfo(PropertyName propertyName, Class<?> cls, Class<? extends ObjectIdGenerator<?>> cls2, Class<? extends ObjectIdResolver> cls3) {
        this(propertyName, cls, cls2, false, cls3);
    }

    protected ObjectIdInfo(PropertyName propertyName, Class<?> cls, Class<? extends ObjectIdGenerator<?>> cls2, boolean z) {
        this(propertyName, cls, cls2, z, SimpleObjectIdResolver.class);
    }

    /* JADX WARN: Incorrect type for immutable var: ssa=java.lang.Class<? extends com.fasterxml.jackson.annotation.ObjectIdResolver>, code=java.lang.Class, for r5v0, types: [java.lang.Class<? extends com.fasterxml.jackson.annotation.ObjectIdResolver>] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected ObjectIdInfo(com.fasterxml.jackson.databind.PropertyName r1, java.lang.Class<?> r2, java.lang.Class<? extends com.fasterxml.jackson.annotation.ObjectIdGenerator<?>> r3, boolean r4, java.lang.Class r5) {
        /*
            r0 = this;
            r0.<init>()
            r0._propertyName = r1
            r0._scope = r2
            r0._generator = r3
            r0._alwaysAsId = r4
            if (r5 != 0) goto L_0x000f
            java.lang.Class<com.fasterxml.jackson.annotation.SimpleObjectIdResolver> r5 = com.fasterxml.jackson.annotation.SimpleObjectIdResolver.class
        L_0x000f:
            r0._resolver = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.introspect.ObjectIdInfo.<init>(com.fasterxml.jackson.databind.PropertyName, java.lang.Class, java.lang.Class, boolean, java.lang.Class):void");
    }

    public static ObjectIdInfo empty() {
        return a;
    }

    public ObjectIdInfo withAlwaysAsId(boolean z) {
        return this._alwaysAsId == z ? this : new ObjectIdInfo(this._propertyName, this._scope, this._generator, z, this._resolver);
    }

    public PropertyName getPropertyName() {
        return this._propertyName;
    }

    public Class<?> getScope() {
        return this._scope;
    }

    public Class<? extends ObjectIdGenerator<?>> getGeneratorType() {
        return this._generator;
    }

    public Class<? extends ObjectIdResolver> getResolverType() {
        return this._resolver;
    }

    public boolean getAlwaysAsId() {
        return this._alwaysAsId;
    }

    public String toString() {
        return "ObjectIdInfo: propName=" + this._propertyName + ", scope=" + ClassUtil.nameOf(this._scope) + ", generatorType=" + ClassUtil.nameOf(this._generator) + ", alwaysAsId=" + this._alwaysAsId;
    }
}
