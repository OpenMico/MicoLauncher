package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: HeaderBehavior.java */
/* loaded from: classes2.dex */
public abstract class a<V extends View> extends c<V> {
    OverScroller a;
    @Nullable
    private Runnable b;
    private boolean c;
    private int e;
    @Nullable
    private VelocityTracker g;
    private int d = -1;
    private int f = -1;

    void a(CoordinatorLayout coordinatorLayout, V v) {
    }

    boolean c(V v) {
        return false;
    }

    public a() {
    }

    public a(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull MotionEvent motionEvent) {
        int findPointerIndex;
        if (this.f < 0) {
            this.f = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getActionMasked() == 2 && this.c) {
            int i = this.d;
            if (i == -1 || (findPointerIndex = motionEvent.findPointerIndex(i)) == -1) {
                return false;
            }
            int y = (int) motionEvent.getY(findPointerIndex);
            if (Math.abs(y - this.e) > this.f) {
                this.e = y;
                return true;
            }
        }
        if (motionEvent.getActionMasked() == 0) {
            this.d = -1;
            int x = (int) motionEvent.getX();
            int y2 = (int) motionEvent.getY();
            this.c = c(v) && coordinatorLayout.isPointInChildBounds(v, x, y2);
            if (this.c) {
                this.e = y2;
                this.d = motionEvent.getPointerId(0);
                b();
                OverScroller overScroller = this.a;
                if (overScroller != null && !overScroller.isFinished()) {
                    this.a.abortAnimation();
                    return true;
                }
            }
        }
        VelocityTracker velocityTracker = this.g;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEvent);
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0081  */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(@androidx.annotation.NonNull androidx.coordinatorlayout.widget.CoordinatorLayout r12, @androidx.annotation.NonNull V r13, @androidx.annotation.NonNull android.view.MotionEvent r14) {
        /*
            r11 = this;
            int r0 = r14.getActionMasked()
            r1 = 6
            r2 = 1
            r3 = 0
            if (r0 == r1) goto L_0x0063
            r1 = -1
            switch(r0) {
                case 1: goto L_0x002f;
                case 2: goto L_0x000f;
                case 3: goto L_0x0053;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x007c
        L_0x000f:
            int r0 = r11.d
            int r0 = r14.findPointerIndex(r0)
            if (r0 != r1) goto L_0x0018
            return r3
        L_0x0018:
            float r0 = r14.getY(r0)
            int r0 = (int) r0
            int r1 = r11.e
            int r7 = r1 - r0
            r11.e = r0
            int r8 = r11.b(r13)
            r9 = 0
            r4 = r11
            r5 = r12
            r6 = r13
            r4.b(r5, r6, r7, r8, r9)
            goto L_0x007c
        L_0x002f:
            android.view.VelocityTracker r0 = r11.g
            if (r0 == 0) goto L_0x0053
            r0.addMovement(r14)
            android.view.VelocityTracker r0 = r11.g
            r4 = 1000(0x3e8, float:1.401E-42)
            r0.computeCurrentVelocity(r4)
            android.view.VelocityTracker r0 = r11.g
            int r4 = r11.d
            float r10 = r0.getYVelocity(r4)
            int r0 = r11.a(r13)
            int r8 = -r0
            r9 = 0
            r5 = r11
            r6 = r12
            r7 = r13
            r5.a(r6, r7, r8, r9, r10)
            r12 = r2
            goto L_0x0054
        L_0x0053:
            r12 = r3
        L_0x0054:
            r11.c = r3
            r11.d = r1
            android.view.VelocityTracker r13 = r11.g
            if (r13 == 0) goto L_0x007d
            r13.recycle()
            r13 = 0
            r11.g = r13
            goto L_0x007d
        L_0x0063:
            int r12 = r14.getActionIndex()
            if (r12 != 0) goto L_0x006b
            r12 = r2
            goto L_0x006c
        L_0x006b:
            r12 = r3
        L_0x006c:
            int r13 = r14.getPointerId(r12)
            r11.d = r13
            float r12 = r14.getY(r12)
            r13 = 1056964608(0x3f000000, float:0.5)
            float r12 = r12 + r13
            int r12 = (int) r12
            r11.e = r12
        L_0x007c:
            r12 = r3
        L_0x007d:
            android.view.VelocityTracker r13 = r11.g
            if (r13 == 0) goto L_0x0084
            r13.addMovement(r14)
        L_0x0084:
            boolean r13 = r11.c
            if (r13 != 0) goto L_0x008c
            if (r12 == 0) goto L_0x008b
            goto L_0x008c
        L_0x008b:
            r2 = r3
        L_0x008c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.a.onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    int a(CoordinatorLayout coordinatorLayout, V v, int i) {
        return a(coordinatorLayout, (CoordinatorLayout) v, i, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    int a(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
        int clamp;
        int topAndBottomOffset = getTopAndBottomOffset();
        if (i2 == 0 || topAndBottomOffset < i2 || topAndBottomOffset > i3 || topAndBottomOffset == (clamp = MathUtils.clamp(i, i2, i3))) {
            return 0;
        }
        setTopAndBottomOffset(clamp);
        return topAndBottomOffset - clamp;
    }

    int a() {
        return getTopAndBottomOffset();
    }

    final int b(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
        return a(coordinatorLayout, (CoordinatorLayout) v, a() - i, i2, i3);
    }

    final boolean a(CoordinatorLayout coordinatorLayout, @NonNull V v, int i, int i2, float f) {
        Runnable runnable = this.b;
        if (runnable != null) {
            v.removeCallbacks(runnable);
            this.b = null;
        }
        if (this.a == null) {
            this.a = new OverScroller(v.getContext());
        }
        this.a.fling(0, getTopAndBottomOffset(), 0, Math.round(f), 0, 0, i, i2);
        if (this.a.computeScrollOffset()) {
            this.b = new RunnableC0075a(coordinatorLayout, v);
            ViewCompat.postOnAnimation(v, this.b);
            return true;
        }
        a(coordinatorLayout, v);
        return false;
    }

    int b(@NonNull V v) {
        return -v.getHeight();
    }

    int a(@NonNull V v) {
        return v.getHeight();
    }

    private void b() {
        if (this.g == null) {
            this.g = VelocityTracker.obtain();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Incorrect field signature: TV; */
    /* compiled from: HeaderBehavior.java */
    /* renamed from: com.google.android.material.appbar.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public class RunnableC0075a implements Runnable {
        private final CoordinatorLayout b;
        private final View c;

        RunnableC0075a(CoordinatorLayout coordinatorLayout, V v) {
            this.b = coordinatorLayout;
            this.c = v;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            if (this.c != null && a.this.a != null) {
                if (a.this.a.computeScrollOffset()) {
                    a aVar = a.this;
                    aVar.a(this.b, this.c, aVar.a.getCurrY());
                    ViewCompat.postOnAnimation(this.c, this);
                    return;
                }
                a.this.a(this.b, this.c);
            }
        }
    }
}
