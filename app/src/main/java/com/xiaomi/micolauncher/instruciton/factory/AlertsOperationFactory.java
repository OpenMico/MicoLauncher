package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.AlertsOperation;

/* loaded from: classes3.dex */
public class AlertsOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.Alerts.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1663923229) {
            if (str.equals(AIApiConstants.Alerts.StopAlert)) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode == -795955311) {
            if (str.equals(AIApiConstants.Alerts.SetAlert)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 670244535) {
            if (hashCode == 850442777 && str.equals(AIApiConstants.Alerts.DeleteAlerts)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(AIApiConstants.Alerts.UpdateAlerts)) {
                c = 2;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
                return new AlertsOperation(instruction);
            default:
                return null;
        }
    }
}
