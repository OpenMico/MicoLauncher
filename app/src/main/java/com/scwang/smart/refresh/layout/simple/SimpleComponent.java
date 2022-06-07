package com.scwang.smart.refresh.layout.simple;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshComponent;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

/* loaded from: classes2.dex */
public abstract class SimpleComponent extends RelativeLayout implements RefreshComponent {
    protected SpinnerStyle mSpinnerStyle;
    protected RefreshComponent mWrappedInternal;
    protected View mWrappedView;

    public SimpleComponent(@NonNull View view) {
        this(view, view instanceof RefreshComponent ? (RefreshComponent) view : null);
    }

    protected SimpleComponent(@NonNull View view, @Nullable RefreshComponent refreshComponent) {
        super(view.getContext(), null, 0);
        this.mWrappedView = view;
        this.mWrappedInternal = refreshComponent;
        if (this instanceof RefreshFooter) {
            RefreshComponent refreshComponent2 = this.mWrappedInternal;
            if ((refreshComponent2 instanceof RefreshHeader) && refreshComponent2.getSpinnerStyle() == SpinnerStyle.MatchLayout) {
                refreshComponent.getView().setScaleY(-1.0f);
                return;
            }
        }
        if (this instanceof RefreshHeader) {
            RefreshComponent refreshComponent3 = this.mWrappedInternal;
            if ((refreshComponent3 instanceof RefreshFooter) && refreshComponent3.getSpinnerStyle() == SpinnerStyle.MatchLayout) {
                refreshComponent.getView().setScaleY(-1.0f);
            }
        }
    }

    public SimpleComponent(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return (obj instanceof RefreshComponent) && getView() == ((RefreshComponent) obj).getView();
        }
        return true;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshComponent
    @NonNull
    public View getView() {
        View view = this.mWrappedView;
        return view == null ? this : view;
    }

    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent == null || refreshComponent == this) {
            return 0;
        }
        return refreshComponent.onFinish(refreshLayout, z);
    }

    public void setPrimaryColors(@ColorInt int... iArr) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent != null && refreshComponent != this) {
            refreshComponent.setPrimaryColors(iArr);
        }
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshComponent
    @NonNull
    public SpinnerStyle getSpinnerStyle() {
        SpinnerStyle spinnerStyle = this.mSpinnerStyle;
        if (spinnerStyle != null) {
            return spinnerStyle;
        }
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (!(refreshComponent == null || refreshComponent == this)) {
            return refreshComponent.getSpinnerStyle();
        }
        View view = this.mWrappedView;
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof SmartRefreshLayout.LayoutParams) {
                this.mSpinnerStyle = ((SmartRefreshLayout.LayoutParams) layoutParams).spinnerStyle;
                SpinnerStyle spinnerStyle2 = this.mSpinnerStyle;
                if (spinnerStyle2 != null) {
                    return spinnerStyle2;
                }
            }
            if (layoutParams != null && (layoutParams.height == 0 || layoutParams.height == -1)) {
                SpinnerStyle[] spinnerStyleArr = SpinnerStyle.values;
                for (SpinnerStyle spinnerStyle3 : spinnerStyleArr) {
                    if (spinnerStyle3.scale) {
                        this.mSpinnerStyle = spinnerStyle3;
                        return spinnerStyle3;
                    }
                }
            }
        }
        SpinnerStyle spinnerStyle4 = SpinnerStyle.Translate;
        this.mSpinnerStyle = spinnerStyle4;
        return spinnerStyle4;
    }

    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i, int i2) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent == null || refreshComponent == this) {
            View view = this.mWrappedView;
            if (view != null) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof SmartRefreshLayout.LayoutParams) {
                    refreshKernel.requestDrawBackgroundFor(this, ((SmartRefreshLayout.LayoutParams) layoutParams).backgroundColor);
                    return;
                }
                return;
            }
            return;
        }
        refreshComponent.onInitialized(refreshKernel, i, i2);
    }

    public boolean isSupportHorizontalDrag() {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        return (refreshComponent == null || refreshComponent == this || !refreshComponent.isSupportHorizontalDrag()) ? false : true;
    }

    public void onHorizontalDrag(float f, int i, int i2) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent != null && refreshComponent != this) {
            refreshComponent.onHorizontalDrag(f, i, i2);
        }
    }

    public void onMoving(boolean z, float f, int i, int i2, int i3) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent != null && refreshComponent != this) {
            refreshComponent.onMoving(z, f, i, i2, i3);
        }
    }

    public void onReleased(@NonNull RefreshLayout refreshLayout, int i, int i2) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent != null && refreshComponent != this) {
            refreshComponent.onReleased(refreshLayout, i, i2);
        }
    }

    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int i, int i2) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent != null && refreshComponent != this) {
            refreshComponent.onStartAnimator(refreshLayout, i, i2);
        }
    }

    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        if (refreshComponent != null && refreshComponent != this) {
            if ((this instanceof RefreshFooter) && (refreshComponent instanceof RefreshHeader)) {
                if (refreshState.isFooter) {
                    refreshState = refreshState.toHeader();
                }
                if (refreshState2.isFooter) {
                    refreshState2 = refreshState2.toHeader();
                }
            } else if ((this instanceof RefreshHeader) && (this.mWrappedInternal instanceof RefreshFooter)) {
                if (refreshState.isHeader) {
                    refreshState = refreshState.toFooter();
                }
                if (refreshState2.isHeader) {
                    refreshState2 = refreshState2.toFooter();
                }
            }
            RefreshComponent refreshComponent2 = this.mWrappedInternal;
            if (refreshComponent2 != null) {
                refreshComponent2.onStateChanged(refreshLayout, refreshState, refreshState2);
            }
        }
    }

    @SuppressLint({"RestrictedApi"})
    public boolean setNoMoreData(boolean z) {
        RefreshComponent refreshComponent = this.mWrappedInternal;
        return (refreshComponent instanceof RefreshFooter) && ((RefreshFooter) refreshComponent).setNoMoreData(z);
    }
}
