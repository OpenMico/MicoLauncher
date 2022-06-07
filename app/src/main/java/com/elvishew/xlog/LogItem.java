package com.elvishew.xlog;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/LogItem.class */
public class LogItem {
    public int level;
    public String tag;
    public String msg;
    public String threadInfo;
    public String stackTraceInfo;

    public LogItem(int level, String tag, String msg) {
        this.level = level;
        this.tag = tag;
        this.msg = msg;
    }

    public LogItem(int level, String tag, String threadInfo, String stackTraceInfo, String msg) {
        this.level = level;
        this.tag = tag;
        this.threadInfo = threadInfo;
        this.stackTraceInfo = stackTraceInfo;
        this.msg = msg;
    }
}
