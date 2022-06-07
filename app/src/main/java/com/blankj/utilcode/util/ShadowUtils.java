package com.blankj.utilcode.util;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.view.View;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public class ShadowUtils {
    public static void apply(View... viewArr) {
        if (viewArr != null) {
            for (View view : viewArr) {
                apply(view, new Config());
            }
        }
    }

    public static void apply(View view, Config config) {
        if (view != null && config != null) {
            Drawable background = view.getBackground();
            Object tag = view.getTag(-16);
            if (tag instanceof Drawable) {
                ViewCompat.setBackground(view, (Drawable) tag);
                return;
            }
            Drawable a2 = config.a(background);
            ViewCompat.setBackground(view, a2);
            view.setTag(-16, a2);
        }
    }

    /* loaded from: classes.dex */
    public static class Config {
        private static final int a = b.a(8.0f);
        private float b = -1.0f;
        private float c = -1.0f;
        private float d = -1.0f;
        private float e = -1.0f;
        private float f = -1.0f;
        private int g = 1140850688;
        private int h = 1140850688;
        private boolean i = false;

        public Config setShadowRadius(float f) {
            this.b = f;
            if (!this.i) {
                return this;
            }
            throw new IllegalArgumentException("Set circle needn't set radius.");
        }

        public Config setCircle() {
            this.i = true;
            if (this.b == -1.0f) {
                return this;
            }
            throw new IllegalArgumentException("Set circle needn't set radius.");
        }

        public Config setShadowSize(int i) {
            return setShadowSize(i, i);
        }

        public Config setShadowSize(int i, int i2) {
            this.c = i;
            this.d = i2;
            return this;
        }

        public Config setShadowMaxSize(int i) {
            return setShadowMaxSize(i, i);
        }

        public Config setShadowMaxSize(int i, int i2) {
            this.e = i;
            this.f = i2;
            return this;
        }

        public Config setShadowColor(int i) {
            return setShadowColor(i, i);
        }

        public Config setShadowColor(int i, int i2) {
            this.g = i;
            this.h = i2;
            return this;
        }

        Drawable a(Drawable drawable) {
            if (drawable == null) {
                drawable = new ColorDrawable(0);
            }
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{16842919}, new ShadowDrawable(drawable, a(), b(), d(), this.h, this.i));
            stateListDrawable.addState(StateSet.WILD_CARD, new ShadowDrawable(drawable, a(), c(), e(), this.g, this.i));
            return stateListDrawable;
        }

        private float a() {
            if (this.b < 0.0f) {
                this.b = 0.0f;
            }
            return this.b;
        }

        private float b() {
            if (this.c == -1.0f) {
                this.c = a;
            }
            return this.c;
        }

        private float c() {
            if (this.d == -1.0f) {
                this.d = b();
            }
            return this.d;
        }

        private float d() {
            if (this.e == -1.0f) {
                this.e = b();
            }
            return this.e;
        }

        private float e() {
            if (this.f == -1.0f) {
                this.f = c();
            }
            return this.f;
        }
    }

    /* loaded from: classes.dex */
    public static class ShadowDrawable extends a {
        private static final double a = Math.cos(Math.toRadians(45.0d));
        private float b;
        private float c;
        private float d;
        private float e;
        private Paint f;
        private Paint g;
        private RectF h;
        private float i;
        private Path j;
        private float k;
        private float l;
        private float m;
        private float n;
        private final int p;
        private final int q;
        private float s;
        private boolean t;
        private boolean o = true;
        private boolean r = false;

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int getChangingConfigurations() {
            return super.getChangingConfigurations();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ Drawable getCurrent() {
            return super.getCurrent();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int getIntrinsicHeight() {
            return super.getIntrinsicHeight();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int getIntrinsicWidth() {
            return super.getIntrinsicWidth();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int getMinimumHeight() {
            return super.getMinimumHeight();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int getMinimumWidth() {
            return super.getMinimumWidth();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int[] getState() {
            return super.getState();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ Region getTransparentRegion() {
            return super.getTransparentRegion();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a
        public /* bridge */ /* synthetic */ Drawable getWrappedDrawable() {
            return super.getWrappedDrawable();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable.Callback
        public /* bridge */ /* synthetic */ void invalidateDrawable(Drawable drawable) {
            super.invalidateDrawable(drawable);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ boolean isAutoMirrored() {
            return super.isAutoMirrored();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ boolean isStateful() {
            return super.isStateful();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void jumpToCurrentState() {
            super.jumpToCurrentState();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable.Callback
        public /* bridge */ /* synthetic */ void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
            super.scheduleDrawable(drawable, runnable, j);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setAutoMirrored(boolean z) {
            super.setAutoMirrored(z);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setChangingConfigurations(int i) {
            super.setChangingConfigurations(i);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setColorFilter(ColorFilter colorFilter) {
            super.setColorFilter(colorFilter);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setDither(boolean z) {
            super.setDither(z);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z) {
            super.setFilterBitmap(z);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setHotspot(float f, float f2) {
            super.setHotspot(f, f2);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
            super.setHotspotBounds(i, i2, i3, i4);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
            return super.setState(iArr);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setTint(int i) {
            super.setTint(i);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setTintList(ColorStateList colorStateList) {
            super.setTintList(colorStateList);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setTintMode(PorterDuff.Mode mode) {
            super.setTintMode(mode);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2) {
            return super.setVisible(z, z2);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a
        public /* bridge */ /* synthetic */ void setWrappedDrawable(Drawable drawable) {
            super.setWrappedDrawable(drawable);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable.Callback
        public /* bridge */ /* synthetic */ void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            super.unscheduleDrawable(drawable, runnable);
        }

        public ShadowDrawable(Drawable drawable, float f, float f2, float f3, int i, boolean z) {
            super(drawable);
            this.b = 1.0f;
            this.c = 1.0f;
            this.d = 1.0f;
            this.e = 1.0f;
            this.p = i;
            this.q = this.p & 16777215;
            this.t = z;
            if (z) {
                this.b = 1.0f;
                this.c = 1.0f;
                this.d = 1.0f;
                this.e = 1.0f;
            }
            this.f = new Paint(5);
            this.f.setStyle(Paint.Style.FILL);
            this.i = Math.round(f);
            this.h = new RectF();
            this.g = new Paint(this.f);
            this.g.setAntiAlias(false);
            a(f2, f3);
        }

        private static int a(float f) {
            int round = Math.round(f);
            return round % 2 == 1 ? round - 1 : round;
        }

        public void setAddPaddingForCorners(boolean z) {
            this.r = z;
            invalidateSelf();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            super.setAlpha(i);
            this.f.setAlpha(i);
            this.g.setAlpha(i);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        protected void onBoundsChange(Rect rect) {
            this.o = true;
        }

        void a(float f, float f2) {
            if (f < 0.0f || f2 < 0.0f) {
                throw new IllegalArgumentException("invalid shadow size");
            }
            float a2 = a(f);
            float a3 = a(f2);
            if (a2 > a3) {
                a2 = a3;
            }
            if (this.n != a2 || this.l != a3) {
                this.n = a2;
                this.l = a3;
                this.m = Math.round(a2 * this.b);
                this.k = a3;
                this.o = true;
                invalidateSelf();
            }
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public boolean getPadding(Rect rect) {
            int ceil = (int) Math.ceil(a(this.l, this.i, this.r));
            int ceil2 = (int) Math.ceil(b(this.l, this.i, this.r));
            rect.set(ceil2, ceil, ceil2, ceil);
            return true;
        }

        private float a(float f, float f2, boolean z) {
            if (z) {
                return (float) ((f * this.b) + ((1.0d - a) * f2));
            }
            return f * this.b;
        }

        private static float b(float f, float f2, boolean z) {
            return z ? (float) (f + ((1.0d - a) * f2)) : f;
        }

        public void setCornerRadius(float f) {
            float round = Math.round(f);
            if (this.i != round) {
                this.i = round;
                this.o = true;
                invalidateSelf();
            }
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.a, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            if (this.o) {
                a(getBounds());
                this.o = false;
            }
            a(canvas);
            super.draw(canvas);
        }

        private void a(Canvas canvas) {
            float f;
            int i;
            int i2;
            float f2;
            float f3;
            float f4;
            if (this.t) {
                int save = canvas.save();
                canvas.translate(this.h.centerX(), this.h.centerY());
                canvas.drawPath(this.j, this.f);
                canvas.restoreToCount(save);
                return;
            }
            int save2 = canvas.save();
            canvas.rotate(this.s, this.h.centerX(), this.h.centerY());
            float f5 = this.i;
            float f6 = (-f5) - this.m;
            float f7 = f5 * 2.0f;
            boolean z = this.h.width() - f7 > 0.0f;
            boolean z2 = this.h.height() - f7 > 0.0f;
            float f8 = this.n;
            float f9 = f8 - (this.c * f8);
            float f10 = f8 - (this.d * f8);
            float f11 = f8 - (this.e * f8);
            int i3 = (f5 > 0.0f ? 1 : (f5 == 0.0f ? 0 : -1));
            float f12 = i3 == 0 ? 1.0f : f5 / (f10 + f5);
            float f13 = i3 == 0 ? 1.0f : f5 / (f9 + f5);
            float f14 = i3 == 0 ? 1.0f : f5 / (f11 + f5);
            int save3 = canvas.save();
            canvas.translate(this.h.left + f5, this.h.top + f5);
            canvas.scale(f12, f13);
            canvas.drawPath(this.j, this.f);
            if (z) {
                canvas.scale(1.0f / f12, 1.0f);
                i2 = save3;
                f = f14;
                i = save2;
                f2 = f13;
                canvas.drawRect(0.0f, f6, this.h.width() - f7, -this.i, this.g);
            } else {
                i2 = save3;
                f = f14;
                i = save2;
                f2 = f13;
            }
            canvas.restoreToCount(i2);
            int save4 = canvas.save();
            canvas.translate(this.h.right - f5, this.h.bottom - f5);
            canvas.scale(f12, f);
            canvas.rotate(180.0f);
            canvas.drawPath(this.j, this.f);
            if (z) {
                canvas.scale(1.0f / f12, 1.0f);
                f3 = f2;
                f4 = f;
                canvas.drawRect(0.0f, f6, this.h.width() - f7, -this.i, this.g);
            } else {
                f3 = f2;
                f4 = f;
            }
            canvas.restoreToCount(save4);
            int save5 = canvas.save();
            canvas.translate(this.h.left + f5, this.h.bottom - f5);
            canvas.scale(f12, f4);
            canvas.rotate(270.0f);
            canvas.drawPath(this.j, this.f);
            if (z2) {
                canvas.scale(1.0f / f4, 1.0f);
                canvas.drawRect(0.0f, f6, this.h.height() - f7, -this.i, this.g);
            }
            canvas.restoreToCount(save5);
            int save6 = canvas.save();
            canvas.translate(this.h.right - f5, this.h.top + f5);
            canvas.scale(f12, f3);
            canvas.rotate(90.0f);
            canvas.drawPath(this.j, this.f);
            if (z2) {
                canvas.scale(1.0f / f3, 1.0f);
                canvas.drawRect(0.0f, f6, this.h.height() - f7, -this.i, this.g);
            }
            canvas.restoreToCount(save6);
            canvas.restoreToCount(i);
        }

        private void a() {
            if (this.t) {
                float width = (this.h.width() / 2.0f) - 1.0f;
                float f = -width;
                RectF rectF = new RectF(f, f, width, width);
                RectF rectF2 = new RectF(rectF);
                float f2 = this.m;
                rectF2.inset(-f2, -f2);
                Path path = this.j;
                if (path == null) {
                    this.j = new Path();
                } else {
                    path.reset();
                }
                this.j.setFillType(Path.FillType.EVEN_ODD);
                this.j.moveTo(f, 0.0f);
                this.j.rLineTo(-this.m, 0.0f);
                this.j.arcTo(rectF2, 180.0f, 180.0f, false);
                this.j.arcTo(rectF2, 0.0f, 180.0f, false);
                this.j.arcTo(rectF, 180.0f, 180.0f, false);
                this.j.arcTo(rectF, 0.0f, 180.0f, false);
                this.j.close();
                float f3 = -rectF2.top;
                if (f3 > 0.0f) {
                    this.f.setShader(new RadialGradient(0.0f, 0.0f, f3, new int[]{0, this.p, this.q}, new float[]{0.0f, width / f3, 1.0f}, Shader.TileMode.CLAMP));
                    return;
                }
                return;
            }
            float f4 = this.i;
            RectF rectF3 = new RectF(-f4, -f4, f4, f4);
            RectF rectF4 = new RectF(rectF3);
            float f5 = this.m;
            rectF4.inset(-f5, -f5);
            Path path2 = this.j;
            if (path2 == null) {
                this.j = new Path();
            } else {
                path2.reset();
            }
            this.j.setFillType(Path.FillType.EVEN_ODD);
            this.j.moveTo(-this.i, 0.0f);
            this.j.rLineTo(-this.m, 0.0f);
            this.j.arcTo(rectF4, 180.0f, 90.0f, false);
            this.j.arcTo(rectF3, 270.0f, -90.0f, false);
            this.j.close();
            float f6 = -rectF4.top;
            if (f6 > 0.0f) {
                this.f.setShader(new RadialGradient(0.0f, 0.0f, f6, new int[]{0, this.p, this.q}, new float[]{0.0f, this.i / f6, 1.0f}, Shader.TileMode.CLAMP));
            }
            this.g.setShader(new LinearGradient(0.0f, rectF3.top, 0.0f, rectF4.top, this.p, this.q, Shader.TileMode.CLAMP));
            this.g.setAntiAlias(false);
        }

        private void a(Rect rect) {
            if (this.t) {
                this.i = rect.width() / 2;
            }
            float f = this.l * this.b;
            this.h.set(rect.left + this.l, rect.top + f, rect.right - this.l, rect.bottom - f);
            getWrappedDrawable().setBounds((int) this.h.left, (int) this.h.top, (int) this.h.right, (int) this.h.bottom);
            a();
        }

        public float getCornerRadius() {
            return this.i;
        }

        public void setShadowSize(float f) {
            a(f, this.l);
        }

        public void setMaxShadowSize(float f) {
            a(this.n, f);
        }

        public float getShadowSize() {
            return this.n;
        }

        public float getMaxShadowSize() {
            return this.l;
        }

        public float getMinWidth() {
            float f = this.l;
            return (Math.max(f, this.i + (f / 2.0f)) * 2.0f) + (this.l * 2.0f);
        }

        public float getMinHeight() {
            float f = this.l;
            return (Math.max(f, this.i + ((this.b * f) / 2.0f)) * 2.0f) + (this.l * this.b * 2.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a extends Drawable implements Drawable.Callback {
        private Drawable a;

        public a(Drawable drawable) {
            setWrappedDrawable(drawable);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            this.a.draw(canvas);
        }

        @Override // android.graphics.drawable.Drawable
        protected void onBoundsChange(Rect rect) {
            this.a.setBounds(rect);
        }

        @Override // android.graphics.drawable.Drawable
        public void setChangingConfigurations(int i) {
            this.a.setChangingConfigurations(i);
        }

        @Override // android.graphics.drawable.Drawable
        public int getChangingConfigurations() {
            return this.a.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable
        public void setDither(boolean z) {
            this.a.setDither(z);
        }

        @Override // android.graphics.drawable.Drawable
        public void setFilterBitmap(boolean z) {
            this.a.setFilterBitmap(z);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            this.a.setAlpha(i);
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            this.a.setColorFilter(colorFilter);
        }

        @Override // android.graphics.drawable.Drawable
        public boolean isStateful() {
            return this.a.isStateful();
        }

        @Override // android.graphics.drawable.Drawable
        public boolean setState(int[] iArr) {
            return this.a.setState(iArr);
        }

        @Override // android.graphics.drawable.Drawable
        public int[] getState() {
            return this.a.getState();
        }

        @Override // android.graphics.drawable.Drawable
        public void jumpToCurrentState() {
            DrawableCompat.jumpToCurrentState(this.a);
        }

        @Override // android.graphics.drawable.Drawable
        public Drawable getCurrent() {
            return this.a.getCurrent();
        }

        @Override // android.graphics.drawable.Drawable
        public boolean setVisible(boolean z, boolean z2) {
            return super.setVisible(z, z2) || this.a.setVisible(z, z2);
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return this.a.getOpacity();
        }

        @Override // android.graphics.drawable.Drawable
        public Region getTransparentRegion() {
            return this.a.getTransparentRegion();
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.a.getIntrinsicWidth();
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.a.getIntrinsicHeight();
        }

        @Override // android.graphics.drawable.Drawable
        public int getMinimumWidth() {
            return this.a.getMinimumWidth();
        }

        @Override // android.graphics.drawable.Drawable
        public int getMinimumHeight() {
            return this.a.getMinimumHeight();
        }

        @Override // android.graphics.drawable.Drawable
        public boolean getPadding(Rect rect) {
            return this.a.getPadding(rect);
        }

        public void invalidateDrawable(Drawable drawable) {
            invalidateSelf();
        }

        public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
            scheduleSelf(runnable, j);
        }

        public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            unscheduleSelf(runnable);
        }

        @Override // android.graphics.drawable.Drawable
        protected boolean onLevelChange(int i) {
            return this.a.setLevel(i);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAutoMirrored(boolean z) {
            DrawableCompat.setAutoMirrored(this.a, z);
        }

        @Override // android.graphics.drawable.Drawable
        public boolean isAutoMirrored() {
            return DrawableCompat.isAutoMirrored(this.a);
        }

        @Override // android.graphics.drawable.Drawable
        public void setTint(int i) {
            DrawableCompat.setTint(this.a, i);
        }

        @Override // android.graphics.drawable.Drawable
        public void setTintList(ColorStateList colorStateList) {
            DrawableCompat.setTintList(this.a, colorStateList);
        }

        @Override // android.graphics.drawable.Drawable
        public void setTintMode(PorterDuff.Mode mode) {
            DrawableCompat.setTintMode(this.a, mode);
        }

        @Override // android.graphics.drawable.Drawable
        public void setHotspot(float f, float f2) {
            DrawableCompat.setHotspot(this.a, f, f2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setHotspotBounds(int i, int i2, int i3, int i4) {
            DrawableCompat.setHotspotBounds(this.a, i, i2, i3, i4);
        }

        public Drawable getWrappedDrawable() {
            return this.a;
        }

        public void setWrappedDrawable(Drawable drawable) {
            Drawable drawable2 = this.a;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            this.a = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
            }
        }
    }
}
