package com.google.android.material.shadow;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class ShadowRenderer {
    private static final int[] g = new int[3];
    private static final float[] h = {0.0f, 0.5f, 1.0f};
    private static final int[] i = new int[4];
    private static final float[] j = {0.0f, 0.0f, 0.5f, 1.0f};
    @NonNull
    private final Paint a;
    @NonNull
    private final Paint b;
    @NonNull
    private final Paint c;
    private int d;
    private int e;
    private int f;
    private final Path k;
    private Paint l;

    public ShadowRenderer() {
        this(ViewCompat.MEASURED_STATE_MASK);
    }

    public ShadowRenderer(int i2) {
        this.k = new Path();
        this.l = new Paint();
        this.a = new Paint();
        setShadowColor(i2);
        this.l.setColor(0);
        this.b = new Paint(4);
        this.b.setStyle(Paint.Style.FILL);
        this.c = new Paint(this.b);
    }

    public void setShadowColor(int i2) {
        this.d = ColorUtils.setAlphaComponent(i2, 68);
        this.e = ColorUtils.setAlphaComponent(i2, 20);
        this.f = ColorUtils.setAlphaComponent(i2, 0);
        this.a.setColor(this.d);
    }

    public void drawEdgeShadow(@NonNull Canvas canvas, @Nullable Matrix matrix, @NonNull RectF rectF, int i2) {
        rectF.bottom += i2;
        rectF.offset(0.0f, -i2);
        int[] iArr = g;
        iArr[0] = this.f;
        iArr[1] = this.e;
        iArr[2] = this.d;
        this.c.setShader(new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, g, h, Shader.TileMode.CLAMP));
        canvas.save();
        canvas.concat(matrix);
        canvas.drawRect(rectF, this.c);
        canvas.restore();
    }

    public void drawCornerShadow(@NonNull Canvas canvas, @Nullable Matrix matrix, @NonNull RectF rectF, int i2, float f, float f2) {
        boolean z = f2 < 0.0f;
        Path path = this.k;
        if (z) {
            int[] iArr = i;
            iArr[0] = 0;
            iArr[1] = this.f;
            iArr[2] = this.e;
            iArr[3] = this.d;
        } else {
            path.rewind();
            path.moveTo(rectF.centerX(), rectF.centerY());
            path.arcTo(rectF, f, f2);
            path.close();
            float f3 = -i2;
            rectF.inset(f3, f3);
            int[] iArr2 = i;
            iArr2[0] = 0;
            iArr2[1] = this.d;
            iArr2[2] = this.e;
            iArr2[3] = this.f;
        }
        float width = rectF.width() / 2.0f;
        if (width > 0.0f) {
            float f4 = 1.0f - (i2 / width);
            float[] fArr = j;
            fArr[1] = f4;
            fArr[2] = ((1.0f - f4) / 2.0f) + f4;
            this.b.setShader(new RadialGradient(rectF.centerX(), rectF.centerY(), width, i, j, Shader.TileMode.CLAMP));
            canvas.save();
            canvas.concat(matrix);
            canvas.scale(1.0f, rectF.height() / rectF.width());
            if (!z) {
                canvas.clipPath(path, Region.Op.DIFFERENCE);
                canvas.drawPath(path, this.l);
            }
            canvas.drawArc(rectF, f, f2, true, this.b);
            canvas.restore();
        }
    }

    @NonNull
    public Paint getShadowPaint() {
        return this.a;
    }
}
