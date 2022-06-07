package com.scwang.smart.refresh.header.material;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class MaterialProgressDrawable extends Drawable implements Animatable {
    public static final byte DEFAULT = 1;
    public static final byte LARGE = 0;
    float b;
    boolean c;
    private final List<Animation> f = new ArrayList();
    private final a g = new a();
    private float h;
    private View i;
    private Animation j;
    private float k;
    private float l;
    private static final Interpolator d = new LinearInterpolator();
    static final Interpolator a = new FastOutSlowInInterpolator();
    private static final int[] e = {ViewCompat.MEASURED_STATE_MASK};

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface ProgressDrawableSize {
    }

    private int a(float f, int i, int i2) {
        int i3 = (i >> 24) & 255;
        int i4 = (i >> 16) & 255;
        int i5 = (i >> 8) & 255;
        int i6 = i & 255;
        return ((i3 + ((int) ((((i2 >> 24) & 255) - i3) * f))) << 24) | ((i4 + ((int) ((((i2 >> 16) & 255) - i4) * f))) << 16) | ((i5 + ((int) ((((i2 >> 8) & 255) - i5) * f))) << 8) | (i6 + ((int) (f * ((i2 & 255) - i6))));
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    public MaterialProgressDrawable(View view) {
        this.i = view;
        setColorSchemeColors(e);
        updateSizes(1);
        a();
    }

    private void a(int i, int i2, float f, float f2, float f3, float f4) {
        float f5 = Resources.getSystem().getDisplayMetrics().density;
        this.k = i * f5;
        this.l = i2 * f5;
        this.g.a(0);
        float f6 = f2 * f5;
        this.g.b.setStrokeWidth(f6);
        a aVar = this.g;
        aVar.g = f6;
        aVar.q = f * f5;
        aVar.r = (int) (f3 * f5);
        aVar.s = (int) (f4 * f5);
        aVar.a((int) this.k, (int) this.l);
        invalidateSelf();
    }

    public void updateSizes(int i) {
        if (i == 0) {
            a(56, 56, 12.5f, 3.0f, 12.0f, 6.0f);
        } else {
            a(40, 40, 8.75f, 2.5f, 10.0f, 5.0f);
        }
    }

    public void showArrow(boolean z) {
        if (this.g.n != z) {
            this.g.n = z;
            invalidateSelf();
        }
    }

    public void setArrowScale(float f) {
        if (this.g.p != f) {
            this.g.p = f;
            invalidateSelf();
        }
    }

    public void setStartEndTrim(float f, float f2) {
        a aVar = this.g;
        aVar.d = f;
        aVar.e = f2;
        invalidateSelf();
    }

    public void setProgressRotation(float f) {
        this.g.f = f;
        invalidateSelf();
    }

    public void setColorSchemeColors(@ColorInt int... iArr) {
        a aVar = this.g;
        aVar.i = iArr;
        aVar.a(0);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) this.l;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return (int) this.k;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int save = canvas.save();
        canvas.rotate(this.h, bounds.exactCenterX(), bounds.exactCenterY());
        this.g.a(canvas, bounds);
        canvas.restoreToCount(save);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.g.b.setColorFilter(colorFilter);
        invalidateSelf();
    }

    void a(float f) {
        this.h = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        List<Animation> list = this.f;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Animation animation = list.get(i);
            if (animation.hasStarted() && !animation.hasEnded()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.j.reset();
        this.g.d();
        if (this.g.e != this.g.d) {
            this.c = true;
            this.j.setDuration(666L);
            this.i.startAnimation(this.j);
            return;
        }
        this.g.a(0);
        this.g.e();
        this.j.setDuration(1332L);
        this.i.startAnimation(this.j);
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.i.clearAnimation();
        this.g.a(0);
        this.g.e();
        showArrow(false);
        a(0.0f);
    }

    float a(a aVar) {
        return (float) Math.toRadians(aVar.g / (aVar.q * 6.283185307179586d));
    }

    void a(float f, a aVar) {
        if (f > 0.75f) {
            aVar.t = a((f - 0.75f) / 0.25f, aVar.c(), aVar.a());
        }
    }

    void b(float f, a aVar) {
        a(f, aVar);
        setStartEndTrim(aVar.k + (((aVar.l - a(aVar)) - aVar.k) * f), aVar.l);
        setProgressRotation(aVar.m + ((((float) (Math.floor(aVar.m / 0.8f) + 1.0d)) - aVar.m) * f));
    }

    private void a() {
        final a aVar = this.g;
        Animation animation = new Animation() { // from class: com.scwang.smart.refresh.header.material.MaterialProgressDrawable.1
            @Override // android.view.animation.Animation
            public void applyTransformation(float f, Transformation transformation) {
                if (MaterialProgressDrawable.this.c) {
                    MaterialProgressDrawable.this.b(f, aVar);
                    return;
                }
                float a2 = MaterialProgressDrawable.this.a(aVar);
                float f2 = aVar.l;
                float f3 = aVar.k;
                float f4 = aVar.m;
                MaterialProgressDrawable.this.a(f, aVar);
                if (f <= 0.5f) {
                    aVar.d = f3 + ((0.8f - a2) * MaterialProgressDrawable.a.getInterpolation(f / 0.5f));
                }
                if (f > 0.5f) {
                    aVar.e = f2 + ((0.8f - a2) * MaterialProgressDrawable.a.getInterpolation((f - 0.5f) / 0.5f));
                }
                MaterialProgressDrawable.this.setProgressRotation(f4 + (0.25f * f));
                MaterialProgressDrawable.this.a((f * 216.0f) + ((MaterialProgressDrawable.this.b / 5.0f) * 1080.0f));
            }
        };
        animation.setRepeatCount(-1);
        animation.setRepeatMode(1);
        animation.setInterpolator(d);
        animation.setAnimationListener(new Animation.AnimationListener() { // from class: com.scwang.smart.refresh.header.material.MaterialProgressDrawable.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation2) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation2) {
                MaterialProgressDrawable.this.b = 0.0f;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation2) {
                aVar.d();
                aVar.b();
                a aVar2 = aVar;
                aVar2.d = aVar2.e;
                if (MaterialProgressDrawable.this.c) {
                    MaterialProgressDrawable.this.c = false;
                    animation2.setDuration(1332L);
                    MaterialProgressDrawable.this.showArrow(false);
                    return;
                }
                MaterialProgressDrawable materialProgressDrawable = MaterialProgressDrawable.this;
                materialProgressDrawable.b = (materialProgressDrawable.b + 1.0f) % 5.0f;
            }
        });
        this.j = animation;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class a {
        final RectF a = new RectF();
        final Paint b = new Paint();
        final Paint c = new Paint();
        float d = 0.0f;
        float e = 0.0f;
        float f = 0.0f;
        float g = 5.0f;
        float h = 2.5f;
        int[] i;
        int j;
        float k;
        float l;
        float m;
        boolean n;
        Path o;
        float p;
        double q;
        int r;
        int s;
        int t;

        a() {
            this.b.setStrokeCap(Paint.Cap.SQUARE);
            this.b.setAntiAlias(true);
            this.b.setStyle(Paint.Style.STROKE);
            this.c.setStyle(Paint.Style.FILL);
            this.c.setAntiAlias(true);
        }

        public void a(Canvas canvas, Rect rect) {
            RectF rectF = this.a;
            rectF.set(rect);
            float f = this.h;
            rectF.inset(f, f);
            float f2 = this.d;
            float f3 = this.f;
            float f4 = (f2 + f3) * 360.0f;
            float f5 = ((this.e + f3) * 360.0f) - f4;
            if (f5 != 0.0f) {
                this.b.setColor(this.t);
                canvas.drawArc(rectF, f4, f5, false, this.b);
            }
            a(canvas, f4, f5, rect);
        }

        private void a(Canvas canvas, float f, float f2, Rect rect) {
            if (this.n) {
                Path path = this.o;
                if (path == null) {
                    this.o = new Path();
                    this.o.setFillType(Path.FillType.EVEN_ODD);
                } else {
                    path.reset();
                }
                float f3 = (((int) this.h) / 2) * this.p;
                float sin = (float) ((this.q * Math.sin(0.0d)) + rect.exactCenterY());
                this.o.moveTo(0.0f, 0.0f);
                this.o.lineTo(this.r * this.p, 0.0f);
                Path path2 = this.o;
                float f4 = this.p;
                path2.lineTo((this.r * f4) / 2.0f, this.s * f4);
                this.o.offset(((float) ((this.q * Math.cos(0.0d)) + rect.exactCenterX())) - f3, sin);
                this.o.close();
                this.c.setColor(this.t);
                canvas.rotate((f + f2) - 5.0f, rect.exactCenterX(), rect.exactCenterY());
                canvas.drawPath(this.o, this.c);
            }
        }

        public void a(int i) {
            this.j = i;
            this.t = this.i[this.j];
        }

        public int a() {
            return this.i[f()];
        }

        private int f() {
            return (this.j + 1) % this.i.length;
        }

        public void b() {
            a(f());
        }

        public int c() {
            return this.i[this.j];
        }

        public void a(int i, int i2) {
            float min = Math.min(i, i2);
            double d = this.q;
            this.h = (d <= 0.0d || min < 0.0f) ? (float) Math.ceil(this.g / 2.0f) : (float) ((min / 2.0f) - d);
        }

        public void d() {
            this.k = this.d;
            this.l = this.e;
            this.m = this.f;
        }

        public void e() {
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 0.0f;
        }
    }
}
