package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager;

/* loaded from: classes3.dex */
public class BluetoothSettingSetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        BluetoothSettingManager.init(context);
        if (BluetoothSettingManager.getInstance() != null) {
            BluetoothSettingManager.getInstance().start();
        }
        L.base.d("ImsModelSetup init BluetoothSettingManager.");
    }

    @Override // com.xiaomi.micolauncher.application.setup.AbsSyncSetup, com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
        if (BluetoothSettingManager.getInstance() != null) {
            BluetoothSettingManager.getInstance().destroy();
        }
    }
}
