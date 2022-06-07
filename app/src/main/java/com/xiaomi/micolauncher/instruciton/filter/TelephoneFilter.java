package com.xiaomi.micolauncher.instruciton.filter;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;

/* loaded from: classes3.dex */
public class TelephoneFilter implements InstructionFilter {
    @Override // com.xiaomi.micolauncher.instruciton.filter.InstructionFilter
    public boolean filterInstruction(String str) {
        return !a(str);
    }

    @Override // com.xiaomi.micolauncher.instruciton.filter.InstructionFilter
    public boolean isWorking() {
        return OperationManager.getInstance().isPhoneRunning();
    }

    private boolean a(String str) {
        return AIApiConstants.Phone.NAME.equals(str);
    }
}
