package com.xiaomi.micolauncher.module.homepage.viewholder.audiobook;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.AudiobookStatUtils;
import com.xiaomi.micolauncher.module.homepage.activity.AudiobookSingleCategoryActivity;
import com.xiaomi.micolauncher.module.homepage.bean.AudiobookContent;
import com.xiaomi.micolauncher.module.homepage.bean.OprationAudiobook;
import com.xiaomi.micolauncher.module.homepage.view.AudioBookContentView;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class AudiobookLatestViewHolder extends BaseViewHolder {
    TextView a;
    TextView b;
    TextView c;
    ImageView e;
    ViewGroup f;
    ImageView g;
    ImageView h;
    String[] j;
    private AudiobookContent k;
    TextView[] d = new TextView[3];
    AudioBookContentView[] i = new AudioBookContentView[2];

    public AudiobookLatestViewHolder(View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.audiobook_title);
        this.b = (TextView) view.findViewById(R.id.audiobook_album);
        this.c = (TextView) view.findViewById(R.id.audiobook_author);
        this.d[0] = (TextView) view.findViewById(R.id.tip_one);
        this.d[1] = (TextView) view.findViewById(R.id.tip_two);
        this.d[2] = (TextView) view.findViewById(R.id.tip_three);
        this.e = (ImageView) view.findViewById(R.id.cover_img);
        this.f = (ViewGroup) view.findViewById(R.id.latest_layout_parent);
        this.g = (ImageView) view.findViewById(R.id.latest_bg);
        this.h = (ImageView) view.findViewById(R.id.audiobook_play);
        this.i[0] = (AudioBookContentView) view.findViewById(R.id.content_one);
        this.i[1] = (AudioBookContentView) view.findViewById(R.id.content_two);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
        AnimLifecycleManager.repeatOnAttach(this.f, MicoAnimConfigurator4EntertainmentAndApps.get());
        AnimLifecycleManager.repeatOnAttach(this.h, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.f).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.-$$Lambda$AudiobookLatestViewHolder$FfWSyV_iFsH3LiwXx6YW-vX_yKE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookLatestViewHolder.this.b(obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.h).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.-$$Lambda$AudiobookLatestViewHolder$KmluDMM-ghLLE_ma5Td0K01lWnI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookLatestViewHolder.this.a(obj);
            }
        });
        final int i = 0;
        while (true) {
            TextView[] textViewArr = this.d;
            if (i < textViewArr.length) {
                AnimLifecycleManager.repeatOnAttach(textViewArr[i], MicoAnimConfigurator4EntertainmentAndApps.get());
                RxViewHelp.debounceClicksWithOneSeconds(this.d[i]).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.-$$Lambda$AudiobookLatestViewHolder$2Bsfh2A07ZRGH-6yaYpUs3pMPC8
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        AudiobookLatestViewHolder.this.a(i, obj);
                    }
                });
                i++;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Object obj) throws Exception {
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Object obj) throws Exception {
        AudiobookSingleCategoryActivity.startAudiobookSingleCategoryActivity(this.g.getContext(), this.j[i]);
    }

    private void a() {
        AudiobookStatUtils.recordPromotion1(this.k, EventType.CLICK);
        if (CommonUtil.checkHasNetworkAndShowToast(this.h.getContext())) {
            PlayerApi.playStation(this.itemView.getContext(), this.k.getCpAlbumId(), this.k.getCp(), this.k.getTypeId());
        }
    }

    public void bind(OprationAudiobook oprationAudiobook) {
        if (oprationAudiobook.getItemList().size() == 1) {
            this.k = oprationAudiobook.getItemList().get(0);
            a(this.k);
            AudiobookStatUtils.recordPromotion1(this.k, EventType.EXPOSE);
            return;
        }
        a(oprationAudiobook.getItemList());
    }

    private void a(List<AudiobookContent> list) {
        if (isVisible(this.f)) {
            setVisibility(this.f, 8);
        }
        for (int i = 0; i < list.size(); i++) {
            AudiobookContent audiobookContent = list.get(i);
            this.i[i].initInMain();
            setVisibility(this.i[i], 0);
            this.i[i].setData(audiobookContent, TrackWidget.STATION_DISCOVER_PROMOTION_1, i);
            this.i[i].setResourceIndex(i);
            AudiobookStatUtils.recordPromotion1(audiobookContent, EventType.EXPOSE);
        }
    }

    private void a(final AudiobookContent audiobookContent) {
        AudioBookContentView[] audioBookContentViewArr = this.i;
        for (AudioBookContentView audioBookContentView : audioBookContentViewArr) {
            if (audioBookContentView.getVisibility() == 0) {
                setVisibility(audioBookContentView, 8);
            }
        }
        if (!isVisible(this.f)) {
            setVisibility(this.f, 0);
        }
        this.a.setText(audiobookContent.getLastSoundTitle());
        this.b.setSingleLine();
        this.b.setText(audiobookContent.getCpAlbumTitle());
        this.c.setText(audiobookContent.getCpName());
        UiUtils.waitLayoutComplete(new UiUtils.OnLayoutCompleteListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.-$$Lambda$AudiobookLatestViewHolder$KMaaFPcgF23l0nFdwtWROCOvYU8
            @Override // com.xiaomi.micolauncher.common.utils.UiUtils.OnLayoutCompleteListener
            public final void onLayoutComplete(boolean z) {
                AudiobookLatestViewHolder.this.b(audiobookContent, z);
            }
        }, this.e);
        if (!TextUtils.isEmpty(audiobookContent.getCategory())) {
            this.j = audiobookContent.getCategory().split(";");
            if (!ContainerUtil.isEmpty(this.j)) {
                for (int i = 0; i < this.j.length; i++) {
                    TextView[] textViewArr = this.d;
                    if (i == textViewArr.length) {
                        break;
                    }
                    textViewArr[i].setText(this.c.getContext().getString(R.string.category_mark, this.j[i]));
                    this.d[i].setBackgroundResource(R.drawable.mark_bg);
                }
                UiUtils.waitLayoutComplete(new UiUtils.OnLayoutCompleteListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.-$$Lambda$AudiobookLatestViewHolder$IfhAJ9ZqZkE0j88NGm1rhHM39kg
                    @Override // com.xiaomi.micolauncher.common.utils.UiUtils.OnLayoutCompleteListener
                    public final void onLayoutComplete(boolean z) {
                        AudiobookLatestViewHolder.this.a(audiobookContent, z);
                    }
                }, this.g);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(AudiobookContent audiobookContent, boolean z) {
        Context context = this.e.getContext();
        GlideUtils.bindImageViewWithRoundCorners(this.e, audiobookContent.getCover(), UiUtils.getSize(context, R.dimen.audiobook_img_cover_radius), R.drawable.book_album_hold, UiUtils.getSize(context, R.dimen.audiobook_newest_cover_size), UiUtils.getSize(context, R.dimen.audiobook_newest_cover_size));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AudiobookContent audiobookContent, boolean z) {
        Context context = this.g.getContext();
        GlideUtils.bindImageView(context, this.g, audiobookContent.getCover(), UiUtils.getSize(context, R.dimen.recommend_layout_width), UiUtils.getSize(context, R.dimen.recommend_layout_height));
    }
}
