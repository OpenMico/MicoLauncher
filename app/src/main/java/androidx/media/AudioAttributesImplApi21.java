package androidx.media;

import android.media.AudioAttributes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.media.AudioAttributesImpl;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class AudioAttributesImplApi21 implements AudioAttributesImpl {
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public AudioAttributes mAudioAttributes;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mLegacyStreamType;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public AudioAttributesImplApi21() {
        this.mLegacyStreamType = -1;
    }

    public AudioAttributesImplApi21(AudioAttributes audioAttributes) {
        this(audioAttributes, -1);
    }

    public AudioAttributesImplApi21(AudioAttributes audioAttributes, int i) {
        this.mLegacyStreamType = -1;
        this.mAudioAttributes = audioAttributes;
        this.mLegacyStreamType = i;
    }

    @Override // androidx.media.AudioAttributesImpl
    @Nullable
    public Object getAudioAttributes() {
        return this.mAudioAttributes;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getVolumeControlStream() {
        return AudioAttributesCompat.a(true, getFlags(), getUsage());
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getLegacyStreamType() {
        int i = this.mLegacyStreamType;
        return i != -1 ? i : AudioAttributesCompat.a(false, getFlags(), getUsage());
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getRawLegacyStreamType() {
        return this.mLegacyStreamType;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getContentType() {
        return this.mAudioAttributes.getContentType();
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getUsage() {
        return this.mAudioAttributes.getUsage();
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getFlags() {
        return this.mAudioAttributes.getFlags();
    }

    public int hashCode() {
        return this.mAudioAttributes.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AudioAttributesImplApi21)) {
            return false;
        }
        return this.mAudioAttributes.equals(((AudioAttributesImplApi21) obj).mAudioAttributes);
    }

    @NonNull
    public String toString() {
        return "AudioAttributesCompat: audioattributes=" + this.mAudioAttributes;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class a implements AudioAttributesImpl.Builder {
        final AudioAttributes.Builder a;

        public a() {
            this.a = new AudioAttributes.Builder();
        }

        public a(Object obj) {
            this.a = new AudioAttributes.Builder((AudioAttributes) obj);
        }

        @Override // androidx.media.AudioAttributesImpl.Builder
        @NonNull
        public AudioAttributesImpl build() {
            return new AudioAttributesImplApi21(this.a.build());
        }

        @NonNull
        /* renamed from: a */
        public a setUsage(int i) {
            if (i == 16) {
                i = 12;
            }
            this.a.setUsage(i);
            return this;
        }

        @NonNull
        /* renamed from: b */
        public a setContentType(int i) {
            this.a.setContentType(i);
            return this;
        }

        @NonNull
        /* renamed from: c */
        public a setFlags(int i) {
            this.a.setFlags(i);
            return this;
        }

        @NonNull
        /* renamed from: d */
        public a setLegacyStreamType(int i) {
            this.a.setLegacyStreamType(i);
            return this;
        }
    }
}
