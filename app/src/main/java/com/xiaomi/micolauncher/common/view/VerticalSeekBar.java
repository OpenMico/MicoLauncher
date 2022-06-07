package com.xiaomi.micolauncher.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatSeekBar;

/* loaded from: classes3.dex */
public class VerticalSeekBar extends AppCompatSeekBar {
    float a;
    private boolean b;
    private float c;
    private int d;
    private boolean e = false;
    private SeekBar.OnSeekBarChangeListener f;

    @Override // android.widget.SeekBar
    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.f = onSeekBarChangeListener;
    }

    public boolean isInScrollingContainer() {
        return this.e;
    }

    public void setInScrollingContainer(boolean z) {
        this.e = z;
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.d = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VerticalSeekBar(Context context) {
        super(context);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i2, i, i4, i3);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i2, i);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public synchronized void onDraw(Canvas canvas) {
        canvas.rotate(-90.0f);
        canvas.translate(-getHeight(), 0.0f);
        super.onDraw(canvas);
    }

    @Override // android.widget.AbsSeekBar, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                if (!isInScrollingContainer()) {
                    setPressed(true);
                    invalidate();
                    a();
                    a(motionEvent);
                    c();
                    onSizeChanged(getWidth(), getHeight(), 0, 0);
                    break;
                } else {
                    this.c = motionEvent.getY();
                    break;
                }
            case 1:
                if (this.b) {
                    a(motionEvent);
                    b();
                    setPressed(false);
                } else {
                    a();
                    a(motionEvent);
                    b();
                }
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                invalidate();
                break;
            case 2:
                if (this.b) {
                    a(motionEvent);
                } else if (Math.abs(motionEvent.getY() - this.c) > this.d) {
                    setPressed(true);
                    invalidate();
                    a();
                    a(motionEvent);
                    c();
                }
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
        }
        return true;
    }

    private void a(MotionEvent motionEvent) {
        float f;
        int height = getHeight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i = (height - paddingTop) - paddingBottom;
        int y = (int) motionEvent.getY();
        int i2 = height - paddingBottom;
        float f2 = 0.0f;
        if (y > i2) {
            f = 0.0f;
        } else if (y < paddingTop) {
            f = 1.0f;
        } else {
            f = ((i - y) + paddingTop) / i;
            f2 = this.a;
        }
        setProgress((int) (f2 + (f * getMax())));
    }

    void a() {
        this.b = true;
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.f;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    void a(int i) {
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.f;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onProgressChanged(this, i, true);
        }
    }

    void b() {
        this.b = false;
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.f;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    private void c() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override // android.widget.ProgressBar
    public synchronized void setProgress(int i) {
        super.setProgress(i);
        a(i);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }
}
