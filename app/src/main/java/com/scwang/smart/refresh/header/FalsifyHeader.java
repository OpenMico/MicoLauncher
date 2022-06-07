package com.scwang.smart.refresh.header;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import com.scwang.smart.refresh.header.falsify.FalsifyAbstract;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

/* loaded from: classes2.dex */
public class FalsifyHeader extends FalsifyAbstract implements RefreshHeader {
    public FalsifyHeader(Context context) {
        this(context, null);
    }

    public FalsifyHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    @Override // com.scwang.smart.refresh.header.falsify.FalsifyAbstract, com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i, int i2) {
        if (this.mRefreshKernel != null) {
            refreshLayout.closeHeaderOrFooter();
        }
    }
}
