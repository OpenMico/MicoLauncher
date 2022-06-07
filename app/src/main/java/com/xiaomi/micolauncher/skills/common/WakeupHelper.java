package com.xiaomi.micolauncher.skills.common;

/* loaded from: classes3.dex */
public interface WakeupHelper {

    /* loaded from: classes3.dex */
    public enum WakeupStatus {
        WAKEUP_STATUS_INIT,
        WAKEUP_STATUS_LISTENING,
        WAKEUP_STATUS_TRANS_LISTENING_SPEAKING,
        WAKEUP_STATUS_SPEAKING,
        WAKEUP_STATUS_TRANS_SPEAKING_LOADING,
        WAKEUP_STATUS_LOADING,
        WAKEUP_STATUS_TTS,
        WAKEUP_STATUS_STOP
    }

    boolean aiSpeakerTts(String str);

    void autoDismiss(int i);

    void finishWakeup();

    void playWakeup(boolean z, boolean z2);

    void show(boolean z);

    void show(boolean z, boolean z2);

    void showAsr(String str);

    void showAsr(String str, boolean z);

    void showAsr(String str, boolean z, boolean z2);

    boolean showNlp(String str);

    void stepToTtsAnimation();

    void ttsEnd();

    void ttsStart();

    void waitNlp();

    void wakeUpCancel();
}
