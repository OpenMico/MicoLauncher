package com.xiaomi.micolauncher.instruciton.impl;

import android.text.TextUtils;
import com.xiaomi.ai.api.Sys;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.utils.ResettingUtil;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;

/* loaded from: classes3.dex */
public class SystemEnvSwitchOperation extends BaseOperation<Instruction<Sys.EnvSwitch>> {
    public SystemEnvSwitchOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        if (a()) {
            L.ope.i("do not support switch env for release");
            return BaseOperation.OpState.STATE_FAIL;
        }
        Sys.Environment env = ((Sys.EnvSwitch) this.instruction.getPayload()).getEnv();
        String str = "";
        if (Sys.Environment.PRODUCTION == env) {
            str = ApiConstants.LEVEL_PRODUCTION;
        } else if (Sys.Environment.PREVIEW == env) {
            str = "preview";
        } else if (Sys.Environment.PREVIEW4TEST == env) {
            str = "preview4test";
        }
        if (TextUtils.isEmpty(str)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        if (ApiConstants.setMiBrainLevel(str)) {
            L.base.i("!! SystemEnvSwitchOperation ResettingUtil rebootSystem !!");
            ResettingUtil.rebootSystem("change mico environment by instruction");
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    private boolean a() {
        return "release".equalsIgnoreCase(SystemSetting.getRomChannel());
    }
}
