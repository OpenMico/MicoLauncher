package com.scwang.smart.refresh.layout.api;

import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import com.scwang.smart.refresh.layout.listener.ScrollBoundaryDecider;

/* loaded from: classes2.dex */
public interface RefreshContent {
    boolean canLoadMore();

    boolean canRefresh();

    @NonNull
    View getScrollableView();

    @NonNull
    View getView();

    void moveSpinner(int i, int i2, int i3);

    void onActionDown(MotionEvent motionEvent);

    ValueAnimator.AnimatorUpdateListener scrollContentWhenFinished(int i);

    void setEnableLoadMoreWhenContentNotFull(boolean z);

    void setScrollBoundaryDecider(ScrollBoundaryDecider scrollBoundaryDecider);

    void setUpComponent(RefreshKernel refreshKernel, View view, View view2);
}
