package com.xiaomi.micolauncher.module.homepage.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.TypefaceUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.homepage.VideoLayoutScrollMix;
import com.xiaomi.micolauncher.module.homepage.adapter.VideoAdapter;
import com.xiaomi.micolauncher.module.homepage.bean.HomeVideoData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo;
import com.xiaomi.micolauncher.module.homepage.event.HomeVideoLoadSuccess;
import com.xiaomi.micolauncher.module.homepage.event.VideoQueryEvent;
import com.xiaomi.micolauncher.module.homepage.manager.HomeVideoDataManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import io.reactivex.functions.Consumer;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class VideoListActivity extends BaseActivity implements VideoLayoutScrollMix.NextPageListener, VideoFooterViewHolder.LoadMoreCallback, VideoFooterViewHolder.OnFooterRefreshCallback {
    public static final String TAB_DATA = "mico.tab.data";
    private String a;
    private RecyclerView b;
    private VideoAdapter c;
    private VideoFooterViewHolder e;
    private View f;
    private VideoLayoutScrollMix h;
    private int i;
    private int d = 0;
    private final VideoTabData g = new VideoTabData();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    @SuppressLint({"CheckResult"})
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setDefaultScheduleDuration();
        setContentView(R.layout.activity_video_list);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        VideoTabInfo.CategoryInfo categoryInfo = (VideoTabInfo.CategoryInfo) intent.getParcelableExtra(TAB_DATA);
        if (categoryInfo == null) {
            finish();
            return;
        }
        this.a = categoryInfo.getTabKey();
        this.g.setTabKey(this.a);
        this.g.setDesc(categoryInfo.getDesc());
        View findViewById = findViewById(R.id.back);
        findViewById.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        RxViewHelp.debounceClicksWithOneSeconds(findViewById).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.activity.-$$Lambda$VideoListActivity$BtGIR6G-tGGffYcr8jce-14ZuZw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoListActivity.this.a(obj);
            }
        });
        TextView textView = (TextView) findViewById(R.id.title);
        UiUtils.setAppTextStyle(textView, TypefaceUtil.FONT_WEIGHT_W500, 0);
        textView.setText(categoryInfo.getName());
        this.f = findViewById(R.id.loading);
        this.h = new VideoLayoutScrollMix(this, "HISTORY".equals(this.a) ? null : this, true);
        this.b = (RecyclerView) findViewById(R.id.recycler_view);
        this.b.setLayoutManager(this.h.getLayoutManager());
        this.b.setHasFixedSize(true);
        this.b.setItemAnimator(null);
        this.b.setItemViewCacheSize(6);
        this.b.setClipToPadding(false);
        this.i = getResources().getDimensionPixelSize(R.dimen.video_item_margin);
        this.b.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.homepage.activity.VideoListActivity.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                super.getItemOffsets(rect, view, recyclerView, state);
                rect.right = VideoListActivity.this.i;
            }
        });
        if (this.h.getOnScrollListener() != null) {
            this.b.addOnScrollListener(this.h.getOnScrollListener());
        }
        this.e = new VideoFooterViewHolder(LayoutInflater.from(this).inflate(R.layout.loading_layout, (ViewGroup) this.b, false)).setLoadMoreCallback(this).setCallback(this);
        this.e.initInMain();
        EventBusRegistry.getEventBus().register(this);
        if ("HISTORY".equals(this.a)) {
            ApiRealmHelper.getInstance().queryAllVideoDatas();
        } else {
            HomeVideoDataManager.getManager().loadVideoData(getApplicationContext(), this.a, this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        VideoLayoutScrollMix videoLayoutScrollMix = this.h;
        if (videoLayoutScrollMix != null) {
            videoLayoutScrollMix.release();
        }
        EventBusRegistry.getEventBus().unregister(this);
        super.onDestroy();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder.LoadMoreCallback
    public void loadMore() {
        autoPaging();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder.OnFooterRefreshCallback
    public void onFooterRefresh() {
        RecyclerView recyclerView = this.b;
        if (recyclerView != null) {
            recyclerView.scrollToPosition(0);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.VideoLayoutScrollMix.NextPageListener
    public void autoPaging() {
        VideoAdapter videoAdapter = this.c;
        if (videoAdapter != null && !ContainerUtil.isEmpty(videoAdapter.getVideoDataList())) {
            HomeVideoDataManager manager = HomeVideoDataManager.getManager();
            Context applicationContext = getApplicationContext();
            String str = this.a;
            int i = this.d + 1;
            this.d = i;
            manager.loadVideoData(applicationContext, str, i);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void queryVideoDataSuccess(VideoQueryEvent videoQueryEvent) {
        if ("HISTORY".equals(this.a)) {
            List<VideoData> list = videoQueryEvent.datas;
            b();
            a();
            this.c.setFootViewHolder(null);
            if (ContainerUtil.hasData(list)) {
                this.c.setVideoDataList(list);
            } else {
                this.c.setVideoDataList(null);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoDataLoadedSuccess(HomeVideoLoadSuccess homeVideoLoadSuccess) {
        HomeVideoData homeVideoData;
        if (homeVideoLoadSuccess != null && this.a.equals(homeVideoLoadSuccess.tabKey) && (homeVideoData = homeVideoLoadSuccess.homeVideoData) != null) {
            a();
            a(homeVideoData);
            List<VideoData> videoDataList = this.c.getVideoDataList();
            List<VideoData> videoList = homeVideoData.getVideoList();
            if (ContainerUtil.isEmpty(videoList) && ContainerUtil.isEmpty(videoDataList)) {
                b();
                this.c.setVideoDataList(null);
            } else if (ContainerUtil.isEmpty(videoDataList)) {
                this.c.setVideoDataList(videoList);
                b();
            } else {
                videoDataList.addAll(videoList);
                this.c.setVideoDataList(videoDataList);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshVideoData(RecommendEvent.RefreshVideoData refreshVideoData) {
        if (TextUtils.equals(refreshVideoData.tabKey, this.a)) {
            L.video.i("VideoListActivity onRefreshVideoData");
            HomeVideoDataManager.getManager().loadVideoData(this, this.a, this.d);
        }
    }

    private void a(HomeVideoData homeVideoData) {
        this.e.hasMore(homeVideoData.isHasMore());
        this.e.refreshUI();
    }

    private void a() {
        if (this.c == null) {
            this.c = new VideoAdapter();
            this.c.setDataInfo(this.g, "", "");
            this.c.setFootViewHolder(this.e);
            this.b.setAdapter(this.c);
        }
    }

    private void b() {
        this.f.setVisibility(8);
    }
}
