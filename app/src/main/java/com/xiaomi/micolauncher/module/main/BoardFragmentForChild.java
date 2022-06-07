package com.xiaomi.micolauncher.module.main;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.main.BoardFragment;
import com.xiaomi.micolauncher.module.main.FadeInCardView;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.main.view.FadeInCardViewInterface;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeRecentPlayHelper;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeRefreshHelper;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.ChildModeConfigChangedEvent;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class BoardFragmentForChild extends BaseFragment {
    protected static final int MSG_REFRESH_PAGE = 1;
    protected static final long REFRESH_PAGE_TIME_PERIOD = TimeUnit.SECONDS.toMillis(8);
    FadeInCardView a;
    RecyclerView b;
    CardView c;
    @Nullable
    FadeInCardViewInterface d;
    private View e;
    private BoardFragment.b f;
    private List<FadeInCardView.FadeInCardItem> g;
    private int h;
    @Nullable
    private FadeInCardView.FadeInCardItem i;
    private ChildModeRefreshHelper k;
    private Handler m;
    private ChildModeRecentPlayHelper j = new ChildModeRecentPlayHelper(getContext());
    private List<MainPage.KidMenuItem> l = new ArrayList();
    private Handler.Callback n = new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.main.BoardFragmentForChild.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            BoardFragmentForChild.this.refreshPage();
            return false;
        }
    };

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.k = new ChildModeRefreshHelper();
        this.j.loadRecentPlayList(getContext());
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (this.e == null) {
            this.e = layoutInflater.inflate(R.layout.fragment_board, viewGroup, false);
            a(this.e);
        }
        this.m = new WeakRefHandler(this.n);
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        b();
        d();
        refreshData();
        return this.e;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        a();
        this.f.notifyDataSetChanged();
    }

    private void a() {
        if (this.k.needRefresh()) {
            b();
            d();
            refreshData();
        }
    }

    private void b() {
        FadeInCardViewForChildMode fadeInCardViewForChildMode = new FadeInCardViewForChildMode(getContext());
        fadeInCardViewForChildMode.setLayoutParams(((View) this.d).getLayoutParams());
        this.d = fadeInCardViewForChildMode;
        this.c.removeAllViews();
        this.c.addView(fadeInCardViewForChildMode);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
        FadeInCardViewInterface fadeInCardViewInterface = this.d;
        if (fadeInCardViewInterface != null) {
            fadeInCardViewInterface.clear();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.j.release();
        this.j = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        super.onActivate();
        this.m.sendEmptyMessage(1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onDeactivate() {
        super.onDeactivate();
        this.m.removeMessages(1);
    }

    private void a(View view) {
        this.a = (FadeInCardView) view.findViewById(R.id.music_card);
        this.b = (RecyclerView) view.findViewById(R.id.app_list);
        this.c = (CardView) view.findViewById(R.id.cardViewContainer);
        this.f = new BoardFragment.b(getContext());
        this.b.setLayoutManager(new GridLayoutManager(getContext(), 2));
        this.b.setAdapter(this.f);
        this.b.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.main.BoardFragmentForChild.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                if (recyclerView.getChildLayoutPosition(view2) % 2 != 0) {
                    rect.left = DisplayUtils.dip2px(BoardFragmentForChild.this.getContext(), 4.0f);
                } else {
                    rect.left = DisplayUtils.dip2px(BoardFragmentForChild.this.getContext(), 8.0f);
                    rect.left = DisplayUtils.dip2px(BoardFragmentForChild.this.getContext(), 8.0f);
                }
                rect.bottom = DisplayUtils.dip2px(BoardFragmentForChild.this.getContext(), 10.0f);
            }
        });
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$BoardFragmentForChild$8FAbwvwi-TH-btk2PEYoEj45lWI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BoardFragmentForChild.this.b(view2);
            }
        });
        this.d = this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        c();
    }

    private void c() {
        FadeInCardView.FadeInCardItem fadeInCardItem;
        L.launcher.i("on click music card");
        if (CommonUtil.checkHasNetworkAndShowToast(getContext()) && (fadeInCardItem = this.i) != null) {
            fadeInCardItem.handleItemClick(getContext());
        }
    }

    private void d() {
        e();
    }

    private void e() {
        if (ContainerUtil.hasData(this.l)) {
            this.f.a(this.l);
        }
        ApiManager.displayService.getKidMenu().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$BoardFragmentForChild$0E4ZiR38HdoaRkCV62YtYrJyonE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BoardFragmentForChild.this.a((List) obj);
            }
        }, $$Lambda$BoardFragmentForChild$YxbQv35Ff4CCOYIVMrtJhPdUK68.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        this.l = list;
        this.f.a(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        Logger logger = L.launcher;
        logger.e("loadChildModeMenuItems: " + th.getMessage());
    }

    protected void refreshData() {
        FadeInCardView.FadeInCardItem fadeInCardItem;
        this.g = this.j.getRecentPlayList();
        List<FadeInCardView.FadeInCardItem> list = this.g;
        if (list != null && list.size() > 0) {
            FadeInCardViewInterface fadeInCardViewInterface = this.d;
            if (!(fadeInCardViewInterface == null || (fadeInCardItem = this.i) == null)) {
                fadeInCardViewInterface.setIconIv(fadeInCardItem.getTypeResId());
            }
            this.h = 0;
            if (isActivated()) {
                this.m.sendEmptyMessage(1);
            }
        }
    }

    protected void refreshPage() {
        List<FadeInCardView.FadeInCardItem> list = this.g;
        if (list != null && list.size() > 0) {
            L.launcher.i(" start refresh music page");
            this.m.removeMessages(1);
            List<FadeInCardView.FadeInCardItem> list2 = this.g;
            int size = this.h % list2.size();
            this.h = size;
            this.i = list2.get(size);
            this.h++;
            FadeInCardView.FadeInCardItem fadeInCardItem = this.i;
            if (fadeInCardItem != null) {
                FadeInCardViewInterface fadeInCardViewInterface = this.d;
                if (fadeInCardViewInterface != null) {
                    fadeInCardViewInterface.refreshPage(fadeInCardItem.getImgUrl(), this.i.getTitle(), this.i.getCardType(), this.i.getTypeResId(), this.i.getBgResId());
                }
                this.m.sendEmptyMessageDelayed(1, REFRESH_PAGE_TIME_PERIOD);
                return;
            }
            this.m.sendEmptyMessage(1);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRecommendMusicDataChanged(RecommendEvent.MusicDataUpdated musicDataUpdated) {
        if (musicDataUpdated != null) {
            L.launcher.i(" on recommend music data changed");
            refreshData();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRecommendMusicDataChanged(RecommendEvent.ChildRecentPlayUpdated childRecentPlayUpdated) {
        if (childRecentPlayUpdated != null) {
            L.launcher.i(" on ChildRecentPlayUpdated data changed");
            refreshData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecommendMusicDataChanged(ChildModeConfigChangedEvent childModeConfigChangedEvent) {
        L.launcher.i(" on receive ChildModeConfigChangedEvent");
        a();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWifiConnectivityChangedEvent(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        if (wifiConnectivityChangedEvent.connected) {
            this.j.loadRecentPlayList(getContext());
        }
    }
}
