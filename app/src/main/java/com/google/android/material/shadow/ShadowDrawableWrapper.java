package com.google.android.material.shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;

@Deprecated
/* loaded from: classes2.dex */
public class ShadowDrawableWrapper extends DrawableWrapper {
    static final double a = Math.cos(Math.toRadians(45.0d));
    float e;
    Path f;
    float g;
    float h;
    float i;
    float j;
    private final int l;
    private final int m;
    private final int n;
    private float p;
    private boolean k = true;
    private boolean o = true;
    private boolean q = false;
    @NonNull
    final Paint b = new Paint(5);
    @NonNull
    final RectF d = new RectF();
    @NonNull
    final Paint c = new Paint(this.b);

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public ShadowDrawableWrapper(Context context, Drawable drawable, float f, float f2, float f3) {
        super(drawable);
        this.l = ContextCompat.getColor(context, R.color.design_fab_shadow_start_color);
        this.m = ContextCompat.getColor(context, R.color.design_fab_shadow_mid_color);
        this.n = ContextCompat.getColor(context, R.color.design_fab_shadow_end_color);
        this.b.setStyle(Paint.Style.FILL);
        this.e = Math.round(f);
        this.c.setAntiAlias(false);
        setShadowSize(f2, f3);
    }

    private static int a(float f) {
        int round = Math.round(f);
        return round % 2 == 1 ? round - 1 : round;
    }

    public void setAddPaddingForCorners(boolean z) {
        this.o = z;
        invalidateSelf();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        super.setAlpha(i);
        this.b.setAlpha(i);
        this.c.setAlpha(i);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        this.k = true;
    }

    public void setShadowSize(float f, float f2) {
        if (f < 0.0f || f2 < 0.0f) {
            throw new IllegalArgumentException("invalid shadow size");
        }
        float a2 = a(f);
        float a3 = a(f2);
        if (a2 > a3) {
            if (!this.q) {
                this.q = true;
            }
            a2 = a3;
        }
        if (this.j != a2 || this.h != a3) {
            this.j = a2;
            this.h = a3;
            this.i = Math.round(a2 * 1.5f);
            this.g = a3;
            this.k = true;
            invalidateSelf();
        }
    }

    public void setShadowSize(float f) {
        setShadowSize(f, this.h);
    }

