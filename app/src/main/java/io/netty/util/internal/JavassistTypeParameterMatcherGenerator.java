package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;
import javassist.ClassClassPath;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import okhttp3.HttpUrl;

/* loaded from: classes4.dex */
public final class JavassistTypeParameterMatcherGenerator {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(JavassistTypeParameterMatcherGenerator.class);
    private static final ClassPool b = new ClassPool(true);

    static {
        b.appendClassPath(new ClassClassPath(NoOpTypeParameterMatcher.class));
    }

    public static void appendClassPath(ClassPath classPath) {
        b.appendClassPath(classPath);
    }

    public static void appendClassPath(String str) throws NotFoundException {
        b.appendClassPath(str);
    }

    public static ClassPool classPool() {
        return b;
    }

    public static TypeParameterMatcher generate(Class<?> cls) {
        ClassLoader contextClassLoader = PlatformDependent.getContextClassLoader();
        if (contextClassLoader == null) {
            contextClassLoader = PlatformDependent.getSystemClassLoader();
        }
        return generate(cls, contextClassLoader);
    }

    public static TypeParameterMatcher generate(Class<?> cls, ClassLoader classLoader) {
        try {
            String a2 = a(cls);
            String str = "io.netty.util.internal.__matchers__." + a2 + "Matcher";
            try {
                try {
                    return (TypeParameterMatcher) Class.forName(str, true, classLoader).newInstance();
                } catch (Exception unused) {
                    CtClass andRename = b.getAndRename(NoOpTypeParameterMatcher.class.getName(), str);
                    andRename.setModifiers(andRename.getModifiers() | 16);
                    andRename.getDeclaredMethod("match").setBody("{ return $1 instanceof " + a2 + "; }");
                    byte[] bytecode = andRename.toBytecode();
                    andRename.detach();
                    Method declaredMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                    declaredMethod.setAccessible(true);
                    Class cls2 = (Class) declaredMethod.invoke(classLoader, str, bytecode, 0, Integer.valueOf(bytecode.length));
                    if (cls != Object.class) {
                        a.debug("Generated: {}", cls2.getName());
                    }
                    return (TypeParameterMatcher) cls2.newInstance();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e2) {
            throw e2;
        }
    }

    private static String a(Class<?> cls) {
        if (!cls.isArray()) {
            return cls.getName();
        }
        return a(cls.getComponentType()) + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
    }

    private JavassistTypeParameterMatcherGenerator() {
    }
}
