package com.xiaomi.micolauncher.skills.music.view_v2;

import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent;
import com.xiaomi.micolauncher.module.cameradetection.CameraDetectionController;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraSingleSwitchEvent;
import com.xiaomi.micolauncher.module.cameradetection.event.GestureControlEvent;
import com.xiaomi.micolauncher.skills.music.MusicProfile;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import ui.view.GestureToast;

/* loaded from: classes3.dex */
public class PlayerActivityV2 extends BaseActivity implements LocalPlayerManager.MetadataChangedCallback {
    public static final String EXTRA_PLAYER_STATUS = "EXTRA_PLAYER_STATUS";
    public static final int THEME_AUTO = 1;
    public static final int THEME_EFFECT_CLOSE = 3;
    public static final int THEME_EFFECT_OPEN = 2;
    public static final int THEME_NONE = 0;
    private static final long b = TimeUnit.MINUTES.toMillis(15);
    FrameLayout a;
    private boolean c;
    private int d = -1;
    private int e = -1;
    public int effectIndex;
    private Fragment f;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface THEME_TYPE {
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaListChanged(Remote.Response.PlayerStatus playerStatus) {
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_player_v2);
        this.a = (FrameLayout) findViewById(R.id.fragment_container);
        Remote.Response.PlayerStatus playingPlayerStatus = LocalPlayerManager.getInstance().getPlayingPlayerStatus();
        this.e = playingPlayerStatus.media_type;
        if (MusicHelper.mediaTypeUnknown(playingPlayerStatus.media_type) || MusicHelper.isPlayingSong(playingPlayerStatus.media_type) || MusicHelper.isPlayRemote(playingPlayerStatus.media_type)) {
            setFragmentContainer(PlayerMainFragmentV2.newInstance(), 0);
            if (SpeechManager.peekInstance() != null) {
                SpeechManager.getInstance().uiShow(QueryLatency.MusicPlayer);
            }
        } else {
            setFragmentContainer(PlayerRadioFragmentV2.newInstance(), 0);
            if (SpeechManager.peekInstance() != null) {
                SpeechManager.getInstance().uiShow(QueryLatency.RadioPlayer);
            }
        }
        CameraDetectionController.getManager().showSupportGestureNotification(this);
        LocalPlayerManager.getInstance().registerCallback(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        this.c = true;
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        this.c = false;
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        LocalPlayerManager.getInstance().unregisterCallback(this);
    }

    public void setFragmentContainer(Fragment fragment, int i) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, fragment);
        switch (i) {
            case 0:
                beginTransaction.setTransition(0);
                break;
            case 1:
                Fragment fragment2 = this.f;
                if (fragment2 != null && (fragment2 instanceof PlayerSimpleFragmentV2)) {
                    beginTransaction.setTransition(8194);
                    break;
                } else {
                    beginTransaction.setTransition(0);
                    break;
                }
                break;
            case 2:
                beginTransaction.setTransition(4097);
                break;
            case 3:
                beginTransaction.setTransition(8194);
                break;
        }
        this.f = fragment;
        try {
            beginTransaction.commitNowAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void a(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus != null && playerStatus.play_song_detail != null && playerStatus.media_type != this.e) {
            this.e = playerStatus.media_type;
            if (MusicHelper.isPlayRemote(playerStatus.media_type)) {
                L.player.d("PlayerActivityV2 refreshUI isPlayingBluetoothOrDLNA");
                LrcManager.getInstance().clearLrc();
                setFragmentContainer(PlayerMainFragmentV2.newInstance(), 1);
                SpeechManager.getInstance().uiShow(QueryLatency.MusicPlayer);
            } else if (MusicHelper.isPlayingSong(playerStatus.media_type)) {
                L.player.d("PlayerActivityV2 refreshUI PlayerMainFragmentV2");
                setFragmentContainer(PlayerMainFragmentV2.newInstance(), 1);
                SpeechManager.getInstance().uiShow(QueryLatency.MusicPlayer);
            } else {
                L.player.d("PlayerActivityV2 refreshUI will show PlayerRadioFragmentV2 mediaType=%d", Integer.valueOf(playerStatus.media_type));
                L.base.e("refreshUI PlayerRadioFragmentV2");
                setFragmentContainer(PlayerRadioFragmentV2.newInstance(), 1);
                SpeechManager.getInstance().uiShow(QueryLatency.RadioPlayer);
            }
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onPlayError(PlayerEvent.OnPlayError onPlayError) {
        L.player.e("PlayerActivityV2 OnPlayError");
        unregisterToEventBusIfNeed();
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPersonalPlayFinish(PlayerEvent.OnPlayFinish onPlayFinish) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicAuthInvalid(PlayerEvent.OnMusicAuthInvalid onMusicAuthInvalid) {
        MusicProfile.shareInstance().checkAuthStateAndShowView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGestureControlEvent(GestureControlEvent gestureControlEvent) {
        if (SystemSetting.getKeyGestureControlEnable() && this.c) {
            L.camera2.i("PlayerActivityV2 receive gesture control event  %s", gestureControlEvent.gestureType);
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

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
        a(playerStatus);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus) {
        if (this.c && playerStatus != null) {
            if (this.d != playerStatus.status) {
                L.player.i("PlayerActivityV2 detect player play status change from %d to %d", Integer.valueOf(this.d), Integer.valueOf(playerStatus.status));
                if (playerStatus.status == 0 && playerStatus.isMiPlay()) {
                    scheduleToClose(0L);
                } else if (playerStatus.status != 1) {
                    L.player.i("PlayerActivityV2 schedule to close player PlayerActivityV2 with %ds", Long.valueOf(b));
                    scheduleToClose(b);
                } else {
                    L.player.i("PlayerActivityV2 player is playing, remove schedule");
                    removeCloseScheduler();
                }
            }
            this.d = playerStatus.status;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCameraSingleSwitchEvent(CameraSingleSwitchEvent cameraSingleSwitchEvent) {
        if (cameraSingleSwitchEvent.open) {
            Remote.Response.PlayerStatus playingPlayerStatus = LocalPlayerManager.getInstance().getPlayingPlayerStatus();
            if (playingPlayerStatus != null) {
                a(playingPlayerStatus);
            } else {
                setFragmentContainer(PlayerMainFragmentV2.newInstance(), 1);
            }
        }
    }
}
