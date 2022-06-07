package com.ouyang.network.monitor;

/* loaded from: classes2.dex */
public interface NetworkChangeListener {
    void onEthernetConnected();

    void onEthernetDisconnected();

    void onMobileConnected();

    void onMobileDisconnected();

    void onWifiConnected();

    void onWifiDisconnected();
}
