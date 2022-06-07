package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.google.android.material.color.MaterialColors;

/* compiled from: CircularDrawingDelegate.java */
/* loaded from: classes2.dex */
final class a extends d<CircularProgressIndicatorSpec> {
    private int c = 1;
    private float d;
    private float e;
    private float f;

    public a(@NonNull CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(circularProgressIndicatorSpec);
    }

    @Override // com.google.android.material.progressindicator.d
    public int a() {
        return c();
    }

    @Override // com.google.android.material.progressindicator.d
    public int b() {
        return c();
    }

    @Override // com.google.android.material.progressindicator.d
    public void a(@NonNull Canvas canvas, @FloatRange(from = 0.0d, to = 1.0d) float f) {
        float f2 = (((CircularProgressIndicatorSpec) this.a).indicatorSize / 2.0f) + ((CircularProgressIndicatorSpec) this.a).indicatorInset;
        canvas.translate(f2, f2);
        canvas.rotate(-90.0f);
        float f3 = -f2;
        canvas.clipRect(f3, f3, f2, f2);
        this.c = ((CircularProgressIndicatorSpec) this.a).indicatorDirection == 0 ? 1 : -1;
        this.d = ((CircularProgressIndicatorSpec) this.a).trackThickness * f;
        this.e = ((CircularProgressIndicatorSpec) this.a).trackCornerRadius * f;
        this.f = (((CircularProgressIndicatorSpec) this.a).indicatorSize - ((CircularProgressIndicatorSpec) this.a).trackThickness) / 2.0f;
        if ((this.b.isShowing() && ((CircularProgressIndicatorSpec) this.a).showAnimationBehavior == 2) || (this.b.isHiding() && ((CircularProgressIndicatorSpec) this.a).hideAnimationBehavior == 1)) {
            this.f += ((1.0f - f) * ((CircularProgressIndicatorSpec) this.a).trackThickness) / 2.0f;
        } else if ((this.b.isShowing() && ((CircularProgressIndicatorSpec) this.a).showAnimationBehavior == 1) || (this.b.isHiding() && ((CircularProgressIndicatorSpec) this.a).hideAnimationBehavior == 2)) {
            this.f -= ((1.0f - f) * ((CircularProgressIndicatorSpec) this.a).trackThickness) / 2.0f;
        }
    }

    @Override // com.google.android.material.progressindicator.d
    void a(@NonNull Canvas canvas, @NonNull Paint paint, @FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2, @ColorInt int i) {
        if (f != f2) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.BUTT);
            paint.setAntiAlias(true);
            paint.setColor(i);
            paint.setStrokeWidth(this.d);
            int i2 = this.c;
            float f3 = f * 360.0f * i2;
            float f4 = (f2 >= f ? f2 - f : (f2 + 1.0f) - f) * 360.0f * i2;
            float f5 = this.f;
            canvas.drawArc(new RectF(-f5, -f5, f5, f5), f3, f4, false, paint);
            if (this.e > 0.0f && Math.abs(f4) < 360.0f) {
                paint.setStyle(Paint.Style.FILL);
                float f6 = this.e;
                RectF rectF = new RectF(-f6, -f6, f6, f6);
                a(canvas, paint, this.d, this.e, f3, true, rectF);
                a(canvas, paint, this.d, this.e, f3 + f4, false, rectF);
            }
        }
    }

    @Override // com.google.android.material.progressindicator.d
    void a(@NonNull Canvas canvas, @NonNull Paint paint) {
        int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(((CircularProgressIndicatorSpec) this.a).trackColor, this.b.getAlpha());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        paint.setColor(compositeARGBWithAlpha);
        paint.setStrokeWidth(this.d);
        float f = this.f;
        canvas.drawArc(new RectF(-f, -f, f, f), 0.0f, 360.0f, false, paint);
    }

    private int c() {
        return ((CircularProgressIndicatorSpec) this.a).indicatorSize + (((CircularProgressIndicatorSpec) this.a).indicatorInset * 2);
    }

    private void a(Canvas canvas, Paint paint, float f, float f2, float f3, boolean z, RectF rectF) {
        float f4 = z ? -1.0f : 1.0f;
        canvas.save();
        canvas.rotate(f3);
        float f5 = f / 2.0f;
        float f6 = f4 * f2;
        canvas.drawRect((this.f - f5) + f2, Math.min(0.0f, this.c * f6), (this.f + f5) - f2, Math.max(0.0f, f6 * this.c), paint);
        canvas.translate((this.f - f5) + f2, 0.0f);
        canvas.drawArc(rectF, 180.0f, (-f4) * 90.0f * this.c, true, paint);
        canvas.translate(f - (f2 * 2.0f), 0.0f);
        canvas.drawArc(rectF, 0.0f, f4 * 90.0f * this.c, true, paint);
        canvas.restore();
    }
}
