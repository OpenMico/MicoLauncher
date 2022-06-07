package com.xiaomi.micolauncher.module.homepage.viewholder.audiobook;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.AudiobookStatUtils;
import com.xiaomi.micolauncher.module.homepage.activity.AudiobookBoughtActivity;
import com.xiaomi.micolauncher.module.homepage.activity.AudiobookCategoryActivity;
import com.xiaomi.micolauncher.module.homepage.activity.AudiobookFavoriteActivity;
import com.xiaomi.micolauncher.module.homepage.activity.AudiobookHistoryActivity;
import com.xiaomi.micolauncher.module.homepage.bean.AudiobookContent;
import com.xiaomi.micolauncher.module.homepage.bean.OprationAudiobook;
import com.xiaomi.micolauncher.module.homepage.view.AudioBookContentView;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class AudiobookBannerViewHolder extends BaseViewHolder {
    ImageView[] a = new ImageView[3];
    AudioBookContentView[] b = new AudioBookContentView[3];
    ImageView c;
    ImageView d;
    ImageView e;
    ImageView f;
    Context g;
    private List<AudiobookContent> h;

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void setData(List<Object> list) {
    }

    public AudiobookBannerViewHolder(View view) {
        super(view);
        this.g = view.getContext();
        this.a[0] = (ImageView) view.findViewById(R.id.audiobook_recommend_one);
        this.a[1] = (ImageView) view.findViewById(R.id.audiobook_recommend_two);
        this.a[2] = (ImageView) view.findViewById(R.id.audiobook_recommend_three);
        this.b[0] = (AudioBookContentView) view.findViewById(R.id.content_one);
        this.b[1] = (AudioBookContentView) view.findViewById(R.id.content_two);
        this.b[2] = (AudioBookContentView) view.findViewById(R.id.content_three);
        this.c = (ImageView) view.findViewById(R.id.audiobook_play_recent);
        this.d = (ImageView) view.findViewById(R.id.audiobook_buy);
        this.e = (ImageView) view.findViewById(R.id.audiobook_favorite);
        this.f = (ImageView) view.findViewById(R.id.audiobook_category);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
        final int i = 0;
        for (AudioBookContentView audioBookContentView : this.b) {
            audioBookContentView.initInMain();
        }
        AnimLifecycleManager.repeatOnAttach(this.c, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.c).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.-$$Lambda$AudiobookBannerViewHolder$a3IF_VXc9ijXmKPbBIbHZz_qQt0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookBannerViewHolder.this.d(obj);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.d, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.d).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.-$$Lambda$AudiobookBannerViewHolder$G7dhQC7Ypty5yH-x8Nnq-06O35g
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookBannerViewHolder.this.c(obj);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.f, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.f).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.-$$Lambda$AudiobookBannerViewHolder$fyjp_RC9YWXM0lk9iimRmiLFaMk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookBannerViewHolder.this.b(obj);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.e, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.e).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.-$$Lambda$AudiobookBannerViewHolder$-a8tX6rX8_NvIemuGYdFbLLmC2c
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookBannerViewHolder.this.a(obj);
            }
        });
        while (true) {
            ImageView[] imageViewArr = this.a;
            if (i < imageViewArr.length) {
                AnimLifecycleManager.repeatOnAttach(imageViewArr[i], MicoAnimConfigurator4EntertainmentAndApps.get());
                RxViewHelp.debounceClicksWithOneSeconds(this.a[i]).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.-$$Lambda$AudiobookBannerViewHolder$6epC993QNkKWGd8hKvVCjGSA5lI
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        AudiobookBannerViewHolder.this.a(i, obj);
                    }
                });
                i++;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(Object obj) throws Exception {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(this.g, AudiobookHistoryActivity.class));
        AudiobookStatUtils.recordFunctionModule(EventType.CLICK, this.g.getString(R.string.audiobook_recent_play));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Object obj) throws Exception {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(this.g, AudiobookBoughtActivity.class));
        AudiobookStatUtils.recordFunctionModule(EventType.CLICK, this.g.getString(R.string.audiobook_buy_text));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Object obj) throws Exception {
        if (CommonUtil.checkHasNetworkAndShowToast(this.g)) {
            ActivityLifeCycleManager.startActivityQuietly(new Intent(this.g, AudiobookCategoryActivity.class));
            AudiobookStatUtils.recordFunctionModule(EventType.CLICK, this.g.getString(R.string.audiobook_all_category));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(this.g, AudiobookFavoriteActivity.class));
        AudiobookStatUtils.recordFunctionModule(EventType.CLICK, this.g.getString(R.string.audiobook_favorite));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Object obj) throws Exception {
        if (CommonUtil.checkHasNetworkAndShowToast(this.a[i].getContext())) {
            AudiobookContent audiobookContent = this.h.get(i);
            PlayerApi.playStation(this.g, audiobookContent.getCpAlbumId(), audiobookContent.getCp(), audiobookContent.getTypeId());
            AudiobookStatUtils.recordPromotion2(audiobookContent, i, EventType.CLICK);
        }
    }

    public void bind(OprationAudiobook oprationAudiobook) {
        this.h = oprationAudiobook.getItemList();
        for (int i = 0; i < this.h.size(); i++) {
            AudiobookContent audiobookContent = this.h.get(i);
            if (TextUtils.isEmpty(audiobookContent.getEid())) {
                a(i);
                GlideUtils.bindImageViewWithRoundCorners(this.a[i], audiobookContent.getCover(), 25, R.drawable.book_banner_hold, UiUtils.getSize(this.g, R.dimen.audiobook_content_width), UiUtils.getSize(this.g, R.dimen.audiobook_banner_height));
            } else {
                b(i);
                this.b[i].setData(audiobookContent, TrackWidget.STATION_DISCOVER_PROMOTION_2, i);
                this.b[i].setResourceIndex(i + 2);
            }
            AudiobookStatUtils.recordPromotion2(audiobookContent, i, EventType.EXPOSE);
        }
        AudiobookStatUtils.recordFunctionModule(EventType.EXPOSE, this.g.getString(R.string.audiobook_recent_play));
        AudiobookStatUtils.recordFunctionModule(EventType.EXPOSE, this.g.getString(R.string.audiobook_buy_text));
        AudiobookStatUtils.recordFunctionModule(EventType.EXPOSE, this.g.getString(R.string.audiobook_all_category));
        AudiobookStatUtils.recordFunctionModule(EventType.EXPOSE, this.g.getString(R.string.audiobook_favorite));
    }

    private void a(int i) {
        if (isVisible(this.b[i])) {
            setVisibility(this.b[i], 8);
        }
        if (!isVisible(this.a[i])) {
            setVisibility(this.a[i], 0);
        }
    }

    private void b(int i) {
        if (!isVisible(this.b[i])) {
            setVisibility(this.b[i], 0);
        }
        if (isVisible(this.a[i])) {
            setVisibility(this.a[i], 8);
        }
    }
}
