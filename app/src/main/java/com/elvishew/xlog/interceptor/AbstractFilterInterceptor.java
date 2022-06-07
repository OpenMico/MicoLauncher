package com.elvishew.xlog.interceptor;

import com.elvishew.xlog.LogItem;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/interceptor/AbstractFilterInterceptor.class */
public abstract class AbstractFilterInterceptor implements Interceptor {
    protected abstract boolean reject(LogItem logItem);

    public LogItem intercept(LogItem log) {
        if (reject(log)) {
            return null;
        }
        return log;
    }
}
