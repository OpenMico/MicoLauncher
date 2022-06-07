package com.google.android.exoplayer2;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.AudioFocusManager;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class AudioFocusManager {
    private final AudioManager a;
    private final a b;
    @Nullable
    private PlayerControl c;
    @Nullable
    private AudioAttributes d;
    private int f;
    private AudioFocusRequest h;
    private boolean i;
    private float g = 1.0f;
    private int e = 0;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface PlayerCommand {
    }

    /* loaded from: classes.dex */
    public interface PlayerControl {
        void executePlayerCommand(int i);

        void setVolumeMultiplier(float f);
    }

    public AudioFocusManager(Context context, Handler handler, PlayerControl playerControl) {
        this.a = (AudioManager) Assertions.checkNotNull((AudioManager) context.getApplicationContext().getSystemService("audio"));
        this.c = playerControl;
        this.b = new a(handler);
    }

    public float a() {
        return this.g;
    }

    public void a(@Nullable AudioAttributes audioAttributes) {
        if (!Util.areEqual(this.d, audioAttributes)) {
            this.d = audioAttributes;
            this.f = b(audioAttributes);
            int i = this.f;
            boolean z = true;
            if (!(i == 1 || i == 0)) {
                z = false;
            }
            Assertions.checkArgument(z, "Automatic handling of audio focus is only available for USAGE_MEDIA and USAGE_GAME.");
        }
    }

    public int a(boolean z, int i) {
        if (a(i)) {
            d();
            return z ? 1 : -1;
        } else if (z) {
            return c();
        } else {
            return -1;
        }
    }

    public void b() {
        this.c = null;
        d();
    }

    private boolean a(int i) {
        return i == 1 || this.f != 1;
    }

    private int c() {
        if (this.e == 1) {
            return 1;
        }
        if ((Util.SDK_INT >= 26 ? f() : e()) == 1) {
            b(1);
            return 1;
        }
        b(0);
        return -1;
    }

    private void d() {
        if (this.e != 0) {
            if (Util.SDK_INT >= 26) {
                h();
            } else {
                g();
            }
            b(0);
        }
    }

    private int e() {
        return this.a.requestAudioFocus(this.b, Util.getStreamTypeForAudioUsage(((AudioAttributes) Assertions.checkNotNull(this.d)).usage), this.f);
    }

    @RequiresApi(26)
    private int f() {
        AudioFocusRequest.Builder builder;
        if (this.h == null || this.i) {
            AudioFocusRequest audioFocusRequest = this.h;
            if (audioFocusRequest == null) {
                builder = new AudioFocusRequest.Builder(this.f);
            } else {
                builder = new AudioFocusRequest.Builder(audioFocusRequest);
            }
            this.h = builder.setAudioAttributes(((AudioAttributes) Assertions.checkNotNull(this.d)).getAudioAttributesV21()).setWillPauseWhenDucked(i()).setOnAudioFocusChangeListener(this.b).build();
            this.i = false;
        }
        return this.a.requestAudioFocus(this.h);
    }

    private void g() {
        this.a.abandonAudioFocus(this.b);
    }

    @RequiresApi(26)
    private void h() {
        AudioFocusRequest audioFocusRequest = this.h;
        if (audioFocusRequest != null) {
            this.a.abandonAudioFocusRequest(audioFocusRequest);
        }
    }

    private boolean i() {
        AudioAttributes audioAttributes = this.d;
        return audioAttributes != null && audioAttributes.contentType == 1;
    }

    private static int b(@Nullable AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            return 0;
        }
        switch (audioAttributes.usage) {
            case 0:
                Log.w("AudioFocusManager", "Specify a proper usage in the audio attributes for audio focus handling. Using AUDIOFOCUS_GAIN by default.");
                return 1;
            case 1:
            case 14:
                return 1;
            case 2:
            case 4:
                return 2;
            case 3:
                return 0;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
                return 3;
            case 11:
                return audioAttributes.contentType == 1 ? 2 : 3;
            case 15:
            default:
                int i = audioAttributes.usage;
                StringBuilder sb = new StringBuilder(37);
                sb.append("Unidentified audio usage: ");
                sb.append(i);
                Log.w("AudioFocusManager", sb.toString());
                return 0;
            case 16:
                return Util.SDK_INT >= 19 ? 4 : 2;
        }
    }

    private void b(int i) {
        if (this.e != i) {
            this.e = i;
            float f = i == 3 ? 0.2f : 1.0f;
            if (this.g != f) {
                this.g = f;
                PlayerControl playerControl = this.c;
                if (playerControl != null) {
                    playerControl.setVolumeMultiplier(f);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(int i) {
        if (i != 1) {
            switch (i) {
                case -3:
                case -2:
                    if (i == -2 || i()) {
                        d(0);
                        b(2);
                        return;
                    }
                    b(3);
                    return;
                case -1:
                    d(-1);
                    d();
                    return;
                default:
                    StringBuilder sb = new StringBuilder(38);
                    sb.append("Unknown focus change type: ");
                    sb.append(i);
                    Log.w("AudioFocusManager", sb.toString());
                    return;
            }
        } else {
            b(1);
            d(1);
        }
    }

    private void d(int i) {
        PlayerControl playerControl = this.c;
        if (playerControl != null) {
            playerControl.executePlayerCommand(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class a implements AudioManager.OnAudioFocusChangeListener {
        private final Handler b;

        public a(Handler handler) {
            this.b = handler;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(int i) {
            AudioFocusManager.this.c(i);
        }

        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(final int i) {
            this.b.post(new Runnable() { // from class: com.google.android.exoplayer2.-$$Lambda$AudioFocusManager$a$3XB-SY0iYroH3mDCxfS48VHBnes
                @Override // java.lang.Runnable
                public final void run() {
                    AudioFocusManager.a.this.a(i);
                }
            });
        }
    }
}
