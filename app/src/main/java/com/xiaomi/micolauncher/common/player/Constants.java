package com.xiaomi.micolauncher.common.player;

/* loaded from: classes3.dex */
public interface Constants {
    public static final String AUDIO_URL = "KEY_AUDIO_URL";
    public static final float MAX_SOUND_VOLUME = 1.0f;
    public static final float MIN_SOUND_VOLUME = 0.1f;
    public static final int POST_FINISH_DELAYED_MS = 7000;
    public static final String SHOW_TEXT = "KEY_TEXT_CONTENT";

    /* loaded from: classes3.dex */
    public enum PlayErrorCode {
        RET_FINISH,
        RET_LOSS_AUDIO_FOCUS,
        RET_PLAY_ERROR
    }
}
