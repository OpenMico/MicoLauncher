package com.xiaomi.miot.host.lan.impl.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.xiaomi.miot.host.lan.impl.MiotLogUtil;
import com.xiaomi.miot.host.lan.impl.client.MiotClient;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotWifiConnectResult;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotWifiDisconnectedEvent;

/* loaded from: classes2.dex */
public class WifiNetworkCallback extends ConnectivityManager.NetworkCallback {
    private static String TAG = "WifiNetworkCallback";
    private String bssid;
    private Network connectedNetwork;
    private Context context;
    private String ssid;

    public WifiNetworkCallback(Context context) {
        this.context = context;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onAvailable(Network network) {
        super.onAvailable(network);
        MiotLogUtil.e(TAG, "wifi onAvailable");
        this.connectedNetwork = network;
        WifiInfo connectionInfo = ((WifiManager) this.context.getSystemService(com.xiaomi.smarthome.library.common.network.Network.NETWORK_TYPE_WIFI)).getConnectionInfo();
        String ssid = connectionInfo.getSSID();
        if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        MiotWifiConnectResult miotWifiConnectResult = new MiotWifiConnectResult();
        miotWifiConnectResult.setSsid(ssid);
        miotWifiConnectResult.setBssid(connectionInfo.getBSSID());
        miotWifiConnectResult.setResult("ok");
        setWifiInfo(ssid, connectionInfo.getBSSID());
        MiotClient.getInstance().sendMessageWithoutEncrypt(miotWifiConnectResult.encodeRequest());
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        MiotLogUtil.e(TAG, "wifi onCapabilitiesChanged");
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onLost(Network network) {
        super.onLost(network);
        MiotLogUtil.e(TAG, "wifi onLost");
        String str = TAG;
        MiotLogUtil.e(str, "ssid：" + this.ssid + ",bssid:" + this.bssid);
        MiotClient.getInstance().sendMessageWithoutEncrypt(new MiotWifiDisconnectedEvent(this.ssid, this.bssid).encodeRequest());
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onUnavailable() {
        super.onUnavailable();
        MiotWifiConnectResult miotWifiConnectResult = new MiotWifiConnectResult();
        miotWifiConnectResult.setSsid(this.ssid);
        miotWifiConnectResult.setBssid(this.bssid);
        miotWifiConnectResult.setResult("error");
        MiotClient.getInstance().sendMessageWithoutEncrypt(miotWifiConnectResult.encodeResponse());
    }

    public void setWifiInfo(String str, String str2) {
        this.ssid = str;
        this.bssid = str2;
    }
}
