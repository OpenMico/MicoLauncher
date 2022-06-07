package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.SpeechSynthesizerSpeakOperation;

/* loaded from: classes3.dex */
public class SpeechSynthesizerOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return "SpeechSynthesizer";
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        if (((str.hashCode() == -349709590 && str.equals(AIApiConstants.SpeechSynthesizer.Speak)) ? (char) 0 : (char) 65535) != 0) {
            return null;
        }
        return new SpeechSynthesizerSpeakOperation(instruction);
    }
}
