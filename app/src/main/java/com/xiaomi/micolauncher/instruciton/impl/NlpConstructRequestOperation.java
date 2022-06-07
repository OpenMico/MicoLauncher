package com.xiaomi.micolauncher.instruciton.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.Nlp;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;

/* loaded from: classes3.dex */
public class NlpConstructRequestOperation extends BaseOperation<Instruction<Nlp.ConstructRequest>> {
    public NlpConstructRequestOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        ObjectNode request = ((Nlp.ConstructRequest) this.instruction.getPayload()).getRequest();
        if (request != null) {
            try {
                Event readEvent = APIUtils.readEvent(request);
                if (readEvent != null) {
                    L.ope.i("sendEventRequest=%s", readEvent.toJsonString());
                    SpeechManager.getInstance().sendEventRequest(readEvent);
                    return BaseOperation.OpState.STATE_SUCCESS;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return BaseOperation.OpState.STATE_FAIL;
    }
}
