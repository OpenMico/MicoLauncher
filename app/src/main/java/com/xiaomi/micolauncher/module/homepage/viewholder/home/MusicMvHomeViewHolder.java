package com.xiaomi.micolauncher.module.homepage.viewholder.home;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.homepage.HomePageUtils;
import com.xiaomi.micolauncher.module.homepage.view.FadeInCardView;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicMvHomeViewHolder extends BaseHomeViewHolder {
    FadeInCardView a;
    FadeInCardView b;
    FadeInCardView c;
    FadeInCardView d;
    FadeInCardView e;
    ViewStub f;
    ViewStub g;
    private View h;
    private TextView i;
    private List<MainPage.Music> j;
    private List<PatchWall.Item> k;
    private boolean l;
    private boolean m;
    private int n;

    public MusicMvHomeViewHolder(View view) {
        super(view);
        this.a = (FadeInCardView) view.findViewById(R.id.first_mv);
        this.b = (FadeInCardView) view.findViewById(R.id.second_mv);
        this.c = (FadeInCardView) view.findViewById(R.id.first_music);
        this.d = (FadeInCardView) view.findViewById(R.id.second_music);
        this.e = (FadeInCardView) view.findViewById(R.id.third_music);
        this.f = (ViewStub) view.findViewById(R.id.auth_status);
        this.g = (ViewStub) view.findViewById(R.id.empty_layout);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder, com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void initViewInMain() {
        super.initViewInMain();
        this.f.setOnInflateListener(new ViewStub.OnInflateListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$MusicMvHomeViewHolder$8HgbKw2BTCapmZkHUldNwZtEHzk
            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub, View view) {
                MusicMvHomeViewHolder.this.b(viewStub, view);
            }
        });
        this.g.setOnInflateListener(new ViewStub.OnInflateListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$MusicMvHomeViewHolder$skDZTDJs2NxnDk-IlplmTYm2-J4
            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub, View view) {
                MusicMvHomeViewHolder.this.a(viewStub, view);
            }
        });
        this.a.initInMain();
        this.b.initInMain();
        this.c.initInMain();
        this.d.initInMain();
        this.e.initInMain();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(ViewStub viewStub, View view) {
        this.l = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ViewStub viewStub, View view) {
        this.m = true;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    protected boolean empty() {
        this.j = RecommendDataManager.getManager().getRecommendMusicList();
        return ContainerUtil.isEmpty(this.j) && ContainerUtil.isEmpty(this.k);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    public void setData() {
        this.k = RecommendDataManager.getManager().getRecommendMusicMvs();
        L.homepage.d("music mvs is : %s,qq source :%b,empty : %b,qq_music_bind : %b", this.k, Boolean.valueOf(HomePageUtils.isSourceQQ()), Boolean.valueOf(empty()), Boolean.valueOf(PreferenceUtils.getSettingBoolean(this.context, HomePageUtils.QQ_MUSIC_BIND, true)));
        if (PreferenceUtils.getSettingBoolean(this.context, HomePageUtils.QQ_MUSIC_BIND, true) || !HomePageUtils.isSourceQQ() || !empty()) {
            if (this.l) {
                this.f.setVisibility(8);
            }
            this.currentMediaIndex = 0;
            if (!empty()) {
                this.g.setVisibility(8);
                fillData();
                sendRefreshMsg();
            } else if (this.m) {
                this.g.setVisibility(0);
            } else {
                this.g.inflate();
            }
        } else {
            a();
            this.i.setText(R.string.qq_music_auth_unbind);
            this.h.setBackgroundResource(R.drawable.home_notlogin);
        }
    }

    private void a() {
        if (this.l) {
            this.f.setVisibility(0);
            return;
        }
        this.h = this.f.inflate();
        this.i = (TextView) this.h.findViewById(R.id.qq_unbind_tv);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    protected void fillData() {
        this.n = 0;
        this.currentMediaIndex = 0;
        if (ContainerUtil.hasData(this.j)) {
            c();
        }
        if (!"QQ".equals(RecommendDataManager.getManager().getMusicResource())) {
            this.a.musicReplaceMv(this.j.get(this.n + 3), R.drawable.homepage_loading_video);
            this.b.musicReplaceMv(this.j.get(this.n + 4), R.drawable.homepage_loading_video);
        } else if (ContainerUtil.hasData(this.k)) {
            b();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    public void refreshMedia() {
        if ("QQ".equals(RecommendDataManager.getManager().getMusicResource()) && PreferenceUtils.getSettingBoolean(this.context, HomePageUtils.QQ_MUSIC_BIND, true)) {
            c();
            b();
            super.refreshMedia();
        }
    }

    private void b() {
        if (ContainerUtil.hasData(this.k)) {
            this.currentMediaIndex = getIndex(this.currentMediaIndex, this.k.size());
            this.a.refreshPage(this.k.get(this.currentMediaIndex), R.drawable.homepage_loading_video);
            this.b.refreshPage(this.k.get(getIndex(this.currentMediaIndex + 1, this.k.size())), R.drawable.homepage_loading_video);
            this.currentMediaIndex += 2;
        }
    }

    private void c() {
        if (ContainerUtil.hasData(this.j)) {
            this.n = getIndex(this.n, this.j.size());
            this.c.refreshPage(this.j.get(this.n), R.drawable.homepage_loading_music);
            FadeInCardView fadeInCardView = this.d;
            List<MainPage.Music> list = this.j;
            fadeInCardView.refreshPage(list.get(getIndex(this.n + 1, list.size())), R.drawable.homepage_loading_music);
            FadeInCardView fadeInCardView2 = this.e;
            List<MainPage.Music> list2 = this.j;
            fadeInCardView2.refreshPage(list2.get(getIndex(this.n + 2, list2.size())), R.drawable.homepage_loading_music);
            this.n += 3;
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    public void removeRefreshMsg() {
        super.removeRefreshMsg();
        this.a.cancelAnimator();
        this.b.cancelAnimator();
        this.c.cancelAnimator();
        this.d.cancelAnimator();
        this.e.cancelAnimator();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    public void sendRefreshMsg() {
        super.sendRefreshMsg();
        this.a.setCancel(false);
        this.b.setCancel(false);
        this.c.setCancel(false);
        this.d.setCancel(false);
        this.e.setCancel(false);
    }
}
