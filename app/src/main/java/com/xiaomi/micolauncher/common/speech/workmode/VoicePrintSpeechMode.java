package com.xiaomi.micolauncher.common.speech.workmode;

import android.text.TextUtils;
import com.xiaomi.ai.api.SpeechRecognizer;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.speaker.vpmclient.VpmClient;
import com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;

/* loaded from: classes3.dex */
public class VoicePrintSpeechMode extends BaseSpeechWorkMode {
    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    void a() {
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    void b() {
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void cancelSpeech() {
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public String name() {
        return "VoicePrint";
    }

    public VoicePrintSpeechMode(VpmClient vpmClient, BaseSpeechEngine baseSpeechEngine) {
        super(vpmClient, baseSpeechEngine);
        this.a.vpmEnableWuw();
        this.a.vpmSetUnWakeup();
        this.a.vpmSetWaitAsrTimeout(2000);
        clearNewSession();
        vpmClient.disableVoiceWakeup();
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public boolean wakeupHandle() {
        super.wakeupHandle();
        OperationManager.getInstance().onWakeup(false, true);
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void asrResultHandle(String str) {
        super.asrResultHandle(str);
        if (TextUtils.isEmpty(str)) {
            c();
        }
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public void release() {
        super.release();
        this.a.enableVoiceWakeup();
    }

    private void c() {
        this.b.postEventWithContext(APIUtils.buildEvent(new SpeechRecognizer.VoiceprintIdle()));
    }
}
