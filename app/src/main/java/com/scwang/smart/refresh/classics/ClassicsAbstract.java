package com.scwang.smart.refresh.classics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.scwang.smart.drawable.PaintDrawable;
import com.scwang.smart.refresh.classics.ClassicsAbstract;
import com.scwang.smart.refresh.footer.classics.R;
import com.scwang.smart.refresh.layout.api.RefreshComponent;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.simple.SimpleComponent;
import com.scwang.smart.refresh.layout.util.SmartUtil;

/* loaded from: classes2.dex */
public abstract class ClassicsAbstract<T extends ClassicsAbstract> extends SimpleComponent implements RefreshComponent {
    protected PaintDrawable mArrowDrawable;
    protected ImageView mArrowView;
    protected int mBackgroundColor;
    protected PaintDrawable mProgressDrawable;
    protected ImageView mProgressView;
    protected RefreshKernel mRefreshKernel;
    protected boolean mSetAccentColor;
    protected boolean mSetPrimaryColor;
    protected TextView mTitleText;
    public static final int ID_TEXT_TITLE = R.id.srl_classics_title;
    public static final int ID_IMAGE_ARROW = R.id.srl_classics_arrow;
    public static final int ID_IMAGE_PROGRESS = R.id.srl_classics_progress;
    protected int mFinishDuration = 500;
    protected int mPaddingTop = 20;
    protected int mPaddingBottom = 20;
    protected int mMinHeightOfContent = 0;

    protected T self() {
        return this;
    }

    public ClassicsAbstract(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSpinnerStyle = SpinnerStyle.Translate;
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mMinHeightOfContent == 0) {
            this.mPaddingTop = getPaddingTop();
            this.mPaddingBottom = getPaddingBottom();
            if (this.mPaddingTop == 0 || this.mPaddingBottom == 0) {
                int paddingLeft = getPaddingLeft();
                int paddingRight = getPaddingRight();
                int i3 = this.mPaddingTop;
                if (i3 == 0) {
                    i3 = SmartUtil.dp2px(20.0f);
                }
                this.mPaddingTop = i3;
                int i4 = this.mPaddingBottom;
                if (i4 == 0) {
                    i4 = SmartUtil.dp2px(20.0f);
                }
                this.mPaddingBottom = i4;
                setPadding(paddingLeft, this.mPaddingTop, paddingRight, this.mPaddingBottom);
            }
            setClipToPadding(false);
        }
        if (View.MeasureSpec.getMode(i2) == 1073741824) {
            int size = View.MeasureSpec.getSize(i2);
            int i5 = this.mMinHeightOfContent;
            if (size < i5) {
                int i6 = (size - i5) / 2;
                setPadding(getPaddingLeft(), i6, getPaddingRight(), i6);
            } else {
                setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
            }
        } else {
            setPadding(getPaddingLeft(), this.mPaddingTop, getPaddingRight(), this.mPaddingBottom);
        }
        super.onMeasure(i, i2);
        if (this.mMinHeightOfContent == 0) {
            for (int i7 = 0; i7 < getChildCount(); i7++) {
                int measuredHeight = getChildAt(i7).getMeasuredHeight();
                if (this.mMinHeightOfContent < measuredHeight) {
                    this.mMinHeightOfContent = measuredHeight;
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ImageView imageView = this.mArrowView;
        ImageView imageView2 = this.mProgressView;
        imageView.animate().cancel();
        imageView2.animate().cancel();
        Drawable drawable = this.mProgressView.getDrawable();
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            if (animatable.isRunning()) {
                animatable.stop();
            }
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i, int i2) {
        this.mRefreshKernel = refreshKernel;
        this.mRefreshKernel.requestDrawBackgroundFor(this, this.mBackgroundColor);
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int i, int i2) {
        ImageView imageView = this.mProgressView;
        if (imageView.getVisibility() != 0) {
            imageView.setVisibility(0);
            Drawable drawable = this.mProgressView.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            } else {
                imageView.animate().rotation(36000.0f).setDuration(100000L);
            }
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i, int i2) {
        onStartAnimator(refreshLayout, i, i2);
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z) {
        ImageView imageView = this.mProgressView;
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            if (animatable.isRunning()) {
                animatable.stop();
            }
        } else {
            imageView.animate().rotation(0.0f).setDuration(0L);
        }
        imageView.setVisibility(8);
        return this.mFinishDuration;
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void setPrimaryColors(@ColorInt int... iArr) {
        if (iArr.length > 0) {
            if (!(getBackground() instanceof BitmapDrawable) && !this.mSetPrimaryColor) {
                setPrimaryColor(iArr[0]);
                this.mSetPrimaryColor = false;
            }
            if (!this.mSetAccentColor) {
                if (iArr.length > 1) {
                    setAccentColor(iArr[1]);
                }
                this.mSetAccentColor = false;
            }
        }
    }

    public T setProgressBitmap(Bitmap bitmap) {
        this.mProgressDrawable = null;
        this.mProgressView.setImageBitmap(bitmap);
        return self();
    }

    public T setProgressDrawable(Drawable drawable) {
        this.mProgressDrawable = null;
        this.mProgressView.setImageDrawable(drawable);
        return self();
    }

    public T setProgressResource(@DrawableRes int i) {
        this.mProgressDrawable = null;
        this.mProgressView.setImageResource(i);
        return self();
    }

    public T setArrowBitmap(Bitmap bitmap) {
        this.mArrowDrawable = null;
        this.mArrowView.setImageBitmap(bitmap);
        return self();
    }

    public T setArrowDrawable(Drawable drawable) {
        this.mArrowDrawable = null;
        this.mArrowView.setImageDrawable(drawable);
        return self();
    }

    public T setArrowResource(@DrawableRes int i) {
        this.mArrowDrawable = null;
        this.mArrowView.setImageResource(i);
        return self();
    }

    public T setSpinnerStyle(SpinnerStyle spinnerStyle) {
        this.mSpinnerStyle = spinnerStyle;
        return self();
    }

    public T setPrimaryColor(@ColorInt int i) {
        this.mSetPrimaryColor = true;
        this.mBackgroundColor = i;
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            refreshKernel.requestDrawBackgroundFor(this, i);
        }
        return self();
    }

    public T setAccentColor(@ColorInt int i) {
        this.mSetAccentColor = true;
        this.mTitleText.setTextColor(i);
        PaintDrawable paintDrawable = this.mArrowDrawable;
        if (paintDrawable != null) {
            paintDrawable.setColor(i);
            this.mArrowView.invalidateDrawable(this.mArrowDrawable);
        }
        PaintDrawable paintDrawable2 = this.mProgressDrawable;
        if (paintDrawable2 != null) {
            paintDrawable2.setColor(i);
            this.mProgressView.invalidateDrawable(this.mProgressDrawable);
        }
        return self();
    }

    public T setPrimaryColorId(@ColorRes int i) {
        setPrimaryColor(ContextCompat.getColor(getContext(), i));
        return self();
    }

    public T setAccentColorId(@ColorRes int i) {
        setAccentColor(ContextCompat.getColor(getContext(), i));
        return self();
    }

    public T setFinishDuration(int i) {
        this.mFinishDuration = i;
        return self();
    }

    public T setTextSizeTitle(float f) {
        this.mTitleText.setTextSize(f);
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            refreshKernel.requestRemeasureHeightFor(this);
        }
        return self();
    }

