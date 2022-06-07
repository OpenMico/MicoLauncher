package com.xiaomi.micolauncher.common.player.policy;

import android.util.SparseIntArray;

/* loaded from: classes3.dex */
public interface IAudioPolicy {
    public static final int AUDIO_SOURCE_TYPE_ALL = 4095;
    public static final int AUDIO_SOURCE_TYPE_LIKE_ALARM = 8;
    public static final int AUDIO_SOURCE_TYPE_LIKE_CALL = 32;
    public static final int AUDIO_SOURCE_TYPE_LIKE_FAKE = 256;
    public static final int AUDIO_SOURCE_TYPE_LIKE_MUSIC = 1;
    public static final int AUDIO_SOURCE_TYPE_LIKE_RING_TONE = 16;
    public static final int AUDIO_SOURCE_TYPE_LIKE_SHORT_AUDIO = 1024;
    public static final int AUDIO_SOURCE_TYPE_LIKE_SHORT_VIDEO = 128;
    public static final int AUDIO_SOURCE_TYPE_LIKE_TONE = 64;
    public static final int AUDIO_SOURCE_TYPE_LIKE_TTS = 2;
    public static final int AUDIO_SOURCE_TYPE_LIKE_UI = 512;
    public static final int AUDIO_SOURCE_TYPE_LIKE_WAKE_UP = 4;
    public static final int AUDIO_SOURCE_TYPE_MAX = 4096;
    public static final int AUDIO_SOURCE_TYPE_NULL = 0;

    /* loaded from: classes3.dex */
    public enum AudioSourcePriority {
        AUDIO_SOURCE_PRIORITY_ONE,
        AUDIO_SOURCE_PRIORITY_TWO,
        AUDIO_SOURCE_PRIORITY_THREE,
        AUDIO_SOURCE_PRIORITY_FOUR,
        AUDIO_SOURCE_PRIORITY_FIVE,
        AUDIO_SOURCE_PRIORITY_SIX,
        AUDIO_SOURCE_PRIORITY_SEVEN,
        AUDIO_SOURCE_PRIORITY_MAX
    }

    /* loaded from: classes3.dex */
    public enum BackgroundVolume {
        BACKGROUND_VOLUME_ZERO,
        BACKGROUND_VOLUME_FIVE_ONE,
        BACKGROUND_VOLUME_FIVE_TWO,
        BACKGROUND_VOLUME_FIVE_THREE,
        BACKGROUND_VOLUME_FIVE_FOUR,
        BACKGROUND_VOLUME_FULL
    }

    /* loaded from: classes3.dex */
    public enum RequestRet {
        REQUEST_RET_ALLOW,
        REQUEST_RET_SUSPEND,
        REQUEST_RET_SUSPEND_AI,
        REQUEST_RET_NOT_ALLOW,
        REQUEST_RET_RESUME,
        REQUEST_RET_RESUME_AI,
        REQUEST_RET_BACKGROUND,
        REQUEST_RET_FOREGROUND,
        REQUEST_RET_FINISH,
        REQUEST_RET_FORCE_STOP,
        REQUEST_RET_MAX
    }

    /* loaded from: classes3.dex */
    public enum SourceSuspendType {
        SOURCE_SUSPEND_TYPE_NORMAL,
        SOURCE_SUSPEND_TYPE_STOP_OTHER
    }

    /* loaded from: classes3.dex */
    public static final class AudioSourceProperty {
        int a;
        AudioSourcePriority b;
        final SparseIntArray e = new SparseIntArray();
        BackgroundVolume c = BackgroundVolume.BACKGROUND_VOLUME_FIVE_ONE;
        SourceSuspendType d = SourceSuspendType.SOURCE_SUSPEND_TYPE_NORMAL;

        /* loaded from: classes3.dex */
        enum a {
            NOT_ALLOW,
            SUSPEND,
            BACKGROUND,
            ALLOW,
            NOT_ALLOW_SELF,
            SUSPEND_SELF,
            BACKGROUND_SELF
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public AudioSourceProperty(int i, AudioSourcePriority audioSourcePriority) {
            this.a = i;
            this.b = audioSourcePriority;
            a(a.NOT_ALLOW, 0);
            a(a.SUSPEND, 0);
            a(a.BACKGROUND, 0);
            a(a.ALLOW, IAudioPolicy.AUDIO_SOURCE_TYPE_ALL);
            a(a.NOT_ALLOW_SELF, IAudioPolicy.AUDIO_SOURCE_TYPE_ALL);
            a(a.SUSPEND_SELF, 0);
            a(a.BACKGROUND_SELF, 0);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public AudioSourceProperty a(a aVar, int i) {
            this.e.put(aVar.ordinal(), i);
            return this;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public AudioSourceProperty b(a aVar, int i) {
            this.e.put(aVar.ordinal(), (~i) & this.e.get(aVar.ordinal(), 0));
            return this;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int a(a aVar) {
            return this.e.get(aVar.ordinal());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public AudioSourceProperty a(SourceSuspendType sourceSuspendType) {
            this.d = sourceSuspendType;
            return this;
        }
    }
}
