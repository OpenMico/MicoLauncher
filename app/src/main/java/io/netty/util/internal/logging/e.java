package io.netty.util.internal.logging;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/* compiled from: Log4JLogger.java */
/* loaded from: classes4.dex */
class e extends AbstractInternalLogger {
    static final String b = "io.netty.util.internal.logging.e";
    private static final long serialVersionUID = 2851357342488183058L;
    final transient Logger a;
    final boolean traceCapable = a();

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Logger logger) {
        super(logger.getName());
        this.a = logger;
    }

    private boolean a() {
        try {
            this.a.isTraceEnabled();
            return true;
        } catch (NoSuchMethodError unused) {
            return false;
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isTraceEnabled() {
        if (this.traceCapable) {
            return this.a.isTraceEnabled();
        }
        return this.a.isDebugEnabled();
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str) {
        this.a.log(b, this.traceCapable ? Level.TRACE : Level.DEBUG, str, (Throwable) null);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Object obj) {
        if (isTraceEnabled()) {
            b a = f.a(str, obj);
            this.a.log(b, this.traceCapable ? Level.TRACE : Level.DEBUG, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Object obj, Object obj2) {
        if (isTraceEnabled()) {
            b a = f.a(str, obj, obj2);
            this.a.log(b, this.traceCapable ? Level.TRACE : Level.DEBUG, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Object... objArr) {
        if (isTraceEnabled()) {
            b a = f.a(str, objArr);
            this.a.log(b, this.traceCapable ? Level.TRACE : Level.DEBUG, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Throwable th) {
        this.a.log(b, this.traceCapable ? Level.TRACE : Level.DEBUG, str, th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isDebugEnabled() {
        return this.a.isDebugEnabled();
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str) {
        this.a.log(b, Level.DEBUG, str, (Throwable) null);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Object obj) {
        if (this.a.isDebugEnabled()) {
            b a = f.a(str, obj);
            this.a.log(b, Level.DEBUG, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Object obj, Object obj2) {
        if (this.a.isDebugEnabled()) {
            b a = f.a(str, obj, obj2);
            this.a.log(b, Level.DEBUG, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Object... objArr) {
        if (this.a.isDebugEnabled()) {
            b a = f.a(str, objArr);
            this.a.log(b, Level.DEBUG, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Throwable th) {
        this.a.log(b, Level.DEBUG, str, th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isInfoEnabled() {
        return this.a.isInfoEnabled();
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str) {
        this.a.log(b, Level.INFO, str, (Throwable) null);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Object obj) {
        if (this.a.isInfoEnabled()) {
            b a = f.a(str, obj);
            this.a.log(b, Level.INFO, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Object obj, Object obj2) {
        if (this.a.isInfoEnabled()) {
            b a = f.a(str, obj, obj2);
            this.a.log(b, Level.INFO, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Object... objArr) {
        if (this.a.isInfoEnabled()) {
            b a = f.a(str, objArr);
            this.a.log(b, Level.INFO, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Throwable th) {
        this.a.log(b, Level.INFO, str, th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isWarnEnabled() {
        return this.a.isEnabledFor(Level.WARN);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str) {
        this.a.log(b, Level.WARN, str, (Throwable) null);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Object obj) {
        if (this.a.isEnabledFor(Level.WARN)) {
            b a = f.a(str, obj);
            this.a.log(b, Level.WARN, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Object obj, Object obj2) {
        if (this.a.isEnabledFor(Level.WARN)) {
            b a = f.a(str, obj, obj2);
            this.a.log(b, Level.WARN, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Object... objArr) {
        if (this.a.isEnabledFor(Level.WARN)) {
            b a = f.a(str, objArr);
            this.a.log(b, Level.WARN, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Throwable th) {
        this.a.log(b, Level.WARN, str, th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isErrorEnabled() {
        return this.a.isEnabledFor(Level.ERROR);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str) {
        this.a.log(b, Level.ERROR, str, (Throwable) null);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Object obj) {
        if (this.a.isEnabledFor(Level.ERROR)) {
            b a = f.a(str, obj);
            this.a.log(b, Level.ERROR, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Object obj, Object obj2) {
        if (this.a.isEnabledFor(Level.ERROR)) {
            b a = f.a(str, obj, obj2);
            this.a.log(b, Level.ERROR, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Object... objArr) {
        if (this.a.isEnabledFor(Level.ERROR)) {
            b a = f.a(str, objArr);
            this.a.log(b, Level.ERROR, a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Throwable th) {
        this.a.log(b, Level.ERROR, str, th);
    }
}
