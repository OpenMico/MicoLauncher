package com.xiaomi.micolauncher.skills.common.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.core.content.ContextCompat;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class WakeupTextView extends View {
    private float A;
    private float B;
    private float C;
    private float D;
    String a;
    float b;
    RectF c;
    private Paint d;
    private Rect e;
    private ValueAnimator f;
    private ValueAnimator g;
    private ObjectAnimator h;
    private float i;
    private final int j;
    private long k;
    private long l;
    private final int m;
    private final int n;
    private final int o;
    private final int p;
    private float q;
    private Paint r;
    private float s;
    private float t;
    private float u;
    private float v;
    private float w;
    private float x;
    private long y;
    private float z;

    public WakeupTextView(Context context) {
        this(context, null);
    }

    public WakeupTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WakeupTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e = new Rect();
        this.j = DisplayUtils.dip2px(getContext(), getResources().getDimension(R.dimen.wakeup_max_text_width));
        this.m = DisplayUtils.dip2px(getContext(), getResources().getDimension(R.dimen.wakeup_text_margin_right));
        this.n = DisplayUtils.dip2px(getContext(), getResources().getDimension(R.dimen.wakeup_text_margin_left));
        this.o = DisplayUtils.dip2px(getContext(), getResources().getDimension(R.dimen.wakeup_height));
        this.p = this.o / 2;
        this.q = DisplayUtils.dip2px(getContext(), getResources().getDimension(R.dimen.wakeup_height));
        this.r = new Paint(1);
        this.B = this.j;
        a();
    }

    private void a() {
        this.d = new TextPaint();
        this.d.setStyle(Paint.Style.FILL);
        this.d.setAntiAlias(true);
        this.d.setColor(Color.parseColor("#272727"));
        this.d.setTextSize(DisplayUtils.dip2px(getContext(), getResources().getDimension(R.dimen.wake_guide_wakeup_tv_size)));
        this.r.setColor(-1);
        this.c = new RectF();
        int i = this.m;
        this.s = i;
        this.t = i + this.j;
        this.w = this.o;
        this.u = 0.0f;
        this.v = 0.0f;
        this.c = new RectF();
    }

    public void reset() {
        this.z = 0.0f;
        this.C = 0.0f;
        this.A = 0.0f;
        setText("", false, true, false);
    }

    public void stop() {
        a();
        this.z = 0.0f;
        this.C = 0.0f;
        this.A = 0.0f;
        invalidate();
    }

    public void setText(String str, boolean z) {
        setText(str, z, false, false);
    }

    public void setText(String str, boolean z, boolean z2, boolean z3) {
        if (z3 || TextUtils.isEmpty(str) || !str.equals(this.a)) {
            this.d.setColor(ContextCompat.getColor(getContext(), z3 ? R.color.asr_color_fade : R.color.asr_color_normal));
            this.a = !z3 ? str : this.a;
            String str2 = this.a;
            if (str2 != null) {
                this.d.getTextBounds(str2, 0, str2.length(), this.e);
                if (z3 || !TextUtils.isEmpty(str)) {
                    this.i = this.d.measureText(this.a);
                    float f = this.i;
                    int i = this.j;
                    if (f < i) {
                        this.B = f;
                        this.u = 0.0f;
                        this.v = 0.0f;
                    } else {
                        this.B = i;
                        this.u = this.A;
                        this.v = f - i;
                    }
                    this.A = 0.0f;
                    float f2 = this.C;
                    if (f2 == 0.0f) {
                        this.w = this.o + f2;
                    } else {
                        this.w = f2;
                    }
                    float f3 = this.z;
                    if (f3 == 0.0f) {
                        this.s = this.m + f3;
                    } else {
                        this.s = f3;
                    }
                    float f4 = this.B;
                    this.t = this.m + f4;
                    this.B = f4 + this.n + this.o;
                    this.D = this.B - this.C;
                    float f5 = this.D;
                    this.x = this.w + f5;
                    if (z) {
                        this.k = 100L;
                        this.y = 3000L;
                    } else {
                        if (f5 < 0.0f) {
                            this.k = 0L;
                        } else {
                            this.k = Math.abs((f5 / 55.0f) * 70.0f);
                        }
                        this.y = 0L;
                    }
                    this.l = ((this.v - this.u) * 70.0f) / 55.0f;
                    if (!z2) {
                        this.k = 0L;
                        this.l = 0L;
                    }
                    b();
                    invalidate();
                    return;
                }
                this.C = this.o;
                invalidate();
            }
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(this.a)) {
            this.C = this.o;
        }
        this.c.set(0.0f, 0.0f, this.C, this.o);
        canvas.save();
        RectF rectF = this.c;
        int i = this.p;
        canvas.drawRoundRect(rectF, i, i, this.r);
        canvas.clipRect(this.m, 0.0f, this.z, this.o);
        canvas.translate((-this.A) * 10.0f, 0.0f);
        canvas.drawText(this.a, this.m, this.b, this.d);
        canvas.restore();
    }

    public float getBgmove() {
        return this.q;
    }

    @SuppressLint({"AnimatorKeep"})
    public void setBgmove(float f) {
        this.q = f;
        invalidate();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.b = (getHeight() / 2.0f) + (Math.abs(this.d.ascent() + this.d.descent()) / 2.0f);
    }

    private void b() {
        ValueAnimator valueAnimator = this.f;
        if (valueAnimator != null) {
            valueAnimator.end();
        }
        ValueAnimator valueAnimator2 = this.g;
        if (valueAnimator2 != null) {
            valueAnimator2.end();
            this.A = 0.0f;
        }
        ObjectAnimator objectAnimator = this.h;
        if (objectAnimator != null) {
            objectAnimator.end();
        }
        this.f = ValueAnimator.ofFloat(this.s, this.t);
        this.f.setDuration(this.k);
        this.f.setInterpolator(new LinearInterpolator());
        this.f.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$WakeupTextView$nft7P0so7-G-TxEzVo12ryEdvNc
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                WakeupTextView.this.c(valueAnimator3);
            }
        });
        this.f.addListener(new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.skills.common.view.WakeupTextView.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (WakeupTextView.this.i > WakeupTextView.this.j) {
                    WakeupTextView.this.g.setStartDelay(WakeupTextView.this.y);
                    WakeupTextView.this.g.start();
                }
            }
        });
        this.h = ObjectAnimator.ofFloat(this, "bgmove", this.w, this.x);
        this.h.setInterpolator(new LinearInterpolator());
        this.h.setDuration(this.k);
        this.h.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$WakeupTextView$n3pX37iSCJJc50YU1VqG7fczXFs
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                WakeupTextView.this.b(valueAnimator3);
            }
        });
        ObjectAnimator objectAnimator2 = this.h;
        if (objectAnimator2 != null) {
            objectAnimator2.start();
        }
        ValueAnimator valueAnimator3 = this.f;
        if (valueAnimator3 != null) {
            valueAnimator3.start();
        }
        this.g = ValueAnimator.ofFloat(this.u / 10.0f, this.v / 10.0f);
        this.g.setDuration(this.l * 3);
        this.g.setInterpolator(new LinearInterpolator());
        this.g.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$WakeupTextView$vMlrhBwm9lw29XTTPbWypnDCwx8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator4) {
                WakeupTextView.this.a(valueAnimator4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(ValueAnimator valueAnimator) {
        this.z = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(ValueAnimator valueAnimator) {
        this.C = ((Float) valueAnimator.getAnimatedValue()).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ValueAnimator valueAnimator) {
        this.A = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }
}
