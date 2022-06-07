package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.NlpConstructRequestOperation;

/* loaded from: classes3.dex */
public class NlpOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.Nlp.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        if (((str.hashCode() == -2081416460 && str.equals(AIApiConstants.Nlp.ConstructRequest)) ? (char) 0 : (char) 65535) != 0) {
            return null;
        }
        return new NlpConstructRequestOperation(instruction);
    }
}
