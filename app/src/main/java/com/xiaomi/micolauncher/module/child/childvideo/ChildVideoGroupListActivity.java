package com.xiaomi.micolauncher.module.child.childvideo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.station.ui.StationListFragment;
import com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildVideoGroupListActivity extends BaseGroupListActivity {
    @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity
    protected void setupView() {
        if (!ContainerUtil.isEmpty(this.dataList)) {
            for (final int i = 0; i < this.dataList.size(); i++) {
                BaseGroupListActivity.CategoryInterface categoryInterface = (BaseGroupListActivity.CategoryInterface) this.dataList.get(i);
                Bundle bundle = new Bundle();
                bundle.putLong("EXTRA_CATEGORY_ID", categoryInterface.getCategoryId());
                bundle.putString("EXTRA_CATEGORY_NAME", categoryInterface.getCategoryName());
                bundle.putString(StationListFragment.EXTRA_CATEGORY_URL, ((ChildVideo.ItemsBean) categoryInterface).getTarget().getUrl());
                bundle.putInt("EXTRA_CATEGORY_TYPE", 1001);
                addTab(categoryInterface.getCategoryName(), StationListFragment.class, bundle, new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoGroupListActivity$-v4H36SvGgA-Z1hB4S9HsQT_GIg
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ChildVideoGroupListActivity.this.a(i, view);
                    }
                });
                if (categoryInterface.getCategoryId() == this.categoryId) {
                    this.tabHost.setCurrentTab(i);
                }
            }
            if (this.categoryId <= 0) {
                this.tabHost.setCurrentTab(0);
            }
        }
    }

    public /* synthetic */ void a(int i, View view) {
        this.tabHost.setCurrentTab(i);
    }

    @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity
    protected void loadData() {
        addToDisposeBag(ChildVideoDataManager.getManager().a().subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoGroupListActivity$CosNbuK2WZbeB11majfsrJMmxzk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildVideoGroupListActivity.this.a((List) obj);
            }
        }));
    }

    public /* synthetic */ void a(List list) throws Exception {
        this.dataList.addAll(list);
        setupView();
    }

    public static void openChildVideoGroupListActivity(Context context, long j, boolean z) {
        Intent intent = new Intent(context, ChildVideoGroupListActivity.class);
        intent.putExtra("EXTRA_CATEGORY_ID", String.valueOf(j));
        intent.putExtra(BaseGroupListActivity.EXTRA_STYLE_KID, String.valueOf(z));
        ActivityLifeCycleManager.startActivityQuietly(context, intent);
    }
}
