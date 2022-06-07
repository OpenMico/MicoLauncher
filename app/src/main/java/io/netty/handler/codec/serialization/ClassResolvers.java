package io.netty.handler.codec.serialization;

import io.netty.util.internal.PlatformDependent;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class ClassResolvers {
    public static ClassResolver cacheDisabled(ClassLoader classLoader) {
        return new b(a(classLoader));
    }

    public static ClassResolver weakCachingResolver(ClassLoader classLoader) {
        return new a(new b(a(classLoader)), new g(new HashMap()));
    }

    public static ClassResolver softCachingResolver(ClassLoader classLoader) {
        return new a(new b(a(classLoader)), new f(new HashMap()));
    }

    public static ClassResolver weakCachingConcurrentResolver(ClassLoader classLoader) {
        return new a(new b(a(classLoader)), new g(PlatformDependent.newConcurrentHashMap()));
    }

    public static ClassResolver softCachingConcurrentResolver(ClassLoader classLoader) {
        return new a(new b(a(classLoader)), new f(PlatformDependent.newConcurrentHashMap()));
    }

    static ClassLoader a(ClassLoader classLoader) {
        if (classLoader != null) {
            return classLoader;
        }
        ClassLoader contextClassLoader = PlatformDependent.getContextClassLoader();
        return contextClassLoader != null ? contextClassLoader : PlatformDependent.getClassLoader(ClassResolvers.class);
    }

    private ClassResolvers() {
    }
}
