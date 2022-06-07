package io.netty.handler.codec.serialization;

/* compiled from: ClassLoaderClassResolver.java */
/* loaded from: classes4.dex */
class b implements ClassResolver {
    private final ClassLoader a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(ClassLoader classLoader) {
        this.a = classLoader;
    }

    @Override // io.netty.handler.codec.serialization.ClassResolver
    public Class<?> resolve(String str) throws ClassNotFoundException {
        try {
            return this.a.loadClass(str);
        } catch (ClassNotFoundException unused) {
            return Class.forName(str, false, this.a);
        }
    }
}
