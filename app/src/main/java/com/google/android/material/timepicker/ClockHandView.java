package com.google.android.material.timepicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.Dimension;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class ClockHandView extends View {
    private ValueAnimator a;
    private boolean b;
    private float c;
    private float d;
    private boolean e;
    private int f;
    private final List<OnRotateListener> g;
    private final int h;
    private final float i;
    private final Paint j;
    private final RectF k;
    @Px
    private final int l;
    private float m;
    private boolean n;
    private OnActionUpListener o;
    private double p;
    private int q;

    /* loaded from: classes2.dex */
    public interface OnActionUpListener {
        void onActionUp(@FloatRange(from = 0.0d, to = 360.0d) float f, boolean z);
    }

    /* loaded from: classes2.dex */
    public interface OnRotateListener {
        void onRotate(@FloatRange(from = 0.0d, to = 360.0d) float f, boolean z);
    }

    public ClockHandView(Context context) {
        this(context, null);
    }

    public ClockHandView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialClockStyle);
    }

    public ClockHandView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.g = new ArrayList();
        this.j = new Paint();
        this.k = new RectF();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ClockHandView, i, R.style.Widget_MaterialComponents_TimePicker_Clock);
        this.q = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ClockHandView_materialCircleRadius, 0);
        this.h = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ClockHandView_selectorSize, 0);
        Resources resources = getResources();
        this.l = resources.getDimensionPixelSize(R.dimen.material_clock_hand_stroke_width);
        this.i = resources.getDimensionPixelSize(R.dimen.material_clock_hand_center_dot_radius);
        int color = obtainStyledAttributes.getColor(R.styleable.ClockHandView_clockHandColor, 0);
        this.j.setAntiAlias(true);
        this.j.setColor(color);
        a(0.0f);
        this.f = ViewConfiguration.get(context).getScaledTouchSlop();
        ViewCompat.setImportantForAccessibility(this, 2);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        a(a());
    }

    public void a(@FloatRange(from = 0.0d, to = 360.0d) float f) {
        a(f, false);
    }

    public void a(@FloatRange(from = 0.0d, to = 360.0d) float f, boolean z) {
        ValueAnimator valueAnimator = this.a;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (!z) {
            b(f, false);
            return;
        }
        Pair<Float, Float> b = b(f);
        this.a = ValueAnimator.ofFloat(((Float) b.first).floatValue(), ((Float) b.second).floatValue());
        this.a.setDuration(200L);
        this.a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.timepicker.ClockHandView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ClockHandView.this.b(((Float) valueAnimator2.getAnimatedValue()).floatValue(), true);
            }
        });
        this.a.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.timepicker.ClockHandView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                animator.end();
            }
        });
        this.a.start();
    }

    private Pair<Float, Float> b(float f) {
        float a = a();
        if (Math.abs(a - f) > 180.0f) {
            if (a > 180.0f && f < 180.0f) {
                f += 360.0f;
            }
            if (a < 180.0f && f > 180.0f) {
                a += 360.0f;
            }
        }
        return new Pair<>(Float.valueOf(a), Float.valueOf(f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(@FloatRange(from = 0.0d, to = 360.0d) float f, boolean z) {
        float f2 = f % 360.0f;
        this.m = f2;
        this.p = Math.toRadians(this.m - 90.0f);
        float width = (getWidth() / 2) + (this.q * ((float) Math.cos(this.p)));
        float height = (getHeight() / 2) + (this.q * ((float) Math.sin(this.p)));
        RectF rectF = this.k;
        int i = this.h;
        rectF.set(width - i, height - i, width + i, height + i);
        for (OnRotateListener onRotateListener : this.g) {
            onRotateListener.onRotate(f2, z);
        }
        invalidate();
    }

    public void a(boolean z) {
        this.b = z;
    }

    public void a(OnRotateListener onRotateListener) {
        this.g.add(onRotateListener);
    }

    public void a(OnActionUpListener onActionUpListener) {
        this.o = onActionUpListener;
    }

    @FloatRange(from = 0.0d, to = 360.0d)
    public float a() {
        return this.m;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        a(canvas);
    }

    private void a(Canvas canvas) {
        int height = getHeight() / 2;
        int width = getWidth() / 2;
        float f = width;
        float f2 = height;
        this.j.setStrokeWidth(0.0f);
        canvas.drawCircle((this.q * ((float) Math.cos(this.p))) + f, (this.q * ((float) Math.sin(this.p))) + f2, this.h, this.j);
        double sin = Math.sin(this.p);
        double cos = Math.cos(this.p);
        this.j.setStrokeWidth(this.l);
        canvas.drawLine(f, f2, width + ((int) (cos * r6)), height + ((int) (r6 * sin)), this.j);
        canvas.drawCircle(f, f2, this.i, this.j);
    }

    public RectF b() {
        return this.k;
    }

    public int c() {
        return this.h;
    }

    public void a(@Dimension int i) {
        this.q = i;
        invalidate();
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        boolean z3;
        OnActionUpListener onActionUpListener;
        int actionMasked = motionEvent.getActionMasked();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (actionMasked) {
            case 0:
                this.c = x;
                this.d = y;
                this.e = true;
                this.n = false;
                z3 = false;
                z2 = false;
                z = true;
                break;
            case 1:
            case 2:
                int i = (int) (x - this.c);
                int i2 = (int) (y - this.d);
                this.e = (i * i) + (i2 * i2) > this.f;
                boolean z4 = this.n;
                z3 = actionMasked == 1;
                z = false;
                z2 = z4;
                break;
            default:
                z3 = false;
                z2 = false;
                z = false;
                break;
        }
        this.n = a(x, y, z2, z, z3) | this.n;
        if (this.n && z3 && (onActionUpListener = this.o) != null) {
            onActionUpListener.onActionUp(a(x, y), this.e);
        }
        return true;
    }

    private boolean a(float f, float f2, boolean z, boolean z2, boolean z3) {
        float a = a(f, f2);
        boolean z4 = false;
        boolean z5 = a() != a;
        if (z2 && z5) {
            return true;
        }
        if (!z5 && !z) {
            return false;
        }
        if (z3 && this.b) {
            z4 = true;
        }
        a(a, z4);
        return true;
    }

    private int a(float f, float f2) {
        int degrees = ((int) Math.toDegrees(Math.atan2(f2 - (getHeight() / 2), f - (getWidth() / 2)))) + 90;
        return degrees < 0 ? degrees + 360 : degrees;
    }
}
