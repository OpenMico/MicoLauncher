package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Sys;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.setting.utils.Screen;

/* loaded from: classes3.dex */
public class SystemUnlockScreenOperation extends BaseOperation<Instruction<Sys.UnlockScreen>> {
    public SystemUnlockScreenOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Screen.getInstance().closeLockScreen();
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
