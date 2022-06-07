package com.xiaomi.mico.base.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import androidx.annotation.GuardedBy;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.LogCallback;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.mico.utils.UtilsConfig;
import com.xiaomi.smarthome.library.common.network.Network;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class HiddenWifiScanHelper {
    public static final String DEFAULT_HIDDEN_SSID = "25c829b1922d3123_miwifi";
    private final Context a;
    private boolean b;
    private final String c;
    private OnScanResultListener d;
    private final Object e;
    @GuardedBy("scanningStateLock")
    private boolean f;
    private volatile int g;
    private List<Integer> h;
    private BroadcastReceiver i;

    /* loaded from: classes3.dex */
    public interface OnScanResultListener {
        void onScanResult(List<ScanResult> list);
    }

    public static String getDefaultHiddenSsid() {
        return "25c829b1922d3123_miwifi";
    }

    public HiddenWifiScanHelper(Context context) {
        this(context, "25c829b1922d3123_miwifi");
    }

    public HiddenWifiScanHelper(Context context, String str) {
        this.b = false;
        this.e = new Object();
        this.g = 0;
        this.h = new ArrayList();
        this.i = new BroadcastReceiver() { // from class: com.xiaomi.mico.base.utils.HiddenWifiScanHelper.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action;
                if (intent != null && (action = intent.getAction()) != null && "android.net.wifi.SCAN_RESULTS".equals(action)) {
                    HiddenWifiScanHelper.b("scan results available", new Object[0]);
                    HiddenWifiScanHelper.this.a(false);
                }
            }
        };
        this.a = context;
        this.c = str;
    }

    public void init() {
        if (!this.b) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            this.a.registerReceiver(this.i, intentFilter, null, Threads.getLightWorkHandler());
            this.b = true;
        }
    }

    public boolean isScanning() {
        boolean z;
        synchronized (this.e) {
            z = this.f;
        }
        return z;
    }

    public void startScan() {
        if (!isInited()) {
            b("not initialized");
            return;
        }
        WifiManager a = a(this.a);
        if (ContainerUtil.hasData(this.h)) {
            boolean z = false;
            for (Integer num : this.h) {
                if (!a(a, num.intValue())) {
                    b("failed to add channel " + num);
                } else {
                    z = true;
                }
            }
            int i = this.g;
            this.g = i + 1;
            b("Time %s, %s add network %s", Integer.valueOf(i), "HiddenWifiScanHelper", this.c);
            if (!z) {
                b("failed to add config with explicit channel");
                return;
            }
        } else if (!a(a, 0)) {
            b("failed to add config without explicit channel");
            return;
        }
        this.g++;
        a.startScan();
        synchronized (this.e) {
            this.f = true;
        }
    }

    private boolean a(WifiManager wifiManager, int i) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = a(WiFiUtil.stripSSID(this.c));
        wifiConfiguration.hiddenSSID = true;
        wifiConfiguration.allowedKeyManagement.set(0);
        wifiConfiguration.status = 2;
        if (i > 0) {
            wifiConfiguration.apChannel = i;
        }
        b("%s add network %s, channel %s", "HiddenWifiScanHelper", wifiConfiguration.SSID, Integer.valueOf(i));
        if (wifiManager.addNetwork(wifiConfiguration) != -1) {
            return true;
        }
        b("HiddenWifiScanHelper failed to add hidden network.");
        return false;
    }

    @NotNull
    private static String a(String str) {
        return "\"" + str + "\"";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        synchronized (this.e) {
            if (this.f) {
                if (z) {
                    b("scanning timeout.");
                }
                this.f = false;
                b("set scanning to false", new Object[0]);
                OnScanResultListener onScanResultListener = this.d;
                if (onScanResultListener != null) {
                    Threads.verifyThread();
                    onScanResultListener.onScanResult(WiFiUtil.getWiFiList(this.a));
                }
            }
        }
    }

    public boolean isInited() {
        return this.b;
    }

    public void destroy() {
        if (isInited()) {
            this.a.unregisterReceiver(this.i);
            this.b = false;
        }
    }

    private static WifiManager a(Context context) {
        return (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, Object... objArr) {
        LogCallback logCallback = UtilsConfig.getLogCallback();
        if (logCallback == null) {
            Log.i("HiddenWifiScanHelper", String.format(str, objArr));
        } else {
            logCallback.i(str, objArr);
        }
    }

    private static void b(String str) {
        LogCallback logCallback = UtilsConfig.getLogCallback();
        if (logCallback == null) {
            Log.e("HiddenWifiScanHelper", str);
        } else {
            logCallback.e(str, new Object[0]);
        }
    }

    public static boolean isDefaultHiddenSsid(String str) {
        return WiFiUtil.isSameSSID(str, getDefaultHiddenSsid());
    }

    public String getHiddenSsid() {
        return this.c;
    }

    public void setOnScanResultListener(OnScanResultListener onScanResultListener) {
        this.d = onScanResultListener;
    }

    public List<Integer> getChannels() {
        return this.h;
    }

    public void setChannels(List<Integer> list) {
        this.h = list;
    }
}
