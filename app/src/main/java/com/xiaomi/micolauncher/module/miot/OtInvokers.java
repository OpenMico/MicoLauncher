package com.xiaomi.micolauncher.module.miot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.gson.JsonSyntaxException;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.mico.base.utils.Base64Coder;
import com.xiaomi.mico.base.utils.HiddenWifiConnectHelper;
import com.xiaomi.mico.base.utils.HiddenWifiScanHelper;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.wifi.BlackListWifiStore;
import com.xiaomi.mico.settingslib.wifi.WiFiConnState;
import com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.LoginModel;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.utils.IRUtils;
import com.xiaomi.micolauncher.module.NetworkMonitor;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.micolauncher.module.miot.OtApi;
import com.xiaomi.micolauncher.module.miot.OtDefs;
import com.xiaomi.miotse.MiotSecureElementManager;
import com.xiaomi.onetrack.OneTrack;
import io.netty.handler.codec.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class OtInvokers {
    private static final OtInvokers a = new OtInvokers();
    private static final Logger b = L.ot;
    private static final long c = TimeUnit.MINUTES.toMillis(5);
    private static final long d = TimeUnit.SECONDS.toSeconds(10);
    private int f;
    private volatile boolean g;
    private volatile OtDefs.t h;
    @GuardedBy("helpersLock")
    private HiddenWifiScanHelper k;
    @GuardedBy("helpersLock")
    private HiddenWifiConnectHelper l;
    @GuardedBy("helpersLock")
    private d m;
    private c n;
    private b o;
    private final OtApi e = new OtApi();
    private a i = new a();
    private final Object j = new Object();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public interface e {
        void onNotFound();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class d {
        static final long a = TimeUnit.SECONDS.toMillis(10);
        WifiConnectExecutor b;
        long c;

        private d() {
            this.b = null;
        }

        void a(Context context, WifiConnectExecutor.WiFiConnectionListener wiFiConnectionListener) {
            this.c = SystemClock.elapsedRealtime();
            this.b = new WifiConnectExecutor(context, wiFiConnectionListener, OtInvokers.b);
        }

        boolean a() {
            return SystemClock.elapsedRealtime() - this.c > a;
        }
    }

    private OtInvokers() {
    }

    public static OtInvokers getInstance() {
        return a;
    }

    public void setInterruptHandleOT(boolean z) {
        this.g = z;
    }

    public void startDevice() {
        b.i("OtInvokers: start MioTDevice");
        this.e.startDevice();
    }

    public void setWifiMode(int i) {
        b.i("OtInvokers: setWifiMode =%s", Integer.valueOf(i));
        this.e.setWifiMode(i);
    }

    public boolean isBinded() {
        return this.e.isBinded();
    }

    public void setOnStartListener(MiotDeviceManager.OnStartDeviceListener onStartDeviceListener) {
        this.e.setOnStartListener(onStartDeviceListener);
    }

    public void bind(final Context context, final boolean z, final MiotDeviceManager.OnBindListener onBindListener) {
        this.e.bindService(context, new MiotDeviceManager.OnBindListener() { // from class: com.xiaomi.micolauncher.module.miot.OtInvokers.1
            @Override // com.xiaomi.micolauncher.module.miot.MiotDeviceManager.OnBindListener
            public void onBindSuccess() {
                OtInvokers.b.i("OtInvokers: bind OT service done, register methods=%s", Boolean.valueOf(z));
                if (z) {
                    OtInvokers.this.registerMethodsToReceive(context);
                }
                MiotDeviceManager.OnBindListener onBindListener2 = onBindListener;
                if (onBindListener2 != null) {
                    onBindListener2.onBindSuccess();
                }
            }

            @Override // com.xiaomi.micolauncher.module.miot.MiotDeviceManager.OnBindListener
            public void onBindFailed(int i) {
                OtInvokers.b.e("OtInvokers:onBindFailed error=%s", Integer.valueOf(i));
                MiotDeviceManager.OnBindListener onBindListener2 = onBindListener;
                if (onBindListener2 != null) {
                    onBindListener2.onBindFailed(i);
                }
            }

            @Override // com.xiaomi.micolauncher.module.miot.MiotDeviceManager.OnBindListener
            public void onStartDevice(boolean z2) {
                MiotDeviceManager.OnBindListener onBindListener2 = onBindListener;
                if (onBindListener2 != null) {
                    onBindListener2.onStartDevice(z2);
                }
            }
        });
    }

    public void unbind(Context context) {
        this.e.unbindService(context);
    }

    public void registerMethodsToReceive(final Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("_internal.req_wifi_conf_status");
        arrayList.add("_internal.wifi_scan_req");
        arrayList.add("_internal.wifi_start");
        arrayList.add(OtDefs.INTERNAL_WIFI_DISCONNECT_REQ);
        arrayList.add(OtDefs.INTERNAL_AUTH_LOGIN);
        arrayList.add(OtDefs.LOCAL_MSC_SIGN_REQ);
        arrayList.add(OtDefs.MIIO_IR_PLAY);
        arrayList.add(OtDefs.MIIO_IR_PLAY_BATCH);
        arrayList.add(OtDefs.MIIO_IR_READ);
        this.e.initialize(arrayList, new OtApi.OnRegisterListener() { // from class: com.xiaomi.micolauncher.module.miot.OtInvokers.2
            @Override // com.xiaomi.micolauncher.module.miot.OtApi.OnRegisterListener
            public void onMessage(String str) {
                OtInvokers.b.d("otApi get message:%s", str);
                try {
                    String str2 = ((OtDefs.d) Gsons.getGson().fromJson(str, (Class<Object>) OtDefs.d.class)).method;
                    OtInvokers.b.d("OtInvokers: Important : on message %s, \nmethod=%s", str, str2);
                    if (str2.startsWith(OtDefs.METHOD_PREFIX_INTERNAL)) {
                        if (OtInvokers.this.b()) {
                            OtInvokers.b.w("Ignore message [%s] for user is setting wifi", str);
                            return;
                        } else if (SystemSetting.isInitialized(context)) {
                            if (NetworkMonitor.getInstance().isWifiConnected(true)) {
                                OtInvokers.b.i("already connected, abort the sync-password process");
                                OtInvokers.this.a(context);
                                return;
                            }
                        } else if (OtInvokers.this.g) {
                            OtInvokers.b.w("Ignore message [%s] for user because interruptHandleOT=true", str);
                            return;
                        }
                    }
                    OtInvokers.this.a(context, str2, str);
                } catch (JsonSyntaxException e2) {
                    Logger logger = OtInvokers.b;
                    logger.e("OtInvokers:transparent message " + str, e2);
                }
            }

            @Override // com.xiaomi.micolauncher.module.miot.OtApi.OnRegisterListener
            public void onFailed(int i) {
                OtInvokers.b.e("OtInvokers: failed to register transparent request listener, error code %s", Integer.valueOf(i));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context) {
        synchronized (this.j) {
            if (this.m == null || this.m.b == null) {
                NetworkMonitor.WifiInfo wifiInfo = NetworkMonitor.getInstance().getWifiInfo();
                String ssid = wifiInfo.getSsid();
                String bssid = wifiInfo.getBssid();
                b.i("OtInvokers:tellConnectSuccessToOt ssid=%s bssid=%s", ssid, bssid);
                getInstance().sendWifiConnectivityEvent(context, ssid, bssid, true, null);
            } else {
                String stripSSID = WiFiUtil.stripSSID(this.m.b.getCurSsid());
                String curBssid = this.m.b.getCurBssid();
                b.i("with masterSsidConnectionWrapper, send wifi (ssid %s, bssid %s) connected event", stripSSID, curBssid);
                sendWifiConnectivityEvent(context, stripSSID, curBssid, true, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b() {
        return NetworkMonitor.getInstance().isManuallySettingWifi();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void a(Context context, String str, String str2) {
        char c2;
        int i = 3;
        switch (str.hashCode()) {
            case -1584441445:
                if (str.equals(OtDefs.INTERNAL_AUTH_LOGIN)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -1115249804:
                if (str.equals("_internal.wifi_scan_req")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -539447350:
                if (str.equals("_internal.wifi_start")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case -537592813:
                if (str.equals(OtDefs.INTERNAL_WIFI_DISCONNECT_REQ)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -413378978:
                if (str.equals(OtDefs.MIIO_IR_PLAY)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 204893014:
                if (str.equals("_internal.req_wifi_conf_status")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 228210497:
                if (str.equals(OtDefs.LOCAL_MSC_SIGN_REQ)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 1585238553:
                if (str.equals(OtDefs.MIIO_IR_PLAY_BATCH)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                NetworkMonitor.getInstance().setOTInitDone(true);
                if (SystemSetting.isInitialized(context)) {
                    i = 1;
                }
                b.w("OtInvokers:_internal.req_wifi_conf_status status=%s ", Integer.valueOf(i));
                return;
            case 1:
                if (NetworkMonitor.getInstance().isManuallySettingWifi()) {
                    b.i("OtInvokers:manually setting wifi, %s abort", str);
                    return;
                }
                if (SystemSetting.isInitialized(context)) {
                    this.i.g();
                }
                parseAndStartScan(context, str2);
                return;
            case 2:
                if (NetworkMonitor.getInstance().isManuallySettingWifi()) {
                    b.i("OtInvokers:manually setting wifi, %s abort", str);
                    return;
                }
                if (SystemSetting.isInitialized(context)) {
                    this.i.h();
                }
                d(context, str2);
                return;
            case 3:
                e(context, str2);
                return;
            case 4:
                b(context, str2);
                return;
            case 5:
                a(context, str2);
                return;
            case 6:
                OtDefs.IRData iRData = (OtDefs.IRData) Gsons.getGson().fromJson(str2, (Class<Object>) OtDefs.IRData.class);
                notifySendIRSuccessToOt(context, iRData.a);
                IRUtils.sendIR(context, iRData.params.freq, iRData.params.code, iRData.params.length, 0, false);
                return;
            case 7:
                OtDefs.IRData iRData2 = (OtDefs.IRData) Gsons.getGson().fromJson(str2, (Class<Object>) OtDefs.IRData.class);
                notifySendIRSuccessToOt(context, iRData2.a);
                IRUtils.sendIR(context, iRData2.params.freq, iRData2.params.codes, iRData2.params.length, iRData2.params.interval, true);
                return;
            default:
                b.e("OtInvokers: unexpected method %s", str);
                return;
        }
    }

    @SuppressLint({"WrongConstant"})
    private void a(Context context, String str) {
        MiotSecureElementManager miotSecureElementManager;
        OtDefs.e eVar = (OtDefs.e) Gsons.getGson().fromJson(str, (Class<Object>) OtDefs.e.class);
        if (eVar != null && eVar.a != null && TextUtils.equals(eVar.a.a, HttpHeaders.Values.BASE64)) {
            String str2 = eVar.a.b;
            if (!TextUtils.isEmpty(str2) && (miotSecureElementManager = (MiotSecureElementManager) context.getSystemService(MiotDeviceManager.SE_ELEMENT_SERVICE)) != null) {
                String encode = Base64Coder.encode(miotSecureElementManager.getSignature(Base64Coder.decode(str2)));
                OtDefs.g gVar = new OtDefs.g();
                gVar.a = new OtDefs.h(HttpHeaders.Values.BASE64, encode);
                a(context, gVar, new OtApi.OnSendTransparentMessageListener() { // from class: com.xiaomi.micolauncher.module.miot.OtInvokers.3
                    @Override // com.xiaomi.micolauncher.module.miot.OtApi.OnSendTransparentMessageListener
                    public void onSucceed(String str3) {
                        OtInvokers.b.i("OtInvokers: mIoTSeSign onSucceed result=%s", str3);
                    }

                    @Override // com.xiaomi.micolauncher.module.miot.OtApi.OnSendTransparentMessageListener
                    public void onFailed(int i) {
                        OtInvokers.b.e("OtInvokers: mIoTSeSign onFailed error=%s", Integer.valueOf(i));
                    }
                });
            }
        }
    }

    public void sendWifiConfStatus(Context context, int i) {
        OtDefs.WifiConfStatus wifiConfStatus = new OtDefs.WifiConfStatus();
        wifiConfStatus.params = i;
        a(context, wifiConfStatus, (OtApi.OnSendTransparentMessageListener) null);
    }

    public void notifySendIRSuccessToOt(Context context, int i) {
        OtDefs.IRStatus iRStatus = new OtDefs.IRStatus();
        iRStatus.id = i;
        a(context, iRStatus, (OtApi.OnSendTransparentMessageListener) null);
    }

    private void b(final Context context, String str) {
        OtDefs.a aVar = (OtDefs.a) Gsons.getGson().fromJson(str, (Class<Object>) OtDefs.a.class);
        if (aVar != null) {
            this.f = aVar.a;
            String str2 = aVar.b.a;
            if (!TextUtils.isEmpty(str2)) {
                c(context, str2);
                ThreadUtil.getLightWorkHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$OtInvokers$sDs6qzdFQSLMZEkzZdZqGtVyGiM
                    @Override // java.lang.Runnable
                    public final void run() {
                        OtInvokers.this.b(context);
                    }
                }, 200L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Context context) {
        a(context, (OtApi.OnSendTransparentMessageListener) null);
    }

    private void c(Context context, String str) {
        Intent intent = new Intent("com.xiaomi.micolauncher.ot.init");
        intent.putExtra("step", "auth_login");
        intent.putExtra("url", str);
        context.sendBroadcast(intent);
    }

    private void a(Context context, OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener) {
        OtDefs.c cVar = new OtDefs.c();
        cVar.b = 0;
        cVar.a = this.f;
        a(context, cVar, onSendTransparentMessageListener);
    }

    public void startConnectHiddenWifi(final Context context, final String str, final String str2) {
        synchronized (this.j) {
            if (this.l != null && this.l.isConnecting()) {
                b.w("already being connecting to connected to ssid=%s bssid=%s", this.l.getSsid(), this.l.getBssid());
            }
            final OtDefs.s sVar = new OtDefs.s();
            sVar.a = new OtDefs.t();
            sVar.a.b = str;
            sVar.a.d = str2;
            if (this.l == null) {
                this.l = new HiddenWifiConnectHelper();
            }
            this.l.connect(context, str, str2, new HiddenWifiConnectHelper.OnConnectResultListener() { // from class: com.xiaomi.micolauncher.module.miot.OtInvokers.4
                @Override // com.xiaomi.mico.base.utils.HiddenWifiConnectHelper.OnConnectResultListener
                public void onConnected(WifiInfo wifiInfo) {
                    OtInvokers.b.d("OtInvokers:connected to wifiInfo toString=%s", wifiInfo.toString());
                    String str3 = str;
                    if (str3 == null || !WiFiUtil.isSameSSID(str3.toLowerCase(), wifiInfo.getSSID().toLowerCase()) || !TextUtils.equals(str2.toLowerCase(), wifiInfo.getBSSID().toLowerCase())) {
                        OtInvokers.b.w("OtInvokers:Unexpected occasion, what gets connected(%s) is not given, failed to connect to %s %s", wifiInfo, str, str2);
                        sVar.a = new OtDefs.t();
                        sVar.a.b = WiFiUtil.stripSSID(wifiInfo.getSSID());
                        sVar.a.d = wifiInfo.getBSSID();
                        OtInvokers.this.a(context, sVar.a, true);
                        return;
                    }
                    OtInvokers.b.i("OtInvokers:connected to %s %s successfully", str, str2);
                    if (!TextUtils.equals(str, sVar.a.b) || !TextUtils.equals(str2, sVar.a.d)) {
                        sVar.a = new OtDefs.t();
                        sVar.a.b = str;
                        sVar.a.d = str2;
                    }
                    OtInvokers.this.a(context, sVar.a, true);
                    synchronized (OtInvokers.this.j) {
                        OtInvokers.this.n = new c(WiFiUtil.stripSSID(wifiInfo.getSSID()), wifiInfo.getBSSID(), wifiInfo.getNetworkId());
                    }
                }

                @Override // com.xiaomi.mico.base.utils.HiddenWifiConnectHelper.OnConnectResultListener
                public void onFailed(String str3, String str4) {
                    OtInvokers.b.e("OtInvokers:failed to connect to ssid=%s bssid=%s", str3, str4);
                    if (!TextUtils.equals(str3, sVar.a.b) || !TextUtils.equals(str4, sVar.a.d)) {
                        sVar.a = new OtDefs.t();
                        sVar.a.b = str3;
                        sVar.a.d = str4;
                    }
                    OtInvokers.this.a(context, sVar.a, false);
                }

                @Override // com.xiaomi.mico.base.utils.HiddenWifiConnectHelper.OnConnectResultListener
                public void onDisConnected() {
                    OtInvokers.b.e("OtInvokers:onDisConnected ssid=%s bssid=%s", str, str2);
                    OtInvokers.this.sendWifiDisconnectedEvent(context, str, str2, null);
                }
            });
        }
    }

    private void d(Context context, String str) {
        b bVar = this.o;
        if (bVar != null) {
            bVar.a();
        }
        OtDefs.s sVar = (OtDefs.s) Gsons.getGson().fromJson(str, (Class<Object>) OtDefs.s.class);
        if (!TextUtils.equals(sVar.method, "_internal.wifi_start")) {
            b.w("OtInvokers:Unexpected method %s, expected %s", sVar.method, "_internal.wifi_start");
        }
        if (sVar.a == null || (ContainerUtil.isEmpty(sVar.a.b) && ContainerUtil.isEmpty(sVar.a.e))) {
            b.e("OtInvokers:wifi start params is null");
            return;
        }
        boolean z = sVar.a.a == 0;
        boolean isSameSSID = WiFiUtil.isSameSSID(sVar.a.b, HiddenWifiScanHelper.getDefaultHiddenSsid());
        if (z || !isSameSSID) {
            b.i("OtInvokers:start connect wifi isHiddenSsidMode=%s，params=%s", Boolean.valueOf(z), sVar.a);
            if (this.h == null) {
                this.h = sVar.a;
            } else if (!TextUtils.equals(this.h.toString(), sVar.a.toString())) {
                this.h = sVar.a;
            } else {
                b.w("OtInvokers:startConnectWifi otSdk emit same params, and device may be connecting to wifi, so ignore it !!!");
                return;
            }
            if (!z || !isSameSSID) {
                a(context, sVar);
                return;
            }
            NetworkMonitor.getInstance().setCanReportHiddenSsid(true);
            startConnectHiddenWifi(context, sVar.a.b, sVar.a.d);
            return;
        }
        b.e("OT error, hidden ssid is regarded as master ssid, ignore this action");
    }

    private void a(final Context context, final OtDefs.s sVar) {
        b.i("connectNormalSsid before lock");
        synchronized (this.j) {
            b.i("connectNormalSsid in lock");
            c cVar = this.n;
            if (cVar != null) {
                b.d("disable hidden network before connectNormalSsid");
                if (this.l != null) {
                    this.l.destroy();
                }
                WiFiUtil.disableNetwork(context, cVar.a);
                HiddenWifiConnectHelper.removeNetwork(context, cVar.a, cVar.b, new HiddenWifiConnectHelper.OnWifiRemovedListener() { // from class: com.xiaomi.micolauncher.module.miot.OtInvokers.5
                    @Override // com.xiaomi.mico.base.utils.HiddenWifiConnectHelper.OnWifiRemovedListener
                    public void onWifiRemoveSuccess() {
                        OtInvokers.b.i("removeNetwork hidden network success before connectNormalSsid");
                        synchronized (OtInvokers.this.j) {
                            OtInvokers.this.n = null;
                        }
                        OtInvokers.this.a(context, sVar.a);
                    }

                    @Override // com.xiaomi.mico.base.utils.HiddenWifiConnectHelper.OnWifiRemovedListener
                    public void onWifiRemoveFailed() {
                        OtInvokers.b.w("removeNetwork hidden network failed before connectNormalSsid");
                        OtInvokers.this.a(context, sVar.a);
                    }
                });
            } else {
                a(context, sVar.a);
            }
        }
    }

    private boolean b(Context context, String str, String str2) {
        if (!WiFiUtil.isSecurityOpenWifi(context, str, str2, "")) {
            b.d("SSID %s is not open wifi, safe to connect", str);
            return true;
        }
        boolean isValid = NetworkMonitor.getWifiSsidPassword(context, str, b).isValid();
        b.d("SSID %s found %B in password store", str, Boolean.valueOf(isValid));
        return isValid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, OtDefs.t tVar) {
        boolean z = WiFiUtil.support5G(context) || Hardware.isX08A(context) || Hardware.isX08E();
        boolean hasData = ContainerUtil.hasData(tVar.e);
        boolean z2 = hasData && BlackListWifiStore.contains(context, tVar.e);
        boolean z3 = z && hasData && !z2;
        String str = z3 ? tVar.e : tVar.b;
        String str2 = z3 ? tVar.f : tVar.c;
        String str3 = z3 ? null : tVar.d;
        b.i("connect 5G=%B, support 5G=%B, has 5G ssid=%B， is5gSsidInBlackList=%B, ssid=%s, password=%s, bssid=%s", Boolean.valueOf(z3), Boolean.valueOf(z), Boolean.valueOf(hasData), Boolean.valueOf(z2), str, str2, str3);
        String str4 = tVar.g;
        if (BlackListWifiStore.contains(context, str)) {
            b.i("OtInvokers:%s is in black list, give up connect", str);
            a(context, tVar, false);
            if (SystemSetting.isInitialized(context)) {
                this.i.j();
            }
        } else if (!ContainerUtil.hasData(str2) || b(context, str, str2)) {
            if (WiFiUtil.isWifiConnected(context) && SystemSetting.isInitialized(context)) {
                String ssid = WiFiUtil.getSSID(context);
                if (!TextUtils.equals(ssid, HiddenWifiScanHelper.getDefaultHiddenSsid())) {
                    b.w("current network ssid=%s is connected, won't connect to %s", ssid, str);
                    this.i.j();
                    sendWifiConnectivityEvent(context, ssid, WiFiUtil.getBSSID(context), true, null);
                    return;
                }
                b.w("current network ssid=%s is connected, disconnect it", ssid);
                WiFiUtil.disableNetwork(context, ssid);
            }
            a(context, z3, str4, str, str2, str3);
        } else {
            b.w("won't connect to %s, open wifi and SDK orders a password", str);
            a(context, tVar, false);
            if (SystemSetting.isInitialized(context)) {
                this.i.j();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Context context, final boolean z, final String str, final String str2, final String str3, final String str4) {
        synchronized (this.j) {
            if (this.m == null) {
                this.m = new d();
            }
            this.m.a(context, new WifiConnectExecutor.WiFiConnectionListener() { // from class: com.xiaomi.micolauncher.module.miot.OtInvokers.6
                @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                public void onStateChange(SupplicantState supplicantState) {
                }

                @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                public void onConnectionStart(String str5) {
                    OtInvokers.b.d("OtInvokers:ConnectNormalWifi onConnectionStart ssid=%s", str5);
                }

                @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                public void onConnectionSuccess(String str5) {
                    OtInvokers.b.i("OtInvokers:ConnectNormalWifi onConnectionSuccess ssid=%s", str5);
                    if (SystemSetting.isInitialized(context)) {
                        OtInvokers.this.i.j();
                    } else {
                        OtInvokers.this.a(context, "wifi_config_success", str, str5);
                    }
                    OtInvokers.this.sendWifiConnectivityEvent(context, str5, str4, true, null);
                    synchronized (OtInvokers.this.j) {
                        WifiConnectExecutor wifiConnectExecutor = OtInvokers.this.m == null ? null : OtInvokers.this.m.b;
                        if (wifiConnectExecutor != null) {
                            String curSsid = wifiConnectExecutor.getCurSsid();
                            if (WiFiUtil.isSameSSID(str5, curSsid) && !HiddenWifiScanHelper.isDefaultHiddenSsid(curSsid)) {
                                MiotDeviceManager.getInstance().setWifi(WiFiUtil.stripSSID(curSsid));
                            }
                        }
                    }
                }

                @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                public void onConnectionFail(String str5, WiFiConnState wiFiConnState) {
                    OtInvokers.b.e("OtInvokers:ConnectNormalWifi onConnectionFail ssid=%s, error code=%s", str5, wiFiConnState);
                    if (OtInvokers.this.h == null) {
                        return;
                    }
                    if (z) {
                        OtInvokers otInvokers = OtInvokers.this;
                        otInvokers.a(context, false, otInvokers.h.g, OtInvokers.this.h.b, OtInvokers.this.h.c, OtInvokers.this.h.d);
                        return;
                    }
                    OtInvokers.this.sendWifiConnectivityEvent(context, str5, str4, false, null);
                    OtInvokers.this.a(context, "wifi_config_fail", str, str5);
                    if (SystemSetting.isInitialized(context)) {
                        OtInvokers.this.i.j();
                    }
                    OtInvokers.this.h = null;
                }
            });
            this.m.b.setRemoveExistedNetworkBeforeConnect(false);
            if (!SystemSetting.isInitialized(context)) {
                a(context, "wifi_config", "", str2);
                ThreadUtil.getWorkHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$OtInvokers$6OhGkfoYk_a0Xlo3-8St5ANT3sI
                    @Override // java.lang.Runnable
                    public final void run() {
                        OtInvokers.this.a(context, str, str2, str3, str4);
                    }
                }, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                return;
            }
            this.m.b.connect(str2, str3, "", str4, true, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, String str, String str2, String str3, String str4) {
        a(context, "wifi_config_connecting", str, str2);
        this.m.b.setPingTimeAfterConnected(true);
        this.m.b.connect(str2, str3, "", str4, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, String str, String str2, String str3) {
        Intent intent = new Intent("com.xiaomi.micolauncher.ot.init");
        intent.putExtra("step", "connect_ssid");
        intent.putExtra(XiaomiOAuthConstants.EXTRA_STATE_2, str);
        intent.putExtra(OneTrack.Param.UID, str2);
        intent.putExtra("ssid", str3);
        context.sendBroadcast(intent);
    }

    private void e(final Context context, final String str) {
        final OtDefs.o oVar = (OtDefs.o) Gsons.getGson().fromJson(str, (Class<Object>) OtDefs.o.class);
        sendWifiDisconnectResp(context, oVar.a.a, oVar.a.b, null);
        HiddenWifiConnectHelper hiddenWifiConnectHelper = this.l;
        if (hiddenWifiConnectHelper != null) {
            hiddenWifiConnectHelper.destroy();
        }
        HiddenWifiConnectHelper.removeNetwork(context, oVar.a.a, oVar.a.b, new HiddenWifiConnectHelper.OnWifiRemovedListener() { // from class: com.xiaomi.micolauncher.module.miot.OtInvokers.7
            @Override // com.xiaomi.mico.base.utils.HiddenWifiConnectHelper.OnWifiRemovedListener
            public void onWifiRemoveSuccess() {
                OtInvokers.this.sendWifiDisconnectedEvent(context, oVar.a.a, oVar.a.b, null);
            }

            @Override // com.xiaomi.mico.base.utils.HiddenWifiConnectHelper.OnWifiRemovedListener
            public void onWifiRemoveFailed() {
                OtInvokers.b.e("OtInvokers: failed to disconnect %s", str);
                WiFiUtil.disableNetwork(context, oVar.a.a);
            }
        });
        if (this.o == null) {
            this.o = new b(TimeUnit.SECONDS.toMillis(10L));
        }
        this.o.a(context);
    }

    public void sendWifiDisconnectResp(Context context, String str, String str2, OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener) {
        OtDefs.p pVar = new OtDefs.p();
        pVar.a = new OtDefs.n();
        pVar.a.a = WiFiUtil.stripSSID(str);
        pVar.a.b = str2;
        a(context, pVar, onSendTransparentMessageListener);
    }

    public void sendWifiConnectivityEvent(Context context, String str, String str2, boolean z, OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener) {
        if (TextUtils.isEmpty(str)) {
            DebugHelper.printStackTrace("sendWifiConnectedEvent_SSID_Empty", b);
            return;
        }
        OtDefs.k kVar = new OtDefs.k(z);
        kVar.a = new OtDefs.l();
        kVar.a.a = str;
        if (TextUtils.isEmpty(str2)) {
            kVar.a.b = WiFiUtil.getBSSID(context);
        } else {
            kVar.a.b = str2;
        }
        kVar.a.c = z ? "ok" : "error";
        if (z) {
            this.h = null;
        }
        a(context, kVar, onSendTransparentMessageListener);
    }

    public void sendWifiDisconnectedEvent(Context context, String str, String str2, OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener) {
        OtDefs.m mVar = new OtDefs.m();
        mVar.a = new OtDefs.n();
        mVar.a.a = WiFiUtil.stripSSID(str);
        mVar.a.b = str2;
        a(context, mVar, onSendTransparentMessageListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, OtDefs.t tVar, boolean z) {
        OtDefs.k kVar = new OtDefs.k(z);
        kVar.a = new OtDefs.l();
        kVar.a.a = WiFiUtil.stripSSID(tVar.b);
        kVar.a.b = tVar.d;
        kVar.a.c = z ? "ok" : "error";
        a(context, kVar, (OtApi.OnSendTransparentMessageListener) null);
        this.h = null;
    }

    public void parseAndStartScan(final Context context, String str) {
        OtDefs.WifiScanRequest wifiScanRequest = (OtDefs.WifiScanRequest) Gsons.getGson().fromJson(str, (Class<Object>) OtDefs.WifiScanRequest.class);
        if (wifiScanRequest.params == null || !ContainerUtil.hasData(wifiScanRequest.params.ssid)) {
            Logger logger = b;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(wifiScanRequest.params != null ? wifiScanRequest.params.type : -1);
            objArr[1] = wifiScanRequest.params != null ? wifiScanRequest.params.ssid : null;
            logger.e("OtInvokers: not supported scan wifi type, type = %s, ssid = %s, ignore this command", objArr);
            return;
        }
        final String str2 = wifiScanRequest.params.ssid;
        b.i("OtInvokers: Ordered ssid %s", str2);
        if (c()) {
            b.w("already scanning or connecting, do not go on scan %s", str2);
        } else {
            a(context, str2, wifiScanRequest.params.channels, new e() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$OtInvokers$CXmo1i44y325fSBdsyKcab1DNbQ
                @Override // com.xiaomi.micolauncher.module.miot.OtInvokers.e
                public final void onNotFound() {
                    OtInvokers.this.a(str2, context);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, Context context) {
        b.w("Not found hidden SSID");
    }

    private void a(final Context context, final String str, List<Integer> list, @NonNull final e eVar) {
        synchronized (this.j) {
            if (this.k == null) {
                this.k = new HiddenWifiScanHelper(context, str);
                this.k.init();
            }
            this.k.setChannels(list);
            this.k.setOnScanResultListener(new HiddenWifiScanHelper.OnScanResultListener() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$OtInvokers$H8QZPemdsIBz2Z8cJhGy8fG7UpQ
                @Override // com.xiaomi.mico.base.utils.HiddenWifiScanHelper.OnScanResultListener
                public final void onScanResult(List list2) {
                    OtInvokers.this.a(context, str, eVar, list2);
                }
            });
            this.k.startScan();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, String str, @NonNull e eVar, List list) {
        boolean a2 = a(context, str, list, (OtApi.OnSendTransparentMessageListener) null);
        b.i("OtInvokers:got scan wifi result, need to connect directly=%s", Boolean.valueOf(!a2));
        if (!a2) {
            eVar.onNotFound();
        }
    }

    private boolean c() {
        synchronized (this.j) {
            HiddenWifiScanHelper hiddenWifiScanHelper = this.k;
            if (hiddenWifiScanHelper == null || !hiddenWifiScanHelper.isScanning()) {
                HiddenWifiConnectHelper hiddenWifiConnectHelper = this.l;
                if (hiddenWifiConnectHelper != null && hiddenWifiConnectHelper.isConnecting()) {
                    b.i("is connecting %s %s", hiddenWifiConnectHelper.getSsid(), hiddenWifiConnectHelper.getBssid());
                    return true;
                } else if (this.m == null || this.m.b == null || !this.m.b.isConnecting() || this.m.a()) {
                    return NetworkMonitor.getInstance().isManuallySettingWifi();
                } else {
                    b.i("master ssid is connecting", this.m.b.getCurSsid());
                    return true;
                }
            } else {
                b.i("is scanning %s", hiddenWifiScanHelper.getHiddenSsid());
                return true;
            }
        }
    }

    private boolean a(Context context, @NonNull String str, @NonNull List<ScanResult> list, OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener) {
        if (!this.e.isBinded()) {
            b.e("OtInvokers: Unexpected, OT not binded, failed to send msg");
            return true;
        }
        b.i("OtInvokers:to find given SSID in scan wifi result listSize=%s", Integer.valueOf(ContainerUtil.getSize(list)));
        OtDefs.i iVar = new OtDefs.i();
        iVar.a = new OtDefs.j();
        String stripSSID = WiFiUtil.stripSSID(str);
        iVar.a.a = stripSSID;
        iVar.a.c = new ArrayList();
        List<OtDefs.q> list2 = iVar.a.c;
        for (ScanResult scanResult : list) {
            if (!WiFiUtil.isSameSSID(stripSSID, scanResult.SSID)) {
                b.v("OtInvokers: SSID %s not same as %s, ignored", scanResult.SSID, stripSSID);
            } else if (scanResult.wifiSsid == null || scanResult.wifiSsid.isHidden()) {
                b.w("OtInvokers: scanWifiResult is not hidden ssid, ignore it !!!");
            } else {
                b.i("OtInvokers:will send SSID %s BSSID %s", scanResult.SSID, scanResult.BSSID);
                OtDefs.q qVar = new OtDefs.q();
                qVar.a = scanResult.BSSID;
                qVar.c = WiFiUtil.frequencyToChannel(scanResult.frequency);
                qVar.b = scanResult.level;
                list2.add(qVar);
            }
        }
        iVar.a.b = ContainerUtil.getSize(list2);
        boolean z = iVar.a.b <= 0;
        if (z) {
            b.w("OtInvokers: cannot find the given ssidStripped %s in scan result.", stripSSID);
        }
        a(context, iVar, onSendTransparentMessageListener);
        return !z;
    }

    public void sendWifiReconnectedFailedEvent(final Context context, final OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener) {
        this.e.getOtLocalStatus(new MiotDeviceManager.OnOtStatusListener() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$OtInvokers$Rvv960dapjDC27QylEKV3q4sUKQ
            @Override // com.xiaomi.micolauncher.module.miot.MiotDeviceManager.OnOtStatusListener
            public final void onStatus(String str) {
                OtInvokers.this.a(context, onSendTransparentMessageListener, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final Context context, final OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener, String str) {
        b.i("ot local status is %s", str);
        if (!OtDefs.OtLocalStatus.STATUS_CLOUD_CONNECTED.equals(((OtDefs.OtLocalStatus) Gsons.getGson().fromJson(str, (Class<Object>) OtDefs.OtLocalStatus.class)).a)) {
            b(context, onSendTransparentMessageListener);
        } else if (NetworkMonitor.getInstance().isWifiConnected(true)) {
            b.i("wifi connected, abort");
        } else {
            b.i("check it later");
            Threads.getLightWorkHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$OtInvokers$yssxacsUAb_1EpkfNQJkozlmOJA
                @Override // java.lang.Runnable
                public final void run() {
                    OtInvokers.this.d(context, onSendTransparentMessageListener);
                }
            }, d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(Context context, OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener) {
        if (NetworkMonitor.getInstance().isWifiConnected(true) || !canSentReconnectedFailedEvent()) {
            b.i("wifi connected or sending condition not met, abort");
            return;
        }
        b.i("retry send wifi reconnected failed event");
        sendWifiReconnectedFailedEvent(context, onSendTransparentMessageListener);
    }

    public boolean hasSetWifiStatus() {
        return this.e.hasSetWifiStatus();
    }

    private void b(final Context context, final OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener) {
        if (!hasSetWifiStatus()) {
            b.w("OtInvokers:Not ready to send transparent message");
            return;
        }
        b.i("OtInvokers:yes, ready to send transparent message");
        this.i.d();
        a(context, new OtDefs.r(), onSendTransparentMessageListener);
        Threads.getLightWorkHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$OtInvokers$aMWseSjdNGY1MyUs0-4kaNw8uNA
            @Override // java.lang.Runnable
            public final void run() {
                OtInvokers.this.c(context, onSendTransparentMessageListener);
            }
        }, a.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Context context, OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener) {
        if (this.i.c() || this.i.i()) {
            b.i("OtInvokers:scan request received");
        } else if (NetworkMonitor.getInstance().isWifiConnected()) {
            b.i("OtInvokers:network connected, abort send WifiReconnectedFailedEvent");
        } else {
            b.i("OtInvokers:scan request not received, send wifi-reconnected-failed event again");
            sendWifiReconnectedFailedEvent(context, onSendTransparentMessageListener);
        }
    }

    private void a(Context context, final Object obj, final OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener) {
        if (obj == null) {
            b.e("empty message won't be sent");
        } else if (!this.e.isBinded()) {
            b.e("OtInvokers:sendTransparentMsg ignore cause of not bind");
        } else {
            final String json = Remote.getGson().toJson(obj);
            this.e.a(json, new OtApi.OnSendTransparentMessageListener() { // from class: com.xiaomi.micolauncher.module.miot.OtInvokers.8
                @Override // com.xiaomi.micolauncher.module.miot.OtApi.OnSendTransparentMessageListener
                public void onSucceed(String str) {
                    OtInvokers.b.i("OtInvokers: send %s %s to OT successfully, received result=%s", obj.getClass(), json, str);
                    OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener2 = onSendTransparentMessageListener;
                    if (onSendTransparentMessageListener2 != null) {
                        onSendTransparentMessageListener2.onSucceed(str);
                    }
                }

                @Override // com.xiaomi.micolauncher.module.miot.OtApi.OnSendTransparentMessageListener
                public void onFailed(int i) {
                    OtInvokers.b.e("OtInvokers: failed to send %s %s to OT, result error code %s", obj.getClass(), json, Integer.valueOf(i));
                    OtApi.OnSendTransparentMessageListener onSendTransparentMessageListener2 = onSendTransparentMessageListener;
                    if (onSendTransparentMessageListener2 != null) {
                        onSendTransparentMessageListener2.onFailed(i);
                    }
                }
            });
        }
    }

    public boolean canSentReconnectedFailedEvent() {
        boolean b2 = this.i.b();
        boolean hasSetWifiStatus = hasSetWifiStatus();
        boolean hasUid = LoginModel.getInstance().hasUid();
        b.i("can send reconnected failed event, can send=%s, has set wifi status=%s, has uid=%s", Boolean.valueOf(b2), Boolean.valueOf(hasSetWifiStatus), Boolean.valueOf(hasUid));
        return b2 && hasSetWifiStatus && hasUid;
    }

    public void destroy() {
        synchronized (this.j) {
            if (this.l != null) {
                this.l.destroy();
            }
            if (this.k != null) {
                this.k.destroy();
            }
        }
    }

    public void removeHiddenNetworkIfExpired(Context context) {
        if (a.e() - this.i.a() > c) {
            b.d("OtInvokers:removeHiddenNetworkIfExpired disconnect hidden ssid");
            HiddenWifiConnectHelper.removeNetwork(context, HiddenWifiScanHelper.getDefaultHiddenSsid(), null, null);
        }
    }

    public static long getTimeoutForWaitOtEvent() {
        return a.b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static boolean a = true;
        private static final long b = TimeUnit.SECONDS.toMillis(25);
        private boolean c;
        private boolean d;
        private boolean e;
        private long f;
        private long g;

        private a() {
        }

        synchronized long a() {
            return this.g;
        }

        synchronized boolean b() {
            long e = e();
            long j = e - (a ? this.g : this.f);
            boolean z = false;
            OtInvokers.b.d("check status isReconnectedFailedEventSent=%s, isWifiStartReceived=%s, isScanReqReceived=%s, currentTime=%s, start time=%s, duration=%s", Boolean.valueOf(this.c), Boolean.valueOf(this.e), Boolean.valueOf(this.d), Long.valueOf(e), Long.valueOf(this.f), Long.valueOf(j));
            if (a) {
                if (j > b) {
                    z = true;
                }
                return z;
            }
            if (!this.d && !this.e) {
                if (j > b) {
                    OtInvokers.b.i("Elapsed %s millis, timeout, can send again", Long.valueOf(j));
                    return true;
                }
                return !this.c;
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void g() {
            this.d = true;
            this.g = e();
            OtInvokers.b.i("set isScanReqReceived=true, lastEventMillis=%s", Long.valueOf(this.g));
        }

        public synchronized boolean c() {
            return this.d;
        }

        synchronized void d() {
            j();
            this.c = true;
            this.f = e();
            this.g = e();
            OtInvokers.b.i("set isReconnectedFailedEventSent=true, lastEventMillis=%s", Long.valueOf(this.g));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void h() {
            this.e = true;
            this.g = e();
            OtInvokers.b.i("setReceivedWifStart, lastEventMillis=%s", Long.valueOf(this.g));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized boolean i() {
            return this.e;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void j() {
            this.c = false;
            this.e = false;
            this.d = false;
            this.f = 0L;
            this.g = 0L;
            OtInvokers.b.i("OtInvokers:AutoChangePasswordProcessStatus reset flags");
        }

        public static long e() {
            return SystemClock.uptimeMillis();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class c {
        String a;
        String b;
        int c;

        public c(String str, String str2, int i) {
            this.a = str;
            this.b = str2;
            this.c = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class b implements Runnable {
        private WifiConnectExecutor b;
        private final long c;
        private Context d;

        b(long j) {
            this.c = j;
        }

        void a(final Context context) {
            this.d = context;
            if (this.b == null) {
                this.b = new WifiConnectExecutor(context, new WifiConnectExecutor.WiFiConnectionListener() { // from class: com.xiaomi.micolauncher.module.miot.OtInvokers.b.1
                    @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                    public void onStateChange(SupplicantState supplicantState) {
                    }

                    @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                    public void onConnectionStart(String str) {
                        OtInvokers.b.d("OtInvokers: ConnectHistoryWifiTask connectUsableWiFi onConnectionStart ssid=%s", str);
                    }

                    @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                    public void onConnectionSuccess(String str) {
                        OtInvokers.b.d("OtInvokers: ConnectHistoryWifiTask connectUsableWiFi onConnectionSuccess ssid=%s", str);
                        OtInvokers.this.i.j();
                        OtInvokers.this.sendWifiConnectivityEvent(context, str, "", true, null);
                    }

                    @Override // com.xiaomi.mico.settingslib.wifi.WifiConnectExecutor.WiFiConnectionListener
                    public void onConnectionFail(String str, WiFiConnState wiFiConnState) {
                        OtInvokers.this.i.j();
                        OtInvokers.b.w("OtInvokers: ConnectHistoryWifiTask onConnectionFail ssid=%s, reason=%s", str, wiFiConnState);
                    }
                }, OtInvokers.b);
                this.b.setConnectMaxTime(NetworkMonitor.CONNECT_MAX_TIME_MILLIS);
                this.b.setRemoveExistedNetworkBeforeConnect(false);
            }
            Threads.getLightWorkHandler().removeCallbacks(this);
            Threads.getLightWorkHandler().postDelayed(this, this.c);
        }

        void a() {
            Threads.getLightWorkHandler().removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            WifiConnectExecutor wifiConnectExecutor;
            if (SystemSetting.isInitialized(this.d) && (wifiConnectExecutor = this.b) != null && !wifiConnectExecutor.isConnecting()) {
                OtInvokers.b.i("OtInvokers:ConnectHistoryWifiTask run");
                this.b.connectUsableWiFi();
            }
        }
    }
}
