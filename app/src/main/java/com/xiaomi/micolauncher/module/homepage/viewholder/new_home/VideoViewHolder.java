package com.xiaomi.micolauncher.module.homepage.viewholder.new_home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.request.RequestOptions;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.Picture;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.TraceUtil;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.child.childvideo.MiTvVipActivity;
import com.xiaomi.micolauncher.module.homepage.activity.SimpleWebActivity;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import com.xiaomi.micolauncher.module.homepage.manager.HomeVideoDataManager;
import com.xiaomi.micolauncher.module.homepage.record.HomeVideoRecordHelper;
import com.xiaomi.micolauncher.module.homepage.view.StrokeTextView;
import com.xiaomi.micolauncher.module.homepage.viewholder.home.PlayHelper;
import com.xiaomi.micolauncher.module.lockscreen.utils.AdHelper;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoViewHolder extends BaseViewHolder {
    static final /* synthetic */ boolean a = !VideoViewHolder.class.desiredAssertionStatus();
    private static RequestOptions b;
    private final ImageView c;
    private final ImageView d;
    private final ImageView e;
    private final ImageView f;
    private final TextView g;
    private final StrokeTextView h;
    private final TextView i;
    private final TextView j;
    private final View k;
    private final View l;
    private final TextView m;
    private int n;
    private int o;
    private String p;
    private String q;
    private String r;
    private VideoData s;
    private final Context t;

    @SuppressLint({"CheckResult"})
    public VideoViewHolder(View view) {
        super(view);
        this.t = view.getContext();
        this.c = (ImageView) view.findViewById(R.id.table_video_cover_img);
        this.d = (ImageView) view.findViewById(R.id.video_vip_img);
        this.e = (ImageView) view.findViewById(R.id.cp_img);
        this.i = (TextView) view.findViewById(R.id.video_title);
        this.f = (ImageView) view.findViewById(R.id.hot_play_img);
        this.h = (StrokeTextView) view.findViewById(R.id.ad_tag);
        this.j = (TextView) view.findViewById(R.id.score_tv);
        this.g = (TextView) view.findViewById(R.id.video_text);
        this.k = view.findViewById(R.id.bottom_divider);
        this.l = view.findViewById(R.id.bottom_divider2);
        this.m = (TextView) view.findViewById(R.id.video_summary);
        int size = UiUtils.getSize(this.t, R.dimen.video_item_height);
        if (b == null) {
            b = GlideUtils.createVideoRequestOptions(this.t, UiUtils.getSize(this.t, R.dimen.video_item_width), size, UiUtils.getSize(this.t, R.dimen.video_item_title_height));
        }
    }

    public void bind(int i, int i2, String str, VideoData videoData, String str2, String str3) {
        a(i, i2, str, str2, str3);
        a(videoData);
    }

    private void a(final Context context, VideoData videoData) {
        final Picture.AdInfo adInfo = videoData.getAdInfo();
        if (adInfo.isUrl() && !TextUtils.isEmpty(adInfo.landingPageUrl)) {
            L.homepage.d("ad landing page url : %s", adInfo.deeplink);
            SimpleWebActivity.startSimpleWebActivity(context, adInfo.landingPageUrl);
        }
        if (!TextUtils.isEmpty(adInfo.deeplink)) {
            L.homepage.d("ad updeeplink : %s", adInfo.deeplink);
            if (adInfo.deeplink.contains("mico.childvideo.vip")) {
                Intent intent = new Intent(context, MiTvVipActivity.class);
                intent.putExtra(MiTvVipActivity.INTENT_EXTRA_MI_TV_TYPE, "mico.childvideo.vip.mitv".equals(adInfo.deeplink) ? ChildVideo.MiTvType.CHILD_VIDEO : ChildVideo.MiTvType.COURSE_PRIMARY);
                ActivityLifeCycleManager.startActivityQuietly(intent);
                return;
            }
            Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoViewHolder$cAgiMnfH4T0Qo5-BKqhCAhNVfeI
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    AdHelper.recordAdDeepLinkLaunchSuccess(context, adInfo);
                }
            }).subscribeOn(MicoSchedulers.io()).subscribe();
            SchemaManager.handleSchema(context, adInfo.deeplink);
        }
    }

    private void a(Context context) {
        List<VideoData> list = HomeVideoDataManager.getManager().getShortVideoMaps().get(this.p);
        if (a || list != null) {
            int indexOf = list.indexOf(this.s);
            ArrayList arrayList = new ArrayList();
            for (VideoData videoData : list) {
                VideoItem videoItem = new VideoItem();
                videoItem.setCp(videoData.getCp());
                videoItem.setMediaId(videoData.getCpId());
                videoItem.setVideoUrl(videoData.getWebUrl());
                videoItem.setImageUrl(videoData.getImage());
                videoItem.setTitle(videoData.getName());
                arrayList.add(videoItem);
            }
            VideoPlayerApi.play(context, arrayList, indexOf, VideoMode.SHORT_VIDEO_RECOMMEND);
            return;
        }
        throw new AssertionError();
    }

    private void a(int i, int i2, String str, String str2, String str3) {
        this.n = i;
        this.o = i2;
        this.p = str;
        this.q = str2;
        this.r = str3;
    }

    private void a(final VideoData videoData) {
        if (ContainerUtil.isEmpty(this.q)) {
            this.q = videoData.getTraceId();
        }
        if (ContainerUtil.isEmpty(this.r)) {
            this.r = videoData.getExpId();
        }
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoViewHolder$HpCzHRERgYMCiqIgC3x4_TowcDU
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                VideoViewHolder.this.a(videoData, observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).subscribe();
        this.s = videoData;
        if (this.j == null) {
            L.homepage.e("videoViewHolder error happen ,view is null");
            return;
        }
        TraceUtil.beginSection("jiangliang setrating");
        boolean b2 = b(videoData);
        TraceUtil.endSection();
        TraceUtil.beginSection("jiangliang setVideoEpisode");
        boolean b3 = b(videoData, b2);
        TraceUtil.endSection();
        int i = 0;
        a(videoData, b2 || b3);
        Drawable drawable = null;
        this.f.setImageDrawable(videoData.isHotPlay() ? GlideUtils.getDrawable(this.t, R.drawable.icon_video_fire) : null);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.d.getLayoutParams();
        if (videoData.isHotPlay()) {
            marginLayoutParams.leftMargin = this.itemView.getResources().getDimensionPixelSize(R.dimen.video_vip_hot_margin);
        } else {
            marginLayoutParams.leftMargin = 0;
        }
        ImageView imageView = this.d;
        if (videoData.isVip()) {
            drawable = GlideUtils.getDrawable(this.t, R.drawable.icon_video_vip);
        }
        imageView.setImageDrawable(drawable);
        StrokeTextView strokeTextView = this.h;
        if (!videoData.isAd()) {
            i = 8;
        }
        strokeTextView.setVisibility(i);
        TraceUtil.beginSection("jiangliang set cp");
        a(videoData.getCp());
        TraceUtil.endSection();
        TraceUtil.beginSection("jiangliang set video title");
        String titleText = videoData.getTitleText();
        if (titleText == null) {
            this.i.setText("");
        } else {
            this.i.setText(titleText);
        }
        TraceUtil.endSection();
        TraceUtil.beginSection("jiangliang bind image");
        GlideUtils.bindImageViewWithDesignatedLocation(this.c, videoData.getImage(), b);
        TraceUtil.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(VideoData videoData, ObservableEmitter observableEmitter) throws Exception {
        HomeVideoRecordHelper.recordHomeVideo(EventType.EXPOSE, this.p, this.n, this.o, videoData);
        HomeVideoRecordHelper.recordHomeByMi(videoData, true, this.p);
    }

    private boolean a(VideoData videoData, boolean z) {
        String desc = videoData.getDesc();
        if (desc == null) {
            desc = "";
        }
        this.m.setText(desc);
        boolean z2 = !TextUtils.isEmpty(desc);
        int i = 8;
        if (z) {
            View view = this.l;
            if (z2) {
                i = 0;
            }
            view.setVisibility(i);
        } else {
            this.l.setVisibility(8);
        }
        return z2;
    }

    private boolean b(VideoData videoData, boolean z) {
        String text = videoData.getText();
        this.g.setText(text);
        boolean z2 = !TextUtils.isEmpty(text);
        int i = 0;
        if (z) {
            this.k.setVisibility(z2 ? 0 : 8);
        } else {
            this.k.setVisibility(8);
        }
        TextView textView = this.g;
        if (!z2) {
            i = 8;
        }
        textView.setVisibility(i);
        return z2;
    }

    private boolean b(VideoData videoData) {
        if (videoData.getRating() != 0.0d) {
            this.j.setVisibility(0);
            String ratingText = videoData.getRatingText();
            if (!ContainerUtil.hasData(ratingText)) {
                ratingText = Double.toString(videoData.getRating());
            }
            this.j.setText(this.itemView.getResources().getString(R.string.format_score, ratingText));
            return true;
        }
        this.j.setVisibility(8);
        return false;
    }

    private void a(String str) {
        if (ContainerUtil.isEmpty(str)) {
            this.e.setImageDrawable(null);
            return;
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -1427573947:
                if (str.equals("tencent")) {
                    c = 4;
                    break;
                }
                break;
            case 100440849:
                if (str.equals("iqiyi")) {
                    c = 0;
                    break;
                }
                break;
            case 115168713:
                if (str.equals("youku")) {
                    c = 1;
                    break;
                }
                break;
            case 835447236:
                if (str.equals("mangotv")) {
                    c = 3;
                    break;
                }
                break;
            case 887268872:
                if (str.equals("bilibili")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.e.setImageDrawable(GlideUtils.getDrawable(this.t, R.drawable.icon_video_aiqiyi));
                return;
            case 1:
                this.e.setImageDrawable(GlideUtils.getDrawable(this.t, R.drawable.icon_video_youku));
                return;
            case 2:
                this.e.setImageDrawable(GlideUtils.getDrawable(this.t, R.drawable.icon_video_bilibili));
                return;
            case 3:
                this.e.setImageDrawable(GlideUtils.getDrawable(this.t, R.drawable.icon_video_mangguo));
                return;
            case 4:
                this.e.setImageDrawable(GlideUtils.getDrawable(this.t, R.drawable.icon_video_tencent));
                return;
            default:
                this.e.setImageDrawable(null);
                return;
        }
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"CheckResult"})
    public void initInMain() {
        super.initInMain();
        AnimLifecycleManager.repeatOnAttach(this.itemView, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.itemView).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoViewHolder$7xbuFrlkgx84k3cdahddAPg5OeU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoViewHolder.this.a(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        L.homepage.i("play video : %s", this.s);
        if (CommonUtil.checkHasNetworkAndShowToast(this.t)) {
            HomeVideoRecordHelper.recordHomeVideo(EventType.CLICK, this.p, this.n, this.o, this.s);
            HomeVideoRecordHelper.recordHomeByMi(this.s, false, this.p);
            if (this.s.isAd()) {
                AdHelper.jump(this.t, this.s.getAdInfo(), AdHelper.AdCategory.HOME_AD_CLICK, AdHelper.AdKey.HOME);
                return;
            }
            this.s.setTraceId(this.q);
            this.s.setExpId(this.r);
            ApiRealmHelper.getInstance().insertVideoData(this.s);
            if (this.s.isOp()) {
                a(this.t, this.s);
            } else if (this.s.isShort()) {
                a(this.t);
            } else {
                PlayHelper.play(this.t, this.s);
            }
        }
    }
}
