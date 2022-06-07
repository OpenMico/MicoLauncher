package androidx.media;

import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.util.ObjectsCompat;
import androidx.media.AudioAttributesCompat;

/* loaded from: classes.dex */
public class AudioFocusRequestCompat {
    static final AudioAttributesCompat a = new AudioAttributesCompat.Builder().setUsage(1).build();
    private final int b;
    private final AudioManager.OnAudioFocusChangeListener c;
    private final Handler d;
    private final AudioAttributesCompat e;
    private final boolean f;
    private final Object g;

    AudioFocusRequestCompat(int i, AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, Handler handler, AudioAttributesCompat audioAttributesCompat, boolean z) {
        this.b = i;
        this.d = handler;
        this.e = audioAttributesCompat;
        this.f = z;
        if (Build.VERSION.SDK_INT >= 26 || this.d.getLooper() == Looper.getMainLooper()) {
            this.c = onAudioFocusChangeListener;
        } else {
            this.c = new b(onAudioFocusChangeListener, handler);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            this.g = a.a(this.b, a(), this.f, this.c, this.d);
        } else {
            this.g = null;
        }
    }

    public int getFocusGain() {
        return this.b;
    }

    @NonNull
    public AudioAttributesCompat getAudioAttributesCompat() {
        return this.e;
    }

    public boolean willPauseWhenDucked() {
        return this.f;
    }

    @NonNull
    public AudioManager.OnAudioFocusChangeListener getOnAudioFocusChangeListener() {
        return this.c;
    }

    @NonNull
    public Handler getFocusChangeHandler() {
        return this.d;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AudioFocusRequestCompat)) {
            return false;
        }
        AudioFocusRequestCompat audioFocusRequestCompat = (AudioFocusRequestCompat) obj;
        return this.b == audioFocusRequestCompat.b && this.f == audioFocusRequestCompat.f && ObjectsCompat.equals(this.c, audioFocusRequestCompat.c) && ObjectsCompat.equals(this.d, audioFocusRequestCompat.d) && ObjectsCompat.equals(this.e, audioFocusRequestCompat.e);
    }

    public int hashCode() {
        return ObjectsCompat.hash(Integer.valueOf(this.b), this.c, this.d, this.e, Boolean.valueOf(this.f));
    }

    @RequiresApi(21)
    AudioAttributes a() {
        AudioAttributesCompat audioAttributesCompat = this.e;
        if (audioAttributesCompat != null) {
            return (AudioAttributes) audioAttributesCompat.unwrap();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(26)
    public AudioFocusRequest b() {
        return (AudioFocusRequest) this.g;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private int a;
        private AudioManager.OnAudioFocusChangeListener b;
        private Handler c;
        private AudioAttributesCompat d;
        private boolean e;

        private static boolean a(int i) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                    return true;
                default:
                    return false;
            }
        }

        public Builder(int i) {
            this.d = AudioFocusRequestCompat.a;
            setFocusGain(i);
        }

        public Builder(@NonNull AudioFocusRequestCompat audioFocusRequestCompat) {
            this.d = AudioFocusRequestCompat.a;
            if (audioFocusRequestCompat != null) {
                this.a = audioFocusRequestCompat.getFocusGain();
                this.b = audioFocusRequestCompat.getOnAudioFocusChangeListener();
                this.c = audioFocusRequestCompat.getFocusChangeHandler();
                this.d = audioFocusRequestCompat.getAudioAttributesCompat();
                this.e = audioFocusRequestCompat.willPauseWhenDucked();
                return;
            }
            throw new IllegalArgumentException("AudioFocusRequestCompat to copy must not be null");
        }

        @NonNull
        public Builder setFocusGain(int i) {
            if (a(i)) {
                if (Build.VERSION.SDK_INT < 19 && i == 4) {
                    i = 2;
                }
                this.a = i;
                return this;
            }
            throw new IllegalArgumentException("Illegal audio focus gain type " + i);
        }

        @NonNull
        public Builder setOnAudioFocusChangeListener(@NonNull AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
            return setOnAudioFocusChangeListener(onAudioFocusChangeListener, new Handler(Looper.getMainLooper()));
        }

        @NonNull
        public Builder setOnAudioFocusChangeListener(@NonNull AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, @NonNull Handler handler) {
            if (onAudioFocusChangeListener == null) {
                throw new IllegalArgumentException("OnAudioFocusChangeListener must not be null");
            } else if (handler != null) {
                this.b = onAudioFocusChangeListener;
                this.c = handler;
                return this;
            } else {
                throw new IllegalArgumentException("Handler must not be null");
            }
        }

        @NonNull
        public Builder setAudioAttributes(@NonNull AudioAttributesCompat audioAttributesCompat) {
            if (audioAttributesCompat != null) {
                this.d = audioAttributesCompat;
                return this;
            }
            throw new NullPointerException("Illegal null AudioAttributes");
        }

        @NonNull
        public Builder setWillPauseWhenDucked(boolean z) {
            this.e = z;
            return this;
        }

        public AudioFocusRequestCompat build() {
            AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = this.b;
            if (onAudioFocusChangeListener != null) {
                return new AudioFocusRequestCompat(this.a, onAudioFocusChangeListener, this.c, this.d, this.e);
            }
            throw new IllegalStateException("Can't build an AudioFocusRequestCompat instance without a listener");
        }
    }

    /* loaded from: classes.dex */
    private static class b implements AudioManager.OnAudioFocusChangeListener, Handler.Callback {
        private final Handler a;
        private final AudioManager.OnAudioFocusChangeListener b;

        b(@NonNull AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, @NonNull Handler handler) {
            this.b = onAudioFocusChangeListener;
            this.a = new Handler(handler.getLooper(), this);
        }

        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i) {
            Handler handler = this.a;
            handler.sendMessage(Message.obtain(handler, 2782386, i, 0));
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 2782386) {
                return false;
            }
            this.b.onAudioFocusChange(message.arg1);
            return true;
        }
    }

    @RequiresApi(26)
    /* loaded from: classes.dex */
    private static class a {
        @DoNotInline
        static AudioFocusRequest a(int i, AudioAttributes audioAttributes, boolean z, AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, Handler handler) {
            return new AudioFocusRequest.Builder(i).setAudioAttributes(audioAttributes).setWillPauseWhenDucked(z).setOnAudioFocusChangeListener(onAudioFocusChangeListener, handler).build();
        }
    }
}
