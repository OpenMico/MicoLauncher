package com.xiaomi.micolauncher.common.player;

import android.content.Context;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.utils.SoundToneManager;

/* loaded from: classes3.dex */
public class PromptSoundPlayer extends BasePlayer implements BasePlayer.PlayerListener {
    private static volatile PromptSoundPlayer a;
    private CompleteListener b = null;

    /* loaded from: classes3.dex */
    public interface CompleteListener {
        void onFinish();
    }

    private PromptSoundPlayer() {
        super(AudioSource.AUDIO_SOURCE_PROMPT_VOICE);
        setListener(this);
    }

    public static PromptSoundPlayer getInstance() {
        if (a == null) {
            synchronized (PromptSoundPlayer.class) {
                if (a == null) {
                    a = new PromptSoundPlayer();
                }
            }
        }
        return a;
    }

    @Deprecated
    public void play(int i) {
        play(i, (CompleteListener) null);
    }

    public void play(Context context, int i) {
        play(context, i, null, false);
    }

    public void play(Context context, int i, boolean z) {
        play(context, i, null, z);
    }

    @Deprecated
    public void play(int i, CompleteListener completeListener) {
        play(MicoApplication.getGlobalContext(), i, completeListener, false);
    }

    public void play(Context context, int i, CompleteListener completeListener, boolean z) {
        CompleteListener completeListener2;
        if (i <= 0) {
            L.player.e("%s play parameter error, rawId=%d, listener=%s", "[PromptSoundPlayer]: ", Integer.valueOf(i), completeListener);
            return;
        }
        if (isPlaying() && (completeListener2 = this.b) != null) {
            completeListener2.onFinish();
        }
        int toneRawId = SoundToneManager.getToneRawId(i, context);
        this.b = completeListener;
        if (setDataSource(toneRawId, context)) {
            setLooping(z);
            prepareAsync();
        }
        L.player.i("%s play.sound=%s", "[PromptSoundPlayer]: ", Integer.valueOf(toneRawId));
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onPrepared(BasePlayer basePlayer) {
        start();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onComplete(BasePlayer basePlayer) {
        releaseFocus();
        CompleteListener completeListener = this.b;
        if (completeListener != null) {
            completeListener.onFinish();
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onError(BasePlayer basePlayer, int i, int i2) {
        releaseFocus();
        CompleteListener completeListener = this.b;
        if (completeListener != null) {
            completeListener.onFinish();
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer
    public void onStopped() {
        CompleteListener completeListener = this.b;
        if (completeListener != null) {
            completeListener.onFinish();
        }
    }
}
