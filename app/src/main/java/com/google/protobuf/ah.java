package com.google.protobuf;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: Protobuf.java */
/* loaded from: classes2.dex */
public final class ah {
    private static final ah a = new ah();
    private final ConcurrentMap<Class<?>, am<?>> c = new ConcurrentHashMap();
    private final an b = new s();

    public static ah a() {
        return a;
    }

    public <T> void a(T t, ak akVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        a((ah) t).a(t, akVar, extensionRegistryLite);
    }

    public <T> am<T> a(Class<T> cls) {
        Internal.a(cls, "messageType");
        am<T> amVar = (am<T>) this.c.get(cls);
        if (amVar != null) {
            return amVar;
        }
        am<T> a2 = this.b.a(cls);
        am<T> amVar2 = (am<T>) a(cls, a2);
        return amVar2 != null ? amVar2 : a2;
    }

    public <T> am<T> a(T t) {
        return a((Class) t.getClass());
    }

    public am<?> a(Class<?> cls, am<?> amVar) {
        Internal.a(cls, "messageType");
        Internal.a(amVar, "schema");
        return this.c.putIfAbsent(cls, amVar);
    }

    private ah() {
    }
}
