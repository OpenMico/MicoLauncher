package com.rd.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.rd.animation.data.Value;
import com.rd.animation.data.type.DropAnimationValue;
import com.rd.draw.data.Indicator;
import com.rd.draw.data.Orientation;

/* loaded from: classes2.dex */
public class DropDrawer extends a {
    public DropDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        super(paint, indicator);
    }

    public void draw(@NonNull Canvas canvas, @NonNull Value value, int i, int i2) {
        if (value instanceof DropAnimationValue) {
            DropAnimationValue dropAnimationValue = (DropAnimationValue) value;
            int unselectedColor = this.b.getUnselectedColor();
            int selectedColor = this.b.getSelectedColor();
            this.a.setColor(unselectedColor);
            canvas.drawCircle(i, i2, this.b.getRadius(), this.a);
            this.a.setColor(selectedColor);
            if (this.b.getOrientation() == Orientation.HORIZONTAL) {
                canvas.drawCircle(dropAnimationValue.getWidth(), dropAnimationValue.getHeight(), dropAnimationValue.getRadius(), this.a);
            } else {
                canvas.drawCircle(dropAnimationValue.getHeight(), dropAnimationValue.getWidth(), dropAnimationValue.getRadius(), this.a);
            }
        }
    }
}
