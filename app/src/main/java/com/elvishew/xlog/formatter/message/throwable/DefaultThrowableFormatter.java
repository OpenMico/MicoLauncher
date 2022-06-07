package com.elvishew.xlog.formatter.message.throwable;

import com.elvishew.xlog.internal.util.StackTraceUtil;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/formatter/message/throwable/DefaultThrowableFormatter.class */
public class DefaultThrowableFormatter implements ThrowableFormatter {
    public String format(Throwable tr) {
        return StackTraceUtil.getStackTraceString(tr);
    }
}
