package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.General;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.battery.BatteryStatusMonitor;

/* loaded from: classes3.dex */
public class GeneralQueryClientStatusOperation extends BaseOperation<Instruction<General.QueryClientStatus>> {
    public GeneralQueryClientStatusOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        switch (((General.QueryClientStatus) this.instruction.getPayload()).getItem()) {
            case REMAINING_BATTERY:
            case SOUNDBOX_REMAINING_BATTERY:
                SpeechManager.getInstance().ttsRequest(getContext().getString(R.string.current_system_battery, Integer.valueOf(BatteryStatusMonitor.getInstance().getLevel())), true);
                break;
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
