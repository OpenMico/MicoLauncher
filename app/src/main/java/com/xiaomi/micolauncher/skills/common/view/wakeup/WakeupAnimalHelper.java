package com.xiaomi.micolauncher.skills.common.view.wakeup;

import com.xiaomi.micolauncher.skills.common.WakeupHelper;

/* loaded from: classes3.dex */
public interface WakeupAnimalHelper {
    WakeupHelper.WakeupStatus getStatus();

    void init(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    void stepInit();

    void stepListening();

    void stepListeningTransToSpeaking();

    void stepLoading();

    void stepLoadingTransToSpeaking();

    void stepSpeaking();

    void stepSpeakingTransToLoading();

    void stepStop();

    void stepTts();
}
