package com.google.android.material.textfield;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CutoutDrawable.java */
/* loaded from: classes2.dex */
public class c extends MaterialShapeDrawable {
    @NonNull
    private final Paint a;
    @NonNull
    private final RectF b;
    private int c;

    c() {
        this(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(@Nullable ShapeAppearanceModel shapeAppearanceModel) {
        super(shapeAppearanceModel == null ? new ShapeAppearanceModel() : shapeAppearanceModel);
        this.a = new Paint(1);
        c();
        this.b = new RectF();
    }

    private void c() {
        this.a.setStyle(Paint.Style.FILL_AND_STROKE);
        this.a.setColor(-1);
        this.a.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return !this.b.isEmpty();
    }

    void a(float f, float f2, float f3, float f4) {
        if (f != this.b.left || f2 != this.b.top || f3 != this.b.right || f4 != this.b.bottom) {
            this.b.set(f, f2, f3, f4);
            invalidateSelf();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull RectF rectF) {
        a(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        a(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        a(canvas);
        super.draw(canvas);
        canvas.drawRect(this.b, this.a);
        c(canvas);
    }

    private void a(@NonNull Canvas canvas) {
        Drawable.Callback callback = getCallback();
        if (a(callback)) {
            View view = (View) callback;
            if (view.getLayerType() != 2) {
                view.setLayerType(2, null);
                return;
            }
            return;
        }
        b(canvas);
    }

    private void b(@NonNull Canvas canvas) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.c = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null);
        } else {
            this.c = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, 31);
        }
    }

    private void c(@NonNull Canvas canvas) {
        if (!a(getCallback())) {
            canvas.restoreToCount(this.c);
        }
    }

    private boolean a(Drawable.Callback callback) {
        return callback instanceof View;
    }
}
