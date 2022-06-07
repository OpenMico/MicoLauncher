package com.elvishew.xlog.formatter.stacktrace;

import com.elvishew.xlog.internal.SystemCompat;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/formatter/stacktrace/DefaultStackTraceFormatter.class */
public class DefaultStackTraceFormatter implements StackTraceFormatter {
    public String format(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder(256);
        if (stackTrace == null || stackTrace.length == 0) {
            return null;
        }
        if (stackTrace.length == 1) {
            return "\t─ " + stackTrace[0].toString();
        }
        int N = stackTrace.length;
        for (int i = 0; i < N; i++) {
            if (i != N - 1) {
                sb.append("\t├ ");
                sb.append(stackTrace[i].toString());
                sb.append(SystemCompat.lineSeparator);
            } else {
                sb.append("\t└ ");
                sb.append(stackTrace[i].toString());
            }
        }
        return sb.toString();
    }
}
