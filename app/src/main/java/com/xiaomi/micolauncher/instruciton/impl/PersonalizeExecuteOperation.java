package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Personalize;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.personalize.model.PersonalizeExecution;

/* loaded from: classes3.dex */
public class PersonalizeExecuteOperation extends BaseOperation<Instruction<Personalize.Execute>> {
    public PersonalizeExecuteOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        PersonalizeExecution.getInstance().setPersonalizeInstruction(this.instruction);
        PersonalizeExecution.getInstance().setCmdList(((Personalize.Execute) this.instruction.getPayload()).getCmds());
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
