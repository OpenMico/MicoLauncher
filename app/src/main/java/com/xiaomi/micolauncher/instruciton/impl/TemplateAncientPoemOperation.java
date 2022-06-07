package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.event.UiEvent;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.ancientpoem.AncientPoemEntity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TemplateAncientPoemOperation extends BaseOperation<Instruction<Template.AncientPoem>> {
    public TemplateAncientPoemOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        List<Template.AncientPoemData> poems = ((Template.AncientPoem) this.instruction.getPayload()).getPoems();
        if (ContainerUtil.isEmpty(poems)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        ArrayList<AncientPoemEntity> a = a(poems);
        if (!ContainerUtil.isEmpty(a)) {
            EventBusRegistry.getEventBus().post(new UiEvent.AncientPoemUiEvent(getDialogId(), a));
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    private ArrayList<AncientPoemEntity> a(List<Template.AncientPoemData> list) {
        ArrayList<AncientPoemEntity> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            AncientPoemEntity ancientPoemEntity = new AncientPoemEntity();
            Template.AncientPoemData ancientPoemData = list.get(i);
            ancientPoemEntity.name = ancientPoemData.getName();
            ancientPoemEntity.verse = ancientPoemData.getVerse();
            ancientPoemEntity.authors = ancientPoemData.getAuthors();
            ancientPoemEntity.audioId = ancientPoemData.getAudioId();
            ancientPoemEntity.dynasty = ancientPoemData.getDynasty();
            if (ancientPoemData.getMeaning().isPresent()) {
                ancientPoemEntity.meaning = ancientPoemData.getMeaning().get();
            }
            arrayList.add(ancientPoemEntity);
        }
        return arrayList;
    }
}
