package com.xiaomi.micolauncher.module.homepage.viewholder.apphome;

import android.view.View;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.homepage.view.AppView;

/* loaded from: classes3.dex */
public class AllAppItemViewHolder extends BaseAppHolder {
    private AppView a;

    public AllAppItemViewHolder(View view) {
        super(view, false);
        if (view instanceof AppView) {
            this.a = (AppView) view;
            this.a.setIconSize(UiUtils.getSize(view.getContext(), R.dimen.skill_recommend_large_icon));
            this.a.setPlaceViewHolderDrawableId(R.drawable.skill_app_icon_place_holder);
        }
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void initInMain() {
        super.initInMain();
        this.a.initMain();
    }

    public void bindAppInfo(AppInfo appInfo, String str, String str2) {
        this.a.bindAppInfo(appInfo, str, str2);
    }
}
