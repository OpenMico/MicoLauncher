package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.PlaybackControllerOperation;

/* loaded from: classes3.dex */
public class PlaybackControllerOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.PlaybackController.NAME;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        switch (str.hashCode()) {
            case -1990497024:
                if (str.equals(AIApiConstants.PlaybackController.SetProperty)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1954408854:
                if (str.equals(AIApiConstants.PlaybackController.Next)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1954343253:
                if (str.equals(AIApiConstants.PlaybackController.Play)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1954337366:
                if (str.equals(AIApiConstants.PlaybackController.Prev)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1954260497:
                if (str.equals(AIApiConstants.PlaybackController.Seek)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1954245767:
                if (str.equals(AIApiConstants.PlaybackController.Stop)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1315060253:
                if (str.equals(AIApiConstants.PlaybackController.StopAfter)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1234733648:
                if (str.equals(AIApiConstants.PlaybackController.ContinuePlaying)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1171720590:
                if (str.equals(AIApiConstants.PlaybackController.Rewind)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -751535297:
                if (str.equals(AIApiConstants.PlaybackController.StartOver)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -455407265:
                if (str.equals(AIApiConstants.PlaybackController.Pause)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 412115826:
                if (str.equals(AIApiConstants.PlaybackController.FastForward)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1868090825:
                if (str.equals(AIApiConstants.PlaybackController.CancelStopAfter)) {
                    c = 11;
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
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
                return new PlaybackControllerOperation(instruction);
            default:
                return null;
        }
    }
}
