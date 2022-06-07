package com.xiaomi.micolauncher.common.player;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.Constants;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;

/* loaded from: classes3.dex */
public class UrlPlayer extends BasePlayer implements BasePlayer.PlayerListener {
    private static volatile UrlPlayer a;
    private String b = null;
    private Listener c = null;
    private boolean d = false;
    private int e;

    /* loaded from: classes3.dex */
    public interface Listener {
        void onFinish(Constants.PlayErrorCode playErrorCode);
    }

    private UrlPlayer() {
        super(AudioSource.AUDIO_SOURCE_URL_PLAYER);
        setListener(this);
    }

    public static void play(String str, Listener listener, boolean z) {
        if (a == null) {
            synchronized (UrlPlayer.class) {
                if (a == null) {
                    a = new UrlPlayer();
                }
            }
        }
        a.a(str, listener, z);
    }

    public static void play(String str, Listener listener) {
        play(str, listener, false);
    }

    public static void stop(String str) {
        if (a != null) {
            a.a(str);
        }
    }

    private void a(String str, boolean z) {
        L.player.d("%s internalPlay.url=%s, loop=%s", "[UrlPlayer]:", str, Boolean.valueOf(z));
        setDataSource(str);
        prepareAsync();
        setLooping(z);
    }

    private void a(String str, Listener listener, boolean z) {
        if (TextUtils.isEmpty(str)) {
            L.player.e("%s requestPlayAsync: Url=%s", "[UrlPlayer]:", str);
            return;
        }
        String str2 = this.b;
        if (str2 == null || !str2.equals(str) || !isPlaying()) {
            Listener listener2 = this.c;
            if (!(listener2 == null || listener2 == listener)) {
                listener2.onFinish(Constants.PlayErrorCode.RET_LOSS_AUDIO_FOCUS);
            }
            a(str, z);
            this.b = str;
            this.c = listener;
            this.d = z;
            this.e = 0;
            L.player.i("%s requestPlayAsync: url=%s", "[UrlPlayer]:", str);
            return;
        }
        L.player.e("%s now is playing sample Url=%s", "[UrlPlayer]:", str);
    }

    private void a(String str) {
        L.player.i("%s requestFinish", "[UrlPlayer]:");
        String str2 = this.b;
        if (str2 != null && str2.equals(str)) {
            this.c = null;
            this.d = false;
            this.e = 10;
            super.stop();
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onPrepared(BasePlayer basePlayer) {
        L.player.i("%s onPrepared", "[UrlPlayer]:");
        this.e = 0;
        super.start();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onComplete(BasePlayer basePlayer) {
        L.player.i("%s onComplete", "[UrlPlayer]:");
        releaseFocus();
        Listener listener = this.c;
        if (listener != null) {
            listener.onFinish(Constants.PlayErrorCode.RET_FINISH);
        }
        this.c = null;
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
    public void onError(BasePlayer basePlayer, int i, int i2) {
        L.player.i("%s onError, errno=%d, retries=%d, loop=%b, url=%s", "[UrlPlayer]:", Integer.valueOf(i), Integer.valueOf(this.e), Boolean.valueOf(this.d), this.b);
        if (!this.d || this.e >= 10) {
            Listener listener = this.c;
            if (listener != null) {
                listener.onFinish(Constants.PlayErrorCode.RET_PLAY_ERROR);
            }
            this.c = null;
            releaseFocus();
            return;
        }
        a(this.b, true);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer
    public void onStopped() {
        L.player.i("%s onStopped", "[UrlPlayer]:");
        Listener listener = this.c;
        if (listener != null) {
            listener.onFinish(Constants.PlayErrorCode.RET_LOSS_AUDIO_FOCUS);
        }
        this.c = null;
    }

    @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer
    protected void onPaused() {
        L.player.i("%s onPaused", "[UrlPlayer]:");
        Listener listener = this.c;
        if (listener != null) {
            listener.onFinish(Constants.PlayErrorCode.RET_LOSS_AUDIO_FOCUS);
        }
        this.c = null;
    }
}
