package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.DeviceBinding;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;

/* loaded from: classes3.dex */
public class DevicePairOperation extends BaseOperation<Instruction<DeviceBinding.PairDevices>> {
    public DevicePairOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected void onPreCreateOp() {
        setSpeakDependence();
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        boolean isExecBind = ((DeviceBinding.PairDevices) this.instruction.getPayload()).isExecBind();
        L.base.i("processPairDevices connDevice=%s", Boolean.valueOf(isExecBind));
        MiotProvisionManagerWrapper.getInstance().queryIsConnectDevicesResult(isExecBind);
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
