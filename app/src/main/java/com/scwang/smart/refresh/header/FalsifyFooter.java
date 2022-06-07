package com.scwang.smart.refresh.header;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import com.scwang.smart.refresh.header.falsify.FalsifyAbstract;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

/* loaded from: classes2.dex */
public class FalsifyFooter extends FalsifyAbstract implements RefreshFooter {
    public FalsifyFooter(Context context) {
        this(context, null);
    }

    public FalsifyFooter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    @Override // com.scwang.smart.refresh.header.falsify.FalsifyAbstract, com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i, int i2) {
        this.mRefreshKernel = refreshKernel;
        refreshKernel.getRefreshLayout().setEnableAutoLoadMore(false);
    }

    @Override // com.scwang.smart.refresh.header.falsify.FalsifyAbstract, com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i, int i2) {
        if (this.mRefreshKernel != null) {
            refreshLayout.closeHeaderOrFooter();
        }
    }
}
