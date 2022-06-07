package com.xiaomi.micolauncher.skills.music.controller.mediacontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaMetadata;
import android.media.Rating;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.ServiceUtils;
import com.elvishew.xlog.Logger;
import com.mi.milink.mediacore.MediaCoreService;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.mico.base.utils.NetworkUtil;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.api.service.SpeakerService;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.VolumeChangeEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.setting.utils.SystemVolume;
import com.xiaomi.miplay.audioclient.tv.MiPlayTVClient;
import com.xiaomi.miplay.audioclient.tv.MiPlayTVClientCallback;
import com.xiaomi.miplay.audioclient.tv.TVMediaMetaData;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class MiplayMediaControllerProxy extends MiPlayTVClientCallback implements MediaControllerProxy {
    private volatile int a;
    private MiPlayTVClient b;
    private Remote.Response.PlayingData e;
    private Callback f;
    private boolean g;
    private boolean h;
    private boolean j;
    private boolean k;
    private String l;
    private String m;
    private long c = Long.MAX_VALUE;
    private long d = 0;
    private int i = 0;
    private int n = 0;
    private SpeakerService.Config o = null;
    private boolean p = false;
    private long q = TimeUnit.MINUTES.toMillis(1);
    private boolean r = false;
    private Runnable s = new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.2
        @Override // java.lang.Runnable
        public void run() {
            if (!MiplayMediaControllerProxy.this.r) {
                L.player.d("delay init service start");
                MiplayMediaControllerProxy.this.a(MicoApplication.getApp());
            }
        }
    };
    private Runnable t = new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.3
        @Override // java.lang.Runnable
        public void run() {
            if (ServiceUtils.isServiceRunning(MediaCoreService.class)) {
                L.player.d("begin initAsync");
                MiplayMediaControllerProxy.this.b.initAsync(MiplayMediaControllerProxy.this, MicoApplication.getApp().getPackageName());
            } else if (MiplayMediaControllerProxy.this.n < 3) {
                L.player.d("not ready");
                MiplayMediaControllerProxy.this.a();
                MiplayMediaControllerProxy.g(MiplayMediaControllerProxy.this);
            } else {
                L.player.d("begin initAsync last");
                MiplayMediaControllerProxy.this.b.initAsync(MiplayMediaControllerProxy.this, MicoApplication.getApp().getPackageName());
            }
        }
    };
    private BroadcastReceiver u = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("com.xiaomi.smarthome.refresh_current_device_name".equals(intent.getAction())) {
                MiplayMediaControllerProxy.this.l = intent.getStringExtra("name");
                MiplayMediaControllerProxy.this.m = intent.getStringExtra("roomName");
                if (!TextUtils.isEmpty(MiplayMediaControllerProxy.this.l)) {
                    String b = MiplayMediaControllerProxy.this.b();
                    L.relay.d("deviceName: %s roomName :%s", MiplayMediaControllerProxy.this.l, b);
                    MiplayMediaControllerProxy miplayMediaControllerProxy = MiplayMediaControllerProxy.this;
                    miplayMediaControllerProxy.setLocalDeviceInfo(b, miplayMediaControllerProxy.l, TokenManager.getInstance().getUserId());
                }
            } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()) && NetworkUtil.isNetworkConnected(context) && !MiplayMediaControllerProxy.this.r) {
                MiplayMediaControllerProxy.this.a(context);
            }
        }
    };

    /* loaded from: classes3.dex */
    public interface Callback {
        void onCmdSessionError();

        void onCmdSessionSuccess();

        void onConnect();

        void onDisconnect();

        void onDurationUpdated(long j);

        void onMediaInfoChange(Remote.Response.PlayingData playingData);

        void onPauseByCmd();

        void onPlayByCmd();

        void onPlayStateChange(int i);

        void onRelayMediaData(TVMediaMetaData tVMediaMetaData);

        void onSourceNameChange(String str);

        void onVoipStop();

        void onVolumeChange(int i);

        void pushMediaState();
    }

    private int b(int i) {
        if (i == 0) {
            return 0;
        }
        return i == 2 ? 1 : 2;
    }

    private int c(int i) {
        if (i == 3) {
            return 2;
        }
        return i == 2 ? 3 : 0;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public int getMediaType() {
        return 21;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public List<Remote.Response.TrackData> getPlaylist() {
        return null;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public boolean hasHistoryData() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public boolean isReleased() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void release() {
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void setRating(Rating rating) {
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void stop() {
    }

    static /* synthetic */ int g(MiplayMediaControllerProxy miplayMediaControllerProxy) {
        int i = miplayMediaControllerProxy.n;
        miplayMediaControllerProxy.n = i + 1;
        return i;
    }

    public MiplayMediaControllerProxy(Context context, Callback callback) {
        a(context, callback);
    }

    private void a(Context context, Callback callback) {
        this.b = new MiPlayTVClient(context);
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        this.f = callback;
        c(context);
        a(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Context context) {
        L.startUpProfile.d("MiPlayerSetupBegin");
        if (!this.p) {
            this.p = true;
            ApiManager.speechMiService.getConfig().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).safeSubscribe(new Observer<SpeakerService.Config>() { // from class: com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MiplayMediaControllerProxy.1
                @Override // io.reactivex.Observer
                public void onSubscribe(@NonNull Disposable disposable) {
                }

                /* renamed from: a */
                public void onNext(@NonNull SpeakerService.Config config) {
                    MiplayMediaControllerProxy.this.r = true;
                    if (config != null) {
                        MiplayMediaControllerProxy.this.o = config;
                        if (TextUtils.equals(config.interConnectionOff, "1")) {
                            return;
                        }
                    }
                    MiplayMediaControllerProxy.this.b(context);
                }

                @Override // io.reactivex.Observer
                public void onError(@NonNull Throwable th) {
                    L.player.d("load config error");
                    MiplayMediaControllerProxy.this.p = false;
                    if (NetworkUtil.isNetworkConnected(MicoApplication.getApp()) && MiplayMediaControllerProxy.this.q < TimeUnit.MINUTES.toMillis(1000L)) {
                        MiplayMediaControllerProxy.this.q *= 2;
                        ThreadUtil.removeCallbacksInMainThread(MiplayMediaControllerProxy.this.s);
                        ThreadUtil.postDelayedInMainThread(MiplayMediaControllerProxy.this.s, MiplayMediaControllerProxy.this.q);
                    }
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    MiplayMediaControllerProxy.this.p = false;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Context context) {
        if (!this.k) {
            this.k = true;
            Intent intent = new Intent(context, MediaCoreService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
            a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        ThreadUtil.postDelayedInMainThread(this.t, 3000L);
    }

    private void c(Context context) {
        try {
            Log.d("MiplayReceiver", "INIT");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.xiaomi.smarthome.refresh_current_device_name");
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            context.registerReceiver(this.u, intentFilter);
            Log.d("MiplayReceiver", "INITEND");
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b() {
        return TextUtils.isEmpty(this.m) ? "" : this.m;
    }

    public long getCurrentPos() {
        return this.d;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public int getPlayStatus() {
        return b(this.a);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public Remote.Response.PlayingData getCurrentPlayingData() {
        return this.e;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public Remote.Response.PlayerStatus getPlayerStatus() {
        Remote.Response.PlayerStatus playerStatus = new Remote.Response.PlayerStatus();
        playerStatus.status = getPlayStatus();
        playerStatus.volume = this.i;
        playerStatus.media_type = 21;
        playerStatus.play_song_detail = getCurrentPlayingData();
        return playerStatus;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public long getSongDuration() {
        return this.c;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void setIsActive(boolean z) {
        this.j = z;
    }

    public boolean isActive() {
        return this.j;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void skipToNext() {
        if (!c()) {
            L.player.d("clickNxtButton, next button clicked! ");
            this.b.onNext();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void skipToPrevious() {
        if (!c()) {
            L.player.d("last button clicked!");
            this.b.onPrev();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void pause() {
        L.player.d("pause");
        if (!c()) {
            this.b.Pause();
            Callback callback = this.f;
            if (callback != null) {
                callback.onPauseByCmd();
            }
        }
    }

    public void pauseByFocus() {
        L.player.d("pauseByFocus");
        if (!c()) {
            this.b.Pause();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void play() {
        L.player.d("play");
        if (!c()) {
            this.b.Resume();
            Callback callback = this.f;
            if (callback != null) {
                callback.onPlayByCmd();
            }
        }
    }

    public void playByFocus() {
        L.player.d("play");
        if (!c()) {
            this.b.Resume();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void pushMediaState() {
        Callback callback = this.f;
        if (callback != null) {
            callback.pushMediaState();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void seekTo(long j) {
        if (!c()) {
            L.player.d("seekTo");
            this.b.seek(j);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void setVolume(int i) {
        a(i);
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onInitSuccess() throws RemoteException {
        L.player.d("onInitSuccess");
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onInitError() throws RemoteException {
        L.player.d("onInitError");
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onCmdSessionError() throws RemoteException {
        L.player.d("onCmdSessionError");
        this.g = false;
        Callback callback = this.f;
        if (callback != null) {
            callback.onCmdSessionError();
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onCmdSessionSuccess() throws RemoteException {
        L.player.d("onCmdSessionSuccess");
        this.g = true;
        a(-1);
        this.b.getMediaInfo();
        this.b.getPlayState();
        onSourceNameChange(this.b.getSourceName());
        Callback callback = this.f;
        if (callback != null) {
            callback.onCmdSessionSuccess();
        }
        if (this.l != null) {
            setLocalDeviceInfo(b(), this.l, TokenManager.getInstance().getUserId());
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onPositionAck(long j) throws RemoteException {
        L.player.d("onPositionAck");
        this.c = j;
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onPlayStateChange(int i) throws RemoteException {
        Logger logger = L.player;
        logger.d("onPlayStateChange, playStatus = " + i);
        this.a = i;
        Callback callback = this.f;
        if (callback != null && this.h) {
            callback.onPlayStateChange(getPlayStatus());
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onPlayStateAck(int i) throws RemoteException {
        L.player.d("onPlayStateAck");
        onPlayStateChange(i);
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onMediaInfoAck(TVMediaMetaData tVMediaMetaData) throws RemoteException {
        L.player.d("onMediaInfoAck");
        onMediaInfoChange(tVMediaMetaData);
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onMediaInfoChange(TVMediaMetaData tVMediaMetaData) throws RemoteException {
        L.player.d("onMediaInfoChange %s", tVMediaMetaData.toString());
        Remote.Response.PlayingData b = b(tVMediaMetaData);
        try {
            this.c = tVMediaMetaData.getDuration();
            if (a(tVMediaMetaData)) {
                this.f.onRelayMediaData(tVMediaMetaData);
            } else {
                this.e = b;
                this.f.onMediaInfoChange(b);
            }
        } catch (Exception unused) {
        }
    }

    private boolean a(TVMediaMetaData tVMediaMetaData) {
        return !TextUtils.isEmpty(tVMediaMetaData.getDeviceID());
    }

    public boolean hasDataToPlay() {
        Remote.Response.PlayingData playingData = this.e;
        return playingData != null && !TextUtils.isEmpty(playingData.title);
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onVolumeChange(int i) throws RemoteException {
        Logger logger = L.player;
        logger.d("onVolumeChange, volume = " + i);
        this.i = i;
        Callback callback = this.f;
        if (callback != null) {
            callback.onVolumeChange(i);
        }
        if (i != SystemVolume.getInstance().getVolume2()) {
            SystemVolume.getInstance().setVolume(i);
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onVolumeAck(int i) throws RemoteException {
        L.player.d("onVolumeAck");
        this.i = i;
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onSourceNameChange(String str) throws RemoteException {
        Logger logger = L.player;
        logger.d("onSourceNameChange deviceName = " + str);
        Callback callback = this.f;
        if (callback != null) {
            callback.onSourceNameChange(str);
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onDurationUpdated(long j) throws RemoteException {
        this.d = j;
        Callback callback = this.f;
        if (callback != null) {
            callback.onDurationUpdated(j);
        }
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onMirrorModeNotify(int i) throws RemoteException {
        Logger logger = L.player;
        logger.d("onMirrorModeNotify, mode = " + i);
        if (i != 1) {
            this.h = false;
            Callback callback = this.f;
            if (callback != null) {
                callback.onDisconnect();
                this.a = 0;
                return;
            }
            return;
        }
        this.h = true;
        if (this.f != null) {
            a(-1);
            this.f.onConnect();
        }
    }

    private boolean c() {
        if (!this.g) {
            L.player.d("cmd session not ready");
        }
        return !this.g;
    }

    private void a(int i) {
        if (!c()) {
            if (i == -1) {
                i = SystemVolume.getInstance().getVolume();
            }
            Logger logger = L.player;
            logger.d("syncVolume, volume = " + i);
            this.b.setVolume(i);
        }
    }

    public void playRelay(String str) {
        if (!c()) {
            this.b.musicRelay(str, 1);
        }
    }

    public void disConnect() {
        L.player.d("stop");
        try {
            this.b.stop();
        } catch (Exception unused) {
            L.player.e("stop error");
        }
    }

    public void unInitService() {
        L.player.d("unInitService");
        try {
            this.b.unInit();
        } catch (Exception unused) {
            L.player.e("miplay unInit error");
        }
        d();
    }

    private void d() {
        this.f = null;
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    private Remote.Response.PlayingData b(TVMediaMetaData tVMediaMetaData) {
        Remote.Response.PlayingData playingData = new Remote.Response.PlayingData();
        try {
            playingData.title = tVMediaMetaData.getTitle();
            if (tVMediaMetaData.getArt() != null) {
                playingData.coverBitmap = tVMediaMetaData.getArt();
                playingData.outCoverBitmap = CommonUtils.compressBitmapWithNoDistortion(tVMediaMetaData.getArt(), 120, 120);
            }
            playingData.lrc = tVMediaMetaData.getLrc();
            playingData.position = getCurrentPos();
            playingData.duration = tVMediaMetaData.getDuration();
            playingData.artist = tVMediaMetaData.getArtist();
            playingData.audioType = "MUSIC";
            playingData.albumName = tVMediaMetaData.getAlbum();
            playingData.screenExtend.mediaType = 21;
            playingData.cover = tVMediaMetaData.getCoverUrl();
            playingData.musicID = tVMediaMetaData.getId();
        } catch (Exception unused) {
            L.player.e("tvMediaMetaData2playingData error");
        }
        return playingData;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVolumeChangeEvent(VolumeChangeEvent volumeChangeEvent) {
        a(volumeChangeEvent.volume);
    }

    public boolean isMiplayIsConnect() {
        return this.g && this.h;
    }

    public int setLocalDeviceInfo(String str, String str2, String str3) {
        if (!this.g) {
            return -1;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("roomName", str);
            jSONObject.put("miName", str2);
            jSONObject.put("accountId", str3);
            L.relay.d("sendLocalDevice");
            return this.b.setLocalDeviceInfo(jSONObject.toString());
        } catch (JSONException e) {
            L.relay.d("sendLocalDeviceError");
            e.printStackTrace();
            return -1;
        }
    }

    public int setLocalMediaInfo(MediaMetadata mediaMetadata) {
        if (!this.g) {
            return -1;
        }
        TVMediaMetaData tVMediaMetaData = new TVMediaMetaData();
        String string = mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE);
        String string2 = mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST);
        String string3 = mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI);
        String string4 = mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM);
        tVMediaMetaData.setTitle(string);
        tVMediaMetaData.setArtist(string2);
        tVMediaMetaData.setCoverUrl(string3);
        tVMediaMetaData.setAlbum(string4);
        return this.b.setLocalMediaInfo(tVMediaMetaData);
    }

    public int setLocalMediaState(int i) {
        if (!this.g) {
            return -1;
        }
        return this.b.setLocalMediaState(c(i));
    }
}
