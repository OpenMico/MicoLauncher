package io.netty.util.internal.logging;

import io.netty.util.internal.ThreadLocalRandom;

/* loaded from: classes4.dex */
public abstract class InternalLoggerFactory {
    private static volatile InternalLoggerFactory a = a(InternalLoggerFactory.class.getName());

    protected abstract InternalLogger newInstance(String str);

    static {
        try {
            Class.forName(ThreadLocalRandom.class.getName(), true, InternalLoggerFactory.class.getClassLoader());
        } catch (Exception unused) {
        }
    }

    private static InternalLoggerFactory a(String str) {
        try {
            try {
                Slf4JLoggerFactory slf4JLoggerFactory = new Slf4JLoggerFactory(true);
                slf4JLoggerFactory.newInstance(str).debug("Using SLF4J as the default logging framework");
                return slf4JLoggerFactory;
            } catch (Throwable unused) {
                InternalLoggerFactory internalLoggerFactory = JdkLoggerFactory.INSTANCE;
                internalLoggerFactory.newInstance(str).debug("Using java.util.logging as the default logging framework");
                return internalLoggerFactory;
            }
        } catch (Throwable unused2) {
            InternalLoggerFactory internalLoggerFactory2 = Log4JLoggerFactory.INSTANCE;
            internalLoggerFactory2.newInstance(str).debug("Using Log4J as the default logging framework");
            return internalLoggerFactory2;
        }
    }

    public static InternalLoggerFactory getDefaultFactory() {
        return a;
    }

    public static void setDefaultFactory(InternalLoggerFactory internalLoggerFactory) {
        if (internalLoggerFactory != null) {
            a = internalLoggerFactory;
            return;
        }
        throw new NullPointerException("defaultFactory");
    }

    public static InternalLogger getInstance(Class<?> cls) {
        return getInstance(cls.getName());
    }

    public static InternalLogger getInstance(String str) {
        return getDefaultFactory().newInstance(str);
    }
}
