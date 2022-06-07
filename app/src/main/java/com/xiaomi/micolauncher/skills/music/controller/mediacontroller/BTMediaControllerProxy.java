package com.xiaomi.micolauncher.skills.music.controller.mediacontroller;

import android.content.Context;
import android.content.Intent;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.ConnectType;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class BTMediaControllerProxy implements MediaControllerProxy {
    private final MediaSessionController a;
    private MediaController b;
    private final Context d;
    private boolean e;
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private Remote.Response.PlayerStatus j;
    private Remote.Response.PlayingData k;
    private int l;
    private long m;
    public CharSequence mLastDes;
    public long mLastDuration;
    private long n;
    private final MediaController.Callback c = new a();
    private Handler.Callback p = new Handler.Callback() { // from class: com.xiaomi.micolauncher.skills.music.controller.mediacontroller.BTMediaControllerProxy.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 10) {
                boolean a2 = BTMediaControllerProxy.this.a();
                L.player.d("BTMediaControllerProxy canResume=%b", Boolean.valueOf(a2));
                if (a2) {
                    MediaSessionController.getInstance().playWiFiAfterBT();
                }
            }
            return false;
        }
    };
    private WeakRefHandler o = new WeakRefHandler(this.p);

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public int getMediaType() {
        return 20;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public long getSongDuration() {
        return 0L;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public boolean hasHistoryData() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void setVolume(int i) {
    }

    public BTMediaControllerProxy(Context context, MediaSessionController mediaSessionController, MediaController mediaController) {
        this.d = context;
        this.a = mediaSessionController;
        this.b = mediaController;
        this.b.registerCallback(this.c);
        L.player.d("BTMediaControllerProxy create");
    }

    public void resetMediaController(MediaController mediaController) {
        MediaController mediaController2 = this.b;
        if (mediaController2 != null) {
            mediaController2.unregisterCallback(this.c);
        }
        if (this.b != mediaController) {
            this.b = mediaController;
            this.b.registerCallback(this.c);
            L.player.d("BTMediaControllerProxy resetMediaController %s", mediaController);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void release() {
        this.b.unregisterCallback(this.c);
        this.e = true;
        MediaSessionController.getInstance().resetBTMediaController();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public int getPlayStatus() {
        return this.b.getPlaybackState().getState() == 3 ? 1 : 2;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public Remote.Response.PlayingData getCurrentPlayingData() {
        if (this.k == null) {
            this.k = new Remote.Response.PlayingData();
        }
        MediaMetadata metadata = this.b.getMetadata();
        this.k.title = metadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE);
        this.k.artist = metadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST);
        this.k.albumName = metadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST);
        this.k.duration = metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
        this.k.screenExtend.mediaType = getMediaType();
        return this.k;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public boolean isReleased() {
        return this.e;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void setIsActive(boolean z) {
        L.player.i("BTMediaControllerProxy setIsActive %b", Boolean.valueOf(z));
        this.f = z;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public List<Remote.Response.TrackData> getPlaylist() {
        ArrayList arrayList = new ArrayList();
        if (this.b.getQueue() != null) {
            for (MediaSession.QueueItem queueItem : this.b.getQueue()) {
                Remote.Response.TrackData trackData = new Remote.Response.TrackData();
                MediaDescription description = queueItem.getDescription();
                trackData.title = (String) description.getTitle();
                trackData.artist = (String) description.getSubtitle();
                arrayList.add(trackData);
            }
        } else if (this.b.getMetadata() != null) {
            Remote.Response.TrackData trackData2 = new Remote.Response.TrackData();
            MediaMetadata metadata = this.b.getMetadata();
            trackData2.title = metadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE);
            trackData2.artist = metadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST);
            arrayList.add(trackData2);
        }
        return arrayList;
    }

    public int getLoopType() {
        return LoopType.LIST_LOOP.ordinal();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void skipToNext() {
        if (this.b != null) {
            this.i = true;
            L.player.d("BTMediaControllerProxy skipToNext");
            this.b.getTransportControls().skipToNext();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void skipToPrevious() {
        MediaController mediaController = this.b;
        if (mediaController != null) {
            this.i = true;
            mediaController.getTransportControls().skipToPrevious();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void pause() {
        this.g = false;
        MediaController mediaController = this.b;
        if (mediaController != null) {
            mediaController.getTransportControls().pause();
            L.player.d("BTMediaControllerProxy pause");
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void play() {
        L.player.d("BTMediaControllerProxy play");
        MediaController mediaController = this.b;
        if (mediaController != null) {
            mediaController.getTransportControls().play();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void seekTo(long j) {
        MediaController mediaController = this.b;
        if (mediaController != null) {
            mediaController.getTransportControls().seekTo(j);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void pushMediaState() {
        MediaController mediaController = this.b;
        if (mediaController != null) {
            a(mediaController.getMetadata());
            if (this.b.getPlaybackState() != null) {
                a(this.b.getPlaybackState().getState());
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void stop() {
        MediaController mediaController = this.b;
        if (mediaController != null) {
            mediaController.getTransportControls().stop();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        MediaController mediaController = this.b;
        if (mediaController != null) {
            mediaController.sendCommand(str, bundle, resultReceiver);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public void setRating(Rating rating) {
        MediaController mediaController = this.b;
        if (mediaController != null) {
            mediaController.getTransportControls().setRating(rating);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a() {
        Logger logger = L.player;
        logger.d("BTMediaControllerProxy willPlayNext=" + this.i);
        int i = this.l;
        return (i == 2 || i == 1) && !this.i && !VideoMediaSession.getInstance().isPlaying() && !VoipModel.getInstance().isVoipActive() && !MediaSessionController.getInstance().isMiplayUsing();
    }

    public void clearWhenBtDisconnect() {
        this.a.notifyMetadataChanged(ConnectType.BLUETOOTH, buildEmptyMediaMetadata());
    }

    public boolean isPlayingWhenDisconnect() {
        L.player.d("BTMediaControllerProxy isPlayingWhenDisconnect lastPlayebackState: %s", Integer.valueOf(this.l));
        int i = this.l;
        if (i == 3) {
            return true;
        }
        if (i != 2 && i != 1 && i != 7) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis() - this.m;
        L.player.d("interval: %s", Long.valueOf(currentTimeMillis));
        return currentTimeMillis < SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(boolean z) {
        L.player.d("BTMediaControllerProxy needReplayWiFiMusicOfTime stateChange=%b, autoPause=%b", Boolean.valueOf(z), Boolean.valueOf(this.g));
        if (this.g) {
            return false;
        }
        if (!z) {
            return !this.f;
        }
        Logger logger = L.player;
        logger.d("BTMediaControllerProxy needReplayWiFiMusicOfTime startPlayTime=" + this.n);
        long currentTimeMillis = System.currentTimeMillis() - this.n;
        Logger logger2 = L.player;
        logger2.d("BTMediaControllerProxy needReplayWiFiMusicOfTime interval=" + currentTimeMillis);
        this.n = 0L;
        return currentTimeMillis < TimeUnit.SECONDS.toMillis(30L);
    }

    private void a(MediaMetadata mediaMetadata) {
        if (mediaMetadata == null || this.d == null) {
            L.player.w("BTMediaControllerProxy send Media Metadata is null");
            return;
        }
        L.player.d("BTMediaControllerProxy sendMediaMetadata");
        Intent intent = new Intent(PlayerEvent.ACTION_MUSIC_META_CHANGE);
        intent.putExtra(PlayerEvent.METADATA, mediaMetadata);
        intent.putExtra("source", PlayerEvent.MUSIC_SOURCE_BT);
        this.d.sendBroadcast(intent);
    }

    private void a(int i) {
        if (this.d != null) {
            L.player.d("BTMediaControllerProxy sendPlayState");
            Intent intent = new Intent(PlayerEvent.ACTION_MUSIC_STATE_CHANGE);
            intent.putExtra(PlayerEvent.PLAYSTATE, i);
            this.d.sendBroadcast(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (this.l == 3) {
            L.player.d("!!! updateLastPausedTime: %s", Long.valueOf(this.m));
            this.m = System.currentTimeMillis();
        }
    }

    /* loaded from: classes3.dex */
    private class a extends MediaController.Callback {
        FakePlayer a;

        private a() {
            this.a = new FakePlayer(AudioSource.AUDIO_SOURCE_MUSIC_BT) { // from class: com.xiaomi.micolauncher.skills.music.controller.mediacontroller.BTMediaControllerProxy.a.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
                public void postStart() {
                    L.player.i("BTMediaControllerProxy postStart");
                    BTMediaControllerProxy.this.g = false;
                    BTMediaControllerProxy.this.h = false;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
                public void postStop(AudioSource audioSource) {
                    L.player.i("BTMediaControllerProxy postStop");
                    if (BTMediaControllerProxy.this.b != null) {
                        BTMediaControllerProxy.this.g = false;
                    }
                    BTMediaControllerProxy.this.mLastDes = "";
                    BTMediaControllerProxy.this.mLastDuration = 0L;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
                public void suspend(AudioSource audioSource) {
                    PlaybackState playbackState;
                    L.player.i("BTMediaControllerProxy suspend");
                    if (BTMediaControllerProxy.this.b != null && (playbackState = BTMediaControllerProxy.this.b.getPlaybackState()) != null) {
                        L.player.d("BTMediaControllerProxy suspend.state=%d", Integer.valueOf(playbackState.getState()));
                        if (playbackState.getState() == 3) {
                            BTMediaControllerProxy.this.g = true;
                            BTMediaControllerProxy.this.b.getTransportControls().pause();
                        }
                    }
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
                public void resume(AudioSource audioSource) {
                    L.player.i("BTMediaControllerProxy resume, autoPause=%b", Boolean.valueOf(BTMediaControllerProxy.this.g));
                    if (BTMediaControllerProxy.this.b != null) {
                        if (BTMediaControllerProxy.this.g) {
                            BTMediaControllerProxy.this.b.getTransportControls().play();
                        }
                        BTMediaControllerProxy.this.g = false;
                    }
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
                public void background(AudioSource audioSource) {
                    L.player.i("BTMediaControllerProxy background");
                    if (BTMediaControllerProxy.this.b != null) {
                        BTMediaControllerProxy.this.g = true;
                    }
                }

                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
                protected void foreground(AudioSource audioSource) {
                    L.player.i("BTMediaControllerProxy foreground");
                    if (BTMediaControllerProxy.this.b != null) {
                        BTMediaControllerProxy.this.g = false;
                    }
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
                public void forceStop(AudioSource audioSource) {
                    L.player.i("BTMediaControllerProxy forceStop");
                    super.forceStop(audioSource);
                    BTMediaControllerProxy.this.h = true;
                }

                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
                protected void playbackControl(PlaybackControllerInfo playbackControllerInfo) {
                    L.player.d("BT PlaybackControl=%s", playbackControllerInfo);
                    if (playbackControllerInfo != null) {
                        switch (playbackControllerInfo.playbackControl) {
                            case PLAY:
                            case CONTINUE_PLAYING:
                                PlayerApi.play();
                                return;
                            case STOP:
                            case PAUSE:
                                PlayerApi.pause();
                                return;
                            case PREV:
                                PlayerApi.prev();
                                return;
                            case NEXT:
                                PlayerApi.next();
                                return;
                            case SEEK:
                            case STOP_AFTER:
                            case CANCEL_STOP_AFTER:
                            default:
                                return;
                            case START_OVER:
                                BTMediaControllerProxy.this.seekTo(0L);
                                return;
                        }
                    }
                }
            };
        }

        @Override // android.media.session.MediaController.Callback
        public void onSessionDestroyed() {
            super.onSessionDestroyed();
            L.player.e("BTMediaControllerProxy BTMediaControllerCallback onSessionDestroyed");
            BTMediaControllerProxy.this.release();
        }

        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(PlaybackState playbackState) {
            if (playbackState == null) {
                L.player.e("BTMediaControllerProxy BTMediaControllerCallback onPlaybackStateChanged: bt.play error, state = null");
                return;
            }
            int state = playbackState.getState();
            L.player.d("BTMediaControllerProxy BTMediaControllerCallback currentPlayState=%d, lastState=%d,mIsActivite=%b", Integer.valueOf(state), Integer.valueOf(BTMediaControllerProxy.this.l), Boolean.valueOf(BTMediaControllerProxy.this.f));
            if (state == 3) {
                if (b(state) || BTMediaControllerProxy.this.h) {
                    EventBusRegistry.getEventBus().post(new PlayerEvent.ClosePlayerListActivityV2Event());
                    b();
                    BTMediaControllerProxy.this.a.requestActive(BTMediaControllerProxy.this);
                    this.a.setIgnoreForceStop(AudioSource.AUDIO_SOURCE_POWER_KEY);
                    this.a.start();
                }
                c();
            } else if (state == 2 || state == 1) {
                if (BTMediaControllerProxy.this.a(b(state))) {
                    a();
                }
                BTMediaControllerProxy.this.b();
            } else if (state == 7) {
                BTMediaControllerProxy.this.b();
            }
            if (BTMediaControllerProxy.this.f) {
                if (state != BTMediaControllerProxy.this.l) {
                    BTMediaControllerProxy.this.a.notifyPlaybackStateChanged(playbackState.getState());
                }
                LocalPlayerManager.getInstance().notifyPlaybackStateChanged(BTMediaControllerProxy.this.getPlayerStatus());
            }
            a(state);
            BTMediaControllerProxy.this.l = state;
        }

        private void a(int i) {
            if (!b(i)) {
                return;
            }
            if (i == 3) {
                StatPoints.recordPoint(StatPoints.Event.player_bt, StatPoints.MediaplayerKey.play, "1");
                return;
            }
            StatPoints.recordPoint(StatPoints.Event.player_bt, StatPoints.MediaplayerKey.pause, "1");
            StatPoints.recordPoint(StatPoints.Event.player_bt, StatPoints.MediaplayerKey.duration, String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - BTMediaControllerProxy.this.n)));
        }

        private void a() {
            L.player.d("BTMediaControllerProxy addTaskOfResumeWiFiPayer");
            BTMediaControllerProxy.this.o.removeMessages(10);
            BTMediaControllerProxy.this.o.sendEmptyMessageDelayed(10, 1500L);
        }

        private void b() {
            L.player.d("removeTaskOfResumeWiFiPayer");
            BTMediaControllerProxy.this.o.removeMessages(10);
        }

        private boolean b(int i) {
            return BTMediaControllerProxy.this.l != i;
        }

        @Override // android.media.session.MediaController.Callback
        public void onMetadataChanged(MediaMetadata mediaMetadata) {
            boolean z = true;
            L.player.d("BTMediaControllerProxy onMetadataChanged: mIsActivite=%b", Boolean.valueOf(BTMediaControllerProxy.this.f));
            if (BTMediaControllerProxy.this.f) {
                if (BTMediaControllerProxy.this.i) {
                    BTMediaControllerProxy.this.i = false;
                }
                long j = mediaMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
                CharSequence description = mediaMetadata.getDescription().getDescription();
                if (!TextUtils.equals(BTMediaControllerProxy.this.mLastDes, description) || BTMediaControllerProxy.this.mLastDuration != j) {
                    z = false;
                }
                if (!z) {
                    BTMediaControllerProxy.this.a.notifyMetadataChanged(ConnectType.BLUETOOTH, mediaMetadata);
                    LocalPlayerManager.getInstance().notifyMediaMetadataChanged(BTMediaControllerProxy.this.getPlayerStatus());
                }
                BTMediaControllerProxy bTMediaControllerProxy = BTMediaControllerProxy.this;
                bTMediaControllerProxy.mLastDes = description;
                bTMediaControllerProxy.mLastDuration = j;
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onSessionEvent(@NonNull String str, Bundle bundle) {
            L.player.e("BTMediaControllerProxy onSessionEvent: BTMediaControllerProxy");
            super.onSessionEvent(str, bundle);
        }

        @Override // android.media.session.MediaController.Callback
        public void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            L.player.e("BTMediaControllerProxy onAudioInfoChanged: BTMediaControllerProxy");
            super.onAudioInfoChanged(playbackInfo);
        }

        private void c() {
            if (BTMediaControllerProxy.this.l != 3) {
                BTMediaControllerProxy.this.n = System.currentTimeMillis();
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.mediacontroller.MediaControllerProxy
    public Remote.Response.PlayerStatus getPlayerStatus() {
        if (this.j == null) {
            this.j = new Remote.Response.PlayerStatus();
        }
        this.j.status = getPlayStatus();
        this.j.loop_type = getLoopType();
        this.j.media_type = getMediaType();
        this.j.extra_track_list = getPlaylist();
        this.j.play_song_detail = getCurrentPlayingData();
        MediaController mediaController = this.b;
        if (mediaController != null) {
            int maxVolume = mediaController.getPlaybackInfo().getMaxVolume();
            this.j.volume = (this.b.getPlaybackInfo().getCurrentVolume() * 100) / maxVolume;
        }
        return this.j;
    }

    public static MediaMetadata buildEmptyMediaMetadata() {
        MediaMetadata.Builder builder = new MediaMetadata.Builder();
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, "");
        builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "");
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "");
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, null);
        return builder.build();
    }
}
