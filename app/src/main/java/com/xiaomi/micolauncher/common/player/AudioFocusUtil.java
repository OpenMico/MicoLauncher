package com.xiaomi.micolauncher.common.player;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Handler;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class AudioFocusUtil {
    private static AudioFocusUtil a;
    private AudioManager b;
    private String c;
    private boolean d;
    private boolean e;
    private int f;
    private AudioFocusListener g;
    private final AudioManager.OnAudioFocusChangeListener j = new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaomi.micolauncher.common.player.AudioFocusUtil.1
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i) {
            L.player.d("[AudioFocusUtil]: %s[%s], onAudioFocusChange: -----change=%s", "[AudioFocusUtil]: ", AudioFocusUtil.this.c, Integer.valueOf(i));
            if (i != 1) {
                switch (i) {
                    case -3:
                    case -2:
                    case -1:
                        if (AudioFocusUtil.this.d) {
                            AudioFocusUtil.this.a(false);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private Handler h = new Handler(ThreadUtil.getLightWorkHandler().getLooper());
    private Runnable i = new Runnable() { // from class: com.xiaomi.micolauncher.common.player.-$$Lambda$AudioFocusUtil$5gLGmF02-WGlm6egI3tibKk_88E
        @Override // java.lang.Runnable
        public final void run() {
            AudioFocusUtil.this.a();
        }
    };

    /* loaded from: classes3.dex */
    public interface AudioFocusListener {
        void onLoss(boolean z);
    }

    public AudioFocusUtil(Context context, String str, boolean z, int i) {
        this.b = (AudioManager) context.getSystemService("audio");
        this.c = str;
        this.d = z;
        this.f = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() {
        if (this.b != null) {
            L.player.d("%s mDelayAbandonAudioFocus.run", "[AudioFocusUtil]: ");
            this.b.abandonAudioFocus(this.j);
        }
    }

    public static AudioFocusUtil getInstance() {
        if (a == null) {
            a = new AudioFocusUtil(MicoApplication.getGlobalContext(), "Instance", true, 3);
        }
        return a;
    }

    public boolean requestAudioFocus(boolean z, int i) {
        if (this.b == null) {
            return false;
        }
        this.e = this.b.requestAudioFocus(new AudioFocusRequest.Builder(i).setOnAudioFocusChangeListener(this.j).setWillPauseWhenDucked(false).setAcceptsDelayedFocusGain(true).setAudioAttributes(new AudioAttributes.Builder().setContentType(2).setUsage(1).build()).build()) == 1;
        this.h.removeCallbacks(this.i);
        if (this.e && z) {
            this.h.postDelayed(this.i, 3000L);
        }
        L.player.d("%s[%s], requestAudioFocus, mFocus=%s", "[AudioFocusUtil]: ", this.c, Boolean.valueOf(this.e));
        return this.e;
    }

    public boolean requestAudioFocus(boolean z) {
        return requestAudioFocus(z, this.f);
    }

    public boolean requestAudioFocus() {
        this.h.removeCallbacks(this.i);
        return requestAudioFocus(true, this.f);
    }

    public void abandonAudioFocus() {
        if (this.e) {
            L.player.d("%s[%s], abandonAudioFocus", "[AudioFocusUtil]: ", this.c);
            a(true);
        }
    }

    public void abandonAudioFocusDelayed(int i) {
        this.h.removeCallbacks(this.i);
        if (i <= 0) {
            abandonAudioFocus();
        } else if (this.e) {
            L.player.d("%s[%s], abandonAudioFocusDelayed.ms=%d", "[AudioFocusUtil]: ", this.c, Integer.valueOf(i));
            AudioFocusListener audioFocusListener = this.g;
            if (audioFocusListener != null) {
                audioFocusListener.onLoss(true);
            }
            this.e = false;
            this.h.postDelayed(this.i, i);
        }
    }

    public void setListener(AudioFocusListener audioFocusListener) {
        this.g = audioFocusListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        AudioManager audioManager = this.b;
        if (audioManager != null) {
            audioManager.abandonAudioFocus(this.j);
        }
        AudioFocusListener audioFocusListener = this.g;
        if (audioFocusListener != null) {
            audioFocusListener.onLoss(z);
        }
        this.e = false;
    }

    public boolean isOnFocus() {
        return this.e;
    }
}
