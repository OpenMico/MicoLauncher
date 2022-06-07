package androidx.swiperefreshlayout.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class CircularProgressDrawable extends Drawable implements Animatable {
    public static final int DEFAULT = 1;
    public static final int LARGE = 0;
    private static final Interpolator c = new LinearInterpolator();
    private static final Interpolator d = new FastOutSlowInInterpolator();
    private static final int[] e = {ViewCompat.MEASURED_STATE_MASK};
    float a;
    boolean b;
    private final a f = new a();
    private float g;
    private Resources h;
    private Animator i;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
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

    public CircularProgressDrawable(@NonNull Context context) {
        this.h = ((Context) Preconditions.checkNotNull(context)).getResources();
        this.f.a(e);
        setStrokeWidth(2.5f);
        a();
    }

    private void a(float f, float f2, float f3, float f4) {
        a aVar = this.f;
        float f5 = this.h.getDisplayMetrics().density;
        aVar.a(f2 * f5);
        aVar.e(f * f5);
        aVar.c(0);
        aVar.a(f3 * f5, f4 * f5);
    }

    public void setStyle(int i) {
        if (i == 0) {
            a(11.0f, 3.0f, 12.0f, 6.0f);
        } else {
            a(7.5f, 2.5f, 10.0f, 5.0f);
        }
        invalidateSelf();
    }

    public float getStrokeWidth() {
        return this.f.j();
    }

    public void setStrokeWidth(float f) {
        this.f.a(f);
        invalidateSelf();
    }

    public float getCenterRadius() {
        return this.f.q();
    }

    public void setCenterRadius(float f) {
        this.f.e(f);
        invalidateSelf();
    }

    public void setStrokeCap(@NonNull Paint.Cap cap) {
        this.f.a(cap);
        invalidateSelf();
    }

    @NonNull
    public Paint.Cap getStrokeCap() {
        return this.f.a();
    }

    public float getArrowWidth() {
        return this.f.b();
    }

    public float getArrowHeight() {
        return this.f.c();
    }

    public void setArrowDimensions(float f, float f2) {
        this.f.a(f, f2);
        invalidateSelf();
    }

    public boolean getArrowEnabled() {
        return this.f.r();
    }

    public void setArrowEnabled(boolean z) {
        this.f.a(z);
        invalidateSelf();
    }

    public float getArrowScale() {
        return this.f.s();
    }

    public void setArrowScale(float f) {
        this.f.f(f);
        invalidateSelf();
    }

    public float getStartTrim() {
        return this.f.k();
    }

    public float getEndTrim() {
        return this.f.o();
    }

    public void setStartEndTrim(float f, float f2) {
        this.f.b(f);
        this.f.c(f2);
        invalidateSelf();
    }

    public float getProgressRotation() {
        return this.f.p();
    }

    public void setProgressRotation(float f) {
        this.f.d(f);
        invalidateSelf();
    }

    public int getBackgroundColor() {
        return this.f.e();
    }

    public void setBackgroundColor(int i) {
        this.f.b(i);
        invalidateSelf();
    }

    @NonNull
    public int[] getColorSchemeColors() {
        return this.f.d();
    }

    public void setColorSchemeColors(@NonNull int... iArr) {
        this.f.a(iArr);
        this.f.c(0);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(this.g, bounds.exactCenterX(), bounds.exactCenterY());
        this.f.a(canvas, bounds);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.f.d(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.f.i();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.f.a(colorFilter);
        invalidateSelf();
    }

    private void a(float f) {
        this.g = f;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.i.isRunning();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.i.cancel();
        this.f.u();
        if (this.f.o() != this.f.k()) {
            this.b = true;
            this.i.setDuration(666L);
            this.i.start();
            return;
        }
        this.f.c(0);
        this.f.v();
        this.i.setDuration(1332L);
        this.i.start();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.i.cancel();
        a(0.0f);
        this.f.a(false);
        this.f.c(0);
        this.f.v();
        invalidateSelf();
    }

    void a(float f, a aVar) {
        if (f > 0.75f) {
            aVar.a(a((f - 0.75f) / 0.25f, aVar.n(), aVar.f()));
        } else {
            aVar.a(aVar.n());
        }
    }

    private void b(float f, a aVar) {
        a(f, aVar);
        aVar.b(aVar.l() + (((aVar.m() - 0.01f) - aVar.l()) * f));
        aVar.c(aVar.m());
        aVar.d(aVar.t() + ((((float) (Math.floor(aVar.t() / 0.8f) + 1.0d)) - aVar.t()) * f));
    }

    void a(float f, a aVar, boolean z) {
        float f2;
        float f3;
        if (this.b) {
            b(f, aVar);
        } else if (f != 1.0f || z) {
            float t = aVar.t();
            if (f < 0.5f) {
                float l = aVar.l();
                f2 = (d.getInterpolation(f / 0.5f) * 0.79f) + 0.01f + l;
                f3 = l;
            } else {
                f2 = aVar.l() + 0.79f;
                f3 = f2 - (((1.0f - d.getInterpolation((f - 0.5f) / 0.5f)) * 0.79f) + 0.01f);
            }
            aVar.b(f3);
            aVar.c(f2);
            aVar.d(t + (0.20999998f * f));
            a((f + this.a) * 216.0f);
        }
    }

    private void a() {
        final a aVar = this.f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.swiperefreshlayout.widget.CircularProgressDrawable.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                CircularProgressDrawable.this.a(floatValue, aVar);
                CircularProgressDrawable.this.a(floatValue, aVar, false);
                CircularProgressDrawable.this.invalidateSelf();
            }
        });
        ofFloat.setRepeatCount(-1);
        ofFloat.setRepeatMode(1);
        ofFloat.setInterpolator(c);
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: androidx.swiperefreshlayout.widget.CircularProgressDrawable.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                CircularProgressDrawable.this.a = 0.0f;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
                CircularProgressDrawable.this.a(1.0f, aVar, true);
                aVar.u();
                aVar.h();
                if (CircularProgressDrawable.this.b) {
                    CircularProgressDrawable.this.b = false;
                    animator.cancel();
                    animator.setDuration(1332L);
                    animator.start();
                    aVar.a(false);
                    return;
                }
                CircularProgressDrawable.this.a += 1.0f;
            }
        });
        this.i = ofFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        int[] i;
        int j;
        float k;
        float l;
        float m;
        boolean n;
        Path o;
        float q;
        int r;
        int s;
        int u;
        final RectF a = new RectF();
        final Paint b = new Paint();
        final Paint c = new Paint();
        final Paint d = new Paint();
        float e = 0.0f;
        float f = 0.0f;
        float g = 0.0f;
        float h = 5.0f;
        float p = 1.0f;
        int t = 255;

        a() {
            this.b.setStrokeCap(Paint.Cap.SQUARE);
            this.b.setAntiAlias(true);
            this.b.setStyle(Paint.Style.STROKE);
            this.c.setStyle(Paint.Style.FILL);
            this.c.setAntiAlias(true);
            this.d.setColor(0);
        }

        void a(float f, float f2) {
            this.r = (int) f;
            this.s = (int) f2;
        }

        void a(Paint.Cap cap) {
            this.b.setStrokeCap(cap);
        }

        Paint.Cap a() {
            return this.b.getStrokeCap();
        }

        float b() {
            return this.r;
        }

        float c() {
            return this.s;
        }

        void a(Canvas canvas, Rect rect) {
            RectF rectF = this.a;
            float f = this.q;
            float f2 = (this.h / 2.0f) + f;
            if (f <= 0.0f) {
                f2 = (Math.min(rect.width(), rect.height()) / 2.0f) - Math.max((this.r * this.p) / 2.0f, this.h / 2.0f);
            }
            rectF.set(rect.centerX() - f2, rect.centerY() - f2, rect.centerX() + f2, rect.centerY() + f2);
            float f3 = this.e;
            float f4 = this.g;
            float f5 = (f3 + f4) * 360.0f;
            float f6 = ((this.f + f4) * 360.0f) - f5;
            this.b.setColor(this.u);
            this.b.setAlpha(this.t);
            float f7 = this.h / 2.0f;
            rectF.inset(f7, f7);
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, this.d);
            float f8 = -f7;
            rectF.inset(f8, f8);
            canvas.drawArc(rectF, f5, f6, false, this.b);
            a(canvas, f5, f6, rectF);
        }

        void a(Canvas canvas, float f, float f2, RectF rectF) {
            if (this.n) {
                Path path = this.o;
                if (path == null) {
                    this.o = new Path();
                    this.o.setFillType(Path.FillType.EVEN_ODD);
                } else {
                    path.reset();
                }
                this.o.moveTo(0.0f, 0.0f);
                this.o.lineTo(this.r * this.p, 0.0f);
                Path path2 = this.o;
                float f3 = this.p;
                path2.lineTo((this.r * f3) / 2.0f, this.s * f3);
                this.o.offset(((Math.min(rectF.width(), rectF.height()) / 2.0f) + rectF.centerX()) - ((this.r * this.p) / 2.0f), rectF.centerY() + (this.h / 2.0f));
                this.o.close();
                this.c.setColor(this.u);
                this.c.setAlpha(this.t);
                canvas.save();
                canvas.rotate(f + f2, rectF.centerX(), rectF.centerY());
                canvas.drawPath(this.o, this.c);
                canvas.restore();
            }
        }

        void a(@NonNull int[] iArr) {
            this.i = iArr;
            c(0);
        }

        int[] d() {
            return this.i;
        }

        void a(int i) {
            this.u = i;
        }

        void b(int i) {
            this.d.setColor(i);
        }

        int e() {
            return this.d.getColor();
        }

        void c(int i) {
            this.j = i;
            this.u = this.i[this.j];
        }

        int f() {
            return this.i[g()];
        }

        int g() {
            return (this.j + 1) % this.i.length;
        }

        void h() {
            c(g());
        }

        void a(ColorFilter colorFilter) {
            this.b.setColorFilter(colorFilter);
        }

        void d(int i) {
            this.t = i;
        }

        int i() {
            return this.t;
        }

        void a(float f) {
            this.h = f;
            this.b.setStrokeWidth(f);
        }

        float j() {
            return this.h;
        }

        void b(float f) {
            this.e = f;
        }

        float k() {
            return this.e;
        }

        float l() {
            return this.k;
        }

        float m() {
            return this.l;
        }

        int n() {
            return this.i[this.j];
        }

        void c(float f) {
            this.f = f;
        }

        float o() {
            return this.f;
        }

        void d(float f) {
            this.g = f;
        }

        float p() {
            return this.g;
        }

        void e(float f) {
            this.q = f;
        }

        float q() {
            return this.q;
        }

        void a(boolean z) {
            if (this.n != z) {
                this.n = z;
            }
        }

        boolean r() {
            return this.n;
        }

        void f(float f) {
            if (f != this.p) {
                this.p = f;
            }
        }

        float s() {
            return this.p;
        }

        float t() {
            return this.m;
        }

        void u() {
            this.k = this.e;
            this.l = this.f;
            this.m = this.g;
        }

        void v() {
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            b(0.0f);
            c(0.0f);
            d(0.0f);
        }
    }
}
