package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Speaker;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.module.setting.utils.SystemVolume;

/* loaded from: classes3.dex */
public class SpeakerAdjustVolumeOperation extends BaseOperation<Instruction<Speaker.AdjustVolume>> {
    public SpeakerAdjustVolumeOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Speaker.AdjustVolume adjustVolume = (Speaker.AdjustVolume) this.instruction.getPayload();
        int volumeDelta = adjustVolume.getVolumeDelta();
        Speaker.UnitDef unit = adjustVolume.getUnit();
        if (unit == Speaker.UnitDef.PERCENT) {
            a(volumeDelta, adjustVolume.getType());
            return BaseOperation.OpState.STATE_SUCCESS;
        } else if (unit == Speaker.UnitDef.ABSOLUTE) {
            return BaseOperation.OpState.STATE_FAIL;
        } else {
            return BaseOperation.OpState.STATE_FAIL;
        }
    }

    private boolean a() {
        return SpeechManager.getInstance().isNlpRequest();
    }

    private void a(int i, Speaker.VolumeType volumeType) {
        int i2;
        L.speech.i("volumeType = %d, adjustVolume=%s", Integer.valueOf(volumeType.ordinal()), Integer.valueOf(i));
        if (volumeType == Speaker.VolumeType.ALARM) {
            SystemVolume.getInstance().setAlarmVolume(SystemVolume.getInstance().getAlarmVolume() + i, false);
            i2 = SystemVolume.getInstance().getAlarmVolume();
        } else {
            SystemVolume.getInstance().setVolume(SystemVolume.getInstance().getVolume() + i);
            i2 = SystemVolume.getInstance().getVolume();
        }
        if (!Screen.getInstance().isHoldByPlay() && !a() && i2 >= 0) {
            SpeechManager.getInstance().ttsRequest(getContext().getString(R.string.local_tts_volume, Integer.valueOf(i2)));
        }
    }
}
