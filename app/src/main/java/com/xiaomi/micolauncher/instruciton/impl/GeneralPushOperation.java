package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.General;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.intercom.PushIntercom;

/* loaded from: classes3.dex */
public class GeneralPushOperation extends BaseOperation<Instruction<General.Push>> {
    public GeneralPushOperation(Instruction<General.Push> instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        General.Push push = (General.Push) this.instruction.getPayload();
        (push.getMsgMeta().isPresent() ? push.getMsgMeta().get() : new General.PushMsgMeta()).setSendTimestamp(System.currentTimeMillis());
        PushIntercom.getInstance().setContext(getContext());
        if (PushIntercom.getInstance().addPushList(push)) {
            PushIntercom.getInstance().processPush();
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
