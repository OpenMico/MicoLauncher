package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Alerts;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.alarm.AlarmHelper;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmCreationPromptEvent;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import java.util.List;

/* loaded from: classes3.dex */
public class TemplateAlarmOperation extends BaseOperation<Instruction<Template.Alarm>> {
    public TemplateAlarmOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Template.Alarm alarm = (Template.Alarm) this.instruction.getPayload();
        Template.AlarmOperation operation = alarm.getOperation();
        if (operation == Template.AlarmOperation.QUERY) {
            AlarmHelper.showAlarmList(getContext(), getDialogId());
            return BaseOperation.OpState.STATE_SUCCESS;
        }
        if (operation == Template.AlarmOperation.CREATE) {
            List<Template.AlarmItem> items = alarm.getItems();
            if (ContainerUtil.isEmpty(items)) {
                return BaseOperation.OpState.STATE_FAIL;
            }
            if (items.get(0).getType() != Alerts.AlertType.TIMER) {
                List<AlarmRealmObjectBean> parseAlarmItems = AlertsOperation.parseAlarmItems(items);
                if (!ContainerUtil.isEmpty(parseAlarmItems)) {
                    a(parseAlarmItems.get(0));
                    return BaseOperation.OpState.STATE_SUCCESS;
                }
            }
        }
        return BaseOperation.OpState.STATE_FAIL;
    }

    private void a(AlarmRealmObjectBean alarmRealmObjectBean) {
        AlarmCreationPromptEvent alarmCreationPromptEvent = new AlarmCreationPromptEvent();
        alarmCreationPromptEvent.alarmItem = alarmRealmObjectBean;
        EventBusRegistry.getEventBus().post(alarmCreationPromptEvent);
    }
}
