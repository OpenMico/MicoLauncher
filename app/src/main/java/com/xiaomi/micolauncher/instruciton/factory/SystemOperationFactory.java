package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.SystemEnvSwitchOperation;
import com.xiaomi.micolauncher.instruciton.impl.SystemLockScreenOperation;
import com.xiaomi.micolauncher.instruciton.impl.SystemPowerOperation;
import com.xiaomi.micolauncher.instruciton.impl.SystemSetPropertyOperation;
import com.xiaomi.micolauncher.instruciton.impl.SystemUnlockScreenOperation;
import com.xiaomi.micolauncher.instruciton.impl.SystemUpgradeRomOperation;

/* loaded from: classes3.dex */
public class SystemOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.System.NAME;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        switch (str.hashCode()) {
            case -1836500200:
                if (str.equals(AIApiConstants.System.SetProperty)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1674901709:
                if (str.equals(AIApiConstants.System.UpgradeRom)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1616270202:
                if (str.equals(AIApiConstants.System.Power)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1613606312:
                if (str.equals(AIApiConstants.System.Sleep)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -218114984:
                if (str.equals(AIApiConstants.System.CheckScreenUnlocked)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 86403537:
                if (str.equals(AIApiConstants.System.Ping)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 140280194:
                if (str.equals(AIApiConstants.System.EnvSwitch)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1084629551:
                if (str.equals(AIApiConstants.System.UnlockScreen)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1152796761:
                if (str.equals(AIApiConstants.System.AutoLock)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1200982678:
                if (str.equals(AIApiConstants.System.LockScreen)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1214991184:
                if (str.equals(AIApiConstants.System.Exception)) {
                    c = '\n';
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
                return new SystemPowerOperation(instruction);
            case 1:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            default:
                return null;
            case 2:
                return new SystemLockScreenOperation(instruction);
            case 3:
                return new SystemUnlockScreenOperation(instruction);
            case 4:
                return new SystemUpgradeRomOperation(instruction);
            case 5:
                return new SystemSetPropertyOperation(instruction);
            case 6:
                return new SystemEnvSwitchOperation(instruction);
        }
    }
}
