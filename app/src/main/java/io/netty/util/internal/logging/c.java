package io.netty.util.internal.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* compiled from: JdkLogger.java */
/* loaded from: classes4.dex */
class c extends AbstractInternalLogger {
    static final String b = "io.netty.util.internal.logging.c";
    static final String c = AbstractInternalLogger.class.getName();
    private static final long serialVersionUID = -1767272577989225979L;
    final transient Logger a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(Logger logger) {
        super(logger.getName());
        this.a = logger;
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isTraceEnabled() {
        return this.a.isLoggable(Level.FINEST);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str) {
        if (this.a.isLoggable(Level.FINEST)) {
            a(b, Level.FINEST, str, null);
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Object obj) {
        if (this.a.isLoggable(Level.FINEST)) {
            b a = f.a(str, obj);
            a(b, Level.FINEST, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Object obj, Object obj2) {
        if (this.a.isLoggable(Level.FINEST)) {
            b a = f.a(str, obj, obj2);
            a(b, Level.FINEST, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Object... objArr) {
        if (this.a.isLoggable(Level.FINEST)) {
            b a = f.a(str, objArr);
            a(b, Level.FINEST, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Throwable th) {
        if (this.a.isLoggable(Level.FINEST)) {
            a(b, Level.FINEST, str, th);
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isDebugEnabled() {
        return this.a.isLoggable(Level.FINE);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str) {
        if (this.a.isLoggable(Level.FINE)) {
            a(b, Level.FINE, str, null);
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Object obj) {
        if (this.a.isLoggable(Level.FINE)) {
            b a = f.a(str, obj);
            a(b, Level.FINE, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Object obj, Object obj2) {
        if (this.a.isLoggable(Level.FINE)) {
            b a = f.a(str, obj, obj2);
            a(b, Level.FINE, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Object... objArr) {
        if (this.a.isLoggable(Level.FINE)) {
            b a = f.a(str, objArr);
            a(b, Level.FINE, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Throwable th) {
        if (this.a.isLoggable(Level.FINE)) {
            a(b, Level.FINE, str, th);
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isInfoEnabled() {
        return this.a.isLoggable(Level.INFO);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str) {
        if (this.a.isLoggable(Level.INFO)) {
            a(b, Level.INFO, str, null);
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Object obj) {
        if (this.a.isLoggable(Level.INFO)) {
            b a = f.a(str, obj);
            a(b, Level.INFO, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Object obj, Object obj2) {
        if (this.a.isLoggable(Level.INFO)) {
            b a = f.a(str, obj, obj2);
            a(b, Level.INFO, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Object... objArr) {
        if (this.a.isLoggable(Level.INFO)) {
            b a = f.a(str, objArr);
            a(b, Level.INFO, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Throwable th) {
        if (this.a.isLoggable(Level.INFO)) {
            a(b, Level.INFO, str, th);
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isWarnEnabled() {
        return this.a.isLoggable(Level.WARNING);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str) {
        if (this.a.isLoggable(Level.WARNING)) {
            a(b, Level.WARNING, str, null);
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Object obj) {
        if (this.a.isLoggable(Level.WARNING)) {
            b a = f.a(str, obj);
            a(b, Level.WARNING, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Object obj, Object obj2) {
        if (this.a.isLoggable(Level.WARNING)) {
            b a = f.a(str, obj, obj2);
            a(b, Level.WARNING, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Object... objArr) {
        if (this.a.isLoggable(Level.WARNING)) {
            b a = f.a(str, objArr);
            a(b, Level.WARNING, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Throwable th) {
        if (this.a.isLoggable(Level.WARNING)) {
            a(b, Level.WARNING, str, th);
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isErrorEnabled() {
        return this.a.isLoggable(Level.SEVERE);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str) {
        if (this.a.isLoggable(Level.SEVERE)) {
            a(b, Level.SEVERE, str, null);
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Object obj) {
        if (this.a.isLoggable(Level.SEVERE)) {
            b a = f.a(str, obj);
            a(b, Level.SEVERE, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Object obj, Object obj2) {
        if (this.a.isLoggable(Level.SEVERE)) {
            b a = f.a(str, obj, obj2);
            a(b, Level.SEVERE, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Object... objArr) {
        if (this.a.isLoggable(Level.SEVERE)) {
            b a = f.a(str, objArr);
            a(b, Level.SEVERE, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Throwable th) {
        if (this.a.isLoggable(Level.SEVERE)) {
            a(b, Level.SEVERE, str, th);
        }
    }

    private void a(String str, Level level, String str2, Throwable th) {
        LogRecord logRecord = new LogRecord(level, str2);
        logRecord.setLoggerName(name());
        logRecord.setThrown(th);
        a(str, logRecord);
        this.a.log(logRecord);
    }

    private static void a(String str, LogRecord logRecord) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int i = 0;
        while (true) {
            if (i >= stackTrace.length) {
                i = -1;
                break;
            }
            String className = stackTrace[i].getClassName();
            if (className.equals(str) || className.equals(c)) {
                break;
            }
            i++;
        }
        while (true) {
            i++;
            if (i >= stackTrace.length) {
                i = -1;
                break;
            }
            String className2 = stackTrace[i].getClassName();
            if (!className2.equals(str) && !className2.equals(c)) {
                break;
            }
        }
        if (i != -1) {
            StackTraceElement stackTraceElement = stackTrace[i];
            logRecord.setSourceClassName(stackTraceElement.getClassName());
            logRecord.setSourceMethodName(stackTraceElement.getMethodName());
        }
    }
}
