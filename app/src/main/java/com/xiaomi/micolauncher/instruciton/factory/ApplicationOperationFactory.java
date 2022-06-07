package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.ApplicationOperateOperation;

/* loaded from: classes3.dex */
public class ApplicationOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.Application.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != -1076313028) {
            if (hashCode == 827010726 && str.equals(AIApiConstants.Application.Operate)) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals(AIApiConstants.Application.CheckApps)) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return new ApplicationOperateOperation(instruction);
            case 1:
            default:
                return null;
        }
    }
}
