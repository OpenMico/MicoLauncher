package com.xiaomi.micolauncher.skills.music.view_v2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.common.TypefaceUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.common.widget.PaletteShadowView;
import com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent;
import com.xiaomi.micolauncher.module.cameradetection.event.GestureControlEvent;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.CollectionManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcManager;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcViewImpl1;
import com.xiaomi.micolauncher.skills.music.view_v2.PlayerProgressbar;
import com.xiaomi.micolauncher.skills.music.vip.MusicVipActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import ui.view.GestureToast;

/* loaded from: classes3.dex */
public class PlayerMainFragmentV2 extends BaseFragment implements Handler.Callback, CollectionManager.CollectionListener, LocalPlayerManager.MetadataChangedCallback, PlayerProgressbar.OnPlayerProgressbarChangeListener {
    private static final long a = TimeUnit.SECONDS.toMillis(8);
    private TextView b;
    private TextView c;
    private PaletteShadowView d;
    private ImageView e;
    private ImageView f;
    private ImageView g;
    private LrcViewImpl1 h;
    private PlayerProgressbar i;
    private ImageView j;
    private ImageView k;
    private ImageView l;
    private View m;
    private TextView n;
    private Group o;
    private ImageView p;
    public Remote.Response.PlayingData playingData;
    private TextView q;
    private Handler r;
    private int s = -1;
    private int t = -1;
    private int u;
    private String v;
    private Disposable w;
    private RectF x;

