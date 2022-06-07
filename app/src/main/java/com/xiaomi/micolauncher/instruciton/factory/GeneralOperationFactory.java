package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.General;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.utils.SoundToneManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.GeneralFetchDeviceStateOperation;
import com.xiaomi.micolauncher.instruciton.impl.GeneralPushOperation;
import com.xiaomi.micolauncher.instruciton.impl.GeneralQueryClientStatusOperation;

/* loaded from: classes3.dex */
public class GeneralOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.General.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1767322433) {
            if (str.equals(AIApiConstants.General.QueryClientStatus)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode == 239757260) {
            if (str.equals(AIApiConstants.General.SwitchTone)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 1293186375) {
            if (hashCode == 1602877600 && str.equals(AIApiConstants.General.Push)) {
                c = 3;
            }
            c = 65535;
        } else {
            if (str.equals(AIApiConstants.General.FetchDeviceState)) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return new GeneralQueryClientStatusOperation(instruction);
            case 1:
                return new GeneralFetchDeviceStateOperation(instruction);
            case 2:
                SoundToneManager.onToneTypeChanged(((General.SwitchTone) instruction.getPayload()).getToneId(), MicoApplication.getGlobalContext(), false);
                return null;
            case 3:
                return new GeneralPushOperation(instruction);
            default:
                return null;
        }
    }
}
