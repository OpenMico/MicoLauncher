package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.util.List;

/* loaded from: classes3.dex */
public class AllAppItemView extends LinearLayout {
    private final AppView[] a;

    public AllAppItemView(Context context) {
        this(context, null);
    }

    public AllAppItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new AppView[3];
        X2CWrapper.inflate(context, ChildModeManager.getManager().isChildMode() ? R.layout.child_app_all_item : R.layout.app_all_item, this);
        this.a[0] = (AppView) findViewById(R.id.first_app);
        this.a[1] = (AppView) findViewById(R.id.second_app);
        this.a[2] = (AppView) findViewById(R.id.third_app);
    }

    public void bindInfos(List<AppInfo> list, String str, String str2) {
        if (!ContainerUtil.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                this.a[i].bindAppInfo(list.get(i), str, str2);
                this.a[i].initMain();
            }
        }
    }
}
