package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Speaker;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControl;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;

/* loaded from: classes3.dex */
public class SpeakerSetMuteOperation extends BaseOperation<Instruction<Speaker.SetMute>> {
    public SpeakerSetMuteOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        if (((Speaker.SetMute) this.instruction.getPayload()).isMute()) {
            AudioPolicyService.getInstance().playbackController(PlaybackControl.PAUSE);
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
