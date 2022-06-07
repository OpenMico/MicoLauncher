package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.BrightnessController;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.setting.utils.BrightnessControllerProxy;

/* loaded from: classes3.dex */
public class BrightnessSetOperation extends BaseOperation<Instruction<BrightnessController.SetBrightness>> {
    public BrightnessSetOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        BrightnessControllerProxy.getInstance().setBrightnessPercent(((BrightnessController.SetBrightness) this.instruction.getPayload()).getBrightness());
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
