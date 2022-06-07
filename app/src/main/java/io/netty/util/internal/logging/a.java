package io.netty.util.internal.logging;

import org.apache.commons.logging.Log;

/* compiled from: CommonsLogger.java */
/* loaded from: classes4.dex */
class a extends AbstractInternalLogger {
    private static final long serialVersionUID = 8647838678388394885L;
    private final transient Log a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Log log, String str) {
        super(str);
        if (log != null) {
            this.a = log;
            return;
        }
        throw new NullPointerException("logger");
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isTraceEnabled() {
        return this.a.isTraceEnabled();
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str) {
        this.a.trace(str);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Object obj) {
        if (this.a.isTraceEnabled()) {
            b a = f.a(str, obj);
            this.a.trace(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Object obj, Object obj2) {
        if (this.a.isTraceEnabled()) {
            b a = f.a(str, obj, obj2);
            this.a.trace(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Object... objArr) {
        if (this.a.isTraceEnabled()) {
            b a = f.a(str, objArr);
            this.a.trace(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Throwable th) {
        this.a.trace(str, th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isDebugEnabled() {
        return this.a.isDebugEnabled();
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str) {
        this.a.debug(str);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Object obj) {
        if (this.a.isDebugEnabled()) {
            b a = f.a(str, obj);
            this.a.debug(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Object obj, Object obj2) {
        if (this.a.isDebugEnabled()) {
            b a = f.a(str, obj, obj2);
            this.a.debug(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Object... objArr) {
        if (this.a.isDebugEnabled()) {
            b a = f.a(str, objArr);
            this.a.debug(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Throwable th) {
        this.a.debug(str, th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isInfoEnabled() {
        return this.a.isInfoEnabled();
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str) {
        this.a.info(str);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Object obj) {
        if (this.a.isInfoEnabled()) {
            b a = f.a(str, obj);
            this.a.info(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Object obj, Object obj2) {
        if (this.a.isInfoEnabled()) {
            b a = f.a(str, obj, obj2);
            this.a.info(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Object... objArr) {
        if (this.a.isInfoEnabled()) {
            b a = f.a(str, objArr);
            this.a.info(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Throwable th) {
        this.a.info(str, th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isWarnEnabled() {
        return this.a.isWarnEnabled();
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str) {
        this.a.warn(str);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Object obj) {
        if (this.a.isWarnEnabled()) {
            b a = f.a(str, obj);
            this.a.warn(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Object obj, Object obj2) {
        if (this.a.isWarnEnabled()) {
            b a = f.a(str, obj, obj2);
            this.a.warn(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Object... objArr) {
        if (this.a.isWarnEnabled()) {
            b a = f.a(str, objArr);
            this.a.warn(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Throwable th) {
        this.a.warn(str, th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isErrorEnabled() {
        return this.a.isErrorEnabled();
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str) {
        this.a.error(str);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Object obj) {
        if (this.a.isErrorEnabled()) {
            b a = f.a(str, obj);
            this.a.error(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Object obj, Object obj2) {
        if (this.a.isErrorEnabled()) {
            b a = f.a(str, obj, obj2);
            this.a.error(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Object... objArr) {
        if (this.a.isErrorEnabled()) {
            b a = f.a(str, objArr);
            this.a.error(a.a(), a.b());
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Throwable th) {
        this.a.error(str, th);
    }
}
