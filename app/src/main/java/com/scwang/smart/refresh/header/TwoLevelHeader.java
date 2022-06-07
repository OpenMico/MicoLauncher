package com.scwang.smart.refresh.header;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import com.scwang.smart.refresh.header.listener.OnTwoLevelListener;
import com.scwang.smart.refresh.header.two.level.R;
import com.scwang.smart.refresh.layout.api.RefreshComponent;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.simple.SimpleComponent;

/* loaded from: classes2.dex */
public class TwoLevelHeader extends SimpleComponent implements NestedScrollingParent2, RefreshHeader {
    private int a;
    protected float mBottomPullUpToCloseRate;
    protected boolean mEnablePullToCloseTwoLevel;
    protected boolean mEnableRefresh;
    protected boolean mEnableTwoLevel;
    protected int mFloorDuration;
    protected float mFloorRate;
    protected int mHeaderHeight;
    protected float mMaxRate;
    protected float mPercent;
    protected RefreshComponent mRefreshHeader;
    protected RefreshKernel mRefreshKernel;
    protected float mRefreshRate;
    protected int mSpinner;
    protected OnTwoLevelListener mTwoLevelListener;

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(@NonNull View view, float f, float f2, boolean z) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(@NonNull View view, float f, float f2) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(@NonNull View view, int i, int i2, @NonNull int[] iArr) {
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedPreScroll(@NonNull View view, int i, int i2, @NonNull int[] iArr, int i3) {
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScroll(@NonNull View view, int i, int i2, int i3, int i4) {
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScroll(@NonNull View view, int i, int i2, int i3, int i4, int i5) {
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, int i) {
        return true;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, int i, int i2) {
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(@NonNull View view) {
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onStopNestedScroll(@NonNull View view, int i) {
    }

    public TwoLevelHeader(Context context) {
        this(context, null);
    }

    public TwoLevelHeader(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mPercent = 0.0f;
        this.mMaxRate = 2.5f;
        this.mFloorRate = 1.9f;
        this.mRefreshRate = 1.0f;
        this.mBottomPullUpToCloseRate = 0.16666667f;
        this.mEnableRefresh = true;
        this.mEnableTwoLevel = true;
        this.mEnablePullToCloseTwoLevel = true;
        this.mFloorDuration = 1000;
        this.a = 0;
        this.mSpinnerStyle = SpinnerStyle.FixedBehind;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TwoLevelHeader);
        this.mMaxRate = obtainStyledAttributes.getFloat(R.styleable.TwoLevelHeader_srlMaxRate, this.mMaxRate);
        this.mFloorRate = obtainStyledAttributes.getFloat(R.styleable.TwoLevelHeader_srlFloorRate, this.mFloorRate);
        this.mRefreshRate = obtainStyledAttributes.getFloat(R.styleable.TwoLevelHeader_srlRefreshRate, this.mRefreshRate);
        this.mFloorDuration = obtainStyledAttributes.getInt(R.styleable.TwoLevelHeader_srlFloorDuration, this.mFloorDuration);
        this.mEnableRefresh = obtainStyledAttributes.getBoolean(R.styleable.TwoLevelHeader_srlEnableRefresh, this.mEnableRefresh);
        this.mEnableTwoLevel = obtainStyledAttributes.getBoolean(R.styleable.TwoLevelHeader_srlEnableTwoLevel, this.mEnableTwoLevel);
        this.mBottomPullUpToCloseRate = obtainStyledAttributes.getFloat(R.styleable.TwoLevelHeader_srlBottomPullUpToCloseRate, this.mBottomPullUpToCloseRate);
        this.mEnablePullToCloseTwoLevel = obtainStyledAttributes.getBoolean(R.styleable.TwoLevelHeader_srlEnablePullToCloseTwoLevel, this.mEnablePullToCloseTwoLevel);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                break;
            }
            View childAt = getChildAt(i);
            if (childAt instanceof RefreshHeader) {
                this.mRefreshHeader = (RefreshHeader) childAt;
                this.mWrappedInternal = (RefreshComponent) childAt;
                bringChildToFront(childAt);
                break;
            }
            i++;
        }
        if (this.mRefreshHeader == null) {
            setRefreshHeader(new ClassicsHeader(getContext()));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSpinnerStyle = SpinnerStyle.MatchLayout;
        if (this.mRefreshHeader == null) {
            setRefreshHeader(new ClassicsHeader(getContext()));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mSpinnerStyle = SpinnerStyle.FixedBehind;
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent == null) {
            super.onMeasure(i, i2);
        } else if (View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE) {
            refreshComponent.getView().measure(i, i2);
            super.setMeasuredDimension(View.resolveSize(super.getSuggestedMinimumWidth(), i), refreshComponent.getView().getMeasuredHeight());
        } else {
            super.onMeasure(i, i2);
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent
    public boolean equals(Object obj) {
        RefreshComponent refreshComponent = this.mRefreshHeader;
        return (refreshComponent != null && refreshComponent.equals(obj)) || super.equals(obj);
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i, int i2) {
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent != null) {
            if (((i2 + i) * 1.0f) / i != this.mMaxRate && this.mHeaderHeight == 0) {
                this.mHeaderHeight = i;
                this.mRefreshHeader = null;
                refreshKernel.getRefreshLayout().setHeaderMaxDragRate(this.mMaxRate);
                this.mRefreshHeader = refreshComponent;
            }
            if (this.mRefreshKernel == null && refreshComponent.getSpinnerStyle() == SpinnerStyle.Translate && !isInEditMode()) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) refreshComponent.getView().getLayoutParams();
                marginLayoutParams.topMargin -= i;
                refreshComponent.getView().setLayoutParams(marginLayoutParams);
            }
            this.mHeaderHeight = i;
            this.mRefreshKernel = refreshKernel;
            refreshKernel.requestFloorDuration(this.mFloorDuration);
            refreshKernel.requestFloorBottomPullUpToCloseRate(this.mBottomPullUpToCloseRate);
            refreshKernel.requestNeedTouchEventFor(this, !this.mEnablePullToCloseTwoLevel);
            refreshComponent.onInitialized(refreshKernel, i, i2);
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.listener.OnStateChangedListener
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent != null) {
            if (refreshState2 == RefreshState.ReleaseToRefresh && !this.mEnableRefresh) {
                refreshState2 = RefreshState.PullDownToRefresh;
            }
            refreshComponent.onStateChanged(refreshLayout, refreshState, refreshState2);
            switch (refreshState2) {
                case TwoLevelReleased:
                    if (refreshComponent.getView() != this) {
                        refreshComponent.getView().animate().alpha(0.0f).setDuration(this.mFloorDuration / 2);
                    }
                    RefreshKernel refreshKernel = this.mRefreshKernel;
                    if (refreshKernel != null) {
                        OnTwoLevelListener onTwoLevelListener = this.mTwoLevelListener;
                        refreshKernel.startTwoLevel(onTwoLevelListener == null || onTwoLevelListener.onTwoLevel(refreshLayout));
                        return;
                    }
                    return;
                case TwoLevel:
                default:
                    return;
                case TwoLevelFinish:
                    if (refreshComponent.getView() != this) {
                        refreshComponent.getView().animate().alpha(1.0f).setDuration(this.mFloorDuration / 2);
                        return;
                    }
                    return;
                case PullDownToRefresh:
                    if (refreshComponent.getView().getAlpha() == 0.0f && refreshComponent.getView() != this) {
                        refreshComponent.getView().setAlpha(1.0f);
                        return;
                    }
                    return;
            }
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onMoving(boolean z, float f, int i, int i2, int i3) {
        moveSpinner(i);
        RefreshComponent refreshComponent = this.mRefreshHeader;
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshComponent != null) {
            refreshComponent.onMoving(z, f, i, i2, i3);
        }
        if (z) {
            float f2 = this.mPercent;
            float f3 = this.mFloorRate;
            if (f2 < f3 && f >= f3 && this.mEnableTwoLevel) {
                refreshKernel.setState(RefreshState.ReleaseToTwoLevel);
            } else if (this.mPercent < this.mFloorRate || f >= this.mRefreshRate) {
                float f4 = this.mPercent;
                float f5 = this.mFloorRate;
                if (f4 >= f5 && f < f5 && this.mEnableRefresh) {
                    refreshKernel.setState(RefreshState.ReleaseToRefresh);
                } else if (!this.mEnableRefresh && refreshKernel.getRefreshLayout().getState() != RefreshState.ReleaseToTwoLevel) {
                    refreshKernel.setState(RefreshState.PullDownToRefresh);
                }
            } else {
                refreshKernel.setState(RefreshState.PullDownToRefresh);
            }
            this.mPercent = f;
        }
    }

    protected void moveSpinner(int i) {
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (this.mSpinner != i && refreshComponent != null) {
            this.mSpinner = i;
            SpinnerStyle spinnerStyle = refreshComponent.getSpinnerStyle();
            if (spinnerStyle == SpinnerStyle.Translate) {
                refreshComponent.getView().setTranslationY(i);
            } else if (spinnerStyle.scale) {
                View view = refreshComponent.getView();
                view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getTop() + Math.max(0, i));
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(@NonNull View view, @NonNull View view2, int i) {
        this.a = i;
    }

    @Override // android.view.ViewGroup, androidx.core.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return this.a;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScrollAccepted(@NonNull View view, @NonNull View view2, int i, int i2) {
        this.a = i;
    }

    public TwoLevelHeader setRefreshHeader(RefreshHeader refreshHeader) {
        return setRefreshHeader(refreshHeader, 0, 0);
    }

    public TwoLevelHeader setRefreshHeader(RefreshHeader refreshHeader, int i, int i2) {
        if (refreshHeader != null) {
            if (i == 0) {
                i = -1;
            }
            if (i2 == 0) {
                i2 = -2;
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i, i2);
            ViewGroup.LayoutParams layoutParams2 = refreshHeader.getView().getLayoutParams();
            if (layoutParams2 instanceof RelativeLayout.LayoutParams) {
                layoutParams = (RelativeLayout.LayoutParams) layoutParams2;
            }
            RefreshComponent refreshComponent = this.mRefreshHeader;
            if (refreshComponent != null) {
                removeView(refreshComponent.getView());
            }
            if (refreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                addView(refreshHeader.getView(), 0, layoutParams);
            } else {
                addView(refreshHeader.getView(), getChildCount(), layoutParams);
            }
            this.mRefreshHeader = refreshHeader;
            this.mWrappedInternal = refreshHeader;
        }
        return this;
    }

    public TwoLevelHeader setMaxRate(float f) {
        if (this.mMaxRate != f) {
            this.mMaxRate = f;
            RefreshKernel refreshKernel = this.mRefreshKernel;
            if (refreshKernel != null) {
                this.mHeaderHeight = 0;
                refreshKernel.getRefreshLayout().setHeaderMaxDragRate(this.mMaxRate);
            }
        }
        return this;
    }

    public TwoLevelHeader setEnablePullToCloseTwoLevel(boolean z) {
        RefreshKernel refreshKernel = this.mRefreshKernel;
        this.mEnablePullToCloseTwoLevel = z;
        if (refreshKernel != null) {
            refreshKernel.requestNeedTouchEventFor(this, !z);
        }
        return this;
    }

    public TwoLevelHeader setEnableRefresh(boolean z) {
        this.mEnableRefresh = z;
        return this;
    }

    public TwoLevelHeader setFloorRate(float f) {
        this.mFloorRate = f;
        return this;
    }

    public TwoLevelHeader setRefreshRate(float f) {
        this.mRefreshRate = f;
        return this;
    }

    public TwoLevelHeader setEnableTwoLevel(boolean z) {
        this.mEnableTwoLevel = z;
        return this;
    }

    public TwoLevelHeader setFloorDuration(int i) {
        this.mFloorDuration = i;
        return this;
    }

    public TwoLevelHeader setBottomPullUpToCloseRate(float f) {
        this.mBottomPullUpToCloseRate = f;
        return this;
    }

    public TwoLevelHeader setOnTwoLevelListener(OnTwoLevelListener onTwoLevelListener) {
        this.mTwoLevelListener = onTwoLevelListener;
        return this;
    }

    public TwoLevelHeader finishTwoLevel() {
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            refreshKernel.finishTwoLevel();
        }
        return this;
    }

    public TwoLevelHeader openTwoLevel(boolean z) {
        RefreshKernel refreshKernel = this.mRefreshKernel;
        if (refreshKernel != null) {
            OnTwoLevelListener onTwoLevelListener = this.mTwoLevelListener;
            refreshKernel.startTwoLevel(!z || onTwoLevelListener == null || onTwoLevelListener.onTwoLevel(refreshKernel.getRefreshLayout()));
        }
        return this;
    }
}
