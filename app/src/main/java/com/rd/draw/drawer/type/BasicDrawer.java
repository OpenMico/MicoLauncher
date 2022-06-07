package com.rd.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.rd.animation.type.AnimationType;
import com.rd.draw.data.Indicator;

/* loaded from: classes2.dex */
public class BasicDrawer extends a {
    private Paint c = new Paint();

    public BasicDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        super(paint, indicator);
        this.c.setStyle(Paint.Style.STROKE);
        this.c.setAntiAlias(true);
        this.c.setStrokeWidth(indicator.getStroke());
    }

    public void draw(@NonNull Canvas canvas, int i, boolean z, int i2, int i3) {
        Paint paint;
        float radius = this.b.getRadius();
        int stroke = this.b.getStroke();
        float scaleFactor = this.b.getScaleFactor();
        int selectedColor = this.b.getSelectedColor();
        selectedColor = this.b.getUnselectedColor();
        int selectedPosition = this.b.getSelectedPosition();
        AnimationType animationType = this.b.getAnimationType();
        if (animationType == AnimationType.SCALE && !z) {
            radius *= scaleFactor;
        } else if (animationType == AnimationType.SCALE_DOWN && z) {
            radius *= scaleFactor;
        }
        if (i != selectedPosition) {
        }
        if (animationType != AnimationType.FILL || i == selectedPosition) {
            paint = this.a;
        } else {
            paint = this.c;
            paint.setStrokeWidth(stroke);
        }
        paint.setColor(selectedColor);
        canvas.drawCircle(i2, i3, radius, paint);
    }
}
