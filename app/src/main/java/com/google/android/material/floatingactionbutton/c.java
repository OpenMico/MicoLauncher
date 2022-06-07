package com.google.android.material.floatingactionbutton;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;

/* compiled from: BorderDrawable.java */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
class c extends Drawable {
    @Dimension
    float a;
    @ColorInt
    private int i;
    @ColorInt
    private int j;
    @ColorInt
    private int k;
    @ColorInt
    private int l;
    @ColorInt
    private int m;
    private ShapeAppearanceModel o;
    @Nullable
    private ColorStateList p;
    private final ShapeAppearancePathProvider b = ShapeAppearancePathProvider.getInstance();
    private final Path d = new Path();
    private final Rect e = new Rect();
    private final RectF f = new RectF();
    private final RectF g = new RectF();
    private final a h = new a();
    private boolean n = true;
    @NonNull
    private final Paint c = new Paint(1);

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(ShapeAppearanceModel shapeAppearanceModel) {
        this.o = shapeAppearanceModel;
        this.c.setStyle(Paint.Style.STROKE);
    }

    public void a(@Dimension float f) {
        if (this.a != f) {
            this.a = f;
            this.c.setStrokeWidth(f * 1.3333f);
            this.n = true;
            invalidateSelf();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.m = colorStateList.getColorForState(getState(), this.m);
        }
        this.p = colorStateList;
        this.n = true;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.c.setColorFilter(colorFilter);
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@ColorInt int i, @ColorInt int i2, @ColorInt int i3, @ColorInt int i4) {
        this.i = i;
        this.j = i2;
        this.k = i3;
        this.l = i4;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        if (this.n) {
            this.c.setShader(b());
            this.n = false;
        }
        float strokeWidth = this.c.getStrokeWidth() / 2.0f;
        copyBounds(this.e);
        this.f.set(this.e);
        float min = Math.min(this.o.getTopLeftCornerSize().getCornerSize(a()), this.f.width() / 2.0f);
        if (this.o.isRoundRect(a())) {
            this.f.inset(strokeWidth, strokeWidth);
            canvas.drawRoundRect(this.f, min, min, this.c);
        }
    }

    @Override // android.graphics.drawable.Drawable
    @TargetApi(21)
    public void getOutline(@NonNull Outline outline) {
        if (this.o.isRoundRect(a())) {
            outline.setRoundRect(getBounds(), this.o.getTopLeftCornerSize().getCornerSize(a()));
            return;
        }
        copyBounds(this.e);
        this.f.set(this.e);
        this.b.calculatePath(this.o, 1.0f, this.f, this.d);
        if (this.d.isConvex()) {
            outline.setConvexPath(this.d);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(@NonNull Rect rect) {
        if (!this.o.isRoundRect(a())) {
            return true;
        }
        int round = Math.round(this.a);
        rect.set(round, round, round, round);
        return true;
    }

    @NonNull
    protected RectF a() {
        this.g.set(getBounds());
        return this.g;
    }

    public void a(ShapeAppearanceModel shapeAppearanceModel) {
        this.o = shapeAppearanceModel;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(@IntRange(from = 0, to = 255) int i) {
        this.c.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.a > 0.0f ? -3 : -2;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        this.n = true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ColorStateList colorStateList = this.p;
        return (colorStateList != null && colorStateList.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        int colorForState;
        ColorStateList colorStateList = this.p;
        if (!(colorStateList == null || (colorForState = colorStateList.getColorForState(iArr, this.m)) == this.m)) {
            this.n = true;
            this.m = colorForState;
        }
        if (this.n) {
            invalidateSelf();
        }
        return this.n;
    }

    @NonNull
    private Shader b() {
        Rect rect = this.e;
        copyBounds(rect);
        float height = this.a / rect.height();
        return new LinearGradient(0.0f, rect.top, 0.0f, rect.bottom, new int[]{ColorUtils.compositeColors(this.i, this.m), ColorUtils.compositeColors(this.j, this.m), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.j, 0), this.m), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.l, 0), this.m), ColorUtils.compositeColors(this.l, this.m), ColorUtils.compositeColors(this.k, this.m)}, new float[]{0.0f, height, 0.5f, 0.5f, 1.0f - height, 1.0f}, Shader.TileMode.CLAMP);
    }

    @Override // android.graphics.drawable.Drawable
    @Nullable
    public Drawable.ConstantState getConstantState() {
        return this.h;
    }

    /* compiled from: BorderDrawable.java */
    /* loaded from: classes2.dex */
    private class a extends Drawable.ConstantState {
        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        private a() {
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return c.this;
        }
    }
}
