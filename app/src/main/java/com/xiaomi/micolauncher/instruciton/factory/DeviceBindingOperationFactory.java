package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.DevicePairOperation;
import com.xiaomi.micolauncher.instruciton.impl.DeviceScanOperation;

/* loaded from: classes3.dex */
public class DeviceBindingOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return "DeviceBinding";
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != -1046047676) {
            if (hashCode == 658465601 && str.equals(AIApiConstants.DeviceBinding.ScanDevices)) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals(AIApiConstants.DeviceBinding.PairDevices)) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return new DeviceScanOperation(instruction);
            case 1:
                return new DevicePairOperation(instruction);
            default:
                return null;
        }
    }
}
