package org.eclipse.jetty.util.log;

/* loaded from: classes5.dex */
public interface Logger {
    void debug(String str, Throwable th);

    void debug(String str, Object... objArr);

    void debug(Throwable th);

    Logger getLogger(String str);

    String getName();

    void ignore(Throwable th);

    void info(String str, Throwable th);

    void info(String str, Object... objArr);

    void info(Throwable th);

    boolean isDebugEnabled();

    void setDebugEnabled(boolean z);

    void warn(String str, Throwable th);

    void warn(String str, Object... objArr);

    void warn(Throwable th);
}
