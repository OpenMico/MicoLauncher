package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.LaunchAppOperation;
import com.xiaomi.micolauncher.instruciton.impl.LaunchGeneralQuickAppOperation;
import com.xiaomi.micolauncher.instruciton.impl.LauncherLaunchQuickAppOperation;

/* loaded from: classes3.dex */
public class LauncherOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.Launcher.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1476626137) {
            if (str.equals(AIApiConstants.Launcher.LaunchShortcut)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode == 555340283) {
            if (str.equals(AIApiConstants.Launcher.LaunchGeneralQuickApp)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 1760728224) {
            if (hashCode == 2094405077 && str.equals(AIApiConstants.Launcher.LaunchQuickApp)) {
                c = 3;
            }
            c = 65535;
        } else {
            if (str.equals(AIApiConstants.Launcher.LaunchApp)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return new LaunchAppOperation(instruction);
            case 1:
                return new LaunchGeneralQuickAppOperation(instruction);
            case 2:
            default:
                return null;
            case 3:
                return new LauncherLaunchQuickAppOperation(instruction);
        }
    }
}
