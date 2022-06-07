package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.UIController;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControl;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.BackEvent;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.NextPageEvent;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.PrevPageEvent;

/* loaded from: classes3.dex */
public class UIControllerNavigateOperation extends BaseOperation<Instruction<UIController.Navigate>> {
    public UIControllerNavigateOperation(Instruction instruction) {
        super(instruction);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        switch (((UIController.Navigate) this.instruction.getPayload()).getOperation()) {
            case EXIT_XIAOAI:
            case EXIT:
                a();
                SchemaManager.handleSchema(getContext(), HomepageSchemaHandler.PATH_MAIN_PAGE);
                break;
            case HOME:
                SchemaManager.handleSchema(getContext(), HomepageSchemaHandler.PATH_MAIN_PAGE);
                return BaseOperation.OpState.STATE_SUCCESS;
            case BACK:
                EventBusRegistry.getEventBus().post(new BackEvent());
                return BaseOperation.OpState.STATE_SUCCESS;
            case PREV_PAGE:
                EventBusRegistry.getEventBus().post(new PrevPageEvent());
                return BaseOperation.OpState.STATE_SUCCESS;
            case NEXT_PAGE:
                EventBusRegistry.getEventBus().post(new NextPageEvent());
                return BaseOperation.OpState.STATE_SUCCESS;
        }
        return BaseOperation.OpState.STATE_FAIL;
    }

    private void a() {
        AudioPolicyService.getInstance().playbackController(PlaybackControl.PAUSE);
    }
}
