package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.SelectorSelectOperation;

/* loaded from: classes3.dex */
public class SelectorOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.Selector.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        if (((str.hashCode() == -848412181 && str.equals(AIApiConstants.Selector.Select)) ? (char) 0 : (char) 65535) != 0) {
            return null;
        }
        return new SelectorSelectOperation(instruction);
    }
}
