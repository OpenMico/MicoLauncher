package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;

/* loaded from: classes3.dex */
public class TemplateGeneral2Operation extends BaseOperation<Instruction<Template.General2>> {
    public TemplateGeneral2Operation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        return BaseOperation.OpState.STATE_FAIL;
    }
}
