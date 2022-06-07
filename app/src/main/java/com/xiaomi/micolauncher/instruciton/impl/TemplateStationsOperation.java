package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Station;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.view_v2.AudioBookPlayListActivity;
import java.util.List;

/* loaded from: classes3.dex */
public class TemplateStationsOperation extends BaseOperation<Instruction<Template.Stations>> {
    public TemplateStationsOperation(Instruction<Template.Stations> instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        AudioBookPlayListActivity.listHasShow = false;
        Template.Stations stations = (Template.Stations) this.instruction.getPayload();
        List<Template.StationItem> items = stations.getItems();
        if (ContainerUtil.isEmpty(items)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        String aiAlbumId = items.get(0).getAiAlbumId();
        OperationManager.getInstance().setOriginDialogId(aiAlbumId, this.instruction.getDialogId().isPresent() ? this.instruction.getDialogId().get() : "");
        eventRequest(aiAlbumId);
        LocalPlayerManager.getInstance().setStations(stations);
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    public static void eventRequest(String str) {
        AudioBookPlayListActivity.eventFormDisplayDetail = true;
        Station.DisplayDetails displayDetails = new Station.DisplayDetails();
        displayDetails.setId(str);
        SpeechManager.getInstance().sendEventRequest(APIUtils.buildEvent(displayDetails));
    }
}
