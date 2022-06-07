package com.xiaomi.micolauncher.module.homepage.fragment;

import android.view.View;
import android.view.ViewStub;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.MusicSourceLoadHelper;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.module.homepage.HomePageUtils;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.ChildSongAdapter;
import com.xiaomi.micolauncher.module.homepage.event.AuthStatusEvent;
import com.xiaomi.micolauncher.module.homepage.event.HomeChildSongLoadEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadChildSongFirstScreenSuccessEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadFirstScreenDataFailedEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadRecommendDataFailedEvent;
import com.xiaomi.micolauncher.module.homepage.manager.ChildSongDataManager;
import com.xiaomi.micolauncher.module.homepage.manager.HomeMusicDataManager;
import com.xiaomi.micolauncher.module.music.manager.MusicDataManager;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class ChildSongFragment extends BaseHomeFragment {
    public static final String TAG = "ChildSongFragment";
    ViewStub a;
    private boolean b;
    private boolean c = false;
    private boolean d;

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public String identifier() {
        return TAG;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int itemViewCacheSize() {
        return 4;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int layoutId() {
        return R.layout.layout_child_song_find;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicSourceChanged(RecommendEvent.MusicSourceChanged musicSourceChanged) {
        if (HomePageUtils.isSourceQQ()) {
            e();
            this.b = true;
            c();
            b();
            return;
        }
        d();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void onTimeTickUpdate() {
        if (!isHidden() && isOnResume()) {
            updateDate();
        }
    }

    public void updateDate() {
        getAdapter().notifyItemChanged(0, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onQQMusicUnbinded(RecommendEvent.QQMusicUnBinded qQMusicUnBinded) {
        this.b = false;
        d();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWifiConnectivityChanged(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        if (wifiConnectivityChangedEvent.connected) {
            if (ChildSongDataManager.getManager().hasData()) {
                HomeMusicDataManager.getManager().checkAuthStatus(getContext());
            }
            a();
        }
    }

    private void a() {
        final MusicSourceLoadHelper musicSourceLoadHelper = new MusicSourceLoadHelper(getContext());
        musicSourceLoadHelper.loadMusicSource(new MusicSourceLoadHelper.MusicSourceLoadCallback() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$ChildSongFragment$YFnqBFXHumN9_63RnSJ0fHhcbTI
            @Override // com.xiaomi.micolauncher.api.MusicSourceLoadHelper.MusicSourceLoadCallback
            public final void onMusicSourceChanged(String str) {
                ChildSongFragment.this.a(musicSourceLoadHelper, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MusicSourceLoadHelper musicSourceLoadHelper, String str) {
        if (HomePageUtils.MUSIC_SOURCE_MI.equals(str)) {
            e();
            d();
        } else {
            b();
        }
        musicSourceLoadHelper.release();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadData() {
        ChildSongDataManager.getManager().clearCache();
        this.b = PreferenceUtils.getSettingBoolean(getContext(), HomePageUtils.QQ_MUSIC_BIND, true);
        L.homepage.d("load music data,isbind : %b,isSourceQQ : %b", Boolean.valueOf(this.b), Boolean.valueOf(HomePageUtils.isSourceQQ()));
        if (!HomePageUtils.isSourceQQ(getContext()) || !this.b) {
            d();
            return;
        }
        b();
        if (ChildSongDataManager.getManager().hasData()) {
            getAdapter().notifyDataSetChanged();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void initViews(View view) {
        this.a = (ViewStub) view.findViewById(R.id.auth_status);
        this.a.setOnInflateListener(new ViewStub.OnInflateListener() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$ChildSongFragment$94iuRnc1LDKhnn20IiJlRuBmpgU
            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub, View view2) {
                ChildSongFragment.this.a(viewStub, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ViewStub viewStub, View view) {
        this.c = true;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected BaseAdapter<? extends RecyclerView.ViewHolder> createAdapter() {
        return new ChildSongAdapter();
    }

    private void b() {
        L.homepage.d("%s loadFirstScreenData", getClass().getName());
        ChildSongDataManager.getManager().loadHomeChildSong();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadChildSongFirstScreenSuccess(LoadChildSongFirstScreenSuccessEvent loadChildSongFirstScreenSuccessEvent) {
        a(loadChildSongFirstScreenSuccessEvent.block);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChildSongRecommend(HomeChildSongLoadEvent homeChildSongLoadEvent) {
        a(homeChildSongLoadEvent.viewType, homeChildSongLoadEvent.block);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadFirstScreenDataFailed(LoadFirstScreenDataFailedEvent loadFirstScreenDataFailedEvent) {
        a(loadFirstScreenDataFailedEvent.throwable);
    }

    private void a(PatchWall.Block block) {
        a(0, block);
    }

    private void a(Throwable th) {
        L.homepage.d("load data failed : ", th);
        if ((th instanceof MusicDataManager.QQNotBindedException) || (th instanceof MusicDataManager.QQExpiredException)) {
            d();
        }
    }

    private void c() {
        this.d = false;
        ((ChildSongAdapter) getAdapter()).showItems();
        if (this.c) {
            this.a.setVisibility(8);
        }
    }

    private void d() {
        this.d = true;
        ((ChildSongAdapter) getAdapter()).hideItems();
        e();
        if (this.c) {
            this.a.setVisibility(0);
        } else {
            this.a.inflate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadRecommendDataFailed(LoadRecommendDataFailedEvent loadRecommendDataFailedEvent) {
        a(loadRecommendDataFailedEvent.throwable);
    }

    private void a(int i, PatchWall.Block block) {
        if (!this.d) {
            getAdapter().notifyItemChanged(i, block);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        L.homepage.d("%s  onHiddenChanged %b", getClass().getName(), Boolean.valueOf(z));
        if (z || !this.b || !HomePageUtils.isSourceQQ()) {
            return;
        }
        if (ChildSongDataManager.getManager().hasData()) {
            HomeMusicDataManager.getManager().checkAuthStatus(getContext());
        } else {
            b();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAuthStatusChanged(AuthStatusEvent authStatusEvent) {
        d();
    }

    private void e() {
        ChildSongDataManager.getManager().clearCache();
        getAdapter().notifyDataSetChanged();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ChildSongDataManager.getManager().loadRecentPlayItemCover();
    }
}