    public float getShadowSize() {
        return this.j;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public boolean getPadding(@NonNull Rect rect) {
        int ceil = (int) Math.ceil(calculateVerticalPadding(this.h, this.e, this.o));
        int ceil2 = (int) Math.ceil(calculateHorizontalPadding(this.h, this.e, this.o));
        rect.set(ceil2, ceil, ceil2, ceil);
        return true;
    }

    public static float calculateVerticalPadding(float f, float f2, boolean z) {
        return z ? (float) ((f * 1.5f) + ((1.0d - a) * f2)) : f * 1.5f;
    }

    public static float calculateHorizontalPadding(float f, float f2, boolean z) {
        return z ? (float) (f + ((1.0d - a) * f2)) : f;
    }

    public void setCornerRadius(float f) {
        float round = Math.round(f);
        if (this.e != round) {
            this.e = round;
            this.k = true;
            invalidateSelf();
        }
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        if (this.k) {
            a(getBounds());
            this.k = false;
        }
        a(canvas);
        super.draw(canvas);
    }

    public final void setRotation(float f) {
        if (this.p != f) {
            this.p = f;
            invalidateSelf();
        }
    }

    private void a(@NonNull Canvas canvas) {
        float f;
        int i;
        int i2;
        float f2;
        float f3;
        float f4;
        int save = canvas.save();
        canvas.rotate(this.p, this.d.centerX(), this.d.centerY());
        float f5 = this.e;
        float f6 = (-f5) - this.i;
        float f7 = f5 * 2.0f;
        boolean z = this.d.width() - f7 > 0.0f;
        boolean z2 = this.d.height() - f7 > 0.0f;
        float f8 = this.j;
        float f9 = f5 / ((f8 - (0.5f * f8)) + f5);
        float f10 = f5 / ((f8 - (0.25f * f8)) + f5);
        float f11 = f5 / ((f8 - (f8 * 1.0f)) + f5);
        int save2 = canvas.save();
        canvas.translate(this.d.left + f5, this.d.top + f5);
        canvas.scale(f9, f10);
        canvas.drawPath(this.f, this.b);
        if (z) {
            canvas.scale(1.0f / f9, 1.0f);
            i2 = save2;
            f = f11;
            i = save;
            f2 = f10;
            canvas.drawRect(0.0f, f6, this.d.width() - f7, -this.e, this.c);
        } else {
            i2 = save2;
            f = f11;
            i = save;
            f2 = f10;
        }
        canvas.restoreToCount(i2);
        int save3 = canvas.save();
        canvas.translate(this.d.right - f5, this.d.bottom - f5);
        canvas.scale(f9, f);
        canvas.rotate(180.0f);
        canvas.drawPath(this.f, this.b);
        if (z) {
            canvas.scale(1.0f / f9, 1.0f);
            f3 = f2;
            f4 = f;
            canvas.drawRect(0.0f, f6, this.d.width() - f7, (-this.e) + this.i, this.c);
        } else {
            f3 = f2;
            f4 = f;
        }
        canvas.restoreToCount(save3);
        int save4 = canvas.save();
        canvas.translate(this.d.left + f5, this.d.bottom - f5);
        canvas.scale(f9, f4);
        canvas.rotate(270.0f);
        canvas.drawPath(this.f, this.b);
        if (z2) {
            canvas.scale(1.0f / f4, 1.0f);
            canvas.drawRect(0.0f, f6, this.d.height() - f7, -this.e, this.c);
        }
        canvas.restoreToCount(save4);
        int save5 = canvas.save();
        canvas.translate(this.d.right - f5, this.d.top + f5);
        canvas.scale(f9, f3);
        canvas.rotate(90.0f);
        canvas.drawPath(this.f, this.b);
        if (z2) {
            canvas.scale(1.0f / f3, 1.0f);
            canvas.drawRect(0.0f, f6, this.d.height() - f7, -this.e, this.c);
        }
        canvas.restoreToCount(save5);
        canvas.restoreToCount(i);
    }

    private void a() {
        float f = this.e;
        RectF rectF = new RectF(-f, -f, f, f);
        RectF rectF2 = new RectF(rectF);
        float f2 = this.i;
        rectF2.inset(-f2, -f2);
        Path path = this.f;
        if (path == null) {
            this.f = new Path();
        } else {
            path.reset();
        }
        this.f.setFillType(Path.FillType.EVEN_ODD);
        this.f.moveTo(-this.e, 0.0f);
        this.f.rLineTo(-this.i, 0.0f);
        this.f.arcTo(rectF2, 180.0f, 90.0f, false);
        this.f.arcTo(rectF, 270.0f, -90.0f, false);
        this.f.close();
        float f3 = -rectF2.top;
        if (f3 > 0.0f) {
            float f4 = this.e / f3;
            this.b.setShader(new RadialGradient(0.0f, 0.0f, f3, new int[]{0, this.l, this.m, this.n}, new float[]{0.0f, f4, ((1.0f - f4) / 2.0f) + f4, 1.0f}, Shader.TileMode.CLAMP));
        }
        this.c.setShader(new LinearGradient(0.0f, rectF.top, 0.0f, rectF2.top, new int[]{this.l, this.m, this.n}, new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP));
        this.c.setAntiAlias(false);
    }

    private void a(@NonNull Rect rect) {
        float f = this.h * 1.5f;
        this.d.set(rect.left + this.h, rect.top + f, rect.right - this.h, rect.bottom - f);
        getWrappedDrawable().setBounds((int) this.d.left, (int) this.d.top, (int) this.d.right, (int) this.d.bottom);
        a();
    }

    public float getCornerRadius() {
        return this.e;
    }

    public void setMaxShadowSize(float f) {
        setShadowSize(this.j, f);
    }

    public float getMaxShadowSize() {
        return this.h;
    }

    public float getMinWidth() {
        float f = this.h;
        return (Math.max(f, this.e + (f / 2.0f)) * 2.0f) + (this.h * 2.0f);
    }

    public float getMinHeight() {
        float f = this.h;
        return (Math.max(f, this.e + ((f * 1.5f) / 2.0f)) * 2.0f) + (this.h * 1.5f * 2.0f);
    }
}
