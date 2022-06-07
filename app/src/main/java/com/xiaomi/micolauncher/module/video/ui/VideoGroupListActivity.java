package com.xiaomi.micolauncher.module.video.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.micolauncher.module.video.ui.fragment.VideoListFragment;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoGroupListActivity extends BaseGroupListActivity {
    @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity
    protected void loadData() {
        addToDisposeBag(VideoDataManager.getManager().loadCategoryList(this.origin).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$VideoGroupListActivity$1wvO4yFGjKviikI6Ymizg1UDwcU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoGroupListActivity.this.a((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$VideoGroupListActivity$9ZAdz0PE0kNprVV6vre3IqxS650
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoGroupListActivity.this.a((Throwable) obj);
            }
        }));
    }

    public /* synthetic */ void a(List list) throws Exception {
        this.dataList.clear();
        this.dataList.addAll(list);
        setupView();
    }

    public /* synthetic */ void a(Throwable th) throws Exception {
        ToastUtil.showToast((int) R.string.video_list_load_failed);
        L.childmode.e("VideoGroupListActivity$loadData failed:%s", th);
        finish();
    }

    @Override // com.xiaomi.micolauncher.module.video.base.BaseGroupListActivity
    protected void setupView() {
        if (!ContainerUtil.isEmpty(this.dataList)) {
            for (final int i = 0; i < this.dataList.size(); i++) {
                BaseGroupListActivity.CategoryInterface categoryInterface = (BaseGroupListActivity.CategoryInterface) this.dataList.get(i);
                if (this.tabHost.getTabSize() < this.dataList.size()) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("EXTRA_CATEGORY_ID", categoryInterface.getCategoryId());
                    bundle.putString("EXTRA_CATEGORY_NAME", categoryInterface.getCategoryName());
                    bundle.putString("EXTRA_CATEGORY_TYPE", categoryInterface.getCategoryType());
                    bundle.putString("EXTRA_CATEGORY_ORIGIN", categoryInterface.getCategoryOrigin());
                    addTab(categoryInterface.getCategoryType(), VideoListFragment.class, bundle, new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$VideoGroupListActivity$iky1rxc0yt-M47S3e24BRiYcIqE
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            VideoGroupListActivity.this.a(i, view);
                        }
                    });
                }
                if (categoryInterface.getCategoryId() == this.categoryId) {
                    this.tabHost.setCurrentTab(i);
                }
            }
        }
    }

    public /* synthetic */ void a(int i, View view) {
        this.tabHost.setCurrentTab(i);
    }

    public static void openVideoGroupListActivity(Context context, String str) {
        Intent intent = new Intent(context, VideoGroupListActivity.class);
        intent.putExtra("EXTRA_CATEGORY_ORIGIN", str);
        intent.putExtra(BaseGroupListActivity.EXTRA_STYLE_KID, String.valueOf(Hardware.isBigScreen() && ChildModeManager.getManager().isChildMode()));
        ActivityLifeCycleManager.startActivityQuietly(context, intent);
    }

    public static void openVideoGroupListActivity(Context context, long j) {
        Intent intent = new Intent(context, VideoGroupListActivity.class);
        intent.putExtra("EXTRA_CATEGORY_ID", String.valueOf(j));
        intent.putExtra(BaseGroupListActivity.EXTRA_STYLE_KID, String.valueOf(Hardware.isBigScreen() && ChildModeManager.getManager().isChildMode()));
        ActivityLifeCycleManager.startActivityQuietly(context, intent);
    }
}
