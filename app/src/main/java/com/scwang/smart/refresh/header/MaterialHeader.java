package com.scwang.smart.refresh.header;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.scwang.smart.refresh.header.material.CircleImageView;
import com.scwang.smart.refresh.header.material.MaterialProgressDrawable;
import com.scwang.smart.refresh.header.material.R;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.simple.SimpleComponent;
import com.scwang.smart.refresh.layout.util.SmartUtil;

/* loaded from: classes2.dex */
public class MaterialHeader extends SimpleComponent implements RefreshHeader {
    protected static final int CIRCLE_BG_LIGHT = -328966;
    @VisibleForTesting
    protected static final int CIRCLE_DIAMETER = 40;
    @VisibleForTesting
    protected static final int CIRCLE_DIAMETER_LARGE = 56;
    protected static final float MAX_PROGRESS_ANGLE = 0.8f;
    public static final int SIZE_DEFAULT = 1;
    public static final int SIZE_LARGE = 0;
    protected Paint mBezierPaint;
    protected Path mBezierPath;
    protected int mCircleDiameter;
    protected ImageView mCircleView;
    protected boolean mFinished;
    protected int mHeadHeight;
    protected MaterialProgressDrawable mProgress;
    protected boolean mScrollableWhenRefreshing;
    protected boolean mShowBezierWave;
    protected RefreshState mState;
    protected int mWaveHeight;

    public MaterialHeader(Context context) {
        this(context, null);
    }

