package com.rd.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.rd.animation.data.Value;
import com.rd.animation.data.type.ThinWormAnimationValue;
import com.rd.draw.data.Indicator;
import com.rd.draw.data.Orientation;

/* loaded from: classes2.dex */
public class ThinWormDrawer extends WormDrawer {
    public ThinWormDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        super(paint, indicator);
    }

    @Override // com.rd.draw.drawer.type.WormDrawer
    public void draw(@NonNull Canvas canvas, @NonNull Value value, int i, int i2) {
        if (value instanceof ThinWormAnimationValue) {
            ThinWormAnimationValue thinWormAnimationValue = (ThinWormAnimationValue) value;
            int rectStart = thinWormAnimationValue.getRectStart();
            int rectEnd = thinWormAnimationValue.getRectEnd();
            int height = thinWormAnimationValue.getHeight() / 2;
            int radius = this.b.getRadius();
            int unselectedColor = this.b.getUnselectedColor();
            int selectedColor = this.b.getSelectedColor();
            if (this.b.getOrientation() == Orientation.HORIZONTAL) {
                this.rect.left = rectStart;
                this.rect.right = rectEnd;
                this.rect.top = i2 - height;
                this.rect.bottom = height + i2;
            } else {
                this.rect.left = i - height;
                this.rect.right = height + i;
                this.rect.top = rectStart;
                this.rect.bottom = rectEnd;
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
