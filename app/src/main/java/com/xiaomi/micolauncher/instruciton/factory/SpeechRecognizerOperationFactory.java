package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.SpeechExpectSpeechOperation;
import com.xiaomi.micolauncher.instruciton.impl.SpeechRecognizerVoiceprintOperation;

/* loaded from: classes3.dex */
public class SpeechRecognizerOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return "SpeechRecognizer";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        switch (str.hashCode()) {
            case -2094528890:
                if (str.equals(AIApiConstants.SpeechRecognizer.RegisterVoiceprint)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1456719327:
                if (str.equals(AIApiConstants.SpeechRecognizer.RecognizeVoiceprint)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -324390712:
                if (str.equals(AIApiConstants.SpeechRecognizer.VoiceprintRegistrationStep)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1327948931:
                if (str.equals(AIApiConstants.SpeechRecognizer.RecognizeResult)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1631722339:
                if (str.equals(AIApiConstants.SpeechRecognizer.ExpectSpeech)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1751081753:
                if (str.equals(AIApiConstants.SpeechRecognizer.VoiceprintRegistrationResult)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1956481096:
                if (str.equals(AIApiConstants.SpeechRecognizer.VoiceprintRecognizeResult)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1963775772:
                if (str.equals(AIApiConstants.SpeechRecognizer.StopCapture)) {
                    c = 2;
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
            case 2:
            case 3:
            default:
                return null;
            case 1:
                return new SpeechExpectSpeechOperation(instruction);
            case 4:
            case 5:
            case 6:
            case 7:
                return new SpeechRecognizerVoiceprintOperation(instruction);
        }
    }
}
