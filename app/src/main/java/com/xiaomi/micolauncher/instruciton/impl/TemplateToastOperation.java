package com.xiaomi.micolauncher.instruciton.impl;

import android.text.TextUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.common.AsrTtsCard;
import com.xiaomi.micolauncher.skills.translation.view.TranslationV2Fragment;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes3.dex */
public class TemplateToastOperation extends BaseOperation<Instruction<Template.Toast>> {
    private static final HashSet<String> b = new HashSet<String>() { // from class: com.xiaomi.micolauncher.instruciton.impl.TemplateToastOperation.1
        {
            add(AIApiConstants.Template.Alarm);
            add(AIApiConstants.Template.WeatherV2);
            add(AIApiConstants.FullScreenTemplate.Dialogue);
            add(AIApiConstants.SpeechRecognizer.RecognizeVoiceprint);
        }
    };

    public TemplateToastOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Template.Toast toast = (Template.Toast) this.instruction.getPayload();
        String text = toast.getText();
        boolean z2 = !TextUtils.isEmpty(text) && a();
        a(z2, text);
        if (!z2) {
            PreferenceUtils.setSettingString(getContext(), TranslationV2Fragment.KEY_TRANSLATION_DATA, toast.getQuery().get());
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    private void a(boolean z, String str) {
        L.capability.d("displayTts=%s,text=%s", Boolean.valueOf(z), str);
        if (z) {
            AsrTtsCard.getInstance().onTtsRequest(str);
            QueryLatency.getInstance().setTtsDisplayStartMs();
            return;
        }
        AsrTtsCard.getInstance().hideTts();
    }

    private boolean a() {
        List<Instruction> instructions = OperationManager.getInstance().getInstructions(getDialogId());
        if (ContainerUtil.isEmpty(instructions)) {
            return false;
        }
        boolean z = true;
        for (Instruction instruction : instructions) {
            if (b.contains(instruction.getFullName())) {
                z = false;
            }
        }
        if (z) {
            z = b(instructions);
        }
        return z ? !a(instructions) : z;
    }

    private boolean a(List<Instruction> list) {
        Instruction intention = InstructionUtil.getIntention(list, AIApiConstants.Template.General);
        if (intention == null) {
            return false;
        }
        return isValidBaiKe(intention);
    }

    public boolean isValidBaiKe(Instruction instruction) {
        String str;
        Template.General general = (Template.General) instruction.getPayload();
        List<Template.Image> images = general.getImages();
        if (!ContainerUtil.isEmpty(images)) {
            List<Template.ImageSource> sources = images.get(0).getSources();
            if (!ContainerUtil.isEmpty(sources)) {
                str = sources.get(0).getUrl();
                return TextUtils.isEmpty(general.getText()) || !TextUtils.isEmpty(str);
            }
        }
        str = null;
        if (TextUtils.isEmpty(general.getText())) {
        }
    }

    private boolean b(List<Instruction> list) {
        return InstructionUtil.getIntention(list, AIApiConstants.SpeechSynthesizer.Speak) != null;
    }
}
