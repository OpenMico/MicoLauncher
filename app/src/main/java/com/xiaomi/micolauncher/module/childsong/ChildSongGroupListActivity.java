package com.xiaomi.micolauncher.module.childsong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildSongGroupListActivity extends BaseGroupListActivity {
    @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity
    protected void loadData() {
        addToDisposeBag(ChildSongApiHelper.getInstance().loadCategoryList().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongGroupListActivity$sR59BkX7WemXQgWVYXG-8aWjN6M
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongGroupListActivity.this.a((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongGroupListActivity$SfDBT3faI0pQraZYagl0D8TnOUU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongGroupListActivity.this.a((Throwable) obj);
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
        L.childmode.e("ChildSongGroupListActivity$loadData failed: %s", th);
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
                addTab(categoryInterface.getCategoryName(), ChildSongListFragment.class, bundle, new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongGroupListActivity$2bOYzjh13PexWGfrsy4h0gJlO5A
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ChildSongGroupListActivity.this.a(i, view);
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

    public static void openChildSongGroupListActivity(Context context, long j) {
        Intent intent = new Intent(context, ChildSongGroupListActivity.class);
        intent.putExtra("EXTRA_CATEGORY_ID", String.valueOf(j));
        intent.putExtra(BaseGroupListActivity.EXTRA_STYLE_KID, String.valueOf(true));
        ActivityLifeCycleManager.startActivityQuietly(context, intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ChildSongApiHelper.getInstance().release();
    }
}
