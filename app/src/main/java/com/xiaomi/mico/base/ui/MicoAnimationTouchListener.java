package com.xiaomi.mico.base.ui;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/* loaded from: classes3.dex */
public class MicoAnimationTouchListener implements View.OnTouchListener {
    private static final MicoAnimationTouchListener a = new MicoAnimationTouchListener();
    private AccelerateDecelerateInterpolator b = new AccelerateDecelerateInterpolator();
    private ObjectAnimator c = ObjectAnimator.ofFloat((Object) null, "alpha", 0.0f, 1.0f);

    public static MicoAnimationTouchListener getInstance() {
        return a;
    }

    private MicoAnimationTouchListener() {
        this.c.setDuration(300L);
        this.c.setInterpolator(this.b);
    }

    @Override // android.view.View.OnTouchListener
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (this.c.isRunning()) {
            if (((View) this.c.getTarget()) != null) {
                this.c.setupEndValues();
            }
            this.c.cancel();
        }
        float a2 = a(view);
        this.c.setTarget(view);
        if (action != 3) {
            switch (action) {
                case 0:
                    this.c.setFloatValues(1.0f, a2);
                    break;
            }
            this.c.start();
            return false;
        }
        this.c.setFloatValues(a2, 1.0f);
        this.c.start();
        return false;
    }

    private static float a(View view) {
        int width = view.getWidth() * view.getHeight();
        if (width < 17424) {
            return 0.3f;
        }
        if (width > 367424) {
            return 0.90000004f;
        }
        return (((width - 17424) / 350000.0f) * 0.6f) + 0.3f;
    }
}
