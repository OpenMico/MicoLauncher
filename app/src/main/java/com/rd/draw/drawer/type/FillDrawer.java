package com.rd.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.rd.animation.data.Value;
import com.rd.animation.data.type.FillAnimationValue;
import com.rd.draw.data.Indicator;

/* loaded from: classes2.dex */
public class FillDrawer extends a {
    private Paint c = new Paint();

    public FillDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        super(paint, indicator);
        this.c.setStyle(Paint.Style.STROKE);
        this.c.setAntiAlias(true);
    }

    public void draw(@NonNull Canvas canvas, @NonNull Value value, int i, int i2, int i3) {
        if (value instanceof FillAnimationValue) {
            FillAnimationValue fillAnimationValue = (FillAnimationValue) value;
            int unselectedColor = this.b.getUnselectedColor();
            float radius = this.b.getRadius();
            int stroke = this.b.getStroke();
            int selectedPosition = this.b.getSelectedPosition();
            int selectingPosition = this.b.getSelectingPosition();
            int lastSelectedPosition = this.b.getLastSelectedPosition();
            if (this.b.isInteractiveAnimation()) {
                if (i == selectingPosition) {
                    unselectedColor = fillAnimationValue.getColor();
                    radius = fillAnimationValue.getRadius();
                    stroke = fillAnimationValue.getStroke();
                } else if (i == selectedPosition) {
                    unselectedColor = fillAnimationValue.getColorReverse();
                    radius = fillAnimationValue.getRadiusReverse();
                    stroke = fillAnimationValue.getStrokeReverse();
                }
            } else if (i == selectedPosition) {
                unselectedColor = fillAnimationValue.getColor();
                radius = fillAnimationValue.getRadius();
                stroke = fillAnimationValue.getStroke();
            } else if (i == lastSelectedPosition) {
                unselectedColor = fillAnimationValue.getColorReverse();
                radius = fillAnimationValue.getRadiusReverse();
                stroke = fillAnimationValue.getStrokeReverse();
            }
            this.c.setColor(unselectedColor);
            this.c.setStrokeWidth(this.b.getStroke());
            float f = i2;
            float f2 = i3;
            canvas.drawCircle(f, f2, this.b.getRadius(), this.c);
            this.c.setStrokeWidth(stroke);
            canvas.drawCircle(f, f2, radius, this.c);
        }
    }
}
