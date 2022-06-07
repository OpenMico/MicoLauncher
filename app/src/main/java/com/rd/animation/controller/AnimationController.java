package com.rd.animation.controller;

import androidx.annotation.NonNull;
import com.rd.animation.controller.ValueController;
import com.rd.animation.type.BaseAnimation;
import com.rd.animation.type.DropAnimation;
import com.rd.animation.type.WormAnimation;
import com.rd.draw.data.Indicator;
import com.rd.draw.data.Orientation;
import com.rd.utils.CoordinatesUtils;

/* loaded from: classes2.dex */
public class AnimationController {
    private ValueController a;
    private ValueController.UpdateListener b;
    private BaseAnimation c;
    private Indicator d;
    private float e;
    private boolean f;

    public AnimationController(@NonNull Indicator indicator, @NonNull ValueController.UpdateListener updateListener) {
        this.a = new ValueController(updateListener);
        this.b = updateListener;
        this.d = indicator;
    }

    public void interactive(float f) {
        this.f = true;
        this.e = f;
        a();
    }

    public void basic() {
        this.f = false;
        this.e = 0.0f;
        a();
    }

    public void end() {
        BaseAnimation baseAnimation = this.c;
        if (baseAnimation != null) {
            baseAnimation.end();
        }
    }

    private void a() {
        switch (this.d.getAnimationType()) {
            case NONE:
                this.b.onValueUpdated(null);
                return;
            case COLOR:
                b();
                return;
            case SCALE:
                c();
                return;
            case WORM:
                d();
                return;
            case FILL:
                f();
                return;
            case SLIDE:
                e();
                return;
            case THIN_WORM:
                g();
                return;
            case DROP:
                h();
                return;
            case SWAP:
                i();
                return;
            case SCALE_DOWN:
                j();
                return;
            default:
                return;
        }
    }

    private void b() {
        int selectedColor = this.d.getSelectedColor();
        int unselectedColor = this.d.getUnselectedColor();
        BaseAnimation duration = this.a.color().with(unselectedColor, selectedColor).duration(this.d.getAnimationDuration());
        if (this.f) {
            duration.progress(this.e);
        } else {
            duration.start();
        }
        this.c = duration;
    }

    private void c() {
        int selectedColor = this.d.getSelectedColor();
        int unselectedColor = this.d.getUnselectedColor();
        int radius = this.d.getRadius();
        float scaleFactor = this.d.getScaleFactor();
        BaseAnimation duration = this.a.scale().with(unselectedColor, selectedColor, radius, scaleFactor).duration(this.d.getAnimationDuration());
        if (this.f) {
            duration.progress(this.e);
        } else {
            duration.start();
        }
        this.c = duration;
    }

    private void d() {
        int selectedPosition = this.d.isInteractiveAnimation() ? this.d.getSelectedPosition() : this.d.getLastSelectedPosition();
        int selectingPosition = this.d.isInteractiveAnimation() ? this.d.getSelectingPosition() : this.d.getSelectedPosition();
        WormAnimation duration = this.a.worm().with(CoordinatesUtils.getCoordinate(this.d, selectedPosition), CoordinatesUtils.getCoordinate(this.d, selectingPosition), this.d.getRadius(), selectingPosition > selectedPosition).duration(this.d.getAnimationDuration());
        if (this.f) {
            duration.progress(this.e);
        } else {
            duration.start();
        }
        this.c = duration;
    }

    private void e() {
        BaseAnimation duration = this.a.slide().with(CoordinatesUtils.getCoordinate(this.d, this.d.isInteractiveAnimation() ? this.d.getSelectedPosition() : this.d.getLastSelectedPosition()), CoordinatesUtils.getCoordinate(this.d, this.d.isInteractiveAnimation() ? this.d.getSelectingPosition() : this.d.getSelectedPosition())).duration(this.d.getAnimationDuration());
        if (this.f) {
            duration.progress(this.e);
        } else {
            duration.start();
        }
        this.c = duration;
    }

    private void f() {
        int selectedColor = this.d.getSelectedColor();
        int unselectedColor = this.d.getUnselectedColor();
        int radius = this.d.getRadius();
        int stroke = this.d.getStroke();
        BaseAnimation duration = this.a.fill().with(unselectedColor, selectedColor, radius, stroke).duration(this.d.getAnimationDuration());
        if (this.f) {
            duration.progress(this.e);
        } else {
            duration.start();
        }
        this.c = duration;
    }

    private void g() {
        int selectedPosition = this.d.isInteractiveAnimation() ? this.d.getSelectedPosition() : this.d.getLastSelectedPosition();
        int selectingPosition = this.d.isInteractiveAnimation() ? this.d.getSelectingPosition() : this.d.getSelectedPosition();
        WormAnimation duration = this.a.thinWorm().with(CoordinatesUtils.getCoordinate(this.d, selectedPosition), CoordinatesUtils.getCoordinate(this.d, selectingPosition), this.d.getRadius(), selectingPosition > selectedPosition).duration(this.d.getAnimationDuration());
        if (this.f) {
            duration.progress(this.e);
        } else {
            duration.start();
        }
        this.c = duration;
    }

    private void h() {
        int selectedPosition = this.d.isInteractiveAnimation() ? this.d.getSelectedPosition() : this.d.getLastSelectedPosition();
        int selectingPosition = this.d.isInteractiveAnimation() ? this.d.getSelectingPosition() : this.d.getSelectedPosition();
        int coordinate = CoordinatesUtils.getCoordinate(this.d, selectedPosition);
        int coordinate2 = CoordinatesUtils.getCoordinate(this.d, selectingPosition);
        int paddingTop = this.d.getPaddingTop();
        paddingTop = this.d.getPaddingLeft();
        if (this.d.getOrientation() != Orientation.HORIZONTAL) {
        }
        int radius = this.d.getRadius();
        DropAnimation with = this.a.drop().duration(this.d.getAnimationDuration()).with(coordinate, coordinate2, (radius * 3) + paddingTop, radius + paddingTop, radius);
        if (this.f) {
            with.progress(this.e);
        } else {
            with.start();
        }
        this.c = with;
    }

    private void i() {
        BaseAnimation duration = this.a.swap().with(CoordinatesUtils.getCoordinate(this.d, this.d.isInteractiveAnimation() ? this.d.getSelectedPosition() : this.d.getLastSelectedPosition()), CoordinatesUtils.getCoordinate(this.d, this.d.isInteractiveAnimation() ? this.d.getSelectingPosition() : this.d.getSelectedPosition())).duration(this.d.getAnimationDuration());
        if (this.f) {
            duration.progress(this.e);
        } else {
            duration.start();
        }
        this.c = duration;
    }

    private void j() {
        int selectedColor = this.d.getSelectedColor();
        int unselectedColor = this.d.getUnselectedColor();
        int radius = this.d.getRadius();
        float scaleFactor = this.d.getScaleFactor();
        BaseAnimation duration = this.a.scaleDown().with(unselectedColor, selectedColor, radius, scaleFactor).duration(this.d.getAnimationDuration());
        if (this.f) {
            duration.progress(this.e);
        } else {
            duration.start();
        }
        this.c = duration;
    }
}
