package com.xiaomi.micolauncher.module.homepage.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.transformation.MicoTransformUtils;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.homepage.HomePageUtils;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import com.xiaomi.micolauncher.module.homepage.viewholder.home.PlayHelper;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.view.QQMusicAuthInvalidActivity;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class FadeInCardView extends BaseViewGroup {
    private Context a;
    private ImageView b;
    private ImageView c;
    private ImageView d;
    private TextView e;
    private int f;
    private boolean g;
    private CompositeDisposable h;
    private Disposable i;
    private boolean j;
    private boolean k;
    private int l;
    private RoundedCorners m;
    private int n;
    private PatchWall.Item o;
    private MainPage.Music p;
    private VideoItem q;
    private IListItem r;
    private MainPage.LongVideo s;
    private volatile boolean t;
    private int u;

    public FadeInCardView(@NonNull Context context) {
        this(context, null);
    }

    public FadeInCardView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FadeInCardView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.g = true;
        this.t = true;
        this.a = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FadeInCardView);
        View inflate = View.inflate(context, obtainStyledAttributes.getResourceId(0, R.layout.fade_in_video_item), this);
        this.b = (ImageView) inflate.findViewById(R.id.cover_img);
        this.e = (TextView) inflate.findViewById(R.id.title_tv);
        this.c = (ImageView) inflate.findViewById(R.id.mv_img);
        this.d = (ImageView) inflate.findViewById(R.id.tv_bg);
        try {
            this.l = obtainStyledAttributes.getDimensionPixelOffset(2, UiUtils.getCornerRadius());
        } catch (Exception e) {
            L.base.w(e);
            this.l = UiUtils.getCornerRadius();
        }
        this.n = obtainStyledAttributes.getResourceId(1, R.dimen.small_card_title_height);
        this.m = new RoundedCorners(this.l);
        obtainStyledAttributes.recycle();
        this.h = new CompositeDisposable();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.view.BaseViewGroup
    protected boolean needClipRound() {
        return this.t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        a();
    }

    @SuppressLint({"CheckResult"})
    public void initInMain() {
        RxViewHelp.debounceClicksWithOneSeconds(this).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$FadeInCardView$f0qozUsvVcyc949ABNwl6lcPpjQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FadeInCardView.this.a(obj);
            }
        });
    }

    private void a() {
        if (CommonUtil.checkHasNetworkAndShowToast(this.a)) {
            if (this.p != null) {
                SchemaManager.handleSchema(getContext(), this.p.getAction());
            }
            if (this.o != null) {
                if (PreferenceUtils.getSettingBoolean(this.a, HomePageUtils.QQ_MUSIC_BIND, true)) {
                    MusicHelper.playMV(this.a, this.o);
                } else {
                    Intent intent = new Intent(getContext(), QQMusicAuthInvalidActivity.class);
                    intent.addFlags(536870912);
                    ActivityLifeCycleManager.startActivityQuietly(getContext(), intent);
                }
            }
            if (this.q != null) {
                VideoPlayerApi.play(this.a, RecommendDataManager.getManager().getRecommendShortVideos(), RecommendDataManager.getManager().getRecommendShortVideoIndex(this.q), VideoMode.SHORT_VIDEO_RECOMMEND);
                HomePageRecordHelper.recordShortVideoClick(this.q, "B", this.u);
            }
            MainPage.LongVideo longVideo = this.s;
            if (longVideo != null) {
                PlayHelper.play(this.a, longVideo);
                RecommendDataManager.getManager().updateRecentPlayVideo(this.s);
                HomePageRecordHelper.recordLongVideoClick(this.s, "A", this.u);
            }
        }
    }

    public void refreshPage(MainPage.Music music, int i) {
        this.f = i;
        this.p = music;
        this.r = null;
        this.o = null;
        this.q = null;
        this.t = false;
        refreshPage(music.getImgUrl(), music.getTitle(), R.dimen.music_iv_width, R.dimen.music_iv_height);
    }

    public void refreshPage(IListItem iListItem, int i) {
        this.f = i;
        this.p = null;
        this.r = iListItem;
        this.o = null;
        this.q = null;
        this.t = !TextUtils.isEmpty(iListItem.getItemTitle());
        refreshPage(iListItem.getItemImageUrl(), iListItem.getItemTitle(), R.dimen.home_kids_block_banner_width, R.dimen.home_kids_block_banner_height);
    }

    public void musicReplaceMv(MainPage.Music music, int i) {
        this.f = i;
        this.p = music;
        this.r = null;
        this.o = null;
        this.q = null;
        L.launcher.i("FadeInCardView musicReplaceMv");
        refreshPage(music.getImgUrl(), music.getTitle(), R.dimen.small_card_width, R.dimen.small_card_height);
    }

    public void refreshPage(PatchWall.Item item, int i) {
        this.f = i;
        this.o = item;
        this.r = null;
        this.p = null;
        this.q = null;
        refreshPage(item.images.poster.url, item.title, R.dimen.small_card_width, R.dimen.small_card_height);
    }

    public void refreshPage(VideoItem videoItem, int i, int i2) {
        this.u = i2;
        this.f = i;
        HomePageRecordHelper.recordShortVideoShow(videoItem, "B", i2);
        a(videoItem);
    }

    public void refreshPage(MainPage.LongVideo longVideo, int i, int i2) {
        this.f = i;
        this.u = i2;
        HomePageRecordHelper.recordLongVideoShow(longVideo, "A", i2);
        a(longVideo);
    }

    private void a(MainPage.LongVideo longVideo) {
        this.s = longVideo;
        this.o = null;
        this.p = null;
        this.q = null;
        refreshPage(longVideo.getImageURL(), longVideo.getTitle(), R.dimen.short_banner_width, R.dimen.short_banner_height);
    }

    private void a(VideoItem videoItem) {
        this.q = videoItem;
        this.p = null;
        this.o = null;
        refreshPage(videoItem.getImageUrl(), videoItem.getTitle(), R.dimen.small_card_width, R.dimen.small_card_height);
    }

    public void refreshPage(String str, String str2, int i, int i2) {
        if (!this.j) {
            this.c.setVisibility(this.o != null ? 0 : 8);
            this.e.setText(str2);
            GlideUtils.bindImageView(this.a, str, this.b, this.t ? MicoTransformUtils.getTransformationWithBlurCorner(UiUtils.getSize(this.a, this.n), this.l) : this.m, this.f);
        }
    }

    public void setCancel(boolean z) {
        this.j = z;
    }

    public void cancelAnimator() {
        Disposable disposable;
        setCancel(true);
        CompositeDisposable compositeDisposable = this.h;
        if (compositeDisposable != null && (disposable = this.i) != null) {
            compositeDisposable.remove(disposable);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        L.homepage.d("FadeInCardView onDetachedFromWindow");
        this.k = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        L.homepage.d("FadeInCardView onAttachedToWindow");
        this.k = false;
    }
}
