package com.rd.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.rd.animation.data.Value;
import com.rd.animation.data.type.ColorAnimationValue;
import com.rd.draw.data.Indicator;

/* loaded from: classes2.dex */
public class ColorDrawer extends a {
    public ColorDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        super(paint, indicator);
    }

    public void draw(@NonNull Canvas canvas, @NonNull Value value, int i, int i2, int i3) {
        if (value instanceof ColorAnimationValue) {
            ColorAnimationValue colorAnimationValue = (ColorAnimationValue) value;
            float radius = this.b.getRadius();
            int selectedColor = this.b.getSelectedColor();
            int selectedPosition = this.b.getSelectedPosition();
            int selectingPosition = this.b.getSelectingPosition();
            int lastSelectedPosition = this.b.getLastSelectedPosition();
            if (this.b.isInteractiveAnimation()) {
                if (i == selectingPosition) {
                    selectedColor = colorAnimationValue.getColor();
                } else if (i == selectedPosition) {
                    selectedColor = colorAnimationValue.getColorReverse();
                }
            } else if (i == selectedPosition) {
                selectedColor = colorAnimationValue.getColor();
            } else if (i == lastSelectedPosition) {
                selectedColor = colorAnimationValue.getColorReverse();
            }
            this.a.setColor(selectedColor);
            canvas.drawCircle(i2, i3, radius, this.a);
        }
    }
}
