package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.FullScreenTemplateDialogueOperation;

/* loaded from: classes3.dex */
public class FullScreenTemplateOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.FullScreenTemplate.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        if (AIApiConstants.FullScreenTemplate.Dialogue.equals(str)) {
            return new FullScreenTemplateDialogueOperation(instruction);
        }
        return null;
    }
}
