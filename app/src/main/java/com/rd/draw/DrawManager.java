package com.rd.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.rd.animation.data.Value;
import com.rd.draw.controller.AttributeController;
import com.rd.draw.controller.DrawController;
import com.rd.draw.controller.MeasureController;
import com.rd.draw.data.Indicator;

/* loaded from: classes2.dex */
public class DrawManager {
    private Indicator a = new Indicator();
    private DrawController b = new DrawController(this.a);
    private MeasureController c = new MeasureController();
    private AttributeController d = new AttributeController(this.a);

    @NonNull
    public Indicator indicator() {
        if (this.a == null) {
            this.a = new Indicator();
        }
        return this.a;
    }

    public void setClickListener(@Nullable DrawController.ClickListener clickListener) {
        this.b.setClickListener(clickListener);
    }

    public void touch(@Nullable MotionEvent motionEvent) {
        this.b.touch(motionEvent);
    }

    public void updateValue(@Nullable Value value) {
        this.b.updateValue(value);
    }

    public void draw(@NonNull Canvas canvas) {
        this.b.draw(canvas);
    }

    public Pair<Integer, Integer> measureViewSize(int i, int i2) {
        return this.c.measureViewSize(this.a, i, i2);
    }

    public void initAttributes(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this.d.init(context, attributeSet);
    }
}
