package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.UIControllerNavigateOperation;

/* loaded from: classes3.dex */
public class UIControllerOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.UIController.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        if (((str.hashCode() == -1487063953 && str.equals(AIApiConstants.UIController.Navigate)) ? (char) 0 : (char) 65535) != 0) {
            return null;
        }
        return new UIControllerNavigateOperation(instruction);
    }
}
