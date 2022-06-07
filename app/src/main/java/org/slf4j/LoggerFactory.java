package org.slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.helpers.NOPLoggerFactory;
import org.slf4j.helpers.SubstituteLogger;
import org.slf4j.helpers.SubstituteLoggerFactory;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticLoggerBinder;

/* loaded from: classes4.dex */
public final class LoggerFactory {
    static int a;
    static SubstituteLoggerFactory b = new SubstituteLoggerFactory();
    static NOPLoggerFactory c = new NOPLoggerFactory();
    private static final String[] d = {"1.6", "1.7"};
    private static String e = "org/slf4j/impl/StaticLoggerBinder.class";

    private LoggerFactory() {
    }

    private static final void a() {
        b();
        if (a == 3) {
            d();
        }
    }

    private static boolean a(String str) {
        if (str == null) {
            return false;
        }
        return (str.indexOf("org/slf4j/impl/StaticLoggerBinder") == -1 && str.indexOf("org.slf4j.impl.StaticLoggerBinder") == -1) ? false : true;
    }

    private static final void b() {
        try {
            Set<URL> e2 = e();
            b(e2);
            StaticLoggerBinder.getSingleton();
            a = 3;
            c(e2);
            c();
        } catch (Exception e3) {
            a(e3);
            throw new IllegalStateException("Unexpected initialization failure", e3);
        } catch (NoClassDefFoundError e4) {
            if (a(e4.getMessage())) {
                a = 4;
                Util.report("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".");
                Util.report("Defaulting to no-operation (NOP) logger implementation");
                Util.report("See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.");
                return;
            }
            a(e4);
            throw e4;
        } catch (NoSuchMethodError e5) {
            String message = e5.getMessage();
            if (!(message == null || message.indexOf("org.slf4j.impl.StaticLoggerBinder.getSingleton()") == -1)) {
                a = 2;
                Util.report("slf4j-api 1.6.x (or later) is incompatible with this binding.");
                Util.report("Your binding is version 1.5.5 or earlier.");
                Util.report("Upgrade your binding to version 1.6.x.");
            }
            throw e5;
        }
    }

    static void a(Throwable th) {
        a = 2;
        Util.report("Failed to instantiate SLF4J LoggerFactory", th);
    }

    private static final void c() {
        List<SubstituteLogger> loggers = b.getLoggers();
        if (!loggers.isEmpty()) {
            Util.report("The following set of substitute loggers may have been accessed");
            Util.report("during the initialization phase. Logging calls during this");
            Util.report("phase were not honored. However, subsequent logging calls to these");
            Util.report("loggers will work as normally expected.");
            Util.report("See also http://www.slf4j.org/codes.html#substituteLogger");
            for (SubstituteLogger substituteLogger : loggers) {
                substituteLogger.setDelegate(getLogger(substituteLogger.getName()));
                Util.report(substituteLogger.getName());
            }
            b.clear();
        }
    }

    private static final void d() {
        try {
            String str = StaticLoggerBinder.REQUESTED_API_VERSION;
            boolean z = false;
            for (int i = 0; i < d.length; i++) {
                if (str.startsWith(d[i])) {
                    z = true;
                }
            }
            if (!z) {
                Util.report("The requested version " + str + " by your slf4j binding is not compatible with " + Arrays.asList(d).toString());
                Util.report("See http://www.slf4j.org/codes.html#version_mismatch for further details.");
            }
        } catch (NoSuchFieldError unused) {
        } catch (Throwable th) {
            Util.report("Unexpected problem occured during version sanity check", th);
        }
    }

    private static Set<URL> e() {
        Enumeration<URL> enumeration;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        try {
            ClassLoader classLoader = LoggerFactory.class.getClassLoader();
            if (classLoader == null) {
                enumeration = ClassLoader.getSystemResources(e);
            } else {
                enumeration = classLoader.getResources(e);
            }
            while (enumeration.hasMoreElements()) {
                linkedHashSet.add(enumeration.nextElement());
            }
        } catch (IOException e2) {
            Util.report("Error getting resources from path", e2);
        }
        return linkedHashSet;
    }

    private static boolean a(Set<URL> set) {
        return set.size() > 1;
    }

    private static void b(Set<URL> set) {
        if (a(set)) {
            Util.report("Class path contains multiple SLF4J bindings.");
            Iterator<URL> it = set.iterator();
            while (it.hasNext()) {
                Util.report("Found binding in [" + it.next() + "]");
            }
            Util.report("See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.");
        }
    }

    private static void c(Set<URL> set) {
        if (a(set)) {
            Util.report("Actual binding is of type [" + StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr() + "]");
        }
    }

    public static Logger getLogger(String str) {
        return getILoggerFactory().getLogger(str);
    }

    public static Logger getLogger(Class cls) {
        return getLogger(cls.getName());
    }

    public static ILoggerFactory getILoggerFactory() {
        if (a == 0) {
            a = 1;
            a();
        }
        switch (a) {
            case 1:
                return b;
            case 2:
                throw new IllegalStateException("org.slf4j.LoggerFactory could not be successfully initialized. See also http://www.slf4j.org/codes.html#unsuccessfulInit");
            case 3:
                return StaticLoggerBinder.getSingleton().getLoggerFactory();
            case 4:
                return c;
            default:
                throw new IllegalStateException("Unreachable code");
        }
    }
}
