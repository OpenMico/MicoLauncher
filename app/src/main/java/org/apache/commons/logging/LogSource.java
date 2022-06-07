package org.apache.commons.logging;

import java.lang.reflect.Constructor;
import java.util.Hashtable;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.apache.commons.logging.impl.NoOpLog;

/* loaded from: classes5.dex */
public class LogSource {
    protected static boolean jdk14IsAvailable;
    protected static boolean log4jIsAvailable;
    protected static Hashtable logs = new Hashtable();
    protected static Constructor logImplctor = null;

    static {
        log4jIsAvailable = false;
        jdk14IsAvailable = false;
        String str = null;
        boolean z = true;
        try {
            log4jIsAvailable = Class.forName("org.apache.log4j.Logger") != null;
        } catch (Throwable unused) {
            log4jIsAvailable = false;
        }
        try {
            if (Class.forName("java.util.logging.Logger") == null || Class.forName("org.apache.commons.logging.impl.Jdk14Logger") == null) {
                z = false;
            }
            jdk14IsAvailable = z;
        } catch (Throwable unused2) {
            jdk14IsAvailable = false;
        }
        try {
            str = System.getProperty("org.apache.commons.logging.log");
            if (str == null) {
                str = System.getProperty(LogFactoryImpl.LOG_PROPERTY);
            }
        } catch (Throwable unused3) {
        }
        try {
            if (str != null) {
                setLogImplementation(str);
            } else if (log4jIsAvailable) {
                setLogImplementation("org.apache.commons.logging.impl.Log4JLogger");
            } else if (jdk14IsAvailable) {
                setLogImplementation("org.apache.commons.logging.impl.Jdk14Logger");
            } else {
                setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
            }
        } catch (Throwable unused4) {
        }
    }

    private LogSource() {
    }

    public static void setLogImplementation(String str) throws LinkageError, NoSuchMethodException, SecurityException, ClassNotFoundException {
        try {
            logImplctor = Class.forName(str).getConstructor("".getClass());
        } catch (Throwable unused) {
            logImplctor = null;
        }
    }

    public static void setLogImplementation(Class cls) throws LinkageError, ExceptionInInitializerError, NoSuchMethodException, SecurityException {
        logImplctor = cls.getConstructor("".getClass());
    }

    public static Log getInstance(String str) {
        Log log = (Log) logs.get(str);
        if (log != null) {
            return log;
        }
        Log makeNewLogInstance = makeNewLogInstance(str);
        logs.put(str, makeNewLogInstance);
        return makeNewLogInstance;
    }

    public static Log getInstance(Class cls) {
        return getInstance(cls.getName());
    }

    public static Log makeNewLogInstance(String str) {
        Log log;
        try {
            log = (Log) logImplctor.newInstance(str);
        } catch (Throwable unused) {
            log = null;
        }
        return log == null ? new NoOpLog(str) : log;
    }

    public static String[] getLogNames() {
        return (String[]) logs.keySet().toArray(new String[logs.size()]);
    }
}
