package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.SpeechRecognizer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;

/* loaded from: classes3.dex */
public class SpeechExpectSpeechOperation extends BaseOperation<Instruction<SpeechRecognizer.ExpectSpeech>> {
    public SpeechExpectSpeechOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        SpeechManager.getInstance().setWakeup();
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
