package com.xiaomi.micolauncher.module.setting.utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.elvishew.xlog.Logger;
import com.xiaomi.mesh.MeshLogListener;
import com.xiaomi.mesh.MiotMeshManager;
import com.xiaomi.mesh.MiotMeshStatusListener;
import com.xiaomi.mesh.internal.WiFiInfo;
import com.xiaomi.mesh.provision.MiotBindDevicesResult;
import com.xiaomi.mesh.provision.MiotDeviceScanResult;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.mico.account.sdk.LoginManager;
import com.xiaomi.mico.appstorelib.AppStoreUtil;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.skill.DomainConfig;
import com.xiaomi.micolauncher.module.NetworkMonitor;
import com.xiaomi.micolauncher.module.initialize.WiFiPasswordStore;
import com.xiaomi.micolauncher.module.miot.mesh.MeshPairState;
import com.xiaomi.micolauncher.module.miot.mesh.MiotMeshIconInfo;
import com.xiaomi.micolauncher.module.miot.mesh.uievent.MeshDeviceShowEvent;
import com.xiaomi.micolauncher.module.miot.mesh.uievent.MeshScanEvent;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager;
import com.xiaomi.micolauncher.module.setting.utils.MiotMeshUtils;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import com.xiaomi.miot.host.manager.MiotHostManager;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.library.common.network.Network;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public class MiotProvisionManagerWrapper {
    public static final String ACTION_TRY_TO_UPDATE_MESH_GATEWAY = "android.mesh.gateway.ACTION_TRY_TO_UPDATE_MESH_GATEWAY";
    public static final int MESH_DEFAULT_OFF = 0;
    @SuppressLint({"StaticFieldLeak"})
    private static volatile MiotProvisionManagerWrapper b = null;
    private static volatile boolean d = false;
    private static MiotMeshUtils.WiFiPasswordInfo p;
    private Context e;
    private MiotProvisionDisplayHandler g;
    private List<MiotMeshDeviceItem> o;
    private static GLOBAL_ERROR f = GLOBAL_ERROR.SUCCESS;
    private static Map<String, MiotMeshIconInfo> q = new Hashtable();
    private boolean c = false;
    private MeshPairState.MeshState h = MeshPairState.MeshState.MESH_IDLE;
    private volatile boolean i = false;
    private int j = 0;
    private int k = 10;
    private int l = 10;
    private boolean m = false;
    private volatile boolean n = false;
    private final BroadcastReceiver r = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                L.mesh.i("%s On received action ----> %s", "[MiotProvisionManagerWrapper]: ", action);
                char c = 65535;
                int hashCode = action.hashCode();
                if (hashCode != -1530327060) {
                    if (hashCode != 40146574) {
                        if (hashCode == 482895365 && action.equals(MiotProvisionManagerWrapper.ACTION_TRY_TO_UPDATE_MESH_GATEWAY)) {
                            c = 2;
                        }
                    } else if (action.equals("android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED")) {
                        c = 1;
                    }
                } else if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                    c = 0;
                }
                switch (c) {
                    case 0:
                        int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 10);
                        int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                        L.mesh.i("%s preState: %d, state: %d", "[MiotProvisionManagerWrapper]: ", Integer.valueOf(intExtra), Integer.valueOf(intExtra2));
                        switch (intExtra2) {
                            case 10:
                            case 12:
                                MiotProvisionManagerWrapper miotProvisionManagerWrapper = MiotProvisionManagerWrapper.this;
                                miotProvisionManagerWrapper.k = miotProvisionManagerWrapper.l;
                                MiotProvisionManagerWrapper.this.l = intExtra2;
                                if (MiotProvisionManagerWrapper.this.l != 12) {
                                    MiotProvisionManagerWrapper.this.m = false;
                                    return;
                                } else if (MiotProvisionManagerWrapper.d) {
                                    MiotMeshManager.getInstance().tryToRebindMeshService();
                                    return;
                                } else {
                                    return;
                                }
                            case 11:
                            case 13:
                            default:
                                return;
                        }
                    case 1:
                        int intExtra3 = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 0);
                        int intExtra4 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                        if (((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")) != null) {
                            L.mesh.i("%s preState: %d, state: %d", "[MiotProvisionManagerWrapper]: ", Integer.valueOf(intExtra3), Integer.valueOf(intExtra4));
                            if (intExtra4 == 0) {
                                MiotProvisionManagerWrapper.this.m = false;
                                if (MiotProvisionManagerWrapper.this.n) {
                                    BluetoothSettingManager.getInstance().closeBluetoothReal();
                                    MiotProvisionManagerWrapper.this.n = false;
                                    return;
                                }
                                return;
                            } else if (intExtra4 == 2) {
                                MiotProvisionManagerWrapper.this.m = true;
                                return;
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    case 2:
                        if (MiotProvisionManagerWrapper.this.e == null) {
                            MiotProvisionManagerWrapper.this.e = MicoApplication.getGlobalContext();
                        }
                        AppStoreUtil.silentUpgradeApps(MiotProvisionManagerWrapper.this.e, "com.xiaomi.mesh.gateway");
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private MiotMeshManager.MiotMeshServiceCallBack s = new MiotMeshManager.MiotMeshServiceCallBack() { // from class: com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper.3
        @Override // com.xiaomi.mesh.MiotMeshManager.MiotMeshServiceCallBack
        public void reportScanDevicesResult(MiotDeviceScanResult miotDeviceScanResult) {
            if (MiotProvisionManagerWrapper.this.g != null) {
                MiotProvisionManagerWrapper.this.g.cleanTimeout();
            }
            MiotProvisionManagerWrapper.this.j = miotDeviceScanResult.getParams().size();
            Logger logger = L.mesh;
            logger.i("provisionScanResult count:" + MiotProvisionManagerWrapper.this.j);
            MiotProvisionManagerWrapper.this.dropUnpublishedDevices(miotDeviceScanResult);
            MiotProvisionManagerWrapper.this.h = MeshPairState.MeshState.MESH_SCAN_COMPLETE;
        }

        @Override // com.xiaomi.mesh.MiotMeshManager.MiotMeshServiceCallBack
        public void reportAck(int i, String str) {
            L.mesh.i("MiotMeshServiceCallBack reportAck type=%s, ack=%s", Integer.valueOf(i), str);
            if (i == 0) {
                if (MiotProvisionManagerWrapper.this.g != null) {
                    MiotProvisionManagerWrapper.this.g.handleScanAck(str);
                }
                MiotProvisionManagerWrapper.this.h = MeshPairState.MeshState.MESH_SCAN_IN_OPERATION;
            } else if (i == 1) {
                if (MiotProvisionManagerWrapper.this.g != null) {
                    MiotProvisionManagerWrapper.this.g.handleBindAck(str);
                }
                MiotProvisionManagerWrapper.this.h = MeshPairState.MeshState.MESH_BIND_IN_OPERATION;
            }
        }

        @Override // com.xiaomi.mesh.MiotMeshManager.MiotMeshServiceCallBack
        public void reportBindDevicesResult(MiotBindDevicesResult miotBindDevicesResult) {
            L.mesh.i("MiotMeshServiceCallBack reportBindDevicesResult");
            if (MiotProvisionManagerWrapper.this.g != null) {
                MiotProvisionManagerWrapper.this.g.cleanTimeout();
                MiotProvisionManagerWrapper.this.g.handleBindDeviceResult(miotBindDevicesResult);
            }
            MiotProvisionManagerWrapper.this.h = MeshPairState.MeshState.MESH_BIND_COMPLETE;
        }

        @Override // com.xiaomi.mesh.MiotMeshManager.MiotMeshServiceCallBack
        public WiFiInfo getWiFiInfo() {
            L.mesh.i("MiotMeshServiceCallBack getWifiInfo");
            MiotProvisionManagerWrapper miotProvisionManagerWrapper = MiotProvisionManagerWrapper.this;
            return miotProvisionManagerWrapper.getProvisionWifiInfo(miotProvisionManagerWrapper.e);
        }

        @Override // com.xiaomi.mesh.MiotMeshManager.MiotMeshServiceCallBack
        public void saveCurrentWiFi() {
            L.mesh.i("MiotMeshServiceCallBack saveCurrentWiFi");
            String ssid = WiFiUtil.getSSID(MiotProvisionManagerWrapper.this.e);
            if (ssid != null) {
                MiotProvisionManagerWrapper.setWifiInfoBeforeBind(new MiotMeshUtils.WiFiPasswordInfo(ssid, WiFiPasswordStore.loadStoredWiFi(MiotProvisionManagerWrapper.this.e, ssid)));
                Logger logger = L.mesh;
                logger.i("save wifiInfoBeforeBind: " + MiotProvisionManagerWrapper.getWifiInfoBeforeBind());
            }
            NetworkMonitor.getInstance().setManuallySettingWifi(true);
        }
    };
    private MiotMeshStatusListener t = new MiotMeshStatusListener() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MiotProvisionManagerWrapper$KHnP_PefCq8c6LEcc-nUN3lfaIk
        @Override // com.xiaomi.mesh.MiotMeshStatusListener
        public final void onMeshInitDone(int i) {
            MiotProvisionManagerWrapper.this.a(i);
        }
    };
    MeshLogListener a = new MeshLogListener() { // from class: com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper.4
        @Override // com.xiaomi.mesh.MeshLogListener
        public void logV(String str, String str2) {
            L.mesh.v("%s %s", str, str2);
        }

        @Override // com.xiaomi.mesh.MeshLogListener
        public void logD(String str, String str2) {
            L.mesh.d("%s %s", str, str2);
        }

        @Override // com.xiaomi.mesh.MeshLogListener
        public void logI(String str, String str2) {
            L.mesh.i("%s %s", str, str2);
        }

        @Override // com.xiaomi.mesh.MeshLogListener
        public void logW(String str, String str2) {
            L.mesh.w("%s %s", str, str2);
        }

        @Override // com.xiaomi.mesh.MeshLogListener
        public void logE(String str, String str2) {
            L.mesh.e("%s %s", str, str2);
        }
    };

    /* loaded from: classes3.dex */
    public enum GLOBAL_ERROR {
        SUCCESS,
        NO_AVAILABLE_WIFI,
        NO_ENCRYPTED_AVAILABLE_WIFI
    }

    public static MiotProvisionManagerWrapper getInstance() {
        if (b == null) {
            synchronized (MiotProvisionManagerWrapper.class) {
                if (b == null) {
                    b = new MiotProvisionManagerWrapper();
                    if (SystemSetting.getKeyMeshEnable() == -1) {
                        SystemSetting.setKeyMeshEnable(0);
                    }
                }
            }
        }
        return b;
    }

    public void init(Context context) {
        this.e = context;
        d();
        e();
        if (isMeshEnable()) {
            b();
        }
    }

    private void b() {
        if (!this.c) {
            L.mesh.i("%s start !!", "[MiotProvisionManagerWrapper]: ");
            this.c = true;
            c();
        }
    }

    private void c() {
        if (this.g == null) {
            this.g = new MiotProvisionDisplayHandler(this.e);
        }
    }

    public MiotMeshIconInfo getMiotMeshIconInfoById(String str) {
        return q.get(str);
    }

    private List<MiotMeshDeviceItem> a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSONObject.parseArray(JSONObject.parseObject(str.replace("\n", "").replace("\\\"", "\"")).getJSONArray("noSupportList").toJSONString(), MiotMeshDeviceItem.class);
    }

    private void d() {
        ApiManager.meshIconService.getNoSupportDevice("{\"lang\":\"zh_CN\",\"version\":\"1\",\"name\":\"wifispeaker_nosupport_bind\"}").subscribeOn(MicoSchedulers.io()).map(new Function() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MiotProvisionManagerWrapper$f_g3AXqtCoIZk4YWZyxFGMfNzqM
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                List a;
                a = MiotProvisionManagerWrapper.this.a((ResponseBody) obj);
                return a;
            }
        }).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MiotProvisionManagerWrapper$n38v_FbCG2oc25q7SeyUnioEH2s
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiotProvisionManagerWrapper.this.a((List) obj);
            }
        }, $$Lambda$MiotProvisionManagerWrapper$Guf5IZaPGASdrZY0jXPHZAr3Grg.INSTANCE);
    }

    public /* synthetic */ List a(ResponseBody responseBody) throws Exception {
        String string = responseBody.string();
        Logger logger = L.mesh;
        logger.i("pull no support devices resultStr: " + string);
        if (TextUtils.isEmpty(string)) {
            L.mesh.e("no support devices is empty");
            return null;
        } else if (string.contains("noSupportList")) {
            return a(JSONObject.parseObject(string).getJSONObject("result").getString("content"));
        } else {
            Logger logger2 = L.mesh;
            logger2.e("no support devices is error:" + string);
            return null;
        }
    }

    public /* synthetic */ void a(List list) throws Exception {
        this.o = list;
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        Logger logger = L.mesh;
        logger.e("no support devices error:" + th.getMessage());
    }

    private void e() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction(ACTION_TRY_TO_UPDATE_MESH_GATEWAY);
        Context context = this.e;
        if (context != null) {
            context.registerReceiver(this.r, intentFilter);
        }
    }

    public void dropUnpublishedDevices(final MiotDeviceScanResult miotDeviceScanResult) {
        final ArrayList arrayList = new ArrayList();
        final AtomicInteger atomicInteger = new AtomicInteger(miotDeviceScanResult.getParams().size());
        if (atomicInteger.get() == 0) {
            a(arrayList, miotDeviceScanResult);
            return;
        }
        for (final MiotDeviceScanResult.ParamsBean paramsBean : miotDeviceScanResult.getParams()) {
            String str = null;
            final String str2 = "";
            if (!TextUtils.isEmpty(paramsBean.getModel())) {
                String model = paramsBean.getModel();
                str = String.format("{\"models\":\"%s\"}", paramsBean.getModel());
                str2 = model;
            } else if (paramsBean.getPid() != 0) {
                str2 = String.valueOf(paramsBean.getPid());
                str = String.format("{\"pdids\":\"%d\"}", Integer.valueOf(paramsBean.getPid()));
            } else {
                Logger logger = L.mesh;
                logger.i("unknown device: " + paramsBean);
            }
            ApiManager.meshIconService.getIconInfo(str).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MiotProvisionManagerWrapper$bVyjAUZlWO_W-oGrKdkOYJzmpQQ
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MiotProvisionManagerWrapper.this.a(paramsBean, arrayList, str2, atomicInteger, miotDeviceScanResult, (ResponseBody) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MiotProvisionManagerWrapper$M2jsCQlVGl6BcYtmWrKMBcAIoIU
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MiotProvisionManagerWrapper.this.a(paramsBean, atomicInteger, arrayList, miotDeviceScanResult, (Throwable) obj);
                }
            });
        }
    }

    public /* synthetic */ void a(MiotDeviceScanResult.ParamsBean paramsBean, List list, String str, AtomicInteger atomicInteger, MiotDeviceScanResult miotDeviceScanResult, ResponseBody responseBody) throws Exception {
        String string = responseBody.string();
        Logger logger = L.mesh;
        logger.i("pull name and url resultStr: " + string);
        MiotMeshIconInfo miotMeshIconInfo = (MiotMeshIconInfo) Gsons.getGson().fromJson(string, (Class<Object>) MiotMeshIconInfo.class);
        if (miotMeshIconInfo.getResult() == null || miotMeshIconInfo.getResult().getConfigs() == null || miotMeshIconInfo.getResult().getConfigs().size() <= 0) {
            Logger logger2 = L.mesh;
            logger2.w("This is an unavailable device:" + paramsBean);
            paramsBean.setFriendly_name("未知设备");
            list.add(paramsBean);
        } else {
            Logger logger3 = L.mesh;
            logger3.d("This is an available device:" + paramsBean + ", add to beans");
            list.add(paramsBean);
            if (!TextUtils.isEmpty(str)) {
                q.put(str, miotMeshIconInfo);
            }
        }
        if (a(atomicInteger)) {
            a(list, miotDeviceScanResult);
        }
    }

    public /* synthetic */ void a(MiotDeviceScanResult.ParamsBean paramsBean, AtomicInteger atomicInteger, List list, MiotDeviceScanResult miotDeviceScanResult, Throwable th) throws Exception {
        th.printStackTrace();
        Logger logger = L.mesh;
        logger.e("pull name and url error dev:" + paramsBean, th);
        if (a(atomicInteger)) {
            a(list, miotDeviceScanResult);
        }
    }

    private void a(List<MiotDeviceScanResult.ParamsBean> list, MiotDeviceScanResult miotDeviceScanResult) {
        miotDeviceScanResult.setParams(list);
        MiotProvisionDisplayHandler miotProvisionDisplayHandler = this.g;
        if (miotProvisionDisplayHandler != null) {
            miotProvisionDisplayHandler.handleScanResult(miotDeviceScanResult);
        }
        Logger logger = L.mesh;
        logger.i("send merge scan result: " + miotDeviceScanResult.getParams());
    }

    private boolean a(AtomicInteger atomicInteger) {
        atomicInteger.getAndDecrement();
        return atomicInteger.get() <= 0;
    }

    public boolean isMeshState() {
        switch (this.h) {
            case MESH_IDLE:
            case MESH_OPEN_MITV:
                return true;
            case MESH_REQUEST_SCAN:
            case MESH_SCAN_IN_OPERATION:
            case MESH_SCAN_COMPLETE:
            case MESH_REQUEST_BIND:
            case MESH_BIND_IN_OPERATION:
            case MESH_BIND_COMPLETE:
                return false;
            default:
                return true;
        }
    }

    public void setMeshState(MeshPairState.MeshState meshState) {
        this.h = meshState;
    }

    private List<WiFiPasswordStore.PwdStored> a(Context context) {
        ArrayList arrayList = new ArrayList();
        for (ScanResult scanResult : ((WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI)).getScanResults()) {
            if (!WiFiUtil.is5GHz(scanResult.frequency)) {
                arrayList.add(new WiFiPasswordStore.PwdStored(scanResult.SSID, ""));
            }
        }
        return arrayList;
    }

    public WiFiInfo getProvisionWifiInfo(Context context) {
        List<WiFiPasswordStore.PwdStored> loadStored = WiFiPasswordStore.loadStored(context);
        if (loadStored == null) {
            Log.w("MICO.mesh", "getProvisionWifiInfo loadStored is null");
            loadStored = new ArrayList();
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
        String ssid = WiFiUtil.getSSID(context);
        WiFiPasswordStore.PwdStored pwdStored = null;
        if (!TextUtils.isEmpty(ssid) && loadStored.size() > 0) {
            for (WiFiPasswordStore.PwdStored pwdStored2 : loadStored) {
                if (TextUtils.equals(ssid, pwdStored2.getIdentity())) {
                    break;
                }
            }
        }
        pwdStored2 = null;
        L.mesh.d("currentWifiInfo: " + pwdStored2 + ", is 5G:" + WiFiUtil.is5GHz(wifiManager.getConnectionInfo().getFrequency()));
        if (pwdStored2 != null && TextUtils.isEmpty(pwdStored2.decryptedPassword())) {
            setGlobalError(GLOBAL_ERROR.NO_ENCRYPTED_AVAILABLE_WIFI);
            L.mesh.e(context.getResources().getString(R.string.iot_devices_provision_need_encrypted_wifi));
        } else if (pwdStored2 == null || WiFiUtil.is5GHz(wifiManager.getConnectionInfo().getFrequency()) || TextUtils.isEmpty(pwdStored2.decryptedPassword())) {
            setGlobalError(GLOBAL_ERROR.NO_AVAILABLE_WIFI);
            L.mesh.e(context.getResources().getString(R.string.iot_devices_provision_need_wifi));
        } else {
            setGlobalError(GLOBAL_ERROR.SUCCESS);
            pwdStored = pwdStored2;
        }
        if (pwdStored == null) {
            List<WiFiPasswordStore.PwdStored> a = a(context);
            if (a == null) {
                loadStored.clear();
            } else {
                loadStored.retainAll(a);
            }
            L.mesh.d("available wifi: " + loadStored);
            if (loadStored.size() > 0) {
                for (WiFiPasswordStore.PwdStored pwdStored3 : loadStored) {
                    if (!TextUtils.isEmpty(pwdStored3.decryptedPassword())) {
                        setGlobalError(GLOBAL_ERROR.SUCCESS);
                        break;
                    }
                }
            }
        }
        pwdStored3 = pwdStored;
        L.mesh.i("targetWifi: " + pwdStored3);
        WiFiInfo wiFiInfo = new WiFiInfo();
        wiFiInfo.setSsid(pwdStored3 == null ? "" : pwdStored3.getIdentity());
        wiFiInfo.setPassword(pwdStored3 == null ? "" : pwdStored3.decryptedPassword());
        wiFiInfo.setUserId(LoginManager.get().getUserId());
        return wiFiInfo;
    }

    public static MiotMeshUtils.WiFiPasswordInfo getWifiInfoBeforeBind() {
        return p;
    }

    public static void setWifiInfoBeforeBind(MiotMeshUtils.WiFiPasswordInfo wiFiPasswordInfo) {
        p = wiFiPasswordInfo;
    }

    public static void cleanWiFiInfoBeforeScan() {
        p = null;
    }

    public static GLOBAL_ERROR getGlobalError() {
        return f;
    }

    public static void setGlobalError(GLOBAL_ERROR global_error) {
        f = global_error;
    }

    public void reportMeshServiceHasDisconnect() {
        MiotProvisionDisplayHandler miotProvisionDisplayHandler = this.g;
        if (miotProvisionDisplayHandler != null) {
            miotProvisionDisplayHandler.triggerTimeoutNow();
        }
        if (!d) {
            return;
        }
        if (!this.m) {
            BluetoothSettingManager.getInstance().closeBluetoothReal();
            this.n = false;
            return;
        }
        this.n = true;
    }

    private void f() {
        q.clear();
        this.j = 0;
        int scanDeviceTimeout = MiotMeshManager.getInstance().getScanDeviceTimeout();
        Logger logger = L.mesh;
        logger.d("start scan timeout operation timeout = " + scanDeviceTimeout);
        MiotProvisionDisplayHandler miotProvisionDisplayHandler = this.g;
        if (miotProvisionDisplayHandler != null) {
            miotProvisionDisplayHandler.triggerTimeout(0, (scanDeviceTimeout + 10) * 1000);
        }
        cleanWiFiInfoBeforeScan();
        MiotMeshManager.getInstance().receiveBrainMessage("scan_devices", null);
        this.h = MeshPairState.MeshState.MESH_REQUEST_SCAN;
    }

    private void g() {
        int bindSingleDeviceTimeout = MiotMeshManager.getInstance().getBindSingleDeviceTimeout();
        Logger logger = L.mesh;
        logger.d("start bind timeout operation timeout = " + bindSingleDeviceTimeout);
        MiotProvisionDisplayHandler miotProvisionDisplayHandler = this.g;
        if (miotProvisionDisplayHandler != null) {
            miotProvisionDisplayHandler.triggerTimeout(1, ((bindSingleDeviceTimeout * this.j) + 10) * 1000);
        }
        MiotMeshManager.getInstance().receiveBrainMessage("bind_devices", null);
        this.h = MeshPairState.MeshState.MESH_REQUEST_BIND;
    }

    public static boolean isTargetWifiIsConnected(Context context) {
        String ssid = getWifiInfoBeforeBind().getSsid();
        for (int i = 0; i < 10; i++) {
            String ssid2 = WiFiUtil.getSSID(context);
            if (TextUtils.isEmpty(ssid2)) {
                L.mesh.i("currentSsid is empty");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    L.mesh.e("error: " + e.getMessage());
                }
            } else if (!TextUtils.isEmpty(ssid) && TextUtils.equals(ssid, ssid2)) {
                return true;
            } else {
                L.mesh.i("currentSsid: " + ssid2 + ", targetSsid: " + ssid + " is not match");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e2) {
                    L.mesh.e("error: " + e2.getMessage());
                }
            }
        }
        return false;
    }

    public void registerGatewayReceiveListener() {
        MiotMeshManager.getInstance().registerGatewayReceiveListener();
    }

    public void initMiotMeshManager(boolean z) {
        this.i = z;
        if (!MiotMeshManager.getInstance().isMeshServiceConnected() || !h()) {
            L.mesh.i("%s 开始 bindMeshService!", "[MiotProvisionManagerWrapper]: ");
            if (this.e == null) {
                this.e = MicoApplication.getGlobalContext();
            }
            MiotMeshManager.getInstance().bindMeshService(this.e, new MiotMeshManager.MiotMeshCompleteListener() { // from class: com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper.2
                @Override // com.xiaomi.mesh.MiotMeshManager.MiotMeshCompleteListener
                public void onComplete() {
                    L.mesh.i("%s bindMeshService onComplete!", "[MiotProvisionManagerWrapper]: ");
                    MiotMeshManager.getInstance().registerMeshLogListener(MiotProvisionManagerWrapper.this.a);
                    MiotMeshManager.getInstance().init(Hardware.isBigScreen(), MiotProvisionManagerWrapper.this.o);
                    MiotMeshManager.getInstance().registerMiotMeshServiceCallBack(MiotProvisionManagerWrapper.this.s);
                    MiotMeshManager.getInstance().registerMiotMeshStatusListener(MiotProvisionManagerWrapper.this.t);
                }

                @Override // com.xiaomi.mesh.MiotMeshManager.MiotMeshCompleteListener
                public void onFailed(String str) {
                    L.mesh.e("%s MiotMeshManager bindMeshService failed: %s", "[MiotProvisionManagerWrapper]: ", str);
                }
            });
            return;
        }
        L.mesh.i("%s mesh had been initialized!", "[MiotProvisionManagerWrapper]: ");
        registerGatewayReceiveListener();
    }

    public /* synthetic */ void a(int i) {
        L.mesh.i("%s onMeshInitDone result: %s", "[MiotProvisionManagerWrapper]: ", Integer.valueOf(i));
        if (i == 0) {
            L.mesh.i("%s 初始化mesh网关成功!", "[MiotProvisionManagerWrapper]: ");
            setMeshEnable(1);
            b();
            if (this.i) {
                L.mesh.i("%s 发送扫描Mesh设备请求!", "[MiotProvisionManagerWrapper]: ");
                f();
                return;
            }
            return;
        }
        L.mesh.e("%s 初始化mesh网关失败 !!", "[MiotProvisionManagerWrapper]: ");
    }

    public void discoveryDevice() {
        if (this.h == MeshPairState.MeshState.MESH_SCAN_IN_OPERATION || this.h == MeshPairState.MeshState.MESH_BIND_IN_OPERATION) {
            L.mesh.d("%s current mesh state: %s", "[MiotProvisionManagerWrapper]: ", this.h.toString());
            return;
        }
        try {
            if (MiotHostManager.getInstance().isMiotConnected()) {
                L.mesh.d("%s MiotHostManager isMiotConnected! isMeshEnable=%s", "[MiotProvisionManagerWrapper]: ", Boolean.valueOf(isMeshEnable()));
                if (isMeshEnable()) {
                    L.mesh.d("%s MiotMeshManager isMeshServiceConnected: %s", "[MiotProvisionManagerWrapper]: ", Boolean.valueOf(MiotMeshManager.getInstance().isMeshServiceConnected()));
                    if (!i()) {
                        if (MiotMeshManager.getInstance().isMeshServiceConnected()) {
                            f();
                        } else {
                            initMiotMeshManager(true);
                        }
                    }
                } else if (i()) {
                    initMiotMeshManager(false);
                } else {
                    initMiotMeshManager(true);
                }
            } else {
                L.mesh.e("%s MiotHostManager not connected", "[MiotProvisionManagerWrapper]: ");
            }
        } catch (MiotException e) {
            L.mesh.e("MiotException: ", e);
        }
    }

    public void queryIsConnectDevicesResult(boolean z) {
        if (z) {
            L.mesh.i("%s 发送绑定Mesh设备请求!", "[MiotProvisionManagerWrapper]: ");
            g();
            return;
        }
        L.mesh.i("%s 用户回复不需要连接设备", "[MiotProvisionManagerWrapper]: ");
        MiotMeshManager.getInstance().setProvisionState(MeshPairState.MeshState.MESH_IDLE.ordinal());
        EventBusRegistry.getEventBus().post(new MeshDeviceShowEvent(MeshDeviceShowEvent.MeshDeviceShowEnum.FINISH_TO_MAINACTIVITY));
        this.h = MeshPairState.MeshState.MESH_IDLE;
    }

    public void openMiTv(String str) {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject.put("id", (Object) Integer.valueOf(MiotProvisionSessionId.generateSessionId()));
        jSONObject.put(SchemaActivity.KEY_METHOD, (Object) "_local.open_mitv");
        jSONObject2.put(b.B, (Object) str);
        jSONObject.put("params", (Object) jSONObject2);
        L.mesh.i("%s send [open MiTv data] jsonObject: %s", "[MiotProvisionManagerWrapper]: ", String.valueOf(jSONObject));
        MiotMeshManager.getInstance().receiveBrainMessage("openMiTv", jSONObject.toJSONString());
        Log.i(DomainConfig.Mesh.DOMAIN_NAME, "send bind action");
    }

    public void resumeMesh() {
        L.mesh.i("%s resumeMesh!", "[MiotProvisionManagerWrapper]: ");
        MiotMeshManager.getInstance().start();
        c();
    }

    public void stopMesh() {
        L.mesh.i("%s stopMesh!", "[MiotProvisionManagerWrapper]: ");
        MiotMeshManager.getInstance().stop();
        EventBusRegistry.getEventBus().post(new MeshScanEvent(MeshScanEvent.MeshScanEnum.FINISH));
        EventBusRegistry.getEventBus().post(new MeshDeviceShowEvent(MeshDeviceShowEvent.MeshDeviceShowEnum.FINISH));
    }

    public void quitMesh() {
        L.mesh.i("%s quitMesh!", "[MiotProvisionManagerWrapper]: ");
        setMeshEnable(0);
        MiotMeshManager.getInstance().quit();
        BluetoothSettingManager.getInstance().closeBluetoothReal();
        EventBusRegistry.getEventBus().post(new MeshScanEvent(MeshScanEvent.MeshScanEnum.FINISH));
        EventBusRegistry.getEventBus().post(new MeshDeviceShowEvent(MeshDeviceShowEvent.MeshDeviceShowEnum.FINISH));
    }

    public static boolean isMeshEnable() {
        int keyMeshEnable = SystemSetting.getKeyMeshEnable();
        if (keyMeshEnable == -1 || keyMeshEnable == 0) {
            d = false;
            return false;
        }
        d = true;
        return keyMeshEnable == 1;
    }

    public void setMeshEnable(int i) {
        if (i == -1 || i == 0) {
            d = false;
        } else {
            d = true;
        }
        SystemSetting.setKeyMeshEnable(i);
        MiotMeshUtils.reportMeshSwitchToMijia(i);
    }

    public String getMeshStatusForApp() {
        String str;
        JSONObject jSONObject = new JSONObject();
        if (this.h == MeshPairState.MeshState.MESH_REQUEST_SCAN || this.h == MeshPairState.MeshState.MESH_SCAN_IN_OPERATION) {
            str = MeshPairState.MeshState.MESH_SCAN_IN_OPERATION.name();
        } else if (this.h == MeshPairState.MeshState.MESH_REQUEST_BIND || this.h == MeshPairState.MeshState.MESH_BIND_IN_OPERATION) {
            str = MeshPairState.MeshState.MESH_BIND_IN_OPERATION.name();
        } else {
            str = this.h.name();
        }
        jSONObject.put("mesh_is_support", (Object) Boolean.valueOf(isMeshEnable()));
        jSONObject.put("mesh_is_enable", (Object) Boolean.valueOf(h()));
        jSONObject.put("mesh_status", (Object) str);
        L.mesh.i("%s mesh status info: %s", "[MiotProvisionManagerWrapper]: ", jSONObject.toString());
        return jSONObject.toString();
    }

    private boolean h() {
        int meshInitDoneResult = MiotMeshManager.getInstance().getMeshInitDoneResult();
        L.mesh.i("%s getMeshInitDoneResult: %s", "[MiotProvisionManagerWrapper]: ", Integer.valueOf(meshInitDoneResult));
        return meshInitDoneResult == 0;
    }

    private boolean i() {
        boolean isVoipActive = VoipModel.getInstance().isVoipActive();
        L.mesh.i("%s is voip Calling=%s", "[MiotProvisionManagerWrapper]: ", Boolean.valueOf(isVoipActive));
        return isVoipActive;
    }
}
