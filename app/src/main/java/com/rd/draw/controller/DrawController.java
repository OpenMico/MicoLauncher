package com.rd.draw.controller;

import android.graphics.Canvas;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.rd.animation.data.Value;
import com.rd.draw.data.Indicator;
import com.rd.draw.drawer.Drawer;
import com.rd.utils.CoordinatesUtils;

/* loaded from: classes2.dex */
public class DrawController {
    private Value a;
    private Drawer b;
    private Indicator c;
    private ClickListener d;

    /* loaded from: classes2.dex */
    public interface ClickListener {
        void onIndicatorClicked(int i);
    }

    public DrawController(@NonNull Indicator indicator) {
        this.c = indicator;
        this.b = new Drawer(indicator);
    }

    public void updateValue(@Nullable Value value) {
        this.a = value;
    }

    public void setClickListener(@Nullable ClickListener clickListener) {
        this.d = clickListener;
    }

    public void touch(@Nullable MotionEvent motionEvent) {
        if (motionEvent != null && motionEvent.getAction() == 1) {
            a(motionEvent.getX(), motionEvent.getY());
        }
    }

    private void a(float f, float f2) {
        int position;
        if (this.d != null && (position = CoordinatesUtils.getPosition(this.c, f, f2)) >= 0) {
            this.d.onIndicatorClicked(position);
        }
    }

    public void draw(@NonNull Canvas canvas) {
        int count = this.c.getCount();
        for (int i = 0; i < count; i++) {
            a(canvas, i, CoordinatesUtils.getXCoordinate(this.c, i), CoordinatesUtils.getYCoordinate(this.c, i));
        }
    }

    private void a(@NonNull Canvas canvas, int i, int i2, int i3) {
        boolean isInteractiveAnimation = this.c.isInteractiveAnimation();
        int selectedPosition = this.c.getSelectedPosition();
        int selectingPosition = this.c.getSelectingPosition();
        boolean z = true;
        boolean z2 = !isInteractiveAnimation && (i == selectedPosition || i == this.c.getLastSelectedPosition());
        if (!isInteractiveAnimation || !(i == selectedPosition || i == selectingPosition)) {
            z = false;
        }
        boolean z3 = z2 | z;
        this.b.setup(i, i2, i3);
        if (this.a == null || !z3) {
            this.b.drawBasic(canvas, z3);
        } else {
            a(canvas);
        }
    }

    private void a(@NonNull Canvas canvas) {
        switch (this.c.getAnimationType()) {
            case NONE:
                this.b.drawBasic(canvas, true);
                return;
            case COLOR:
                this.b.drawColor(canvas, this.a);
                return;
            case SCALE:
                this.b.drawScale(canvas, this.a);
                return;
            case WORM:
                this.b.drawWorm(canvas, this.a);
                return;
            case SLIDE:
                this.b.drawSlide(canvas, this.a);
                return;
            case FILL:
                this.b.drawFill(canvas, this.a);
                return;
            case THIN_WORM:
                this.b.drawThinWorm(canvas, this.a);
                return;
            case DROP:
                this.b.drawDrop(canvas, this.a);
                return;
            case SWAP:
                this.b.drawSwap(canvas, this.a);
                return;
            case SCALE_DOWN:
                this.b.drawScaleDown(canvas, this.a);
                return;
            default:
                return;
        }
    }
}
