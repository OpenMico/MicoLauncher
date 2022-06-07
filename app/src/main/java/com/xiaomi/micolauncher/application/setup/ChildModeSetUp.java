package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeUtils;

/* loaded from: classes3.dex */
public class ChildModeSetUp implements ISetupRule {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        ChildModeUtils.setEyeProtectionMode(SystemSetting.getKeyEyeProtectionModeEnable());
    }
}
