package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.LogCallback;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.mico.utils.UtilsConfig;
import com.xiaomi.smarthome.library.common.network.Network;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class HiddenWifiConnectHelper {
    private static final long a = TimeUnit.SECONDS.toMillis(9);
    private boolean b;
    private Context c;
    private volatile String e;
    private volatile String f;
    private OnConnectResultListener j;
    private volatile boolean k;
    private HandlerThread l;
    private Handler m;
    private Runnable n;
    private a p;
    private volatile int d = 0;
    private volatile int g = 0;
    private volatile boolean h = false;
    private AtomicBoolean i = new AtomicBoolean(false);
    private List<Integer> o = new ArrayList();

    /* loaded from: classes3.dex */
    public interface OnConnectResultListener {
        void onConnected(WifiInfo wifiInfo);

        void onDisConnected();

        void onFailed(String str, String str2);
    }

    /* loaded from: classes3.dex */
    public interface OnWifiRemovedListener {
        void onWifiRemoveFailed();

        void onWifiRemoveSuccess();
    }

    public synchronized boolean isConnecting() {
        return this.k;
    }

    public synchronized void connect(final Context context, final String str, String str2, @Nullable final OnConnectResultListener onConnectResultListener) {
        if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) {
            if (!this.k) {
                WifiInfo b = b(context);
                if (b != null && WiFiUtil.isSameSSID(b.getSSID(), str) && str2.equalsIgnoreCase(b.getBSSID()) && onConnectResultListener != null) {
                    Log.w("MICO.ot", "has connected hidden ssid before enable it!");
                    onConnectResultListener.onConnected(b);
                    return;
                }
            } else if (TextUtils.equals(this.e, str) && TextUtils.equals(this.f, str2)) {
                c("HiddenWifiConnectHelper  connect hidden ssid isConnecting, ignore same connect request!", new Object[0]);
                Log.w("MICO.ot", "connect hidden ssid isConnecting, ignore same connect request!");
                return;
            }
            if (!this.i.get() || TextUtils.equals(this.f, str2)) {
                this.h = false;
            } else {
                this.h = true;
            }
            this.c = context;
            this.j = onConnectResultListener;
            this.e = str;
            this.f = str2;
            this.k = true;
            if (!this.b) {
                this.l = new HandlerThread("HiddenWifiConnectHelper ", 0);
                this.l.start();
                this.m = new Handler(this.l.getLooper());
                this.p = new a(context);
                ((ConnectivityManager) context.getSystemService(ConnectivityManager.class)).registerDefaultNetworkCallback(this.p, this.m);
                this.b = true;
            }
            if (this.n == null) {
                this.n = new Runnable() { // from class: com.xiaomi.mico.base.utils.-$$Lambda$HiddenWifiConnectHelper$lLxuLu8ul_zy_AfcKS-d_YEeXBY
                    @Override // java.lang.Runnable
                    public final void run() {
                        HiddenWifiConnectHelper.this.a(context, onConnectResultListener, str);
                    }
                };
            }
            a(context, str, str2);
            return;
        }
        c("HiddenWifiConnectHelper  both ssid or bssid are empty", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, @Nullable OnConnectResultListener onConnectResultListener, String str) {
        synchronized (this) {
            if (this.g > 0) {
                this.g = 0;
                this.k = false;
                this.i.set(false);
                c(context).removeNetwork(this.d);
                if (onConnectResultListener != null) {
                    onConnectResultListener.onFailed(this.e, this.f);
                }
                c("HiddenWifiConnectHelper connect to hidden ssid bssid=%s time out, call failed listener", this.f);
            } else {
                Log.w("MICO.ot", "connect hidden ssid failed, retry once. bssid=" + this.f);
                c("HiddenWifiConnectHelper connect hidden ssid bssid=%s failed, retry once", this.f);
                a(context, str, this.f);
                this.g = this.g + 1;
            }
        }
    }

    private synchronized void a(Context context, String str, String str2) {
        WifiManager c = c(context);
        if (!ContainerUtil.hasData(this.o) || ContainerUtil.getSize(this.o) >= 13) {
            this.d = a(str, str2, c, 0);
        } else {
            for (Integer num : this.o) {
                this.d = a(str, str2, c, num.intValue());
            }
        }
        WifiInfo connectionInfo = c.getConnectionInfo();
        if (!(connectionInfo == null || connectionInfo.getNetworkId() == -1)) {
            boolean disableNetwork = c.disableNetwork(connectionInfo.getNetworkId());
            b("disable current wifi " + connectionInfo.getNetworkId() + " before connect another hidden wifi :" + disableNetwork, new Object[0]);
        }
        boolean enableNetwork = c.enableNetwork(this.d, true);
        b("HiddenWifiConnectHelper  enable networkId=%s, SSID=%s, bssid=%s result=%s", Integer.valueOf(this.d), str, str2, Boolean.valueOf(enableNetwork));
        Log.w("MICO.ot", "enableNetwork hiddenSsid bssid=" + str2 + " networkId=" + this.d + " enableNetwork=" + enableNetwork);
        this.m.removeCallbacks(this.n);
        this.m.postDelayed(this.n, a);
    }

    private int a(String str, String str2, WifiManager wifiManager, int i) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        if (!TextUtils.isEmpty(str)) {
            wifiConfiguration.SSID = a(WiFiUtil.stripSSID(str));
        }
        wifiConfiguration.BSSID = str2;
        wifiConfiguration.hiddenSSID = true;
        wifiConfiguration.allowedKeyManagement.set(0);
        if (i > 0) {
            wifiConfiguration.apChannel = i;
        }
        wifiConfiguration.status = 2;
        wifiManager.setWifiEnabled(true);
        int addNetwork = wifiManager.addNetwork(wifiConfiguration);
        b("HiddenWifiConnectHelper  add network, SSID=%s, BSSID=%s, networkId=%s", wifiConfiguration.SSID, wifiConfiguration.BSSID, Integer.valueOf(addNetwork));
        if (addNetwork == -1) {
            c("HiddenWifiConnectHelper  failed to add hidden network.", new Object[0]);
        }
        return addNetwork;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context) {
        synchronized (this) {
            this.k = false;
            this.i.set(true);
            this.g = 0;
            this.m.removeCallbacks(this.n);
            b("HiddenWifiConnectHelper set connecting to false, retryConnect set 0", new Object[0]);
            Log.d("MICO.ot", "HiddenWifiConnectHelper connect hidden ssid success, reset flag");
        }
        WifiInfo b = b(context);
        if (b != null && !WiFiUtil.isSameSSID(this.e, b.getSSID())) {
            c("HiddenWifiConnectHelper Unexpected, connected ssid %s != given ssid %s", b.getSSID(), this.e);
        }
        OnConnectResultListener onConnectResultListener = this.j;
        if (onConnectResultListener != null && b != null) {
            onConnectResultListener.onConnected(b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        OnConnectResultListener onConnectResultListener;
        if (this.i.compareAndSet(true, false) && !this.h && (onConnectResultListener = this.j) != null) {
            onConnectResultListener.onDisConnected();
        }
    }

    private static WifiInfo b(Context context) {
        WifiManager c = c(context);
        WifiInfo wiFiInfo = WiFiUtil.getWiFiInfo(context);
        if (wiFiInfo != null) {
            b("HiddenWifiConnectHelper ConnectInfo mac=%s, network Id=%s, ssid=%s, bssid=%s, rssi=%s", wiFiInfo.getMacAddress(), Integer.valueOf(wiFiInfo.getNetworkId()), wiFiInfo.getSSID(), wiFiInfo.getBSSID(), Integer.valueOf(wiFiInfo.getRssi()));
        } else {
            c("HiddenWifiConnectHelper no connect info", new Object[0]);
        }
        int wifiState = c.getWifiState();
        if (wifiState != 1) {
            switch (wifiState) {
                case 3:
                    b("HiddenWifiConnectHelper wifi state ENABLE", new Object[0]);
                    break;
                case 4:
                    b("HiddenWifiConnectHelper wifi state UNKNOWN", new Object[0]);
                    break;
            }
        } else {
            b("HiddenWifiConnectHelper wifi state DISABLED", new Object[0]);
        }
        return wiFiInfo;
    }

    public static void removeNetwork(final Context context, final String str, String str2, final OnWifiRemovedListener onWifiRemovedListener) {
        b("HiddenWifiConnectHelper removeNetwork ssid=%s bssid=%s", str, str2);
        Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.mico.base.utils.-$$Lambda$HiddenWifiConnectHelper$_TCiOfJG7B0rGI-oGnTEBlWIBTo
            @Override // java.lang.Runnable
            public final void run() {
                HiddenWifiConnectHelper.a(context, onWifiRemovedListener, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context, OnWifiRemovedListener onWifiRemovedListener, String str) {
        List<WifiConfiguration> configuredNetworks = c(context).getConfiguredNetworks();
        if (ContainerUtil.isEmpty(configuredNetworks)) {
            b("HiddenWifiConnectHelper removeNetwork: the list of all networks configured is empty", new Object[0]);
            if (onWifiRemovedListener != null) {
                onWifiRemovedListener.onWifiRemoveSuccess();
                return;
            }
            return;
        }
        boolean z = true;
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (WiFiUtil.isSameSSID(str, wifiConfiguration.SSID)) {
                boolean removeNetwork = c(context).removeNetwork(wifiConfiguration.networkId);
                b("HiddenWifiConnectHelper removeNetwork removeNetworkResult=%s SSID=%s, BSSID=%s, networkId=%s ", Boolean.valueOf(removeNetwork), wifiConfiguration.SSID, wifiConfiguration.BSSID, Integer.valueOf(wifiConfiguration.networkId));
                z &= removeNetwork;
            }
        }
        if (onWifiRemovedListener != null) {
            if (z) {
                b("HiddenWifiConnectHelper removeNetwork SSID=%s success!", str);
                onWifiRemovedListener.onWifiRemoveSuccess();
                return;
            }
            c("HiddenWifiConnectHelper removeNetwork SSID=%s failed!", str);
            onWifiRemovedListener.onWifiRemoveFailed();
        }
    }

    @NotNull
    private static String a(String str) {
        return "\"" + str + "\"";
    }

    private static WifiManager c(Context context) {
        return (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
    }

    private synchronized void b() {
        if (this.b) {
            this.b = false;
            ((ConnectivityManager) this.c.getSystemService(ConnectivityManager.class)).unregisterNetworkCallback(this.p);
            this.m.removeCallbacksAndMessages(null);
            this.l.quitSafely();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, Object... objArr) {
        LogCallback logCallback = UtilsConfig.getLogCallback();
        if (logCallback == null) {
            Log.i("HiddenWifiConnectHelper ", String.format(str, objArr));
        } else {
            logCallback.i(str, objArr);
        }
    }

    private static void c(String str, Object... objArr) {
        LogCallback logCallback = UtilsConfig.getLogCallback();
        if (logCallback == null) {
            Log.e("HiddenWifiConnectHelper ", String.format(str, objArr));
        } else {
            logCallback.e(str, objArr);
        }
    }

    public String getSsid() {
        return this.e;
    }

    public String getBssid() {
        return this.f;
    }

    public void destroy() {
        b();
    }

    public List<Integer> getApChannels() {
        return this.o;
    }

    public void setApChannels(List<Integer> list) {
        this.o = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a extends ConnectivityManager.NetworkCallback {
        Context a;

        public a(Context context) {
            this.a = context;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(android.net.Network network) {
            super.onAvailable(network);
            Log.w("MICO.ot", "HiddenWifiConnectHelper onAvailable Network=" + network.toString());
            HiddenWifiConnectHelper.this.a(this.a);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(android.net.Network network) {
            super.onLost(network);
            HiddenWifiConnectHelper.b("HiddenWifiConnectHelper onLost Network=" + network.toString(), new Object[0]);
            HiddenWifiConnectHelper.this.a();
        }
    }
}
