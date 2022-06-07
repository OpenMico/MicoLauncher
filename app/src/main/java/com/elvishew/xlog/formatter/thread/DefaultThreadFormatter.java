package com.elvishew.xlog.formatter.thread;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/formatter/thread/DefaultThreadFormatter.class */
public class DefaultThreadFormatter implements ThreadFormatter {
    public String format(Thread data) {
        return "Thread: " + data.getName();
    }
}
