package com.xiaomi.micolauncher.module.homepage.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.module.homepage.adapter.SubAudiobookContentAdapter;
import com.xiaomi.micolauncher.module.homepage.event.LoadCategoryDatasEvent;
import com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookRefreshViewHolder;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AudiobookSingleCategoryActivity extends AudiobookCommonActivity implements AudiobookRefreshViewHolder.OnFooterRefreshCallback {
    @Override // com.xiaomi.micolauncher.module.homepage.activity.AudiobookCommonActivity
    protected int getTitleResource() {
        return 0;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadData();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.activity.AudiobookCommonActivity, com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity
    public void loadData() {
        super.loadData();
        if (ApiManager.isInited()) {
            AudiobookDataManager.getManager().loadAudiobookAll(this.tag);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onApiInitFinish(RecommendEvent.ApiInited apiInited) {
        AudiobookDataManager.getManager().loadAudiobookAll(this.tag);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadCategoryDatasSuccess(LoadCategoryDatasEvent loadCategoryDatasEvent) {
        this.b.setVisibility(8);
        if (!ContainerUtil.isEmpty(loadCategoryDatasEvent.datas)) {
            if (this.adapter == null) {
                this.adapter = new SubAudiobookContentAdapter();
                this.adapter.addFooterHolder(new AudiobookRefreshViewHolder(LayoutInflater.from(this).inflate(R.layout.audiobook_footer_layout, (ViewGroup) this.e, false)).setCallback(this));
                this.e.setAdapter(this.adapter);
            }
            this.adapter.setContents(loadCategoryDatasEvent.datas, TrackWidget.STATION_DISCOVER_CATEGORY_RECOMMEND);
            this.adapter.notifyDataSetChanged();
        }
    }

    public static void startAudiobookSingleCategoryActivity(Context context, String str) {
        Intent intent = new Intent(context, AudiobookSingleCategoryActivity.class);
        intent.putExtra(BaseAudiobookActivity.KEY_TITLE, str);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.activity.AudiobookCommonActivity, com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity
    public void initView() {
        this.tag = getIntent().getStringExtra(BaseAudiobookActivity.KEY_TITLE);
        super.initView();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookRefreshViewHolder.OnFooterRefreshCallback
    public void onFooterRefresh() {
        if (ApiManager.isInited()) {
            AudiobookDataManager.getManager().loadAudiobookAll(this.tag);
        }
    }
}
