package com.efs.sdk.pa.a;

import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import com.efs.sdk.pa.PAANRListener;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class a {
    volatile boolean a;
    final Handler b;
    final Thread c;
    long d;
    long e;
    boolean f;
    Handler g;
    PAANRListener h;
    long i;
    long j;
    final long k;
    boolean l;
    final Runnable m;
    final Runnable n;
    private HandlerThread o;
    private Application p;

    public a(Application application, long j) {
        this(application, j, true);
    }

    public a(Application application, long j, boolean z) {
        this.a = true;
        this.e = 4L;
        this.f = true;
        this.i = 0L;
        this.m = new Runnable() { // from class: com.efs.sdk.pa.a.a.1
            /* JADX WARN: Code restructure failed: missing block: B:23:0x0082, code lost:
                if (com.efs.sdk.pa.a.a.a(r1) != false) goto L_0x0084;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    r5 = this;
                    com.efs.sdk.pa.a.a r0 = com.efs.sdk.pa.a.a.this
                    boolean r0 = r0.f
                    if (r0 == 0) goto L_0x0007
                    return
                L_0x0007:
                    long r0 = android.os.SystemClock.uptimeMillis()
                    com.efs.sdk.pa.a.a r2 = com.efs.sdk.pa.a.a.this
                    long r2 = r2.j
                    long r0 = r0 - r2
                    com.efs.sdk.pa.a.a r2 = com.efs.sdk.pa.a.a.this
                    long r2 = r2.k
                    int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                    if (r2 <= 0) goto L_0x0029
                    com.efs.sdk.pa.a.a r2 = com.efs.sdk.pa.a.a.this
                    com.efs.sdk.pa.PAANRListener r2 = r2.h
                    if (r2 == 0) goto L_0x0029
                    com.efs.sdk.pa.a.a r2 = com.efs.sdk.pa.a.a.this
                    com.efs.sdk.pa.PAANRListener r2 = r2.h
                    java.lang.Long r0 = java.lang.Long.valueOf(r0)
                    r2.unexcept(r0)
                L_0x0029:
                    com.efs.sdk.pa.a.a r0 = com.efs.sdk.pa.a.a.this
                    boolean r0 = r0.a
                    if (r0 == 0) goto L_0x0042
                    com.efs.sdk.pa.a.a r0 = com.efs.sdk.pa.a.a.this
                    r1 = 0
                    r0.i = r1
                    r1 = 0
                    r0.a = r1
                    android.os.Handler r0 = r0.b
                    com.efs.sdk.pa.a.a r1 = com.efs.sdk.pa.a.a.this
                    java.lang.Runnable r1 = r1.n
                    r0.postAtFrontOfQueue(r1)
                    goto L_0x0097
                L_0x0042:
                    com.efs.sdk.pa.a.a r0 = com.efs.sdk.pa.a.a.this
                    long r1 = r0.i
                    r3 = 1
                    long r1 = r1 + r3
                    r0.i = r1
                    com.efs.sdk.pa.a.a r0 = com.efs.sdk.pa.a.a.this
                    boolean r0 = r0.a
                    if (r0 != 0) goto L_0x0097
                    com.efs.sdk.pa.a.a r0 = com.efs.sdk.pa.a.a.this
                    long r0 = r0.i
                    com.efs.sdk.pa.a.a r2 = com.efs.sdk.pa.a.a.this
                    long r2 = r2.e
                    int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                    if (r0 < 0) goto L_0x0097
                    com.efs.sdk.pa.a.a r0 = com.efs.sdk.pa.a.a.this
                    long r0 = r0.i
                    com.efs.sdk.pa.a.a r2 = com.efs.sdk.pa.a.a.this
                    long r2 = r2.e
                    int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                    if (r0 != 0) goto L_0x0097
                    com.efs.sdk.pa.a.a r0 = com.efs.sdk.pa.a.a.this
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    boolean r2 = r0.l
                    if (r2 == 0) goto L_0x007e
                    java.lang.Thread r2 = r0.c
                    java.lang.String r2 = com.efs.sdk.pa.a.a.a(r2)
                    r1.append(r2)
                    goto L_0x0084
                L_0x007e:
                    boolean r2 = com.efs.sdk.pa.a.a.a(r1)
                    if (r2 == 0) goto L_0x0097
                L_0x0084:
                    com.efs.sdk.pa.PAANRListener r2 = r0.h
                    if (r2 == 0) goto L_0x0097
                    int r2 = r1.length()
                    if (r2 <= 0) goto L_0x0097
                    com.efs.sdk.pa.PAANRListener r0 = r0.h
                    java.lang.String r1 = r1.toString()
                    r0.anrStack(r1)
                L_0x0097:
                    com.efs.sdk.pa.a.a r0 = com.efs.sdk.pa.a.a.this
                    long r1 = android.os.SystemClock.uptimeMillis()
                    r0.j = r1
                    com.efs.sdk.pa.a.a r0 = com.efs.sdk.pa.a.a.this
                    android.os.Handler r0 = r0.g
                    com.efs.sdk.pa.a.a r1 = com.efs.sdk.pa.a.a.this
                    java.lang.Runnable r1 = r1.m
                    com.efs.sdk.pa.a.a r2 = com.efs.sdk.pa.a.a.this
                    long r2 = r2.d
                    r0.postDelayed(r1, r2)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.efs.sdk.pa.a.a.AnonymousClass1.run():void");
            }
        };
        this.n = new Runnable() { // from class: com.efs.sdk.pa.a.a.2
            @Override // java.lang.Runnable
            public final void run() {
                a.this.a = true;
            }
        };
        this.k = j;
        this.p = application;
        this.l = z;
        this.d = (((float) j) * 0.8f) / this.e;
        if (this.d < 100) {
            this.d = 100L;
            this.e = j / this.d;
        }
        Log.i("Matrix.AnrTracer", "anrTrace, final mAnrBeatTime:" + this.d + ", mAnrBeatRate:" + this.d);
        this.c = Looper.getMainLooper().getThread();
        this.b = new Handler(Looper.getMainLooper());
        this.o = new HandlerThread("ANR HANDLER THREAD");
        this.o.start();
        this.g = new Handler(this.o.getLooper());
    }

    static boolean a(StringBuilder sb) {
        Set<Map.Entry<Thread, StackTraceElement[]>> entrySet = Thread.getAllStackTraces().entrySet();
        if (entrySet.size() == 0) {
            return false;
        }
        boolean z = false;
        for (Map.Entry<Thread, StackTraceElement[]> entry : entrySet) {
            Thread key = entry.getKey();
            StackTraceElement[] value = entry.getValue();
            if (key.getId() == Looper.getMainLooper().getThread().getId()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(key.getName());
                sb2.append(StringUtils.SPACE);
                sb2.append(key.getPriority());
                sb2.append(StringUtils.SPACE);
                sb2.append(key.getState());
                sb2.append("\n");
                for (StackTraceElement stackTraceElement : value) {
                    String stackTraceElement2 = stackTraceElement.toString();
                    sb2.append("  at  ");
                    sb2.append(stackTraceElement2);
                    sb2.append('\n');
                }
                sb2.append("\n");
                sb.insert(0, (CharSequence) sb2);
                z = true;
            } else {
                sb.append(key.getName());
                sb.append(StringUtils.SPACE);
                sb.append(key.getPriority());
                sb.append(StringUtils.SPACE);
                sb.append(key.getState());
                sb.append("\n");
                for (StackTraceElement stackTraceElement3 : value) {
                    String stackTraceElement4 = stackTraceElement3.toString();
                    sb.append("  at  ");
                    sb.append(stackTraceElement4);
                    sb.append('\n');
                }
                sb.append("\n");
            }
        }
        if (!z) {
            sb.insert(0, a(Looper.getMainLooper().getThread()));
        }
        return true;
    }

    static String a(Thread thread) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = thread.getStackTrace();
        sb.append(thread.getName());
        sb.append(StringUtils.SPACE);
        sb.append(thread.getPriority());
        sb.append(StringUtils.SPACE);
        sb.append(thread.getState());
        sb.append("\n");
        for (StackTraceElement stackTraceElement : stackTrace) {
            String stackTraceElement2 = stackTraceElement.toString();
            sb.append("  at  ");
            sb.append(stackTraceElement2);
            sb.append('\n');
        }
        sb.append("\n");
        return sb.toString();
    }
}
