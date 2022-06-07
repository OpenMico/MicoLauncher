package com.xiaomi.micolauncher.instruciton.impl;

import android.text.TextUtils;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.event.StartTranslationV2UiEvent;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.translation.bean.Translation;

/* loaded from: classes3.dex */
public class TemplateTranslationV2Operation extends BaseOperation<Instruction<Template.Translation>> {
    public TemplateTranslationV2Operation(Instruction<Template.Translation> instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Template.Translation translation = (Template.Translation) this.instruction.getPayload();
        if (TextUtils.isEmpty(translation.getSrcText()) && TextUtils.isEmpty(translation.getDestText())) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        EventBusRegistry.getEventBus().post(new StartTranslationV2UiEvent(new Translation(translation)));
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
