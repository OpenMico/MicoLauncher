package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.TVControllerOperateOperation;

/* loaded from: classes3.dex */
public class TVControllerOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.TVController.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        if (((str.hashCode() == 1629463348 && str.equals(AIApiConstants.TVController.Operate)) ? (char) 0 : (char) 65535) != 0) {
            return null;
        }
        return new TVControllerOperateOperation(instruction);
    }
}
