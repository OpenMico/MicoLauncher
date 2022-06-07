package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Sys;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;

/* loaded from: classes3.dex */
public class SystemUpgradeRomOperation extends BaseOperation<Instruction<Sys.UpgradeRom>> {
    public SystemUpgradeRomOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        if (RomUpdateAdapter.getInstance().hasNewVersion()) {
            SpeechManager.getInstance().ttsRequest(getContext().getString(R.string.local_tts_upgrade_now));
            RomUpdateAdapter.getInstance().doUpdate();
        } else {
            SpeechManager.getInstance().ttsRequest(getContext().getString(R.string.local_tts_upgrade_no_new_version));
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
