package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.micolauncher.module.setting.utils.SleepMode;
import com.xiaomi.smarthome.library.common.network.Network;

/* loaded from: classes3.dex */
public class EarlySetups extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        ToastUtil.init(context);
        SleepMode.initialize(context);
        a(context, "25c829b1922d3123_miwifi");
    }

    private void a(Context context, String str) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
        for (WifiConfiguration wifiConfiguration : WiFiUtil.getWifiManager(context).getConfiguredNetworks()) {
            if (wifiConfiguration.hiddenSSID && WiFiUtil.isSameSSID(str, wifiConfiguration.SSID)) {
                boolean removeNetwork = wifiManager.removeNetwork(wifiConfiguration.networkId);
                Log.w("MICO.ot", "remove hidden ssid removeResult=" + removeNetwork + " networkId=" + wifiConfiguration.networkId);
            }
        }
    }
}
