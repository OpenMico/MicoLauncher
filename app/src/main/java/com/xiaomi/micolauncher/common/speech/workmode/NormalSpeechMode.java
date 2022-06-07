package com.xiaomi.micolauncher.common.speech.workmode;

import com.xiaomi.ai.speaker.vpmclient.VpmClient;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.player.PromptSoundPlayer;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.BaseSpeechEngine;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;

/* loaded from: classes3.dex */
public class NormalSpeechMode extends BaseSpeechWorkMode {
    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public String name() {
        return "Normal";
    }

    public NormalSpeechMode(VpmClient vpmClient, BaseSpeechEngine baseSpeechEngine) {
        super(vpmClient, baseSpeechEngine);
    }

    @Override // com.xiaomi.micolauncher.common.speech.workmode.BaseSpeechWorkMode
    public boolean wakeupHandle() {
        super.wakeupHandle();
        if (SpeechContextHelper.isAlarmRunningWhenPeak()) {
            SpeechContextHelper.clearCachedContext();
            PromptSoundPlayer.getInstance().play(R.raw.close_alarm);
            SpeechManager.getInstance().setUnWakeup();
            return false;
        }
        OperationManager.getInstance().onWakeup(true, !SpeechConfig.getWakeupSound());
        return true;
    }
}
