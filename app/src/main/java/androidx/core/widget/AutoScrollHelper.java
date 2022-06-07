package androidx.core.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public abstract class AutoScrollHelper implements View.OnTouchListener {
    public static final int EDGE_TYPE_INSIDE = 0;
    public static final int EDGE_TYPE_INSIDE_EXTEND = 1;
    public static final int EDGE_TYPE_OUTSIDE = 2;
    public static final float NO_MAX = Float.MAX_VALUE;
    public static final float NO_MIN = 0.0f;
    public static final float RELATIVE_UNSPECIFIED = 0.0f;
    private static final int r = ViewConfiguration.getTapTimeout();
    final View b;
    boolean c;
    boolean d;
    boolean e;
    private Runnable g;
    private int j;
    private int k;
    private boolean o;
    private boolean p;
    private boolean q;
    final a a = new a();
    private final Interpolator f = new AccelerateInterpolator();
    private float[] h = {0.0f, 0.0f};
    private float[] i = {Float.MAX_VALUE, Float.MAX_VALUE};
    private float[] l = {0.0f, 0.0f};
    private float[] m = {0.0f, 0.0f};
    private float[] n = {Float.MAX_VALUE, Float.MAX_VALUE};

    static float a(float f, float f2, float f3) {
        return f > f3 ? f3 : f < f2 ? f2 : f;
    }

    static int a(int i, int i2, int i3) {
        return i > i3 ? i3 : i < i2 ? i2 : i;
    }

    public abstract boolean canTargetScrollHorizontally(int i);

    public abstract boolean canTargetScrollVertically(int i);

    public abstract void scrollTargetBy(int i, int i2);

    public AutoScrollHelper(@NonNull View view) {
        this.b = view;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float f = (int) ((displayMetrics.density * 1575.0f) + 0.5f);
        setMaximumVelocity(f, f);
        float f2 = (int) ((displayMetrics.density * 315.0f) + 0.5f);
        setMinimumVelocity(f2, f2);
        setEdgeType(1);
        setMaximumEdges(Float.MAX_VALUE, Float.MAX_VALUE);
        setRelativeEdges(0.2f, 0.2f);
        setRelativeVelocity(1.0f, 1.0f);
        setActivationDelay(r);
        setRampUpDuration(500);
        setRampDownDuration(500);
    }

    public AutoScrollHelper setEnabled(boolean z) {
        if (this.p && !z) {
            d();
        }
        this.p = z;
        return this;
    }

    public boolean isEnabled() {
        return this.p;
    }

    public AutoScrollHelper setExclusive(boolean z) {
        this.q = z;
        return this;
    }

    public boolean isExclusive() {
        return this.q;
    }

    @NonNull
    public AutoScrollHelper setMaximumVelocity(float f, float f2) {
        float[] fArr = this.n;
        fArr[0] = f / 1000.0f;
        fArr[1] = f2 / 1000.0f;
        return this;
    }

    @NonNull
    public AutoScrollHelper setMinimumVelocity(float f, float f2) {
        float[] fArr = this.m;
        fArr[0] = f / 1000.0f;
        fArr[1] = f2 / 1000.0f;
        return this;
    }

    @NonNull
    public AutoScrollHelper setRelativeVelocity(float f, float f2) {
        float[] fArr = this.l;
        fArr[0] = f / 1000.0f;
        fArr[1] = f2 / 1000.0f;
        return this;
    }

    @NonNull
    public AutoScrollHelper setEdgeType(int i) {
        this.j = i;
        return this;
    }

    @NonNull
    public AutoScrollHelper setRelativeEdges(float f, float f2) {
        float[] fArr = this.h;
        fArr[0] = f;
        fArr[1] = f2;
        return this;
    }

    @NonNull
    public AutoScrollHelper setMaximumEdges(float f, float f2) {
        float[] fArr = this.i;
        fArr[0] = f;
        fArr[1] = f2;
        return this;
    }

    @NonNull
    public AutoScrollHelper setActivationDelay(int i) {
        this.k = i;
        return this;
    }

    @NonNull
    public AutoScrollHelper setRampUpDuration(int i) {
        this.a.a(i);
        return this;
    }

    @NonNull
    public AutoScrollHelper setRampDownDuration(int i) {
        this.a.b(i);
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.p) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.d = true;
                this.o = false;
                this.a.a(a(0, motionEvent.getX(), view.getWidth(), this.b.getWidth()), a(1, motionEvent.getY(), view.getHeight(), this.b.getHeight()));
                if (!this.e && a()) {
                    c();
                    break;
                }
                break;
            case 1:
            case 3:
                d();
                break;
            case 2:
                this.a.a(a(0, motionEvent.getX(), view.getWidth(), this.b.getWidth()), a(1, motionEvent.getY(), view.getHeight(), this.b.getHeight()));
                if (!this.e) {
                    c();
                    break;
                }
                break;
        }
        return this.q && this.e;
    }

    boolean a() {
        a aVar = this.a;
        int f = aVar.f();
        int e = aVar.e();
        return (f != 0 && canTargetScrollVertically(f)) || (e != 0 && canTargetScrollHorizontally(e));
    }

    private void c() {
        int i;
        if (this.g == null) {
            this.g = new b();
        }
        this.e = true;
        this.c = true;
        if (this.o || (i = this.k) <= 0) {
            this.g.run();
        } else {
            ViewCompat.postOnAnimationDelayed(this.b, this.g, i);
        }
        this.o = true;
    }

    private void d() {
        if (this.c) {
            this.e = false;
        } else {
            this.a.b();
        }
    }

    private float a(int i, float f, float f2, float f3) {
        float a2 = a(this.h[i], f2, this.i[i], f);
        int i2 = (a2 > 0.0f ? 1 : (a2 == 0.0f ? 0 : -1));
        if (i2 == 0) {
            return 0.0f;
        }
        float f4 = this.l[i];
        float f5 = this.m[i];
        float f6 = this.n[i];
        float f7 = f4 * f3;
        if (i2 > 0) {
            return a(a2 * f7, f5, f6);
        }
        return -a((-a2) * f7, f5, f6);
    }

    private float a(float f, float f2, float f3, float f4) {
        float f5;
        float a2 = a(f * f2, 0.0f, f3);
        float a3 = a(f2 - f4, a2) - a(f4, a2);
        if (a3 < 0.0f) {
            f5 = -this.f.getInterpolation(-a3);
        } else if (a3 <= 0.0f) {
            return 0.0f;
        } else {
            f5 = this.f.getInterpolation(a3);
        }
        return a(f5, -1.0f, 1.0f);
    }

    private float a(float f, float f2) {
        if (f2 == 0.0f) {
            return 0.0f;
        }
        int i = this.j;
        switch (i) {
            case 0:
            case 1:
                if (f < f2) {
                    if (f >= 0.0f) {
                        return 1.0f - (f / f2);
                    }
                    if (this.e && i == 1) {
                        return 1.0f;
                    }
                }
                break;
            case 2:
                if (f < 0.0f) {
                    return f / (-f2);
                }
                break;
        }
        return 0.0f;
    }

    void b() {
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
        this.b.onTouchEvent(obtain);
        obtain.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (AutoScrollHelper.this.e) {
                if (AutoScrollHelper.this.c) {
                    AutoScrollHelper autoScrollHelper = AutoScrollHelper.this;
                    autoScrollHelper.c = false;
                    autoScrollHelper.a.a();
                }
                a aVar = AutoScrollHelper.this.a;
                if (aVar.c() || !AutoScrollHelper.this.a()) {
                    AutoScrollHelper.this.e = false;
                    return;
                }
                if (AutoScrollHelper.this.d) {
                    AutoScrollHelper autoScrollHelper2 = AutoScrollHelper.this;
                    autoScrollHelper2.d = false;
                    autoScrollHelper2.b();
                }
                aVar.d();
                AutoScrollHelper.this.scrollTargetBy(aVar.g(), aVar.h());
                ViewCompat.postOnAnimation(AutoScrollHelper.this.b, this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        private int a;
        private int b;
        private float c;
        private float d;
        private float j;
        private int k;
        private long e = Long.MIN_VALUE;
        private long i = -1;
        private long f = 0;
        private int g = 0;
        private int h = 0;

        private float a(float f) {
            return ((-4.0f) * f * f) + (f * 4.0f);
        }

        a() {
        }

        public void a(int i) {
            this.a = i;
        }

        public void b(int i) {
            this.b = i;
        }

        public void a() {
            this.e = AnimationUtils.currentAnimationTimeMillis();
            this.i = -1L;
            this.f = this.e;
            this.j = 0.5f;
            this.g = 0;
            this.h = 0;
        }

        public void b() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.k = AutoScrollHelper.a((int) (currentAnimationTimeMillis - this.e), 0, this.b);
            this.j = a(currentAnimationTimeMillis);
            this.i = currentAnimationTimeMillis;
        }

        public boolean c() {
            return this.i > 0 && AnimationUtils.currentAnimationTimeMillis() > this.i + ((long) this.k);
        }

        private float a(long j) {
            if (j < this.e) {
                return 0.0f;
            }
            long j2 = this.i;
            if (j2 < 0 || j < j2) {
                return AutoScrollHelper.a(((float) (j - this.e)) / this.a, 0.0f, 1.0f) * 0.5f;
            }
            long j3 = j - j2;
            float f = this.j;
            return (1.0f - f) + (f * AutoScrollHelper.a(((float) j3) / this.k, 0.0f, 1.0f));
        }

        public void d() {
            if (this.f != 0) {
                long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                float a = a(a(currentAnimationTimeMillis));
                this.f = currentAnimationTimeMillis;
                float f = ((float) (currentAnimationTimeMillis - this.f)) * a;
                this.g = (int) (this.c * f);
                this.h = (int) (f * this.d);
                return;
            }
            throw new RuntimeException("Cannot compute scroll delta before calling start()");
        }

        public void a(float f, float f2) {
            this.c = f;
            this.d = f2;
        }

        public int e() {
            float f = this.c;
            return (int) (f / Math.abs(f));
        }

        public int f() {
            float f = this.d;
            return (int) (f / Math.abs(f));
        }

        public int g() {
            return this.g;
        }

        public int h() {
            return this.h;
        }
    }
}
