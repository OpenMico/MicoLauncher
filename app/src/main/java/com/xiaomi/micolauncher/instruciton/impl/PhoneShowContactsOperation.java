package com.xiaomi.micolauncher.instruciton.impl;

import android.content.Intent;
import com.xiaomi.ai.api.Phone;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class PhoneShowContactsOperation extends BaseOperation<Instruction<Phone.ShowContacts>> {
    public PhoneShowContactsOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Intent intent = new Intent();
        L.voip.d("%s onProcess", "PhoneShowContactsOperation");
        intent.setAction(MicoVoipClient.ACTION_QUERY);
        intent.putExtra("instruction_payload", (Serializable) ((Phone.ShowContacts) this.instruction.getPayload()));
        MicoVoipClient.getInstance(getContext()).sendMessageToMicoVoipService(MicoVoipClient.MSG_FROM_LAUNCHER_CLIENT_VOIP_EVENT, intent);
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
