package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.BrightnessController;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.setting.utils.BrightnessControllerProxy;

/* loaded from: classes3.dex */
public class BrightnessAdjustOperation extends BaseOperation<Instruction<BrightnessController.AdjustBrightness>> {
    public BrightnessAdjustOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        int brightnessDelta = ((BrightnessController.AdjustBrightness) this.instruction.getPayload()).getBrightnessDelta();
        if (brightnessDelta > 0) {
            BrightnessControllerProxy.getInstance().upBrightness(brightnessDelta);
        } else {
            BrightnessControllerProxy.getInstance().downBrightness(Math.abs(brightnessDelta));
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
