package com.scwang.smart.refresh.layout.simple;

import android.graphics.PointF;
import android.view.View;
import com.scwang.smart.refresh.layout.listener.ScrollBoundaryDecider;
import com.scwang.smart.refresh.layout.util.SmartUtil;

/* loaded from: classes2.dex */
public class SimpleBoundaryDecider implements ScrollBoundaryDecider {
    public ScrollBoundaryDecider boundary;
    public PointF mActionEvent;
    public boolean mEnableLoadMoreWhenContentNotFull = true;

    @Override // com.scwang.smart.refresh.layout.listener.ScrollBoundaryDecider
    public boolean canRefresh(View view) {
        ScrollBoundaryDecider scrollBoundaryDecider = this.boundary;
        if (scrollBoundaryDecider != null) {
            return scrollBoundaryDecider.canRefresh(view);
        }
        return SmartUtil.canRefresh(view, this.mActionEvent);
    }

    @Override // com.scwang.smart.refresh.layout.listener.ScrollBoundaryDecider
    public boolean canLoadMore(View view) {
        ScrollBoundaryDecider scrollBoundaryDecider = this.boundary;
        if (scrollBoundaryDecider != null) {
            return scrollBoundaryDecider.canLoadMore(view);
        }
        return SmartUtil.canLoadMore(view, this.mActionEvent, this.mEnableLoadMoreWhenContentNotFull);
    }
}