    public T setTextSizeTitle(int i, float f) {
        this.mTitleText.setTextSize(i, f);
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            refreshKernel.requestRemeasureHeightFor(this);
        }
        return self();
    }

    public T setDrawableMarginRight(float f) {
        ImageView imageView = this.mArrowView;
        ImageView imageView2 = this.mProgressView;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) imageView2.getLayoutParams();
        int dp2px = SmartUtil.dp2px(f);
        marginLayoutParams2.rightMargin = dp2px;
        marginLayoutParams.rightMargin = dp2px;
        imageView.setLayoutParams(marginLayoutParams);
        imageView2.setLayoutParams(marginLayoutParams2);
        return self();
    }

    public T setDrawableMarginRightPx(int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mArrowView.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mProgressView.getLayoutParams();
        marginLayoutParams2.rightMargin = i;
        marginLayoutParams.rightMargin = i;
        this.mArrowView.setLayoutParams(marginLayoutParams);
        this.mProgressView.setLayoutParams(marginLayoutParams2);
        return self();
    }

    public T setDrawableSize(float f) {
        ImageView imageView = this.mArrowView;
        ImageView imageView2 = this.mProgressView;
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = imageView2.getLayoutParams();
        int dp2px = SmartUtil.dp2px(f);
        layoutParams2.width = dp2px;
        layoutParams.width = dp2px;
        int dp2px2 = SmartUtil.dp2px(f);
        layoutParams2.height = dp2px2;
        layoutParams.height = dp2px2;
        imageView.setLayoutParams(layoutParams);
        imageView2.setLayoutParams(layoutParams2);
        return self();
    }

    public T setDrawableSizePx(int i) {
        ViewGroup.LayoutParams layoutParams = this.mArrowView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = this.mProgressView.getLayoutParams();
        layoutParams2.width = i;
        layoutParams.width = i;
        layoutParams2.height = i;
        layoutParams.height = i;
        this.mArrowView.setLayoutParams(layoutParams);
        this.mProgressView.setLayoutParams(layoutParams2);
        return self();
    }

    public T setDrawableArrowSize(float f) {
        ImageView imageView = this.mArrowView;
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int dp2px = SmartUtil.dp2px(f);
        layoutParams.width = dp2px;
        layoutParams.height = dp2px;
        imageView.setLayoutParams(layoutParams);
        return self();
    }

    public T setDrawableArrowSizePx(int i) {
        ViewGroup.LayoutParams layoutParams = this.mArrowView.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        this.mArrowView.setLayoutParams(layoutParams);
        return self();
    }

    public T setDrawableProgressSize(float f) {
        ImageView imageView = this.mProgressView;
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int dp2px = SmartUtil.dp2px(f);
        layoutParams.width = dp2px;
        layoutParams.height = dp2px;
        imageView.setLayoutParams(layoutParams);
        return self();
    }

    public T setDrawableProgressSizePx(int i) {
        ViewGroup.LayoutParams layoutParams = this.mProgressView.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        this.mProgressView.setLayoutParams(layoutParams);
        return self();
    }
}
