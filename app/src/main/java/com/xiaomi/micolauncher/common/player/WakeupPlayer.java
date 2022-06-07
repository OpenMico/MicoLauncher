package com.xiaomi.micolauncher.common.player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.SoundPool;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.common.speech.utils.WakeupInfo;
import com.xiaomi.micolauncher.common.utils.SoundToneManager;

/* loaded from: classes3.dex */
public class WakeupPlayer extends FakePlayer {
    @SuppressLint({"StaticFieldLeak"})
    private static WakeupPlayer a;
    private Context b;
    private SoundPool c;
    private int d;
    private int e;
    private int f;
    private int h;
    private final int j = 17;
    private int g = 0;
    private boolean i = SpeechConfig.muteWakeupSound();

    public static WakeupPlayer getInstance(Context context) {
        if (a == null) {
            synchronized (WakeupPlayer.class) {
                a = new WakeupPlayer(context);
            }
        }
        return a;
    }

    private WakeupPlayer(Context context) {
        super(AudioSource.AUDIO_SOURCE_WAKEUP);
        this.b = context;
        c();
    }

    private void c() {
        if (this.c == null) {
            this.c = new SoundPool(1, 3, 0);
        }
        int i = this.d;
        if (i > 0) {
            this.c.unload(i);
        }
        int i2 = this.e;
        if (i2 > 0) {
            this.c.unload(i2);
        }
        int i3 = this.f;
        if (i3 > 0) {
            this.c.unload(i3);
        }
        SoundPool soundPool = this.c;
        Context context = this.b;
        this.d = soundPool.load(context, SoundToneManager.getToneRawId(R.raw.wakeup_01, context), 1);
        if (this.d == 0) {
            L.speech.d("[WakeupPlayer]:load wakeup_01.wav failed");
        }
        SoundPool soundPool2 = this.c;
        Context context2 = this.b;
        this.e = soundPool2.load(context2, SoundToneManager.getToneRawId(R.raw.wakeup_02, context2), 1);
        if (this.e == 0) {
            L.speech.d("[WakeupPlayer]:load wakeup_02.wav failed");
        }
        SoundPool soundPool3 = this.c;
        Context context3 = this.b;
        this.f = soundPool3.load(context3, SoundToneManager.getToneRawId(R.raw.wakeup_wozai, context3), 1);
        if (this.f == 0) {
            L.speech.d("[WakeupPlayer]:load wakeup_wozai.wav failed");
        }
        if (this.h == 0) {
            this.h = this.c.load(this.b, R.raw.wakeup_cmd, 1);
            if (this.h == 0) {
                L.speech.d("[WakeupPlayer]:load wakeup_cmd.wav failed");
            }
        }
    }

    public void requestPlayAsync(boolean z, boolean z2) {
        int i = this.g;
        if (i != 0) {
            this.c.stop(i);
        }
        if (z2) {
            this.g = 0;
        } else if (z) {
            this.g = this.h;
        } else {
            int currentTimeMillis = (int) ((System.currentTimeMillis() / 1000) % 3);
            L.player.d("%s requestPlayAsync play_id=%d", "[WakeupPlayer]:", Integer.valueOf(currentTimeMillis));
            if (currentTimeMillis != 3) {
                switch (currentTimeMillis) {
                    case 0:
                        this.g = this.d;
                        break;
                    case 1:
                        this.g = this.e;
                        break;
                    default:
                        this.g = this.f;
                        break;
                }
            } else {
                this.g = this.f;
            }
        }
        start();
        L.player.d("%s requestPlayAsync tone=%s mute=%s", "[WakeupPlayer]:", Boolean.valueOf(z), Boolean.valueOf(z2));
    }

    public void requestFinish() {
        L.player.d("[WakeupPlayer]:requestFinish");
        releaseFocus(1000L);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
    public void postStart() {
        if (this.c != null && this.g > 0 && !this.i) {
            int currentToneOrder = SoundToneManager.getCurrentToneOrder();
            int d = d();
            L.player.d("%s WakeupSound.play toneOrder=%d resOrder=%d", "[WakeupPlayer]:", Integer.valueOf(currentToneOrder), Integer.valueOf(d));
            int vpmPlayWakeup = SpeechManager.getInstance().vpmPlayWakeup(currentToneOrder, d);
            L.player.d("%s WakeupSound.play ret=%d", "[WakeupPlayer]:", Integer.valueOf(vpmPlayWakeup));
            if (17 != vpmPlayWakeup) {
                this.c.play(this.g, 1.0f, 1.0f, 0, 0, 1.0f);
            }
            WakeupInfo.setPlaySoundAtMs();
            QueryLatency.getInstance().setPlaySoundAtMs();
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
    public void postStop(AudioSource audioSource) {
        int i;
        SoundPool soundPool = this.c;
        if (soundPool != null && (i = this.g) > 0) {
            soundPool.stop(i);
        }
    }

    public static void onToneVendorChanged(Context context) {
        getInstance(context).c();
    }

    private int d() {
        int i = this.g;
        if (i == this.d) {
            return 10;
        }
        if (i == this.e) {
            return 8;
        }
        return i == this.f ? 7 : -1;
    }
}
