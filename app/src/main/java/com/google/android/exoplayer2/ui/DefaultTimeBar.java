package com.google.android.exoplayer2.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes2.dex */
public class DefaultTimeBar extends View implements TimeBar {
    public static final int BAR_GRAVITY_BOTTOM = 1;
    public static final int BAR_GRAVITY_CENTER = 0;
    public static final int DEFAULT_AD_MARKER_COLOR = -1291845888;
    public static final int DEFAULT_AD_MARKER_WIDTH_DP = 4;
    public static final int DEFAULT_BAR_HEIGHT_DP = 4;
    public static final int DEFAULT_BUFFERED_COLOR = -855638017;
    public static final int DEFAULT_PLAYED_AD_MARKER_COLOR = 872414976;
    public static final int DEFAULT_PLAYED_COLOR = -1;
    public static final int DEFAULT_SCRUBBER_COLOR = -1;
    public static final int DEFAULT_SCRUBBER_DISABLED_SIZE_DP = 0;
    public static final int DEFAULT_SCRUBBER_DRAGGED_SIZE_DP = 16;
    public static final int DEFAULT_SCRUBBER_ENABLED_SIZE_DP = 12;
    public static final int DEFAULT_TOUCH_TARGET_HEIGHT_DP = 26;
    public static final int DEFAULT_UNPLAYED_COLOR = 872415231;
    private int A;
    private long B;
    private int C;
    private Rect D;
    private ValueAnimator E;
    private float F;
    private boolean G;
    private boolean H;
    private long I;
    private long J;
    private long K;
    private long L;
    private int M;
    @Nullable
    private long[] N;
    @Nullable
    private boolean[] O;
    private final Rect a;
    private final Rect b;
    private final Rect c;
    private final Rect d;
    private final Paint e;
    private final Paint f;
    private final Paint g;
    private final Paint h;
    private final Paint i;
    private final Paint j;
    @Nullable
    private final Drawable k;
    private final int l;
    private final int m;
    private final int n;
    private final int o;
    private final int p;
    private final int q;
    private final int r;
    private final int s;
    private final int t;
    private final StringBuilder u;
    private final Formatter v;
    private final Runnable w;
    private final CopyOnWriteArraySet<TimeBar.OnScrubListener> x;
    private final Point y;
    private final float z;

    private static int a(float f, int i) {
        return (int) ((i * f) + 0.5f);
    }

    private static int b(float f, int i) {
        return (int) (i / f);
    }

