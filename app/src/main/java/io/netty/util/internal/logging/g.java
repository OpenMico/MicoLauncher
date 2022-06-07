package io.netty.util.internal.logging;

import org.slf4j.Logger;

/* compiled from: Slf4JLogger.java */
/* loaded from: classes4.dex */
class g extends AbstractInternalLogger {
    private static final long serialVersionUID = 108038972685130825L;
    private final transient Logger a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(Logger logger) {
        super(logger.getName());
        this.a = logger;
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
        this.a.trace(str, obj);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Object obj, Object obj2) {
        this.a.trace(str, obj, obj2);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(String str, Object... objArr) {
        this.a.trace(str, objArr);
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
        this.a.debug(str, obj);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Object obj, Object obj2) {
        this.a.debug(str, obj, obj2);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(String str, Object... objArr) {
        this.a.debug(str, objArr);
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
        this.a.info(str, obj);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Object obj, Object obj2) {
        this.a.info(str, obj, obj2);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(String str, Object... objArr) {
        this.a.info(str, objArr);
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
        this.a.warn(str, obj);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Object... objArr) {
        this.a.warn(str, objArr);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(String str, Object obj, Object obj2) {
        this.a.warn(str, obj, obj2);
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
        this.a.error(str, obj);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Object obj, Object obj2) {
        this.a.error(str, obj, obj2);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Object... objArr) {
        this.a.error(str, objArr);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(String str, Throwable th) {
        this.a.error(str, th);
    }
}
