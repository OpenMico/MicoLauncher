package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.BrightnessAdjustOperation;
import com.xiaomi.micolauncher.instruciton.impl.BrightnessSetOperation;

/* loaded from: classes3.dex */
public class BrightnessControllerOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.BrightnessController.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != 1029794369) {
            if (hashCode == 1388751090 && str.equals(AIApiConstants.BrightnessController.SetBrightness)) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals(AIApiConstants.BrightnessController.AdjustBrightness)) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return new BrightnessSetOperation(instruction);
            case 1:
                return new BrightnessAdjustOperation(instruction);
            default:
                return null;
        }
    }
}
