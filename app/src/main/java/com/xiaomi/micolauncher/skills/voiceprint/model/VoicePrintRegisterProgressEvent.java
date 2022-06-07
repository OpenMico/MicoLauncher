package com.xiaomi.micolauncher.skills.voiceprint.model;

import android.util.ArrayMap;

/* loaded from: classes3.dex */
public class VoicePrintRegisterProgressEvent {
    private ProgressStep a;
    private ArrayMap<String, String> b;

    /* loaded from: classes3.dex */
    public enum ProgressStep {
        PROGRESS_STEP_START,
        PROGRESS_STEP_SHOW_XATX_PAGE,
        PROGRESS_STEP_SPEAK_XATX,
        PROGRESS_STEP_RESULT,
        PROGRESS_STEP_END,
        PROGRESS_STEP_CANCEL,
        PROGRESS_STEP_NUM
    }

    public VoicePrintRegisterProgressEvent(ProgressStep progressStep, ArrayMap<String, String> arrayMap) {
        this.a = progressStep;
        this.b = arrayMap;
    }

    public ProgressStep getStep() {
        return this.a;
    }

    public ArrayMap<String, String> getParams() {
        return this.b;
    }
}
