package com.scwang.smart.refresh.layout.listener;

import android.view.View;

/* loaded from: classes2.dex */
public interface ScrollBoundaryDecider {
    boolean canLoadMore(View view);

    boolean canRefresh(View view);
}
