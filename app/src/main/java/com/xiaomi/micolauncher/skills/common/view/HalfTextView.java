package com.xiaomi.micolauncher.skills.common.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.core.content.ContextCompat;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class HalfTextView extends View implements AsrTextInterface {
    String a;
    float b;
    RectF c;
    private Paint d;
    private Rect e;
    private ValueAnimator f;
    private ValueAnimator g;
    private ObjectAnimator h;
    private float i;
    private long j;
    private long k;
    private float l;
    private Paint m;
    private float n;
    private float o;
    private float p;
    private float q;
    private float r;
    private float s;
    private long t;
    private float u;
    private float v;
    private float w;
    private float x;
    private float y;

    public HalfTextView(Context context) {
        this(context, null);
    }

    public HalfTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HalfTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e = new Rect();
        this.l = 100.0f;
        this.m = new Paint(1);
        this.w = 750.0f;
        a();
    }

    private void a() {
        this.d = new TextPaint();
        this.d.setStyle(Paint.Style.FILL);
        this.d.setAntiAlias(true);
        this.d.setColor(getResources().getColor(R.color.white, null));
        this.d.setTextSize(getResources().getDimensionPixelSize(R.dimen.wakeup_half_asr_text_size));
        this.m.setColor(-1);
        this.c = new RectF();
        this.n = 200.0f;
        this.o = 950.0f;
        this.r = 100.0f;
        this.p = 0.0f;
        this.q = 0.0f;
        this.c = new RectF();
    }

    public void reset() {
        this.u = 0.0f;
        this.x = 0.0f;
        this.v = 0.0f;
        setText("", false);
    }

    public void stop() {
        a();
        this.u = 0.0f;
        this.x = 0.0f;
        this.v = 0.0f;
        invalidate();
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setText(String str, boolean z) {
        setText(str, z, false);
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setText(String str, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str) || !str.equals(this.a)) {
            this.a = str;
            this.d.setColor(ContextCompat.getColor(getContext(), z2 ? R.color.asr_color_fade : R.color.asr_color_normal));
            Paint paint = this.d;
            String str2 = this.a;
            paint.getTextBounds(str2, 0, str2.length(), this.e);
            if (TextUtils.isEmpty(str)) {
                this.x = 100.0f;
                return;
            }
            this.i = this.d.measureText(this.a);
            float f = this.i;
            if (f < 750.0f) {
                this.w = f;
                this.p = 0.0f;
                this.q = 0.0f;
            } else {
                this.w = 750.0f;
                this.p = this.v;
                this.q = f - 750.0f;
            }
            this.v = 0.0f;
            float f2 = this.x;
            if (f2 == 0.0f) {
                this.r = f2 + 100.0f;
            } else {
                this.r = f2;
            }
            float f3 = this.u;
            if (f3 == 0.0f) {
                this.n = f3 + 200.0f;
            } else {
                this.n = f3;
            }
            float f4 = this.w;
            this.o = f4 + 200.0f;
            this.w = f4 + 200.0f + 100.0f;
            this.y = this.w - this.x;
            float f5 = this.y;
            this.s = this.r + f5;
            if (z) {
                this.j = 100L;
                this.t = 3000L;
            } else {
                this.j = Math.abs((f5 / 55.0f) * 70.0f);
                this.t = 0L;
            }
            this.k = Math.abs(((this.q - this.p) * 70.0f) / 55.0f);
            b();
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(this.a)) {
            this.x = 100.0f;
            return;
        }
        this.c.set(0.0f, 0.0f, this.x, 100.0f);
        canvas.save();
        canvas.clipRect(200, 0, getWidth() - 200, 100);
        canvas.translate((-this.v) * 10.0f, 0.0f);
        canvas.drawText(this.a, 200.0f, this.b, this.d);
        canvas.restore();
    }

    public float getBgmove() {
        return this.l;
    }

    @SuppressLint({"AnimatorKeep"})
    public void setBgmove(float f) {
        this.l = f;
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
            this.v = 0.0f;
        }
        ObjectAnimator objectAnimator = this.h;
        if (objectAnimator != null) {
            objectAnimator.end();
        }
        this.f = ValueAnimator.ofFloat(this.n, this.o);
        this.f.setDuration(this.j);
        this.f.setInterpolator(new LinearInterpolator());
        this.f.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$HalfTextView$2ifZwMXQ5c8TJfbkfmwVBN6VGRM
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                HalfTextView.this.c(valueAnimator3);
            }
        });
        this.f.addListener(new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.skills.common.view.HalfTextView.1
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
                if (HalfTextView.this.i > 750.0f) {
                    HalfTextView.this.g.setStartDelay(HalfTextView.this.t);
                    HalfTextView.this.g.start();
                }
            }
        });
        this.h = ObjectAnimator.ofFloat(this, "bgmove", this.r, this.s);
        this.h.setInterpolator(new LinearInterpolator());
        this.h.setDuration(this.j);
        this.h.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$HalfTextView$ywiOwPshx1GJldJ9jfatCAR2u3Q
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                HalfTextView.this.b(valueAnimator3);
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
        this.g = ValueAnimator.ofFloat(this.p / 10.0f, this.q / 10.0f);
        this.g.setDuration(this.k * 3);
        this.g.setInterpolator(new LinearInterpolator());
        this.g.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$HalfTextView$NLT94lEdseUv6FBDP5r6E2N5vfA
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator4) {
                HalfTextView.this.a(valueAnimator4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(ValueAnimator valueAnimator) {
        this.u = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(ValueAnimator valueAnimator) {
        this.x = ((Float) valueAnimator.getAnimatedValue()).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ValueAnimator valueAnimator) {
        this.v = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setText(String str) {
        setText(str, false);
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public float getTextSize() {
        return this.d.getTextSize();
    }
}
