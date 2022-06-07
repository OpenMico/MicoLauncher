package com.scwang.smart.refresh.layout.listener;

import android.content.Context;
import androidx.annotation.NonNull;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

/* loaded from: classes2.dex */
public interface DefaultRefreshFooterCreator {
    @NonNull
    RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout refreshLayout);
}
