package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Microphone;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.setting.utils.Mic;

/* loaded from: classes3.dex */
public class MicrophoneOperation extends BaseOperation<Instruction<Microphone.TurnOff>> {
    public MicrophoneOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Microphone.TurnOff turnOff = (Microphone.TurnOff) this.instruction.getPayload();
        Mic.getInstance().muteMicrophone(turnOff.getDuration().isPresent() ? turnOff.getDuration().get().intValue() : 0);
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
