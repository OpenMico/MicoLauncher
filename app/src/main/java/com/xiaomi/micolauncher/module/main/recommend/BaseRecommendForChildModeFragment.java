package com.xiaomi.micolauncher.module.main.recommend;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.module.main.FadeInCardView;
import com.xiaomi.micolauncher.module.main.FadeInCardViewForChildMode;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.main.util.MainStatHelper;
import com.xiaomi.micolauncher.module.main.view.FadeInCardViewInterface;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeRecommendApiHelper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class BaseRecommendForChildModeFragment extends BaseFragment {
    protected static final int MSG_REFRESH_LEFT_CARD = 1;
    protected static final int MSG_REFRESH_RIGHT_CARD = 2;
    public static final int PAGE_NUM_FIRST_RECOMMEND_FRAGMENT = 1;
    public static final int PAGE_NUM_FIRST_SECOND_FRAGMENT = 2;
    public static final int PAGE_NUM_FIRST_THIRD_FRAGMENT = 3;
    protected static final long REFRESH_PAGE_TIME_PERIOD = TimeUnit.SECONDS.toMillis(8);
    FadeInCardViewForChildMode a;
    FadeInCardViewForChildMode b;
    @Nullable
    FadeInCardViewInterface c;
    @Nullable
    FadeInCardViewInterface d;
    private int g;
    private int h;
    private int i;
    @Nullable
    private FadeInCardView.FadeInCardItem j;
    @Nullable
    private FadeInCardView.FadeInCardItem k;
    private List<? extends FadeInCardView.FadeInCardItem> e = new ArrayList();
    private List<? extends FadeInCardView.FadeInCardItem> f = new ArrayList();
    private ChildModeRecommendApiHelper l = ChildModeRecommendApiHelper.getInstance();
    protected Handler handler = new a(this);

    public static BaseRecommendForChildModeFragment newInstance(int i) {
        BaseRecommendForChildModeFragment baseRecommendForChildModeFragment;
        if (i == 1) {
            baseRecommendForChildModeFragment = new FirstRecommendFragment();
        } else if (i == 2) {
            baseRecommendForChildModeFragment = new SecondRecommendFragment();
        } else if (i == 3) {
            baseRecommendForChildModeFragment = new ThirdRecommendFragment();
        } else {
            baseRecommendForChildModeFragment = new BaseRecommendForChildModeFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("PAGE_NUM", i);
        baseRecommendForChildModeFragment.setArguments(bundle);
        return baseRecommendForChildModeFragment;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.i = getArguments().getInt("PAGE_NUM", 1);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_recommend_for_child_mode, viewGroup, false);
        this.a = (FadeInCardViewForChildMode) inflate.findViewById(R.id.leftCard);
        this.b = (FadeInCardViewForChildMode) inflate.findViewById(R.id.rightCard);
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        a();
        refreshData();
        return inflate;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
        FadeInCardViewInterface fadeInCardViewInterface = this.c;
        if (fadeInCardViewInterface != null) {
            fadeInCardViewInterface.clear();
        }
        FadeInCardViewInterface fadeInCardViewInterface2 = this.d;
        if (fadeInCardViewInterface2 != null) {
            fadeInCardViewInterface2.clear();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        super.onActivate();
        this.handler.sendEmptyMessage(1);
        this.handler.sendEmptyMessage(2);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onDeactivate() {
        super.onDeactivate();
        this.handler.removeCallbacksAndMessages(null);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRecommendDataChanged(RecommendEvent.ChildRecommendUpdated childRecommendUpdated) {
        if (childRecommendUpdated != null) {
            L.launcher.i(" on RecommendEvent.DataUpdated");
            refreshData();
        }
    }

    private void a() {
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.main.recommend.-$$Lambda$BaseRecommendForChildModeFragment$4Qdh9DlK_X2xjxOKxEhvlJuE6FA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseRecommendForChildModeFragment.this.b(view);
            }
        });
        this.c = this.a;
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.main.recommend.-$$Lambda$BaseRecommendForChildModeFragment$XmSh46_VqqEKm1b3Mpio9PdTjMc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseRecommendForChildModeFragment.this.a(view);
            }
        });
        this.d = this.b;
    }

    public /* synthetic */ void b(View view) {
        FadeInCardView.FadeInCardItem fadeInCardItem;
        L.launcher.i("on click music card");
        if (CommonUtil.checkHasNetworkAndShowToast(getContext()) && (fadeInCardItem = this.j) != null) {
            fadeInCardItem.handleItemClick(this.a.getContext());
        }
        MainStatHelper.recordCardClick(MainStatHelper.RecommendVal.LAUNCHER_MUSIC_RECOMMEND);
    }

    public /* synthetic */ void a(View view) {
        FadeInCardView.FadeInCardItem fadeInCardItem;
        L.launcher.i("on click music card");
        if (CommonUtil.checkHasNetworkAndShowToast(getContext()) && (fadeInCardItem = this.k) != null) {
            fadeInCardItem.handleItemClick(this.b.getContext());
        }
        MainStatHelper.recordCardClick(MainStatHelper.RecommendVal.LAUNCHER_MUSIC_RECOMMEND);
    }

    protected void refreshData() {
        FadeInCardView.FadeInCardItem fadeInCardItem;
        FadeInCardView.FadeInCardItem fadeInCardItem2;
        ChildModeRecommendApiHelper.RecommendList recommend = this.l.getRecommend(this.i);
        if (recommend != null) {
            this.e = recommend.leftList;
            this.f = recommend.rightList;
        }
        if (!ContainerUtil.isEmpty(this.e)) {
            FadeInCardViewInterface fadeInCardViewInterface = this.c;
            if (!(fadeInCardViewInterface == null || (fadeInCardItem2 = this.j) == null)) {
                fadeInCardViewInterface.setIconIv(fadeInCardItem2.getTypeResId());
            }
            this.g = 0;
            if (isActivated()) {
                this.handler.removeMessages(1);
                this.handler.sendEmptyMessage(1);
            }
        }
        if (this.f.size() != 0) {
            FadeInCardViewInterface fadeInCardViewInterface2 = this.d;
            if (!(fadeInCardViewInterface2 == null || (fadeInCardItem = this.k) == null)) {
                fadeInCardViewInterface2.setIconIv(fadeInCardItem.getTypeResId());
            }
            this.h = 0;
            if (isActivated()) {
                this.handler.removeMessages(2);
                this.handler.sendEmptyMessage(2);
            }
        }
    }

    protected void refreshLeftCard() {
        L.childmode.i(String.format(Locale.getDefault(), "start refreshLeftCard, pageNumInViewPager = %d", Integer.valueOf(this.i)));
        if (!ContainerUtil.isEmpty(this.e)) {
            this.handler.removeMessages(1);
            List<? extends FadeInCardView.FadeInCardItem> list = this.e;
            int size = this.g % list.size();
            this.g = size;
            this.j = (FadeInCardView.FadeInCardItem) list.get(size);
            this.g++;
            FadeInCardView.FadeInCardItem fadeInCardItem = this.j;
            if (fadeInCardItem != null) {
                FadeInCardViewInterface fadeInCardViewInterface = this.c;
                if (fadeInCardViewInterface != null) {
                    fadeInCardViewInterface.refreshPage(fadeInCardItem.getImgUrl(), this.j.getTitle(), this.j.getCardType(), this.j.getTypeResId(), this.j.getBgResId());
                }
                this.handler.sendEmptyMessageDelayed(1, REFRESH_PAGE_TIME_PERIOD);
                return;
            }
            this.handler.sendEmptyMessage(1);
        }
    }

    protected void refreshRightCard() {
        if (!ContainerUtil.isEmpty(this.f)) {
            this.handler.removeMessages(2);
            List<? extends FadeInCardView.FadeInCardItem> list = this.f;
            int size = this.h % list.size();
            this.h = size;
            this.k = (FadeInCardView.FadeInCardItem) list.get(size);
            this.h++;
            FadeInCardView.FadeInCardItem fadeInCardItem = this.k;
            if (fadeInCardItem != null) {
                FadeInCardViewInterface fadeInCardViewInterface = this.d;
                if (fadeInCardViewInterface != null) {
                    fadeInCardViewInterface.refreshPage(fadeInCardItem.getImgUrl(), this.k.getTitle(), this.k.getCardType(), this.k.getTypeResId(), this.k.getBgResId());
                }
                this.handler.sendEmptyMessageDelayed(2, REFRESH_PAGE_TIME_PERIOD);
                return;
            }
            this.handler.sendEmptyMessage(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends Handler {
        private final WeakReference<BaseRecommendForChildModeFragment> a;

        a(BaseRecommendForChildModeFragment baseRecommendForChildModeFragment) {
            this.a = new WeakReference<>(baseRecommendForChildModeFragment);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            BaseRecommendForChildModeFragment baseRecommendForChildModeFragment = this.a.get();
            if (baseRecommendForChildModeFragment != null) {
                if (message.what == 1) {
                    L.launcher.i(" refresh page");
                    baseRecommendForChildModeFragment.refreshLeftCard();
                } else if (message.what == 2) {
                    L.launcher.i(" refresh page");
                    baseRecommendForChildModeFragment.refreshRightCard();
                }
            }
        }
    }
}
