package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.SpeakerAdjustVolumeOperation;
import com.xiaomi.micolauncher.instruciton.impl.SpeakerSetMuteOperation;
import com.xiaomi.micolauncher.instruciton.impl.SpeakerSetVolumeOperation;

/* loaded from: classes3.dex */
public class SpeakerOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.Speaker.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1603244712) {
            if (str.equals(AIApiConstants.Speaker.AdjustVolume)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 506647148) {
            if (hashCode == 1808506541 && str.equals(AIApiConstants.Speaker.SetVolume)) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals(AIApiConstants.Speaker.SetMute)) {
                c = 2;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return new SpeakerSetVolumeOperation(instruction);
            case 1:
                return new SpeakerAdjustVolumeOperation(instruction);
            case 2:
                return new SpeakerSetMuteOperation(instruction);
            default:
                return null;
        }
    }
}
