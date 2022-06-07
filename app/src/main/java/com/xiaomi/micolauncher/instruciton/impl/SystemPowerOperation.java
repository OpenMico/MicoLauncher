package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Sys;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.common.utils.SystemPowerUtil;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class SystemPowerOperation extends BaseOperation<Instruction<Sys.Power>> {
    public SystemPowerOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Sys.Power power = (Sys.Power) this.instruction.getPayload();
        int intValue = power.getDelay().isPresent() ? power.getDelay().get().intValue() : 0;
        switch (power.getOperation()) {
            case REBOOT:
                SystemPowerUtil.rebootDelay(TimeUnit.SECONDS.toMillis(intValue));
                return BaseOperation.OpState.STATE_SUCCESS;
            case SHUTDOWN:
                if (Hardware.isX08E() && intValue == 0) {
                    intValue = 6;
                }
                SystemPowerUtil.shutdownOrScreenOff(TimeUnit.SECONDS.toMillis(intValue));
                return BaseOperation.OpState.STATE_SUCCESS;
            default:
                return BaseOperation.OpState.STATE_FAIL;
        }
    }
}
