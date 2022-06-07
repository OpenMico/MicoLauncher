package com.xiaomi.onetrack.api;

import android.os.Process;
import com.xiaomi.onetrack.e.a;
import com.xiaomi.onetrack.util.b;
import com.xiaomi.onetrack.util.p;
import java.lang.Thread;
import java.util.Date;

/* loaded from: classes4.dex */
public class e implements Thread.UncaughtExceptionHandler {
    private static final String a = "OneTrackExceptionHandler";
    private static final String c = "tombstone";
    private static final String d = ".java.xcrash";
    private static final String e = "backtrace feature id:\n\t";
    private static final String f = "error reason:\n\t";
    private Thread.UncaughtExceptionHandler b;
    private final Date g = new Date();
    private int h = 50;
    private int i = 50;
    private int j = 200;
    private boolean k = true;
    private boolean l = true;

    public void a() {
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (!(defaultUncaughtExceptionHandler instanceof e)) {
            this.b = defaultUncaughtExceptionHandler;
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        p.a(a, "uncaughtException start");
        a(thread, th);
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.b;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:2|(2:56|3)|(3:63|4|(1:6))|54|11|61|12|(1:59)|(17:65|18|71|19|(1:21)|22|(1:26)|28|29|(1:31)|32|(1:34)|35|36|58|67|53)(1:73)|(1:(0))) */
    /* JADX WARN: Can't wrap try/catch for region: R(11:2|56|3|(3:63|4|(1:6))|54|11|61|12|(1:59)|(17:65|18|71|19|(1:21)|22|(1:26)|28|29|(1:31)|32|(1:34)|35|36|58|67|53)(1:73)|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x007c, code lost:
        r11 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x007e, code lost:
        r11 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x007f, code lost:
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0080, code lost:
        com.xiaomi.onetrack.util.p.b(com.xiaomi.onetrack.api.e.a, "JavaCrashHandler getEmergency failed", r11);
        r11 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00ec, code lost:
        if (r10.i > 0) goto L_0x00ee;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:65:0x008a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.Thread r11, java.lang.Throwable r12) {
        /*
            Method dump skipped, instructions count: 366
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.onetrack.api.e.a(java.lang.Thread, java.lang.Throwable):void");
    }

    private String a(Date date, Thread thread, String str) {
        return b.a(this.g, date, "java", a.d(), b.a(a.a())) + "pid: " + Process.myPid() + ", tid: " + Process.myTid() + ", name: " + thread.getName() + "  >>> " + b.a(a.a(), Process.myPid()) + " <<<\n\njava stacktrace:\n" + str + "\n";
    }
}
