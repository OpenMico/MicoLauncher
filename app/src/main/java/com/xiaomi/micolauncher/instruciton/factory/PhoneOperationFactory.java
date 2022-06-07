package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.PhoneHangUpOperation;
import com.xiaomi.micolauncher.instruciton.impl.PhoneMakeCallOperation;
import com.xiaomi.micolauncher.instruciton.impl.PhonePickUpOperation;
import com.xiaomi.micolauncher.instruciton.impl.PhoneShowContactsOperation;

/* loaded from: classes3.dex */
public class PhoneOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.Phone.NAME;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        switch (str.hashCode()) {
            case -2092382355:
                if (str.equals(AIApiConstants.Phone.HangUp)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1856284836:
                if (str.equals(AIApiConstants.Phone.PickUp)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1802692381:
                if (str.equals(AIApiConstants.Phone.Redial)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -974099145:
                if (str.equals(AIApiConstants.Phone.DialBack)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -698913236:
                if (str.equals(AIApiConstants.Phone.MakeCall)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1247548208:
                if (str.equals(AIApiConstants.Phone.ShowContacts)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return new PhoneHangUpOperation(instruction);
            case 1:
                return new PhonePickUpOperation(instruction);
            case 2:
                return new PhoneMakeCallOperation(instruction);
            case 3:
                return new PhoneShowContactsOperation(instruction);
            case 4:
            case 5:
            default:
                return null;
        }
    }
}
