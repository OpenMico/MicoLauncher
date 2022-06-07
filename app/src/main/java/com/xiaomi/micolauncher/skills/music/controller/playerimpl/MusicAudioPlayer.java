package com.xiaomi.micolauncher.skills.music.controller.playerimpl;

import android.content.Context;
import com.elvishew.xlog.Logger;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.BaseExoPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.base.Player;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayer;
import com.xiaomi.micolauncher.skills.music.controller.AudioPlayerListener;
import com.xiaomi.micolauncher.skills.music.controller.PlayerControlCallback;
import com.xiaomi.micolauncher.skills.music.controller.playerimpl.MusicAudioPlayer;

/* loaded from: classes3.dex */
public class MusicAudioPlayer implements BaseExoPlayer.PlayerListener, AudioPlayer {
    private BaseExoPlayer a;
    private AudioPlayerListener b;
    private PlayerControlCallback c;
    private String d = "";
    private Context e;
    private boolean f;

    private boolean b() {
        return true;
    }

    public MusicAudioPlayer(Context context) {
        this.e = context;
        a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.skills.music.controller.playerimpl.MusicAudioPlayer$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 extends BaseExoPlayer {
        AnonymousClass1(Context context, AudioSource audioSource, BasePlayer.StreamType streamType, boolean z) {
            super(context, audioSource, streamType, z);
        }

        @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
        protected void onPaused() {
            L.player.d("MusicAudioPlayer onPaused");
            if (MusicAudioPlayer.this.b != null) {
                MusicAudioPlayer.this.b.onPaused();
            }
            MusicAudioPlayer.this.setPaused(true);
        }

        @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
        protected void onStarted() {
            L.player.d("MusicAudioPlayer onStarted");
            if (MusicAudioPlayer.this.a.getCurrentVolume() != 1.0f) {
                setVolume(1.0f);
            }
            if (MusicAudioPlayer.this.b != null) {
                MusicAudioPlayer.this.b.onStarted();
            }
            MusicAudioPlayer.this.setPaused(false);
        }

        @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
        protected void onStopped() {
            L.player.d("MusicAudioPlayer onStopped");
            if (MusicAudioPlayer.this.b != null) {
                MusicAudioPlayer.this.b.onStopped();
            }
            MusicAudioPlayer.this.setPaused(true);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
        public void onBackground() {
            super.onBackground();
            L.player.d("MusicAudioPlayer onBackground");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
        public void onForeground() {
            super.onForeground();
            L.player.d("MusicAudioPlayer onForeground");
        }

        @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
        protected void onSuspend() {
            L.player.d("MusicAudioPlayer onSuspend");
        }

        @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
        protected void onResume() {
            L.player.d("MusicAudioPlayer onResume");
        }

        @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
        public void onPlaybackControl(final PlaybackControllerInfo playbackControllerInfo) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.controller.playerimpl.-$$Lambda$MusicAudioPlayer$1$3ujuNHQpF0gPv8SK5DZCuE5WJ_c
                @Override // java.lang.Runnable
                public final void run() {
                    MusicAudioPlayer.AnonymousClass1.this.a(playbackControllerInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(PlaybackControllerInfo playbackControllerInfo) {
            if (MusicAudioPlayer.this.c != null) {
                MusicAudioPlayer.this.c.onControlCommand(playbackControllerInfo);
            }
        }
    }

    private void a() {
        L.player.i("MusicAudioPlayer initPlayer");
        this.a = new AnonymousClass1(this.e, AudioSource.AUDIO_SOURCE_MUSIC, BasePlayer.StreamType.STREAM_TYPE_MUSIC, true);
        this.a.setListener(this);
        this.a.setPrepareTimeOut(Constants.TimeConstants.SECONDS_10S_TO_MILLIS);
        if (b()) {
            this.a.setIgnoreForceStop(AudioSource.AUDIO_SOURCE_POWER_KEY);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public boolean isPlaying() {
        try {
            return this.a.isPlaying();
        } catch (Exception e) {
            L.player.e("isPlaying", e);
            return false;
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public void stop() {
        try {
            L.player.w("MusicAudioPlayer stop()");
            this.a.stop();
            this.a.reset();
            setPaused(true);
        } catch (Exception e) {
            L.player.e("stop", e);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public void play(String str) {
        this.d = str;
        Logger logger = L.player;
        logger.i("MusicAudioPlayer try to play url: " + str);
        if (this.a.isPlaying()) {
            this.a.pause();
            L.player.d("MusicAudioPlayer stop before setDataSource");
        }
        setPaused(false);
        this.a.setDataSource(str);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public int getCurrentPosition() {
        try {
            if (this.a.isPrepared()) {
                return this.a.getCurrentPosition();
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public void pause() {
        L.player.i("MusicAudioPlayer pause()");
        try {
            setPaused(true);
            if (this.a.isPrepared()) {
                this.a.pause();
            }
        } catch (Exception e) {
            L.player.e("MusicAudioPlayer pause error ", e);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public void resume() {
        L.player.i("MusicAudioPlayer resume()");
        try {
            setPaused(false);
            if (this.a.isPrepared()) {
                this.a.start();
                L.player.d("MusicAudioPlayer resume(),start");
            } else if (this.b != null) {
                this.b.onError(1, ExoPlaybackException.createForRemote("not prepared when resume"));
            }
        } catch (Exception e) {
            stop();
            onError(this.a, 1, e);
            L.player.e("MusicAudioPlayer resume failed", e);
        }
    }

    public void setPaused(boolean z) {
        this.f = z;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public boolean isPaused() {
        return this.f;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public void release() {
        L.player.w("MusicAudioPlayer release");
        this.a.release();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public void reset() {
        L.player.w("MusicAudioPlayer reset");
        this.a.reset();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public void setListener(AudioPlayerListener audioPlayerListener) {
        this.b = audioPlayerListener;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public void setPlayerControlCallback(PlayerControlCallback playerControlCallback) {
        this.c = playerControlCallback;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public void setVolume(float f) {
        BaseExoPlayer baseExoPlayer = this.a;
        if (baseExoPlayer != null) {
            try {
                baseExoPlayer.setVolume(f);
            } catch (Exception e) {
                Logger logger = L.player;
                logger.e("MusicAudioPlayer::setVolume" + e);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public long getDuration() {
        try {
            if (this.a.isPrepared()) {
                return this.a.getDuration();
            }
            return 0L;
        } catch (Exception e) {
            L.player.e("getDuration", e);
            return 0L;
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public void seekTo(long j) {
        this.a.seekTo((int) j);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
    public void onPrepared(Player player) {
        L.player.d("onPrepared %s", this.d);
        if (!this.f) {
            L.player.d("MusicAudioPlayer:onPrepared start");
            this.a.start();
        }
        AudioPlayerListener audioPlayerListener = this.b;
        if (audioPlayerListener != null) {
            audioPlayerListener.onPrepared();
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
    public void onComplete(Player player) {
        AudioPlayerListener audioPlayerListener = this.b;
        if (audioPlayerListener != null) {
            audioPlayerListener.onComplete();
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
    public void onError(Player player, int i, Exception exc) {
        L.player.e("MusicAudioPlayer player error paused=%s error=%d extra=%s mUrl=%s", Boolean.valueOf(this.f), Integer.valueOf(i), exc, this.d);
        if (isPaused()) {
            L.player.i("onError isPaused");
            return;
        }
        AudioPlayerListener audioPlayerListener = this.b;
        if (audioPlayerListener == null) {
            return;
        }
        if (i == -110) {
            audioPlayerListener.onPrepareTimeout();
        } else {
            audioPlayerListener.onError(i, exc);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public boolean isManualPause() {
        return this.f;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.AudioPlayer
    public String getResourcePath() {
        return this.d;
    }
}