    public static PlayerMainFragmentV2 newInstance() {
        return new PlayerMainFragmentV2();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what == 1) {
            PlayerProgressbar playerProgressbar = this.i;
            if (playerProgressbar == null || this.d == null) {
                return false;
            }
            playerProgressbar.setVisibility(8);
            PlayerActivityV2 playerActivityV2 = (PlayerActivityV2) getActivity();
            if (playerActivityV2 != null && !playerActivityV2.isFinishing()) {
                if (this.x == null) {
                    int[] iArr = new int[2];
                    this.d.getLocationOnScreen(iArr);
                    this.x = new RectF(iArr[0], iArr[1], iArr[0] + this.d.getWidth(), iArr[1] + this.d.getHeight());
                }
                playerActivityV2.setFragmentContainer(PlayerSimpleFragmentV2.newInstance(this.x, SystemSetting.getKeyGestureControlEnable()), 2);
            }
        }
        return true;
    }

    private boolean a() {
        return this.i == null;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.r = new MicoHandler(Looper.getMainLooper(), "PlayerV2Handler", this) { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerMainFragmentV2.1
            @Override // com.xiaomi.micolauncher.common.MicoHandler
            public String getLogTag() {
                return "PlayerV2Handler";
            }

            @Override // com.xiaomi.micolauncher.common.MicoHandler
            public void processMessage(Message message) {
            }
        };
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_player_music_v2, (ViewGroup) null);
        this.b = (TextView) inflate.findViewById(R.id.song_name);
        this.c = (TextView) inflate.findViewById(R.id.artist);
        this.d = (PaletteShadowView) inflate.findViewById(R.id.cover);
        this.e = (ImageView) inflate.findViewById(R.id.pre_btn);
        this.f = (ImageView) inflate.findViewById(R.id.play_btn);
        this.g = (ImageView) inflate.findViewById(R.id.next_btn);
        this.h = (LrcViewImpl1) inflate.findViewById(R.id.lrc_view);
        this.i = (PlayerProgressbar) inflate.findViewById(R.id.progress_bar);
        this.j = (ImageView) inflate.findViewById(R.id.play_list_btn);
        this.k = (ImageView) inflate.findViewById(R.id.play_mode);
        this.l = (ImageView) inflate.findViewById(R.id.love_btn);
        this.m = inflate.findViewById(R.id.player_list_mask);
        this.n = (TextView) inflate.findViewById(R.id.openVip);
        this.o = (Group) inflate.findViewById(R.id.groupQQLogo);
        this.p = (ImageView) inflate.findViewById(R.id.phone_name_icon);
        this.q = (TextView) inflate.findViewById(R.id.phone_name_text);
        b();
        CollectionManager.getInstance().registerCollectionListener(this);
        LocalPlayerManager.getInstance().registerCallback(this);
        return inflate;
    }

    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    private void b() {
        this.g.setClickable(false);
        this.e.setClickable(false);
        this.d.setClickable(false);
        this.d.setImageResource(R.drawable.player_cover);
        this.d.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.k.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.l.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        RxViewHelp.debounceClicks(this.d).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$sq-sIPS2ntQpOsUMGe79_QlQ-Mo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerMainFragmentV2.this.g(obj);
            }
        });
        RxViewHelp.debounceClicks(this.k, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$mCGI6kIjgz-LxsoabpKi1Gy8_YQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerMainFragmentV2.this.f(obj);
            }
        });
        RxViewHelp.debounceClicks(this.e, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$0g0wcnbZGUk0m-FzOzZ8ttgBJik
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerMainFragmentV2.this.e(obj);
            }
        });
        RxViewHelp.debounceClicks(this.f, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$4yJTYPp9IzSCNy350fZblxEvx5k
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerMainFragmentV2.this.d(obj);
            }
        });
        RxViewHelp.debounceClicks(this.g, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$WxISa2kZn17Gj9Ie8CnNrJ82pxA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerMainFragmentV2.this.c(obj);
            }
        });
        RxViewHelp.debounceClicks(this.l, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$cxzb_j6JwVl0JwR6bH0US2cksrA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerMainFragmentV2.this.b(obj);
            }
        });
        RxViewHelp.debounceClicks(this.j, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$iQ6bwKULu4h50JjkhvL1uS5U5kI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerMainFragmentV2.this.a(obj);
            }
        });
        this.i.setPlayerProgressbarChangeListener(this);
        if (Hardware.isBigScreen()) {
            this.h.setNormalLrcTextTypeface(Typeface.create(TypefaceUtil.FONT_WEIGHT_W300, 0));
            this.h.setHighlightLrcTextTypeface(Typeface.create(TypefaceUtil.FONT_WEIGHT_W600, 0));
        }
        this.h.setAlignment(Layout.Alignment.ALIGN_NORMAL);
        this.h.setTotalRowsToDraw(6);
        this.h.setOnLrcTouchListener(new LrcViewImpl1.OnLrcTouchListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerMainFragmentV2.2
            @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcViewImpl1.OnLrcTouchListener
            public void onLrcTouchActive() {
                if (PlayerMainFragmentV2.this.r.hasMessages(1)) {
                    PlayerMainFragmentV2.this.r.removeMessages(1);
                }
            }

            @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcViewImpl1.OnLrcTouchListener
            public void onLrcTouchEnd() {
                PlayerMainFragmentV2.this.c();
            }
        });
        String charSequence = this.n.getText().toString();
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.music_vip_open_green)), charSequence.indexOf(getString(R.string.open_vip_music_part)), charSequence.length(), 33);
        this.n.setText(spannableString);
        this.n.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$BD-Um232fMwVvPHdhERR_-CNwjE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PlayerMainFragmentV2.this.a(view);
            }
        });
    }

    public /* synthetic */ void g(Object obj) throws Exception {
        a(0L);
    }

    public /* synthetic */ void f(Object obj) throws Exception {
        c();
        a(PlayerApi.nextLoop());
        MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.RECYCLE);
    }

    public /* synthetic */ void e(Object obj) throws Exception {
        c();
        PlayerApi.prev();
        MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.PREV);
    }

    public /* synthetic */ void d(Object obj) throws Exception {
        c();
        PlayerApi.playOrPause();
        if ("1".equals(this.f.getTag())) {
            this.f.setImageResource(R.drawable.icon_black_pause);
            MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.PAUSE);
            return;
        }
        this.f.setImageResource(R.drawable.icon_black_play);
        MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.PLAY);
    }

    public /* synthetic */ void c(Object obj) throws Exception {
        c();
        PlayerApi.next();
        MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.NEXT);
    }

    public /* synthetic */ void b(Object obj) throws Exception {
        c();
        ImageView imageView = this.l;
        if (imageView != null) {
            PlayerApi.toggleLove(!imageView.isSelected());
            if (this.l.isSelected()) {
                MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.CANCEL_COLLECT);
            } else {
                MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.COLLECT);
            }
            PlaybackTrackHelper.postFavoriteEvent(this.playingData, "click", !this.l.isSelected());
            ImageView imageView2 = this.l;
            imageView2.setSelected(!imageView2.isSelected());
        }
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        this.r.removeMessages(1);
        this.m.setVisibility(0);
        ActivityLifeCycleManager.startActivityQuietly(getContext(), new Intent(getContext(), PlayerListActivityV2.class));
    }

    public /* synthetic */ void a(View view) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(getContext(), MusicVipActivity.class));
    }

    private void a(int i) {
        if (i == LoopType.ORDER.ordinal()) {
            this.k.setImageResource(R.drawable.playmode_loop);
        } else if (i == LoopType.SINGLE_LOOP.ordinal()) {
            this.k.setImageResource(R.drawable.playmode_loop_single);
        } else if (i == LoopType.LIST_LOOP.ordinal()) {
            this.k.setImageResource(R.drawable.playmode_loop);
        } else if (i == LoopType.SHUFFLE.ordinal()) {
            this.k.setImageResource(R.drawable.playmode_loop_shuffle);
        } else if (i == LoopType.NONE.ordinal()) {
            this.k.setImageResource(R.drawable.playmode_loop_disable);
        } else {
            this.k.setImageResource(R.drawable.playmode_loop);
        }
    }

    public void c() {
        a(a);
    }

    private void a(long j) {
        this.r.removeMessages(1);
        Remote.Response.PlayerStatus playingPlayerStatus = LocalPlayerManager.getInstance().getPlayingPlayerStatus();
        if (playingPlayerStatus != null && playingPlayerStatus.isPlayingStatus() && !MusicHelper.isPlayRemote(playingPlayerStatus.media_type)) {
            this.r.sendEmptyMessageDelayed(1, j);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        registerToEventBusIfNot();
        this.m.setVisibility(8);
        Remote.Response.PlayerStatus playingPlayerStatus = LocalPlayerManager.getInstance().getPlayingPlayerStatus();
        if (playingPlayerStatus != null) {
            a(playingPlayerStatus);
            if (playingPlayerStatus.isPlayingStatus()) {
                c();
            }
        }
        MusicStatHelper.recordPlayerShow(MusicStatHelper.PlayerPage.LYRIC);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.r.removeMessages(1);
        unregisterToEventBusIfNeed();
        LocalPlayerManager.getInstance().unregisterCallback(this);
        CollectionManager.getInstance().unregisterCollectionListener(this);
        d();
        LrcManager.getInstance().clearLrc();
    }

    private void d() {
        Disposable disposable = this.w;
        if (disposable != null && !disposable.isDisposed()) {
            this.w.dispose();
        }
    }

    private void a(Remote.Response.PlayerStatus playerStatus) {
        PaletteShadowView paletteShadowView;
        if (playerStatus.play_song_detail != null && !isDetached()) {
            d(playerStatus);
            if (playerStatus.media_type != this.t || LocalPlayerManager.isResourceChange(playerStatus, this.playingData)) {
                this.playingData = playerStatus.play_song_detail;
                this.t = playerStatus.media_type;
                a(playerStatus.play_song_detail);
                e(playerStatus);
                if (MusicHelper.isPlayingBluetoothOrDlna(playerStatus.media_type)) {
                    h();
                    this.d.setImageResource(R.drawable.player_cover_default);
                    if (!isOnResume()) {
                        EventBusRegistry.getEventBus().post(new PlayerEvent.ClosePlayerListActivityV2Event());
                    }
                    e();
                } else if (MusicHelper.isPlayingMiPlay(playerStatus.media_type)) {
                    i();
                    if (TextUtils.isEmpty(playerStatus.play_song_detail.lrc)) {
                        a((String) null, (List<LrcRow>) null);
                    } else {
                        this.h.showLoadingLrc();
                        LrcManager.getInstance().loadLrc(playerStatus.play_song_detail, new $$Lambda$PlayerMainFragmentV2$T909LLWNDI8Yw18atYgx6RlURM(this));
                    }
                    if (!(playerStatus.play_song_detail.coverBitmap == null || (paletteShadowView = this.d) == null)) {
                        paletteShadowView.setImageBitmap(playerStatus.play_song_detail.coverBitmap);
                    }
                    if (!isOnResume()) {
                        EventBusRegistry.getEventBus().post(new PlayerEvent.ClosePlayerListActivityV2Event());
                    }
                    f();
                } else {
                    f(playerStatus);
                    this.h.showLoadingLrc();
                    LrcManager.getInstance().loadLrc(playerStatus.play_song_detail, new $$Lambda$PlayerMainFragmentV2$T909LLWNDI8Yw18atYgx6RlURM(this));
                    if (!TextUtils.isEmpty(playerStatus.play_song_detail.cover)) {
                        a(playerStatus.play_song_detail.cover);
                    }
                    if (this.l.isEnabled()) {
                        c(playerStatus);
                    }
                    b(playerStatus);
                    e();
                }
            } else {
                L.player.i("PlayerMainFragmentV2 refreshUI needResourceChange but not");
            }
        }
    }

    private void e() {
        this.q.setVisibility(8);
        this.p.setVisibility(8);
    }

    private void f() {
        this.q.setVisibility(0);
        this.p.setVisibility(0);
        g();
    }

    private void b(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus.play_song_detail.isPreview()) {
            this.n.setVisibility(0);
            this.h.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.player_v2_lrc_height);
            return;
        }
        this.n.setVisibility(8);
        this.h.getLayoutParams().height = (int) (getResources().getDimensionPixelOffset(R.dimen.player_v2_lrc_height) * 1.1d);
    }

    private void a(Remote.Response.PlayingData playingData) {
        if (playingData != null) {
            this.b.setText(playingData.title);
            this.b.setSelected(true);
            this.c.setText(playingData.getSubTitle());
            this.o.setVisibility(MusicHelper.isQQResource(playingData.cpOrigin) ? 0 : 8);
        }
    }

    private void a(String str) {
        if (this.d != null) {
            if (str == null || !str.equals(this.v)) {
                this.v = str;
                Glide.with(this.d).load(str).apply((BaseRequestOptions<?>) GlideUtils.getDefaultRequestOptions().error(R.drawable.player_cover_default)).into(this.d);
            }
        }
    }

    private void c(final Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus != null && playerStatus.play_song_detail != null && !a()) {
            Remote.Response.PlayingData playingData = playerStatus.play_song_detail;
            d();
            this.w = CollectionManager.getInstance().loadLoveStatus(playingData.audioID, playingData.albumGlobalID, playerStatus.media_type).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$IR5dbD7ltLWn1EMgC_mZXYkCCWE
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    PlayerMainFragmentV2.this.a(playerStatus, (Boolean) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$Fv9l0csCrJAUdC9Qxcn6_ZvnRaA
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    PlayerMainFragmentV2.this.a((Throwable) obj);
                }
            });
        }
    }

    public /* synthetic */ void a(Remote.Response.PlayerStatus playerStatus, Boolean bool) throws Exception {
        L.player.i("PlayerMainFragmentV2 loadLoveStatus cpId=%s isFavourite=%s", playerStatus.play_song_detail.cpID, bool);
        ImageView imageView = this.l;
        if (imageView != null) {
            imageView.setSelected(bool.booleanValue());
        }
    }

    public /* synthetic */ void a(Throwable th) throws Exception {
        L.player.e("PlayerMainFragmentV2 setLoveState %s", th);
        ImageView imageView = this.l;
        if (imageView != null) {
            imageView.setSelected(false);
        }
    }

    private void g() {
        if (LocalPlayerManager.getInstance().getDeviceName() != null) {
            this.q.setText(getString(R.string.music_source_from, LocalPlayerManager.getInstance().getDeviceName()));
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.view_v2.PlayerProgressbar.OnPlayerProgressbarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        this.r.removeMessages(1);
    }

    @Override // com.xiaomi.micolauncher.skills.music.view_v2.PlayerProgressbar.OnPlayerProgressbarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        Remote.Response.PlayingData playingData = this.playingData;
        if (playingData != null) {
            PlayerApi.seekTo((playingData.duration * seekBar.getProgress()) / 100);
            c();
        }
    }

    public void a(String str, List<LrcRow> list) {
        String str2;
        LrcViewImpl1 lrcViewImpl1 = this.h;
        if (lrcViewImpl1 != null && lrcViewImpl1.getVisibility() == 0) {
            if (list == null || list.isEmpty()) {
                if (getActivity() != null) {
                    str2 = getActivity().getString(R.string.music_no_lrc);
                } else {
                    str2 = MicoApplication.getGlobalContext().getString(R.string.music_no_lrc);
                }
                this.h.setEmptyText(str, str2);
            } else if (!MusicHelper.isPlayingBluetoothOrDlna(this.t)) {
                this.h.setLrcRows(str, list);
                Remote.Response.PlayingData playingData = this.playingData;
                if (playingData != null) {
                    a(playingData.position, false, true);
                }
            }
        }
    }

    private void a(long j, boolean z, boolean z2) {
        LrcViewImpl1 lrcViewImpl1 = this.h;
        if (lrcViewImpl1 != null && lrcViewImpl1.hasLrcRows() && isOnResume()) {
            this.h.seekTo((int) j, z, z2);
        }
    }

    private void d(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus.loop_type != this.u) {
            if (MusicHelper.canResetLoop(playerStatus.media_type)) {
                this.k.setEnabled(true);
                a(playerStatus.loop_type);
            }
            this.u = playerStatus.loop_type;
        }
    }

    private void e(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus.status != this.s) {
            if (playerStatus.isPlayingStatus()) {
                this.f.setImageResource(R.drawable.icon_black_pause);
                this.f.setTag("1");
                if (MusicHelper.isPlayingSong(playerStatus.media_type) && isOnResume() && !this.r.hasMessages(1)) {
                    this.r.sendEmptyMessageDelayed(1, a);
                }
            } else {
                this.f.setImageResource(R.drawable.icon_black_play);
                this.f.setTag("0");
                this.r.removeMessages(1);
            }
        }
        PlayerProgressbar playerProgressbar = this.i;
        if (playerProgressbar != null) {
            playerProgressbar.setPlayerStatus(playerStatus);
        }
        this.s = playerStatus.status;
    }

    private void h() {
        this.l.setSelected(false);
        this.l.setEnabled(false);
        this.j.setEnabled(false);
        this.k.setEnabled(false);
        this.k.setImageResource(R.drawable.playmode_loop_disable);
        this.n.setVisibility(8);
        a((String) null, (List<LrcRow>) null);
        this.r.removeMessages(1);
        this.l.setVisibility(0);
        this.k.setVisibility(0);
        this.j.setVisibility(0);
    }

    private void i() {
        this.l.setSelected(false);
        this.l.setEnabled(false);
        this.j.setEnabled(false);
        this.k.setEnabled(false);
        this.k.setImageResource(R.drawable.playmode_loop_disable);
        this.n.setVisibility(8);
        this.r.removeMessages(1);
        this.l.setVisibility(8);
        this.k.setVisibility(8);
        this.j.setVisibility(8);
    }

    private void f(Remote.Response.PlayerStatus playerStatus) {
        this.j.setEnabled(true);
        if (!(playerStatus.play_song_detail == null || playerStatus.play_song_detail.screenExtend == null)) {
            this.l.setEnabled(CollectionManager.canLovable(playerStatus.media_type));
        }
        if (MusicHelper.canResetLoop(playerStatus.media_type)) {
            this.k.setEnabled(true);
            a(playerStatus.loop_type);
        } else {
            this.k.setEnabled(false);
            this.k.setImageResource(R.drawable.playmode_loop_disable);
        }
        this.l.setVisibility(0);
        this.k.setVisibility(0);
        this.j.setVisibility(0);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.CollectionManager.CollectionListener
    public void onCollectionChange(String str, final boolean z) {
        ImageView imageView = this.l;
        if (imageView != null) {
            imageView.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$CQhv4OOMfVvYpY1Z6paOi6lDe3Y
                @Override // java.lang.Runnable
                public final void run() {
                    PlayerMainFragmentV2.this.a(z);
                }
            });
        }
    }

    public /* synthetic */ void a(boolean z) {
        ImageView imageView = this.l;
        if (imageView != null) {
            imageView.setSelected(z);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
        if (!a()) {
            a(playerStatus);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaListChanged(Remote.Response.PlayerStatus playerStatus) {
        if (a()) {
            L.player.i("onMediaListChanged is not activated of=%s", this);
            return;
        }
        boolean hasData = ContainerUtil.hasData(playerStatus.extra_track_list);
        this.g.setClickable(hasData);
        this.e.setClickable(hasData);
        this.d.setClickable(hasData);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus) {
        if (a()) {
            L.player.w("onPlayerStateChanged event comes but view not bind=%s", getClass().getSimpleName());
            return;
        }
        d(playerStatus);
        e(playerStatus);
        if (playerStatus.play_song_detail != null && (MusicHelper.isPlayingSong(this.t) || MusicHelper.isPlayingMiPlay(this.t))) {
            LrcViewImpl1 lrcViewImpl1 = this.h;
            if (lrcViewImpl1 != null && lrcViewImpl1.hasNotUpdatedLrc(playerStatus.play_song_detail.getSimpleId())) {
                this.h.showLoadingLrc();
                LrcManager.getInstance().loadLrc(playerStatus.play_song_detail, new $$Lambda$PlayerMainFragmentV2$T909LLWNDI8Yw18atYgx6RlURM(this));
            }
            if (playerStatus.isPlayingStatus()) {
                a(playerStatus.play_song_detail.position, false, !(playerStatus.play_song_detail.position > 1));
            }
        }
        this.playingData = playerStatus.play_song_detail;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGestureControlEvent(GestureControlEvent gestureControlEvent) {
        if (SystemSetting.getKeyGestureControlEnable() && isOnResume()) {
            L.camera2.i("PlayerMainFragmentV2 receive gesture control event  %s", gestureControlEvent.gestureType);
            GestureInfoEvent.GestureType gestureType = gestureControlEvent.gestureType;
            Remote.Response.PlayerStatus playingPlayerStatus = LocalPlayerManager.getInstance().getPlayingPlayerStatus();
            if (GestureInfoEvent.GestureType.ThumbsUp == gestureType && !MusicHelper.isPlayRemote(playingPlayerStatus.media_type)) {
                if (CollectionManager.canLovable(playingPlayerStatus.media_type)) {
                    boolean isSelected = this.l.isSelected();
                    CollectionManager.getInstance().doSetLovable(!isSelected);
                    GestureToast.showGesture(getContext(), gestureType, getString(isSelected ? R.string.gesture_collection_cancel : R.string.gesture_collected));
                    return;
                }
                ToastUtil.showToast((int) R.string.gesture_collection_not_supported);
            }
        }
    }
}
