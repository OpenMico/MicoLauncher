package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothAudioRelayManager;

/* loaded from: classes3.dex */
public class BluetoothAudioRelaySetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        BluetoothAudioRelayManager.init(context);
        if (BluetoothAudioRelayManager.getInstance() != null) {
            BluetoothAudioRelayManager.getInstance().start();
        }
        L.base.d("ImsModelSetup init BluetoothAudioRelayManager.");
    }

    @Override // com.xiaomi.micolauncher.application.setup.AbsSyncSetup, com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
        if (BluetoothAudioRelayManager.getInstance() != null) {
            BluetoothAudioRelayManager.getInstance().destroy();
        }
    }
}
