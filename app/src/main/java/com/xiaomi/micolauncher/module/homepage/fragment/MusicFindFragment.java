package com.xiaomi.micolauncher.module.homepage.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewStub;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.homepage.HomePageUtils;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.MusicAdapter;
import com.xiaomi.micolauncher.module.homepage.event.AuthStatusEvent;
import com.xiaomi.micolauncher.module.homepage.event.AutoPagingFailedEvent;
import com.xiaomi.micolauncher.module.homepage.event.AutoPagingSuccessEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadFirstScreenDataFailedEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadFirstScreenDataSuccessEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadMusicCacheSuccessEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadRecommendDataEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadRecommendDataFailedEvent;
import com.xiaomi.micolauncher.module.homepage.event.MusicItemDataChangeEvent;
import com.xiaomi.micolauncher.module.homepage.event.ResetScrollViewPositionEvent;
import com.xiaomi.micolauncher.module.homepage.fragment.EntertainmentFragment;
import com.xiaomi.micolauncher.module.homepage.manager.HomeMusicDataManager;
import com.xiaomi.micolauncher.module.homepage.view.EntertainmentHolderCacheManager;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.music.manager.MusicDataManager;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class MusicFindFragment extends BaseHomeFragment implements EntertainmentFragment.FragmentRefreshListener {
    public static final String TAG = "MusicFindFragment";
    private ViewStub a;
    private boolean c;
    private int d;
    private boolean f;
    private final List<PatchWall.Block> b = new ArrayList();
    private boolean e = true;
    private boolean g = true;
    private boolean h = false;
    private boolean i = false;
    private boolean j = true;

    private int a(int i) {
        return i;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public String identifier() {
        return TAG;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int itemViewCacheSize() {
        return 15;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int layoutId() {
        return R.layout.music_find_layout;
    }

    public MusicFindFragment() {
        HomeMusicDataManager.getManager().loadGroupFromDb();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void init(Context context) {
        super.init(context);
        if (ApiManager.isInited()) {
            loadDataFromSever();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected boolean hasData() {
        return ContainerUtil.hasData(this.b);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadDataFromSever() {
        HomeMusicDataManager.getManager().checkAuthStatus(this.context);
        RecommendDataManager.getManager().loadMusicSource(this.context);
        HomeMusicDataManager.getManager().loadFirstScreenData();
        HomeMusicDataManager.getManager().loadMusicData(this.context, this.d);
        HomeMusicDataManager manager = HomeMusicDataManager.getManager();
        Activity activity = this.context;
        int i = this.d + 1;
        this.d = i;
        manager.autoPaging(activity, i);
        HomeMusicDataManager manager2 = HomeMusicDataManager.getManager();
        Activity activity2 = this.context;
        int i2 = this.d + 1;
        this.d = i2;
        manager2.autoPaging(activity2, i2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicSourceChanged(RecommendEvent.MusicSourceChanged musicSourceChanged) {
        j();
        if (g()) {
            getAdapter().notifyItemRemoved(0);
            d();
            a();
            return;
        }
        e();
    }

    public void updateDate() {
        getAdapter().notifyItemChanged(0, 0);
        HomeMusicDataManager.getManager().resetDataChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onQQMusicUnbinded(RecommendEvent.QQMusicUnBinded qQMusicUnBinded) {
        j();
        e();
        if (this.refreshDataListener != null) {
            this.refreshDataListener.onRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshFirstMusic(RecommendEvent.RefreshFirstScreenMusicData refreshFirstScreenMusicData) {
        HomeMusicDataManager.getManager().loadFirstScreenData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshMusicRecommend(RecommendEvent.RefreshMusicRecommend refreshMusicRecommend) {
        HomeMusicDataManager.getManager().loadMusicData(this.context, this.d);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshPageMusic(RecommendEvent.RefreshPageMusicData refreshPageMusicData) {
        HomeMusicDataManager.getManager().autoPaging(this.context, this.d);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
        MusicStatHelper.recordPatchWallShow();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadData() {
        L.homepage.d("load music data,isbind : %b,isSourceQQ : %b", Boolean.valueOf(PreferenceUtils.getSettingBoolean(this.context, HomePageUtils.QQ_MUSIC_BIND, true)), Boolean.valueOf(HomePageUtils.isSourceQQ(this.context)));
        if (g()) {
            SparseArray<PatchWall.Block> cacheBlocks = HomeMusicDataManager.getManager().getCacheBlocks();
            int size = cacheBlocks.size();
            if (!ContainerUtil.hasData(cacheBlocks) || size != 15) {
                HomeMusicDataManager.getManager().clearCache();
                a();
                return;
            }
            this.b.clear();
            this.j = false;
            for (int i = 0; i < size; i++) {
                this.b.add(cacheBlocks.get(i));
            }
            getAdapter().notifyItemRangeInserted(0, this.b.size());
            b();
            return;
        }
        e();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void onTimeTickUpdate() {
        if (!isHidden() && isOnResume()) {
            updateDate();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void initViews(View view) {
        this.a = (ViewStub) view.findViewById(R.id.empty_layout);
        this.a.setOnInflateListener(new ViewStub.OnInflateListener() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$MusicFindFragment$1ZOwtEhnSdH401Q1H6mi_fGUNGQ
            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub, View view2) {
                MusicFindFragment.this.a(viewStub, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ViewStub viewStub, View view) {
        this.i = true;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void onScrolled() {
        if (this.j) {
            if (this.g) {
                this.g = false;
                L.homepage.d("onScrolled loadmusicdata.................");
                f();
                return;
            }
            L.homepage.d("onScrolled autoPaging");
            h();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected BaseAdapter<? extends RecyclerView.ViewHolder> createAdapter() {
        return new MusicAdapter(this.b);
    }

    private void a() {
        L.homepage.d("%s loadFirstScreenData", getClass().getName());
        this.e = true;
        this.g = true;
        this.j = true;
        this.d = 0;
        if (ContainerUtil.isEmpty(HomeMusicDataManager.getManager().getCacheBlocks())) {
            c();
        }
        HomeMusicDataManager.getManager().loadFirstScreenData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadFirstScreenDataSuccess(LoadFirstScreenDataSuccessEvent loadFirstScreenDataSuccessEvent) {
        a(loadFirstScreenDataSuccessEvent.patchWall.blocks);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadFirstScreenDataFailed(LoadFirstScreenDataFailedEvent loadFirstScreenDataFailedEvent) {
        a(loadFirstScreenDataFailedEvent.throwable);
    }

    private void a(List<PatchWall.Block> list) {
        L.homepage.d("loadFirstScreenSuccess blocks : %s", list);
        if (g()) {
            this.c = true;
            b();
            b(0, list);
        }
    }

    private void a(Throwable th) {
        L.homepage.d("load data failed : ", th);
        if ((th instanceof MusicDataManager.QQNotBindedException) || (th instanceof MusicDataManager.QQExpiredException)) {
            e();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadMusicCacheSuccess(LoadMusicCacheSuccessEvent loadMusicCacheSuccessEvent) {
        L.homepage.d("load cache data.");
        a(loadMusicCacheSuccessEvent.blocks);
    }

    private void b() {
        this.a.setVisibility(8);
    }

    private void c() {
        L.homepage.d("showEmptyLayout........");
        if (this.i) {
            this.a.setVisibility(0);
        } else {
            this.a.inflate();
        }
    }

    private void d() {
        ((MusicAdapter) getAdapter()).setAuthStatus(false);
    }

    private void e() {
        b();
        this.b.clear();
        ((MusicAdapter) getAdapter()).setAuthStatus(true);
        getAdapter().notifyDataSetChanged();
    }

    private void f() {
        this.f = true;
        HomeMusicDataManager.getManager().loadMusicData(this.context, this.d);
    }

    private boolean g() {
        return HomePageUtils.isBindQQ(getContext());
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (g()) {
            d();
        } else {
            e();
            b();
        }
        if (!isHidden() && HomeMusicDataManager.getManager().isDataChanged()) {
            updateDate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadRecommendDataSuccess(LoadRecommendDataEvent loadRecommendDataEvent) {
        if (g()) {
            this.c = true;
            this.f = false;
            b(2, loadRecommendDataEvent.patchWall.blocks);
            b();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadRecommendDataFailed(LoadRecommendDataFailedEvent loadRecommendDataFailedEvent) {
        this.f = false;
        a(loadRecommendDataFailedEvent.throwable);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAutoPagingSuccess(AutoPagingSuccessEvent autoPagingSuccessEvent) {
        if (g()) {
            if (autoPagingSuccessEvent.patchWall == null || !ContainerUtil.hasData(autoPagingSuccessEvent.patchWall.blocks)) {
                this.e = false;
                this.f = false;
                return;
            }
            a(autoPagingSuccessEvent.type, autoPagingSuccessEvent.patchWall.blocks);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicItemDataChanged(MusicItemDataChangeEvent musicItemDataChangeEvent) {
        if (g() && musicItemDataChangeEvent.type < this.b.size()) {
            this.b.set(musicItemDataChangeEvent.type, musicItemDataChangeEvent.block);
            getAdapter().notifyItemChanged(musicItemDataChangeEvent.type, 0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAutoPagingFailed(AutoPagingFailedEvent autoPagingFailedEvent) {
        this.f = false;
    }

    private void h() {
        if (this.c && this.e && !this.f) {
            this.f = true;
            HomeMusicDataManager manager = HomeMusicDataManager.getManager();
            Activity activity = this.context;
            int i = this.d + 1;
            this.d = i;
            manager.autoPaging(activity, i);
        }
    }

    private void a(int i, List<PatchWall.Block> list) {
        this.f = false;
        b(a(i), list);
    }

    private void b(int i, List<PatchWall.Block> list) {
        int size = list.size();
        int size2 = this.b.size();
        if (size2 > i) {
            for (int i2 = 0; i2 < size; i2++) {
                int i3 = i2 + i;
                if (i3 < size2) {
                    this.b.set(i3, list.get(i2));
                } else {
                    this.b.add(list.get(i2));
                }
            }
            getAdapter().notifyItemRangeChanged(i, size);
            return;
        }
        int i4 = i - size2;
        if (i4 > 0) {
            for (int i5 = 0; i5 < i4; i5++) {
                this.b.add(list.get(0));
            }
        }
        this.b.addAll(list);
        getAdapter().notifyItemRangeInserted(i, size);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        L.homepage.d("%s  onHiddenChanged %b", getClass().getName(), Boolean.valueOf(z));
        if (z || !g()) {
            return;
        }
        if (ContainerUtil.hasData(this.b)) {
            if (HomeMusicDataManager.getManager().isDataChanged()) {
                updateDate();
            }
            HomeMusicDataManager.getManager().checkAuthStatus(this.context);
            return;
        }
        a();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAuthStatusChanged(AuthStatusEvent authStatusEvent) {
        j();
        e();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWifiConnectivityChanged(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        if (wifiConnectivityChangedEvent.connected) {
            if (ContainerUtil.hasData(this.b)) {
                HomeMusicDataManager.getManager().checkAuthStatus(this.context);
            }
            i();
        }
    }

    @SuppressLint({"CheckResult"})
    private void i() {
        ApiManager.userProfileService.getMusicSource().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$MusicFindFragment$lmKz8hfSHSQUeNLuFWkP5OfnmF8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicFindFragment.this.a((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) throws Exception {
        if (!str.equals(PreferenceUtils.getSettingString(this.context, HomePageUtils.MUSIC_SOURCE, "QQ"))) {
            HomePageUtils.setMusicSource(this.context, str);
            if (HomePageUtils.MUSIC_SOURCE_MI.equals(str)) {
                j();
                e();
                return;
            }
            a();
        }
    }

    private void j() {
        this.j = false;
        HomeMusicDataManager.getManager().clearCache();
        int size = this.b.size();
        this.b.clear();
        getAdapter().notifyItemRangeRemoved(0, size);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment, com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.a = null;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        EntertainmentHolderCacheManager.getManager().clearMusicViewHolder();
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
