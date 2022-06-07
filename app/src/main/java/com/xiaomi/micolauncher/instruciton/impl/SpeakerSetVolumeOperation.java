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
public class SpeakerSetVolumeOperation extends BaseOperation<Instruction<Speaker.SetVolume>> {
    public SpeakerSetVolumeOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Speaker.SetVolume setVolume = (Speaker.SetVolume) this.instruction.getPayload();
        int volume = setVolume.getVolume();
        Speaker.UnitDef unit = setVolume.getUnit();
        if (unit == Speaker.UnitDef.PERCENT) {
            a(volume, setVolume.getType());
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
        L.ope.i("setVolume=%s", Integer.valueOf(i));
        if (volumeType == Speaker.VolumeType.ALARM) {
            SystemVolume.getInstance().setAlarmVolume(i, false);
        } else {
            SystemVolume.getInstance().setVolume(i);
        }
        if (!Screen.getInstance().isHoldByPlay() && !a()) {
            if (i <= 5) {
                SpeechManager.getInstance().ttsRequest(getContext().getString(R.string.local_tts_volume_min));
            } else if (i <= 10) {
                SpeechManager.getInstance().ttsRequest(getContext().getString(R.string.local_tts_volume_min_warning));
            } else if (i > 100) {
                SpeechManager.getInstance().ttsRequest(getContext().getString(R.string.local_tts_volume_exceed_100_warning));
            } else if (i >= 80) {
                SpeechManager.getInstance().ttsRequest(getContext().getString(R.string.local_tts_volume_max_warning));
            } else {
                SpeechManager.getInstance().ttsRequest(getContext().getString(R.string.local_tts_ok));
            }
        }
    }
}
