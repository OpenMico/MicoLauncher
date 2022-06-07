package com.milink.base.itf;

import androidx.annotation.Keep;

@Keep
/* loaded from: classes2.dex */
public interface ILogger {
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;

    void log(int i, String str, String str2, Throwable th);

    default void log(int i, String str, String str2) {
        log(i, str, str2, null);
    }
}
