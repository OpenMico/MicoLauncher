package com.xiaomi.micolauncher.module.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.homepage.SkillPath;
import com.xiaomi.micolauncher.module.main.BoardFragment;
import com.xiaomi.micolauncher.module.main.FadeInCardView;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.main.util.MainStatHelper;
import com.xiaomi.micolauncher.module.main.view.FadeInCardViewInterface;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeRecentPlayHelper;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeRefreshHelper;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.ChildModeConfigChangedEvent;
import com.xiaomi.micolauncher.skills.music.view.QQMusicAuthInvalidActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class BoardFragment extends BaseFragment {
    protected static final int MSG_REFRESH_PAGE = 1;
    protected static final long REFRESH_PAGE_TIME_PERIOD = TimeUnit.SECONDS.toMillis(8);
    FadeInCardView a;
    RecyclerView b;
    CardView c;
    @Nullable
    FadeInCardViewInterface d;
    private View e;
    private b f;
    private List<FadeInCardView.FadeInCardItem> g;
    private int h;
    @Nullable
    private FadeInCardView.FadeInCardItem i;
    private ChildModeRefreshHelper k;
    private Handler m;
    private ChildModeRecentPlayHelper j = new ChildModeRecentPlayHelper(getContext());
    private List<MainPage.KidMenuItem> l = new ArrayList();
    private Handler.Callback n = new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.main.BoardFragment.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            BoardFragment.this.refreshPage();
            return false;
        }
    };

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.k = new ChildModeRefreshHelper();
        this.j.loadRecentPlayList(getContext());
        this.m = new WeakRefHandler(this.n);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (this.e == null) {
            this.e = layoutInflater.inflate(R.layout.fragment_board, viewGroup, false);
            a(this.e);
        }
        this.a = (FadeInCardView) this.e.findViewById(R.id.music_card);
        this.b = (RecyclerView) this.e.findViewById(R.id.app_list);
        this.c = (CardView) this.e.findViewById(R.id.cardViewContainer);
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
        View view;
        if (ChildModeManager.getManager().isChildMode()) {
            view = new FadeInCardViewForChildMode(getContext());
        } else {
            view = new FadeInCardView(getContext());
        }
        view.setLayoutParams(((View) this.d).getLayoutParams());
        this.d = (FadeInCardViewInterface) view;
        this.c.removeAllViews();
        this.c.addView(view);
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
        this.f = new b(getContext());
        this.b.setLayoutManager(new GridLayoutManager(getContext(), 2));
        this.b.setAdapter(this.f);
        this.b.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.main.BoardFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                if (recyclerView.getChildLayoutPosition(view2) % 2 != 0) {
                    rect.left = DisplayUtils.dip2px(BoardFragment.this.getContext(), 4.0f);
                } else {
                    rect.left = DisplayUtils.dip2px(BoardFragment.this.getContext(), 8.0f);
                    rect.left = DisplayUtils.dip2px(BoardFragment.this.getContext(), 8.0f);
                }
                rect.bottom = DisplayUtils.dip2px(BoardFragment.this.getContext(), 10.0f);
            }
        });
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$BoardFragment$602Vd55VjmnKj3AXVAatoOO0nEY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BoardFragment.this.b(view2);
            }
        });
        this.d = this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        c();
    }

    private void c() {
        L.launcher.i("on click music card");
        if (CommonUtil.checkHasNetworkAndShowToast(getContext()) && this.i != null) {
            if (ChildModeManager.getManager().isChildMode()) {
                this.i.handleItemClick(getContext());
            } else if (!TextUtils.isEmpty(this.i.getCardAction())) {
                SchemaManager.handleSchema(getContext(), this.i.getCardAction());
            } else {
                f();
            }
            MainStatHelper.recordCardClick(MainStatHelper.RecommendVal.LAUNCHER_MUSIC_RECOMMEND);
        }
    }

    private void d() {
        if (ChildModeManager.getManager().isChildMode()) {
            e();
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new a(R.drawable.icon_app_miot, SkillPath.MIOT, false, "mijia"));
        arrayList.add(new a(R.drawable.icon_app_music, "mico://launcher/music", true, "music"));
        arrayList.add(new a(R.drawable.icon_app_book, "mico://launcher/audiobooks", true, "audiobooks"));
        arrayList.add(new a(R.drawable.icon_app_video, "mico://launcher/video", true, "video"));
        arrayList.add(new a(R.drawable.icon_app_alarm, SkillPath.ALARM, false, "alarm"));
        arrayList.add(new a(R.drawable.icon_app_skills, "mico://launcher/skill", false, "skill"));
        this.f.a(arrayList);
    }

    private void e() {
        if (ContainerUtil.hasData(this.l)) {
            this.f.a(this.l);
        }
        ApiManager.displayService.getKidMenu().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$BoardFragment$GUUUwOqQQmM965v7EijdaXy_b30
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BoardFragment.this.a((List) obj);
            }
        }, $$Lambda$BoardFragment$2LSDtjPRkPlNOJ7WinRLd_ke6JY.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        Logger logger = L.launcher;
        logger.i("loadChildModeMenuItems: " + ContainerUtil.getSize(list));
        this.l = list;
        this.f.a(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Throwable th) throws Exception {
        Logger logger = L.launcher;
        logger.e("loadChildModeMenuItems: " + th.getMessage());
    }

    private void f() {
        ApiManager.userProfileService.getMusicSource().flatMap($$Lambda$BoardFragment$WiMiqc83kKvVlbbdWYUgHeMK79w.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$BoardFragment$a9pSHtOQP-jkLCewsES4Y45sLUs
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BoardFragment.this.a((MIBrain.CPBindStatus) obj);
            }
        }, $$Lambda$BoardFragment$cG7uWHnAUT3wBCaqhlJSsieGs.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(String str) throws Exception {
        if (SystemSetting.MusicSuorce.QQ.getSource().equals(str)) {
            return ApiManager.minaService.qqMusicAuthStatus("");
        }
        return Observable.error(new Throwable("music source is not qq"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MIBrain.CPBindStatus cPBindStatus) throws Exception {
        if (!(cPBindStatus.isExpired() || cPBindStatus.isNotBinded()) || getContext() == null) {
            ToastUtil.showToast((int) R.string.error_get_data_failed);
            return;
        }
        Intent intent = new Intent(getContext(), QQMusicAuthInvalidActivity.class);
        intent.addFlags(536870912);
        ActivityLifeCycleManager.startActivityQuietly(getContext(), intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.launcher.e(th);
        ToastUtil.showToast((int) R.string.error_get_data_failed);
    }

    protected void refreshData() {
        FadeInCardView.FadeInCardItem fadeInCardItem;
        if (ChildModeManager.getManager().isChildMode()) {
            this.g = this.j.getRecentPlayList();
        } else {
            this.g = RecommendDataManager.getManager().getRecommendMusicList();
        }
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
            d();
            this.j.loadRecentPlayList(getContext());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a {
        private int b;
        private String c;
        private boolean d;
        private String e;

        a(int i, String str, boolean z, String str2) {
            this.b = i;
            this.c = str;
            this.d = z;
            this.e = str2;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class b extends RecyclerView.Adapter<c> {
        private Context a;
        private List<Object> b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(Context context) {
            this.a = context;
        }

        public void a(List list) {
            this.b = list;
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            List<Object> list = this.b;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        @NonNull
        /* renamed from: a */
        public c onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new c(LayoutInflater.from(this.a).inflate(R.layout.view_app_item, viewGroup, false));
        }

        /* renamed from: a */
        public void onBindViewHolder(@NonNull c cVar, int i) {
            Object obj = this.b.get(i);
            final String str = "";
            final String str2 = "";
            final boolean z = false;
            if (obj instanceof a) {
                a aVar = (a) obj;
                GlideUtils.bindImageView(this.a, aVar.b, cVar.a);
                str = aVar.c;
                z = aVar.d;
                str2 = aVar.e;
            } else if (obj instanceof MainPage.KidMenuItem) {
                MainPage.KidMenuItem kidMenuItem = (MainPage.KidMenuItem) obj;
                Glide.with(this.a).load(kidMenuItem.iconUrl).apply((BaseRequestOptions<?>) GlideUtils.getDefaultRequestOptions().placeholder(R.drawable.music_card_view_default).error(R.drawable.music_card_view_default).transforms(new CenterCrop(), new RoundedCorners(DisplayUtils.dip2px(this.a, 16.0f)))).into(cVar.a);
                str = kidMenuItem.actionUrl;
                z = kidMenuItem.needNetwork;
                str2 = kidMenuItem.statName;
            }
            cVar.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$BoardFragment$b$Pm251XVCXWuRwnyKllJkrSPeuP0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BoardFragment.b.this.a(z, str, str2, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(boolean z, String str, String str2, View view) {
            if (!z || CommonUtil.checkHasNetworkAndShowToast(this.a)) {
                SchemaManager.handleSchema(this.a, str);
            }
            MainStatHelper.recordAppClick(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class c extends RecyclerView.ViewHolder {
        private ImageView a;

        public c(View view) {
            super(view);
            this.a = (ImageView) view.findViewById(R.id.icon_iv);
        }
    }
}
