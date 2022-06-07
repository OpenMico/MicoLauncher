package xcrash;

import android.annotation.SuppressLint;
import android.os.Process;
import android.text.TextUtils;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: JavaCrashHandler.java */
@SuppressLint({"StaticFieldLeak"})
/* loaded from: classes6.dex */
public class e implements Thread.UncaughtExceptionHandler {
    private static final e a = new e();
    private int c;
    private String d;
    private String e;
    private String f;
    private boolean g;
    private String h;
    private int i;
    private int j;
    private int k;
    private boolean l;
    private boolean m;
    private boolean n;
    private int o;
    private String[] p;
    private ICrashCallback q;
    private final Date b = new Date();
    private Thread.UncaughtExceptionHandler r = null;

    private e() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static e a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, String str, String str2, String str3, String str4, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5, String[] strArr, ICrashCallback iCrashCallback) {
        this.c = i;
        if (TextUtils.isEmpty(str)) {
            str = "unknown";
        }
        this.d = str;
        this.e = str2;
        this.f = str3;
        this.g = z;
        this.h = str4;
        this.i = i2;
        this.j = i3;
        this.k = i4;
        this.l = z2;
        this.m = z3;
        this.n = z4;
        this.o = i5;
        this.p = strArr;
        this.q = iCrashCallback;
        this.r = Thread.getDefaultUncaughtExceptionHandler();
        try {
            Thread.setDefaultUncaughtExceptionHandler(this);
        } catch (Exception e) {
            XCrash.d().e("xcrash", "JavaCrashHandler setDefaultUncaughtExceptionHandler failed", e);
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.r;
        if (uncaughtExceptionHandler != null) {
            Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
        try {
            a(thread, th);
        } catch (Exception e) {
            XCrash.d().e("xcrash", "JavaCrashHandler handleException failed", e);
        }
        if (this.g) {
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = this.r;
            if (uncaughtExceptionHandler2 != null) {
                uncaughtExceptionHandler2.uncaughtException(thread, th);
                return;
            }
            return;
        }
        a.a().b();
        Process.killProcess(this.c);
        System.exit(10);
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x018e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0086 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.Thread r11, java.lang.Throwable r12) {
        /*
            Method dump skipped, instructions count: 417
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: xcrash.e.a(java.lang.Thread, java.lang.Throwable):void");
    }

    private String a(Date date, Thread thread, String str) {
        return f.a(this.b, date, "java", this.e, this.f) + "pid: " + this.c + ", tid: " + Process.myTid() + ", name: " + thread.getName() + "  >>> " + this.d + " <<<\n\njava stacktrace:\n" + str + "\n";
    }

    private String a(Thread thread) {
        ArrayList<Pattern> arrayList;
        if (this.p != null) {
            arrayList = new ArrayList<>();
            for (String str : this.p) {
                try {
                    arrayList.add(Pattern.compile(str));
                } catch (Exception e) {
                    XCrash.d().w("xcrash", "JavaCrashHandler pattern compile failed", e);
                }
            }
        } else {
            arrayList = null;
        }
        StringBuilder sb = new StringBuilder();
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
            Thread key = entry.getKey();
            StackTraceElement[] value = entry.getValue();
            if (!key.getName().equals(thread.getName()) && (arrayList == null || a(arrayList, key.getName()))) {
                i2++;
                int i4 = this.o;
                if (i4 <= 0 || i < i4) {
                    sb.append("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n");
                    sb.append("pid: ");
                    sb.append(this.c);
                    sb.append(", tid: ");
                    sb.append(key.getId());
                    sb.append(", name: ");
                    sb.append(key.getName());
                    sb.append("  >>> ");
                    sb.append(this.d);
                    sb.append(" <<<\n");
                    sb.append("\n");
                    sb.append("java stacktrace:\n");
                    for (StackTraceElement stackTraceElement : value) {
                        sb.append("    at ");
                        sb.append(stackTraceElement.toString());
                        sb.append("\n");
                    }
                    sb.append("\n");
                    i++;
                } else {
                    i3++;
                }
            }
        }
        if (allStackTraces.size() > 1) {
            if (i == 0) {
                sb.append("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n");
            }
            sb.append("total JVM threads (exclude the crashed thread): ");
            sb.append(allStackTraces.size() - 1);
            sb.append("\n");
            if (arrayList != null) {
                sb.append("JVM threads matched whitelist: ");
                sb.append(i2);
                sb.append("\n");
            }
            if (this.o > 0) {
                sb.append("JVM threads ignored by max count limit: ");
                sb.append(i3);
                sb.append("\n");
            }
            sb.append("dumped JVM threads:");
            sb.append(i);
            sb.append("\n");
            sb.append("+++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++\n");
        }
        return sb.toString();
    }

    private boolean a(ArrayList<Pattern> arrayList, String str) {
        Iterator<Pattern> it = arrayList.iterator();
        while (it.hasNext()) {
            if (it.next().matcher(str).matches()) {
                return true;
            }
        }
        return false;
    }
}
