package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class RefreshLayout extends FrameLayout {
    public static final int MODE_ABOVE = 2;
    public static final int MODE_UNDER = 1;
    public static final int MODE_UNDER_FOLLOW_DRAG = 0;
    protected static final int REFRESH_STATE_DRAGGING = 2;
    protected static final int REFRESH_STATE_IDLE = 0;
    protected static final int REFRESH_STATE_READY_TO_RELEASE = 3;
    protected static final int REFRESH_STATE_REFRESHING = 4;
    protected static final int REFRESH_STATE_START = 1;
    protected int commonMarginP;
    protected Context context;
    protected int height;
    protected OnReboundEndListener mOnReboundEndListener;
    protected float mRefreshHeadHeight;
    protected View mRefreshHeadView;
    public RefreshHeader mRefreshHeader;
    protected View mTarget;
    public RefreshCallBack refreshCallback;
    protected int touchSlop;
    protected int width;
    protected int refreshState = 0;
    protected boolean enable = true;
    private int commonMargin = 16;
    protected int refreshMode = 1;
    protected Rect mRect = new Rect();
    protected HeadMode mHeadMode = HeadMode.REBOUND;
    protected TailMode mTailMode = TailMode.REBOUND;
    protected AnimType mAnimType = AnimType.NONE;
    protected int START = 0;

    /* loaded from: classes3.dex */
    public enum AnimType {
        NONE,
        REBOUND,
        REFRESH
    }

    /* loaded from: classes3.dex */
    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }

    /* loaded from: classes3.dex */
    public enum HeadMode {
        REFRESH,
        REBOUND
    }

    /* loaded from: classes3.dex */
    public enum TailMode {
        NONE,
        REBOUND
    }

    public RefreshLayout(@NonNull @NotNull Context context) {
        super(context);
        init(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public RefreshLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.context = context;
        this.commonMarginP = dp2px(context, this.commonMargin);
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            View childAt = getChildAt(i3);
            if (childAt == this.mRefreshHeadView) {
                if (this instanceof HorizontalRefreshLayout) {
                    this.mRefreshHeadHeight = childAt.getMeasuredWidth() + this.commonMarginP;
                } else {
                    this.mRefreshHeadHeight = childAt.getMeasuredHeight() + this.commonMarginP;
                }
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
        if (getChildCount() != 0) {
            if (this.mTarget == null) {
                ensureTarget();
            }
            View view = this.mTarget;
            if (view != null) {
                if (view.getRight() == this.mTarget.getLeft() || this.mTarget.getBottom() == this.mTarget.getTop()) {
                    setEnable(false);
                } else {
                    setEnable(true);
                }
                this.mRect.set(this.mTarget.getLeft(), this.mTarget.getTop(), this.mTarget.getRight(), this.mTarget.getBottom());
            }
        }
    }

    protected void ensureTarget() {
        if (this.mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (!childAt.equals(this.mRefreshHeadView)) {
                    this.mTarget = childAt;
                    return;
                }
            }
        }
    }

    @MainThread
    private void setRefreshView(View view) {
        view.measure(this.width, this.height);
        ((FrameLayout.LayoutParams) view.getLayoutParams()).gravity = 48;
        if (view.getParent() != null) {
            return;
        }
        if (this.refreshMode == 2) {
            addView(view);
        } else {
            addView(view, 0);
        }
    }

    public boolean canChildScrollLeft() {
        return this.mTarget.canScrollHorizontally(-1);
    }

    public boolean canChildScrollRight() {
        return this.mTarget.canScrollHorizontally(1);
    }

    public boolean canChildScrollUp() {
        return this.mTarget.canScrollVertically(-1);
    }

    public boolean canChildScrollDown() {
        return this.mTarget.canScrollVertically(1);
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean z) {
        this.enable = z;
    }

    public int getCommonMargin() {
        return this.commonMargin;
    }

    public void setCommonMargin(int i) {
        if (i >= 0) {
            this.commonMargin = i;
        }
    }

    private void setRefreshMode(int i) {
        this.refreshMode = i;
    }

    public void setHeadMode(HeadMode headMode, RefreshCallBack refreshCallBack) {
        this.mHeadMode = headMode;
        if (this.mHeadMode == HeadMode.REFRESH) {
            setRefreshMode(0);
            if (this instanceof HorizontalRefreshLayout) {
                setRefreshHeader(new MaterialRefreshHeader(Direction.HORIZONTAL));
            } else {
                setRefreshHeader(new MaterialRefreshHeader(Direction.VERTICAL));
            }
            setRefreshCallback(refreshCallBack);
        }
    }

    private void setLeftHeadView(View view) {
        this.mRefreshHeadView = view;
        setRefreshView(this.mRefreshHeadView);
    }

    public void setRefreshHeader(RefreshHeader refreshHeader) {
        this.mRefreshHeader = refreshHeader;
        setLeftHeadView(this.mRefreshHeader.getView(this));
    }

    private void setRefreshCallback(RefreshCallBack refreshCallBack) {
        this.refreshCallback = refreshCallBack;
    }

    public static int dp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }
}
