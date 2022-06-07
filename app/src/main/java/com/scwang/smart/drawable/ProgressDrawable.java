package com.scwang.smart.drawable;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import androidx.annotation.NonNull;
import com.blankj.utilcode.constant.CacheConstants;

/* loaded from: classes2.dex */
public class ProgressDrawable extends PaintDrawable implements ValueAnimator.AnimatorUpdateListener, Animatable {
    protected int mWidth = 0;
    protected int mHeight = 0;
    protected int mProgressDegree = 0;
    protected Path mPath = new Path();
    protected ValueAnimator mValueAnimator = ValueAnimator.ofInt(30, CacheConstants.HOUR);

    public ProgressDrawable() {
        this.mValueAnimator.setDuration(10000L);
        this.mValueAnimator.setInterpolator(null);
        this.mValueAnimator.setRepeatCount(-1);
        this.mValueAnimator.setRepeatMode(1);
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.mProgressDegree = (((Integer) valueAnimator.getAnimatedValue()).intValue() / 30) * 30;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();
        float f = width;
        float max = Math.max(1.0f, f / 22.0f);
        if (!(this.mWidth == width && this.mHeight == height)) {
            this.mPath.reset();
            float f2 = f - max;
            float f3 = height / 2.0f;
            this.mPath.addCircle(f2, f3, max, Path.Direction.CW);
            float f4 = f - (5.0f * max);
            this.mPath.addRect(f4, f3 - max, f2, f3 + max, Path.Direction.CW);
            this.mPath.addCircle(f4, f3, max, Path.Direction.CW);
            this.mWidth = width;
            this.mHeight = height;
        }
        canvas.save();
        float f5 = f / 2.0f;
        float f6 = height / 2.0f;
        canvas.rotate(this.mProgressDegree, f5, f6);
        for (int i = 0; i < 12; i++) {
            this.mPaint.setAlpha((i + 5) * 17);
            canvas.rotate(30.0f, f5, f6);
            canvas.drawPath(this.mPath, this.mPaint);
        }
        canvas.restore();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (!this.mValueAnimator.isRunning()) {
            this.mValueAnimator.addUpdateListener(this);
            this.mValueAnimator.start();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        if (this.mValueAnimator.isRunning()) {
            this.mValueAnimator.removeAllListeners();
            this.mValueAnimator.removeAllUpdateListeners();
            this.mValueAnimator.cancel();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mValueAnimator.isRunning();
    }
}
