package com.elvishew.xlog.flattener;

import com.elvishew.xlog.LogLevel;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/flattener/DefaultFlattener.class */
public class DefaultFlattener implements Flattener, Flattener2 {
    @Override // com.elvishew.xlog.flattener.Flattener
    public CharSequence flatten(int logLevel, String tag, String message) {
        return flatten(System.currentTimeMillis(), logLevel, tag, message);
    }

    @Override // com.elvishew.xlog.flattener.Flattener2
    public CharSequence flatten(long timeMillis, int logLevel, String tag, String message) {
        return Long.toString(timeMillis) + '|' + LogLevel.getShortLevelName(logLevel) + '|' + tag + '|' + message;
    }
}
