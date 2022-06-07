package com.elvishew.xlog.internal.util;

import com.elvishew.xlog.XLog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/internal/util/StackTraceUtil.class */
public class StackTraceUtil {
    private static final String XLOG_STACK_TRACE_ORIGIN;

    static {
        String xlogClassName = XLog.class.getName();
        XLOG_STACK_TRACE_ORIGIN = xlogClassName.substring(0, xlogClassName.lastIndexOf(46) + 1);
    }

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        for (Throwable t = tr; t != null; t = t.getCause()) {
            if (t instanceof UnknownHostException) {
                return "";
            }
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public static StackTraceElement[] getCroppedRealStackTrack(StackTraceElement[] stackTrace, String stackTraceOrigin, int maxDepth) {
        return cropStackTrace(getRealStackTrack(stackTrace, stackTraceOrigin), maxDepth);
    }

    private static StackTraceElement[] getRealStackTrack(StackTraceElement[] stackTrace, String stackTraceOrigin) {
        int ignoreDepth = 0;
        int allDepth = stackTrace.length;
        for (int i = allDepth - 1; i >= 0; i--) {
            String className = stackTrace[i].getClassName();
            if (className.startsWith(XLOG_STACK_TRACE_ORIGIN) || (stackTraceOrigin != null && className.startsWith(stackTraceOrigin))) {
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(stackTrace, ignoreDepth, realStack, 0, realDepth);
        return realStack;
    }

    private static StackTraceElement[] cropStackTrace(StackTraceElement[] callStack, int maxDepth) {
        int realDepth = callStack.length;
        if (maxDepth > 0) {
            realDepth = Math.min(maxDepth, realDepth);
        }
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callStack, 0, realStack, 0, realDepth);
        return realStack;
    }
}