    public DefaultTimeBar(Context context) {
        this(context, null);
    }

    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, attributeSet);
    }

    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet, int i, @Nullable AttributeSet attributeSet2) {
        this(context, attributeSet, i, attributeSet2, 0);
    }

    public DefaultTimeBar(Context context, @Nullable AttributeSet attributeSet, int i, @Nullable AttributeSet attributeSet2, int i2) {
        super(context, attributeSet, i);
        this.a = new Rect();
        this.b = new Rect();
        this.c = new Rect();
        this.d = new Rect();
        this.e = new Paint();
        this.f = new Paint();
        this.g = new Paint();
        this.h = new Paint();
        this.i = new Paint();
        this.j = new Paint();
        this.j.setAntiAlias(true);
        this.x = new CopyOnWriteArraySet<>();
        this.y = new Point();
        this.z = context.getResources().getDisplayMetrics().density;
        this.t = a(this.z, -50);
        int a = a(this.z, 4);
        int a2 = a(this.z, 26);
        int a3 = a(this.z, 4);
        int a4 = a(this.z, 12);
        int a5 = a(this.z, 0);
        int a6 = a(this.z, 16);
        if (attributeSet2 != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet2, R.styleable.DefaultTimeBar, i, i2);
            try {
                this.k = obtainStyledAttributes.getDrawable(R.styleable.DefaultTimeBar_scrubber_drawable);
                if (this.k != null) {
                    a(this.k);
                    a2 = Math.max(this.k.getMinimumHeight(), a2);
                }
                this.l = obtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_bar_height, a);
                this.m = obtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_touch_target_height, a2);
                this.n = obtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_bar_gravity, 0);
                this.o = obtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_ad_marker_width, a3);
                this.p = obtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_scrubber_enabled_size, a4);
                this.q = obtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_scrubber_disabled_size, a5);
                this.r = obtainStyledAttributes.getDimensionPixelSize(R.styleable.DefaultTimeBar_scrubber_dragged_size, a6);
                int i3 = obtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_played_color, -1);
                int i4 = obtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_scrubber_color, -1);
                int i5 = obtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_buffered_color, DEFAULT_BUFFERED_COLOR);
                int i6 = obtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_unplayed_color, DEFAULT_UNPLAYED_COLOR);
                int i7 = obtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_ad_marker_color, DEFAULT_AD_MARKER_COLOR);
                int i8 = obtainStyledAttributes.getInt(R.styleable.DefaultTimeBar_played_ad_marker_color, DEFAULT_PLAYED_AD_MARKER_COLOR);
                this.e.setColor(i3);
                this.j.setColor(i4);
                this.f.setColor(i5);
                this.g.setColor(i6);
                this.h.setColor(i7);
                this.i.setColor(i8);
            } finally {
                obtainStyledAttributes.recycle();
            }
        } else {
            this.l = a;
            this.m = a2;
            this.n = 0;
            this.o = a3;
            this.p = a4;
            this.q = a5;
            this.r = a6;
            this.e.setColor(-1);
            this.j.setColor(-1);
            this.f.setColor(DEFAULT_BUFFERED_COLOR);
            this.g.setColor(DEFAULT_UNPLAYED_COLOR);
            this.h.setColor(DEFAULT_AD_MARKER_COLOR);
            this.i.setColor(DEFAULT_PLAYED_AD_MARKER_COLOR);
            this.k = null;
        }
        this.u = new StringBuilder();
        this.v = new Formatter(this.u, Locale.getDefault());
        this.w = new Runnable() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$DefaultTimeBar$bZcJeQmFTIdE_lIproAHGmVH_qk
            @Override // java.lang.Runnable
            public final void run() {
                DefaultTimeBar.this.c();
            }
        };
        Drawable drawable = this.k;
        if (drawable != null) {
            this.s = (drawable.getMinimumWidth() + 1) / 2;
        } else {
            this.s = (Math.max(this.q, Math.max(this.p, this.r)) + 1) / 2;
        }
        this.F = 1.0f;
        this.E = new ValueAnimator();
        this.E.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$DefaultTimeBar$eF8k4qIbubvu8DOrzCUfyYLNCZc
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DefaultTimeBar.this.a(valueAnimator);
            }
        });
        this.J = C.TIME_UNSET;
        this.B = C.TIME_UNSET;
        this.A = 20;
        setFocusable(true);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    public /* synthetic */ void c() {
        a(false);
    }

    public /* synthetic */ void a(ValueAnimator valueAnimator) {
        this.F = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate(this.a);
    }

    public void showScrubber() {
        if (this.E.isStarted()) {
            this.E.cancel();
        }
        this.G = false;
        this.F = 1.0f;
        invalidate(this.a);
    }

    public void showScrubber(long j) {
        if (this.E.isStarted()) {
            this.E.cancel();
        }
        this.G = false;
        this.E.setFloatValues(this.F, 1.0f);
        this.E.setDuration(j);
        this.E.start();
    }

    public void hideScrubber(boolean z) {
        if (this.E.isStarted()) {
            this.E.cancel();
        }
        this.G = z;
        this.F = 0.0f;
        invalidate(this.a);
    }

    public void hideScrubber(long j) {
        if (this.E.isStarted()) {
            this.E.cancel();
        }
        this.E.setFloatValues(this.F, 0.0f);
        this.E.setDuration(j);
        this.E.start();
    }

    public void setPlayedColor(@ColorInt int i) {
        this.e.setColor(i);
        invalidate(this.a);
    }

    public void setScrubberColor(@ColorInt int i) {
        this.j.setColor(i);
        invalidate(this.a);
    }

    public void setBufferedColor(@ColorInt int i) {
        this.f.setColor(i);
        invalidate(this.a);
    }

    public void setUnplayedColor(@ColorInt int i) {
        this.g.setColor(i);
        invalidate(this.a);
    }

    public void setAdMarkerColor(@ColorInt int i) {
        this.h.setColor(i);
        invalidate(this.a);
    }

    public void setPlayedAdMarkerColor(@ColorInt int i) {
        this.i.setColor(i);
        invalidate(this.a);
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void addListener(TimeBar.OnScrubListener onScrubListener) {
        Assertions.checkNotNull(onScrubListener);
        this.x.add(onScrubListener);
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void removeListener(TimeBar.OnScrubListener onScrubListener) {
        this.x.remove(onScrubListener);
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setKeyTimeIncrement(long j) {
        Assertions.checkArgument(j > 0);
        this.A = -1;
        this.B = j;
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setKeyCountIncrement(int i) {
        Assertions.checkArgument(i > 0);
        this.A = i;
        this.B = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setPosition(long j) {
        if (this.K != j) {
            this.K = j;
            setContentDescription(getProgressText());
            a();
        }
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setBufferedPosition(long j) {
        if (this.L != j) {
            this.L = j;
            a();
        }
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setDuration(long j) {
        if (this.J != j) {
            this.J = j;
            if (this.H && j == C.TIME_UNSET) {
                a(true);
            }
            a();
        }
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public long getPreferredUpdateDelay() {
        int b = b(this.z, this.b.width());
        if (b != 0) {
            long j = this.J;
            if (!(j == 0 || j == C.TIME_UNSET)) {
                return j / b;
            }
        }
        return Long.MAX_VALUE;
    }

    @Override // com.google.android.exoplayer2.ui.TimeBar
    public void setAdGroupTimesMs(@Nullable long[] jArr, @Nullable boolean[] zArr, int i) {
        Assertions.checkArgument(i == 0 || !(jArr == null || zArr == null));
        this.M = i;
        this.N = jArr;
        this.O = zArr;
        a();
    }

    @Override // android.view.View, com.google.android.exoplayer2.ui.TimeBar
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (this.H && !z) {
            a(true);
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        canvas.save();
        a(canvas);
        b(canvas);
        canvas.restore();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (!isEnabled() || this.J <= 0) {
            return false;
        }
        Point a = a(motionEvent);
        int i = a.x;
        int i2 = a.y;
        switch (motionEvent.getAction()) {
            case 0:
                float f = i;
                if (a(f, i2)) {
                    a(f);
                    a(getScrubberPosition());
                    a();
                    invalidate();
                    return true;
                }
                break;
            case 1:
            case 3:
                if (this.H) {
                    if (motionEvent.getAction() == 3) {
                        z = true;
                    }
                    a(z);
                    return true;
                }
                break;
            case 2:
                if (this.H) {
                    if (i2 < this.t) {
                        int i3 = this.C;
                        a(i3 + ((i - i3) / 3));
                    } else {
                        this.C = i;
                        a(i);
                    }
                    b(getScrubberPosition());
                    a();
                    invalidate();
                    return true;
                }
                break;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001a  */
    @Override // android.view.View, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onKeyDown(int r5, android.view.KeyEvent r6) {
        /*
            r4 = this;
            boolean r0 = r4.isEnabled()
            if (r0 == 0) goto L_0x0030
            long r0 = r4.getPositionIncrement()
            r2 = 66
            r3 = 1
            if (r5 == r2) goto L_0x0027
            switch(r5) {
                case 21: goto L_0x0013;
                case 22: goto L_0x0014;
                case 23: goto L_0x0027;
                default: goto L_0x0012;
            }
        L_0x0012:
            goto L_0x0030
        L_0x0013:
            long r0 = -r0
        L_0x0014:
            boolean r0 = r4.c(r0)
            if (r0 == 0) goto L_0x0030
            java.lang.Runnable r5 = r4.w
            r4.removeCallbacks(r5)
            java.lang.Runnable r5 = r4.w
            r0 = 1000(0x3e8, double:4.94E-321)
            r4.postDelayed(r5, r0)
            return r3
        L_0x0027:
            boolean r0 = r4.H
            if (r0 == 0) goto L_0x0030
            r5 = 0
            r4.a(r5)
            return r3
        L_0x0030:
            boolean r5 = super.onKeyDown(r5, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.DefaultTimeBar.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, @Nullable Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (this.H && !z) {
            a(false);
        }
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        b();
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.k;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == 0) {
            size = this.m;
        } else if (mode != 1073741824) {
            size = Math.min(this.m, size);
        }
        setMeasuredDimension(View.MeasureSpec.getSize(i), size);
        b();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = i3 - i;
        int i8 = i4 - i2;
        int paddingLeft = getPaddingLeft();
        int paddingRight = i7 - getPaddingRight();
        int i9 = this.G ? 0 : this.s;
        if (this.n == 1) {
            i6 = (i8 - getPaddingBottom()) - this.m;
            int i10 = this.l;
            i5 = ((i8 - getPaddingBottom()) - i10) - Math.max(i9 - (i10 / 2), 0);
        } else {
            i6 = (i8 - this.m) / 2;
            i5 = (i8 - this.l) / 2;
        }
        this.a.set(paddingLeft, i6, paddingRight, this.m + i6);
        this.b.set(this.a.left + i9, i5, this.a.right - i9, this.l + i5);
        if (Util.SDK_INT >= 29) {
            a(i7, i8);
        }
        a();
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        Drawable drawable = this.k;
        if (drawable != null && a(drawable, i)) {
            invalidate();
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (accessibilityEvent.getEventType() == 4) {
            accessibilityEvent.getText().add(getProgressText());
        }
        accessibilityEvent.setClassName("android.widget.SeekBar");
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.SeekBar");
        accessibilityNodeInfo.setContentDescription(getProgressText());
        if (this.J > 0) {
            if (Util.SDK_INT >= 21) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                return;
            }
            accessibilityNodeInfo.addAction(4096);
            accessibilityNodeInfo.addAction(8192);
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, @Nullable Bundle bundle) {
        if (super.performAccessibilityAction(i, bundle)) {
            return true;
        }
        if (this.J <= 0) {
            return false;
        }
        if (i == 8192) {
            if (c(-getPositionIncrement())) {
                a(false);
            }
        } else if (i != 4096) {
            return false;
        } else {
            if (c(getPositionIncrement())) {
                a(false);
            }
        }
        sendAccessibilityEvent(4);
        return true;
    }

    private void a(long j) {
        this.I = j;
        this.H = true;
        setPressed(true);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        Iterator<TimeBar.OnScrubListener> it = this.x.iterator();
        while (it.hasNext()) {
            it.next().onScrubStart(this, j);
        }
    }

    private void b(long j) {
        if (this.I != j) {
            this.I = j;
            Iterator<TimeBar.OnScrubListener> it = this.x.iterator();
            while (it.hasNext()) {
                it.next().onScrubMove(this, j);
            }
        }
    }

    private void a(boolean z) {
        removeCallbacks(this.w);
        this.H = false;
        setPressed(false);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
        invalidate();
        Iterator<TimeBar.OnScrubListener> it = this.x.iterator();
        while (it.hasNext()) {
            it.next().onScrubStop(this, this.I, z);
        }
    }

    private boolean c(long j) {
        if (this.J <= 0) {
            return false;
        }
        long j2 = this.H ? this.I : this.K;
        long constrainValue = Util.constrainValue(j2 + j, 0L, this.J);
        if (constrainValue == j2) {
            return false;
        }
        if (!this.H) {
            a(constrainValue);
        } else {
            b(constrainValue);
        }
        a();
        return true;
    }

    private void a() {
        this.c.set(this.b);
        this.d.set(this.b);
        long j = this.H ? this.I : this.K;
        if (this.J > 0) {
            this.c.right = Math.min(this.b.left + ((int) ((this.b.width() * this.L) / this.J)), this.b.right);
            this.d.right = Math.min(this.b.left + ((int) ((this.b.width() * j) / this.J)), this.b.right);
        } else {
            this.c.right = this.b.left;
            this.d.right = this.b.left;
        }
        invalidate(this.a);
    }

    private void a(float f) {
        this.d.right = Util.constrainValue((int) f, this.b.left, this.b.right);
    }

    private Point a(MotionEvent motionEvent) {
        this.y.set((int) motionEvent.getX(), (int) motionEvent.getY());
        return this.y;
    }

    private long getScrubberPosition() {
        if (this.b.width() <= 0 || this.J == C.TIME_UNSET) {
            return 0L;
        }
        return (this.d.width() * this.J) / this.b.width();
    }

    private boolean a(float f, float f2) {
        return this.a.contains((int) f, (int) f2);
    }

    private void a(Canvas canvas) {
        int height = this.b.height();
        int centerY = this.b.centerY() - (height / 2);
        int i = height + centerY;
        if (this.J <= 0) {
            canvas.drawRect(this.b.left, centerY, this.b.right, i, this.g);
            return;
        }
        int i2 = this.c.left;
        int i3 = this.c.right;
        int max = Math.max(Math.max(this.b.left, i3), this.d.right);
        if (max < this.b.right) {
            canvas.drawRect(max, centerY, this.b.right, i, this.g);
        }
        int max2 = Math.max(i2, this.d.right);
        if (i3 > max2) {
            canvas.drawRect(max2, centerY, i3, i, this.f);
        }
        if (this.d.width() > 0) {
            canvas.drawRect(this.d.left, centerY, this.d.right, i, this.e);
        }
        if (this.M != 0) {
            long[] jArr = (long[]) Assertions.checkNotNull(this.N);
            boolean[] zArr = (boolean[]) Assertions.checkNotNull(this.O);
            int i4 = this.o / 2;
            for (int i5 = 0; i5 < this.M; i5++) {
                int min = this.b.left + Math.min(this.b.width() - this.o, Math.max(0, ((int) ((this.b.width() * Util.constrainValue(jArr[i5], 0L, this.J)) / this.J)) - i4));
                canvas.drawRect(min, centerY, min + this.o, i, zArr[i5] ? this.i : this.h);
            }
        }
    }

    private void b(Canvas canvas) {
        int i;
        if (this.J > 0) {
            int constrainValue = Util.constrainValue(this.d.right, this.d.left, this.b.right);
            int centerY = this.d.centerY();
            Drawable drawable = this.k;
            if (drawable == null) {
                if (this.H || isFocused()) {
                    i = this.r;
                } else {
                    i = isEnabled() ? this.p : this.q;
                }
                canvas.drawCircle(constrainValue, centerY, (int) ((i * this.F) / 2.0f), this.j);
                return;
            }
            int intrinsicWidth = ((int) (drawable.getIntrinsicWidth() * this.F)) / 2;
            int intrinsicHeight = ((int) (this.k.getIntrinsicHeight() * this.F)) / 2;
            this.k.setBounds(constrainValue - intrinsicWidth, centerY - intrinsicHeight, constrainValue + intrinsicWidth, centerY + intrinsicHeight);
            this.k.draw(canvas);
        }
    }

    private void b() {
        Drawable drawable = this.k;
        if (drawable != null && drawable.isStateful() && this.k.setState(getDrawableState())) {
            invalidate();
        }
    }

    @RequiresApi(29)
    private void a(int i, int i2) {
        Rect rect = this.D;
        if (rect == null || rect.width() != i || this.D.height() != i2) {
            this.D = new Rect(0, 0, i, i2);
            setSystemGestureExclusionRects(Collections.singletonList(this.D));
        }
    }

    private String getProgressText() {
        return Util.getStringForTime(this.u, this.v, this.K);
    }

    private long getPositionIncrement() {
        long j = this.B;
        if (j != C.TIME_UNSET) {
            return j;
        }
        long j2 = this.J;
        if (j2 == C.TIME_UNSET) {
            return 0L;
        }
        return j2 / this.A;
    }

    private boolean a(Drawable drawable) {
        return Util.SDK_INT >= 23 && a(drawable, getLayoutDirection());
    }

    private static boolean a(Drawable drawable, int i) {
        return Util.SDK_INT >= 23 && drawable.setLayoutDirection(i);
    }
}
