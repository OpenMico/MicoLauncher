package com.xiaomi.micolauncher.module.homepage.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.settingslib.core.BigSettings;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4SmallButton;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import com.xiaomi.micolauncher.module.TabViewPresenter;
import com.xiaomi.micolauncher.module.homepage.VideoLayoutScrollMix;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.CategoryTabAdapter;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabData;
import com.xiaomi.micolauncher.module.homepage.event.HomeVideoTabLoadSuccess;
import com.xiaomi.micolauncher.module.homepage.manager.HomeVideoDataManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.CategoryViewHolder;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.setting.SettingOpenManager;
import io.reactivex.functions.Consumer;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class EntertainmentFragment extends BaseHomeFragment implements TabViewPresenter.OnRefreshDataListener, CategoryViewHolder.TabClickListener {
    public static final String TAG = "EntertainmentFragment";
    private HorizontalRecyclerView a;
    private CategoryTabAdapter<VideoTabData> b;
    private BaseHomeFragment c;
    private AudiobookFragment d;
    private MusicFindFragment e;
    private HomeVideoFragment f;
    private HomeKidsFragment g;
    private VideoFragment h;
    private VideoLayoutScrollMix i;
    private View j;
    private ImageView k;
    private boolean l;
    private final Runnable m = new Runnable() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$EntertainmentFragment$vEhyCmhRHXl9cy2kTr2Ih6yz1OM
        @Override // java.lang.Runnable
        public final void run() {
            EntertainmentFragment.this.c();
        }
    };

    /* loaded from: classes3.dex */
    public interface FragmentRefreshListener {
        void onRefresh();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected BaseAdapter<? extends RecyclerView.ViewHolder> createAdapter() {
        return null;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int itemViewCacheSize() {
        return 3;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int layoutId() {
        return R.layout.fragment_entertainment_layout;
    }

    @Override // com.xiaomi.micolauncher.module.TabViewPresenter.OnRefreshDataListener
    public void onRefresh() {
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected RecyclerView.LayoutManager initLayoutManager() {
        return this.i.getLayoutManager();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z) {
            CategoryTabAdapter<VideoTabData> categoryTabAdapter = this.b;
            if (categoryTabAdapter == null || categoryTabAdapter.getItemCount() == 0) {
                loadData();
            }
            if (this.l) {
                b();
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
        BaseHomeFragment baseHomeFragment = this.c;
        if (baseHomeFragment != null) {
            baseHomeFragment.recordShow();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void init(Context context) {
        super.init(context);
        this.i = new VideoLayoutScrollMix(context, null);
        this.f = new HomeVideoFragment();
        this.f.init(context);
        this.e = new MusicFindFragment();
        this.e.init(context);
        this.d = new AudiobookFragment();
        this.d.init(context);
        this.g = new HomeKidsFragment();
        this.g.init(context);
        registerToEventBusIfNot();
        if (HomeVideoDataManager.getManager().getHomeVideoTabDataFromLocalDB() == null) {
            HomeVideoDataManager.getManager().loadVideoTabListFromDB();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onAllApiServiceInitFinished(RecommendEvent.ApiInited apiInited) {
        HomeVideoDataManager.getManager().loadVideoTabList(this.context);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadData() {
        final HomeVideoTabLoadSuccess homeVideoTabDataFromLocalDB = HomeVideoDataManager.getManager().getHomeVideoTabDataFromLocalDB();
        if (homeVideoTabDataFromLocalDB != null) {
            this.j.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$EntertainmentFragment$SGvssqClZqoWBQkjToomXHW-H1w
                @Override // java.lang.Runnable
                public final void run() {
                    EntertainmentFragment.this.a(homeVideoTabDataFromLocalDB);
                }
            }, 300L);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    protected void initViews(View view) {
        this.a = (HorizontalRecyclerView) view.findViewById(R.id.category_recycler);
        this.d.setOnRefreshDataListener(this.refreshDataListener);
        this.j = view.findViewById(R.id.loading_layout);
        this.k = (ImageView) view.findViewById(R.id.common_set);
        AnimLifecycleManager.repeatOnAttach(this.k, MicoAnimConfigurator4SmallButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.k).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$EntertainmentFragment$JZyumfhtDoyWYPnMGLKHj5IIqM4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                EntertainmentFragment.this.a(obj);
            }
        });
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        SettingOpenManager.openSetting(this.context, BigSettings.DEFAULT);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment, com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        VideoLayoutScrollMix videoLayoutScrollMix = this.i;
        if (videoLayoutScrollMix != null) {
            videoLayoutScrollMix.release();
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshVideoTabData(RecommendEvent.RefreshVideoTabData refreshVideoTabData) {
        L.homepage.i("EntertainmentFragment onRefreshVideoTabData");
        HomeVideoDataManager.getManager().loadVideoTabList(this.context);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    /* renamed from: onHomeVideoTabLoadedSuccess */
    public void a(HomeVideoTabLoadSuccess homeVideoTabLoadSuccess) {
        if (isAdded() && homeVideoTabLoadSuccess != null) {
            a();
            List<VideoTabData> tabList = homeVideoTabLoadSuccess.getTabList();
            boolean z = true;
            if (this.b == null) {
                this.b = new CategoryTabAdapter<>(this.context, tabList, this);
                this.a.setAdapter(this.b);
            } else {
                int size = !ContainerUtil.isEmpty(tabList) ? tabList.size() : 0;
                if (this.b.getHighlightPosition() != 0 && this.b.getItemCount() == size) {
                    VideoTabData selectItem = this.b.getSelectItem();
                    Iterator<VideoTabData> it = tabList.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (TextUtils.equals(it.next().getTabKey(), selectItem.getTabKey())) {
                                z = false;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                this.b.updateTabList(tabList);
            }
            if (z) {
                this.b.setHighlightPosition(0);
                switchFragment(tabList.get(0));
            }
        }
    }

    private void a(BaseHomeFragment baseHomeFragment, BaseHomeFragment baseHomeFragment2) {
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        if (baseHomeFragment != null) {
            this.c = baseHomeFragment;
            if (!baseHomeFragment.isAdded()) {
                beginTransaction.add(R.id.home_content_fragment, baseHomeFragment).show(baseHomeFragment);
            } else {
                beginTransaction.show(baseHomeFragment);
            }
        }
        if (baseHomeFragment2 != null) {
            beginTransaction.hide(baseHomeFragment2);
        }
        beginTransaction.setTransition(0).commitNowAllowingStateLoss();
    }

    private void a() {
        View view = this.j;
        if (view != null && view.getVisibility() != 8) {
            this.j.setVisibility(8);
        }
    }

    public void switchFragment(VideoTabData videoTabData) {
        BaseHomeFragment baseHomeFragment = this.c;
        if (HomeVideoDataManager.TAB_KEY_REC.equals(videoTabData.getTabKey())) {
            HomeVideoFragment homeVideoFragment = this.f;
            if (baseHomeFragment != homeVideoFragment) {
                homeVideoFragment.setTabData(videoTabData);
                a(this.f, baseHomeFragment);
            }
        } else if ("MUSIC".equals(videoTabData.getTabKey())) {
            MusicFindFragment musicFindFragment = this.e;
            if (baseHomeFragment != musicFindFragment) {
                a(musicFindFragment, baseHomeFragment);
            }
        } else if (HomeVideoDataManager.TAB_AUDIO_BOOK.equals(videoTabData.getTabKey())) {
            AudiobookFragment audiobookFragment = this.d;
            if (baseHomeFragment != audiobookFragment) {
                a(audiobookFragment, baseHomeFragment);
            }
        } else if (HomeVideoDataManager.TAB_KEY_KID.equals(videoTabData.getTabKey())) {
            HomeKidsFragment homeKidsFragment = this.g;
            if (baseHomeFragment != homeKidsFragment) {
                a(homeKidsFragment, baseHomeFragment);
            }
        } else {
            if (this.h == null) {
                this.h = new VideoFragment();
                this.h.init(getContext());
            }
            this.h.setTabData(videoTabData);
            VideoFragment videoFragment = this.h;
            if (baseHomeFragment == videoFragment) {
                baseHomeFragment = null;
            }
            a(videoFragment, baseHomeFragment);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.CategoryViewHolder.TabClickListener
    public void onTabClick(int i, Object obj) {
        if (this.b.setHighlightPosition(i)) {
            switchFragment((VideoTabData) obj);
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        L.homepage.i("onLowMemory enterTainmentFragment isHidden: %b,isOnResume: %b", Boolean.valueOf(isHidden()), Boolean.valueOf(isOnResume()));
        if (isHidden() || !isOnResume()) {
            this.l = true;
        } else {
            b();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (!isHidden() && this.l) {
            b();
        }
    }

    public /* synthetic */ void c() {
        if (isDetached()) {
            this.l = false;
        } else if (isHidden() || !isOnResume()) {
            this.l = true;
        } else {
            this.l = false;
            BaseHomeFragment baseHomeFragment = this.c;
            if (baseHomeFragment != null && (baseHomeFragment instanceof FragmentRefreshListener)) {
                ((FragmentRefreshListener) baseHomeFragment).onRefresh();
            }
        }
    }

    private void b() {
        ThreadUtil.removeCallbacksInMainThread(this.m);
        ThreadUtil.postDelayedInMainThread(this.m, 1500L);
    }
}
