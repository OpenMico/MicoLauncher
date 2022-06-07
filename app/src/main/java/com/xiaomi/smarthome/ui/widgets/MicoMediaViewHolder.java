package com.xiaomi.smarthome.ui.widgets;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4BigButton;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4SmallButton;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4TabAndSwitch;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.MediaType;
import com.xiaomi.smarthome.core.entity.MicoMediaData;
import com.xiaomi.smarthome.core.miot.MediaControllerEvent;
import com.xiaomi.smarthome.core.utils.QsIntent;
import com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment;
import com.xiaomi.smarthome.ui.media.RelayMediaManger;
import com.xiaomi.smarthome.ui.media.RelayPlayCallback;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import com.xiaomi.smarthome.ui.widgets.transform.MediaBorderTransform;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class MicoMediaViewHolder extends BaseViewHolder {
    public static final int FROM_HOME = 1;
    public static final int FROM_SECOND = 2;
    private TextView a;
    private final ImageView b;
    private final ImageView c;
    private final ImageView d;
    private final TextView e;
    private final TextView f;
    private TextView g;
    private final ImageView h;
    private final ImageView i;
    private final ImageView j;
    private MicoMediaData k;
    private int l;
    private int m = 1;

    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    public MicoMediaViewHolder(@NonNull View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.card_title);
        this.b = (ImageView) view.findViewById(R.id.media_player_bg);
        this.c = (ImageView) view.findViewById(R.id.cover);
        this.d = (ImageView) view.findViewById(R.id.media_type);
        this.e = (TextView) view.findViewById(R.id.title);
        this.f = (TextView) view.findViewById(R.id.summary);
        this.g = (TextView) view.findViewById(R.id.continue_play);
        this.j = (ImageView) view.findViewById(R.id.prev_btn);
        this.h = (ImageView) view.findViewById(R.id.play_btn);
        this.i = (ImageView) view.findViewById(R.id.next_btn);
    }

    public void bind(MicoMediaData micoMediaData, int i) {
        if (micoMediaData != null) {
            this.m = i;
            this.k = micoMediaData;
            if (i == 2) {
                this.a.setVisibility(8);
            } else {
                this.a.setVisibility(0);
                int size = RelayMediaManger.INSTANCE.getRelayMediaDataList().size();
                if (size <= 0) {
                    size = 1;
                }
                TextView textView = this.a;
                textView.setText(textView.getResources().getString(R.string.smart_home_card_title_num, Integer.valueOf(size)));
            }
            if (!TextUtils.isEmpty(micoMediaData.getArtist()) || !TextUtils.isEmpty(micoMediaData.getTitle()) || !TextUtils.isEmpty(micoMediaData.getCover())) {
                int i2 = AnonymousClass2.a[micoMediaData.getMediaType().ordinal()];
                if (i2 == 1) {
                    this.d.setVisibility(0);
                    a(micoMediaData);
                    this.f.setText(micoMediaData.getSource());
                    a(true);
                } else if (i2 != 4) {
                    this.d.setVisibility(8);
                    a(micoMediaData);
                    this.f.setText(micoMediaData.getArtist());
                    a(true);
                } else {
                    this.d.setVisibility(0);
                    a(micoMediaData);
                    this.f.setText(micoMediaData.getSource());
                    a(false);
                    if (RelayMediaManger.INSTANCE.isLoading(this.k)) {
                        this.g.setText(R.string.smart_home_media_continue_loading);
                    } else {
                        this.g.setText(R.string.smart_home_media_continue_play);
                    }
                }
                this.l = RelayMediaManger.INSTANCE.getPlayState();
                refreshPlayState(this.l);
                return;
            }
            this.e.setText(this.itemView.getResources().getString(R.string.smart_home_media_none_play));
            this.f.setText("");
            this.e.setTextColor(this.itemView.getResources().getColor(R.color.x08l_media_player_unable_text_color, null));
            this.c.setImageResource(R.drawable.ic_media_default_cover);
            this.i.setImageResource(R.drawable.menu_next_btn_unable);
            this.j.setImageResource(R.drawable.menu_prev_btn_unable);
            this.h.setImageResource(R.drawable.menu_pause_btn_unable);
            this.d.setVisibility(8);
            a(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.smarthome.ui.widgets.MicoMediaViewHolder$2 */
    /* loaded from: classes4.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[MediaType.values().length];

        static {
            try {
                a[MediaType.MIPLAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[MediaType.LOCAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[MediaType.BT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[MediaType.RELAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void a(boolean z) {
        if (z) {
            this.h.setVisibility(0);
            this.j.setVisibility(0);
            this.i.setVisibility(0);
            this.g.setVisibility(8);
            return;
        }
        this.h.setVisibility(8);
        this.j.setVisibility(8);
        this.i.setVisibility(8);
        this.g.setVisibility(0);
    }

    private void a(MicoMediaData micoMediaData) {
        this.e.setText(micoMediaData.getTitle());
        this.e.setTextColor(this.itemView.getResources().getColor(R.color.smart_home_media_title_color, null));
        this.i.setImageResource(R.drawable.menu_next_btn);
        this.j.setImageResource(R.drawable.menu_prev_btn);
        this.i.setEnabled(true);
        this.j.setEnabled(true);
        this.h.setEnabled(true);
        this.b.setBackground(this.itemView.getResources().getDrawable(R.drawable.shape_widgets_online, null));
        if (!TextUtils.isEmpty(micoMediaData.getCover())) {
            Glide.with(this.itemView).load(Uri.parse(micoMediaData.getCover())).placeholder(R.drawable.ic_media_default_cover).transform(new MultiTransformation(new CenterCrop(), new MediaBorderTransform(this.itemView.getResources().getDimensionPixelSize(R.dimen.mico_device_corner_radius), this.itemView.getResources().getDimensionPixelSize(R.dimen.mico_device_bitmap_stroke), this.c.getResources().getColor(R.color.white_10_transparent, null)))).into(this.c);
        } else if (micoMediaData.getBitmap() != null) {
            Glide.with(this.itemView).load(micoMediaData.getBitmap()).transform(new MultiTransformation(new CenterCrop(), new MediaBorderTransform(this.itemView.getResources().getDimensionPixelSize(R.dimen.mico_device_corner_radius), this.itemView.getResources().getDimensionPixelSize(R.dimen.mico_device_bitmap_stroke), this.c.getResources().getColor(R.color.white_10_transparent, null)))).into(this.c);
        } else {
            this.c.setImageResource(R.drawable.ic_media_default_cover);
        }
    }

    @MainThread
    public void onPlayStateChanged(MediaControllerEvent.MusicState musicState) {
        refreshPlayState(musicState.getState());
    }

    public void refreshPlayState(int i) {
        this.l = i;
        MicoMediaData micoMediaData = this.k;
        if (micoMediaData != null && !TextUtils.isEmpty(micoMediaData.getTitle())) {
            if (a()) {
                this.h.setImageResource(R.drawable.menu_pause_btn);
            } else if (this.k.getMediaType() == MediaType.RELAY) {
            } else {
                if (i == 3) {
                    this.h.setImageResource(R.drawable.menu_play_btn);
                } else if (i == 2 || i == 1) {
                    this.h.setImageResource(R.drawable.menu_pause_btn);
                }
            }
        }
    }

    private boolean a() {
        return this.m == 2 && this.k.getMediaType() == MediaType.LOCAL && !RelayMediaManger.INSTANCE.isLocalPlaying();
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void initInMain() {
        AnimLifecycleManager.repeatOnAttach(this.h, MicoAnimConfigurator4SmallButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.h).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$MicoMediaViewHolder$u4Ea1MlnF3Ua9Ywu1dB54kYppoA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MicoMediaViewHolder.this.f(obj);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.j, MicoAnimConfigurator4SmallButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.j).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$MicoMediaViewHolder$qcQoaVitsoa3pl4nDu-yJhWhLM4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MicoMediaViewHolder.this.e(obj);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.b, MicoAnimConfigurator4BigButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.b).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$MicoMediaViewHolder$ZBzGqdcar7yDxmNT3RfyFidKJFo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MicoMediaViewHolder.this.d(obj);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.a, MicoAnimConfigurator4TabAndSwitch.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.a).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$MicoMediaViewHolder$fDm8YDZhKnYMwsoQXDMNDCvoyos
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MicoMediaViewHolder.this.c(obj);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.i, MicoAnimConfigurator4SmallButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.i).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$MicoMediaViewHolder$OkxhlgLVhJRiLtR0QmIN8KC5LDk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MicoMediaViewHolder.this.b(obj);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.g, MicoAnimConfigurator4SmallButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.g).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$MicoMediaViewHolder$KZca0a5syNlkDqcnpcJyxwX2V1I
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MicoMediaViewHolder.this.a(obj);
            }
        });
    }

    public /* synthetic */ void f(Object obj) throws Exception {
        if (a()) {
            QsIntent.onMusicPlayWifi();
        } else if (3 == this.l) {
            QsIntent.onMusicPause();
            this.h.setImageResource(R.drawable.menu_play_btn);
        } else {
            QsIntent.onMusicPlay();
            this.h.setImageResource(R.drawable.menu_play_btn);
        }
        SmartHomeStatHelper.recordSmartTabControlModeClick("media");
    }

    public /* synthetic */ void e(Object obj) throws Exception {
        if (a()) {
            QsIntent.onMusicPrevWifi();
            return;
        }
        QsIntent.onMusicPrev();
        SmartHomeStatHelper.recordSmartTabControlModeClick("media");
    }

    public /* synthetic */ void d(Object obj) throws Exception {
        if (this.k.getMediaType() != MediaType.RELAY || this.m != 2) {
            if (this.m == 2) {
                if (a()) {
                    QsIntent.onMusicPlayWifi();
                } else {
                    QsIntent.onMusicPlay();
                    this.h.setImageResource(R.drawable.menu_play_btn);
                }
            }
            QsIntent.onMusicJump();
            SmartHomeStatHelper.recordSmartTabControlModeClick("media");
        }
    }

    public /* synthetic */ void c(Object obj) throws Exception {
        LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_JUMP_TO_MEDIA_LIST).post(this.k);
    }

    public /* synthetic */ void b(Object obj) throws Exception {
        if (a()) {
            QsIntent.onMusicNextWifi();
            return;
        }
        QsIntent.onMusicNext();
        SmartHomeStatHelper.recordSmartTabControlModeClick("media");
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        if (!TextUtils.isEmpty(this.k.getDeviceId()) && !RelayMediaManger.INSTANCE.isLoading(this.k)) {
            RelayMediaManger.INSTANCE.playRelay(this.k, new RelayPlayCallback() { // from class: com.xiaomi.smarthome.ui.widgets.MicoMediaViewHolder.1
                @Override // com.xiaomi.smarthome.ui.media.RelayPlayCallback
                public void onSuccess(MicoMediaData micoMediaData) {
                }

                @Override // com.xiaomi.smarthome.ui.media.RelayPlayCallback
                public void onError(MicoMediaData micoMediaData) {
                    ToastUtil.showToast(R.string.smart_home_media_relay_error_text);
                    if (micoMediaData != null && MicoMediaViewHolder.this.k != null && TextUtils.equals(micoMediaData.getDeviceId(), MicoMediaViewHolder.this.k.getDeviceId())) {
                        MicoMediaViewHolder.this.g.setText(R.string.smart_home_media_continue_play);
                    }
                }
            });
            this.g.setText(R.string.smart_home_media_continue_loading);
        }
    }
}
