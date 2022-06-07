package com.xiaomi.micolauncher.module.homepage.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.homepage.VideoLayoutScrollMix;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.HomeVideoAdapter;
import com.xiaomi.micolauncher.module.homepage.bean.HomeVideoData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo;
import com.xiaomi.micolauncher.module.homepage.event.HomeVideoLoadSuccess;
import com.xiaomi.micolauncher.module.homepage.event.ResetScrollViewPositionEvent;
import com.xiaomi.micolauncher.module.homepage.fragment.EntertainmentFragment;
import com.xiaomi.micolauncher.module.homepage.manager.HomeVideoDataManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoViewHoldersCache;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class HomeVideoFragment extends BaseHomeFragment implements VideoLayoutScrollMix.NextPageListener, EntertainmentFragment.FragmentRefreshListener, VideoFooterViewHolder.LoadMoreCallback, VideoFooterViewHolder.OnFooterRefreshCallback {
    public static final String TAG = "HomeVideoFragment";
    private static final long a = TimeUnit.HOURS.toMillis(2);
    private View b;
    private HomeVideoAdapter c;
    private HomeVideoData d;
    private VideoFooterViewHolder e;
    private int f = 0;
    private boolean g;
    private boolean h;
    private boolean i;
    private long j;
    private VideoTabData k;
    private VideoLayoutScrollMix l;
    private VideoData m;
    private int n;

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
        return R.layout.fragment_home_video_layout;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
    }

    public void setTabData(VideoTabData videoTabData) {
        this.k = videoTabData;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.VideoLayoutScrollMix.NextPageListener
    public void autoPaging() {
        if (this.g && this.d.isHasMore() && !this.h) {
            this.h = true;
            HomeVideoDataManager manager = HomeVideoDataManager.getManager();
            Activity activity = this.context;
            int i = this.f + 1;
            this.f = i;
            manager.loadVideoData(activity, HomeVideoDataManager.TAB_KEY_REC, i);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void init(Context context) {
        super.init(context);
        L.homepage.i("HomeVideoFragment init");
        this.l = new VideoLayoutScrollMix(context, this, true);
        this.l.resetExtraSpaceToDefault();
        this.n = context.getResources().getDimensionPixelSize(R.dimen.video_item_margin);
        HomeVideoDataManager.getManager().loadFirstScreenDataFromDb(context);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadData() {
        HomeVideoLoadSuccess homeVideoLoadSuccess = HomeVideoDataManager.getManager().getHomeVideoLoadSuccess();
        if (homeVideoLoadSuccess == null || homeVideoLoadSuccess.homeVideoData == null) {
            HomeVideoDataManager.getManager().loadHomeData(this.context);
            return;
        }
        onHomeVideoDataLoadedSuc(homeVideoLoadSuccess);
        Threads.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.HomeVideoFragment.1
            @Override // java.lang.Runnable
            public void run() {
                HomeVideoDataManager.getManager().loadHomeData(HomeVideoFragment.this.context);
            }
        }, 3000L);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected boolean hasData() {
        return this.d != null;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void refreshData() {
        this.f = 0;
        HomeVideoDataManager.getManager().clearLayout();
        HomeVideoDataManager.getManager().loadVideoData(this.context, HomeVideoDataManager.TAB_KEY_REC, this.f);
        L.homepage.i("HomeVideoFragment refresh Data");
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void initViews(View view) {
        this.b = view.findViewById(R.id.loading_layout);
        this.recyclerView.addOnScrollListener(this.l.getOnScrollListener());
        this.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.HomeVideoFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NonNull Rect rect, @NonNull View view2, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                super.getItemOffsets(rect, view2, recyclerView, state);
                rect.right = HomeVideoFragment.this.n;
            }
        });
        this.e = new VideoFooterViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.loading_layout, (ViewGroup) this.recyclerView, false)).setLoadMoreCallback(this).setCallback(this);
        this.e.initInMain();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected RecyclerView.LayoutManager initLayoutManager() {
        return this.l.getLayoutManager();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshVideoData(RecommendEvent.RefreshHomeData refreshHomeData) {
        L.homepage.i("HomeVideoFragment onRefreshVideoData");
        HomeVideoDataManager.getManager().loadVideoData(this.context, HomeVideoDataManager.TAB_KEY_REC, this.f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.util.List] */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHomeVideoDataLoadedSuc(HomeVideoLoadSuccess homeVideoLoadSuccess) {
        int i;
        ArrayList arrayList;
        HomeVideoData homeVideoData;
        L.homepage.i("HomeVideoFragment onHomeVideoDataLoadedSuc " + homeVideoLoadSuccess.isLocalData);
        if (homeVideoLoadSuccess.isLocalData && (homeVideoData = this.d) != null && ContainerUtil.hasData(homeVideoData.getVideoList())) {
            return;
        }
        if (homeVideoLoadSuccess.homeVideoData == null) {
            L.homepage.i("HomeVideoFragment onHomeVideoDataLoadedSuc event.homeVideoData = null ");
            return;
        }
        this.d = homeVideoLoadSuccess.homeVideoData;
        this.e.hasMore(this.d.isHasMore());
        this.h = false;
        this.g = true;
        if (ContainerUtil.isEmpty(homeVideoLoadSuccess.homeVideoData.getVideoList())) {
            L.homepage.i("HomeVideoFragment onHomeVideoDataLoadedSuc homeVideoData videoDataList is null");
            this.f = 0;
            HomeVideoAdapter homeVideoAdapter = this.c;
            if (homeVideoAdapter != null) {
                homeVideoAdapter.hideLoadingLayout();
            }
            a(this.d);
            return;
        }
        a();
        List<VideoData> videoList = homeVideoLoadSuccess.homeVideoData.getVideoList();
        ArrayList arrayList2 = new ArrayList();
        if (this.f == 0) {
            this.m = null;
            VideoTabInfo.RecommendAppCard recommendAppCard = HomeVideoDataManager.getManager().getRecommendAppCard();
            if (recommendAppCard != null) {
                arrayList2.add(recommendAppCard);
            }
            VideoTabInfo.RecommendCategory recommendCategory = HomeVideoDataManager.getManager().getRecommendCategory();
            if (recommendCategory != null) {
                arrayList2.add(videoList.get(0));
                arrayList2.add(recommendCategory);
                if (this.d.isHasMore()) {
                    int size = videoList.size();
                    if (size % 2 == 0) {
                        this.m = videoList.get(size - 1);
                    }
                    if (this.m != null) {
                        arrayList2.addAll(videoList.subList(1, size - 1));
                    }
                } else {
                    arrayList2.addAll(videoList.subList(1, videoList.size()));
                }
            } else {
                arrayList2.addAll(videoList);
            }
        } else {
            VideoData videoData = this.m;
            if (videoData != null) {
                arrayList2.add(videoData);
                this.m = null;
            }
            arrayList2.addAll(videoList);
        }
        this.j = System.currentTimeMillis();
        if (this.c == null) {
            this.i = homeVideoLoadSuccess.isLocalData;
            this.c = new HomeVideoAdapter();
            this.c.setHasStableIds(true);
            this.c.setDataInfo(this.k, homeVideoLoadSuccess.homeVideoData.getTraceId(), homeVideoLoadSuccess.homeVideoData.getExpId());
            this.c.setVideoDataList(arrayList2);
            this.c.setFootViewHolder(this.e);
            this.recyclerView.setAdapter(this.c);
            return;
        }
        a(this.d);
        ArrayList arrayList3 = this.c.getVideoDataList();
        if (this.f == 0) {
            arrayList3 = null;
        }
        if (this.i) {
            L.homepage.i("HomeVideoFragmentisLocalData = true");
            this.i = false;
            this.l.resetExtraSpace();
            this.c.setDataInfo(this.k, homeVideoLoadSuccess.homeVideoData.getTraceId(), homeVideoLoadSuccess.homeVideoData.getExpId());
            this.c.setVideoDataList(arrayList2);
            this.c.notifyDataSetChanged();
            return;
        }
        if (ContainerUtil.hasData(arrayList3)) {
            i = arrayList3.size();
            L.homepage.i("HomeVideoFragmentnon first positionStart=" + i);
            arrayList3.addAll(videoList);
            arrayList = arrayList3;
        } else {
            L.homepage.i("HomeVideoFragmentxx first positionStart=0");
            arrayList = arrayList2;
            i = 0;
        }
        if (i == 0) {
            this.c.setDataInfo(this.k, homeVideoLoadSuccess.homeVideoData.getTraceId(), homeVideoLoadSuccess.homeVideoData.getExpId());
            this.c.setVideoDataList(arrayList);
            this.recyclerView.setAdapter(this.c);
            this.isFlinged = false;
            return;
        }
        this.c.hideLoadingLayout();
        this.c.notifyItemRangeInserted(i, arrayList.size());
    }

    private void a(HomeVideoData homeVideoData) {
        L.homepage.i("has more : %b", Boolean.valueOf(homeVideoData.isHasMore()));
        this.e.hasMore(homeVideoData.isHasMore());
        this.e.refreshUI();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (this.k != null && !z && System.currentTimeMillis() - this.j >= a) {
            refreshData();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder.LoadMoreCallback
    public void loadMore() {
        autoPaging();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoFooterViewHolder.OnFooterRefreshCallback
    public void onFooterRefresh() {
        this.recyclerView.scrollToPosition(0);
        refreshData();
        if (this.refreshDataListener != null) {
            this.refreshDataListener.onRefresh();
        }
    }

    private void a() {
        if (this.recyclerView.getVisibility() == 8) {
            this.recyclerView.setVisibility(0);
        }
        if (this.b.getVisibility() == 0) {
            this.b.setVisibility(8);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        VideoViewHoldersCache.getInstance().clearVideoViewHolder();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment, com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        VideoLayoutScrollMix videoLayoutScrollMix = this.l;
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
