package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class AppSelectedView extends AppBaseView {
    private boolean a;

    public AppSelectedView(Context context) {
        this(context, null);
    }

    public AppSelectedView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = ChildModeManager.getManager().isChildMode();
        setIconSize(UiUtils.getSize(context, this.a ? R.dimen.child_app_icon_size : R.dimen.skill_system_recommend_app_icon_size));
        setPlaceViewHolderDrawableId(R.drawable.skill_app_icon_place_holder);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.view.AppBaseView
    protected int layoutId() {
        return this.a ? R.layout.child_app_view_layout : R.layout.app_view_layout_selected;
    }
}
