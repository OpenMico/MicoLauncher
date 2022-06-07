package com.rd.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.rd.animation.data.Value;
import com.rd.animation.data.type.ScaleAnimationValue;
import com.rd.draw.data.Indicator;

/* loaded from: classes2.dex */
public class ScaleDrawer extends a {
    public ScaleDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        super(paint, indicator);
    }

    public void draw(@NonNull Canvas canvas, @NonNull Value value, int i, int i2, int i3) {
        if (value instanceof ScaleAnimationValue) {
            ScaleAnimationValue scaleAnimationValue = (ScaleAnimationValue) value;
            float radius = this.b.getRadius();
            int selectedColor = this.b.getSelectedColor();
            int selectedPosition = this.b.getSelectedPosition();
            int selectingPosition = this.b.getSelectingPosition();
            int lastSelectedPosition = this.b.getLastSelectedPosition();
            if (this.b.isInteractiveAnimation()) {
                if (i == selectingPosition) {
                    radius = scaleAnimationValue.getRadius();
                    selectedColor = scaleAnimationValue.getColor();
                } else if (i == selectedPosition) {
                    radius = scaleAnimationValue.getRadiusReverse();
                    selectedColor = scaleAnimationValue.getColorReverse();
                }
            } else if (i == selectedPosition) {
                radius = scaleAnimationValue.getRadius();
                selectedColor = scaleAnimationValue.getColor();
            } else if (i == lastSelectedPosition) {
                radius = scaleAnimationValue.getRadiusReverse();
                selectedColor = scaleAnimationValue.getColorReverse();
            }
            this.a.setColor(selectedColor);
            canvas.drawCircle(i2, i3, radius, this.a);
        }
    }
}
