package com.xiaomi.micolauncher.module.homepage.viewholder.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.homepage.view.FadeInCardView;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.model.api.NewsItem;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* loaded from: classes3.dex */
public class NewsShortVideoHomeViewHolder extends BaseHomeViewHolder {
    FadeInCardView a;
    FadeInCardView b;
    ImageView c;
    ImageView d;
    TextView e;
    TextView f;
    TextView g;
    TextView h;
    View i;
    private List<MainPage.News> p;
    private List<VideoItem> q;
    private int r;
    private boolean u;
    private MainPage.News v;
    private MainPage.News w;
    private int[] m = {R.drawable.home_zero, R.drawable.home_one, R.drawable.home_two, R.drawable.home_three, R.drawable.home_four, R.drawable.home_five, R.drawable.home_six, R.drawable.home_seven, R.drawable.home_eight, R.drawable.home_nine};
    private List<VideoItem> n = new ArrayList();
    private List<NewsItem> o = new ArrayList();
    private ValueAnimator.AnimatorUpdateListener x = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.NewsShortVideoHomeViewHolder.1
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            NewsShortVideoHomeViewHolder.this.e.setAlpha(floatValue);
            float f = floatValue - 1.0f;
            NewsShortVideoHomeViewHolder.this.e.setTranslationY(NewsShortVideoHomeViewHolder.this.j * f);
            NewsShortVideoHomeViewHolder.this.f.setAlpha(floatValue);
            NewsShortVideoHomeViewHolder.this.f.setTranslationY(f * NewsShortVideoHomeViewHolder.this.k);
        }
    };
    private Animator.AnimatorListener y = new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.NewsShortVideoHomeViewHolder.2
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            a();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            a();
        }

        private void a() {
            NewsShortVideoHomeViewHolder.this.e.setText("");
            NewsShortVideoHomeViewHolder.this.e.setAlpha(1.0f);
            NewsShortVideoHomeViewHolder.this.e.setTranslationY(0.0f);
            NewsShortVideoHomeViewHolder.this.f.setText("");
            NewsShortVideoHomeViewHolder.this.f.setAlpha(1.0f);
            NewsShortVideoHomeViewHolder.this.f.setTranslationY(0.0f);
        }
    };
    private ValueAnimator.AnimatorUpdateListener z = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.NewsShortVideoHomeViewHolder.3
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            NewsShortVideoHomeViewHolder.this.g.setAlpha(floatValue);
            float f = -floatValue;
            NewsShortVideoHomeViewHolder.this.g.setTranslationY(NewsShortVideoHomeViewHolder.this.l * f);
            NewsShortVideoHomeViewHolder.this.h.setAlpha(floatValue);
            NewsShortVideoHomeViewHolder.this.h.setTranslationY(f * NewsShortVideoHomeViewHolder.this.k);
        }
    };
    private Animator.AnimatorListener A = new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.NewsShortVideoHomeViewHolder.4
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            a();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            a();
        }

        private void a() {
            NewsShortVideoHomeViewHolder.this.e.setText(NewsShortVideoHomeViewHolder.this.v.getTitle());
            NewsShortVideoHomeViewHolder.this.g.setText("");
            NewsShortVideoHomeViewHolder.this.g.setTranslationY(0.0f);
            NewsShortVideoHomeViewHolder.this.f.setText(NewsShortVideoHomeViewHolder.this.w.getTitle());
            NewsShortVideoHomeViewHolder.this.h.setText("");
            NewsShortVideoHomeViewHolder.this.h.setTranslationY(0.0f);
            NewsShortVideoHomeViewHolder.this.u = false;
        }
    };
    private ValueAnimator s = ValueAnimator.ofFloat(1.0f, 0.0f);
    private ValueAnimator t = ValueAnimator.ofFloat(0.0f, 1.0f);
    private int j = this.context.getResources().getDimensionPixelSize(R.dimen.first_title_vertical_move);
    private int l = this.context.getResources().getDimensionPixelSize(R.dimen.first_preview_title_vertical_move);
    private int k = this.context.getResources().getDimensionPixelSize(R.dimen.second_title_vertical_move);

    public NewsShortVideoHomeViewHolder(View view) {
        super(view);
        this.a = (FadeInCardView) view.findViewById(R.id.first_video);
        this.b = (FadeInCardView) view.findViewById(R.id.second_video);
        this.c = (ImageView) view.findViewById(R.id.date_ten_iv);
        this.d = (ImageView) view.findViewById(R.id.date_single_iv);
        this.e = (TextView) view.findViewById(R.id.first_news_title);
        this.f = (TextView) view.findViewById(R.id.second_news_title);
        this.g = (TextView) view.findViewById(R.id.first_news_preview_title);
        this.h = (TextView) view.findViewById(R.id.second_news_preview_title);
        this.i = view.findViewById(R.id.news_view);
        this.s.setDuration(300L);
        this.s.addUpdateListener(this.x);
        this.s.addListener(this.y);
        this.t.setDuration(400L);
        this.t.addUpdateListener(this.z);
        this.t.addListener(this.A);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder, com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void initViewInMain() {
        super.initViewInMain();
        RxViewHelp.debounceClicksWithOneSeconds(this.i).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$NewsShortVideoHomeViewHolder$i0iCwf76bd17bpUxnLESuJLR_H0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                NewsShortVideoHomeViewHolder.this.a(obj);
            }
        });
        this.a.initInMain();
        this.b.initInMain();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        a();
    }

    private void a() {
        if (CommonUtil.checkHasNetworkAndShowToast(this.context)) {
            this.r = getIndex(this.r, this.p.size());
            MainPage.News news = this.p.get(this.r);
            if (news == null) {
                ToastUtil.showToast((int) R.string.error_get_data_failed);
            } else if ("video".equals(news.getType())) {
                if (this.n.size() == 0) {
                    b();
                }
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(this.n);
                L.homepage.d("video type news click index : %d ,items : %s", Integer.valueOf(this.r), arrayList);
                VideoPlayerApi.play(this.context, arrayList, this.r, VideoMode.RECOMMEND);
            } else {
                if (this.o.size() == 0) {
                    b();
                }
                ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(this.o);
                PlayerApi.playNews(this.itemView.getContext(), arrayList2, this.r);
            }
        }
    }

    private void b() {
        this.n.clear();
        this.o.clear();
        for (MainPage.News news : this.p) {
            if ("video".equals(news.getType())) {
                try {
                    this.n.add(new VideoItem(news));
                } catch (Exception e) {
                    L.homepage.e("string conversion to int error : ", e);
                }
            } else {
                this.o.add(new NewsItem(news));
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    protected boolean empty() {
        this.p = RecommendDataManager.getManager().getRecommendNewsList();
        this.q = RecommendDataManager.getManager().getRecommendShortVideos();
        return ContainerUtil.isEmpty(this.p) || ContainerUtil.isEmpty(this.q);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    protected void fillData() {
        L.homepage.i("jiangliang NewsShortVideoHomeViewHolder fillData");
        this.r = 0;
        int i = Calendar.getInstance().get(5);
        this.c.setImageResource(this.m[i / 10]);
        this.d.setImageResource(this.m[i % 10]);
        c();
        d();
        b();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    public void refreshMedia() {
        c();
        super.refreshMedia();
    }

    private void c() {
        if (ContainerUtil.hasData(this.q)) {
            this.currentMediaIndex = getIndex(this.currentMediaIndex, this.q.size());
            this.a.refreshPage(this.q.get(this.currentMediaIndex), R.drawable.homepage_loading_video, this.currentMediaIndex);
            int index = getIndex(this.currentMediaIndex + 1, this.q.size());
            this.b.refreshPage(this.q.get(index), R.drawable.homepage_loading_video, index);
            this.currentMediaIndex += 2;
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    protected void refreshNews() {
        int i = this.r + 1;
        this.r = i;
        this.r = i;
        if (ContainerUtil.hasData(this.p)) {
            this.r = getIndex(this.r, this.p.size());
            this.v = this.p.get(this.r);
            List<MainPage.News> list = this.p;
            this.w = list.get(getIndex(this.r + 1, list.size()));
            if (this.u && this.v != null) {
                this.e.setAlpha(1.0f);
                this.e.setTranslationY(0.0f);
                this.e.setText(this.v.getTitle());
                this.g.setText("");
                this.g.setTranslationY(0.0f);
            }
            if (this.u && this.w != null) {
                this.f.setAlpha(1.0f);
                this.f.setTranslationY(0.0f);
                this.f.setText(this.w.getTitle());
                this.f.setText("");
                this.f.setTranslationY(0.0f);
            }
            this.u = true;
            this.s.start();
            this.g.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$NewsShortVideoHomeViewHolder$gZ9pAjyC1Emo8_F5L10CXiBqmfE
                @Override // java.lang.Runnable
                public final void run() {
                    NewsShortVideoHomeViewHolder.this.e();
                }
            }, 100L);
        }
        sendNewsRefreshMsg();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        this.g.setText(this.v.getTitle());
        this.h.setText(this.w.getTitle());
        this.t.start();
    }

    private void d() {
        L.homepage.i("jiangliang NewsShortVideoHomeViewHolder fillNewsData : %s", this.p);
        if (ContainerUtil.hasData(this.p)) {
            this.r = getIndex(this.r, this.p.size());
            this.v = this.p.get(this.r);
            List<MainPage.News> list = this.p;
            this.w = list.get(getIndex(this.r + 1, list.size()));
            this.e.setText(this.v.getTitle());
            this.f.setText(this.w.getTitle());
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    public void removeRefreshMsg() {
        super.removeRefreshMsg();
        if (this.t.isRunning()) {
            this.t.cancel();
        }
        if (this.s.isRunning()) {
            this.s.cancel();
        }
        this.a.cancelAnimator();
        this.b.cancelAnimator();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    public void sendRefreshMsg() {
        super.sendRefreshMsg();
        this.a.setCancel(false);
        this.b.setCancel(false);
    }
}
