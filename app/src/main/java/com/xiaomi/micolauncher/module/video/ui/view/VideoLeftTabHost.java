package com.xiaomi.micolauncher.module.video.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.video.ui.base.CustomTabHost;

/* loaded from: classes3.dex */
public class VideoLeftTabHost extends CustomTabHost {
    public VideoLeftTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.xiaomi.micolauncher.module.video.ui.base.CustomTabHost
    protected View inflateView() {
        return inflate(getContext(), R.layout.view_left_tab_host, this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.video.ui.base.CustomTabHost
    public void switchTab(int i) {
        super.switchTab(i);
    }
}
