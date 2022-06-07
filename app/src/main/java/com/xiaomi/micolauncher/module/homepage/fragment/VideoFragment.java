package com.xiaomi.micolauncher.module.homepage.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.homepage.VideoLayoutScrollMix;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.VideoAdapter;
import com.xiaomi.micolauncher.module.homepage.bean.HomeVideoData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabData;
import com.xiaomi.micolauncher.module.homepage.event.HomeVideoLoadSuccess;
import com.xiaomi.micolauncher.module.homepage.event.ResetScrollViewPositionEvent;
import com.xiaomi.micolauncher.module.homepage.event.VideoQueryEvent;
import com.xiaomi.micolauncher.module.homepage.fragment.EntertainmentFragment;
import com.xiaomi.micolauncher.module.homepage.manager.HomeVideoDataManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class VideoFragment extends BaseHomeFragment implements VideoLayoutScrollMix.NextPageListener, EntertainmentFragment.FragmentRefreshListener, VideoFooterViewHolder.LoadMoreCallback, VideoFooterViewHolder.OnFooterRefreshCallback {
    public static final String TAG = "VideoFragment";
    private View a;
    private VideoAdapter b;
    private VideoTabData c;
    private int d;
    private boolean e;
    private HomeVideoData f;
    private VideoFooterViewHolder g;
    private VideoLayoutScrollMix h;
    private int i;

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected BaseAdapter<? extends RecyclerView.ViewHolder> createAdapter() {
        return null;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int itemViewCacheSize() {
        return 4;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int layoutId() {
        return R.layout.fragment_video_layout;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
    }

    @Override // com.xiaomi.micolauncher.module.homepage.VideoLayoutScrollMix.NextPageListener
    public void autoPaging() {
        if (this.f.isHasMore() && !this.e && !TextUtils.equals(this.c.getTabKey(), "HISTORY")) {
            this.e = true;
            HomeVideoDataManager manager = HomeVideoDataManager.getManager();
            Activity activity = this.context;
            String tabKey = this.c.getTabKey();
            int i = this.d + 1;
            this.d = i;
            manager.loadVideoData(activity, tabKey, i);
        }
    }

    public void setTabData(VideoTabData videoTabData) {
        VideoTabData videoTabData2 = this.c;
        if (videoTabData2 == null || !TextUtils.equals(videoTabData2.getTabKey(), videoTabData.getTabKey())) {
            this.d = 0;
            this.c = videoTabData;
            VideoAdapter videoAdapter = this.b;
            if (videoAdapter != null) {
                videoAdapter.setVideoDataList(null);
            }
            if (this.recyclerView != null) {
                b();
                loadData();
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected RecyclerView.LayoutManager initLayoutManager() {
        return this.h.getLayoutManager();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void init(Context context) {
        super.init(context);
        L.homepage.i("VideoFragment init");
        this.h = new VideoLayoutScrollMix(context, this);
        this.i = context.getResources().getDimensionPixelSize(R.dimen.video_item_margin);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadData() {
        L.homepage.i("VideoFragment loadData");
        if (ApiManager.isInited()) {
            loadDataFromSever();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected boolean hasData() {
        return (this.f == null || this.c == null) ? false : true;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadDataFromSever() {
        L.homepage.i("VideoFragment loadDataFromSever");
        String tabKey = this.c.getTabKey();
        if ("HISTORY".equals(tabKey)) {
            ApiRealmHelper.getInstance().queryAllVideoDatas();
        } else {
            HomeVideoDataManager.getManager().loadVideoData(this.context, tabKey, this.d);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void initViews(View view) {
        this.a = view.findViewById(R.id.loading_layout);
        this.recyclerView.addOnScrollListener(this.h.getOnScrollListener());
        this.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.VideoFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NonNull Rect rect, @NonNull View view2, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                super.getItemOffsets(rect, view2, recyclerView, state);
                rect.right = VideoFragment.this.i;
            }
        });
        this.g = new VideoFooterViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.loading_layout, (ViewGroup) this.recyclerView, false)).setLoadMoreCallback(this).setCallback(this);
        this.g.initInMain();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshVideoData(RecommendEvent.RefreshVideoData refreshVideoData) {
        if (refreshVideoData != null) {
            String tabKey = this.c.getTabKey();
            if (!TextUtils.equals(refreshVideoData.tabKey, tabKey)) {
                if ("HISTORY".equals(tabKey)) {
                    ApiRealmHelper.getInstance().queryAllVideoDatas();
                } else {
                    HomeVideoDataManager.getManager().loadVideoData(this.context, tabKey, this.d);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHomeVideoDataLoadedSuc(HomeVideoLoadSuccess homeVideoLoadSuccess) {
        if (homeVideoLoadSuccess != null) {
            this.f = homeVideoLoadSuccess.homeVideoData;
            if (this.f != null) {
                c();
                a();
                a(this.f);
                List<VideoData> videoDataList = this.b.getVideoDataList();
                if (ContainerUtil.isEmpty(this.f.getVideoList()) && ContainerUtil.isEmpty(videoDataList)) {
                    this.b.setVideoDataList(null);
                } else if (ContainerUtil.isEmpty(videoDataList)) {
                    this.b.setVideoDataList(this.f.getVideoList());
                } else {
                    videoDataList.addAll(this.f.getVideoList());
                    this.b.setVideoDataList(videoDataList);
                }
            }
        }
    }

    private void a(HomeVideoData homeVideoData) {
        L.homepage.i("VideoFragment has more : %b", Boolean.valueOf(homeVideoData.isHasMore()));
        this.g.hasMore(homeVideoData.isHasMore());
        this.g.refreshUI();
    }

    private void a() {
        if (this.recyclerView.getVisibility() == 8) {
            this.recyclerView.setVisibility(0);
        }
        if (this.a.getVisibility() == 0) {
            this.a.setVisibility(8);
        }
    }

    private void b() {
        if (this.recyclerView.getVisibility() == 0) {
            this.recyclerView.setVisibility(8);
        }
        if (this.a.getVisibility() == 8) {
            this.a.setVisibility(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void queryVideoDataSuccess(VideoQueryEvent videoQueryEvent) {
        VideoTabData videoTabData = this.c;
        if (videoTabData == null || "HISTORY".equals(videoTabData.getTabKey())) {
            List<VideoData> list = videoQueryEvent.datas;
            a();
            c();
            if (ContainerUtil.hasData(list)) {
                this.b.setFootViewHolder(null);
                this.b.setDataInfo(this.c, "", "");
                this.b.setVideoDataList(list);
                return;
            }
            this.b.setVideoDataList(null);
        }
    }

    private void c() {
        if (this.b == null) {
            this.b = new VideoAdapter();
            this.b.setDataInfo(this.c, "", "");
            this.recyclerView.setAdapter(this.b);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder.OnFooterRefreshCallback
    public void onFooterRefresh() {
        this.recyclerView.scrollToPosition(0);
        refreshData();
        if (this.refreshDataListener != null) {
            this.refreshDataListener.onRefresh();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void refreshData() {
        this.d = 0;
        HomeVideoDataManager.getManager().clearLayout();
        HomeVideoDataManager.getManager().loadVideoData(this.context, this.c.getTabKey(), this.d);
        L.homepage.i("refresh tab : %s", this.c.getTabKey());
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder.LoadMoreCallback
    public void loadMore() {
        autoPaging();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment, com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        VideoLayoutScrollMix videoLayoutScrollMix = this.h;
        if (videoLayoutScrollMix != null) {
            videoLayoutScrollMix.release();
        }
        super.onDestroy();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.EntertainmentFragment.FragmentRefreshListener
    public void onRefresh() {
        if (hasData() && this.recyclerView != null && this.recyclerView.getAdapter() != null) {
            this.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetRecyclerViewPosition(ResetScrollViewPositionEvent resetScrollViewPositionEvent) {
        if (!ChildModeManager.getManager().isChildMode() && resetScrollViewPositionEvent.getEventType() == 1 && this.recyclerView != null && hasData()) {
            this.recyclerView.scrollToPosition(0);
        }
    }
}
