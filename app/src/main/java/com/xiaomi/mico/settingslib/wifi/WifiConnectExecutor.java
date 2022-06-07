package com.xiaomi.mico.settingslib.wifi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.elvishew.xlog.Logger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mico.settingslib.core.Constants;
import com.xiaomi.mico.settingslib.utils.SystemServices;
import com.xiaomi.mico.settingslib.utils.WeakHandler;
import com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor;
import com.xiaomi.mico.utils.Threads;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class WifiConnectExecutor {
    public static final String DEFAULT_HIDDEN_SSID = "25c829b1922d3123_miwifi";
    private static final long a = TimeUnit.SECONDS.toMillis(1);
    private static final long b = TimeUnit.SECONDS.toMillis(30);
    private volatile WiFiConnectionListener e;
    private Context f;
    private WifiManager g;
    private a h;
    private boolean i;
    private volatile int j;
    private WifiInfo k;
    private volatile boolean m;
    private volatile boolean n;
    private volatile boolean o;
    private boolean p;
    private boolean q;
    private volatile String r;
    private HashSet<Integer> s;
    private Logger v;
    private Disposable w;
    private String x;
    private String y;
    private String z;
    private long c = b;
    private volatile int l = -1;
    private final HashMap<String, ScanResult> t = new HashMap<>();
    private boolean A = false;
    private boolean B = false;
    private int C = 0;
    private Handler.Callback D = new AnonymousClass1();
    private volatile WeakHandler d = new WeakHandler(Threads.getLightWorkHandler().getLooper(), this.D);
    private WiFiPasswordStore u = new WiFiPasswordStore();

    /* loaded from: classes3.dex */
    public interface OnScanResultListener {
        void onScanResult(List<ScanResult> list);
    }

    /* loaded from: classes3.dex */
    public interface WiFiConnectionListener {
        void onConnectionFail(String str, WiFiConnState wiFiConnState);

        void onConnectionStart(String str);

        void onConnectionSuccess(String str);

        void onStateChange(SupplicantState supplicantState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public interface b {
        void onNetworkAdded(int i);
    }

    static /* synthetic */ int c(WifiConnectExecutor wifiConnectExecutor) {
        int i = wifiConnectExecutor.C;
        wifiConnectExecutor.C = i + 1;
        return i;
    }

    public String getCurSsid() {
        return this.x;
    }

    public String getCurPassword() {
        return this.y;
    }

    public String getCurBssid() {
        return this.z;
    }

    public boolean isConnecting() {
        return this.m;
    }

    public void setRemoveExistedNetworkBeforeConnect(boolean z) {
        this.A = z;
    }

    public void setPingTimeAfterConnected(boolean z) {
        this.B = z;
    }

    public void setRetryNext(boolean z) {
        this.p = z;
    }

    public void setCallFailedBeforeTryNext(boolean z) {
        this.q = z;
    }

    public WifiConnectExecutor(Context context, WiFiConnectionListener wiFiConnectionListener, Logger logger) {
        this.f = context.getApplicationContext();
        this.e = wiFiConnectionListener;
        this.v = logger;
        this.g = (WifiManager) SystemServices.WIFI_MANAGER.get(this.f);
    }

    public void setManuallyConnWifi(boolean z) {
        this.i = z;
    }

    public int getWifiState() {
        return this.g.getWifiState();
    }

    public boolean isWifiEnabled() {
        return this.g.isWifiEnabled();
    }

    public boolean setWifiEnabled(boolean z) {
        this.v.i("WifiConnectExecutor setWifiEnabled=%b", Boolean.valueOf(z));
        return this.g.setWifiEnabled(z);
    }

    public boolean startScan() {
        WifiManager wifiManager = this.g;
        if (wifiManager != null) {
            return wifiManager.startScan();
        }
        this.v.e("WifiConnectExecutor startScan failed wifiManager is null");
        return false;
    }

    public void cleanScanList() {
        synchronized (this.t) {
            this.v.i("WifiConnectExecutor clear last wifi list with %s", Integer.valueOf(this.t.size()));
            this.t.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(getScanWiFiResult());
    }

    @SuppressLint({"CheckResult"})
    public void getScanWifiResultAsync(final OnScanResultListener onScanResultListener) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$tXazpXL3oudRisNulqJ3tH5vVBI
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                WifiConnectExecutor.this.a(observableEmitter);
            }
        }).subscribeOn(Schedulers.single()).observeOn(Schedulers.single()).subscribe(new Consumer() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$CeARMNkV1g5iKGUOJ_v_8ntyZRY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WifiConnectExecutor.OnScanResultListener.this.onScanResult((List) obj);
            }
        });
    }

    public List<ScanResult> getScanWiFiResult() {
        return getScanWiFiResult(true);
    }

    public List<ScanResult> getScanWiFiResult(boolean z) {
        ArrayList arrayList;
        WifiManager wifiManager = this.g;
        if (wifiManager == null) {
            return null;
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        synchronized (this.t) {
            if (scanResults != null) {
                if (!scanResults.isEmpty()) {
                    this.v.i("WifiConnectExecutor found ScanResult count=%d", Integer.valueOf(scanResults.size()));
                    for (ScanResult scanResult : scanResults) {
                        if (!a(scanResult) && !TextUtils.isEmpty(scanResult.SSID)) {
                            this.t.put(WiFiUtil.stripSSID(scanResult.SSID), scanResult);
                        }
                    }
                }
            }
            arrayList = new ArrayList(this.t.values());
        }
        return arrayList;
    }

    public ScanResult loadScanResult(String str) {
        synchronized (this.t) {
            if (this.t.isEmpty()) {
                return null;
            }
            return this.t.get(WiFiUtil.stripSSID(str));
        }
    }

    public void setConnectMaxTime(long j) {
        this.c = j;
    }

    @Nullable
    public List<WifiConfiguration> getConfiguredNetworks() {
        WifiManager wifiManager = this.g;
        if (wifiManager != null) {
            return wifiManager.getConfiguredNetworks();
        }
        return null;
    }

    private boolean a(ScanResult scanResult) {
        synchronized (this.t) {
            if (this.t.isEmpty()) {
                return false;
            }
            return this.t.containsKey(WiFiUtil.stripSSID(scanResult.SSID));
        }
    }

    private boolean a(String str) {
        synchronized (this.t) {
            if (this.t.isEmpty()) {
                return false;
            }
            return this.t.containsKey(WiFiUtil.stripSSID(str));
        }
    }

    public void connect(String str, String str2, String str3, boolean z) {
        connect(str, str2, str3, null, z, true);
    }

    public void connect(final String str, String str2, String str3, String str4, final boolean z, final boolean z2) {
        this.v.i("WifiConnectExecutor connect ssid:%s password:%s identity:%s bssid:%s", str, str2, str3, str4);
        String ssid = WiFiUtil.getSSID(this.f);
        if (ssid == null || !ssid.equals(str)) {
            a(str, str2, str3, str4, new b() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$SIN04ymfzuPzsvOvUdiNULtmRwM
                @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.b
                public final void onNetworkAdded(int i) {
                    WifiConnectExecutor.this.a(str, z, z2, i);
                }
            });
            return;
        }
        this.v.i("WifiConnectExecutor ssid:%s is connected, ignore", ssid);
        this.m = true;
        this.r = str;
        if (!this.B) {
            d();
        } else if (Threads.isMainThread()) {
            Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$p_d5b8fqqEsbYRZUA4qee1TJONk
                @Override // java.lang.Runnable
                public final void run() {
                    WifiConnectExecutor.this.i();
                }
            });
        } else {
            i();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, boolean z, boolean z2, int i) {
        this.v.i("WifiConnectExecutor addNetwork result = %d", Integer.valueOf(i));
        a(i, str, z, z2);
    }

    private void a(final String str, final String str2, final String str3, final String str4, final b bVar) {
        WifiConfiguration wifiConfigurationBySsid;
        this.x = str;
        this.y = str2;
        this.z = str4;
        if (this.A && (wifiConfigurationBySsid = WiFiUtil.getWifiConfigurationBySsid(str, getConfiguredNetworks())) != null) {
            this.v.i("exist configuration = %s", wifiConfigurationBySsid);
            removeNetwork(wifiConfigurationBySsid.networkId, true, false);
        }
        this.u.putIntoMemory(str, str2);
        Disposable disposable = this.w;
        if (disposable != null) {
            disposable.dispose();
        }
        this.w = Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$nKiG7T3HzlDE1sTxax71JmT1zOo
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                WifiConnectExecutor.this.a(str, str2, str3, observableEmitter);
            }
        }).subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation()).subscribe(new Consumer() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$lteiYbRYmruZE-cWKF_nrOPWnlc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WifiConnectExecutor.this.a(str4, bVar, (WifiConfiguration) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, String str2, String str3, ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(WiFiUtil.buildWifiConfiguration(this.f, str, str2, str3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, b bVar, WifiConfiguration wifiConfiguration) throws Exception {
        if (!TextUtils.isEmpty(str)) {
            wifiConfiguration.BSSID = str;
        }
        int addNetwork = this.g.addNetwork(wifiConfiguration);
        this.v.i("buildWifiConfiguration networkId=%d, ssid=%s, bssid=%s", Integer.valueOf(addNetwork), wifiConfiguration.SSID, wifiConfiguration.BSSID);
        bVar.onNetworkAdded(addNetwork);
    }

    public void connect(int i, String str) {
        a(i, str, false, false);
    }

    public void connect(int i, String str, boolean z) {
        a(i, str, false, z);
    }

    private void a(int i, String str, boolean z, boolean z2) {
        this.v.i("WifiConnectExecutor connectNetwork networkId=%s,ssid=%s,removeFailNetworkId=%s", Integer.valueOf(i), str, Boolean.valueOf(z));
        if (i == -1) {
            a(str, WiFiConnState.UNKNOWN);
            return;
        }
        this.o = z;
        this.l = z ? i : -1;
        this.m = true;
        this.n = false;
        this.r = str;
        this.C = 0;
        c(i);
        BlackListWifiStore.remove(this.f, str);
        if (str.equals(WiFiUtil.getSSID(this.f))) {
            this.v.i("connected ssid=%s", str);
            if (!this.B) {
                d();
            } else if (Threads.isMainThread()) {
                Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$U9PwcR0dMb3a85xVhPwU5QOh5JM
                    @Override // java.lang.Runnable
                    public final void run() {
                        WifiConnectExecutor.this.h();
                    }
                });
            } else {
                i();
            }
        } else {
            this.j = i;
            WifiInfo connectionInfo = this.g.getConnectionInfo();
            if (!(connectionInfo == null || connectionInfo.getNetworkId() == -1 || !z2)) {
                this.k = connectionInfo;
                this.v.i("disable current wifi ssid=%s networkId=%s disable another wifi=%s", connectionInfo.getSSID(), Integer.valueOf(connectionInfo.getNetworkId()), Boolean.valueOf(this.g.disableNetwork(connectionInfo.getNetworkId())));
            }
            this.v.i("WifiConnectExecutor WifiManager enableNetwork operation=%b", Boolean.valueOf(this.g.enableNetwork(this.j, true)));
            a();
            this.c = b;
            this.d.sendEmptyMessageDelayed(102, this.c);
        }
    }

    public void connectUsableWiFi() {
        WifiConfiguration findOtherUsableConfig = findOtherUsableConfig();
        if (findOtherUsableConfig != null) {
            String stripSSID = WiFiUtil.stripSSID(findOtherUsableConfig.SSID);
            this.v.i("WifiConnectExecutor connectUsableWiFi config networkId=%d, ssid=%s, hidden=%s", Integer.valueOf(findOtherUsableConfig.networkId), stripSSID, Boolean.valueOf(findOtherUsableConfig.hiddenSSID));
            setRetryNext(true);
            connect(findOtherUsableConfig.networkId, stripSSID, true);
        } else if (this.e != null) {
            this.v.w("WifiConnectExecutor onConnectionFail not found config");
            if (Threads.isMainThread()) {
                this.e.onConnectionFail(null, WiFiConnState.UNKNOWN);
            } else {
                Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$fF1CF6AxZX6aaB_5EDCEWeXUrek
                    @Override // java.lang.Runnable
                    public final void run() {
                        WifiConnectExecutor.this.g();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g() {
        if (this.e != null) {
            this.e.onConnectionFail(null, WiFiConnState.UNKNOWN);
        }
    }

    private void a(int i) {
        if (this.s == null) {
            this.s = new HashSet<>();
        }
        this.s.add(Integer.valueOf(i));
    }

    public WifiConfiguration findOtherUsableConfig() {
        this.v.i("WifiConnectExecutor to find OtherUsableConfig");
        List<WifiConfiguration> configuredNetworks = getConfiguredNetworks();
        if (configuredNetworks == null || configuredNetworks.size() < 1) {
            return null;
        }
        this.v.i("WifiConnectExecutor getConfiguredNetworks configurations=%d", Integer.valueOf(configuredNetworks.size()));
        if (this.t.isEmpty()) {
            getScanWiFiResult();
        }
        for (int size = configuredNetworks.size() - 1; size >= 0; size--) {
            WifiConfiguration wifiConfiguration = configuredNetworks.get(size);
            if (!wifiConfiguration.hiddenSSID || !TextUtils.equals("25c829b1922d3123_miwifi", WiFiUtil.stripSSID(wifiConfiguration.SSID))) {
                boolean z = !b(wifiConfiguration.networkId);
                boolean z2 = wifiConfiguration.hiddenSSID || a(wifiConfiguration.SSID);
                this.v.i("WifiConnectExecutor findOtherUsableConfig ssid=%s, isHidden=%s, notConnected=%b, checkNearBy=%b", wifiConfiguration.SSID, Boolean.valueOf(wifiConfiguration.hiddenSSID), Boolean.valueOf(z), Boolean.valueOf(z2));
                if (z && z2 && wifiConfiguration.networkId != -1) {
                    return wifiConfiguration;
                }
            }
        }
        return null;
    }

    private boolean b(int i) {
        HashSet<Integer> hashSet = this.s;
        return hashSet != null && hashSet.contains(Integer.valueOf(i));
    }

    private void a() {
        if (this.h == null) {
            this.h = new a(this, null);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
            this.f.getApplicationContext().registerReceiver(this.h, intentFilter);
        }
    }

    public void recycle() {
        resetStatus();
        if (this.i) {
            this.i = false;
            setManuallySettingWifiStatus(false);
        }
        this.f = null;
        this.g = null;
        this.e = null;
        this.d = null;
        this.l = -1;
        this.o = false;
    }

    public void resetStatus() {
        b();
        c();
        HashSet<Integer> hashSet = this.s;
        if (hashSet != null) {
            hashSet.clear();
            this.s = null;
        }
        this.m = false;
    }

    private void b() {
        if (this.d != null) {
            this.d.removeMessages(102);
            this.d.removeMessages(101);
            this.d.removeCallbacksAndMessages(null);
        }
    }

    private void c() {
        if (this.h != null) {
            this.f.getApplicationContext().unregisterReceiver(this.h);
            this.h = null;
        }
    }

    private void c(final int i) {
        this.v.i("WifiConnectExecutor onConnectionStart=%s manuallyConnWifi=%s", this.r, Boolean.valueOf(this.i));
        if (this.i) {
            setManuallySettingWifiStatus(true);
        }
        if (Threads.isMainThread()) {
            a(i);
            if (this.e != null) {
                this.e.onConnectionStart(this.r);
                return;
            }
            return;
        }
        Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$Y_Uwj8ML7OYfHiF2FbqrphUCgeQ
            @Override // java.lang.Runnable
            public final void run() {
                WifiConnectExecutor.this.e(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(int i) {
        a(i);
        if (this.e != null) {
            this.e.onConnectionStart(this.r);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        this.v.i("WifiConnectExecutor onConnectionSuccess=%s manuallyConnWifi=%s", this.r, Boolean.valueOf(this.i));
        this.u.save(this.f, this.r);
        if (Threads.isMainThread()) {
            if (this.e != null && this.m) {
                this.e.onConnectionSuccess(this.r);
            }
            this.m = false;
        } else {
            Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$jWK1xacsijHF44wrvvlYwn_edbE
                @Override // java.lang.Runnable
                public final void run() {
                    WifiConnectExecutor.this.f();
                }
            });
        }
        resetStatus();
        if (this.i) {
            setManuallySettingWifiStatus(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void f() {
        if (this.e != null && this.m) {
            this.e.onConnectionSuccess(this.r);
        }
        this.m = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(WiFiConnState wiFiConnState) {
        this.v.i("WifiConnectExecutor onConnectionFail=%s retryNext=%s callFailedBeforeTryNext=%s", wiFiConnState, Boolean.valueOf(this.p), Boolean.valueOf(this.q));
        if (!this.m) {
            this.v.i("is not connecting");
            return;
        }
        b();
        if (this.o && (wiFiConnState == WiFiConnState.WRONG_PASSWORD || this.l == this.j)) {
            this.v.w("WifiConnectExecutor remove network=%s onConnectionFail", Integer.valueOf(this.j));
            removeNetwork(this.j, true, false);
            this.o = false;
        }
        if (this.p) {
            if (this.q) {
                a(this.r, wiFiConnState);
                this.q = false;
            }
            WifiInfo wifiInfo = this.k;
            if (wifiInfo != null) {
                this.v.i("WifiConnectExecutor connect previous wifi=%s", wifiInfo.getSSID());
                connect(this.k.getNetworkId(), this.k.getSSID(), false);
                this.k = null;
                return;
            }
            WifiConfiguration findOtherUsableConfig = findOtherUsableConfig();
            if (findOtherUsableConfig != null) {
                String stripSSID = WiFiUtil.stripSSID(findOtherUsableConfig.SSID);
                this.v.i("WifiConnectExecutor tryConnectOther networkId=%d,ssid=%s", Integer.valueOf(findOtherUsableConfig.networkId), stripSSID);
                connect(findOtherUsableConfig.networkId, stripSSID, false);
                return;
            }
            this.v.w("WifiConnectExecutor there is no usable wifi config, connectionFailCallback!");
            a(this.r, wiFiConnState);
        } else {
            a(this.r, wiFiConnState);
        }
        resetStatus();
        this.v.i("WifiConnectExecutor onConnectionFail manuallyConnWifi=%s", Boolean.valueOf(this.i));
        if (this.i) {
            setManuallySettingWifiStatus(false);
        }
    }

    private void a(final String str, final WiFiConnState wiFiConnState) {
        this.m = false;
        if (Threads.isMainThread()) {
            if (this.e != null) {
                this.e.onConnectionFail(str, wiFiConnState);
            }
        } else if (this.e != null) {
            Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$EtjnJzjZDbRcEPOupLMwx6Ca2I8
                @Override // java.lang.Runnable
                public final void run() {
                    WifiConnectExecutor.this.b(str, wiFiConnState);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(String str, WiFiConnState wiFiConnState) {
        if (this.e != null) {
            this.e.onConnectionFail(str, wiFiConnState);
        }
    }

    public void removeNetwork(int i, boolean z, boolean z2) {
        List<WifiConfiguration> d;
        if (z && (d = d(i)) != null && d.size() > 0) {
            for (WifiConfiguration wifiConfiguration : d) {
                this.v.i("WifiConnectExecutor remove ssid %s in password store", wifiConfiguration.SSID);
                this.u.remove(this.f, wifiConfiguration.SSID);
                if (z2) {
                    BlackListWifiStore.add(this.f, wifiConfiguration.SSID);
                }
            }
        }
        this.v.i("WifiConnectExecutor removeNetwork networkId: %d", Integer.valueOf(i));
        WifiManager wifiManager = this.g;
        if (wifiManager != null) {
            wifiManager.removeNetwork(i);
        }
    }

    @Nullable
    private List<WifiConfiguration> d(int i) {
        List<WifiConfiguration> configuredNetworks = getConfiguredNetworks();
        if (configuredNetworks == null || configuredNetworks.size() < 1) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (wifiConfiguration.networkId == i) {
                arrayList.add(wifiConfiguration);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final SupplicantState supplicantState) {
        this.v.i("onStateChange state: %s", supplicantState);
        if (!Threads.isMainThread()) {
            Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$eh-vIAdfG9h4YDslI0EeiROysVE
                @Override // java.lang.Runnable
                public final void run() {
                    WifiConnectExecutor.this.b(supplicantState);
                }
            });
        } else if (this.e != null) {
            this.e.onStateChange(supplicantState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(SupplicantState supplicantState) {
        if (this.e != null) {
            this.e.onStateChange(supplicantState);
        }
    }

    public void setManuallySettingWifiStatus(boolean z) {
        this.v.d("WifiConnectExecutor send broadcast isManually set wifi=%s", Boolean.valueOf(z));
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_MANUALLY_SETTING_WIFI);
        intent.putExtra("status", z);
        Context context = this.f;
        if (context != null) {
            context.sendBroadcast(intent);
        }
    }

    public void setManuallyDeleteWifi(boolean z) {
        this.v.d("WifiConnectExecutor send broadcast isManually delete wifi isConnectedBeforeDelete=%s", Boolean.valueOf(z));
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_MANUALLY_DELETE_WIFI);
        intent.putExtra(Constants.EXTRA_MANUALLY_DELETE_WIFI_CONNECTED, z);
        Context context = this.f;
        if (context != null) {
            context.sendBroadcast(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    /* renamed from: e */
    public void i() {
        this.B = false;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://api2.mina.mi.com/ping/time").openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();
            if (200 == httpURLConnection.getResponseCode()) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        stringBuffer.append(readLine);
                    } else {
                        String stringBuffer2 = stringBuffer.toString();
                        this.v.i("WifiConnectExecutor connected pingRemoteServer responseStr=%s", stringBuffer2);
                        long asLong = ((JsonObject) JsonParser.parseString(stringBuffer2)).get("data").getAsJsonObject().get(ai.aF).getAsLong();
                        this.v.i("WifiConnectExecutor network is good, syncTime success serverTime=%d", Long.valueOf(asLong));
                        SystemClock.setCurrentTimeMillis(asLong);
                        d();
                        bufferedReader.close();
                        httpURLConnection.disconnect();
                        return;
                    }
                }
            } else if (this.d != null) {
                this.d.sendEmptyMessageDelayed(101, a);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (this.d != null) {
                this.d.sendEmptyMessageDelayed(101, a);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 implements Handler.Callback {
        AnonymousClass1() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 101:
                    WifiConnectExecutor.c(WifiConnectExecutor.this);
                    if (WifiConnectExecutor.this.C <= 5) {
                        Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$1$b92y3yhTlakl0EtW2QB2Nm7_sdw
                            @Override // java.lang.Runnable
                            public final void run() {
                                WifiConnectExecutor.AnonymousClass1.this.a();
                            }
                        });
                        break;
                    } else {
                        WifiConnectExecutor.this.a(WiFiConnState.NETWORK_NOT_AVAILABLE);
                        break;
                    }
                case 102:
                    final String str = WifiConnectExecutor.this.r;
                    if (WifiConnectExecutor.this.e != null) {
                        WifiConnectExecutor.this.getScanWifiResultAsync(new OnScanResultListener() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$1$RuXxt5X2epTBtxeDu7MGxftalCU
                            @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.OnScanResultListener
                            public final void onScanResult(List list) {
                                WifiConnectExecutor.AnonymousClass1.this.a(str, list);
                            }
                        });
                        break;
                    } else {
                        return false;
                    }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(String str, List list) {
            if (list != null) {
                boolean z = true;
                if (list.size() >= 1) {
                    if (!WiFiUtil.isWifiConnected(WifiConnectExecutor.this.f) || !WiFiUtil.isSameSSID(str, WiFiUtil.getSSID(WifiConnectExecutor.this.f))) {
                        Iterator it = list.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z = false;
                                break;
                            }
                            ScanResult scanResult = (ScanResult) it.next();
                            if (WiFiUtil.isSameSSID(scanResult.SSID, str)) {
                                WifiConnectExecutor.this.v.i("To find SSID %s in scan result when connect timeout", str);
                                if (scanResult.level <= -70) {
                                    WifiConnectExecutor.this.a(WiFiConnState.WIFI_SIGNAL_WEAK);
                                } else {
                                    WifiConnectExecutor.this.a(WiFiConnState.TIMEOUT);
                                }
                            }
                        }
                        if (!z) {
                            WifiConnectExecutor.this.v.w("can not find targetSSID in scan result when connect timeout");
                            WifiConnectExecutor.this.a(WiFiConnState.WRONG_SSID);
                            return;
                        }
                        return;
                    } else if (WifiConnectExecutor.this.B) {
                        WifiConnectExecutor.this.i();
                        return;
                    } else {
                        WifiConnectExecutor.this.d();
                        return;
                    }
                }
            }
            WifiConnectExecutor.this.a(WiFiConnState.TIMEOUT);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a() {
            WifiConnectExecutor.this.i();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a extends BroadcastReceiver {
        private a() {
        }

        /* synthetic */ a(WifiConnectExecutor wifiConnectExecutor, AnonymousClass1 r2) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.net.wifi.supplicant.STATE_CHANGE".equals(intent.getAction())) {
                SupplicantState supplicantState = (SupplicantState) intent.getParcelableExtra("newState");
                WifiConnectExecutor.this.a(supplicantState);
                if (supplicantState == null) {
                    WifiConnectExecutor.this.v.e("state is null");
                    return;
                }
                switch (AnonymousClass2.a[supplicantState.ordinal()]) {
                    case 1:
                        String ssid = WiFiUtil.getSSID(context);
                        WifiConnectExecutor.this.v.i("InnerReceiver connected wifi ssid=%s", ssid);
                        if (!TextUtils.isEmpty(WifiConnectExecutor.this.r) && WiFiUtil.isSameSSID(WifiConnectExecutor.this.r, ssid)) {
                            WifiConnectExecutor.this.n = false;
                            if (WifiConnectExecutor.this.B) {
                                Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WifiConnectExecutor$a$0R7Qasq5Qqa25jnhAqarLziY4AU
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        WifiConnectExecutor.a.this.a();
                                    }
                                });
                                return;
                            } else {
                                WifiConnectExecutor.this.d();
                                return;
                            }
                        } else {
                            return;
                        }
                    case 2:
                        if (WifiConnectExecutor.this.n) {
                            int intExtra = intent.getIntExtra("supplicantError", -1);
                            WifiConnectExecutor.this.v.e("InnerReceiver connect wifi failed with error=%d, reason=%d, wifi_psw=%s", Integer.valueOf(intExtra), Integer.valueOf(intent.getIntExtra("supplicantErrorReason", -1)), WifiConnectExecutor.this.y);
                            if (intExtra == 2 || intExtra == 1) {
                                WifiConnectExecutor.this.a(WiFiConnState.WRONG_PASSWORD);
                                return;
                            }
                            return;
                        }
                        return;
                    case 3:
                        WifiConnectExecutor.this.v.i("InnerReceiver authenticating...");
                        return;
                    case 4:
                    case 5:
                        WifiConnectExecutor.this.n = true;
                        return;
                    default:
                        return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a() {
            WifiConnectExecutor.this.i();
        }
    }

    /* renamed from: com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor$2  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[SupplicantState.values().length];

        static {
            try {
                a[SupplicantState.COMPLETED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[SupplicantState.DISCONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[SupplicantState.AUTHENTICATING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[SupplicantState.SCANNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[SupplicantState.ASSOCIATING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }
}
