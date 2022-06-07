package com.xiaomi.micolauncher.skills.common;

/* loaded from: classes3.dex */
public class WakeupUiEvent {
    public static final int WAKEUP_UI_ASR = 1;
    public static final int WAKEUP_UI_DISMISS = 3;
    public static final int WAKEUP_UI_NLP = 2;
    public static final int WAKEUP_UI_SHOW = 0;
    private int a;

    public WakeupUiEvent(int i) {
        this.a = i;
    }

    public int getEvent() {
        return this.a;
    }
}
