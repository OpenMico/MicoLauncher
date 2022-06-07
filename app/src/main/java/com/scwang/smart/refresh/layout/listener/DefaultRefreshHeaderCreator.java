package com.scwang.smart.refresh.layout.listener;

import android.content.Context;
import androidx.annotation.NonNull;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

/* loaded from: classes2.dex */
public interface DefaultRefreshHeaderCreator {
    @NonNull
    RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout refreshLayout);
}
