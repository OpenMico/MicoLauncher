package com.xiaomi.micolauncher.common.player;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.RawRes;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.BaseExoPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.base.Player;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.skills.common.AsrTtsCard;
import java.io.File;

/* loaded from: classes3.dex */
public final class MultiAudioPlayer implements BaseExoPlayer.PlayerListener {
    private PlayerListener a;
    private BaseExoPlayer b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    private final boolean g;

    /* loaded from: classes3.dex */
    public interface PlayerListener {
        void onComplete();

        void onError(int i, Exception exc);

        default void onPrepared() {
        }
    }

    public MultiAudioPlayer(BasePlayer.StreamType streamType) {
        this(MicoApplication.getGlobalContext(), streamType, false);
    }

    public MultiAudioPlayer(BasePlayer.StreamType streamType, boolean z) {
        this.b = new BaseExoPlayer(MicoApplication.getGlobalContext(), AudioSource.AUDIO_SOURCE_NULL, streamType, true);
        this.b.setListener(this);
        this.g = z;
    }

    public MultiAudioPlayer(Context context, BasePlayer.StreamType streamType) {
        this(context, streamType, false);
    }

    public MultiAudioPlayer(Context context, BasePlayer.StreamType streamType, boolean z) {
        this.b = new BaseExoPlayer(context, AudioSource.AUDIO_SOURCE_NULL, streamType, true);
        this.b.setListener(this);
        this.g = z;
    }

    public void setListener(PlayerListener playerListener) {
        this.a = playerListener;
    }

    public void playUrl(final String str) {
        ttsPlayEnd();
        this.c = false;
        this.d = false;
        this.e = true;
        this.f = true;
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.-$$Lambda$MultiAudioPlayer$CreqfINyFOphX7gLGPlitBWteqA
            @Override // java.lang.Runnable
            public final void run() {
                MultiAudioPlayer.this.a(str);
            }
        });
    }

    public /* synthetic */ void a(String str) {
        this.b.setDataSource(str);
    }

    public void playFile(final File file) {
        ttsPlayEnd();
        this.c = false;
        this.d = false;
        this.e = true;
        this.f = true;
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.-$$Lambda$MultiAudioPlayer$dHg7gFdwdvJDuCeY65fQR02cH6E
            @Override // java.lang.Runnable
            public final void run() {
                MultiAudioPlayer.this.a(file);
            }
        });
    }

    public /* synthetic */ void a(File file) {
        this.b.setDataSource(file);
    }

    public void playRawFile(@RawRes int i, Context context) {
        ttsPlayEnd();
        this.c = false;
        this.d = false;
        this.e = true;
        this.f = true;
        this.b.setDataSource(i, context);
    }

    public void playTts(String str) {
        playTts(str, false);
    }

    public void playTts(String str, boolean z) {
        this.c = true;
        this.d = z;
        this.e = true;
        this.f = true;
        if (z) {
            AsrTtsCard.getInstance().onTtsRequest(str);
        }
        SpeechEventUtil.getInstance().ttsRequest(str, new SpeechEventUtil.TTSListener() { // from class: com.xiaomi.micolauncher.common.player.MultiAudioPlayer.1
            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.TTSListener
            public void onTtsUrl(String str2, String str3) {
                if (MultiAudioPlayer.this.d) {
                    AsrTtsCard.getInstance().onTtsPlayStart(0);
                }
                MultiAudioPlayer.this.b.setDataSource(str2);
            }

            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.TTSListener
            public void onTtsFail(String str2, boolean z2) {
                L.player.e("onTtsFail.byNewRequest=%b, listener=%s", Boolean.valueOf(z2), MultiAudioPlayer.this.a);
                if (MultiAudioPlayer.this.a != null) {
                    MultiAudioPlayer.this.a.onError(-1004, ExoPlaybackException.createForRemote(TextUtils.isEmpty(str2) ? "tts is empty" : "onLoadTtsFail"));
                }
                if (MultiAudioPlayer.this.g) {
                    MultiAudioPlayer.this.b.release();
                }
            }
        });
    }

    public void stop() {
        BaseExoPlayer baseExoPlayer = this.b;
        if (baseExoPlayer != null) {
            baseExoPlayer.stop();
            this.b.reset();
            ttsPlayEnd();
        }
    }

    public void pause() {
        BaseExoPlayer baseExoPlayer = this.b;
        if (baseExoPlayer != null) {
            baseExoPlayer.pause();
        }
    }

    public void start() {
        BaseExoPlayer baseExoPlayer = this.b;
        if (baseExoPlayer != null) {
            baseExoPlayer.start();
        }
    }

    public void release() {
        BaseExoPlayer baseExoPlayer = this.b;
        if (baseExoPlayer != null) {
            baseExoPlayer.release();
        }
    }

    public void setVolume(float f) {
        BaseExoPlayer baseExoPlayer = this.b;
        if (baseExoPlayer != null) {
            baseExoPlayer.setVolume(f);
        }
    }

    public void setPlaying(boolean z) {
        this.f = z;
    }

    public void setLooping(boolean z) {
        BaseExoPlayer baseExoPlayer = this.b;
        if (baseExoPlayer != null) {
            baseExoPlayer.setLooping(z);
        }
    }

    public boolean isPlaying() {
        BaseExoPlayer baseExoPlayer;
        return this.f || ((baseExoPlayer = this.b) != null && baseExoPlayer.isPlaying());
    }

    public boolean isPreparing() {
        return this.e;
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
    public void onPrepared(Player player) {
        this.e = false;
        player.start();
        PlayerListener playerListener = this.a;
        if (playerListener != null) {
            playerListener.onPrepared();
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
    public void onComplete(Player player) {
        this.f = false;
        ttsPlayEnd();
        PlayerListener playerListener = this.a;
        if (playerListener != null) {
            playerListener.onComplete();
        }
        if (this.g && player == this.b) {
            player.release();
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
    public void onError(Player player, int i, Exception exc) {
        this.f = false;
        ttsPlayEnd();
        PlayerListener playerListener = this.a;
        if (playerListener != null) {
            playerListener.onError(i, exc);
        }
        if (this.g && player == this.b) {
            player.release();
        }
    }

    public void ttsPlayEnd() {
        if (this.c && this.d) {
            AsrTtsCard.getInstance().onTtsPlayEnd();
        }
        this.f = false;
    }
}
