package com.xiaomi.micolauncher.instruciton.impl;

import android.content.Intent;
import com.xiaomi.ai.api.Phone;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient;

/* loaded from: classes3.dex */
public class PhonePickUpOperation extends BaseOperation<Instruction<Phone.PickUp>> {
    public PhonePickUpOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Intent intent = new Intent();
        L.voip.d("%s onProcess", "PhonePickUpOperation");
        intent.setAction(MicoVoipClient.ACTION_PICK_UP);
        MicoVoipClient.getInstance(getContext()).sendMessageToMicoVoipService(MicoVoipClient.MSG_FROM_LAUNCHER_CLIENT_VOIP_EVENT, intent);
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
