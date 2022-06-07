package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: FastScroller.java */
@VisibleForTesting
/* loaded from: classes.dex */
class d extends RecyclerView.ItemDecoration implements RecyclerView.OnItemTouchListener {
    private static final int[] k = {16842919};
    private static final int[] l = new int[0];
    final StateListDrawable a;
    final Drawable b;
    @VisibleForTesting
    int c;
    @VisibleForTesting
    int d;
    @VisibleForTesting
    float e;
    @VisibleForTesting
    int f;
    @VisibleForTesting
    int g;
    @VisibleForTesting
    float h;
    private final int m;
    private final int n;
    private final int o;
    private final int p;
    private final StateListDrawable q;
    private final Drawable r;
    private final int s;
    private final int t;
    private RecyclerView w;
    private int u = 0;
    private int v = 0;
    private boolean x = false;
    private boolean y = false;
    private int z = 0;
    private int A = 0;
    private final int[] B = new int[2];
    private final int[] C = new int[2];
    final ValueAnimator i = ValueAnimator.ofFloat(0.0f, 1.0f);
    int j = 0;
    private final Runnable D = new Runnable() { // from class: androidx.recyclerview.widget.d.1
        @Override // java.lang.Runnable
        public void run() {
            d.this.b(500);
        }
    };
    private final RecyclerView.OnScrollListener E = new RecyclerView.OnScrollListener() { // from class: androidx.recyclerview.widget.d.2
        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            d.this.a(recyclerView.computeHorizontalScrollOffset(), recyclerView.computeVerticalScrollOffset());
        }
    };

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int i, int i2, int i3) {
        this.a = stateListDrawable;
        this.b = drawable;
        this.q = stateListDrawable2;
        this.r = drawable2;
        this.o = Math.max(i, stateListDrawable.getIntrinsicWidth());
        this.p = Math.max(i, drawable.getIntrinsicWidth());
        this.s = Math.max(i, stateListDrawable2.getIntrinsicWidth());
        this.t = Math.max(i, drawable2.getIntrinsicWidth());
        this.m = i2;
        this.n = i3;
        this.a.setAlpha(255);
        this.b.setAlpha(255);
        this.i.addListener(new a());
        this.i.addUpdateListener(new b());
        a(recyclerView);
    }

    public void a(@Nullable RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.w;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                d();
            }
            this.w = recyclerView;
            if (this.w != null) {
                c();
            }
        }
    }

    private void c() {
        this.w.addItemDecoration(this);
        this.w.addOnItemTouchListener(this);
        this.w.addOnScrollListener(this.E);
    }

    private void d() {
        this.w.removeItemDecoration(this);
        this.w.removeOnItemTouchListener(this);
        this.w.removeOnScrollListener(this.E);
        f();
    }

    void a() {
        this.w.invalidate();
    }

    void a(int i) {
        if (i == 2 && this.z != 2) {
            this.a.setState(k);
            f();
        }
        if (i == 0) {
            a();
        } else {
            b();
        }
        if (this.z == 2 && i != 2) {
            this.a.setState(l);
            c(1200);
        } else if (i == 1) {
            c(1500);
        }
        this.z = i;
    }

    private boolean e() {
        return ViewCompat.getLayoutDirection(this.w) == 1;
    }

    public void b() {
        int i = this.j;
        if (i != 0) {
            if (i == 3) {
                this.i.cancel();
            } else {
                return;
            }
        }
        this.j = 1;
        ValueAnimator valueAnimator = this.i;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f);
        this.i.setDuration(500L);
        this.i.setStartDelay(0L);
        this.i.start();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @VisibleForTesting
    void b(int i) {
        switch (this.j) {
            case 1:
                this.i.cancel();
                break;
            case 2:
                break;
            default:
                return;
        }
        this.j = 3;
        ValueAnimator valueAnimator = this.i;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f);
        this.i.setDuration(i);
        this.i.start();
    }

    private void f() {
        this.w.removeCallbacks(this.D);
    }

    private void c(int i) {
        f();
        this.w.postDelayed(this.D, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.u != this.w.getWidth() || this.v != this.w.getHeight()) {
            this.u = this.w.getWidth();
            this.v = this.w.getHeight();
            a(0);
        } else if (this.j != 0) {
            if (this.x) {
                a(canvas);
            }
            if (this.y) {
                b(canvas);
            }
        }
    }

    private void a(Canvas canvas) {
        int i = this.u;
        int i2 = this.o;
        int i3 = i - i2;
        int i4 = this.d;
        int i5 = this.c;
        int i6 = i4 - (i5 / 2);
        this.a.setBounds(0, 0, i2, i5);
        this.b.setBounds(0, 0, this.p, this.v);
        if (e()) {
            this.b.draw(canvas);
            canvas.translate(this.o, i6);
            canvas.scale(-1.0f, 1.0f);
            this.a.draw(canvas);
            canvas.scale(-1.0f, 1.0f);
            canvas.translate(-this.o, -i6);
            return;
        }
        canvas.translate(i3, 0.0f);
        this.b.draw(canvas);
        canvas.translate(0.0f, i6);
        this.a.draw(canvas);
        canvas.translate(-i3, -i6);
    }

    private void b(Canvas canvas) {
        int i = this.v;
        int i2 = this.s;
        int i3 = i - i2;
        int i4 = this.g;
        int i5 = this.f;
        int i6 = i4 - (i5 / 2);
        this.q.setBounds(0, 0, i5, i2);
        this.r.setBounds(0, 0, this.u, this.t);
        canvas.translate(0.0f, i3);
        this.r.draw(canvas);
        canvas.translate(i6, 0.0f);
        this.q.draw(canvas);
        canvas.translate(-i6, -i3);
    }

    void a(int i, int i2) {
        int computeVerticalScrollRange = this.w.computeVerticalScrollRange();
        int i3 = this.v;
        this.x = computeVerticalScrollRange - i3 > 0 && i3 >= this.m;
        int computeHorizontalScrollRange = this.w.computeHorizontalScrollRange();
        int i4 = this.u;
        this.y = computeHorizontalScrollRange - i4 > 0 && i4 >= this.m;
        if (this.x || this.y) {
            if (this.x) {
                float f = i3;
                this.d = (int) ((f * (i2 + (f / 2.0f))) / computeVerticalScrollRange);
                this.c = Math.min(i3, (i3 * i3) / computeVerticalScrollRange);
            }
            if (this.y) {
                float f2 = i4;
                this.g = (int) ((f2 * (i + (f2 / 2.0f))) / computeHorizontalScrollRange);
                this.f = Math.min(i4, (i4 * i4) / computeHorizontalScrollRange);
            }
            int i5 = this.z;
            if (i5 == 0 || i5 == 1) {
                a(1);
            }
        } else if (this.z != 0) {
            a(0);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        int i = this.z;
        if (i != 1) {
            return i == 2;
        }
        boolean a2 = a(motionEvent.getX(), motionEvent.getY());
        boolean b2 = b(motionEvent.getX(), motionEvent.getY());
        if (motionEvent.getAction() != 0) {
            return false;
        }
        if (!a2 && !b2) {
            return false;
        }
        if (b2) {
            this.A = 1;
            this.h = (int) motionEvent.getX();
        } else if (a2) {
            this.A = 2;
            this.e = (int) motionEvent.getY();
        }
        a(2);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        if (this.z != 0) {
            if (motionEvent.getAction() == 0) {
                boolean a2 = a(motionEvent.getX(), motionEvent.getY());
                boolean b2 = b(motionEvent.getX(), motionEvent.getY());
                if (a2 || b2) {
                    if (b2) {
                        this.A = 1;
                        this.h = (int) motionEvent.getX();
                    } else if (a2) {
                        this.A = 2;
                        this.e = (int) motionEvent.getY();
                    }
                    a(2);
                }
            } else if (motionEvent.getAction() == 1 && this.z == 2) {
                this.e = 0.0f;
                this.h = 0.0f;
                a(1);
                this.A = 0;
            } else if (motionEvent.getAction() == 2 && this.z == 2) {
                b();
                if (this.A == 1) {
                    b(motionEvent.getX());
                }
                if (this.A == 2) {
                    a(motionEvent.getY());
                }
            }
        }
    }

    private void a(float f) {
        int[] g = g();
        float max = Math.max(g[0], Math.min(g[1], f));
        if (Math.abs(this.d - max) >= 2.0f) {
            int a2 = a(this.e, max, g, this.w.computeVerticalScrollRange(), this.w.computeVerticalScrollOffset(), this.v);
            if (a2 != 0) {
                this.w.scrollBy(0, a2);
            }
            this.e = max;
        }
    }

    private void b(float f) {
        int[] h = h();
        float max = Math.max(h[0], Math.min(h[1], f));
        if (Math.abs(this.g - max) >= 2.0f) {
            int a2 = a(this.h, max, h, this.w.computeHorizontalScrollRange(), this.w.computeHorizontalScrollOffset(), this.u);
            if (a2 != 0) {
                this.w.scrollBy(a2, 0);
            }
            this.h = max;
        }
    }

    private int a(float f, float f2, int[] iArr, int i, int i2, int i3) {
        int i4 = iArr[1] - iArr[0];
        if (i4 == 0) {
            return 0;
        }
        int i5 = i - i3;
        int i6 = (int) (((f2 - f) / i4) * i5);
        int i7 = i2 + i6;
        if (i7 >= i5 || i7 < 0) {
            return 0;
        }
        return i6;
    }

    @VisibleForTesting
    boolean a(float f, float f2) {
        if (!e() ? f >= this.u - this.o : f <= this.o) {
            int i = this.d;
            int i2 = this.c;
            if (f2 >= i - (i2 / 2) && f2 <= i + (i2 / 2)) {
                return true;
            }
        }
        return false;
    }

    @VisibleForTesting
    boolean b(float f, float f2) {
        if (f2 >= this.v - this.s) {
            int i = this.g;
            int i2 = this.f;
            if (f >= i - (i2 / 2) && f <= i + (i2 / 2)) {
                return true;
            }
        }
        return false;
    }

    private int[] g() {
        int[] iArr = this.B;
        int i = this.n;
        iArr[0] = i;
        iArr[1] = this.v - i;
        return iArr;
    }

    private int[] h() {
        int[] iArr = this.C;
        int i = this.n;
        iArr[0] = i;
        iArr[1] = this.u - i;
        return iArr;
    }

    /* compiled from: FastScroller.java */
    /* loaded from: classes.dex */
    private class a extends AnimatorListenerAdapter {
        private boolean b = false;

        a() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (this.b) {
                this.b = false;
            } else if (((Float) d.this.i.getAnimatedValue()).floatValue() == 0.0f) {
                d dVar = d.this;
                dVar.j = 0;
                dVar.a(0);
            } else {
                d dVar2 = d.this;
                dVar2.j = 2;
                dVar2.a();
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.b = true;
        }
    }

    /* compiled from: FastScroller.java */
    /* loaded from: classes.dex */
    private class b implements ValueAnimator.AnimatorUpdateListener {
        b() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int floatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            d.this.a.setAlpha(floatValue);
            d.this.b.setAlpha(floatValue);
            d.this.a();
        }
    }
}
