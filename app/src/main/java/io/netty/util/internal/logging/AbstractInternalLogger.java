package io.netty.util.internal.logging;

import io.netty.util.internal.StringUtil;
import java.io.ObjectStreamException;
import java.io.Serializable;

/* loaded from: classes4.dex */
public abstract class AbstractInternalLogger implements InternalLogger, Serializable {
    private static final long serialVersionUID = -6382972526573193470L;
    private final String name;

    public AbstractInternalLogger(String str) {
        if (str != null) {
            this.name = str;
            return;
        }
        throw new NullPointerException("name");
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public String name() {
        return this.name;
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public boolean isEnabled(InternalLogLevel internalLogLevel) {
        switch (internalLogLevel) {
            case TRACE:
                return isTraceEnabled();
            case DEBUG:
                return isDebugEnabled();
            case INFO:
                return isInfoEnabled();
            case WARN:
                return isWarnEnabled();
            case ERROR:
                return isErrorEnabled();
            default:
                throw new Error();
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void trace(Throwable th) {
        trace("Unexpected exception:", th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void debug(Throwable th) {
        debug("Unexpected exception:", th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void info(Throwable th) {
        info("Unexpected exception:", th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void warn(Throwable th) {
        warn("Unexpected exception:", th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void error(Throwable th) {
        error("Unexpected exception:", th);
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void log(InternalLogLevel internalLogLevel, String str, Throwable th) {
        switch (internalLogLevel) {
            case TRACE:
                trace(str, th);
                return;
            case DEBUG:
                debug(str, th);
                return;
            case INFO:
                info(str, th);
                return;
            case WARN:
                warn(str, th);
                return;
            case ERROR:
                error(str, th);
                return;
            default:
                throw new Error();
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void log(InternalLogLevel internalLogLevel, Throwable th) {
        switch (internalLogLevel) {
            case TRACE:
                trace(th);
                return;
            case DEBUG:
                debug(th);
                return;
            case INFO:
                info(th);
                return;
            case WARN:
                warn(th);
                return;
            case ERROR:
                error(th);
                return;
            default:
                throw new Error();
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void log(InternalLogLevel internalLogLevel, String str) {
        switch (internalLogLevel) {
            case TRACE:
                trace(str);
                return;
            case DEBUG:
                debug(str);
                return;
            case INFO:
                info(str);
                return;
            case WARN:
                warn(str);
                return;
            case ERROR:
                error(str);
                return;
            default:
                throw new Error();
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void log(InternalLogLevel internalLogLevel, String str, Object obj) {
        switch (internalLogLevel) {
            case TRACE:
                trace(str, obj);
                return;
            case DEBUG:
                debug(str, obj);
                return;
            case INFO:
                info(str, obj);
                return;
            case WARN:
                warn(str, obj);
                return;
            case ERROR:
                error(str, obj);
                return;
            default:
                throw new Error();
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void log(InternalLogLevel internalLogLevel, String str, Object obj, Object obj2) {
        switch (internalLogLevel) {
            case TRACE:
                trace(str, obj, obj2);
                return;
            case DEBUG:
                debug(str, obj, obj2);
                return;
            case INFO:
                info(str, obj, obj2);
                return;
            case WARN:
                warn(str, obj, obj2);
                return;
            case ERROR:
                error(str, obj, obj2);
                return;
            default:
                throw new Error();
        }
    }

    @Override // io.netty.util.internal.logging.InternalLogger
    public void log(InternalLogLevel internalLogLevel, String str, Object... objArr) {
        switch (internalLogLevel) {
            case TRACE:
                trace(str, objArr);
                return;
            case DEBUG:
                debug(str, objArr);
                return;
            case INFO:
                info(str, objArr);
                return;
            case WARN:
                warn(str, objArr);
                return;
            case ERROR:
                error(str, objArr);
                return;
            default:
                throw new Error();
        }
    }

    protected Object readResolve() throws ObjectStreamException {
        return InternalLoggerFactory.getInstance(name());
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + name() + ')';
    }
}