    public MaterialHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mShowBezierWave = false;
        this.mScrollableWhenRefreshing = true;
        this.mSpinnerStyle = SpinnerStyle.MatchLayout;
        setMinimumHeight(SmartUtil.dp2px(100.0f));
        this.mProgress = new MaterialProgressDrawable(this);
        this.mProgress.setColorSchemeColors(-16737844, -48060, -10053376, -5609780, -30720);
        this.mCircleView = new CircleImageView(context, CIRCLE_BG_LIGHT);
        this.mCircleView.setImageDrawable(this.mProgress);
        this.mCircleView.setAlpha(0.0f);
        addView(this.mCircleView);
        this.mCircleDiameter = (int) (getResources().getDisplayMetrics().density * 40.0f);
        this.mBezierPath = new Path();
        this.mBezierPaint = new Paint();
        this.mBezierPaint.setAntiAlias(true);
        this.mBezierPaint.setStyle(Paint.Style.FILL);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MaterialHeader);
        this.mShowBezierWave = obtainStyledAttributes.getBoolean(R.styleable.MaterialHeader_srlShowBezierWave, this.mShowBezierWave);
        this.mScrollableWhenRefreshing = obtainStyledAttributes.getBoolean(R.styleable.MaterialHeader_srlScrollableWhenRefreshing, this.mScrollableWhenRefreshing);
        this.mBezierPaint.setColor(obtainStyledAttributes.getColor(R.styleable.MaterialHeader_srlPrimaryColor, -15614977));
        if (obtainStyledAttributes.hasValue(R.styleable.MaterialHeader_srlShadowRadius)) {
            int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.MaterialHeader_srlShadowRadius, 0);
            this.mBezierPaint.setShadowLayer(dimensionPixelOffset, 0.0f, 0.0f, obtainStyledAttributes.getColor(R.styleable.MaterialHeader_mhShadowColor, ViewCompat.MEASURED_STATE_MASK));
            setLayerType(1, null);
        }
        this.mShowBezierWave = obtainStyledAttributes.getBoolean(R.styleable.MaterialHeader_mhShowBezierWave, this.mShowBezierWave);
        this.mScrollableWhenRefreshing = obtainStyledAttributes.getBoolean(R.styleable.MaterialHeader_mhScrollableWhenRefreshing, this.mScrollableWhenRefreshing);
        if (obtainStyledAttributes.hasValue(R.styleable.MaterialHeader_mhPrimaryColor)) {
            this.mBezierPaint.setColor(obtainStyledAttributes.getColor(R.styleable.MaterialHeader_mhPrimaryColor, -15614977));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.MaterialHeader_mhShadowRadius)) {
            int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.MaterialHeader_mhShadowRadius, 0);
            this.mBezierPaint.setShadowLayer(dimensionPixelOffset2, 0.0f, 0.0f, obtainStyledAttributes.getColor(R.styleable.MaterialHeader_mhShadowColor, ViewCompat.MEASURED_STATE_MASK));
            setLayerType(1, null);
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
        this.mCircleView.measure(View.MeasureSpec.makeMeasureSpec(this.mCircleDiameter, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mCircleDiameter, 1073741824));
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        if (getChildCount() != 0) {
            ImageView imageView = this.mCircleView;
            int measuredWidth = getMeasuredWidth();
            int measuredWidth2 = imageView.getMeasuredWidth();
            int measuredHeight = imageView.getMeasuredHeight();
            if (!isInEditMode() || (i5 = this.mHeadHeight) <= 0) {
                int i6 = measuredWidth / 2;
                int i7 = measuredWidth2 / 2;
                imageView.layout(i6 - i7, -measuredHeight, i6 + i7, 0);
                return;
            }
            int i8 = i5 - (measuredHeight / 2);
            int i9 = measuredWidth / 2;
            int i10 = measuredWidth2 / 2;
            imageView.layout(i9 - i10, i8, i9 + i10, measuredHeight + i8);
            this.mProgress.showArrow(true);
            this.mProgress.setStartEndTrim(0.0f, MAX_PROGRESS_ANGLE);
            this.mProgress.setArrowScale(1.0f);
            imageView.setAlpha(1.0f);
            imageView.setVisibility(0);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mShowBezierWave) {
            this.mBezierPath.reset();
            this.mBezierPath.lineTo(0.0f, this.mHeadHeight);
            this.mBezierPath.quadTo(getMeasuredWidth() / 2.0f, this.mHeadHeight + (this.mWaveHeight * 1.9f), getMeasuredWidth(), this.mHeadHeight);
            this.mBezierPath.lineTo(getMeasuredWidth(), 0.0f);
            canvas.drawPath(this.mBezierPath, this.mBezierPaint);
        }
        super.dispatchDraw(canvas);
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i, int i2) {
        if (!this.mShowBezierWave) {
            refreshKernel.requestDefaultTranslationContentFor(this, false);
        }
        if (isInEditMode()) {
            int i3 = i / 2;
            this.mHeadHeight = i3;
            this.mWaveHeight = i3;
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onMoving(boolean z, float f, int i, int i2, int i3) {
        if (this.mState != RefreshState.Refreshing) {
            if (this.mShowBezierWave) {
                this.mHeadHeight = Math.min(i, i2);
                this.mWaveHeight = Math.max(0, i - i2);
                postInvalidate();
            }
            if (z || (!this.mProgress.isRunning() && !this.mFinished)) {
                if (this.mState != RefreshState.Refreshing) {
                    float f2 = i2;
                    float max = (((float) Math.max(Math.min(1.0f, Math.abs((i * 1.0f) / f2)) - 0.4d, 0.0d)) * 5.0f) / 3.0f;
                    double max2 = Math.max(0.0f, Math.min(Math.abs(i) - i2, f2 * 2.0f) / f2) / 4.0f;
                    float f3 = max * MAX_PROGRESS_ANGLE;
                    this.mProgress.showArrow(true);
                    this.mProgress.setStartEndTrim(0.0f, Math.min((float) MAX_PROGRESS_ANGLE, f3));
                    this.mProgress.setArrowScale(Math.min(1.0f, max));
                    this.mProgress.setProgressRotation((((max * 0.4f) - 0.25f) + (((float) (max2 - Math.pow(max2, 2.0d))) * 2.0f * 2.0f)) * 0.5f);
                }
                ImageView imageView = this.mCircleView;
                float f4 = i;
                imageView.setTranslationY(Math.min(f4, (f4 / 2.0f) + (this.mCircleDiameter / 2.0f)));
                imageView.setAlpha(Math.min(1.0f, (f4 * 4.0f) / this.mCircleDiameter));
            }
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i, int i2) {
        this.mProgress.start();
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.listener.OnStateChangedListener
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        ImageView imageView = this.mCircleView;
        this.mState = refreshState2;
        switch (refreshState2) {
            case None:
            case ReleaseToRefresh:
            case Refreshing:
            default:
                return;
            case PullDownToRefresh:
                this.mFinished = false;
                imageView.setVisibility(0);
                imageView.setTranslationY(0.0f);
                imageView.setScaleX(1.0f);
                imageView.setScaleY(1.0f);
                return;
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z) {
        ImageView imageView = this.mCircleView;
        this.mProgress.stop();
        imageView.animate().scaleX(0.0f).scaleY(0.0f);
        this.mFinished = true;
        return 0;
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    @Deprecated
    public void setPrimaryColors(@ColorInt int... iArr) {
        if (iArr.length > 0) {
            this.mBezierPaint.setColor(iArr[0]);
        }
    }

    public MaterialHeader setProgressBackgroundColorSchemeResource(@ColorRes int i) {
        setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), i));
        return this;
    }

    public MaterialHeader setProgressBackgroundColorSchemeColor(@ColorInt int i) {
        this.mCircleView.setBackgroundColor(i);
        return this;
    }

    public MaterialHeader setColorSchemeColors(@ColorInt int... iArr) {
        this.mProgress.setColorSchemeColors(iArr);
        return this;
    }

    public MaterialHeader setColorSchemeResources(@ColorRes int... iArr) {
        Context context = getContext();
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = ContextCompat.getColor(context, iArr[i]);
        }
        return setColorSchemeColors(iArr2);
    }

    public MaterialHeader setSize(int i) {
        if (i != 0 && i != 1) {
            return this;
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (i == 0) {
            this.mCircleDiameter = (int) (displayMetrics.density * 56.0f);
        } else {
            this.mCircleDiameter = (int) (displayMetrics.density * 40.0f);
        }
        this.mCircleView.setImageDrawable(null);
        this.mProgress.updateSizes(i);
        this.mCircleView.setImageDrawable(this.mProgress);
        return this;
    }

    public MaterialHeader setShowBezierWave(boolean z) {
        this.mShowBezierWave = z;
        return this;
    }

    public MaterialHeader setScrollableWhenRefreshing(boolean z) {
        this.mScrollableWhenRefreshing = z;
        return this;
    }
}
