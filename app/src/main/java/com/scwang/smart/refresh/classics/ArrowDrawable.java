package com.scwang.smart.refresh.classics;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.scwang.smart.drawable.PaintDrawable;

/* loaded from: classes2.dex */
public class ArrowDrawable extends PaintDrawable {
    private int a = 0;
    private int b = 0;
    private Path c = new Path();

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();
        if (!(this.a == width && this.b == height)) {
            this.c.reset();
            float f = (width * 30) / 225;
            float f2 = f * 0.70710677f;
            float f3 = f / 0.70710677f;
            float f4 = width;
            float f5 = f4 / 2.0f;
            float f6 = height;
            this.c.moveTo(f5, f6);
            float f7 = f6 / 2.0f;
            this.c.lineTo(0.0f, f7);
            float f8 = f7 - f2;
            this.c.lineTo(f2, f8);
            float f9 = f / 2.0f;
            float f10 = f5 - f9;
            float f11 = (f6 - f3) - f9;
            this.c.lineTo(f10, f11);
            this.c.lineTo(f10, 0.0f);
            float f12 = f5 + f9;
            this.c.lineTo(f12, 0.0f);
            this.c.lineTo(f12, f11);
            this.c.lineTo(f4 - f2, f8);
            this.c.lineTo(f4, f7);
            this.c.close();
            this.a = width;
            this.b = height;
        }
        canvas.drawPath(this.c, this.mPaint);
    }
}
