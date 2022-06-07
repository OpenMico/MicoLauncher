package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class AppView extends AppBaseView {
    public AppView(Context context) {
        this(context, null);
    }

    public AppView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.view.AppBaseView
    protected int layoutId() {
        return ChildModeManager.getManager().isChildMode() ? R.layout.child_app_view_layout : R.layout.app_view_layout;
    }
}
