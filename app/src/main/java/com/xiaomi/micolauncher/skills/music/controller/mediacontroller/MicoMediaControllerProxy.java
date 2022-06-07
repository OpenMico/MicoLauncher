package com.xiaomi.micolauncher.skills.music.controller.mediacontroller;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaController;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.music.controller.ConnectType;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;
import com.xiaomi.micolauncher.skills.music.service.AudioPlayerService;
import com.xiaomi.micolauncher.skills.music.view_v2.AudioBookPlayListActivity;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.fourthline.cling.support.model.MediaInfo;

/* loaded from: classes3.dex */
public class MicoMediaControllerProxy implements MediaControllerProxy {
    private MediaController a;
    private final MediaSessionController b;
    private final Context c;
    private long d;
    private boolean e;
    private final a f = new a();
    private boolean g;

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public List<Remote.Response.TrackData> getPlaylist() {
        return null;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public long getSongDuration() {
        return 0L;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public boolean isReleased() {
        return false;
    }

    public void playDlna(MediaInfo mediaInfo) {
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void setVolume(int i) {
    }

    public MicoMediaControllerProxy(Context context, MediaSessionController mediaSessionController, MediaController mediaController) {
        this.c = context;
        this.b = mediaSessionController;
        this.a = mediaController;
        L.player.d("MicoMediaControllerProxy create");
        LocalPlayerManager.getInstance().registerCallback(this.f);
    }

    public void resetMediaController(MediaController mediaController) {
        if (this.a != mediaController) {
            this.a = mediaController;
            LocalPlayerManager.getInstance().registerCallback(this.f);
            L.player.d("MicoMediaControllerProxy resetMediaController %s", mediaController);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void release() {
        LocalPlayerManager.getInstance().unregisterCallback(this.f);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public int getPlayStatus() {
        return LocalPlayerManager.getInstance().getPlayingPlayerStatus().status;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public Remote.Response.PlayingData getCurrentPlayingData() {
        return LocalPlayerManager.getInstance().getCurrentPlayingData();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public Remote.Response.PlayerStatus getPlayerStatus() {
        Remote.Response.PlayerStatus playingPlayerStatus = LocalPlayerManager.getInstance().getPlayingPlayerStatus();
        MediaController mediaController = this.a;
        if (mediaController != null) {
            playingPlayerStatus.volume = (this.a.getPlaybackInfo().getCurrentVolume() * 100) / mediaController.getPlaybackInfo().getMaxVolume();
        }
        return playingPlayerStatus;
    }

    public boolean isManualPaused() {
        return this.e;
    }

    public void playSheet(long j, int i, int i2) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", j);
        bundle.putInt("offset", i);
        bundle.putInt(AudioPlayerService.ARGS_KEY_LOOP_TYPE, i2);
        sendCommand(AudioPlayerService.CMD_PLAY_SHEET, bundle, null);
    }

    public void playStation(String str, String str2, int i, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("id", str);
        bundle.putString("origin", str2);
        bundle.putInt("type", i);
        bundle.putBoolean(AudioPlayerService.ARGS_KEY_CONN_PLAY, z);
        sendCommand(AudioPlayerService.CMD_PLAY_STATION, bundle, null);
    }

    public void playByIndex(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("data", i);
        this.a.sendCommand(AudioPlayerService.CMD_PLAY_BY_INDEX, bundle, null);
    }

    public void playAudioList(String str, AudioPlayer.Play play, Template.PlayInfo playInfo, String str2, boolean z, long j) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("id", str);
            bundle.putString("data", APIUtils.toJsonString(play));
            bundle.putString(AudioPlayerService.ARGS_KEY_PLAY_INFO, APIUtils.toJsonString(playInfo));
            bundle.putString(AudioPlayerService.ARGS_KEY_LOOP_TYPE, str2);
            bundle.putBoolean(AudioPlayerService.ARGS_KEY_DEPENDENCE, z);
            bundle.putLong("duration", j);
            this.a.sendCommand(AudioPlayerService.CMD_PLAY_AUDIO_LIST, bundle, null);
        } catch (JsonProcessingException unused) {
            L.player.e("playAudioList error");
        }
    }

    public void playNewsList(String str, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("data", str);
        bundle.putInt("offset", i);
        this.a.sendCommand(AudioPlayerService.CMD_PLAY_NEWS, bundle, null);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public boolean hasHistoryData() {
        return ContainerUtil.hasData(LocalPlayerManager.getInstance().getPlayList());
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void skipToNext() {
        if (this.a != null) {
            MediaSessionController.getInstance().requestActive(this);
            this.a.getTransportControls().skipToNext();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void skipToPrevious() {
        if (this.a != null) {
            MediaSessionController.getInstance().requestActive(this);
            this.a.getTransportControls().skipToPrevious();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void pause() {
        MediaController mediaController = this.a;
        if (mediaController != null) {
            this.e = true;
            mediaController.getTransportControls().pause();
            L.player.d("MicoMediaControllerProxy pause");
        }
    }

    public void innerPause() {
        MediaController mediaController = this.a;
        if (mediaController != null) {
            mediaController.getTransportControls().pause();
            L.player.d("MicoMediaControllerProxy innerPause");
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void play() {
        if (this.a != null) {
            this.e = false;
            MediaSessionController.getInstance().requestActive(this);
            this.a.getTransportControls().play();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public int getMediaType() {
        return LocalPlayerManager.getInstance().getMediaType();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void setIsActive(boolean z) {
        this.g = z;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void pushMediaState() {
        if (this.a != null) {
            Remote.Response.PlayerStatus playerStatus = LocalPlayerManager.getInstance().getPlayerStatus();
            a(playerStatus);
            c(playerStatus);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void seekTo(long j) {
        MediaController mediaController = this.a;
        if (mediaController != null) {
            mediaController.getTransportControls().seekTo(j);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void stop() {
        MediaController mediaController = this.a;
        if (mediaController != null) {
            mediaController.getTransportControls().stop();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        MediaController mediaController = this.a;
        if (mediaController != null) {
            mediaController.sendCommand(str, bundle, resultReceiver);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void setRating(Rating rating) {
        MediaController mediaController = this.a;
        if (mediaController != null) {
            mediaController.getTransportControls().setRating(rating);
        }
    }

    public boolean isActivite() {
        return this.g;
    }

    public void playMusicList(String str) {
        if (this.a != null) {
            Bundle bundle = new Bundle();
            bundle.putString("data", str);
            this.a.sendCommand(AudioPlayerService.CMD_PLAY_MUSIC_LIST, bundle, null);
        }
    }

    public void toggleLove(boolean z) {
        if (this.a != null) {
            setRating(Rating.newHeartRating(z));
        }
    }

    public void setLoopType(int i) {
        if (this.a != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(AudioPlayerService.ARGS_KEY_LOOP_TYPE, i);
            this.a.sendCommand(AudioPlayerService.CMD_SET_LOOP_TYPE, bundle, null);
        }
    }

    public void pauseAfterFinish(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("offset", i);
        sendCommand(AudioPlayerService.CMD_PAUSE_AFTER_FINISH, bundle, null);
    }

    public void playRecentlyMusic() {
        if (this.a != null) {
            this.a.sendCommand(AudioPlayerService.CMD_PLAY_RECENTLY_MUSIC, new Bundle(), null);
        }
    }

    public void playPatchwallMusic(String str, String str2, int i, int i2) {
        if (this.a != null) {
            Bundle bundle = new Bundle();
            bundle.putString("type", str);
            bundle.putString("id", str2);
            bundle.putInt(AudioPlayerService.ARGS_KEY_LOOP_TYPE, i);
            bundle.putInt("offset", i2);
            this.a.sendCommand(AudioPlayerService.CMD_PLAY_PATCHWALL_MUSIC, bundle, null);
        }
    }

    public void playBabyCourse(String str) {
        L.player.d("playBabyCourse=%s", str);
        if (this.a != null) {
            Bundle bundle = new Bundle();
            bundle.putString("id", str);
            this.a.sendCommand(AudioPlayerService.CMD_PLAY_BABY_COURSE, bundle, null);
        }
    }

    public boolean onResumePlayer() {
        if (!hasHistoryData() || !a() || isManualPaused() || getPlayStatus() == 1) {
            return false;
        }
        L.player.d("MicoMediaControllerProxy onResumePlayer");
        if (this.a == null) {
            return false;
        }
        MediaSessionController.getInstance().requestActive(this);
        this.a.getTransportControls().play();
        return true;
    }

    private boolean a() {
        L.player.d("needReplayWiFiMusicOfBlueIntercept playerPauseTime=%d", Long.valueOf(this.d));
        return System.currentTimeMillis() - this.d < TimeUnit.SECONDS.toMillis(30L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Remote.Response.PlayerStatus playerStatus) {
        MediaMetadata buildMediaMetadata = buildMediaMetadata(playerStatus);
        if (buildMediaMetadata == null) {
            L.player.d("updateMediaMetadata:metadata is null");
            return;
        }
        this.b.notifyMetadataChanged(ConnectType.MICO, buildMediaMetadata);
        b(playerStatus);
    }

    private void b(final Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus.play_song_detail != null && playerStatus.play_song_detail.outCoverBitmap == null && !TextUtils.isEmpty(playerStatus.play_song_detail.cover)) {
            Glide.with(MicoApplication.getApp()).asBitmap().load(playerStatus.play_song_detail.cover).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MicoMediaControllerProxy.1
                /* renamed from: a */
                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    if (MicoMediaControllerProxy.this.isActivite() && bitmap != null) {
                        playerStatus.play_song_detail.outCoverBitmap = CommonUtils.compressBitmapWithNoDistortion(bitmap, 120, 120);
                        MicoMediaControllerProxy.this.b.notifyMetadataChanged(ConnectType.MICO, MicoMediaControllerProxy.buildMediaMetadata(playerStatus));
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Remote.Response.PlayerStatus playerStatus) {
        this.b.notifyPlaybackStateChanged(transformPlaybackState(playerStatus));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a implements LocalPlayerManager.MetadataChangedCallback {
        private int b;

        @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
        public void onMediaListChanged(Remote.Response.PlayerStatus playerStatus) {
        }

        a() {
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
        public void onMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
            if (playerStatus != null && !playerStatus.isMiPlay() && !playerStatus.isBluetooth()) {
                MicoMediaControllerProxy.this.a(playerStatus);
            }
        }

        @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
        public void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus) {
            if (playerStatus == null || playerStatus.isBluetooth() || playerStatus.isMiPlay()) {
                L.player.d("MicoMetadataChangedCallback:onPlayerStateChanged");
                return;
            }
            int i = playerStatus.status;
            if (playerStatus.isPlayingStatus()) {
                MicoMediaControllerProxy.this.b.requestActive(MicoMediaControllerProxy.this);
                MicoMediaControllerProxy.this.d = 0L;
                MicoMediaControllerProxy.this.e = false;
            } else if (playerStatus.isPauseStatus()) {
                MicoMediaControllerProxy.this.d = System.currentTimeMillis();
            }
            if (MicoMediaControllerProxy.this.g && i != this.b) {
                MicoMediaControllerProxy.this.c(playerStatus);
            }
            this.b = i;
        }
    }

    public static int transformPlaybackState(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus.isPlayingStatus()) {
            return 3;
        }
        return playerStatus.isPauseStatus() ? 2 : 1;
    }

    public static MediaMetadata buildMediaMetadata(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus == null) {
            L.player.d("buildMediaMetadata:playerStatus is null");
            return null;
        }
        Remote.Response.PlayingData playingData = playerStatus.play_song_detail;
        if (playingData == null) {
            L.player.d("buildMediaMetadata:playingData is null");
            return null;
        }
        MediaMetadata.Builder builder = new MediaMetadata.Builder();
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, playingData.title);
        builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, playingData.getSubTitle());
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, playingData.albumName);
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, playingData.cover);
        if (playingData.outCoverBitmap != null) {
            builder.putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, playingData.outCoverBitmap);
        }
        return builder.build();
    }

    public void clearAudioBookPlayList() {
        if (!AudioBookPlayListActivity.eventFormDisplayDetail) {
            LocalPlayerManager.getInstance().setStations(null);
        }
        AudioBookPlayListActivity.eventFormDisplayDetail = false;
    }
}
