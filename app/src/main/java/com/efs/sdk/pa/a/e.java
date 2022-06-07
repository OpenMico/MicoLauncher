package com.efs.sdk.pa.a;

import android.os.SystemClock;
import android.util.Printer;
import java.util.Iterator;
import java.util.Vector;

/* loaded from: classes.dex */
final class e implements Printer {
    private long f;
    private boolean b = false;
    private String c = null;
    private long d = -1;
    private long e = -1;
    Vector<d> a = new Vector<>();

    @Override // android.util.Printer
    public final void println(String str) {
        if (str.startsWith(">")) {
            this.d = SystemClock.elapsedRealtime();
            this.e = SystemClock.currentThreadTimeMillis();
            this.c = str;
            this.b = true;
            Iterator<d> it = this.a.iterator();
            while (it.hasNext()) {
                it.next();
            }
        } else if (this.b && str.startsWith("<")) {
            this.b = false;
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.d;
            if (elapsedRealtime > this.f) {
                long currentThreadTimeMillis = SystemClock.currentThreadTimeMillis() - this.e;
                Iterator<d> it2 = this.a.iterator();
                while (it2.hasNext()) {
                    it2.next().a(this.c, elapsedRealtime, currentThreadTimeMillis);
                }
            }
        }
    }
}
