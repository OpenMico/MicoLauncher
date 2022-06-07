package com.rd.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.rd.animation.data.Value;
import com.rd.animation.data.type.SwapAnimationValue;
import com.rd.draw.data.Indicator;
import com.rd.draw.data.Orientation;

/* loaded from: classes2.dex */
public class SwapDrawer extends a {
    public SwapDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        super(paint, indicator);
    }

    public void draw(@NonNull Canvas canvas, @NonNull Value value, int i, int i2, int i3) {
        if (value instanceof SwapAnimationValue) {
            SwapAnimationValue swapAnimationValue = (SwapAnimationValue) value;
            int selectedColor = this.b.getSelectedColor();
            int unselectedColor = this.b.getUnselectedColor();
            int radius = this.b.getRadius();
            int selectedPosition = this.b.getSelectedPosition();
            int selectingPosition = this.b.getSelectingPosition();
            int lastSelectedPosition = this.b.getLastSelectedPosition();
            int coordinate = swapAnimationValue.getCoordinate();
            if (this.b.isInteractiveAnimation()) {
                if (i == selectingPosition) {
                    coordinate = swapAnimationValue.getCoordinate();
                    unselectedColor = selectedColor;
                } else if (i == selectedPosition) {
                    coordinate = swapAnimationValue.getCoordinateReverse();
                }
            } else if (i == lastSelectedPosition) {
                coordinate = swapAnimationValue.getCoordinate();
                unselectedColor = selectedColor;
            } else if (i == selectedPosition) {
                coordinate = swapAnimationValue.getCoordinateReverse();
            }
            this.a.setColor(unselectedColor);
            if (this.b.getOrientation() == Orientation.HORIZONTAL) {
                canvas.drawCircle(coordinate, i3, radius, this.a);
            } else {
                canvas.drawCircle(i2, coordinate, radius, this.a);
            }
        }
    }
}
