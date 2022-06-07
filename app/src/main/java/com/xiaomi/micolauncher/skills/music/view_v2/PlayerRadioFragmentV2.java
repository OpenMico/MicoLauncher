package com.xiaomi.micolauncher.skills.music.view_v2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.common.widget.PaletteShadowView;
import com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent;
import com.xiaomi.micolauncher.module.cameradetection.event.GestureControlEvent;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.OnPlayListItemClickListener;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.CollectionManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.controller.MetadataPreLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.view_v2.PlayerProgressbar;
import com.xiaomi.micolauncher.skills.music.vip.BookSinglePurchaseActivity;
import com.xiaomi.micolauncher.skills.music.vip.BookVipActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import ui.view.GestureToast;

/* loaded from: classes3.dex */
public class PlayerRadioFragmentV2 extends BaseFragment implements CollectionManager.CollectionListener, LocalPlayerManager.MetadataChangedCallback, PlayerProgressbar.OnPlayerProgressbarChangeListener {
    private TextView a;
    private TextView b;
    private PaletteShadowView c;
    private ImageView d;
    private ImageView e;
    private ImageView f;
    private PlayerProgressbar g;
    private ImageView h;
    private RecyclerView i;
    private TextView j;
    private View k;
    private SmartRefreshLayout l;
    private PlayerListAdapter m;
    private int n = -1;
    private boolean o;
    private CustomDialog p;
    public Remote.Response.PlayingData playingData;
    private Disposable q;

    @Override // com.xiaomi.micolauncher.skills.music.view_v2.PlayerProgressbar.OnPlayerProgressbarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public static PlayerRadioFragmentV2 newInstance() {
        return new PlayerRadioFragmentV2();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    @NonNull
    public BaseFragment.EventBusFragmentMode getEventBusRegisterMode() {
        return BaseFragment.EventBusFragmentMode.WHOLE_LIFE;
    }

