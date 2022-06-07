package com.google.android.exoplayer2.video.spherical;

import android.content.Context;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.BinderThread;
import com.google.android.exoplayer2.video.spherical.OrientationListener;

/* loaded from: classes2.dex */
final class TouchTracker extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener, OrientationListener.Listener {
    private final Listener c;
    private final float d;
    private final GestureDetector e;
    private final PointF a = new PointF();
    private final PointF b = new PointF();
    private volatile float f = 3.1415927f;

    /* loaded from: classes2.dex */
    public interface Listener {
        void onScrollChange(PointF pointF);

        default boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }
    }

    public TouchTracker(Context context, Listener listener, float f) {
        this.c = listener;
        this.d = f;
        this.e = new GestureDetector(context, this);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.e.onTouchEvent(motionEvent);
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        this.a.set(motionEvent.getX(), motionEvent.getY());
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        float x = (motionEvent2.getX() - this.a.x) / this.d;
        float y = (motionEvent2.getY() - this.a.y) / this.d;
        this.a.set(motionEvent2.getX(), motionEvent2.getY());
        double d = this.f;
        float cos = (float) Math.cos(d);
        float sin = (float) Math.sin(d);
        this.b.x -= (cos * x) - (sin * y);
        this.b.y += (sin * x) + (cos * y);
        PointF pointF = this.b;
        pointF.y = Math.max(-45.0f, Math.min(45.0f, pointF.y));
        this.c.onScrollChange(this.b);
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return this.c.onSingleTapUp(motionEvent);
    }

    @Override // com.google.android.exoplayer2.video.spherical.OrientationListener.Listener
    @BinderThread
    public void onOrientationChange(float[] fArr, float f) {
        this.f = -f;
    }
}
