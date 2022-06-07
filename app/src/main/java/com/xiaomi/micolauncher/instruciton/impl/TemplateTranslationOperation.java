package com.xiaomi.micolauncher.instruciton.impl;

import android.text.TextUtils;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.event.StartTranslationUiEvent;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;

@Deprecated
/* loaded from: classes3.dex */
public class TemplateTranslationOperation extends BaseOperation<Instruction<Template.Translation>> {
    public TemplateTranslationOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Template.Translation translation = (Template.Translation) this.instruction.getPayload();
        String srcText = translation.getSrcText();
        String destText = translation.getDestText();
        String dialogId = getDialogId();
        if (TextUtils.isEmpty(destText) || TextUtils.isEmpty(srcText)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        EventBusRegistry.getEventBus().post(new StartTranslationUiEvent(srcText, destText, dialogId));
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
