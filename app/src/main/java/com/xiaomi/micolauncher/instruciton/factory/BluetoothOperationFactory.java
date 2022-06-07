package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.BluetoothOperation;

/* loaded from: classes3.dex */
public class BluetoothOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.Bluetooth.NAME;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        switch (str.hashCode()) {
            case -876418020:
                if (str.equals(AIApiConstants.Bluetooth.Disconnect)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -738784588:
                if (str.equals(AIApiConstants.Bluetooth.Switch)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -711740740:
                if (str.equals(AIApiConstants.Bluetooth.TurnOn)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -589126606:
                if (str.equals(AIApiConstants.Bluetooth.TurnOff)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1327732074:
                if (str.equals(AIApiConstants.Bluetooth.Connect)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return new BluetoothOperation(instruction);
            default:
                return null;
        }
    }
}
