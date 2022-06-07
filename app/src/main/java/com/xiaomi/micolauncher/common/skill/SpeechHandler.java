package com.xiaomi.micolauncher.common.skill;

/* loaded from: classes3.dex */
public interface SpeechHandler {

    /* loaded from: classes3.dex */
    public enum HandleResult {
        HANDLE_RESULT_NO_HANDLED,
        HANDLE_RESULT_HANDLED,
        HANDLE_RESULT_CONTINUE,
        HANDLE_RESULT_NO_TTS,
        HANDLE_RESULT_ONLY_SHOW_TTS_ANI,
        HANDLE_RESULT_FALSE,
        HANDLE_RESULT_NO_SUPPORT
    }

    /* loaded from: classes3.dex */
    public enum WakeupHandleResult {
        WP_HANDLE_RESULT_NO_HANDLED,
        WP_HANDLE_RESULT_HANDLED,
        WP_HANDLE_RESULT_ONLY_SHOW_UI
    }
}
