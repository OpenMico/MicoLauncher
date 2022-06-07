package org.apache.commons.logging.impl;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.eclipse.jetty.http.HttpMethods;

/* loaded from: classes5.dex */
public class Log4JLogger implements Serializable, Log {
    static Class a = null;
    static Class b = null;
    static Class c = null;
    private static final String d;
    private static final Priority f;
    private static final long serialVersionUID = 5160705895411730424L;
    private volatile transient Logger e;
    private final String name;

    static {
        Priority priority;
        Class cls;
        Class cls2 = a;
        if (cls2 == null) {
            cls2 = a("org.apache.commons.logging.impl.Log4JLogger");
            a = cls2;
        }
        d = cls2.getName();
        Class cls3 = c;
        if (cls3 == null) {
            cls3 = a("org.apache.log4j.Priority");
            c = cls3;
        }
        Class<?> cls4 = b;
        if (cls4 == null) {
            cls4 = a("org.apache.log4j.Level");
            b = cls4;
        }
        if (cls3.isAssignableFrom(cls4)) {
            try {
                if (b == null) {
                    cls = a("org.apache.log4j.Level");
                    b = cls;
                } else {
                    cls = b;
                }
                priority = (Priority) cls.getDeclaredField(HttpMethods.TRACE).get(null);
            } catch (Exception unused) {
                priority = Level.DEBUG;
            }
            f = priority;
            return;
        }
        throw new InstantiationError("Log4J 1.2 not available");
    }

    static Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public Log4JLogger() {
        this.e = null;
        this.name = null;
    }

    public Log4JLogger(String str) {
        this.e = null;
        this.name = str;
        this.e = getLogger();
    }

    public Log4JLogger(Logger logger) {
        this.e = null;
        if (logger != null) {
            this.name = logger.getName();
            this.e = logger;
            return;
        }
        throw new IllegalArgumentException("Warning - null logger in constructor; possible log4j misconfiguration.");
    }

    @Override // org.apache.commons.logging.Log
    public void trace(Object obj) {
        getLogger().log(d, f, obj, (Throwable) null);
    }

    @Override // org.apache.commons.logging.Log
    public void trace(Object obj, Throwable th) {
        getLogger().log(d, f, obj, th);
    }

    @Override // org.apache.commons.logging.Log
    public void debug(Object obj) {
        getLogger().log(d, Level.DEBUG, obj, (Throwable) null);
    }

    @Override // org.apache.commons.logging.Log
    public void debug(Object obj, Throwable th) {
        getLogger().log(d, Level.DEBUG, obj, th);
    }

    @Override // org.apache.commons.logging.Log
    public void info(Object obj) {
        getLogger().log(d, Level.INFO, obj, (Throwable) null);
    }

    @Override // org.apache.commons.logging.Log
    public void info(Object obj, Throwable th) {
        getLogger().log(d, Level.INFO, obj, th);
    }

    @Override // org.apache.commons.logging.Log
    public void warn(Object obj) {
        getLogger().log(d, Level.WARN, obj, (Throwable) null);
    }

    @Override // org.apache.commons.logging.Log
    public void warn(Object obj, Throwable th) {
        getLogger().log(d, Level.WARN, obj, th);
    }

    @Override // org.apache.commons.logging.Log
    public void error(Object obj) {
        getLogger().log(d, Level.ERROR, obj, (Throwable) null);
    }

    @Override // org.apache.commons.logging.Log
    public void error(Object obj, Throwable th) {
        getLogger().log(d, Level.ERROR, obj, th);
    }

    @Override // org.apache.commons.logging.Log
    public void fatal(Object obj) {
        getLogger().log(d, Level.FATAL, obj, (Throwable) null);
    }

    @Override // org.apache.commons.logging.Log
    public void fatal(Object obj, Throwable th) {
        getLogger().log(d, Level.FATAL, obj, th);
    }

    public Logger getLogger() {
        Logger logger = this.e;
        if (logger == null) {
            synchronized (this) {
                logger = this.e;
                if (logger == null) {
                    logger = Logger.getLogger(this.name);
                    this.e = logger;
                }
            }
        }
        return logger;
    }

    @Override // org.apache.commons.logging.Log
    public boolean isDebugEnabled() {
        return getLogger().isDebugEnabled();
    }

    @Override // org.apache.commons.logging.Log
    public boolean isErrorEnabled() {
        return getLogger().isEnabledFor(Level.ERROR);
    }

    @Override // org.apache.commons.logging.Log
    public boolean isFatalEnabled() {
        return getLogger().isEnabledFor(Level.FATAL);
    }

    @Override // org.apache.commons.logging.Log
    public boolean isInfoEnabled() {
        return getLogger().isInfoEnabled();
    }

    @Override // org.apache.commons.logging.Log
    public boolean isTraceEnabled() {
        return getLogger().isEnabledFor(f);
    }

    @Override // org.apache.commons.logging.Log
    public boolean isWarnEnabled() {
        return getLogger().isEnabledFor(Level.WARN);
    }
}
