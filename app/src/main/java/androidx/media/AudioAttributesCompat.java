package androidx.media;

import android.media.AudioAttributes;
import android.os.Build;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.media.AudioAttributesImpl;
import androidx.media.AudioAttributesImplApi21;
import androidx.media.AudioAttributesImplApi26;
import androidx.media.AudioAttributesImplBase;
import androidx.versionedparcelable.VersionedParcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class AudioAttributesCompat implements VersionedParcelable {
    public static final int CONTENT_TYPE_MOVIE = 3;
    public static final int CONTENT_TYPE_MUSIC = 2;
    public static final int CONTENT_TYPE_SONIFICATION = 4;
    public static final int CONTENT_TYPE_SPEECH = 1;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    public static final int FLAG_AUDIBILITY_ENFORCED = 1;
    public static final int FLAG_HW_AV_SYNC = 16;
    public static final int USAGE_ALARM = 4;
    public static final int USAGE_ASSISTANCE_ACCESSIBILITY = 11;
    public static final int USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12;
    public static final int USAGE_ASSISTANCE_SONIFICATION = 13;
    public static final int USAGE_ASSISTANT = 16;
    public static final int USAGE_GAME = 14;
    public static final int USAGE_MEDIA = 1;
    public static final int USAGE_NOTIFICATION = 5;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7;
    public static final int USAGE_NOTIFICATION_EVENT = 10;
    public static final int USAGE_NOTIFICATION_RINGTONE = 6;
    public static final int USAGE_UNKNOWN = 0;
    public static final int USAGE_VOICE_COMMUNICATION = 2;
    public static final int USAGE_VOICE_COMMUNICATION_SIGNALLING = 3;
    static boolean a;
    private static final SparseIntArray b = new SparseIntArray();
    private static final int[] c = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16};
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public AudioAttributesImpl mImpl;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* loaded from: classes.dex */
    public @interface AttributeContentType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* loaded from: classes.dex */
    public @interface AttributeUsage {
    }

    static {
        b.put(5, 1);
        b.put(6, 2);
        b.put(7, 2);
        b.put(8, 1);
        b.put(9, 1);
        b.put(10, 1);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public AudioAttributesCompat() {
    }

    AudioAttributesCompat(AudioAttributesImpl audioAttributesImpl) {
        this.mImpl = audioAttributesImpl;
    }

    public int getVolumeControlStream() {
        return this.mImpl.getVolumeControlStream();
    }

    @Nullable
    public Object unwrap() {
        return this.mImpl.getAudioAttributes();
    }

    public int getLegacyStreamType() {
        return this.mImpl.getLegacyStreamType();
    }

    @Nullable
    public static AudioAttributesCompat wrap(@NonNull Object obj) {
        if (a) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return new AudioAttributesCompat(new AudioAttributesImplApi26((AudioAttributes) obj));
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return new AudioAttributesCompat(new AudioAttributesImplApi21((AudioAttributes) obj));
        }
        return null;
    }

    public int getContentType() {
        return this.mImpl.getContentType();
    }

    public int getUsage() {
        return this.mImpl.getUsage();
    }

    public int getFlags() {
        return this.mImpl.getFlags();
    }

    /* loaded from: classes.dex */
    public static class Builder {
        final AudioAttributesImpl.Builder a;

        public Builder() {
            if (AudioAttributesCompat.a) {
                this.a = new AudioAttributesImplBase.a();
            } else if (Build.VERSION.SDK_INT >= 26) {
                this.a = new AudioAttributesImplApi26.a();
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.a = new AudioAttributesImplApi21.a();
            } else {
                this.a = new AudioAttributesImplBase.a();
            }
        }

        public Builder(AudioAttributesCompat audioAttributesCompat) {
            if (AudioAttributesCompat.a) {
                this.a = new AudioAttributesImplBase.a(audioAttributesCompat);
            } else if (Build.VERSION.SDK_INT >= 26) {
                this.a = new AudioAttributesImplApi26.a(audioAttributesCompat.unwrap());
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.a = new AudioAttributesImplApi21.a(audioAttributesCompat.unwrap());
            } else {
                this.a = new AudioAttributesImplBase.a(audioAttributesCompat);
            }
        }

        public AudioAttributesCompat build() {
            return new AudioAttributesCompat(this.a.build());
        }

        public Builder setUsage(int i) {
            this.a.setUsage(i);
            return this;
        }

        public Builder setContentType(int i) {
            this.a.setContentType(i);
            return this;
        }

        public Builder setFlags(int i) {
            this.a.setFlags(i);
            return this;
        }

        public Builder setLegacyStreamType(int i) {
            this.a.setLegacyStreamType(i);
            return this;
        }
    }

    public int hashCode() {
        return this.mImpl.hashCode();
    }

    public String toString() {
        return this.mImpl.toString();
    }

    public static String a(int i) {
        switch (i) {
            case 0:
                return "USAGE_UNKNOWN";
            case 1:
                return "USAGE_MEDIA";
            case 2:
                return "USAGE_VOICE_COMMUNICATION";
            case 3:
                return "USAGE_VOICE_COMMUNICATION_SIGNALLING";
            case 4:
                return "USAGE_ALARM";
            case 5:
                return "USAGE_NOTIFICATION";
            case 6:
                return "USAGE_NOTIFICATION_RINGTONE";
            case 7:
                return "USAGE_NOTIFICATION_COMMUNICATION_REQUEST";
            case 8:
                return "USAGE_NOTIFICATION_COMMUNICATION_INSTANT";
            case 9:
                return "USAGE_NOTIFICATION_COMMUNICATION_DELAYED";
            case 10:
                return "USAGE_NOTIFICATION_EVENT";
            case 11:
                return "USAGE_ASSISTANCE_ACCESSIBILITY";
            case 12:
                return "USAGE_ASSISTANCE_NAVIGATION_GUIDANCE";
            case 13:
                return "USAGE_ASSISTANCE_SONIFICATION";
            case 14:
                return "USAGE_GAME";
            case 15:
            default:
                return "unknown usage " + i;
            case 16:
                return "USAGE_ASSISTANT";
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static void setForceLegacyBehavior(boolean z) {
        a = z;
    }

    public int a() {
        return this.mImpl.getRawLegacyStreamType();
    }

    public static int a(boolean z, int i, int i2) {
        if ((i & 1) == 1) {
            return z ? 1 : 7;
        }
        if ((i & 4) == 4) {
            return z ? 0 : 6;
        }
        switch (i2) {
            case 0:
                return 3;
            case 1:
            case 12:
            case 14:
            case 16:
                return 3;
            case 2:
                return 0;
            case 3:
                return z ? 0 : 8;
            case 4:
                return 4;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                return 5;
            case 6:
                return 2;
            case 11:
                return 10;
            case 13:
                return 1;
            case 15:
            default:
                if (!z) {
                    return 3;
                }
                throw new IllegalArgumentException("Unknown usage value " + i2 + " in audio attributes");
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AudioAttributesCompat)) {
            return false;
        }
        AudioAttributesCompat audioAttributesCompat = (AudioAttributesCompat) obj;
        AudioAttributesImpl audioAttributesImpl = this.mImpl;
        if (audioAttributesImpl == null) {
            return audioAttributesCompat.mImpl == null;
        }
        return audioAttributesImpl.equals(audioAttributesCompat.mImpl);
    }
}
