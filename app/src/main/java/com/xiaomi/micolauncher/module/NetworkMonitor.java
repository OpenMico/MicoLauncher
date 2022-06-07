package com.xiaomi.micolauncher.module;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.miui.privacypolicy.PrivacyManager;
import com.xiaomi.mico.base.utils.HiddenWifiScanHelper;
import com.xiaomi.mico.base.utils.NetworkUtil;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.settingslib.core.Constants;
import com.xiaomi.mico.settingslib.wifi.BlackListWifiStore;
import com.xiaomi.mico.settingslib.wifi.WiFiConnState;
import com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.ApiError;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.crash.DumpWifiHelper;
import com.xiaomi.micolauncher.common.event.StatusBarEvent;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.NetworkMonitor;
import com.xiaomi.micolauncher.module.initialize.WiFiPasswordStore;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.micolauncher.module.miot.OtInvokers;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.smarthome.library.common.network.Network;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class NetworkMonitor {
    private BroadcastReceiver d;
    private volatile boolean e;
    private volatile boolean f;
    private Context j;
    private volatile boolean k;
    private NetworkRequest q;
    private f r;
    private WifiConnectExecutor s;
    private MqttMonitor t;
    @SuppressLint({"StaticFieldLeak"})
    private static final NetworkMonitor a = new NetworkMonitor();
    private static final long b = TimeUnit.SECONDS.toMillis(5);
    private static final long c = TimeUnit.SECONDS.toMillis(5);
    public static final long CONNECT_MAX_TIME_MILLIS = TimeUnit.SECONDS.toMillis(15);
    private volatile boolean g = false;
    private volatile long h = 0;
    private volatile long i = 0;
    private volatile boolean l = false;
    private final e m = new e();
    private final c n = new c();
    private a o = null;
    private WifiInfo p = new WifiInfo();

    public static NetworkMonitor getInstance() {
        return a;
    }

    public void setOTInitDone(boolean z) {
        this.e = z;
    }

    private NetworkMonitor() {
    }

    public void init(final Context context) {
        this.j = context;
        this.t = new MqttMonitor(context);
        this.d = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.module.NetworkMonitor.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                ConnectivityManager connectivityManager;
                if ("com.xiaomi.mico.mqtt.action.CONNECTION_STATUS".equals(intent.getAction())) {
                    boolean booleanExtra = intent.getBooleanExtra("status", true);
                    L.wlan.i("on mqtt connection action, connected ? %B", Boolean.valueOf(booleanExtra));
                    if (!booleanExtra) {
                        NetworkMonitor.this.t.killMqtt();
                    }
                } else if (Constants.ACTION_MANUALLY_SETTING_WIFI.equals(intent.getAction())) {
                    boolean booleanExtra2 = intent.getBooleanExtra("status", false);
                    L.wlan.i("on mico.settings.manually.set.wifi action, isManuallySetWifi=%B", Boolean.valueOf(booleanExtra2));
                    NetworkMonitor.this.setManuallySettingWifi(booleanExtra2);
                } else if (Constants.ACTION_MANUALLY_DELETE_WIFI.equals(intent.getAction())) {
                    NetworkMonitor.this.f = intent.getBooleanExtra(Constants.EXTRA_MANUALLY_DELETE_WIFI_CONNECTED, false);
                } else if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("wifi_state", 0);
                    Log.d("MICO.wlan", "on wifi state change action, wifiState=" + intExtra);
                    if (intExtra == 3 && (connectivityManager = (ConnectivityManager) context2.getSystemService(ConnectivityManager.class)) != null && NetworkMonitor.this.q != null && NetworkMonitor.this.r != null) {
                        L.wlan.w("Wifi state is turn on, request network in timeout=30s");
                        connectivityManager.unregisterNetworkCallback(NetworkMonitor.this.r);
                        connectivityManager.requestNetwork(NetworkMonitor.this.q, NetworkMonitor.this.r, Threads.getLightWorkHandler(), 30000);
                    }
                }
            }
        };
        this.q = new NetworkRequest.Builder().build();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        if (connectivityManager != null) {
            this.r = new f(context);
            connectivityManager.requestNetwork(this.q, this.r, Threads.getLightWorkHandler(), 30000);
            b(context);
            Threads.getHeavyWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$NetworkMonitor$QlPLyKVHtzaRxplDeyYWFTBiu8c
                @Override // java.lang.Runnable
                public final void run() {
                    NetworkMonitor.c(context);
                }
            });
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.xiaomi.mico.mqtt.action.CONNECTION_STATUS");
            intentFilter.addAction(Constants.ACTION_MANUALLY_SETTING_WIFI);
            intentFilter.addAction(Constants.ACTION_MANUALLY_DELETE_WIFI);
            intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            context.registerReceiver(this.d, intentFilter, null, Threads.getLightWorkHandler());
            this.t.startMqtt(true);
        }
    }

    public static /* synthetic */ void c(Context context) {
        OtInvokers.getInstance().removeHiddenNetworkIfExpired(context);
    }

    public long c() {
        return SystemClock.elapsedRealtime();
    }

    public void a(Context context, boolean z) {
        DebugHelper.printStackTrace("NetworkMonitor:", L.base);
        boolean a2 = this.p.a(context);
        L.wlan.w("NetworkMonitor:process network lost,  is hiddenSsid=%s", Boolean.valueOf(a2));
        this.k = false;
        this.l = true;
        this.m.b(false);
        g().removeCallbacks(this.m);
        if (z) {
            g().postDelayed(this.m, a2 ? OtInvokers.getTimeoutForWaitOtEvent() : b);
        } else {
            g().post(this.m);
        }
        if (!a2) {
            this.p.c(context);
        }
        MiotDeviceManager.getInstance().setWifi("");
    }

    public void a(Context context) {
        this.p.updateOnConnected(context);
        String ssid = this.p.getSsid();
        boolean z = !WifiInfo.a(ssid);
        L.wlan.i("NetworkMonitor:onNetworkAvailable Got ssid=%s isNormalWifiConnected=%s", ssid, Boolean.valueOf(z));
        this.k = z;
        this.l = true;
        if (z) {
            MiotDeviceManager.getInstance().setWifi(ssid);
            String str = this.p.b;
            L.ot.d("NetworkMonitor:onNetworkAvailable sendWifiConnectedEvent ssid=%s bssid=%s", ssid, str);
            if (this.e) {
                OtInvokers.getInstance().sendWifiConnectivityEvent(context, ssid, str, true, null);
            }
        } else {
            L.ot.w("NetworkMonitor:onNetworkAvailable hidden ssid connected ..");
            OtInvokers.getInstance().removeHiddenNetworkIfExpired(context);
        }
        if (SystemSetting.isInitialized(context)) {
            this.m.b(z);
            this.m.a(z);
        }
    }

    private void b(final Context context) {
        L.ot.d("NetworkMonitor: OT setOnStartListener");
        OtInvokers.getInstance().setOnStartListener(new MiotDeviceManager.OnStartDeviceListener() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$NetworkMonitor$BJJvrj0xvBN5zc1ZN-Dvl60iyow
            @Override // com.xiaomi.micolauncher.module.miot.MiotDeviceManager.OnStartDeviceListener
            public final void onStartDevice(boolean z) {
                NetworkMonitor.this.b(context, z);
            }
        });
    }

    public /* synthetic */ void b(Context context, boolean z) {
        boolean isInitialized = SystemSetting.isInitialized(context);
        if (z && isInitialized) {
            if (!isWifiConnected(true)) {
                L.ot.d("NetworkMonitor:to confirm disconnected by delayed check");
                this.n.a();
                this.p.b(context);
                return;
            }
            L.ot.d("NetworkMonitor:wifi already connected");
        }
    }

    public boolean isWifiConnected() {
        return isWifiConnected(true);
    }

    public boolean isWifiConnected(boolean z) {
        boolean d2 = d();
        L.wlan.d("isStatusSet=%b,isConnectedStatus=%b,strictly=%b,isConnectedNormalSsid=%b", Boolean.valueOf(this.l), Boolean.valueOf(this.k), Boolean.valueOf(z), Boolean.valueOf(d2));
        if (this.l) {
            if (this.k) {
                return !z || d2;
            }
            return false;
        } else if (NetworkUtil.isWifiConnected(MicoApplication.getGlobalContext())) {
            return !z || d2;
        } else {
            return false;
        }
    }

    public boolean d() {
        boolean a2 = this.p.a(this.j);
        L.wlan.d("NetworkMonitor: isConnectedNormalWifi ? ssid=%s hidden=%B", this.p.a, Boolean.valueOf(a2));
        return !a2;
    }

    public void e() {
        EventBusRegistry.getEventBus().post(new WifiConnectivityChangedEvent(false, this.g));
        EventBusRegistry.getEventBus().post(new StatusBarEvent(StatusBarEvent.StatusBarType.STATUS_BAR_TYPE_WIFI, true, this.g));
    }

    public void f() {
        EventBusRegistry.getEventBus().post(new WifiConnectivityChangedEvent(true, this.g));
        EventBusRegistry.getEventBus().post(new StatusBarEvent(StatusBarEvent.StatusBarType.STATUS_BAR_TYPE_WIFI, false, this.g));
        this.t.startMqtt();
        if (SystemSetting.isInitializedNoCheckAccount(this.j)) {
            if (TokenManager.isInited() && !TokenManager.getInstance().hasValidServiceToken()) {
                L.base.i("NetworkMonitor:serviceToken in invalid, try to refresh");
                TokenManager.getInstance().refreshServiceToken(ApiConstants.MICO_SID).subscribeOn(MicoSchedulers.io()).retry(3L).observeOn(MicoSchedulers.mainThread()).subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.module.NetworkMonitor.2
                    /* renamed from: a */
                    public void onSuccess(String str) {
                        L.base.i("NetworkMonitor:refresh token success");
                    }

                    @Override // com.xiaomi.micolauncher.api.DefaultObserver
                    public void onFail(ApiError apiError) {
                        super.onFail(apiError);
                        Logger logger = L.base;
                        logger.e("NetworkMonitor:refresh token onFail " + apiError);
                    }
                });
            }
            Log.i(MicoSupConstants.TAG_LAU, "Info: refresh device in NetworkMonitor");
            MiotDeviceManager.getInstance().refreshMIoTDevice();
        }
    }

    public void destroy(Context context) {
        BroadcastReceiver broadcastReceiver = this.d;
        if (broadcastReceiver != null) {
            context.unregisterReceiver(broadcastReceiver);
            this.d = null;
        }
        OtInvokers.getInstance().destroy();
    }

    public void mockSendReconnectFailedEvent() {
        L.base.i("NetworkMonitor:mock send reconnect failed event");
        this.n.a();
    }

    public boolean isManuallySettingWifi() {
        return this.g;
    }

    public void setManuallySettingWifi(boolean z) {
        L.wlan.i("NetworkMonitor:set manually setting wifi %s", Boolean.valueOf(z));
        this.g = z;
        if (!z) {
            if (this.o == null) {
                this.o = new a();
            }
            this.o.a(TimeUnit.SECONDS.toMillis(20L));
        }
    }

    /* loaded from: classes3.dex */
    public class e implements Runnable {
        private boolean b;

        private e() {
            NetworkMonitor.this = r1;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this) {
                a(this.b);
            }
        }

        void a(boolean z) {
            synchronized (this) {
                if (!NetworkMonitor.this.l || z == NetworkMonitor.this.k) {
                    NetworkMonitor.this.l = true;
                    NetworkMonitor.this.k = z;
                    if (SpeechManager.peekInstance() != null) {
                        SpeechManager.getInstance().onWifiInfoChanged();
                    }
                    if (z) {
                        MiotManager.onNetworkAvailable();
                        L.ot.i("NetworkMonitor:SmartNotifier on wifi connected, ConnectTime=%s", Long.valueOf(NetworkMonitor.this.c()));
                        NetworkMonitor.this.f();
                        NetworkMonitor.this.n.b();
                        return;
                    }
                    L.ot.i("NetworkMonitor:SmartNotifier on wifi disconnected, DisconnectTime=%s", Long.valueOf(NetworkMonitor.this.c()));
                    NetworkMonitor.this.e();
                    NetworkMonitor.this.n.a();
                    return;
                }
                L.ot.d("NetworkMonitor:SmartNotifier ignore connected status changed event, wifi connect isConnected event %s", Boolean.valueOf(z));
            }
        }

        public void b(boolean z) {
            synchronized (this) {
                this.b = z;
            }
        }
    }

    /* loaded from: classes3.dex */
    public class c implements Runnable {
        private c() {
            NetworkMonitor.this = r1;
        }

        void a() {
            L.wlan.d("NetworkMonitor:run confirmed task : post NetworkDisconnectedConfirmTask");
            NetworkMonitor.this.g().post(this);
        }

        void b() {
            L.wlan.i("NetworkMonitor:cancel NetworkDisconnectedConfirmTask");
            NetworkMonitor.this.g().removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            c();
        }

        private void c() {
            if (!OtInvokers.getInstance().hasSetWifiStatus()) {
                L.ot.w("NetworkMonitor:OT not init done to send message, delay");
                NetworkMonitor.this.g().postDelayed(this, NetworkMonitor.c);
            } else if (!OtInvokers.getInstance().canSentReconnectedFailedEvent()) {
                L.ot.w("NetworkMonitor:cannot send reconnected failed event. recheck after 5s");
                NetworkMonitor.this.g().postDelayed(this, NetworkMonitor.c);
            } else {
                L.wlan.i("NetworkMonitor:NetworkDisconnectedConfirmTask check if manually setting wifi");
                if (NetworkMonitor.this.isManuallySettingWifi()) {
                    L.wlan.i("NetworkMonitor:user is setting wifi, do not send wifi reconnected failed event");
                    NetworkMonitor.this.g().postDelayed(this, NetworkMonitor.c);
                } else if (!NetworkMonitor.this.isWifiConnected()) {
                    L.ot.w("NetworkMonitor:network disconnected confirmed, send wifi reconnected failed event");
                    OtInvokers.getInstance().sendWifiReconnectedFailedEvent(NetworkMonitor.this.j, null);
                } else {
                    L.ot.i("NetworkMonitor:network reconnected, so do not send wifi reconnected failed event anymore");
                }
            }
        }
    }

    public Handler g() {
        return Threads.getLightWorkHandler();
    }

    public void setCanReportHiddenSsid(boolean z) {
        Logger logger = L.base;
        Object[] objArr = new Object[1];
        objArr[0] = z ? "can" : "cannot";
        logger.d("Set %s report hidden ssid", objArr);
        this.p.a(z);
    }

    public WifiInfo getWifiInfo() {
        return this.p;
    }

    @NonNull
    public static WifiSsidPassword getWifiSsidPassword(Context context, String str, Logger logger) {
        WifiSsidPassword wifiSsidPassword = new WifiSsidPassword("", "");
        if (ContainerUtil.isEmpty(str)) {
            logger.i("getWifiSsidPassword returns blank, wifi connected %B", Boolean.valueOf(getInstance().isWifiConnected()));
            return wifiSsidPassword;
        }
        WiFiPasswordStore.PwdStored loadStoredWiFiFull = WiFiPasswordStore.loadStoredWiFiFull(context, str);
        if (loadStoredWiFiFull == null || ContainerUtil.isEmpty(loadStoredWiFiFull.getIdentity())) {
            return wifiSsidPassword;
        }
        wifiSsidPassword.setSsid(str);
        wifiSsidPassword.setPassword(loadStoredWiFiFull.decryptedPassword());
        return wifiSsidPassword;
    }

    /* loaded from: classes3.dex */
    public static class WifiSsidPassword {
        private String a;
        private String b;

        WifiSsidPassword(String str, String str2) {
            setSsid(str);
            setPassword(str2);
        }

        public boolean isValid() {
            return ContainerUtil.hasData(getSsid());
        }

        public String getSsid() {
            return this.a;
        }

        public void setSsid(String str) {
            this.a = str;
        }

        public String getPassword() {
            return this.b;
        }

        public void setPassword(String str) {
            this.b = str;
        }

        public String toString() {
            return "WifiSsidPassword{ssid='" + this.a + "', password='" + this.b + "'}";
        }
    }

    /* loaded from: classes3.dex */
    public static class WifiInfo {
        private volatile String a;
        private volatile String b;
        private volatile boolean c;

        public synchronized String getSsid() {
            return this.a;
        }

        public synchronized String getBssid() {
            return this.b;
        }

        void a(boolean z) {
            this.c = z;
        }

        boolean a(Context context) {
            if (context == null) {
                context = MicoApplication.getGlobalContext();
            }
            if (TextUtils.isEmpty(this.a)) {
                this.a = WiFiUtil.getSSID(context);
                L.ot.w("set wifiInfo ssid=%s before check hiddenSsid", this.a);
            }
            if (!TextUtils.isEmpty(this.a)) {
                return a(this.a);
            }
            android.net.wifi.WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService(Network.NETWORK_TYPE_WIFI)).getConnectionInfo();
            if (connectionInfo == null) {
                L.ot.w("ssid is empty, and wifiInfo is null when check hiddenSsid!!!");
                return false;
            }
            boolean hiddenSSID = connectionInfo.getHiddenSSID();
            L.ot.w("ssid is empty, check current wifiInfo is hidden=%s", Boolean.valueOf(hiddenSSID));
            return hiddenSSID;
        }

        static boolean a(String str) {
            return WiFiUtil.isSameSSID(str, HiddenWifiScanHelper.getDefaultHiddenSsid());
        }

        void b(final Context context) {
            if (!NetworkMonitor.getInstance().isManuallySettingWifi() && !NetworkMonitor.getInstance().isWifiConnected()) {
                Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$NetworkMonitor$WifiInfo$xQsKnRNz4k-kFUHMmgXLWFQ4vSs
                    @Override // java.lang.Runnable
                    public final void run() {
                        NetworkMonitor.WifiInfo.this.d(context);
                    }
                });
            }
        }

        public /* synthetic */ void d(Context context) {
            synchronized (this) {
                if (!a()) {
                    this.a = WiFiUtil.stripSSID(b.a(context));
                    this.b = b.b(context);
                    L.ot.d("NetworkMonitor:reportIfNoNetworkAsync ssid=%s bssid=%s", this.a, this.b);
                }
                c(context);
            }
        }

        synchronized boolean a() {
            boolean z;
            if (!TextUtils.isEmpty(this.a)) {
                if (!TextUtils.isEmpty(this.b)) {
                    z = true;
                }
            }
            z = false;
            return z;
        }

        public synchronized void updateOnConnected(Context context) {
            this.a = WiFiUtil.stripSSID(WiFiUtil.getSSID(context));
            this.b = WiFiUtil.getBSSID(context);
            if (!a()) {
                L.wlan.e("ssid=%s or bssid=%s is invalid when connected", this.a, this.b);
            } else {
                b.a(context, this.a, this.b);
            }
        }

        synchronized void c(Context context) {
            if (!a()) {
                L.ot.w("NetworkMonitor:Either ssid or bssid is lost, so we cannot report disconnected event to OT.");
            } else if (!this.c && a(this.a)) {
                L.ot.i("Do not report hidden ssid for disabled");
            } else if (NetworkMonitor.getInstance().isWifiConnected(false)) {
                L.ot.i("NetworkMonitor:wifi is connected state, do not invoke auto config wifi");
            } else if (NetworkMonitor.getInstance().isManuallySettingWifi()) {
                L.ot.i("NetworkMonitor:user is setting wifi, do not invoke auto config wifi");
            } else {
                L.ot.i("NetworkMonitor:report wifi disconnected to OT ssid=%s bssid=%s", this.a, this.b);
                OtInvokers.getInstance().sendWifiDisconnectedEvent(context, this.a, this.b, null);
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class b {
        static void a(Context context, String str, String str2) {
            PreferenceUtils.setSettingString(context, "com.xiaomi.micolauncher.module.NetworkMonitor.LastSSID", str);
            PreferenceUtils.setSettingString(context, "com.xiaomi.micolauncher.module.NetworkMonitor.LastBSSID", str2);
        }

        static String a(Context context) {
            return PreferenceUtils.getSettingString(context, "com.xiaomi.micolauncher.module.NetworkMonitor.LastSSID", null);
        }

        static String b(Context context) {
            return PreferenceUtils.getSettingString(context, "com.xiaomi.micolauncher.module.NetworkMonitor.LastBSSID", null);
        }
    }

    /* loaded from: classes3.dex */
    public class d implements WifiConnectExecutor.WiFiConnectionListener {
        private WifiConnectExecutor.WiFiConnectionListener b;

        private d(WifiConnectExecutor.WiFiConnectionListener wiFiConnectionListener) {
            NetworkMonitor.this = r1;
            this.b = wiFiConnectionListener;
        }

        public /* synthetic */ void b(String str) {
            this.b.onConnectionStart(str);
        }

        @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
        public void onConnectionStart(final String str) {
            NetworkMonitor.this.g().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$NetworkMonitor$d$NUQUZ7usQEas_bgiYPs_FVjSUJE
                @Override // java.lang.Runnable
                public final void run() {
                    NetworkMonitor.d.this.b(str);
                }
            });
        }

        public /* synthetic */ void a(String str) {
            this.b.onConnectionSuccess(str);
        }

        @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
        public void onConnectionSuccess(final String str) {
            NetworkMonitor.this.g().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$NetworkMonitor$d$KeuuXAxLsKuExG8EpKncsGSFDHA
                @Override // java.lang.Runnable
                public final void run() {
                    NetworkMonitor.d.this.a(str);
                }
            });
        }

        public /* synthetic */ void a(String str, WiFiConnState wiFiConnState) {
            this.b.onConnectionFail(str, wiFiConnState);
        }

        @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
        public void onConnectionFail(final String str, final WiFiConnState wiFiConnState) {
            NetworkMonitor.this.g().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$NetworkMonitor$d$UrJtfQ3XFUFcRXhYVukEV9CFsK4
                @Override // java.lang.Runnable
                public final void run() {
                    NetworkMonitor.d.this.a(str, wiFiConnState);
                }
            });
        }

        public /* synthetic */ void a(SupplicantState supplicantState) {
            this.b.onStateChange(supplicantState);
        }

        @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
        public void onStateChange(final SupplicantState supplicantState) {
            NetworkMonitor.this.g().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$NetworkMonitor$d$m2fbnYw9sdqKucHLRDwd57Drha8
                @Override // java.lang.Runnable
                public final void run() {
                    NetworkMonitor.d.this.a(supplicantState);
                }
            });
        }
    }

    private void h() {
        if (this.s == null) {
            this.s = new WifiConnectExecutor(this.j, new d(new WifiConnectExecutor.WiFiConnectionListener() { // from class: com.xiaomi.micolauncher.module.NetworkMonitor.3
                @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                public void onStateChange(SupplicantState supplicantState) {
                }

                @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                public void onConnectionStart(String str) {
                    L.wlan.i("NetworkMonitor:WifiConnectExecutor start connect %s", str);
                }

                @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                public void onConnectionSuccess(String str) {
                    L.wlan.i("NetworkMonitor:WifiConnectExecutor connect success ssid=%s", str);
                    NetworkMonitor.this.g = false;
                    NetworkMonitor networkMonitor = NetworkMonitor.this;
                    networkMonitor.a(networkMonitor.j);
                }

                @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                public void onConnectionFail(String str, WiFiConnState wiFiConnState) {
                    if (DebugHelper.isDebugVersion()) {
                        DumpWifiHelper.getInstance().dumpOnce();
                    }
                    NetworkMonitor.this.g = false;
                    boolean isConnecting = NetworkMonitor.this.s.isConnecting();
                    L.wlan.i("NetworkMonitor:WifiConnectExecutor connection failed ssid=%s, reason=%s, connecting=%B", str, wiFiConnState, Boolean.valueOf(isConnecting));
                    if (wiFiConnState == WiFiConnState.WRONG_SSID && WiFiUtil.isWifiConnected(NetworkMonitor.this.j) && NetworkMonitor.this.d()) {
                        if (NetworkMonitor.this.l && !NetworkMonitor.this.k) {
                            NetworkMonitor.this.k = true;
                            L.wlan.w("NetworkMonitor:inconsistency detected and fixed");
                        }
                        L.wlan.i("NetworkMonitor:already connected before the async trying");
                    } else if (!isConnecting) {
                        NetworkMonitor networkMonitor = NetworkMonitor.this;
                        networkMonitor.a(networkMonitor.j, false);
                    }
                }
            }), L.wlan);
            this.s.setConnectMaxTime(CONNECT_MAX_TIME_MILLIS);
            this.s.setRemoveExistedNetworkBeforeConnect(false);
        }
    }

    synchronized void a() {
        if (!((WifiManager) this.j.getSystemService(Network.NETWORK_TYPE_WIFI)).isWifiEnabled()) {
            L.wlan.i("NetworkMonitor:processNetworkLostSmart wifi is disable, do not go on");
        } else if (isManuallySettingWifi()) {
            L.wlan.i("NetworkMonitor:is manually setting wifi, do not go on");
        } else {
            if (this.h == 0 || this.i > this.h) {
                this.h = c();
                L.wlan.d("NetworkMonitor:a new begin of reconnect or sync-wifi-password");
            }
            WifiSsidPassword wifiSsidPassword = getWifiSsidPassword(this.j, this.p.getSsid(), L.wlan);
            boolean z = wifiSsidPassword.isValid() && !this.p.a(this.j);
            L.wlan.i("NetworkMonitor:network lost!!!, wifiInfoSsid=%s, ssid=%s, password=%s, will try connect=%B, TimeMillis=%s", this.p.getSsid(), wifiSsidPassword.getSsid(), wifiSsidPassword.getPassword(), Boolean.valueOf(z), Long.valueOf(c()));
            if (!z && !this.f) {
                this.f = false;
                a(this.j, true);
            } else if (BlackListWifiStore.contains(this.j, wifiSsidPassword.getSsid())) {
                this.f = false;
                a(this.j, true);
            } else if (WiFiUtil.isSecurityOpenWifi(this.j, wifiSsidPassword.getSsid(), wifiSsidPassword.getPassword(), "")) {
                this.f = false;
                L.wlan.w("Won't auto connect to open wifi %s", wifiSsidPassword.getSsid());
                a(this.j, false);
            } else if (this.s == null || !this.s.isConnecting()) {
                h();
                if (this.f) {
                    L.wlan.i("NetworkMonitor:processNetworkLostSmart try to connect to another saved wifi");
                    this.g = true;
                    this.s.connectUsableWiFi();
                    this.f = false;
                } else {
                    this.s.setRetryNext(!isManuallySettingWifi());
                    L.wlan.i("NetworkMonitor:processNetworkLostSmart try to connect to WifiSsidPassword=%s", wifiSsidPassword);
                    WifiConfiguration wifiConfigurationBySsid = WiFiUtil.getWifiConfigurationBySsid(wifiSsidPassword.a, this.s.getConfiguredNetworks());
                    if (wifiConfigurationBySsid != null) {
                        this.s.connect(wifiConfigurationBySsid.networkId, wifiSsidPassword.a);
                    } else {
                        this.s.connect(wifiSsidPassword.a, wifiSsidPassword.getPassword(), "", false);
                    }
                }
            } else {
                L.wlan.w("NetworkMonitor: is connecting, ignore");
            }
        }
    }

    /* loaded from: classes3.dex */
    public class f extends ConnectivityManager.NetworkCallback {
        private final Context b;

        f(Context context) {
            NetworkMonitor.this = r1;
            this.b = context;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(android.net.Network network) {
            int i;
            NetworkMonitor networkMonitor = NetworkMonitor.this;
            networkMonitor.i = networkMonitor.c();
            if (NetworkMonitor.this.o != null) {
                NetworkMonitor.this.o.a();
            }
            NetworkMonitor.this.a(this.b);
            if (SystemSetting.isInitialized(this.b)) {
                try {
                    i = Settings.Global.getInt(this.b.getContentResolver(), "mico_privacy_agree");
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                    L.wlan.e("Network available get privacy agree error!!!!");
                    i = 0;
                }
                L.wlan.i("NetworkMonitor:Network available id=%s, privacyAgree=%s, CostTime=%s", network, Integer.valueOf(i), Long.valueOf(NetworkMonitor.this.i - NetworkMonitor.this.h));
                if (i != 1) {
                    a(this.b);
                }
            }
        }

        @SuppressLint({"MissingPermission"})
        private void a(final Context context) {
            Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$NetworkMonitor$f$xvvxCM0WOJI49u5mrvQPxP_k1y8
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    NetworkMonitor.f.a(context, observableEmitter);
                }
            }).retry(3L).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$NetworkMonitor$f$4oLh2MFbwZ6k2Pi4q5_5hJ0M7vs
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    NetworkMonitor.f.a(context, (Integer) obj);
                }
            }, $$Lambda$NetworkMonitor$f$4oAveyWQXUZZmwjZDevkNVFRvwE.INSTANCE);
        }

        public static /* synthetic */ void a(Context context, ObservableEmitter observableEmitter) throws Exception {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                observableEmitter.onNext(Integer.valueOf(PrivacyManager.privacyAgree(context, "xiaoaispeaker", Build.getSerial(), packageInfo.packageName, packageInfo.versionName)));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                observableEmitter.onNext(-1);
            }
        }

        public static /* synthetic */ void a(Context context, Integer num) throws Exception {
            L.init.i("NetworkMonitor: onNetworkAvailable privacyAgree code=%s", num);
            if (num.intValue() == 1) {
                Settings.Global.putInt(context.getContentResolver(), "mico_privacy_agree", 1);
            }
        }

        public static /* synthetic */ void a(Throwable th) throws Exception {
            L.init.e("NetworkMonitor: onNetworkAvailable privacyAgree error", th);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLosing(android.net.Network network, int i) {
            L.wlan.i("NetworkMonitor:on losing network %s, %s millis to live", network, Integer.valueOf(i));
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(android.net.Network network) {
            L.wlan.i("NetworkMonitor:on lost networkId=%s", network);
            if (SystemSetting.isInitialized(this.b)) {
                if (DebugHelper.isDebugVersion() && !NetworkMonitor.this.isManuallySettingWifi()) {
                    DumpWifiHelper.getInstance().dumpOnce();
                }
                NetworkMonitor.this.e();
                NetworkMonitor.this.k = false;
                NetworkMonitor.this.a();
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onUnavailable() {
            L.wlan.w("NetworkMonitor:network unavailable");
            NetworkMonitor.this.e();
            NetworkMonitor.this.k = false;
            NetworkMonitor.this.a();
            ConnectivityManager connectivityManager = (ConnectivityManager) this.b.getSystemService(ConnectivityManager.class);
            if (connectivityManager != null && NetworkMonitor.this.r != null && NetworkMonitor.this.q != null) {
                L.wlan.w("NetworkMonitor:network unavailable after 30s timeout，then request network.");
                connectivityManager.unregisterNetworkCallback(NetworkMonitor.this.r);
                connectivityManager.requestNetwork(NetworkMonitor.this.q, NetworkMonitor.this.r, Threads.getLightWorkHandler());
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(android.net.Network network, NetworkCapabilities networkCapabilities) {
            Log.i("MICO.wlan", "NetworkMonitor:ssid=" + networkCapabilities.getSSID() + " capabilities changed");
            if (!NetworkMonitor.this.p.a(this.b)) {
                if (SpeechManager.peekInstance() != null) {
                    SpeechManager.getInstance().onWifiInfoChanged();
                }
                EventBusRegistry.getEventBus().post(new StatusBarEvent(StatusBarEvent.StatusBarType.STATUS_BAR_TYPE_WIFI, false));
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(android.net.Network network, LinkProperties linkProperties) {
            L.wlan.i("NetworkMonitor:%s on link properties changes", network);
        }
    }

    /* loaded from: classes3.dex */
    public class a implements Runnable {
        private a() {
            NetworkMonitor.this = r1;
        }

        void a(long j) {
            NetworkMonitor.this.g().postDelayed(this, j);
        }

        void a() {
            NetworkMonitor.this.g().removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!NetworkMonitor.this.isWifiConnected()) {
                NetworkMonitor.this.a();
            }
        }
    }
}
