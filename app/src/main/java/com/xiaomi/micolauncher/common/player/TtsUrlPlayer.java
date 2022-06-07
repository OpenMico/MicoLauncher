package com.xiaomi.micolauncher.common.player;

import android.os.Handler;
import android.text.TextUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.TtsPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;

/* loaded from: classes3.dex */
public class TtsUrlPlayer extends BasePlayer implements TtsPlayer, BasePlayer.PlayerListener {
    private Handler a;
    private String b;
    private String c = null;
    private TtsPlayer.TtsPayload d;

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void disableFocus() {
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void enableFocus() {
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void play(Object obj) {
    }

    public TtsUrlPlayer(Handler handler) {
        super(AudioSource.AUDIO_SOURCE_TTS);
        setListener(this);
        this.a = handler;
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void requestPlay(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            if (TextUtils.isEmpty(str2)) {
                L.player.e("%s requestPlay: Url=%s, ttsContent=%s", "[TtsUrlPlayer]:", str, str2);
                c();
                return;
            }
            String str3 = this.c;
            if (str3 == null || !str3.equals(str) || !isPlaying()) {
                setDataSource(str);
                prepareAsync();
                setLooping(false);
                this.b = str2;
                this.c = str;
                L.player.d("%s requestPlay: url=%s", "[TtsUrlPlayer]:", str);
                return;
            }
            L.player.e("[TtsUrlPlayer]:%s now is playing sample Url=%s", "[TtsUrlPlayer]:", str);
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void requestPlay(TtsPlayer.TtsPayload ttsPayload) {
        if (ttsPayload == null) {
            L.player.e("%s requestPlay payload is null", "[TtsUrlPlayer]:");
            return;
        }
        this.d = ttsPayload;
        requestPlay(ttsPayload.url, ttsPayload.content);
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void requestFinish() {
        L.player.d("%s requestFinish", "[TtsUrlPlayer]:");
        stop();
    }

    private void a(int i) {
        this.a.removeMessages(1001);
        this.a.obtainMessage(1001, i, 0, this.b).sendToTarget();
    }

    private void c() {
        this.a.removeMessages(1002);
        this.a.obtainMessage(1002, this.d).sendToTarget();
        setVolume(1.0f);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer, com.xiaomi.micolauncher.common.player.TtsPlayer
    public boolean isPlaying() {
        return super.isPlaying();
    }

    @Override // com.xiaomi.micolauncher.common.player.TtsPlayer
    public void mute() {
        setVolume(0.0f);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onPrepared(BasePlayer basePlayer) {
        L.player.d("%s onPrepared", "[TtsUrlPlayer]:");
        start();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onComplete(BasePlayer basePlayer) {
        L.player.d("%s onCompletion", "[TtsUrlPlayer]:");
        releaseFocus(1000L);
        c();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onError(BasePlayer basePlayer, int i, int i2) {
        L.player.d("%s onError", "[TtsUrlPlayer]:");
        releaseFocus(1000L);
        c();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer
    public void onStopped() {
        L.player.d("%s onStopped", "[TtsUrlPlayer]:");
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer
    public void onPaused() {
        L.player.d("%s onPaused", "[TtsUrlPlayer]:");
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer
    public void onStarted() {
        L.player.d("%s onStarted", "[TtsUrlPlayer]:");
        a(getDuration());
        QueryLatency.getInstance().setTtsPlayUrlStartMs();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer
    public void releaseFocus() {
        releaseFocus(1000L);
    }
}
