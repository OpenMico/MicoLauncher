package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.UiEvent;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.joke.JokeItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TemplatePlayInfoOperation extends BaseOperation<Instruction<Template.PlayInfo>> {
    public TemplatePlayInfoOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Template.PlayInfo playInfo = (Template.PlayInfo) this.instruction.getPayload();
        if (!playInfo.getType().isPresent()) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        Template.PlayInfoType playInfoType = playInfo.getType().get();
        if (playInfoType == Template.PlayInfoType.JOKE) {
            return a(this.instruction);
        }
        if (!(playInfoType == Template.PlayInfoType.MUSIC || playInfoType == Template.PlayInfoType.STATION)) {
            Template.PlayInfoType playInfoType2 = Template.PlayInfoType.VOICE_NEWS;
        }
        return BaseOperation.OpState.STATE_FAIL;
    }

    private BaseOperation.OpState a(Instruction instruction) {
        ArrayList<JokeItem> parsePlayInfoForJoke = parsePlayInfoForJoke(((Template.PlayInfo) instruction.getPayload()).getItems());
        if (!ContainerUtil.isEmpty(parsePlayInfoForJoke)) {
            EventBusRegistry.getEventBus().post(new UiEvent.JokeUiEvent(getDialogId(), parsePlayInfoForJoke));
            return BaseOperation.OpState.STATE_SUCCESS;
        }
        L.capability.d("jokeItems is empty");
        return BaseOperation.OpState.STATE_FAIL;
    }

    public static ArrayList<JokeItem> parsePlayInfoForJoke(List<Template.PlayInfoItem> list) {
        if (ContainerUtil.isEmpty(list)) {
            return null;
        }
        ArrayList<JokeItem> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            JokeItem jokeItem = new JokeItem();
            Template.PlayInfoItem playInfoItem = list.get(i);
            if (playInfoItem.getText().isPresent()) {
                jokeItem.text = playInfoItem.getText().get();
                jokeItem.id = playInfoItem.getAudioId();
                arrayList.add(jokeItem);
            }
        }
        return arrayList;
    }
}
