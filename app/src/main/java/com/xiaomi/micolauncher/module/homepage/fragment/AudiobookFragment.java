package com.xiaomi.micolauncher.module.homepage.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.StatusBarEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.common.widget.HorizontalRefreshLayout;
import com.xiaomi.micolauncher.common.widget.RefreshCallBack;
import com.xiaomi.micolauncher.common.widget.RefreshLayout;
import com.xiaomi.micolauncher.module.homepage.adapter.AudiobookAdapterWrap;
import com.xiaomi.micolauncher.module.homepage.adapter.AudiobookContentAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.event.LoadAudiobookRecommendEvent;
import com.xiaomi.micolauncher.module.homepage.event.ResetScrollViewPositionEvent;
import com.xiaomi.micolauncher.module.homepage.fragment.EntertainmentFragment;
import com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager;
import com.xiaomi.micolauncher.module.homepage.view.EntertainmentHolderCacheManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookRefreshViewHolder;
import com.xiaomi.micolauncher.module.main.util.HomePageStatHelper;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.miplay.mylibrary.mirror.MirrorControl;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AudiobookFragment extends BaseHomeFragment implements RefreshCallBack, EntertainmentFragment.FragmentRefreshListener, AudiobookRefreshViewHolder.OnFooterRefreshCallback {
    public static final String TAG = "AudiobookFragment";
    HorizontalRefreshLayout a;
    private View b;
    private AudiobookAdapterWrap c;
    private AudiobookContentAdapter d;
    private boolean g;
    private String e = "0";
    private String f = "0";
    private boolean h = true;

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
        return R.layout.audiobooks_layout;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void init(Context context) {
        super.init(context);
        AudiobookDataManager.getManager().initResIdMap(getContext());
        a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
        HomePageStatHelper.recordAudiobookPageShow();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadData() {
        if (ApiManager.isInited()) {
            loadDataFromSever();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected boolean hasData() {
        return this.d != null;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadDataFromSever() {
        AudiobookDataManager.getManager().loadAllCategory();
        b();
    }

    private void a() {
        this.e = PreferenceUtils.getSettingString(getContext(), AudiobookDataManager.FIRST_ITEM_ID, "0");
        this.f = PreferenceUtils.getSettingString(getContext(), AudiobookDataManager.SECOND_ITEM_ID, "0");
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onStatusBarChange(StatusBarEvent statusBarEvent) {
        if (statusBarEvent != null && statusBarEvent.getType() == StatusBarEvent.StatusBarType.STATUS_BAR_TYPE_WIFI && !statusBarEvent.isOfflineStatus() && this.b.getVisibility() == 0) {
            b();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z && this.b.getVisibility() == 0) {
            b();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void initViews(View view) {
        this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, 0, false) { // from class: com.xiaomi.micolauncher.module.homepage.fragment.AudiobookFragment.1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }
        });
        final int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.audiobook_colon_margin_right);
        this.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.AudiobookFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                int childAdapterPosition = recyclerView.getChildAdapterPosition(view2);
                if (childAdapterPosition > 2 && childAdapterPosition % 2 != 0) {
                    rect.top = dimensionPixelOffset;
                }
            }
        });
        this.b = view.findViewById(R.id.audiobook_loading);
        this.a = (HorizontalRefreshLayout) view.findViewById(R.id.refresh_layout);
        this.a.setHeadMode(RefreshLayout.HeadMode.REFRESH, this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadRecommendSuccess(LoadAudiobookRecommendEvent loadAudiobookRecommendEvent) {
        if (this.g) {
            this.a.onRefreshComplete();
        }
        if (ContainerUtil.isEmpty(loadAudiobookRecommendEvent.recommendDatas)) {
            L.homepage.e("loadRecommendSuccess data is empty");
            return;
        }
        a();
        if (this.d == null) {
            this.d = new AudiobookContentAdapter();
            this.d.setForMainPage(true);
        }
        this.d.setContents(loadAudiobookRecommendEvent.recommendDatas, TrackWidget.STATION_DISCOVER_RECOMMEND);
        if (this.c == null) {
            this.c = new AudiobookAdapterWrap(this.d);
            long beginTiming = DebugHelper.beginTiming();
            this.c.addHeaderHolder(EntertainmentHolderCacheManager.getManager().fetchAudiobookViewHolder(getContext(), 100000));
            this.c.addHeaderHolder(EntertainmentHolderCacheManager.getManager().fetchAudiobookViewHolder(getContext(), MirrorControl.DISPLAY_INFO_IS_UDP));
            this.c.addFooterHolder(((AudiobookRefreshViewHolder) EntertainmentHolderCacheManager.getManager().fetchAudiobookViewHolder(getContext(), 200000)).setCallback(this));
            DebugHelper.endTiming(beginTiming, "audiobook create holder", new Object[0]);
        }
        this.h = false;
        this.c.setFirstRegionData(loadAudiobookRecommendEvent.firstRegionData);
        this.c.setSecondRegionData(loadAudiobookRecommendEvent.secondRegionData);
        setAdapter(this.c);
        L.homepage.d("adapterWrap is : %s", this.c);
        this.b.setVisibility(8);
    }

    @Override // com.xiaomi.micolauncher.common.widget.RefreshCallBack
    public void onRefreshing() {
        this.g = true;
        b();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookRefreshViewHolder.OnFooterRefreshCallback
    public void onFooterRefresh() {
        this.recyclerView.scrollToPosition(0);
        if (this.refreshDataListener != null) {
            this.refreshDataListener.onRefresh();
        } else {
            refreshData();
        }
    }

    private void b() {
        long beginTiming = DebugHelper.beginTiming();
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$AudiobookFragment$zX3ULrYYBMeKQrN6h3BBAC4GGNc
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AudiobookFragment.this.a(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).subscribe();
        DebugHelper.endTiming(beginTiming, "audiobook loadRecommendData", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        AudiobookDataManager.getManager().loadRecommendData(getContext(), this.e, this.f);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void refreshData() {
        if (!this.h) {
            if (this.refreshDataListener != null) {
                this.refreshDataListener.onRefresh();
            }
            this.g = true;
            b();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment, com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.b = null;
        this.a = null;
        this.refreshDataListener = null;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        EntertainmentHolderCacheManager.getManager().clearBookViewHolder();
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
