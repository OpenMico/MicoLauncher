package androidx.media;

import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* loaded from: classes.dex */
public final class AudioManagerCompat {
    public static final int AUDIOFOCUS_GAIN = 1;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE = 4;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;

    public static int requestAudioFocus(@NonNull AudioManager audioManager, @NonNull AudioFocusRequestCompat audioFocusRequestCompat) {
        if (audioManager == null) {
            throw new IllegalArgumentException("AudioManager must not be null");
        } else if (audioFocusRequestCompat == null) {
            throw new IllegalArgumentException("AudioFocusRequestCompat must not be null");
        } else if (Build.VERSION.SDK_INT >= 26) {
            return a.b(audioManager, audioFocusRequestCompat.b());
        } else {
            return audioManager.requestAudioFocus(audioFocusRequestCompat.getOnAudioFocusChangeListener(), audioFocusRequestCompat.getAudioAttributesCompat().getLegacyStreamType(), audioFocusRequestCompat.getFocusGain());
        }
    }

    public static int abandonAudioFocusRequest(@NonNull AudioManager audioManager, @NonNull AudioFocusRequestCompat audioFocusRequestCompat) {
        if (audioManager == null) {
            throw new IllegalArgumentException("AudioManager must not be null");
        } else if (audioFocusRequestCompat == null) {
            throw new IllegalArgumentException("AudioFocusRequestCompat must not be null");
        } else if (Build.VERSION.SDK_INT >= 26) {
            return a.a(audioManager, audioFocusRequestCompat.b());
        } else {
            return audioManager.abandonAudioFocus(audioFocusRequestCompat.getOnAudioFocusChangeListener());
        }
    }

    @IntRange(from = 0)
    public static int getStreamMaxVolume(@NonNull AudioManager audioManager, int i) {
        return audioManager.getStreamMaxVolume(i);
    }

    @IntRange(from = 0)
    public static int getStreamMinVolume(@NonNull AudioManager audioManager, int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            return b.a(audioManager, i);
        }
        return 0;
    }

    private AudioManagerCompat() {
    }

    @RequiresApi(26)
    /* loaded from: classes.dex */
    private static class a {
        @DoNotInline
        static int a(AudioManager audioManager, AudioFocusRequest audioFocusRequest) {
            return audioManager.abandonAudioFocusRequest(audioFocusRequest);
        }

        @DoNotInline
        static int b(AudioManager audioManager, AudioFocusRequest audioFocusRequest) {
            return audioManager.requestAudioFocus(audioFocusRequest);
        }
    }

    @RequiresApi(28)
    /* loaded from: classes.dex */
    private static class b {
        @DoNotInline
        static int a(AudioManager audioManager, int i) {
            return audioManager.getStreamMinVolume(i);
        }
    }
}
