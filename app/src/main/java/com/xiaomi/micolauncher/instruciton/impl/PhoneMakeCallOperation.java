package com.xiaomi.micolauncher.instruciton.impl;

import android.content.Intent;
import com.xiaomi.ai.api.Phone;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;
import com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient;

/* loaded from: classes3.dex */
public class PhoneMakeCallOperation extends BaseOperation<Instruction<Phone.MakeCall>> {
    public PhoneMakeCallOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        if (RomUpdateAdapter.getInstance().isUpdateOngoing()) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        L.voip.d("%s onProcess", "PhoneMakeCallOperation");
        Phone.MakeCall makeCall = (Phone.MakeCall) this.instruction.getPayload();
        if (!makeCall.getContact().isPresent()) {
            L.voip.w("getContact is not present");
            return BaseOperation.OpState.STATE_FAIL;
        }
        Phone.Contact contact = makeCall.getContact().get();
        if (!contact.getCategory().isPresent()) {
            L.voip.w("getCategory is not present");
            return BaseOperation.OpState.STATE_FAIL;
        }
        BaseOperation.OpState opState = BaseOperation.OpState.STATE_FAIL;
        Intent intent = new Intent();
        intent.setAction(MicoVoipClient.ACTION_PLAY);
        intent.putExtra("ContactCategory", contact.getCategory().get());
        intent.putExtra("UserId", contact.getId());
        intent.putExtra("number", contact.getNumber());
        intent.putExtra("name", contact.getName());
        MicoVoipClient.getInstance(getContext()).sendMessageToMicoVoipService(MicoVoipClient.MSG_FROM_LAUNCHER_CLIENT_VOIP_EVENT, intent);
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
