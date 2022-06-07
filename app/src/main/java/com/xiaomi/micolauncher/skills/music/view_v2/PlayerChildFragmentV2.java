package com.xiaomi.micolauncher.skills.music.view_v2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.common.TypefaceUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent;
import com.xiaomi.micolauncher.module.cameradetection.event.GestureControlEvent;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.CollectionManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcManager;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcViewImpl1;
import com.xiaomi.micolauncher.skills.music.vip.MusicVipActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import ui.view.GestureToast;

/* loaded from: classes3.dex */
public class PlayerChildFragmentV2 extends BaseFragment implements CollectionManager.CollectionListener, LocalPlayerManager.MetadataChangedCallback {
    private View a;
    private TextView b;
    private TextView c;
    private View d;
    private ImageView e;
    private ImageView f;
    private LrcViewImpl1 g;
    private ImageView h;
    private TextView i;
    private ImageView j;
    private Remote.Response.PlayingData k;
    private Remote.Response.TrackData l;
    private Disposable m;
    private AnimatorSet n;
    private int o = -1;
    private int p;

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaListChanged(Remote.Response.PlayerStatus playerStatus) {
    }

    public static PlayerChildFragmentV2 newInstance(Remote.Response.TrackData trackData, int i) {
        Bundle bundle = new Bundle();
        PlayerChildFragmentV2 playerChildFragmentV2 = new PlayerChildFragmentV2();
        if (trackData != null) {
            bundle.putSerializable("EXTRA_PLAYER_TRACK_DATA", trackData);
            bundle.putInt("EXTRA_MEDIA_TYPE", i);
        }
        playerChildFragmentV2.setArguments(bundle);
        return playerChildFragmentV2;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_player_child_ui, (ViewGroup) null);
        this.a = inflate.findViewById(R.id.player_simple_parent);
        this.b = (TextView) inflate.findViewById(R.id.song_name);
        this.c = (TextView) inflate.findViewById(R.id.artist);
        this.d = inflate.findViewById(R.id.cover_layout);
        this.e = (ImageView) inflate.findViewById(R.id.cover);
        this.f = (ImageView) inflate.findViewById(R.id.vinyl_cover);
        this.g = (LrcViewImpl1) inflate.findViewById(R.id.lrc_view);
        this.h = (ImageView) inflate.findViewById(R.id.love_btn);
        this.i = (TextView) inflate.findViewById(R.id.openVip);
        this.j = (ImageView) inflate.findViewById(R.id.ivQQLogo);
        a();
        return inflate;
    }

    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    private void a() {
        this.h.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.f, "rotation", 0.0f, 360.0f);
        ofFloat.setRepeatCount(-1);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.e, "rotation", 0.0f, 360.0f);
        ofFloat2.setRepeatCount(-1);
        this.n = new AnimatorSet();
        this.n.playTogether(ofFloat, ofFloat2);
        this.n.setDuration(RtspMediaSource.DEFAULT_TIMEOUT_MS);
        this.n.setInterpolator(linearInterpolator);
        RxViewHelp.debounceClicks(this.a, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerChildFragmentV2$GL-pAE4-Ey1qIdpHlzfXsurTDBc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerChildFragmentV2.this.b(obj);
            }
        });
        RxViewHelp.debounceClicks(this.h, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerChildFragmentV2$6IEIT0AATpThG4c8pSs4TTkUMtc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerChildFragmentV2.this.a(obj);
            }
        });
        this.g.setCanDrag(false);
        this.g.setAlignment(Layout.Alignment.ALIGN_NORMAL);
        if (Hardware.isBigScreen()) {
            this.g.setNormalLrcTextTypeface(Typeface.create(TypefaceUtil.FONT_WEIGHT_W500, 0));
            this.g.setHighlightLrcTextTypeface(Typeface.create(TypefaceUtil.FONT_WEIGHT_W600, 0));
        }
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.l = (Remote.Response.TrackData) arguments.getSerializable("EXTRA_PLAYER_TRACK_DATA");
            updateData(this.l, arguments.getInt("EXTRA_MEDIA_TYPE"));
        }
        String charSequence = this.i.getText().toString();
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.music_vip_open_green)), charSequence.indexOf(getString(R.string.open_vip_music_part)), charSequence.length(), 33);
        this.i.setText(spannableString);
        this.i.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerChildFragmentV2$FwOpP24QLCQMfAZPw6LMJa-gwlw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PlayerChildFragmentV2.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Object obj) throws Exception {
        if (getActivity() == null) {
            return;
        }
        if (MusicHelper.isPlayingSong(LocalPlayerManager.getInstance().getMediaType())) {
            ((PlayerActivityV2) getActivity()).setFragmentContainer(PlayerMainFragmentV2.newInstance(), 3);
        } else {
            ((PlayerActivityV2) getActivity()).setFragmentContainer(PlayerRadioFragmentV2.newInstance(), 3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        PlayerApi.toggleLove(!this.h.isSelected());
        if (this.h.isSelected()) {
            MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.CANCEL_COLLECT);
        } else {
            MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.COLLECT);
        }
        PlaybackTrackHelper.postFavoriteEvent(this.k, "click", !this.h.isSelected());
        ImageView imageView = this.h;
        imageView.setSelected(!imageView.isSelected());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(getContext(), MusicVipActivity.class));
    }

    public void updateData(Remote.Response.TrackData trackData, int i) {
        if (trackData != null) {
            this.b.setText(trackData.title);
            this.b.setSelected(true);
            this.c.setText(trackData.artist);
            a(trackData.cover, i);
            this.h.setSelected(false);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        MusicStatHelper.recordPlayerShow(MusicStatHelper.PlayerPage.LYRIC);
        if (isOnResume()) {
            registerToEventBusIfNot();
            this.o = -1;
            LocalPlayerManager.getInstance().registerCallback(this);
            CollectionManager.getInstance().registerCollectionListener(this);
            LrcViewImpl1 lrcViewImpl1 = this.g;
            if (lrcViewImpl1 != null) {
                lrcViewImpl1.showFadeIn(null);
                a(LocalPlayerManager.getInstance().getPlayingPlayerStatus(), false);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        unregisterToEventBusIfNeed();
        LocalPlayerManager.getInstance().unregisterCallback(this);
        CollectionManager.getInstance().unregisterCollectionListener(this);
        b();
        LrcViewImpl1 lrcViewImpl1 = this.g;
        if (lrcViewImpl1 != null) {
            lrcViewImpl1.reset();
        }
        AnimatorSet animatorSet = this.n;
        if (animatorSet != null) {
            animatorSet.pause();
        }
    }

    private void b() {
        Disposable disposable = this.m;
        if (disposable != null && !disposable.isDisposed()) {
            this.m.dispose();
        }
    }

    public View getCoverView() {
        return this.d;
    }

    private void a(Remote.Response.PlayerStatus playerStatus, boolean z) {
        if (playerStatus.play_song_detail != null && !isDetached() && isOnResume()) {
            PlayerSimpleFragmentV2 playerSimpleFragmentV2 = (PlayerSimpleFragmentV2) getParentFragment();
            if (playerSimpleFragmentV2 != null) {
                int currentIndex = playerSimpleFragmentV2.getCurrentIndex();
                int i = playerStatus.play_song_detail.screenExtend.index;
                L.player.d("PlayerChildFragmentV2 refreshUI screenExtend.index=%s currentIndex=%s isActive=%s", Integer.valueOf(i), Integer.valueOf(currentIndex), Boolean.valueOf(this.mActivated));
                boolean z2 = currentIndex == i;
                if (playerStatus.loop_type == LoopType.SHUFFLE.ordinal()) {
                    z2 = currentIndex == LocalPlayerManager.getInstance().getRandomPlayIndexByOriginalIndex(i);
                }
                if (z2 || z) {
                    this.k = playerStatus.play_song_detail;
                    a(playerStatus.play_song_detail);
                    if (!(playerStatus.play_song_detail == null || playerStatus.play_song_detail.screenExtend == null)) {
                        this.h.setEnabled(CollectionManager.canLovable(playerStatus.media_type));
                    }
                    if (this.h.isEnabled()) {
                        b(playerStatus);
                    }
                    a(playerStatus.play_song_detail.cover, playerStatus.media_type);
                    if (MusicHelper.isPlayingSong(playerStatus.media_type)) {
                        this.g.showLoadingLrc();
                        LrcManager.getInstance().loadLrc(playerStatus.play_song_detail, new $$Lambda$PlayerChildFragmentV2$CwujswAfc6LCNSWDG4TnCjyH9aw(this));
                        return;
                    }
                    return;
                }
                L.player.w("PlayerChildFragmentV2 refreshUI is not indexEquals");
                this.g.reset();
                this.g.showLoadingLrc();
            } else if (getActivity() != null) {
                ((PlayerActivityV2) getActivity()).setFragmentContainer(PlayerMainFragmentV2.newInstance(), 3);
            }
        }
    }

    private void a(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus.status != this.o) {
            if (!playerStatus.isPlayingStatus() || !isOnResume()) {
                if (playerStatus.isPauseStatus()) {
                    this.n.pause();
                }
            } else if (!this.n.isStarted()) {
                this.n.start();
            } else {
                this.n.resume();
            }
        }
        this.o = playerStatus.status;
    }

    private void a(Remote.Response.PlayingData playingData) {
        if (playingData != null) {
            this.b.setText(playingData.title);
            this.b.setSelected(true);
            this.c.setText(playingData.getSubTitle());
            TextView textView = this.i;
            int i = 8;
            if (textView != null) {
                textView.setVisibility(playingData.isPreview() ? 0 : 8);
            }
            ImageView imageView = this.j;
            if (!playingData.isPreview() && MusicHelper.isQQResource(playingData.cpOrigin)) {
                i = 0;
            }
            imageView.setVisibility(i);
        }
    }

    private void a(String str, int i) {
        if (this.p == 0) {
            this.p = getResources().getDimensionPixelSize(R.dimen.player_v2_simple_cover_size);
        }
        boolean isPlayingSong = MusicHelper.isPlayingSong(i);
        int i2 = R.drawable.player_cover_default;
        if (isPlayingSong) {
            this.f.setVisibility(0);
            this.e.getLayoutParams().width = this.p / 2;
            this.e.getLayoutParams().height = this.p / 2;
            RequestBuilder<Bitmap> asBitmap = Glide.with(this.e).asBitmap();
            RequestOptions defaultRequestOptions = GlideUtils.getDefaultRequestOptions();
            int i3 = this.p;
            RequestOptions placeholder = defaultRequestOptions.override(i3 / 2, i3 / 2).placeholder(R.drawable.player_cover);
            if (str == null) {
                i2 = R.drawable.player_cover;
            }
            asBitmap.apply((BaseRequestOptions<?>) placeholder.error(i2)).load(str).into(this.e);
            return;
        }
        this.f.setVisibility(8);
        this.e.getLayoutParams().width = this.p;
        this.e.getLayoutParams().height = this.p;
        RequestBuilder<Drawable> load = Glide.with(this.e).load(str);
        RequestOptions defaultRequestOptions2 = GlideUtils.getDefaultRequestOptions();
        int i4 = this.p;
        RequestOptions placeholder2 = defaultRequestOptions2.override(i4, i4).placeholder(R.drawable.player_cover);
        if (str == null) {
            i2 = R.drawable.player_cover;
        }
        load.apply((BaseRequestOptions<?>) placeholder2.error(i2)).into(this.e);
    }

    private void b(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus != null && playerStatus.play_song_detail != null) {
            b();
            this.m = CollectionManager.getInstance().loadLoveStatus(playerStatus.play_song_detail.audioID, playerStatus.play_song_detail.albumGlobalID, playerStatus.media_type).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerChildFragmentV2$_7HPeFXL27iQLS5NIsIQ64wiHSc
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    PlayerChildFragmentV2.this.a((Boolean) obj);
                }
            }, $$Lambda$PlayerChildFragmentV2$RJ0KB1oKb5TMNJvXEpkH5NfJHc.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Boolean bool) throws Exception {
        ImageView imageView = this.h;
        if (imageView != null) {
            imageView.setSelected(bool.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.player.e("PlayerChildFragmentV2 setLoveState error", th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, List<LrcRow> list) {
        if (this.g != null) {
            if (list == null || list.isEmpty()) {
                this.g.setTotalRowsToDraw(3);
                this.g.setShowPreLrcText(true);
                this.g.setEmptyText(str, getString(MusicHelper.isPlayingSong(LocalPlayerManager.getInstance().getMediaType()) ? R.string.music_no_lrc : R.string.music_lrc_playing_station));
                return;
            }
            this.g.setTotalRowsToDraw(2);
            this.g.setShowPreLrcText(false);
            this.g.setLrcRows(str, list);
            Remote.Response.PlayingData playingData = this.k;
            if (playingData != null && TextUtils.equals(playingData.audioID, this.g.getLrcMusicId())) {
                a(this.k.position, false, true);
            }
        }
    }

    private void a(long j, boolean z, boolean z2) {
        LrcViewImpl1 lrcViewImpl1 = this.g;
        if (lrcViewImpl1 != null && lrcViewImpl1.hasLrcRows() && isOnResume()) {
            this.g.seekTo((int) j, z, z2);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.CollectionManager.CollectionListener
    public void onCollectionChange(String str, final boolean z) {
        ImageView imageView = this.h;
        if (imageView != null) {
            imageView.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerChildFragmentV2$pcNdcGDFchnx9rZaOMsl-qGHcbc
                @Override // java.lang.Runnable
                public final void run() {
                    PlayerChildFragmentV2.this.a(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(boolean z) {
        ImageView imageView = this.h;
        if (imageView != null) {
            imageView.setSelected(z);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
        a(playerStatus, true);
        SpeechManager.getInstance().uiShow(QueryLatency.VideoPlayer);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus.play_song_detail != null) {
            LrcViewImpl1 lrcViewImpl1 = this.g;
            if (lrcViewImpl1 != null && lrcViewImpl1.hasNotUpdatedLrc(playerStatus.play_song_detail.getSimpleId())) {
                LrcManager.getInstance().loadLrc(playerStatus.play_song_detail, new $$Lambda$PlayerChildFragmentV2$CwujswAfc6LCNSWDG4TnCjyH9aw(this));
            }
            if (isOnResume() && TextUtils.equals(this.l.audioID, playerStatus.play_song_detail.audioID)) {
                if (playerStatus.isPlayingStatus()) {
                    a(playerStatus.play_song_detail.position, false, !(playerStatus.play_song_detail.position > 1));
                }
                a(playerStatus);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGestureControlEvent(GestureControlEvent gestureControlEvent) {
        if (SystemSetting.getKeyGestureControlEnable() && isOnResume()) {
            L.camera2.i("PlayerChildFragmentV2 receive gesture control event  %s", gestureControlEvent.gestureType);
            Remote.Response.PlayerStatus playingPlayerStatus = LocalPlayerManager.getInstance().getPlayingPlayerStatus();
            GestureInfoEvent.GestureType gestureType = gestureControlEvent.gestureType;
            if (GestureInfoEvent.GestureType.ThumbsUp == gestureType && !MusicHelper.isPlayRemote(playingPlayerStatus.media_type)) {
                if (CollectionManager.canLovable(playingPlayerStatus.media_type)) {
                    boolean isSelected = this.h.isSelected();
                    CollectionManager.getInstance().doSetLovable(!isSelected);
                    GestureToast.showGesture(getContext(), gestureType, getString(isSelected ? R.string.gesture_collection_cancel : R.string.gesture_collected));
                    return;
                }
                ToastUtil.showToast((int) R.string.gesture_collection_not_supported);
            }
        }
    }
}
