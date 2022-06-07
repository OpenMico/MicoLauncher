package com.xiaomi.micolauncher.module.homepage.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public class GravityCompoundDrawable extends Drawable {
    private final Drawable a;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public GravityCompoundDrawable(Drawable drawable) {
        this.a = drawable;
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
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(0.0f, ((canvas.getHeight() / 2) - (this.a.getIntrinsicHeight() / 2)) - 6);
        this.a.draw(canvas);
        canvas.restore();
    }
}
