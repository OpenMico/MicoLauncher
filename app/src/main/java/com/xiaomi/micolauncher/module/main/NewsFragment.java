package com.xiaomi.micolauncher.module.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.main.util.MainStatHelper;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.model.api.NewsItem;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class NewsFragment extends BaseFragment {
    private static final long g = TimeUnit.SECONDS.toMillis(8);
    private static final long h = TimeUnit.SECONDS.toMillis(4);
    FadeInCardView a;
    CardView b;
    ImageView c;
    TextView d;
    TextView e;
    TextView f;
    private List<MainPage.News> i;
    private int l;
    private MainPage.News m;
    private List<MainPage.Station> n;
    private int o;
    private MainPage.Station p;
    private ValueAnimator q;
    private ValueAnimator r;
    private boolean s;
    private String t;
    private Handler u;
    private ArrayList<VideoItem> j = new ArrayList<>();
    private ArrayList<NewsItem> k = new ArrayList<>();
    private Handler.Callback v = new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.main.NewsFragment.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                if (NewsFragment.this.isActivated()) {
                    L.launcher.i(" refresh station page");
                    NewsFragment.this.refreshStationPage();
                    return false;
                }
                L.launcher.i(" stop refresh station page, not activated");
                return false;
            } else if (message.what == 2) {
                if (NewsFragment.this.isActivated()) {
                    L.launcher.i(" refresh news page");
                    NewsFragment.this.d();
                    return false;
                }
                L.launcher.i(" stop refresh news page, not activated");
                return false;
            } else if (message.what != 3) {
                return false;
            } else {
                L.launcher.i(" start fade in");
                if (NewsFragment.this.m == null || NewsFragment.this.r == null) {
                    return false;
                }
                NewsFragment.this.f.setText(NewsFragment.this.t);
                NewsFragment.this.r.start();
                return false;
            }
        }
    };
    private ValueAnimator.AnimatorUpdateListener w = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.main.NewsFragment.2
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            NewsFragment.this.d.setAlpha(floatValue);
            NewsFragment.this.d.setTranslationY((floatValue - 1.0f) * DisplayUtils.dip2px(NewsFragment.this.getContext(), 70.0f));
        }
    };
    private Animator.AnimatorListener x = new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.main.NewsFragment.3
        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            NewsFragment.this.d.setText("");
            NewsFragment.this.d.setAlpha(1.0f);
            NewsFragment.this.d.setTranslationY(0.0f);
        }
    };
    private ValueAnimator.AnimatorUpdateListener y = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.main.NewsFragment.4
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            NewsFragment.this.f.setAlpha(floatValue);
            NewsFragment.this.f.setTranslationY((-floatValue) * DisplayUtils.dip2px(NewsFragment.this.getContext(), 56.0f));
        }
    };
    private Animator.AnimatorListener z = new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.main.NewsFragment.5
        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            NewsFragment.this.d.setText(NewsFragment.this.t);
            NewsFragment.this.f.setText("");
            NewsFragment.this.f.setTranslationY(0.0f);
            NewsFragment.this.s = false;
        }
    };

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_news, viewGroup, false);
        this.a = (FadeInCardView) inflate.findViewById(R.id.station_card);
        this.b = (CardView) inflate.findViewById(R.id.news_card);
        this.c = (ImageView) inflate.findViewById(R.id.news_iv);
        this.d = (TextView) inflate.findViewById(R.id.news_title);
        this.e = (TextView) inflate.findViewById(R.id.news_type_tv);
        this.f = (TextView) inflate.findViewById(R.id.preview_news_title);
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        this.u = new WeakRefHandler(this.v);
        a();
        b();
        refreshStationData();
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        super.onActivate();
        d();
        refreshStationPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onDeactivate() {
        super.onDeactivate();
        this.u.removeMessages(1);
        this.u.removeMessages(2);
    }

    private void a() {
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$NewsFragment$nCmWj0YJYYQLk_1aD9tv1U7bWMA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NewsFragment.this.b(view);
            }
        });
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$NewsFragment$EFDo257lgELlHF1336nFfqV1pGo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NewsFragment.this.a(view);
            }
        });
        this.q = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.q.setDuration(400L);
        this.q.addUpdateListener(this.w);
        this.q.addListener(this.x);
        this.r = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.r.setDuration(400L);
        this.r.addUpdateListener(this.y);
        this.r.addListener(this.z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        L.launcher.i("on click station card");
        if (CommonUtil.checkHasNetworkAndShowToast(getContext())) {
            MainPage.Station station = this.p;
            if (station == null || TextUtils.isEmpty(station.getAction())) {
                ToastUtil.showToast((int) R.string.error_get_data_failed);
            } else {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.p.getAction())));
            }
        }
        MainStatHelper.recordCardClick(MainStatHelper.RecommendVal.LAUNCHER_RADIO_RECOMMEND);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        L.launcher.i("on click news card");
        if (CommonUtil.checkHasNetworkAndShowToast(getContext())) {
            if (this.m != null) {
                int size = (this.l - 1) % this.i.size();
                if (size >= 0 && size <= this.i.size()) {
                    if ("video".equals(this.m.getType())) {
                        ArrayList<VideoItem> arrayList = this.j;
                        if (arrayList == null || size > arrayList.size()) {
                            c();
                        }
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.addAll(this.j);
                        VideoPlayerApi.play(getContext(), arrayList2, size, VideoMode.RECOMMEND);
                    } else {
                        ArrayList<NewsItem> arrayList3 = this.k;
                        if (arrayList3 == null || size > arrayList3.size()) {
                            c();
                        }
                        ArrayList arrayList4 = new ArrayList();
                        arrayList4.addAll(this.k);
                        PlayerApi.playNews(getContext(), arrayList4, size);
                    }
                } else {
                    return;
                }
            } else {
                ToastUtil.showToast((int) R.string.error_get_data_failed);
            }
        }
        MainStatHelper.recordCardClick(MainStatHelper.RecommendVal.LAUNCHER_NEWS_RECOMMEND);
    }

    protected void refreshStationData() {
        this.n = RecommendDataManager.getManager().getRecommendStationList();
        List<MainPage.Station> list = this.n;
        if (list != null && list.size() > 0) {
            this.a.setIconIv(R.drawable.icon_station);
            this.o = 0;
            refreshStationPage();
        }
    }

    protected void refreshStationPage() {
        this.u.removeMessages(1);
        List<MainPage.Station> list = this.n;
        if (list != null && list.size() > 0) {
            L.launcher.i(" start refresh station page");
            List<MainPage.Station> list2 = this.n;
            int size = this.o % list2.size();
            this.o = size;
            this.p = list2.get(size);
            this.o++;
            MainPage.Station station = this.p;
            if (station != null) {
                this.a.refreshPage(station.getBackgroundImage().getSmall(), this.p.getTitle(), this.p.getTag(), 0, 0);
                this.u.sendEmptyMessageDelayed(1, g);
                return;
            }
            this.u.sendEmptyMessage(1);
        }
    }

    private void b() {
        this.i = RecommendDataManager.getManager().getRecommendNewsList();
        List<MainPage.News> list = this.i;
        if (list != null && list.size() > 0) {
            L.launcher.i(" refresh news data");
            this.l = 0;
            c();
            d();
        }
    }

    private void c() {
        this.j.clear();
        this.k.clear();
        for (MainPage.News news : this.i) {
            if ("video".equals(news.getType())) {
                this.j.add(new VideoItem(news));
            } else {
                this.k.add(new NewsItem(news));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        this.u.removeMessages(2);
        List<MainPage.News> list = this.i;
        if (list != null && list.size() > 0) {
            Logger logger = L.launcher;
            logger.i(" start refresh news page " + this.l);
            this.l = this.l % this.i.size();
            this.m = this.i.get(this.l);
            this.l = this.l + 1;
            if (this.m != null) {
                this.u.sendEmptyMessageDelayed(2, h);
                if (this.s && this.m != null) {
                    this.d.setAlpha(1.0f);
                    this.d.setTranslationY(0.0f);
                    this.d.setText(this.t);
                    this.f.setText("");
                    this.f.setTranslationY(0.0f);
                }
                this.s = true;
                this.t = this.m.getTitle();
                this.q.start();
                this.u.sendEmptyMessageDelayed(3, 100L);
                return;
            }
            this.u.sendEmptyMessage(2);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecommendNewsDataChanged(RecommendEvent.NewsDataUpdated newsDataUpdated) {
        if (newsDataUpdated != null) {
            L.launcher.i(" on recommend news data changed");
            b();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecommendStationDataChanged(RecommendEvent.StationDataUpdated stationDataUpdated) {
        if (stationDataUpdated != null) {
            L.launcher.i(" on recommend station data changed");
            refreshStationData();
        }
    }
}
