package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Scene;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;

/* loaded from: classes3.dex */
public class SceneEnterOperation extends BaseOperation<Instruction<Scene.Enter>> {
    public SceneEnterOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Scene.Enter enter = (Scene.Enter) this.instruction.getPayload();
        Scene.SceneType type = enter.getType();
        int intValue = enter.getMicDuration().isPresent() ? enter.getMicDuration().get().intValue() : 30;
        SpeechContextHelper.sceneType = type;
        SpeechManager.getInstance().enterScene(intValue, type.toString());
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
