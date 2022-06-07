package com.rd.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import com.rd.animation.data.Value;
import com.rd.animation.data.type.WormAnimationValue;
import com.rd.draw.data.Indicator;
import com.rd.draw.data.Orientation;

/* loaded from: classes2.dex */
public class WormDrawer extends a {
    public RectF rect = new RectF();

    public WormDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        super(paint, indicator);
    }

    public void draw(@NonNull Canvas canvas, @NonNull Value value, int i, int i2) {
        if (value instanceof WormAnimationValue) {
            WormAnimationValue wormAnimationValue = (WormAnimationValue) value;
            int rectStart = wormAnimationValue.getRectStart();
            int rectEnd = wormAnimationValue.getRectEnd();
            int radius = this.b.getRadius();
            int unselectedColor = this.b.getUnselectedColor();
            int selectedColor = this.b.getSelectedColor();
            if (this.b.getOrientation() == Orientation.HORIZONTAL) {
                RectF rectF = this.rect;
                rectF.left = rectStart;
                rectF.right = rectEnd;
                rectF.top = i2 - radius;
                rectF.bottom = i2 + radius;
            } else {
                RectF rectF2 = this.rect;
                rectF2.left = i - radius;
                rectF2.right = i + radius;
                rectF2.top = rectStart;
                rectF2.bottom = rectEnd;
            }
            this.a.setColor(unselectedColor);
            float f = i;
            float f2 = i2;
            float f3 = radius;
            canvas.drawCircle(f, f2, f3, this.a);
            this.a.setColor(selectedColor);
            canvas.drawRoundRect(this.rect, f3, f3, this.a);
        }
    }
}