    private boolean a() {
        return this.g == null;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_player_radio_v2, (ViewGroup) null);
        this.a = (TextView) inflate.findViewById(R.id.song_name);
        this.b = (TextView) inflate.findViewById(R.id.artist);
        this.c = (PaletteShadowView) inflate.findViewById(R.id.cover);
        this.d = (ImageView) inflate.findViewById(R.id.pre_btn);
        this.e = (ImageView) inflate.findViewById(R.id.play_btn);
        this.f = (ImageView) inflate.findViewById(R.id.next_btn);
        this.g = (PlayerProgressbar) inflate.findViewById(R.id.progress_bar);
        this.h = (ImageView) inflate.findViewById(R.id.love_btn);
        this.i = (RecyclerView) inflate.findViewById(R.id.player_list_rv);
        this.j = (TextView) inflate.findViewById(R.id.openVip);
        this.k = inflate.findViewById(R.id.audio_more);
        this.l = (SmartRefreshLayout) inflate.findViewById(R.id.refreshLayout);
        CollectionManager.getInstance().registerCollectionListener(this);
        LocalPlayerManager.getInstance().registerCallback(this);
        b();
        d();
        return inflate;
    }

    @SuppressLint({"CheckResult"})
    private void b() {
        this.f.setClickable(false);
        this.d.setClickable(false);
        this.c.setImageResource(R.drawable.player_cover);
        RxViewHelp.debounceClicks(this.d).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$Iek7FVL0tUTll3pxVzHGK-mOBMk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerRadioFragmentV2.this.e(obj);
            }
        });
        RxViewHelp.debounceClicks(this.e, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$3vsrFx2vP1vubcuNU_62N2sPnBY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerRadioFragmentV2.this.d(obj);
            }
        });
        RxViewHelp.debounceClicks(this.f).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$QwcXrlCLpESssWb9kKDMOVuZTdE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerRadioFragmentV2.this.c(obj);
            }
        });
        RxViewHelp.debounceClicks(this.h).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$2GHixDxcluD9r5p8Ma6vz-LgKew
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerRadioFragmentV2.this.b(obj);
            }
        });
        this.g.setPlayerProgressbarChangeListener(this);
        c();
        String charSequence = this.j.getText().toString();
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.music_vip_open_pink, null)), charSequence.indexOf(getString(R.string.open_vip_book_part)), charSequence.length(), 33);
        this.j.setText(spannableString);
        this.j.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$GjyJXVCfG9XNf87UTehAy01WPm8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PlayerRadioFragmentV2.this.c(view);
            }
        });
        this.m.setOnItemClickListener(new OnPlayListItemClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$SdqE8XyEyEmOUgtxBm8BQQ1b3nk
            @Override // com.xiaomi.micolauncher.skills.music.OnPlayListItemClickListener
            public final void onItemClick(Remote.Response.TrackData trackData, int i, boolean z) {
                PlayerRadioFragmentV2.this.a(trackData, i, z);
            }
        });
        RxViewHelp.debounceClicks(this.k).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$r-EbpKVUF7uneG4G30BBg1YsF2I
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerRadioFragmentV2.this.a(obj);
            }
        });
        this.l.setEnableOverScrollDrag(true);
        this.l.setEnableOverScrollBounce(true);
        this.l.setEnableAutoLoadMore(false);
        this.l.setFooterTriggerRate(0.8f);
        this.l.setEnableLoadMore(false);
        this.l.setEnableRefresh(false);
        this.l.setRefreshFooter(new PlayListRefreshFooter(getContext()));
        this.l.setRefreshHeader(new PlayListRefreshHeader(getContext()));
        this.l.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerRadioFragmentV2.1
            @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                PlayerRadioFragmentV2.this.g();
            }
        });
        this.l.setOnRefreshListener(new OnRefreshListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerRadioFragmentV2.2
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                PlayerRadioFragmentV2.this.h();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(Object obj) throws Exception {
        if ("2".equals(this.d.getTag())) {
            PlayerApi.seekTo(this.g.getNowPosition() - TimeUnit.SECONDS.toMillis(15L));
            return;
        }
        PlayerApi.prev();
        MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.PREV);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(Object obj) throws Exception {
        PlayerApi.playOrPause();
        if ("0".equals(this.e.getTag())) {
            MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.PAUSE);
        } else {
            MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.PLAY);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Object obj) throws Exception {
        if (Commands.ResolutionValues.BITSTREAM_BLUE_HIGH.equals(this.f.getTag())) {
            PlayerApi.seekTo(this.g.getNowPosition() + TimeUnit.SECONDS.toMillis(15L));
            return;
        }
        PlayerApi.next();
        MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.NEXT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Object obj) throws Exception {
        PlayerApi.toggleLove(!this.h.isSelected());
        if (this.h.isSelected()) {
            MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.CANCEL_COLLECT);
        } else {
            MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.COLLECT);
        }
        PlaybackTrackHelper.postFavoriteEvent(this.playingData, "click", !this.h.isSelected());
        ImageView imageView = this.h;
        imageView.setSelected(!imageView.isSelected());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        Remote.Response.PlayerStatus playerStatus = LocalPlayerManager.getInstance().getPlayerStatus();
        Intent intent = new Intent(getContext(), BookVipActivity.class);
        intent.putExtra(PlayerActivityV2.EXTRA_PLAYER_STATUS, playerStatus.play_song_detail);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Remote.Response.TrackData trackData, int i, boolean z) {
        if (trackData.isStationItemUnPay()) {
            showSingleBookVipDialog(getContext(), trackData, i);
        } else if (trackData.isAlbumUnPay()) {
            showBookVipDialog(getContext(), trackData);
        } else {
            if (!z) {
                PlayerApi.playByIndex(i);
                PlaybackTrackHelper.postPlayListClick(trackData, i);
            }
            this.f.setEnabled(true);
            this.d.setEnabled(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        startActivity(new Intent(getContext(), AudioBookPlayListActivity.class));
        getActivity().overridePendingTransition(0, 0);
    }

    private void c() {
        if (this.m == null) {
            this.m = new PlayerListAdapter();
            this.i.setAdapter(this.m);
            this.i.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    private void d() {
        Remote.Response.PlayerStatus playerStatus = LocalPlayerManager.getInstance().getPlayerStatus();
        if (playerStatus != null) {
            a(playerStatus, false);
            d(playerStatus);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (ContainerUtil.hasData(LocalPlayerManager.getInstance().getStations().getItems())) {
            this.k.setVisibility(0);
            if (!AudioBookPlayListActivity.listHasShow) {
                startActivity(new Intent(getContext(), AudioBookPlayListActivity.class));
                getActivity().overridePendingTransition(0, 0);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        dismissVipDialog();
        CollectionManager.getInstance().unregisterCollectionListener(this);
        LocalPlayerManager.getInstance().unregisterCallback(this);
        e();
        this.i.setAdapter(null);
    }

    private void e() {
        Disposable disposable = this.q;
        if (disposable != null && !disposable.isDisposed()) {
            this.q.dispose();
        }
    }

    private void a(Remote.Response.PlayerStatus playerStatus, boolean z) {
        if (playerStatus != null && playerStatus.play_song_detail != null && this.a != null) {
            if (!z || LocalPlayerManager.isResourceChange(playerStatus, this.playingData)) {
                L.player.i("PlayerRadioFragmentV2 refreshUI mediaType=%d", Integer.valueOf(playerStatus.media_type));
                a(playerStatus.play_song_detail);
                a(playerStatus);
                a(playerStatus.play_song_detail.cover);
                b(playerStatus, false);
                b(playerStatus);
                c(playerStatus);
                this.playingData = playerStatus.play_song_detail;
                f();
                return;
            }
            L.player.i("PlayerRadioFragmentV2 refreshUI needResourceChange but not");
        }
    }

    private void a(Remote.Response.PlayerStatus playerStatus) {
        boolean z = playerStatus.play_song_detail.isPlayingFM() || playerStatus.media_type == 15;
        L.player.d("PlayerRadioFragmentV2 setUIByMediaType screenExtend.mediaType=%d isFM=%s", Integer.valueOf(playerStatus.play_song_detail.screenExtend.mediaType), Boolean.valueOf(z));
        if (z) {
            this.g.setSeekBarEnable(false);
            this.d.setImageResource(R.drawable.icon_black_prev);
            this.d.setTag("3");
            this.f.setImageResource(R.drawable.icon_black_next);
            this.f.setTag(Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND);
            return;
        }
        this.g.setSeekBarEnable(true);
        this.d.setImageResource(R.drawable.prev_15s);
        this.d.setTag("2");
        this.f.setImageResource(R.drawable.next_15s);
        this.f.setTag(Commands.ResolutionValues.BITSTREAM_BLUE_HIGH);
    }

    private void b(Remote.Response.PlayerStatus playerStatus, boolean z) {
        Remote.Response.PlayingData playingData = playerStatus.play_song_detail;
        int i = R.drawable.icon_black_pause;
        if (playingData == null) {
            ImageView imageView = this.e;
            if (!z) {
                i = R.drawable.icon_black_play;
            }
            imageView.setImageResource(i);
            this.e.setTag(z ? "0" : "1");
        } else if (a(playerStatus.status)) {
            if (z) {
                this.e.setImageResource(R.drawable.icon_black_pause);
                this.e.setTag("0");
            } else {
                this.e.setImageResource(R.drawable.icon_black_play);
                this.e.setTag("1");
            }
            this.n = playerStatus.status;
        }
        PlayerProgressbar playerProgressbar = this.g;
        if (playerProgressbar != null) {
            playerProgressbar.setPlayerStatus(playerStatus);
        }
    }

    private void b(Remote.Response.PlayerStatus playerStatus) {
        if (MusicHelper.isPlayRemote(playerStatus.media_type)) {
            this.h.setEnabled(false);
        } else if (!(playerStatus.play_song_detail == null || playerStatus.play_song_detail.screenExtend == null)) {
            this.h.setEnabled(CollectionManager.canLovable(playerStatus.media_type));
        }
        if (this.h.isEnabled()) {
            e(playerStatus);
        }
    }

    private void a(Remote.Response.PlayingData playingData) {
        if (playingData != null) {
            String str = playingData.albumName;
            String subTitle = playingData.getSubTitle();
            if (TextUtils.isEmpty(str) || str.equals(subTitle)) {
                str = playingData.title;
            }
            this.a.setText(str);
            this.a.setSelected(true);
            this.b.setText(playingData.getSubTitle());
        }
    }

    private void c(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus.play_song_detail.isPreview()) {
            this.j.setVisibility(0);
            this.i.getLayoutParams().height = (int) (getResources().getDimensionPixelOffset(R.dimen.player_v2_radio_list_height) * 0.9d);
            return;
        }
        this.j.setVisibility(8);
        this.i.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.player_v2_radio_list_height);
    }

    private void d(Remote.Response.PlayerStatus playerStatus) {
        if (this.i != null && playerStatus.extra_track_list != null && this.i.getVisibility() == 0) {
            int i = playerStatus.play_song_detail == null ? -1 : playerStatus.play_song_detail.screenExtend.index;
            this.m.refresh(playerStatus, i);
            if (this.m.getItemCount() > i) {
                this.i.scrollToPosition(i);
            }
        }
    }

    private boolean a(int i) {
        if (i != this.n) {
            return true;
        }
        if (i != 1 || !"0".equals(this.e.getTag())) {
            return i != 2 || !"1".equals(this.e.getTag());
        }
        return false;
    }

    private void a(String str) {
        Glide.with(this.c).load(str).into(this.c);
    }

    private void e(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus != null && playerStatus.play_song_detail != null) {
            e();
            this.q = CollectionManager.getInstance().loadLoveStatus(playerStatus.play_song_detail.audioID, playerStatus.play_song_detail.albumGlobalID, playerStatus.media_type).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$YtKq9UjBD6UtpFLKSoRByfkxGHE
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    PlayerRadioFragmentV2.this.a((Boolean) obj);
                }
            }, $$Lambda$PlayerRadioFragmentV2$2e0N8J7oEZdNzTkXmhX0cglUDDc.INSTANCE);
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
        L.player.e("PlayerRadioFragmentV2 setLoveState error %s", th);
    }

    @Override // com.xiaomi.micolauncher.skills.music.view_v2.PlayerProgressbar.OnPlayerProgressbarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        Remote.Response.PlayingData playingData = this.playingData;
        if (playingData != null) {
            PlayerApi.seekTo((playingData.duration * seekBar.getProgress()) / 100);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.CollectionManager.CollectionListener
    public void onCollectionChange(String str, final boolean z) {
        ImageView imageView = this.h;
        if (imageView != null) {
            imageView.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$zuWztqBZD0wAGRZXGyOLW7IWBbI
                @Override // java.lang.Runnable
                public final void run() {
                    PlayerRadioFragmentV2.this.a(z);
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
        if (!a()) {
            a(playerStatus, true);
            d(playerStatus);
            SpeechManager.getInstance().uiShow(QueryLatency.RadioPlayer);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaListChanged(Remote.Response.PlayerStatus playerStatus) {
        if (!a()) {
            boolean isEmpty = ContainerUtil.isEmpty(playerStatus.extra_track_list);
            this.f.setEnabled(!isEmpty);
            this.d.setEnabled(!isEmpty);
            d(playerStatus);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus) {
        if (!a()) {
            b(playerStatus, playerStatus.isPlayingStatus());
            this.playingData = playerStatus.play_song_detail;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnPlayIndexChange(PlayerEvent.OnPlayIndexChange onPlayIndexChange) {
        Remote.Response.PlayerStatus playerStatus = LocalPlayerManager.getInstance().getPlayerStatus();
        if (playerStatus != null && playerStatus.extra_track_list != null && !ContainerUtil.isOutOfBound(onPlayIndexChange.index, playerStatus.extra_track_list)) {
            Remote.Response.TrackData trackData = playerStatus.extra_track_list.get(onPlayIndexChange.index);
            if (trackData.isAlbumUnPay()) {
                showBookVipDialog(getContext(), trackData);
                this.f.setEnabled(false);
                this.d.setEnabled(false);
            } else if (trackData.isStationItemUnPay()) {
                showSingleBookVipDialog(getContext(), trackData, onPlayIndexChange.index);
                this.f.setEnabled(false);
                this.d.setEnabled(false);
            } else {
                dismissVipDialog();
                this.f.setEnabled(true);
                this.d.setEnabled(true);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGestureControlEvent(GestureControlEvent gestureControlEvent) {
        if (SystemSetting.getKeyGestureControlEnable() && isOnResume()) {
            L.camera2.i("PlayerRadioFragmentV2 receive gesture control event  %s", gestureControlEvent.gestureType);
            GestureInfoEvent.GestureType gestureType = gestureControlEvent.gestureType;
            Remote.Response.PlayerStatus playingPlayerStatus = LocalPlayerManager.getInstance().getPlayingPlayerStatus();
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

    public void showBookVipDialog(final Context context, final Remote.Response.TrackData trackData) {
        if (!this.o) {
            this.p = new CustomDialog.Builder().setTitleResId(R.string.book_vip).setMessageResId(R.string.book_vip_tip).setPositiveResId(R.string.book_vip_ok).setNegativeResId(R.string.book_vip_no).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$as94qm8GI1X88HskTzgAxpaDivA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerRadioFragmentV2.this.a(context, trackData, view);
                }
            }).setCanceledOnTouchOutside(false).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$1fUprXacnZ0PdJPxriltltTl2eM
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerRadioFragmentV2.this.b(view);
                }
            }).setHasNegativeButton(true).build();
            this.p.show();
            this.o = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, Remote.Response.TrackData trackData, View view) {
        this.o = false;
        Intent intent = new Intent(context, BookVipActivity.class);
        intent.putExtra(PlayerActivityV2.EXTRA_PLAYER_STATUS, Remote.Response.PlayingData.from(trackData));
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        this.o = false;
    }

    public void showSingleBookVipDialog(final Context context, final Remote.Response.TrackData trackData, final int i) {
        if (!this.o) {
            this.p = new CustomDialog.Builder().setTitleResId(R.string.book_vip).setMessageResId(R.string.book_vip_tip).setPositiveResId(R.string.book_vip_ok).setNegativeResId(R.string.book_vip_no).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$wNjveGGRfn_yH-ziIdzOxTF4nIE
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerRadioFragmentV2.this.a(context, trackData, i, view);
                }
            }).setCanceledOnTouchOutside(false).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerRadioFragmentV2$2ZZWdAXKputT960SFk-4cKciLSs
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerRadioFragmentV2.this.a(view);
                }
            }).setHasNegativeButton(true).build();
            this.p.show();
            this.o = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, Remote.Response.TrackData trackData, int i, View view) {
        this.o = false;
        Intent intent = new Intent(context, BookSinglePurchaseActivity.class);
        intent.putExtra(BookSinglePurchaseActivity.EXTRA_BOOK_DATA, trackData);
        intent.putExtra(BookSinglePurchaseActivity.EXTRA_BOOK_INDEX, i);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        this.o = false;
    }

    public void dismissVipDialog() {
        CustomDialog customDialog = this.p;
        if (customDialog != null) {
            customDialog.dismiss();
            this.o = false;
        }
    }

    private void f() {
        boolean canLoadMore = LocalPlayerManager.getInstance().canLoadMore();
        boolean canPreLoadMore = LocalPlayerManager.getInstance().canPreLoadMore();
        SmartRefreshLayout smartRefreshLayout = this.l;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setEnableLoadMore(canLoadMore);
            this.l.setEnableRefresh(canPreLoadMore);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        if (!LocalPlayerManager.getInstance().loadMore(new MetadataLoadMoreCallback() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerRadioFragmentV2.3
            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback
            public void onLoadMoreStart() {
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback
            public void onLoadMoreComplete(boolean z) {
                L.player.d("loadMore onLoadMoreComplete=%s", Boolean.valueOf(z));
                if (PlayerRadioFragmentV2.this.l != null) {
                    PlayerRadioFragmentV2.this.l.finishLoadMore();
                }
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback
            public void onLoadMoreEnd() {
                L.player.d("loadMore onLoadMoreEnd");
                if (PlayerRadioFragmentV2.this.l != null) {
                    PlayerRadioFragmentV2.this.l.finishLoadMore();
                    PlayerRadioFragmentV2.this.l.setEnableLoadMore(false);
                }
            }
        })) {
            this.l.finishLoadMore();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        if (!LocalPlayerManager.getInstance().preLoadMore(new MetadataPreLoadMoreCallback() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerRadioFragmentV2.4
            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataPreLoadMoreCallback
            public void onPreLoadMoreStart() {
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataPreLoadMoreCallback
            public void onPreLoadMoreComplete(boolean z) {
                if (PlayerRadioFragmentV2.this.l != null) {
                    PlayerRadioFragmentV2.this.l.finishRefresh();
                }
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataPreLoadMoreCallback
            public void onPreLoadMoreEnd() {
                if (PlayerRadioFragmentV2.this.l != null) {
                    PlayerRadioFragmentV2.this.l.finishRefresh();
                    PlayerRadioFragmentV2.this.l.setEnableRefresh(false);
                }
            }
        })) {
            this.l.finishRefresh();
        }
    }
}
