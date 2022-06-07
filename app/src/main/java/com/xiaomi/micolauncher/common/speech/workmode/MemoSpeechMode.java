package com.xiaomi.micolauncher.common.speech.workmode;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.speaker.vpmclient.VpmClient;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;

/* loaded from: classes3.dex */
public class MemoSpeechMode extends BaseSpeechWorkMode {
    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public String name() {
        return AIApiConstants.Memo.NAME;
    }

    private MemoSpeechMode(VpmClient vpmClient, BaseSpeechEngine baseSpeechEngine, int i) {
        super(vpmClient, baseSpeechEngine);
        this.a.vpmEnableWuw();
        this.a.vpmSetWaitAsrTimeout(i);
        this.a.vpmSetWakeup();
    }

    public MemoSpeechMode(VpmClient vpmClient, BaseSpeechEngine baseSpeechEngine) {
        this(vpmClient, baseSpeechEngine, 60000);
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public boolean wakeupHandle() {
        super.wakeupHandle();
        OperationManager.getInstance().onWakeup(false, true);
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    boolean a(byte[] bArr) {
        boolean asrRequest = this.b.asrRequest(bArr, null);
        if (!asrRequest) {
            L.speech.e("%s startRequest.error", "[MemoSpeechMode]: ");
        }
        clearNewSession();
        return asrRequest;
    }
}
