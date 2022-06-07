package io.netty.handler.codec.serialization;

import java.util.Map;

/* compiled from: CachingClassResolver.java */
/* loaded from: classes4.dex */
class a implements ClassResolver {
    private final Map<String, Class<?>> a;
    private final ClassResolver b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(ClassResolver classResolver, Map<String, Class<?>> map) {
        this.b = classResolver;
        this.a = map;
    }

    @Override // io.netty.handler.codec.serialization.ClassResolver
    public Class<?> resolve(String str) throws ClassNotFoundException {
        Class<?> cls = this.a.get(str);
        if (cls != null) {
            return cls;
        }
        Class<?> resolve = this.b.resolve(str);
        this.a.put(str, resolve);
        return resolve;
    }
}
