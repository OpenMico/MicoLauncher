package com.xiaomi.micolauncher.skills.music.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Template;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.IMicoRemoteService;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.VolumeLimitInPowerSaveModeEvent;
import com.xiaomi.micolauncher.common.media.MicoMediaEvent;
import com.xiaomi.micolauncher.common.media.RelayMediaEvent;
import com.xiaomi.micolauncher.common.media.RelayPlayEvent;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControl;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.module.setting.utils.SystemVolume;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.childmode.bean.StopAudioEvent;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.mediacontroller.BTMediaControllerProxy;
import com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy;
import com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MicoMediaControllerProxy;
import com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy;
import com.xiaomi.micolauncher.skills.music.controller.playlist.PlaylistController;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.service.AudioPlayerService;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import com.xiaomi.micolauncher.skills.voip.model.VoipStopEvent;
import com.xiaomi.miplay.audioclient.tv.TVMediaMetaData;
import com.xiaomi.smarthome.core.entity.MediaType;
import com.xiaomi.smarthome.core.miot.MediaControllerEvent;
import com.xiaomi.smarthome.core.utils.BitmapUtils;
import java.util.List;
import org.fourthline.cling.support.model.MediaInfo;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class MediaSessionController {
    public static final String COM_ANDROID_BLUETOOTH = "com.android.bluetooth";
    public static final int PAUSE_AFTER_FINISH = -2;
    private static MediaSessionController c;
    private Context a;
    private MicoMediaControllerProxy d;
    private BTMediaControllerProxy e;
    private MediaControllerProxy f;
    private MediaControllerProxy g;
    private MiplayMediaControllerProxy h;
    private PlaylistController i;
    private long j;
    private Handler k;
    private IMicoRemoteService l;
    private int b = LoopType.LIST_LOOP.ordinal();
    private ServiceConnection m = new ServiceConnection() { // from class: com.xiaomi.micolauncher.skills.music.controller.MediaSessionController.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            L.player.d("remoteServiceConnection.onServiceConnected");
            MediaSessionController.this.l = IMicoRemoteService.Stub.asInterface(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            L.player.w("remoteServiceConnection.onServiceDisconnected");
            MediaSessionController.this.l = null;
        }
    };
    public MiplayMediaControllerProxy.Callback miPlayCallback = new MiplayMediaControllerProxy.Callback() { // from class: com.xiaomi.micolauncher.skills.music.controller.MediaSessionController.5
        private Remote.Response.PlayerStatus c;
        private boolean e;
        private boolean f;
        private boolean g;
        private TVMediaMetaData h;
        private int d = 0;
        FakePlayer a = new FakePlayer(AudioSource.AUDIO_SOURCE_MUSIC_MI_PLAY) { // from class: com.xiaomi.micolauncher.skills.music.controller.MediaSessionController.5.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
            public void postStart() {
                L.player.d("MiplayMediaControllerProxy postStart");
                AnonymousClass5.this.e = false;
                AnonymousClass5.this.f = false;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
            public void postStop(AudioSource audioSource) {
                L.player.d("MiplayMediaControllerProxy postStop=%s", audioSource.name());
                if (MediaSessionController.this.h.getPlayStatus() == 1) {
                    AnonymousClass5.this.e = false;
                }
                if (MediaSessionController.this.h.isActive()) {
                    MediaSessionController.this.h.disConnect();
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void suspend(AudioSource audioSource) {
                if (MediaSessionController.this.h != null) {
                    L.player.d("MiplayMediaControllerProxy suspend=%s", audioSource.name());
                    if (MediaSessionController.this.h.getPlayStatus() == 1) {
                        AnonymousClass5.this.e = true;
                        MediaSessionController.this.h.pauseByFocus();
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void resume(AudioSource audioSource) {
                L.player.d("MiplayMediaControllerProxy resume, autoPause=%s", audioSource.name());
                if (MediaSessionController.this.h != null) {
                    if (AnonymousClass5.this.e) {
                        MediaSessionController.this.h.playByFocus();
                    }
                    AnonymousClass5.this.e = false;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void background(AudioSource audioSource) {
                L.player.d("MiplayMediaControllerProxy background, AudioSource=%s", audioSource.name());
                if (MediaSessionController.this.h != null && MediaSessionController.this.h.getPlayStatus() == 1) {
                    AnonymousClass5.this.e = true;
                    MediaSessionController.this.h.pauseByFocus();
                }
            }

            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            protected void foreground(AudioSource audioSource) {
                L.player.d("MiplayMediaControllerProxy foreground, AudioSource=%s", audioSource.name());
                if (MediaSessionController.this.h != null) {
                    if (AnonymousClass5.this.e) {
                        MediaSessionController.this.h.playByFocus();
                    }
                    AnonymousClass5.this.e = false;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void forceStop(AudioSource audioSource) {
                L.player.d("MiplayMediaControllerProxy forceStop");
                super.forceStop(audioSource);
                AnonymousClass5.this.f = true;
            }

            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            protected void playbackControl(PlaybackControllerInfo playbackControllerInfo) {
                L.player.d("Miplay PlaybackControl=%s", playbackControllerInfo);
                if (playbackControllerInfo != null) {
                    switch (AnonymousClass6.b[playbackControllerInfo.playbackControl.ordinal()]) {
                        case 1:
                        case 2:
                            PlayerApi.play();
                            return;
                        case 3:
                        case 4:
                            PlayerApi.pause();
                            return;
                        case 5:
                            PlayerApi.prev();
                            return;
                        case 6:
                            PlayerApi.next();
                            return;
                        default:
                            return;
                    }
                }
            }
        };

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onCmdSessionError() {
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onCmdSessionSuccess() {
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onDisconnect() {
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onPlayByCmd() {
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onVolumeChange(int i) {
        }

        private boolean a(int i) {
            return i == 2 ? this.d == 0 : this.d != i;
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onVoipStop() {
            L.player.d("autoPauseByVoip %b %b", Boolean.valueOf(this.g), Boolean.valueOf(MediaSessionController.this.h.hasDataToPlay()));
            if (this.g) {
                this.g = false;
                if (!MediaSessionController.this.isBTPlaying() && !MediaSessionController.this.isMicoPlaying() && MediaSessionController.this.h.hasDataToPlay() && MediaSessionController.this.h.getPlayStatus() == 2) {
                    MediaSessionController.this.h.playByFocus();
                }
            }
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onRelayMediaData(TVMediaMetaData tVMediaMetaData) {
            if (!a(tVMediaMetaData)) {
                String string = MicoApplication.getApp().getString(R.string.music_source_from, new Object[]{tVMediaMetaData.getSourceName()});
                Bitmap bitmap = null;
                if (tVMediaMetaData.getArt() != null) {
                    bitmap = CommonUtils.compressBitmapWithNoDistortion(tVMediaMetaData.getArt(), 120, 120);
                }
                this.h = tVMediaMetaData;
                EventBusRegistry.getEventBus().post(new RelayMediaEvent(MediaType.RELAY.name(), tVMediaMetaData.getTitle(), tVMediaMetaData.getArtist(), bitmap, string, tVMediaMetaData.getDeviceID(), tVMediaMetaData.getDeviceState(), tVMediaMetaData.getSourceBtMac()));
            }
        }

        private boolean a(TVMediaMetaData tVMediaMetaData, TVMediaMetaData tVMediaMetaData2) {
            return !BitmapUtils.compareBitmapByPixel(tVMediaMetaData.getArt(), tVMediaMetaData2.getArt());
        }

        private boolean a(TVMediaMetaData tVMediaMetaData) {
            return this.h != null && TextUtils.equals(tVMediaMetaData.getTitle(), this.h.getTitle()) && TextUtils.equals(tVMediaMetaData.getDeviceID(), this.h.getDeviceID()) && TextUtils.equals(tVMediaMetaData.getArtist(), this.h.getArtist()) && TextUtils.equals(tVMediaMetaData.getSourceName(), this.h.getSourceName()) && tVMediaMetaData.getDeviceState() == this.h.getDeviceState() && !a(tVMediaMetaData, this.h);
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onPauseByCmd() {
            this.e = false;
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void pushMediaState() {
            Remote.Response.PlayerStatus playerStatus = this.c;
            if (playerStatus != null && playerStatus.play_song_detail != null) {
                MediaSessionController.getInstance().notifyMetadataChanged(ConnectType.MIPLAY, MediaHelper.createMediaMetadata(this.c.play_song_detail));
                MediaSessionController.getInstance().notifyPlaybackStateChanged(MicoMediaControllerProxy.transformPlaybackState(this.c));
            }
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onDurationUpdated(long j) {
            Remote.Response.PlayerStatus playerStatus = this.c;
            if (playerStatus != null && playerStatus.play_song_detail != null && MediaSessionController.this.h.isActive()) {
                this.c.play_song_detail.position = j;
                LocalPlayerManager.getInstance().notifyPlaybackStateChanged(this.c);
            }
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onMediaInfoChange(Remote.Response.PlayingData playingData) {
            Remote.Response.PlayerStatus playerStatus;
            if (this.c == null) {
                this.c = new Remote.Response.PlayerStatus();
                this.c.media_type = 21;
            }
            this.c.play_song_detail = playingData;
            if (MediaSessionController.this.h.isActive() && (playerStatus = this.c) != null && playerStatus.play_song_detail != null) {
                MediaSessionController.getInstance().notifyMetadataChanged(ConnectType.MIPLAY, MediaHelper.createMediaMetadata(this.c.play_song_detail));
                LocalPlayerManager.getInstance().notifyMediaMetadataChanged(this.c);
            }
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onConnect() {
            if (!ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO)) {
                MediaSessionController.this.h.disConnect();
            }
        }

        private void b() {
            if (MediaSessionController.this.h.isActive()) {
                Remote.Response.PlayerStatus playerStatus = this.c;
                if (playerStatus != null) {
                    playerStatus.status = 0;
                    playerStatus.play_song_detail = new Remote.Response.PlayingData();
                    LocalPlayerManager.getInstance().notifyMediaMetadataChanged(this.c);
                }
                a();
            }
            MediaSessionController.this.h.setIsActive(false);
            if (MediaSessionController.this.h == MediaSessionController.this.f) {
                MediaSessionController.this.f = null;
                ConnectType.type = ConnectType.UNKNOWN;
            }
            this.d = 0;
            this.a.stop();
            L.player.d("disconnect miplay");
            this.g = false;
            ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO);
            if (Screen.getInstance().isLockScreenDisplaying() || !Screen.getInstance().isInteractive()) {
                ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
            }
        }

        private void c() {
            MediaSessionController mediaSessionController = MediaSessionController.this;
            mediaSessionController.requestActive(mediaSessionController.h);
            Remote.Response.PlayerStatus playerStatus = this.c;
            if (playerStatus != null && playerStatus.play_song_detail != null) {
                MediaSessionController.getInstance().notifyMetadataChanged(ConnectType.MIPLAY, MediaHelper.createMediaMetadata(this.c.play_song_detail));
                LocalPlayerManager.getInstance().notifyMediaMetadataChanged(this.c);
            }
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onPlayStateChange(int i) {
            if (this.c != null) {
                if (i == 1) {
                    if (!ChildModeManager.getManager().canExecute(ChildModeManager.TimeType.AUDIO)) {
                        MediaSessionController.this.h.disConnect();
                        return;
                    } else if (a(i)) {
                        if (VoipModel.getInstance().isVoipActive()) {
                            MediaSessionController.this.h.pause();
                            this.g = true;
                        } else {
                            this.g = false;
                            EventBusRegistry.getEventBus().post(new PlayerEvent.ClosePlayerListActivityV2Event());
                            c();
                            this.a.setIgnoreForceStop(AudioSource.AUDIO_SOURCE_POWER_KEY);
                            this.a.start();
                            ChildModeManager.getManager().start(ChildModeManager.TimeType.AUDIO);
                            if (Screen.getInstance().isLockScreenDisplaying() || !Screen.getInstance().isInteractive()) {
                                ChildModeManager.getManager().start(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
                            }
                        }
                    }
                } else if (i == 0) {
                    if (a(i)) {
                        b();
                        this.g = false;
                    }
                } else if (i == 2 && a(i)) {
                    if (!MediaSessionController.this.isBTPlaying() && !MediaSessionController.this.isMicoPlaying()) {
                        c();
                    }
                    ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO);
                    if (Screen.getInstance().isLockScreenDisplaying() || !Screen.getInstance().isInteractive()) {
                        ChildModeManager.getManager().stopTiming(ChildModeManager.TimeType.AUDIO_IN_LOCKSCREEN);
                    }
                }
                if (MediaSessionController.this.h.isActive()) {
                    this.c.status = i;
                    MediaSessionController.getInstance().notifyPlaybackStateChanged(MicoMediaControllerProxy.transformPlaybackState(this.c));
                    LocalPlayerManager.getInstance().notifyPlaybackStateChanged(this.c);
                }
                this.d = i;
            }
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.Callback
        public void onSourceNameChange(String str) {
            LocalPlayerManager.getInstance().setDeviceName(str);
        }

        public void a() {
            MediaSessionController.getInstance().notifyMetadataChanged(ConnectType.MIPLAY, BTMediaControllerProxy.buildEmptyMediaMetadata());
            MediaSessionController.getInstance().notifyPlaybackStateChanged(1);
            EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayError());
        }
    };

    private MediaSessionController(Context context) {
        this.a = context;
        L.player.d("MediaSessionController constructor");
    }

    public void initService() {
        L.player.d("MediaSessionController initService");
        if (this.a != null) {
            EventBusRegistry.getEventBus().register(this);
            ((MediaSessionManager) this.a.getSystemService("media_session")).addOnActiveSessionsChangedListener(new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.xiaomi.micolauncher.skills.music.controller.MediaSessionController.2
                @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
                public void onActiveSessionsChanged(List<MediaController> list) {
                    for (MediaController mediaController : list) {
                        String tag = mediaController.getTag();
                        String packageName = mediaController.getPackageName();
                        L.player.d("OnActiveSessionsChanged package: %s tag: %s", mediaController.getPackageName(), tag);
                        if (packageName.equalsIgnoreCase(MediaSessionController.this.a.getPackageName()) && tag.equalsIgnoreCase(AudioPlayerService.MEDIA_SESSION_TAG)) {
                            L.player.d("OnActiveSessionsChanged WiFi mediaController: %s", mediaController);
                            if (MediaSessionController.this.d == null || MediaSessionController.this.d.isReleased()) {
                                MediaSessionController mediaSessionController = MediaSessionController.this;
                                mediaSessionController.d = new MicoMediaControllerProxy(mediaSessionController.a, MediaSessionController.this, mediaController);
                                return;
                            }
                            MediaSessionController.this.d.resetMediaController(mediaController);
                            return;
                        } else if (mediaController.getPackageName().equals(MediaSessionController.COM_ANDROID_BLUETOOTH)) {
                            L.player.d("OnActiveSessionsChanged BT mediaController: %s", mediaController);
                            if (MediaSessionController.this.e == null) {
                                MediaSessionController mediaSessionController2 = MediaSessionController.this;
                                mediaSessionController2.e = new BTMediaControllerProxy(mediaSessionController2.a, MediaSessionController.this, mediaController);
                                return;
                            }
                            MediaSessionController.this.e.resetMediaController(mediaController);
                            return;
                        }
                    }
                }
            }, null);
            if (SystemSetting.isInitialized(this.a)) {
                Context context = this.a;
                context.startForegroundService(new Intent(context, AudioPlayerService.class));
            }
            this.h = new MiplayMediaControllerProxy(MicoApplication.getApp(), this.miPlayCallback);
            Intent intent = new Intent("com.xiaomi.micolauncher.IMicoRemoteService");
            intent.setComponent(new ComponentName("com.xiaomi.micolauncher", "com.xiaomi.micolauncher.common.services.MicoRemoteService"));
            this.a.bindService(intent, this.m, 1);
        }
    }

    public static MediaSessionController getInstance() {
        if (c == null) {
            L.player.e("MediaSessionController call init first");
        }
        return c;
    }

    public void notifyMetadataChanged(ConnectType connectType, MediaMetadata mediaMetadata) {
        if (mediaMetadata != null) {
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_DISPLAY_BAR_EXTRA_TEXT);
            intent.setPackage(Constants.PKG_SYSTEM_UI);
            String string = mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE);
            CharSequence text = mediaMetadata.getText(MediaMetadataCompat.METADATA_KEY_ARTIST);
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(string)) {
                sb.append((CharSequence) string);
            }
            if (!TextUtils.isEmpty(text)) {
                sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append(text);
            }
            intent.putExtra("content", sb.toString());
            MicoApplication.getApp().sendBroadcast(intent);
            a(connectType, string, text, mediaMetadata);
            a(mediaMetadata);
            a(connectType, mediaMetadata);
            return;
        }
        L.player.e("MediaMetadata is null");
    }

    private void a(ConnectType connectType, final MediaMetadata mediaMetadata) {
        if (ConnectType.MICO == connectType || ConnectType.BLUETOOTH == connectType) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.controller.MediaSessionController.3
                @Override // java.lang.Runnable
                public void run() {
                    if (MediaSessionController.this.h != null) {
                        MediaSessionController.this.h.setLocalMediaInfo(mediaMetadata);
                    }
                }
            });
        }
    }

    private void a(ConnectType connectType, CharSequence charSequence, CharSequence charSequence2, MediaMetadata mediaMetadata) {
        EventBusRegistry.getEventBus().post(new MicoMediaEvent(a(connectType), charSequence, charSequence2, mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI), mediaMetadata.getBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON), (ConnectType.MIPLAY != connectType || TextUtils.isEmpty(charSequence) || TextUtils.isEmpty(charSequence2)) ? null : MicoApplication.getApp().getString(R.string.music_source_from, new Object[]{LocalPlayerManager.getInstance().getDeviceName()})));
    }

    private String a(ConnectType connectType) {
        String name = MediaType.NONE.name();
        switch (connectType) {
            case BLUETOOTH:
                return MediaType.BT.name();
            case MIPLAY:
                return MediaType.MIPLAY.name();
            case MICO:
                return MediaType.LOCAL.name();
            default:
                return name;
        }
    }

    private void a(MediaMetadata mediaMetadata) {
        mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI);
        mediaMetadata.getBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON);
        IMicoRemoteService iMicoRemoteService = this.l;
        if (iMicoRemoteService != null) {
            try {
                iMicoRemoteService.notifyMediaMetadataChanged(mediaMetadata);
            } catch (RemoteException e) {
                L.player.e("micoRemoteService.notifyMediaMetadataChanged throws android.os.RemoteException", e);
            }
        }
    }

    public void notifyPlaybackStateChanged(int i) {
        MiplayMediaControllerProxy miplayMediaControllerProxy;
        EventBusRegistry.getEventBus().post(new MediaControllerEvent.MusicState(i));
        IMicoRemoteService iMicoRemoteService = this.l;
        if (iMicoRemoteService != null) {
            try {
                iMicoRemoteService.notifyPlaybackStateChanged(i);
            } catch (RemoteException e) {
                L.player.e("micoRemoteService.notifyMediaMetadataChanged throws android.os.RemoteException", e);
            }
        }
        if ((isBTUsing() || isMicoUsing()) && (miplayMediaControllerProxy = this.h) != null) {
            miplayMediaControllerProxy.setLocalMediaState(i);
        }
    }

    public void prev() {
        if (getActiveMediaController() != null) {
            L.player.d("MediaSessionController prev");
            getActiveMediaController().skipToPrevious();
        }
    }

    public void next() {
        if (getActiveMediaController() != null) {
            L.player.d("MediaSessionController next");
            getActiveMediaController().skipToNext();
        }
    }

    public void prevWiFi() {
        if (this.d != null) {
            L.player.d("MediaSessionController prev");
            this.d.skipToPrevious();
        }
    }

    public void nextWiFi() {
        if (this.d != null) {
            L.player.d("MediaSessionController next");
            this.d.skipToNext();
        }
    }

    public void play() {
        if (getActiveMediaController() != null) {
            L.player.d("MediaSessionController play");
            getActiveMediaController().play();
            PlayerApi.showSystemBarText();
        }
    }

    public void playWiFi() {
        if (this.d != null) {
            L.player.d("MediaSessionController playWiFi");
            this.d.play();
        }
    }

    public void playWiFiAndPushData() {
        if (this.d != null) {
            L.player.d("MediaSessionController playWiFi");
            this.d.play();
            this.d.pushMediaState();
        }
    }

    public void pause() {
        if (getActiveMediaController() != null) {
            L.player.d("MediaSessionController pause");
            getActiveMediaController().pause();
            PlayerApi.clearSystemBarText();
        }
    }

    public void seekTo(long j) {
        if (getActiveMediaController() != null) {
            L.player.d("MediaSessionController seekTo %d", Long.valueOf(j));
            getActiveMediaController().seekTo(j);
        }
    }

    public void playWiFiAfterBT() {
        L.player.d("MediaSessionController playWiFiAfterBT");
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (micoMediaControllerProxy != null) {
            L.player.d("MediaSessionController onResumePlayer = %b", Boolean.valueOf(micoMediaControllerProxy.onResumePlayer()));
        }
    }

    public static void init(Context context) {
        if (c == null) {
            c = new MediaSessionController(context);
            PlayerApi.clearPlayerStatus();
        }
    }

    public int getVolume() {
        if (getActiveMediaController() != null) {
            return getActiveMediaController().getPlayerStatus().volume;
        }
        return SystemVolume.getInstance().getVolume();
    }

    public int getLoopType() {
        return LocalPlayerManager.getInstance().getLoopType();
    }

    public void setLoopType(int i) {
        this.b = i;
        if (getActiveMediaController() != null && getActiveMediaController().equals(this.d)) {
            this.d.setLoopType(i);
        }
    }

    public void playAudioList(String str, AudioPlayer.Play play, Template.PlayInfo playInfo, String str2, boolean z, long j) {
        if (this.d != null) {
            L.player.d("MediaSessionController playAudioList");
            this.d.clearAudioBookPlayList();
            this.d.playAudioList(str, play, playInfo, str2, z, j);
            requestActive(this.d);
            return;
        }
        b();
    }

    public boolean setPlayerShutdownTimer(Remote.Request.PlayerShutdown playerShutdown) {
        MicoMediaControllerProxy micoMediaControllerProxy;
        if (Remote.Request.PlayerShutdown.ACTION_CANCEL.equals(playerShutdown.action)) {
            if (this.j == -2) {
                MicoMediaControllerProxy micoMediaControllerProxy2 = this.d;
                if (micoMediaControllerProxy2 != null) {
                    micoMediaControllerProxy2.pauseAfterFinish(-2);
                }
            } else {
                a();
            }
            this.j = 0L;
            return true;
        } else if (Remote.Request.PlayerShutdown.ACTION_PAUSE_LATER.equals(playerShutdown.action)) {
            long interval = playerShutdown.getInterval();
            this.j = (System.currentTimeMillis() / 1000) + interval;
            a(1, interval * 1000);
            MicoMediaControllerProxy micoMediaControllerProxy3 = this.d;
            if (micoMediaControllerProxy3 != null) {
                micoMediaControllerProxy3.pauseAfterFinish(-2);
            }
            return true;
        } else if (!Remote.Request.PlayerShutdown.ACTION_PAUSE_AFTER_FINISH.equals(playerShutdown.action) || (micoMediaControllerProxy = this.d) == null) {
            return false;
        } else {
            micoMediaControllerProxy.pauseAfterFinish(0);
            this.j = -2L;
            a();
            return true;
        }
    }

    public boolean hasPlayerShutdownTimer() {
        long j = this.j;
        return j > 0 || j == -2;
    }

    private void a(int i, long j) {
        L.player.i("MediaSessionController player shutdown %d", Long.valueOf(j));
        if (this.k == null) {
            this.k = new Handler(Looper.getMainLooper()) { // from class: com.xiaomi.micolauncher.skills.music.controller.MediaSessionController.4
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (message.what == 1 && MediaSessionController.this.d != null) {
                        MediaSessionController.this.j = 0L;
                        MediaSessionController.this.d.pause();
                    }
                }
            };
        }
        this.k.removeMessages(i);
        this.k.sendEmptyMessageDelayed(i, j);
    }

    private void a() {
        L.player.i("MediaSessionController player shutdown cancel");
        Handler handler = this.k;
        if (handler != null) {
            handler.removeMessages(1);
            this.k = null;
        }
    }

    public long getPlayerShutdownRemainTime() {
        long j = this.j;
        if (j > 0) {
            long currentTimeMillis = j - (System.currentTimeMillis() / 1000);
            if (currentTimeMillis > 0) {
                return currentTimeMillis;
            }
        }
        return 0L;
    }

    private void b() {
        if (this.d == null) {
            L.player.w("mMicoMediaController is null, start AudioPlayerService");
            Context context = this.a;
            context.startForegroundService(new Intent(context, AudioPlayerService.class));
        }
    }

    public void playSheet(String str, int i, int i2) {
        L.player.i("MediaSessionController playSheet sheetId:%s offset:%s", str, Integer.valueOf(i));
        if (this.d == null) {
            b();
        } else if (!TextUtils.isEmpty(str)) {
            this.d.playSheet(Long.parseLong(str), i, i2);
            requestActive(this.d);
        }
    }

    public void playStation(String str, String str2, int i, boolean z) {
        L.player.i("MediaSessionController playStation stationId:%s origin:%s type:%s", str, str2, Integer.valueOf(i));
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (micoMediaControllerProxy != null) {
            micoMediaControllerProxy.playStation(str, str2, i, z);
            requestActive(this.d);
            return;
        }
        b();
    }

    protected MediaControllerProxy getActiveMediaController() {
        if (this.f == null) {
            L.player.w("MediaSessionController getActiveMediaController is null, start it");
            b();
        }
        return this.f;
    }

    public int getPlayStatus() {
        if (getActiveMediaController() != null) {
            return getActiveMediaController().getPlayStatus();
        }
        return 0;
    }

    public List<Remote.Response.TrackData> getPlaylist() {
        if (getActiveMediaController() != null) {
            return getActiveMediaController().getPlaylist();
        }
        return null;
    }

    public void stop() {
        pause();
    }

    public void playByIndex(int i) {
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (micoMediaControllerProxy != null) {
            micoMediaControllerProxy.playByIndex(i);
            requestActive(this.d);
            return;
        }
        b();
    }

    public Remote.Response.PlayingData getCurrentPlayingData() {
        MediaControllerProxy mediaControllerProxy = this.f;
        if (mediaControllerProxy != null) {
            return mediaControllerProxy.getCurrentPlayingData();
        }
        L.player.w("MediaSessionController getCurrentPlayingData.mActiveMediaController=null");
        return null;
    }

    public void setVolume(int i) {
        SystemVolume.getInstance().setVolume(i, true);
        if (SystemVolume.volumeTooBigForPowerSaveMode(i)) {
            EventBusRegistry.getEventBus().post(new VolumeLimitInPowerSaveModeEvent());
        }
    }

    public void requestActive(MediaControllerProxy mediaControllerProxy) {
        MediaControllerProxy mediaControllerProxy2;
        if (mediaControllerProxy == null) {
            return;
        }
        if (this.f != mediaControllerProxy || mediaControllerProxy.isReleased()) {
            L.player.i("MediaSessionController requestActive proxy is null or released");
            MediaControllerProxy mediaControllerProxy3 = this.f;
            if (!(mediaControllerProxy3 == null || mediaControllerProxy3.isReleased() || (mediaControllerProxy2 = this.f) == mediaControllerProxy)) {
                this.g = mediaControllerProxy2;
                Logger logger = L.player;
                logger.d("change active proxy, pre_proxy=" + this.f.toString());
            }
            this.f = mediaControllerProxy;
            this.f.setIsActive(true);
            Logger logger2 = L.player;
            logger2.i("MediaSessionController new active proxy=" + this.f.toString());
            c();
        }
    }

    private void c() {
        MediaControllerProxy mediaControllerProxy = this.f;
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (mediaControllerProxy == micoMediaControllerProxy) {
            a(this.e);
            a(this.h);
            return;
        }
        BTMediaControllerProxy bTMediaControllerProxy = this.e;
        if (mediaControllerProxy == bTMediaControllerProxy) {
            a(micoMediaControllerProxy);
            a(this.h);
        } else if (mediaControllerProxy == this.h) {
            a(bTMediaControllerProxy);
            a(this.d);
        }
    }

    private void a(MediaControllerProxy mediaControllerProxy) {
        if (mediaControllerProxy != null && !mediaControllerProxy.isReleased()) {
            mediaControllerProxy.setIsActive(false);
            if (mediaControllerProxy instanceof MicoMediaControllerProxy) {
                ((MicoMediaControllerProxy) mediaControllerProxy).innerPause();
            } else {
                mediaControllerProxy.pause();
            }
        }
    }

    public void playDlna(MediaInfo mediaInfo) {
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (micoMediaControllerProxy != null) {
            micoMediaControllerProxy.playDlna(mediaInfo);
            requestActive(this.d);
        }
    }

    public void playMusicList(String str) {
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (micoMediaControllerProxy != null) {
            micoMediaControllerProxy.playMusicList(str);
            requestActive(this.d);
            return;
        }
        b();
    }

    public void toggleLove(boolean z) {
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (micoMediaControllerProxy != null && micoMediaControllerProxy.equals(this.f)) {
            this.d.toggleLove(z);
        }
    }

    public void playRecommendMusic() {
        L.player.i("MediaSessionController playRecommendMusic");
        if (this.d != null) {
            ThirdPartyAppProxy.getInstance().quit(this.a);
            SpeechManager.getInstance().setNewSession();
            SpeechManager.getInstance().nlpRequest("播放音乐");
            return;
        }
        b();
    }

    public int nextLoop() {
        int nextLoop = Remote.Request.LoopType.nextLoop(getLoopType());
        setLoopType(nextLoop);
        return nextLoop;
    }

    public boolean isPlayWiFiMusic() {
        return this.f == this.d;
    }

    public void onAudioPlayerServiceDestroy() {
        L.player.i("MediaSessionController onAudioPlayerServiceDestroy");
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (micoMediaControllerProxy != null) {
            micoMediaControllerProxy.release();
        }
        if (this.d == this.f) {
            this.f = null;
        }
        this.d = null;
        EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayError());
    }

    public PlaylistController getMicoPlaylistController() {
        return this.i;
    }

    public void setPlaylistController(PlaylistController playlistController) {
        this.i = playlistController;
    }

    public MicoMediaControllerProxy getMicoMediaController() {
        return this.d;
    }

    public void resetMicoMediaController(MediaController mediaController) {
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (micoMediaControllerProxy == null) {
            this.d = new MicoMediaControllerProxy(this.a, this, mediaController);
        } else {
            micoMediaControllerProxy.resetMediaController(mediaController);
        }
    }

    public void resetBTMediaController() {
        this.e = null;
    }

    public void playRecentlyMusic() {
        L.player.d("MediaSessionController playRecentlyMusic");
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (micoMediaControllerProxy != null) {
            micoMediaControllerProxy.playRecentlyMusic();
            requestActive(this.d);
            return;
        }
        b();
    }

    public void playPatchwallMusic(String str, String str2, int i, int i2) {
        L.player.d("MediaSessionController playPatchwallMusic");
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (micoMediaControllerProxy != null) {
            micoMediaControllerProxy.playPatchwallMusic(str, str2, i, i2);
            requestActive(this.d);
            return;
        }
        b();
    }

    public void playNewsList(String str, int i) {
        if (this.d != null) {
            L.player.i("MediaSessionController play playNewsList");
            this.d.playNewsList(str, i);
            requestActive(this.d);
            return;
        }
        b();
    }

    public void pushMediaState() {
        if (getActiveMediaController() != null) {
            getActiveMediaController().pushMediaState();
        }
    }

    public void playBabyCourse(String str) {
        MicoMediaControllerProxy micoMediaControllerProxy = this.d;
        if (micoMediaControllerProxy != null) {
            micoMediaControllerProxy.playBabyCourse(str);
        }
    }

    public boolean isBtPlayingWhenDisconnect() {
        if (!(this.f instanceof BTMediaControllerProxy)) {
            return false;
        }
        EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayError());
        BTMediaControllerProxy bTMediaControllerProxy = (BTMediaControllerProxy) this.f;
        bTMediaControllerProxy.clearWhenBtDisconnect();
        return bTMediaControllerProxy.isPlayingWhenDisconnect();
    }

    public boolean isBTPlaying() {
        MediaControllerProxy mediaControllerProxy = this.f;
        return (mediaControllerProxy instanceof BTMediaControllerProxy) && 1 == ((BTMediaControllerProxy) mediaControllerProxy).getPlayStatus();
    }

    public boolean isMicoPlaying() {
        MediaControllerProxy mediaControllerProxy = this.f;
        return (mediaControllerProxy instanceof MicoMediaControllerProxy) && 1 == ((MicoMediaControllerProxy) mediaControllerProxy).getPlayStatus();
    }

    public boolean isMiplayPlaying() {
        MediaControllerProxy mediaControllerProxy = this.f;
        return (mediaControllerProxy instanceof MiplayMediaControllerProxy) && 1 == ((MiplayMediaControllerProxy) mediaControllerProxy).getPlayStatus();
    }

    public boolean isMiplayUsing() {
        return this.f instanceof MiplayMediaControllerProxy;
    }

    public boolean isMicoUsing() {
        return this.f instanceof MicoMediaControllerProxy;
    }

    public boolean isBTUsing() {
        return this.f instanceof BTMediaControllerProxy;
    }

    public boolean isRemotePlaying() {
        return isBTPlaying() || isMiplayPlaying();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVoipStopEvent(VoipStopEvent voipStopEvent) {
        MiplayMediaControllerProxy.Callback callback = this.miPlayCallback;
        if (callback != null) {
            callback.onVoipStop();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopAudioEvent(StopAudioEvent stopAudioEvent) {
        MiplayMediaControllerProxy miplayMediaControllerProxy = this.h;
        if (miplayMediaControllerProxy != null) {
            miplayMediaControllerProxy.disConnect();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRelayPlayEvent(RelayPlayEvent relayPlayEvent) {
        if (this.h != null && !TextUtils.isEmpty(relayPlayEvent.deviceId)) {
            this.h.playRelay(relayPlayEvent.deviceId);
        }
    }

    /* renamed from: com.xiaomi.micolauncher.skills.music.controller.MediaSessionController$6 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] b = new int[PlaybackControl.values().length];

        static {
            try {
                b[PlaybackControl.PLAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[PlaybackControl.CONTINUE_PLAYING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[PlaybackControl.STOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[PlaybackControl.PAUSE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                b[PlaybackControl.PREV.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                b[PlaybackControl.NEXT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            a = new int[ConnectType.values().length];
            try {
                a[ConnectType.BLUETOOTH.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[ConnectType.MIPLAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[ConnectType.MICO.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }
}
