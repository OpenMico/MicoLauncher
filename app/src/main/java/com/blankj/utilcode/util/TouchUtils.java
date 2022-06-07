package com.blankj.utilcode.util;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class TouchUtils {
    public static final int DOWN = 8;
    public static final int LEFT = 1;
    public static final int RIGHT = 4;
    public static final int UNKNOWN = 0;
    public static final int UP = 2;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Direction {
    }

    private TouchUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setOnTouchListener(View view, OnTouchUtilsListener onTouchUtilsListener) {
        if (view != null && onTouchUtilsListener != null) {
            view.setOnTouchListener(onTouchUtilsListener);
        }
    }

    /* loaded from: classes.dex */
    public static abstract class OnTouchUtilsListener implements View.OnTouchListener {
        private int a;
        private int b;
        private int c;
        private int d;
        private int e;
        private int f;
        private int g;
        private VelocityTracker h;
        private int i;
        private int j;

        public abstract boolean onDown(View view, int i, int i2, MotionEvent motionEvent);

        public abstract boolean onMove(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, MotionEvent motionEvent);

        public abstract boolean onStop(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, MotionEvent motionEvent);

        public OnTouchUtilsListener() {
            a(-1, -1);
        }

        private void a(int i, int i2) {
            this.b = i;
            this.c = i2;
            this.d = i;
            this.e = i2;
            this.f = 0;
            this.g = 0;
            VelocityTracker velocityTracker = this.h;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (this.a == 0) {
                this.a = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            }
            if (this.i == 0) {
                this.i = ViewConfiguration.get(view.getContext()).getScaledMaximumFlingVelocity();
            }
            if (this.j == 0) {
                this.j = ViewConfiguration.get(view.getContext()).getScaledMinimumFlingVelocity();
            }
            if (this.h == null) {
                this.h = VelocityTracker.obtain();
            }
            this.h.addMovement(motionEvent);
            switch (motionEvent.getAction()) {
                case 0:
                    return onUtilsDown(view, motionEvent);
                case 1:
                case 3:
                    return onUtilsStop(view, motionEvent);
                case 2:
                    return onUtilsMove(view, motionEvent);
                default:
                    return false;
            }
        }

        public boolean onUtilsDown(View view, MotionEvent motionEvent) {
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            a(rawX, rawY);
            view.setPressed(true);
            return onDown(view, rawX, rawY, motionEvent);
        }

        public boolean onUtilsMove(View view, MotionEvent motionEvent) {
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            if (this.b == -1) {
                a(rawX, rawY);
                view.setPressed(true);
            }
            if (this.f != 1) {
                if (Math.abs(rawX - this.d) < this.a && Math.abs(rawY - this.e) < this.a) {
                    return true;
                }
                this.f = 1;
                if (Math.abs(rawX - this.d) >= Math.abs(rawY - this.e)) {
                    if (rawX - this.d < 0) {
                        this.g = 1;
                    } else {
                        this.g = 4;
                    }
                } else if (rawY - this.e < 0) {
                    this.g = 2;
                } else {
                    this.g = 8;
                }
            }
            boolean onMove = onMove(view, this.g, rawX, rawY, rawX - this.d, rawY - this.e, rawX - this.b, rawY - this.c, motionEvent);
            this.d = rawX;
            this.e = rawY;
            return onMove;
        }

        public boolean onUtilsStop(View view, MotionEvent motionEvent) {
            int i;
            int i2;
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            VelocityTracker velocityTracker = this.h;
            if (velocityTracker != null) {
                velocityTracker.computeCurrentVelocity(1000, this.i);
                i2 = (int) this.h.getXVelocity();
                i = (int) this.h.getYVelocity();
                this.h.recycle();
                if (Math.abs(i2) < this.j) {
                    i2 = 0;
                }
                if (Math.abs(i) < this.j) {
                    i = 0;
                }
                this.h = null;
            } else {
                i2 = 0;
                i = 0;
            }
            view.setPressed(false);
            boolean onStop = onStop(view, this.g, rawX, rawY, rawX - this.b, rawY - this.c, i2, i, motionEvent);
            if (motionEvent.getAction() == 1 && this.f == 0) {
                if (motionEvent.getEventTime() - motionEvent.getDownTime() <= 1000) {
                    view.performClick();
                } else {
                    view.performLongClick();
                }
            }
            a(-1, -1);
            return onStop;
        }
    }
}
