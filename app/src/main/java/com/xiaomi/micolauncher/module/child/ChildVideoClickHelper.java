package com.xiaomi.micolauncher.module.child;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil;
import com.xiaomi.micolauncher.module.child.childvideo.MiTvVipActivity;
import com.xiaomi.micolauncher.module.homepage.activity.SimpleWebActivity;
import com.xiaomi.micolauncher.module.station.StationCategoryListActivity;

/* loaded from: classes3.dex */
public class ChildVideoClickHelper {
    public static void clickBanner(Context context, ChildVideo.ItemsBean itemsBean) {
        if (itemsBean != null) {
            if (itemsBean.getAndroidIntent().startsWith("http") || itemsBean.getAndroidIntent().startsWith("https")) {
                Intent intent = new Intent(context, SimpleWebActivity.class);
                intent.putExtra(SimpleWebActivity.NAME, itemsBean.getAndroidIntent());
                ActivityLifeCycleManager.startActivityQuietly(intent);
            } else if (itemsBean.isBuiltinItem() && "vip".equals(itemsBean.getTarget().getEntity())) {
                Intent intent2 = new Intent(context, MiTvVipActivity.class);
                intent2.putExtra(MiTvVipActivity.INTENT_EXTRA_MI_TV_TYPE, itemsBean.getMiTvType());
                ActivityLifeCycleManager.startActivityQuietly(intent2);
            } else if (itemsBean.isFilterItem()) {
                StationCategoryListActivity.openStationWithVideoMore(context, itemsBean.getItemTitle(), itemsBean.getTarget().getUrl());
            } else {
                ChildPlayUtil.playVideo(context, itemsBean.getItemId(), itemsBean.getItemTitle(), itemsBean.getItemImageUrl(), itemsBean.getMiTvType());
            }
        }
    }
}
