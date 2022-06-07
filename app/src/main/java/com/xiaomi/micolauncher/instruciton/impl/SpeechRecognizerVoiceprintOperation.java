package com.xiaomi.micolauncher.instruciton.impl;

import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.SpeechRecognizer;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.skill.DomainConfig;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRegisterModel;

/* loaded from: classes3.dex */
public class SpeechRecognizerVoiceprintOperation extends BaseOperation {
    public SpeechRecognizerVoiceprintOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        char c;
        String fullName = this.instruction.getFullName();
        T payload = this.instruction.getPayload();
        int hashCode = fullName.hashCode();
        boolean z2 = true;
        if (hashCode == -1456719327) {
            if (fullName.equals(AIApiConstants.SpeechRecognizer.RecognizeVoiceprint)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode == -324390712) {
            if (fullName.equals(AIApiConstants.SpeechRecognizer.VoiceprintRegistrationStep)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 1751081753) {
            if (hashCode == 1956481096 && fullName.equals(AIApiConstants.SpeechRecognizer.VoiceprintRecognizeResult)) {
                c = 3;
            }
            c = 65535;
        } else {
            if (fullName.equals(AIApiConstants.SpeechRecognizer.VoiceprintRegistrationResult)) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                VoicePrintRegisterModel.getInstance().setNickNameConfirmTts(a());
                DomainConfig.VoicePrint.Action currentStep = VoicePrintRegisterModel.getInstance().getCurrentStep();
                L.ope.i("%s process RecognizeVoiceprint currentStep=%s", getOpName(), currentStep);
                if (currentStep != DomainConfig.VoicePrint.Action.REGISTER_PROCESS) {
                    Bundle bundle = null;
                    String a = a();
                    if (!TextUtils.isEmpty(a)) {
                        bundle = new Bundle();
                        bundle.putString(VoicePrintRegisterController.PARAMS_TTS, a);
                    }
                    VoicePrintRegisterModel.getInstance().getController().registerStartFake(bundle);
                    break;
                }
                break;
            case 1:
                SpeechRecognizer.VoiceprintRegistrationResult voiceprintRegistrationResult = (SpeechRecognizer.VoiceprintRegistrationResult) payload;
                VoicePrintRegisterModel.getInstance().setNickName(voiceprintRegistrationResult.getNickName().isPresent() ? voiceprintRegistrationResult.getNickName().get() : "");
                boolean booleanValue = voiceprintRegistrationResult.isFinished().isPresent() ? voiceprintRegistrationResult.isFinished().get().booleanValue() : false;
                VoicePrintRegisterController controller = VoicePrintRegisterModel.getInstance().getController();
                if (booleanValue) {
                    L.capability.i("%s process VoiceprintRegistrationResult finish=true", getOpName());
                    if (voiceprintRegistrationResult.getStatusCode() != 0) {
                        z2 = false;
                    }
                    controller.registerResult(z2, a());
                    VoicePrintRegisterModel.getInstance().setCurrentStep(DomainConfig.VoicePrint.Action.REGISTER_SUCCESS);
                    break;
                }
                break;
            case 2:
                SpeechRecognizer.VoiceprintRegistrationStep voiceprintRegistrationStep = (SpeechRecognizer.VoiceprintRegistrationStep) payload;
                VoicePrintRegisterModel.getInstance().setProgressStepInfo(voiceprintRegistrationStep.getAll(), voiceprintRegistrationStep.getCurrent(), a());
                VoicePrintRegisterController controller2 = VoicePrintRegisterModel.getInstance().getController();
                if (VoicePrintRegisterModel.getInstance().getCurrentStep() != DomainConfig.VoicePrint.Action.REGISTER_PROCESS) {
                    controller2.registerProgressStart();
                } else {
                    controller2.registerProgress();
                }
                VoicePrintRegisterModel.getInstance().setCurrentStep(DomainConfig.VoicePrint.Action.REGISTER_PROCESS);
                break;
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    private String a() {
        Instruction b = b();
        return b != null ? ((Template.Toast) b.getPayload()).getText() : "";
    }

    private Instruction b() {
        return InstructionUtil.getIntention(OperationManager.getInstance().getInstructions(getDialogId()), AIApiConstants.Template.Toast);
    }
}
