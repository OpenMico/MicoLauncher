package io.netty.util.internal.logging;

/* loaded from: classes4.dex */
public interface InternalLogger {
    void debug(String str);

    void debug(String str, Object obj);

    void debug(String str, Object obj, Object obj2);

    void debug(String str, Throwable th);

    void debug(String str, Object... objArr);

    void debug(Throwable th);

    void error(String str);

    void error(String str, Object obj);

    void error(String str, Object obj, Object obj2);

    void error(String str, Throwable th);

    void error(String str, Object... objArr);

    void error(Throwable th);

    void info(String str);

    void info(String str, Object obj);

    void info(String str, Object obj, Object obj2);

    void info(String str, Throwable th);

    void info(String str, Object... objArr);

    void info(Throwable th);

    boolean isDebugEnabled();

    boolean isEnabled(InternalLogLevel internalLogLevel);

    boolean isErrorEnabled();

    boolean isInfoEnabled();

    boolean isTraceEnabled();

    boolean isWarnEnabled();

    void log(InternalLogLevel internalLogLevel, String str);

    void log(InternalLogLevel internalLogLevel, String str, Object obj);

    void log(InternalLogLevel internalLogLevel, String str, Object obj, Object obj2);

    void log(InternalLogLevel internalLogLevel, String str, Throwable th);

    void log(InternalLogLevel internalLogLevel, String str, Object... objArr);

    void log(InternalLogLevel internalLogLevel, Throwable th);

    String name();

    void trace(String str);

    void trace(String str, Object obj);

    void trace(String str, Object obj, Object obj2);

    void trace(String str, Throwable th);

    void trace(String str, Object... objArr);

    void trace(Throwable th);

    void warn(String str);

    void warn(String str, Object obj);

    void warn(String str, Object obj, Object obj2);

    void warn(String str, Throwable th);

    void warn(String str, Object... objArr);

    void warn(Throwable th);
}
