package com.xiaomi.micolauncher.module.homepage.viewholder.apphome;

import android.view.View;
import android.widget.LinearLayout;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.homepage.AppHelpUtils;
import com.xiaomi.micolauncher.module.homepage.view.AllAppItemView;
import java.util.List;

/* loaded from: classes3.dex */
public class AllAppViewHolder extends BaseAppHolder {
    private final LinearLayout a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder
    public void setBackground() {
    }

    public AllAppViewHolder(View view) {
        super(view, false);
        this.a = (LinearLayout) view;
    }

    public void bindData(List<Long> list, String str, String str2) {
        this.tabName = str;
        List<AppInfo> allAppsExcludeKeys = AppHelpUtils.getAllAppsExcludeKeys(list);
        int size = allAppsExcludeKeys.size();
        for (int i = 0; i < size / 3; i++) {
            AllAppItemView allAppItemView = new AllAppItemView(this.context);
            int i2 = i * 3;
            allAppItemView.bindInfos(allAppsExcludeKeys.subList(i2, i2 + 3), str, str2);
            this.a.addView(allAppItemView);
        }
        int i3 = size % 3;
        if (i3 != 0) {
            AllAppItemView allAppItemView2 = new AllAppItemView(this.context);
            allAppItemView2.bindInfos(allAppsExcludeKeys.subList(allAppsExcludeKeys.size() - i3, allAppsExcludeKeys.size()), str, str2);
            this.a.addView(allAppItemView2);
        }
        super.setBackground();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder
    protected int getBgWidth() {
        return this.a.getChildCount() * UiUtils.getSize(this.context, R.dimen.app_item_width);
    }
}
