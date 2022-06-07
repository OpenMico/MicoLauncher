package com.xiaomi.micolauncher.module.homepage.viewholder.apphome;

import android.view.View;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.homepage.bean.RecommendCard;
import com.xiaomi.micolauncher.module.homepage.view.AppBaseView;
import com.xiaomi.micolauncher.module.homepage.view.AppSelectedView;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class AppSelectedHolder extends BaseAppHolder {
    private final AppBaseView[] a = new AppSelectedView[9];
    private final float b;

    public AppSelectedHolder(View view) {
        super(view);
        this.a[0] = (AppBaseView) view.findViewById(R.id.selected_app_first);
        this.a[1] = (AppBaseView) view.findViewById(R.id.selected_app_second);
        this.a[2] = (AppBaseView) view.findViewById(R.id.selected_app_third);
        this.a[3] = (AppBaseView) view.findViewById(R.id.selected_app_fourth);
        this.a[4] = (AppBaseView) view.findViewById(R.id.selected_app_fifth);
        this.a[5] = (AppBaseView) view.findViewById(R.id.selected_app_sixth);
        this.a[6] = (AppBaseView) view.findViewById(R.id.selected_app_seventh);
        this.a[7] = (AppBaseView) view.findViewById(R.id.selected_app_eight);
        this.a[8] = (AppBaseView) view.findViewById(R.id.selected_app_ninth);
        this.b = view.getResources().getDimension(R.dimen.app_selected_item_app_name_text_size);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder
    protected int getBgWidth() {
        return UiUtils.getSize(this.context, R.dimen.app_selected_layout_width);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void initInMain() {
        super.initInMain();
        int length = this.a.length;
        for (int i = 0; i < length; i++) {
            this.a[i].initMain();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder
    public void bindData(RecommendCard recommendCard, String str) {
        super.bindData(recommendCard, str);
        List<Long> appKeyList = recommendCard.getAppKeyList();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < appKeyList.size(); i++) {
            AppInfo appInfoByAppKey = SkillDataManager.getManager().getAppInfoByAppKey(appKeyList.get(i).longValue());
            if (appInfoByAppKey != null) {
                arrayList.add(appInfoByAppKey);
            }
        }
        if (!ContainerUtil.isEmpty(arrayList)) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                this.a[i2].bindAppInfo((AppInfo) arrayList.get(i2), str, recommendCard.getTrackKey(), this.b);
            }
        }
    }
}
