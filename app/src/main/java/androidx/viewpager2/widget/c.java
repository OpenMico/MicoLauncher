package androidx.viewpager2.widget;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: FakeDrag.java */
/* loaded from: classes.dex */
final class c {
    private final ViewPager2 a;
    private final e b;
    private final RecyclerView c;
    private VelocityTracker d;
    private int e;
    private float f;
    private int g;
    private long h;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(ViewPager2 viewPager2, e eVar, RecyclerView recyclerView) {
        this.a = viewPager2;
        this.b = eVar;
        this.c = recyclerView;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return this.b.g();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @UiThread
    public boolean b() {
        if (this.b.f()) {
            return false;
        }
        this.g = 0;
        this.f = 0;
        this.h = SystemClock.uptimeMillis();
        d();
        this.b.b();
        if (!this.b.e()) {
            this.c.stopScroll();
        }
        a(this.h, 0, 0.0f, 0.0f);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @UiThread
    public boolean a(float f) {
        if (!this.b.g()) {
            return false;
        }
        this.f -= f;
        int round = Math.round(this.f - this.g);
        this.g += round;
        long uptimeMillis = SystemClock.uptimeMillis();
        boolean z = this.a.getOrientation() == 0;
        int i = z ? round : 0;
        if (z) {
            round = 0;
        }
        float f2 = z ? this.f : 0.0f;
        float f3 = z ? 0.0f : this.f;
        this.c.scrollBy(i, round);
        a(uptimeMillis, 2, f2, f3);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @UiThread
    public boolean c() {
        if (!this.b.g()) {
            return false;
        }
        this.b.c();
        VelocityTracker velocityTracker = this.d;
        velocityTracker.computeCurrentVelocity(1000, this.e);
        if (this.c.fling((int) velocityTracker.getXVelocity(), (int) velocityTracker.getYVelocity())) {
            return true;
        }
        this.a.c();
        return true;
    }

    private void d() {
        VelocityTracker velocityTracker = this.d;
        if (velocityTracker == null) {
            this.d = VelocityTracker.obtain();
            this.e = ViewConfiguration.get(this.a.getContext()).getScaledMaximumFlingVelocity();
            return;
        }
        velocityTracker.clear();
    }

    private void a(long j, int i, float f, float f2) {
        MotionEvent obtain = MotionEvent.obtain(this.h, j, i, f, f2, 0);
        this.d.addMovement(obtain);
        obtain.recycle();
    }
}
