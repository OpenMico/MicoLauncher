package org.eclipse.jetty.util.log;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.eclipse.jetty.util.Loader;

/* loaded from: classes5.dex */
public class Log {
    public static final String EXCEPTION = "EXCEPTION ";
    public static final String IGNORED = "IGNORED ";
    private static Logger LOG;
    public static boolean __ignored;
    private static boolean __initialized;
    public static String __logClass;
    private static final ConcurrentMap<String, Logger> __loggers = new ConcurrentHashMap();
    protected static Properties __props = new Properties();

    static {
        AccessController.doPrivileged(new PrivilegedAction<Object>() { // from class: org.eclipse.jetty.util.log.Log.1
            /* JADX WARN: Removed duplicated region for block: B:19:0x0053  */
            @Override // java.security.PrivilegedAction
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public java.lang.Object run() {
                /*
                    r7 = this;
                    java.lang.Class<org.eclipse.jetty.util.log.Log> r0 = org.eclipse.jetty.util.log.Log.class
                    java.lang.ClassLoader r0 = r0.getClassLoader()
                    java.lang.String r1 = "jetty-logging.properties"
                    java.net.URL r0 = r0.getResource(r1)
                    r1 = 0
                    if (r0 == 0) goto L_0x0045
                    java.io.InputStream r2 = r0.openStream()     // Catch: IOException -> 0x0023, all -> 0x0020
                    java.util.Properties r3 = org.eclipse.jetty.util.log.Log.__props     // Catch: IOException -> 0x001e, all -> 0x001c
                    r3.load(r2)     // Catch: IOException -> 0x001e, all -> 0x001c
                L_0x0018:
                    org.eclipse.jetty.util.IO.close(r2)
                    goto L_0x0045
                L_0x001c:
                    r0 = move-exception
                    goto L_0x0041
                L_0x001e:
                    r3 = move-exception
                    goto L_0x0025
                L_0x0020:
                    r0 = move-exception
                    r2 = r1
                    goto L_0x0041
                L_0x0023:
                    r3 = move-exception
                    r2 = r1
                L_0x0025:
                    java.io.PrintStream r4 = java.lang.System.err     // Catch: all -> 0x001c
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: all -> 0x001c
                    r5.<init>()     // Catch: all -> 0x001c
                    java.lang.String r6 = "Unable to load "
                    r5.append(r6)     // Catch: all -> 0x001c
                    r5.append(r0)     // Catch: all -> 0x001c
                    java.lang.String r0 = r5.toString()     // Catch: all -> 0x001c
                    r4.println(r0)     // Catch: all -> 0x001c
                    java.io.PrintStream r0 = java.lang.System.err     // Catch: all -> 0x001c
                    r3.printStackTrace(r0)     // Catch: all -> 0x001c
                    goto L_0x0018
                L_0x0041:
                    org.eclipse.jetty.util.IO.close(r2)
                    throw r0
                L_0x0045:
                    java.util.Properties r0 = java.lang.System.getProperties()
                    java.util.Enumeration r0 = r0.propertyNames()
                L_0x004d:
                    boolean r2 = r0.hasMoreElements()
                    if (r2 == 0) goto L_0x0065
                    java.lang.Object r2 = r0.nextElement()
                    java.lang.String r2 = (java.lang.String) r2
                    java.lang.String r3 = java.lang.System.getProperty(r2)
                    if (r3 == 0) goto L_0x004d
                    java.util.Properties r4 = org.eclipse.jetty.util.log.Log.__props
                    r4.setProperty(r2, r3)
                    goto L_0x004d
                L_0x0065:
                    java.util.Properties r0 = org.eclipse.jetty.util.log.Log.__props
                    java.lang.String r2 = "org.eclipse.jetty.util.log.class"
                    java.lang.String r3 = "org.eclipse.jetty.util.log.Slf4jLog"
                    java.lang.String r0 = r0.getProperty(r2, r3)
                    org.eclipse.jetty.util.log.Log.__logClass = r0
                    java.util.Properties r0 = org.eclipse.jetty.util.log.Log.__props
                    java.lang.String r2 = "org.eclipse.jetty.util.log.IGNORED"
                    java.lang.String r3 = "false"
                    java.lang.String r0 = r0.getProperty(r2, r3)
                    boolean r0 = java.lang.Boolean.parseBoolean(r0)
                    org.eclipse.jetty.util.log.Log.__ignored = r0
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.log.Log.AnonymousClass1.run():java.lang.Object");
            }
        });
    }

    public static boolean initialized() {
        boolean z = true;
        if (LOG != null) {
            return true;
        }
        synchronized (Log.class) {
            if (__initialized) {
                if (LOG == null) {
                    z = false;
                }
                return z;
            }
            __initialized = true;
            try {
                Class loadClass = Loader.loadClass(Log.class, __logClass);
                if (LOG == null || !LOG.getClass().equals(loadClass)) {
                    LOG = (Logger) loadClass.newInstance();
                    LOG.debug("Logging to {} via {}", LOG, loadClass.getName());
                }
            } catch (Throwable th) {
                initStandardLogging(th);
            }
            return LOG != null;
        }
    }

    private static void initStandardLogging(Throwable th) {
        if (th != null && __ignored) {
            th.printStackTrace();
        }
        if (LOG == null) {
            LOG = new StdErrLog();
            Logger logger = LOG;
            logger.debug("Logging to {} via {}", logger, StdErrLog.class.getName());
        }
    }

    public static void setLog(Logger logger) {
        LOG = logger;
    }

    @Deprecated
    public static Logger getLog() {
        initialized();
        return LOG;
    }

    public static Logger getRootLogger() {
        initialized();
        return LOG;
    }

    public static boolean isIgnored() {
        return __ignored;
    }

    public static void setLogToParent(String str) {
        ClassLoader classLoader = Log.class.getClassLoader();
        if (classLoader.getParent() != null) {
            try {
                setLog(new LoggerLog(classLoader.getParent().loadClass("org.eclipse.jetty.util.log.Log").getMethod("getLogger", String.class).invoke(null, str)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setLog(getLogger(str));
        }
    }

    @Deprecated
    public static void debug(Throwable th) {
        if (isDebugEnabled()) {
            LOG.debug(EXCEPTION, th);
        }
    }

    @Deprecated
    public static void debug(String str) {
        if (initialized()) {
            LOG.debug(str, new Object[0]);
        }
    }

    @Deprecated
    public static void debug(String str, Object obj) {
        if (initialized()) {
            LOG.debug(str, obj);
        }
    }

    @Deprecated
    public static void debug(String str, Object obj, Object obj2) {
        if (initialized()) {
            LOG.debug(str, obj, obj2);
        }
    }

    @Deprecated
    public static void ignore(Throwable th) {
        if (initialized()) {
            LOG.ignore(th);
        }
    }

    @Deprecated
    public static void info(String str) {
        if (initialized()) {
            LOG.info(str, new Object[0]);
        }
    }

    @Deprecated
    public static void info(String str, Object obj) {
        if (initialized()) {
            LOG.info(str, obj);
        }
    }

    @Deprecated
    public static void info(String str, Object obj, Object obj2) {
        if (initialized()) {
            LOG.info(str, obj, obj2);
        }
    }

    @Deprecated
    public static boolean isDebugEnabled() {
        if (!initialized()) {
            return false;
        }
        return LOG.isDebugEnabled();
    }

    @Deprecated
    public static void warn(String str) {
        if (initialized()) {
            LOG.warn(str, new Object[0]);
        }
    }

    @Deprecated
    public static void warn(String str, Object obj) {
        if (initialized()) {
            LOG.warn(str, obj);
        }
    }

    @Deprecated
    public static void warn(String str, Object obj, Object obj2) {
        if (initialized()) {
            LOG.warn(str, obj, obj2);
        }
    }

    @Deprecated
    public static void warn(String str, Throwable th) {
        if (initialized()) {
            LOG.warn(str, th);
        }
    }

    @Deprecated
    public static void warn(Throwable th) {
        if (initialized()) {
            LOG.warn(EXCEPTION, th);
        }
    }

    public static Logger getLogger(Class<?> cls) {
        return getLogger(cls.getName());
    }

    public static Logger getLogger(String str) {
        if (!initialized()) {
            return null;
        }
        if (str == null) {
            return LOG;
        }
        Logger logger = __loggers.get(str);
        return logger == null ? LOG.getLogger(str) : logger;
    }

    public static ConcurrentMap<String, Logger> getMutableLoggers() {
        return __loggers;
    }

    public static Map<String, Logger> getLoggers() {
        return Collections.unmodifiableMap(__loggers);
    }
}
