package com.bumptech.glide.request.target;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes.dex */
public class FixedSizeDrawable extends Drawable {
    private final Matrix a;
    private final RectF b;
    private final RectF c;
    private Drawable d;
    private a e;
    private boolean f;

    public FixedSizeDrawable(Drawable drawable, int i, int i2) {
        this(new a(drawable.getConstantState(), i, i2), drawable);
    }

    FixedSizeDrawable(a aVar, Drawable drawable) {
        this.e = (a) Preconditions.checkNotNull(aVar);
        this.d = (Drawable) Preconditions.checkNotNull(drawable);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        this.a = new Matrix();
        this.b = new RectF(0.0f, 0.0f, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        this.c = new RectF();
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        this.c.set(i, i2, i3, i4);
        a();
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(@NonNull Rect rect) {
        super.setBounds(rect);
        this.c.set(rect);
        a();
    }

    private void a() {
        this.a.setRectToRect(this.b, this.c, Matrix.ScaleToFit.CENTER);
    }

    @Override // android.graphics.drawable.Drawable
    public void setChangingConfigurations(int i) {
        this.d.setChangingConfigurations(i);
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.d.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    @Deprecated
    public void setDither(boolean z) {
        this.d.setDither(z);
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.d.setFilterBitmap(z);
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.Callback getCallback() {
        return this.d.getCallback();
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(19)
    public int getAlpha() {
        return this.d.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(int i, @NonNull PorterDuff.Mode mode) {
        this.d.setColorFilter(i, mode);
    }

    @Override // android.graphics.drawable.Drawable
    public void clearColorFilter() {
        this.d.clearColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    public Drawable getCurrent() {
        return this.d.getCurrent();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        return this.d.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.e.a;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.e.b;
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return this.d.getMinimumWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return this.d.getMinimumHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(@NonNull Rect rect) {
        return this.d.getPadding(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        super.invalidateSelf();
        this.d.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void unscheduleSelf(@NonNull Runnable runnable) {
        super.unscheduleSelf(runnable);
        this.d.unscheduleSelf(runnable);
    }

    @Override // android.graphics.drawable.Drawable
    public void scheduleSelf(@NonNull Runnable runnable, long j) {
        super.scheduleSelf(runnable, j);
        this.d.scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        canvas.concat(this.a);
        this.d.draw(canvas);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.d.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.d.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.d.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    public Drawable mutate() {
        if (!this.f && super.mutate() == this) {
            this.d = this.d.mutate();
            this.e = new a(this.e);
            this.f = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.e;
    }

    /* loaded from: classes.dex */
    static final class a extends Drawable.ConstantState {
        final int a;
        final int b;
        private final Drawable.ConstantState c;

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        a(a aVar) {
            this(aVar.c, aVar.a, aVar.b);
        }

        a(Drawable.ConstantState constantState, int i, int i2) {
            this.c = constantState;
            this.a = i;
            this.b = i2;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return new FixedSizeDrawable(this, this.c.newDrawable());
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable(Resources resources) {
            return new FixedSizeDrawable(this, this.c.newDrawable(resources));
        }
    }
}
