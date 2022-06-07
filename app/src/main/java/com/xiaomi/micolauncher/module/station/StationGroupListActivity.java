package com.xiaomi.micolauncher.module.station;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.station.manager.StationDataManager;
import com.xiaomi.micolauncher.module.station.ui.StationListFragment;
import com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class StationGroupListActivity extends BaseGroupListActivity {
    @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity
    protected void loadData() {
        addToDisposeBag(StationDataManager.getManager().loadCategoryList().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationGroupListActivity$NOGM6Rivoc-R36LSPVIreifbZe8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StationGroupListActivity.this.a((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationGroupListActivity$vPOpZ0EkU_xICbBdhMLIWKBNgsc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StationGroupListActivity.this.a((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        this.dataList.addAll(list);
        setupView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        L.childmode.e("StationGroupListActivity$loadData StationDataManager.getManager().loadCategoryList failed:", th);
        finish();
    }

    @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity
    protected void setupView() {
        if (!ContainerUtil.isEmpty(this.dataList)) {
            for (final int i = 0; i < this.dataList.size(); i++) {
                BaseGroupListActivity.CategoryInterface categoryInterface = (BaseGroupListActivity.CategoryInterface) this.dataList.get(i);
                Bundle bundle = new Bundle();
                bundle.putLong("EXTRA_CATEGORY_ID", categoryInterface.getCategoryId());
                bundle.putString("EXTRA_CATEGORY_NAME", categoryInterface.getCategoryName());
                addTab(categoryInterface.getCategoryName(), StationListFragment.class, bundle, new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationGroupListActivity$PJbWZZQK4q5U1OwW6cEkvqKT5VY
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        StationGroupListActivity.this.a(i, view);
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, View view) {
        this.tabHost.setCurrentTab(i);
    }

    public static void openStationGroupListActivity(Context context, long j) {
        openStationGroupListActivity(context, j, false);
    }

    public static void openStationGroupListActivity(Context context, long j, boolean z) {
        Intent intent = new Intent(context, StationGroupListActivity.class);
        intent.putExtra("EXTRA_CATEGORY_ID", String.valueOf(j));
        intent.putExtra(BaseGroupListActivity.EXTRA_STYLE_KID, String.valueOf(z));
        ActivityLifeCycleManager.startActivityQuietly(context, intent);
    }
}
