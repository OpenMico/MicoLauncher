package miuix.animation.utils;

import android.os.SystemClock;
import java.util.Arrays;
import java.util.LinkedList;

/* loaded from: classes5.dex */
public class VelocityMonitor {
    private Long a = 30L;
    private Long b = 100L;
    private LinkedList<a> c = new LinkedList<>();
    private float[] d;

    private float a(double d, double d2, long j) {
        return (float) (j == 0 ? 0.0d : (d - d2) / (((float) j) / 1000.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a {
        double[] a;
        long b;

        private a() {
        }
    }

    public void setMinFeedbackTime(long j) {
        this.a = Long.valueOf(j);
    }

    public void setMaxFeedbackTime(long j) {
        this.b = Long.valueOf(j);
    }

    public void update(float... fArr) {
        if (!(fArr == null || fArr.length == 0)) {
            a a2 = a();
            a2.a = new double[fArr.length];
            for (int i = 0; i < fArr.length; i++) {
                a2.a[i] = fArr[i];
            }
            a(a2);
        }
    }

    public void update(double... dArr) {
        if (dArr != null && dArr.length != 0) {
            a a2 = a();
            a2.a = dArr;
            a(a2);
        }
    }

    private a a() {
        a aVar = new a();
        aVar.b = SystemClock.uptimeMillis();
        return aVar;
    }

    private void a(a aVar) {
        this.c.add(aVar);
        if (this.c.size() > 10) {
            this.c.remove(0);
        }
        c();
    }

    public float getVelocity(int i) {
        float[] fArr;
        long uptimeMillis = SystemClock.uptimeMillis();
        if ((this.c.size() <= 0 || Math.abs(uptimeMillis - this.c.getLast().b) <= 50) && (fArr = this.d) != null && fArr.length > i) {
            return fArr[i];
        }
        return 0.0f;
    }

    public void clear() {
        this.c.clear();
        b();
    }

    private void b() {
        float[] fArr = this.d;
        if (fArr != null) {
            Arrays.fill(fArr, 0.0f);
        }
    }

    private void c() {
        int size = this.c.size();
        if (size >= 2) {
            a last = this.c.getLast();
            a aVar = this.c.get(size - 2);
            float[] fArr = this.d;
            if (fArr == null || fArr.length < last.a.length) {
                this.d = new float[last.a.length];
            }
            for (int i = 0; i < last.a.length; i++) {
                this.d[i] = a(i, last, aVar);
            }
            return;
        }
        b();
    }

    private float a(int i, a aVar, a aVar2) {
        float f;
        double d = aVar.a[i];
        long j = aVar.b;
        double a2 = a(d, aVar2.a[i], j - aVar2.b);
        int size = this.c.size() - 2;
        a aVar3 = null;
        while (true) {
            if (size < 0) {
                f = Float.MAX_VALUE;
                break;
            }
            a aVar4 = this.c.get(size);
            long j2 = j - aVar4.b;
            if (j2 <= this.a.longValue() || j2 >= this.b.longValue()) {
                size--;
                aVar3 = aVar4;
            } else {
                f = a(d, aVar4.a[i], j2);
                double d2 = f;
                if (a2 * d2 > 0.0d) {
                    f = (float) (f > 0.0f ? Math.max(a2, d2) : Math.min(a2, d2));
                    aVar3 = aVar4;
                } else {
                    aVar3 = aVar4;
                }
            }
        }
        if (f == Float.MAX_VALUE && aVar3 != null) {
            long j3 = j - aVar3.b;
            if (j3 > this.a.longValue() && j3 < this.b.longValue()) {
                f = a(d, aVar3.a[i], j3);
            }
        }
        if (f == Float.MAX_VALUE) {
            return 0.0f;
        }
        return f;
    }
}
