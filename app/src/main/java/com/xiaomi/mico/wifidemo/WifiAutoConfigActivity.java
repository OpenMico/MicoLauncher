package com.xiaomi.mico.wifidemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.micolauncher.R;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class WifiAutoConfigActivity extends Activity {
    private WifiManager a;
    private List<ScanResult> b;
    private Button d;
    private Button e;
    private Button f;
    private int c = -1;
    private View.OnClickListener g = new View.OnClickListener() { // from class: com.xiaomi.mico.wifidemo.WifiAutoConfigActivity.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (WifiAutoConfigActivity.this.a != null) {
                WifiConfiguration a = WifiAutoConfigActivity.this.a("25c829b1922d3123_miwifi");
                if (a != null) {
                    Log.d("WifiAutoConfigActivity", "remove existing network " + a.networkId + ": " + a.SSID);
                    WifiAutoConfigActivity.this.a.removeNetwork(a.networkId);
                }
                WifiConfiguration wifiConfiguration = new WifiConfiguration();
                wifiConfiguration.allowedAuthAlgorithms.clear();
                wifiConfiguration.allowedGroupCiphers.clear();
                wifiConfiguration.allowedKeyManagement.clear();
                wifiConfiguration.allowedPairwiseCiphers.clear();
                wifiConfiguration.allowedProtocols.clear();
                wifiConfiguration.SSID = "\"25c829b1922d3123_miwifi\"";
                wifiConfiguration.hiddenSSID = true;
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.status = 2;
                Log.d("WifiAutoConfigActivity", "add network " + wifiConfiguration.SSID);
                WifiAutoConfigActivity wifiAutoConfigActivity = WifiAutoConfigActivity.this;
                wifiAutoConfigActivity.c = wifiAutoConfigActivity.a.addNetwork(wifiConfiguration);
                if (WifiAutoConfigActivity.this.c == -1) {
                    Log.d("WifiAutoConfigActivity", "Failed to add hidden network.");
                }
            }
        }
    };
    private View.OnClickListener h = new View.OnClickListener() { // from class: com.xiaomi.mico.wifidemo.WifiAutoConfigActivity.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (WifiAutoConfigActivity.this.a != null) {
                Log.d("WifiAutoConfigActivity", "startScan");
                boolean startScan = WifiAutoConfigActivity.this.a.startScan();
                Log.d("WifiAutoConfigActivity", "WifiManager.startScan() returns " + startScan);
            }
        }
    };
    private View.OnClickListener i = new View.OnClickListener() { // from class: com.xiaomi.mico.wifidemo.WifiAutoConfigActivity.3
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            String str = "25c829b1922d3123_miwifi";
            String str2 = null;
            if (WifiAutoConfigActivity.this.b != null) {
                Iterator it = WifiAutoConfigActivity.this.b.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ScanResult scanResult = (ScanResult) it.next();
                    if ("25c829b1922d3123_miwifi".equals(scanResult.SSID)) {
                        str = scanResult.SSID;
                        str2 = scanResult.BSSID;
                        break;
                    }
                }
            } else {
                Log.w("WifiAutoConfigActivity", "no wifi list, plz scan first");
            }
            if (str2 == null) {
                Log.e("WifiAutoConfigActivity", "Didn't find expected hidden SSID, but try to connect with default SSID.");
            } else {
                Log.d("WifiAutoConfigActivity", "find SSID = " + str + " and BSSID = " + str2 + "in scan results.");
            }
            if (WifiAutoConfigActivity.this.a != null) {
                WifiConfiguration a = WifiAutoConfigActivity.this.a("25c829b1922d3123_miwifi");
                if (a != null) {
                    Log.d("WifiAutoConfigActivity", "remove existing network " + a.networkId + ": " + a.SSID);
                    WifiAutoConfigActivity.this.a.removeNetwork(a.networkId);
                }
                WifiConfiguration wifiConfiguration = new WifiConfiguration();
                wifiConfiguration.allowedAuthAlgorithms.clear();
                wifiConfiguration.allowedGroupCiphers.clear();
                wifiConfiguration.allowedKeyManagement.clear();
                wifiConfiguration.allowedPairwiseCiphers.clear();
                wifiConfiguration.allowedProtocols.clear();
                wifiConfiguration.SSID = "\"" + str + "\"";
                wifiConfiguration.BSSID = str2;
                wifiConfiguration.hiddenSSID = true;
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.status = 2;
                Log.d("WifiAutoConfigActivity", "add network, SSID = " + wifiConfiguration.SSID + ", BSSID = " + wifiConfiguration.BSSID);
                WifiAutoConfigActivity.this.a.setWifiEnabled(true);
                WifiAutoConfigActivity wifiAutoConfigActivity = WifiAutoConfigActivity.this;
                wifiAutoConfigActivity.c = wifiAutoConfigActivity.a.addNetwork(wifiConfiguration);
                if (WifiAutoConfigActivity.this.c == -1) {
                    Log.d("WifiAutoConfigActivity", "Failed to add hidden network.");
                    return;
                }
                Log.d("WifiAutoConfigActivity", "disconnect current network.");
                WifiAutoConfigActivity.this.a.disconnect();
                Log.d("WifiAutoConfigActivity", "enable network " + WifiAutoConfigActivity.this.c + ": 25c829b1922d3123_miwifi");
                WifiAutoConfigActivity.this.a.enableNetwork(WifiAutoConfigActivity.this.c, true);
                NetworkRequest build = new NetworkRequest.Builder().build();
                ConnectivityManager connectivityManager = (ConnectivityManager) WifiAutoConfigActivity.this.getSystemService(ConnectivityManager.class);
                if (connectivityManager != null) {
                    connectivityManager.requestNetwork(build, new ConnectivityManager.NetworkCallback() { // from class: com.xiaomi.mico.wifidemo.WifiAutoConfigActivity.3.1
                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onAvailable(Network network) {
                            super.onAvailable(network);
                            Log.d("WifiAutoConfigActivity", "network available ssid : " + WiFiUtil.getSSID(WifiAutoConfigActivity.this.getApplication()) + "bssid : " + WiFiUtil.getBSSID(WifiAutoConfigActivity.this.getApplication()));
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onLosing(Network network, int i) {
                            super.onLosing(network, i);
                            Log.d("WifiAutoConfigActivity", "onLosing");
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onLost(Network network) {
                            super.onLost(network);
                            Log.d("WifiAutoConfigActivity", "onLost");
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onUnavailable() {
                            super.onUnavailable();
                            Log.d("WifiAutoConfigActivity", "onUnavailable");
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                            super.onCapabilitiesChanged(network, networkCapabilities);
                            Log.d("WifiAutoConfigActivity", "onCapabilitiesChanged");
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                            super.onLinkPropertiesChanged(network, linkProperties);
                            Log.d("WifiAutoConfigActivity", "onLinkPropertiesChanged");
                        }
                    });
                }
            }
        }
    };
    private BroadcastReceiver j = new BroadcastReceiver() { // from class: com.xiaomi.mico.wifidemo.WifiAutoConfigActivity.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action;
            if (intent != null && (action = intent.getAction()) != null) {
                Log.d("WifiAutoConfigActivity", "action=" + action);
                char c = 65535;
                if (action.hashCode() == 1878357501 && action.equals("android.net.wifi.SCAN_RESULTS")) {
                    c = 0;
                }
                if (c != 0) {
                    Log.d("WifiAutoConfigActivity", String.format("wifi connected %B, ssid = %s bssid = %s", Boolean.valueOf(WiFiUtil.isWifiConnected(context)), WiFiUtil.getSSID(context), WiFiUtil.getBSSID(context)));
                } else {
                    WifiAutoConfigActivity.this.a();
                }
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = (WifiManager) getApplicationContext().getSystemService(com.xiaomi.smarthome.library.common.network.Network.NETWORK_TYPE_WIFI);
        setContentView(R.layout.activity_main1);
        this.d = (Button) findViewById(R.id.add_hidden_ssid);
        this.d.setOnClickListener(this.g);
        this.e = (Button) findViewById(R.id.scan_network);
        this.e.setOnClickListener(this.h);
        this.f = (Button) findViewById(R.id.connect_hidden_ssid);
        this.f.setOnClickListener(this.i);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getApplicationContext().registerReceiver(this.j, intentFilter);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        getApplicationContext().unregisterReceiver(this.j);
        this.a = null;
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WifiConfiguration a(String str) {
        for (WifiConfiguration wifiConfiguration : this.a.getConfiguredNetworks()) {
            String str2 = wifiConfiguration.SSID;
            if (str2.equals("\"" + str + "\"")) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.b = this.a.getScanResults();
        Log.d("WifiAutoConfigActivity", "Wifi scan results size: " + this.b.size());
        this.b.sort($$Lambda$WifiAutoConfigActivity$9hhHTBHHrYDE2CM9iLqtyVEIAsg.INSTANCE);
        for (ScanResult scanResult : this.b) {
            Log.d("WifiAutoConfigActivity", "BSSID = " + scanResult.BSSID + ", RSSI = " + scanResult.level + ", SSID = " + scanResult.SSID);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(ScanResult scanResult, ScanResult scanResult2) {
        return scanResult2.level - scanResult.level;
    }
}
