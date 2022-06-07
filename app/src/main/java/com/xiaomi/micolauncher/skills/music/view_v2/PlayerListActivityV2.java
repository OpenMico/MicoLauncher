package com.xiaomi.micolauncher.skills.music.view_v2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent;
import com.xiaomi.micolauncher.module.cameradetection.event.GestureControlEvent;
import com.xiaomi.micolauncher.skills.music.OnPlayListItemClickListener;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.controller.MetadataPreLoadMoreCallback;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.vip.MusicVipActivity;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import ui.view.GestureToast;

/* loaded from: classes3.dex */
public class PlayerListActivityV2 extends BaseActivity implements LocalPlayerManager.MetadataChangedCallback {
    private View a;
    private RecyclerView b;
    private SmartRefreshLayout c;
    private PlayerListAdapter d;
    private boolean e;
    private CustomDialog f;
    private boolean g = true;

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_player_list_v2);
        this.a = findViewById(R.id.player_list_parent);
        this.b = (RecyclerView) findViewById(R.id.player_list_rv);
        this.c = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        this.d = new PlayerListAdapter();
        this.b.setAdapter(this.d);
        this.b.setLayoutManager(new LinearLayoutManager(this));
        this.d.setOnItemClickListener(new OnPlayListItemClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerListActivityV2$YhxEs_QiHr6g78sIK0nHWJepgBc
            @Override // com.xiaomi.micolauncher.skills.music.OnPlayListItemClickListener
            public final void onItemClick(Remote.Response.TrackData trackData, int i, boolean z) {
                PlayerListActivityV2.this.a(trackData, i, z);
            }
        });
        registerToEventBusIfNot();
        setDefaultScheduleDuration();
        a(LocalPlayerManager.getInstance().getPlayerStatus());
        b();
        PlayListRefreshFooter playListRefreshFooter = new PlayListRefreshFooter(this);
        playListRefreshFooter.setMargins(0, DisplayUtils.dip2px((Activity) this, 40.0f), 0, DisplayUtils.dip2px((Activity) this, 80.0f));
        this.c.setRefreshFooter(playListRefreshFooter);
        this.c.setFooterTriggerRate(0.65f);
        this.c.setEnableAutoLoadMore(false);
        this.c.setEnableOverScrollDrag(true);
        this.c.setEnableOverScrollBounce(true);
        this.c.setRefreshHeader(new PlayListRefreshHeader(this));
        this.c.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerListActivityV2.1
            @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                PlayerListActivityV2.this.c();
            }
        });
        this.c.setOnRefreshListener(new OnRefreshListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerListActivityV2.2
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                PlayerListActivityV2.this.d();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Remote.Response.TrackData trackData, int i, boolean z) {
        if (trackData.isSongPreview()) {
            a((Context) this);
        }
        if (!z) {
            PlayerApi.playByIndex(i);
            PlaybackTrackHelper.postPlayListClick(trackData, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        LocalPlayerManager.getInstance().registerCallback(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        LocalPlayerManager.getInstance().unregisterCallback(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        a();
        unregisterToEventBusIfNeed();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0 || motionEvent.getX() >= getResources().getDimensionPixelOffset(R.dimen.music_player_list_margin_parent_left)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        finish();
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventClosePlayerListActivity(PlayerEvent.ClosePlayerListActivityV2Event closePlayerListActivityV2Event) {
        L.player.d("PlayerListActivityV2 onEventClosePlayerListActivity");
        finish();
    }

    private void a(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus != null && playerStatus.extra_track_list != null) {
            int i = playerStatus.play_song_detail == null ? -1 : playerStatus.play_song_detail.screenExtend.index;
            this.d.refresh(playerStatus, i);
            if (playerStatus.isManualLoadMore || this.d.getItemCount() <= i) {
                return;
            }
            if (this.g) {
                this.b.scrollToPosition(i);
                this.g = false;
                return;
            }
            this.b.smoothScrollToPosition(i);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
        a(playerStatus);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaListChanged(Remote.Response.PlayerStatus playerStatus) {
        a(playerStatus);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnPlayIndexChange(PlayerEvent.OnPlayIndexChange onPlayIndexChange) {
        Remote.Response.PlayerStatus playingPlayerStatus = LocalPlayerManager.getInstance().getPlayingPlayerStatus();
        if (playingPlayerStatus != null && playingPlayerStatus.extra_track_list != null && !ContainerUtil.isOutOfBound(onPlayIndexChange.index, playingPlayerStatus.extra_track_list) && !playingPlayerStatus.extra_track_list.get(onPlayIndexChange.index).isSongPreview()) {
            a();
        }
    }

    private void a(final Context context) {
        if (!this.e) {
            L.pay.i("show vip dialog");
            this.f = new CustomDialog.Builder().setTitleResId(R.string.qq_vip).setMessageResId(R.string.qq_vip_tip).setPositiveResId(R.string.qq_vip_ok).setNegativeResId(R.string.qq_vip_no).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerListActivityV2$821S6sByGIyPxUmHcyeuRey5O7U
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerListActivityV2.this.a(context, view);
                }
            }).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerListActivityV2$_r5Po6rEmCGmN0ygNOII6KpObFg
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerListActivityV2.this.a(view);
                }
            }).setCanceledOnTouchOutside(false).setHasNegativeButton(true).build();
            this.f.show();
            this.e = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, View view) {
        this.e = false;
        ActivityLifeCycleManager.startActivityQuietly(new Intent(context, MusicVipActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        this.e = false;
    }

    private void a() {
        if (this.f != null) {
            L.pay.i("dismiss vip dialog");
            this.f.dismiss();
            this.e = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGestureControlEvent(GestureControlEvent gestureControlEvent) {
        if (SystemSetting.getKeyGestureControlEnable() && isMicoActivityResumed()) {
            L.camera2.i("PlayerListActivityV2 receive gesture control event  %s", gestureControlEvent.gestureType);
            GestureInfoEvent.GestureType gestureType = gestureControlEvent.gestureType;
            switch (gestureType) {
                case OK:
                    PlayerApi.play();
                    GestureToast.showGesture(this, gestureType, getString(R.string.gesture_resume));
                    return;
                case Stop:
                    GestureToast.showGesture(this, gestureType, getString(R.string.gesture_pause));
                    PlayerApi.pause();
                    return;
                case FastForward:
                    PlayerApi.next();
                    GestureToast.showGesture(this, gestureType, getString(R.string.gesture_next));
                    return;
                case FastBackward:
                    PlayerApi.prev();
                    GestureToast.showGesture(this, gestureType, getString(R.string.gesture_prev));
                    return;
                default:
                    return;
            }
        }
    }

    private void b() {
        boolean canLoadMore = LocalPlayerManager.getInstance().canLoadMore();
        boolean canPreLoadMore = LocalPlayerManager.getInstance().canPreLoadMore();
        SmartRefreshLayout smartRefreshLayout = this.c;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setEnableLoadMore(canLoadMore);
            this.c.setEnableRefresh(canPreLoadMore);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (!LocalPlayerManager.getInstance().loadMore(new MetadataLoadMoreCallback() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerListActivityV2.3
            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback
            public void onLoadMoreStart() {
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback
            public void onLoadMoreComplete(boolean z) {
                L.player.d("loadMore onLoadMoreComplete=%s", Boolean.valueOf(z));
                if (PlayerListActivityV2.this.c != null) {
                    PlayerListActivityV2.this.c.finishLoadMore();
                }
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataLoadMoreCallback
            public void onLoadMoreEnd() {
                L.player.d("loadMore onLoadMoreEnd=%s");
                if (PlayerListActivityV2.this.c != null) {
                    PlayerListActivityV2.this.c.finishLoadMoreWithNoMoreData();
                    PlayerListActivityV2.this.c.setEnableLoadMore(false);
                }
            }
        })) {
            this.c.finishLoadMore();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        if (!LocalPlayerManager.getInstance().preLoadMore(new MetadataPreLoadMoreCallback() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerListActivityV2.4
            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataPreLoadMoreCallback
            public void onPreLoadMoreStart() {
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataPreLoadMoreCallback
            public void onPreLoadMoreComplete(boolean z) {
                if (PlayerListActivityV2.this.c != null) {
                    PlayerListActivityV2.this.c.finishRefresh();
                }
            }

            @Override // com.xiaomi.micolauncher.skills.music.controller.MetadataPreLoadMoreCallback
            public void onPreLoadMoreEnd() {
                if (PlayerListActivityV2.this.c != null) {
                    PlayerListActivityV2.this.c.finishRefresh();
                    PlayerListActivityV2.this.c.setEnableRefresh(false);
                }
            }
        })) {
            this.c.finishRefresh();
        }
    }
}
