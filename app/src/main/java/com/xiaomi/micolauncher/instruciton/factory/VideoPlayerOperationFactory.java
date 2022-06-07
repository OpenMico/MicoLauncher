package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.video.LaunchPlayAppOperation;
import com.xiaomi.micolauncher.instruciton.impl.video.PlayListOperation;
import com.xiaomi.micolauncher.instruciton.impl.video.PlayOperation;

/* loaded from: classes3.dex */
public class VideoPlayerOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.VideoPlayer.NAME;
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1790160732) {
            if (str.equals(AIApiConstants.VideoPlayer.PlayList)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode == -835318042) {
            if (str.equals(AIApiConstants.VideoPlayer.Play)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 418248463) {
            if (hashCode == 813380520 && str.equals(AIApiConstants.VideoPlayer.LaunchPlayApp)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(AIApiConstants.VideoPlayer.PlayMV)) {
                c = 3;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return new PlayOperation(instruction);
            case 1:
                return new LaunchPlayAppOperation(instruction);
            case 2:
                return new PlayListOperation(instruction);
            case 3:
                return null;
            default:
                return null;
        }
    }
}
