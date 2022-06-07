package com.xiaomi.micolauncher.skills.mitv;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.TVController;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.stat.MicoStatUtils;
import com.xiaomi.micolauncher.skills.mitv.ActionDeviceInfo;
import com.xiaomi.micolauncher.skills.mitv.MiTVDevices;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.passport.StatConstants;
import io.reactivex.functions.Consumer;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.ArrayUtils;
import org.fourthline.cling.model.meta.DeviceDetails;
import org.fourthline.cling.model.meta.ManufacturerDetails;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.RemoteDeviceIdentity;
import org.fourthline.cling.model.meta.RemoteService;
import org.fourthline.cling.model.types.UDN;

/* loaded from: classes3.dex */
public class MiTvManager {
    static final /* synthetic */ boolean a = !MiTvManager.class.desiredAssertionStatus();
    @SuppressLint({"StaticFieldLeak"})
    private static final MiTvManager b = new MiTvManager();
    private static byte[] f = {85, 72, 10, 0, 0, 0, 0, 16};
    private static byte[] g = {39, 17, 4, 1};
    private static byte[] h = {0, 0, 0, 0};
    private static final long q = TimeUnit.SECONDS.toMillis(10);
    private Context c;
    private Socket k;
    private OutputStream l;
    private BluetoothLeAdvertiser n;
    private int d = 0;
    private int e = 0;
    private MiTVDevices i = new MiTVDevices();
    private c j = new c();
    private d m = null;
    private volatile boolean o = false;
    private final Handler p = new Handler(Looper.getMainLooper()) { // from class: com.xiaomi.micolauncher.skills.mitv.MiTvManager.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1001) {
                L.dlna.i("%s receive MSG_BLE_ADV_FAIL", "[MiTvManager]: ");
                MiTvManager.this.a("bleAdv", TVController.TVControllerErrorType.OPEN_TV_BLE_FAIL, false);
                MicoStatUtils.postStatLog("tvControl", "bleAdv", "fail");
            }
        }
    };
    private AdvertiseCallback r = new AdvertiseCallback() { // from class: com.xiaomi.micolauncher.skills.mitv.MiTvManager.2
        @Override // android.bluetooth.le.AdvertiseCallback
        public void onStartSuccess(AdvertiseSettings advertiseSettings) {
            L.dlna.i("%s AdvertiseCallback 广播成功 %s", "[MiTvManager]: ", advertiseSettings.toString());
            MiTvManager.this.p.removeMessages(1001);
            MiTvManager.this.o = true;
            MiTvManager.this.a("bleAdv", (TVController.TVControllerErrorType) null, true);
            MicoStatUtils.postStatLog("tvControl", "bleAdv", StatConstants.BIND_SUCCESS);
            L.dlna.i("%S onStartSuccess,remove MSG_BLE_ADV_FAIL", "[MiTvManager]: ");
            super.onStartSuccess(advertiseSettings);
        }

        @Override // android.bluetooth.le.AdvertiseCallback
        public void onStartFailure(int i) {
            L.dlna.e("%s AdvertiseCallback 广播失败 errorCode=%s", "[MiTvManager]: ", Integer.valueOf(i));
            if (!MiTvManager.this.o) {
                L.dlna.i("%s onStartFailure,send MSG_BLE_ADV_FAIL in %d ms", "[MiTvManager]: ", 200L);
                MiTvManager.this.p.removeMessages(1001);
                Message obtain = Message.obtain();
                obtain.what = 1001;
                MiTvManager.this.p.sendMessageDelayed(obtain, 200L);
            }
            super.onStartFailure(i);
        }
    };

    /* loaded from: classes3.dex */
    public static class MiTvFilter {
        final String a = "urn:mi-com:service:RController:1";
        final String b = "Xiaomi";
        final String c = "http://www.xiaomi.com/";
    }

    public static MiTvManager getInstance() {
        return b;
    }

    public void start(Context context) {
        this.c = context;
        k();
        L.dlna.i("%s start!", "[MiTvManager]: ");
    }

    private boolean a(RemoteService[] remoteServiceArr) {
        MiTvFilter miTvFilter = new MiTvFilter();
        for (RemoteService remoteService : remoteServiceArr) {
            String serviceType = remoteService.getServiceType().toString();
            L.dlna.i("%s serviceType: %s", "[MiTvManager]: ", serviceType);
            miTvFilter.getClass();
            if (serviceType.equals("urn:mi-com:service:RController:1")) {
                L.dlna.i("%s checked ServiceType: %s", "[MiTvManager]: ", serviceType);
                return true;
            }
        }
        return false;
    }

    @SuppressLint({"CheckResult"})
    private MiTVDevices.DeviceListBean a(RemoteDevice remoteDevice) {
        UDN udn;
        ManufacturerDetails manufacturerDetails;
        L.dlna.i("%s isCheckedMiTvDevice ==>", "[MiTvManager]: ");
        RemoteDeviceIdentity identity = remoteDevice.getIdentity();
        DeviceDetails details = remoteDevice.getDetails();
        RemoteService[] services = remoteDevice.getServices();
        MiTvFilter miTvFilter = new MiTvFilter();
        if (!(identity == null || (udn = identity.getUdn()) == null)) {
            String udn2 = udn.toString();
            if (TextUtils.isEmpty(udn2)) {
                L.dlna.i("%s udnSting is null", "[MiTvManager]: ");
                return null;
            } else if (services != null && !a(services)) {
                if (details != null) {
                    String friendlyName = details.getFriendlyName();
                    if (TextUtils.isEmpty(friendlyName)) {
                        L.dlna.d("%s friendlyName is null", "[MiTvManager]: ");
                    } else {
                        L.dlna.d("%s friendlyName: %s", "[MiTvManager]: ", friendlyName);
                    }
                }
                return null;
            } else if (!(details == null || (manufacturerDetails = details.getManufacturerDetails()) == null)) {
                String manufacturer = manufacturerDetails.getManufacturer();
                URI manufacturerURI = manufacturerDetails.getManufacturerURI();
                String friendlyName2 = details.getFriendlyName();
                if (TextUtils.isEmpty(friendlyName2)) {
                    L.dlna.d("%s friendlyName is null", "[MiTvManager]: ");
                    return null;
                }
                URL baseURL = details.getBaseURL();
                if (baseURL == null) {
                    L.dlna.d("%s baseURL is null !", "[MiTvManager]: ");
                    return null;
                }
                String host = baseURL.getHost();
                if (!TextUtils.isEmpty(manufacturer) && manufacturerURI != null && !TextUtils.isEmpty(host)) {
                    String uri = manufacturerURI.toString();
                    if (TextUtils.isEmpty(uri)) {
                        L.dlna.d("%s uriSting is null !", "[MiTvManager]: ");
                        return null;
                    }
                    Logger logger = L.dlna;
                    miTvFilter.getClass();
                    logger.d("%s getManufacturer: %s, man: %s", "[MiTvManager]: ", manufacturer, "Xiaomi");
                    Logger logger2 = L.dlna;
                    miTvFilter.getClass();
                    logger2.d("%s uriSting: %s, manufacturerURL: %s", "[MiTvManager]: ", uri, "http://www.xiaomi.com/");
                    miTvFilter.getClass();
                    if (manufacturer.equals("Xiaomi")) {
                        miTvFilter.getClass();
                        if (uri.equals("http://www.xiaomi.com/")) {
                            L.dlna.i("%s ====> Get MiTV device name: %s", "[MiTvManager]: ", friendlyName2);
                            MiTVDevices.DeviceListBean deviceListBean = new MiTVDevices.DeviceListBean();
                            deviceListBean.setName(friendlyName2);
                            deviceListBean.setHost(host);
                            deviceListBean.setUdn(udn2);
                            deviceListBean.setOnline(1);
                            deviceListBean.setSelected(0);
                            return deviceListBean;
                        }
                    }
                }
            }
        }
        L.dlna.w("%s isCheckedMiTvDevice ==> end, It's not a mitv", "[MiTvManager]: ");
        return null;
    }

    public boolean checkMiTvDevice(RemoteDevice remoteDevice) {
        MiTVDevices.DeviceListBean a2 = a(remoteDevice);
        if (a2 == null) {
            return false;
        }
        L.dlna.i("%s added Device: %s", "[MiTvManager]: ", a2.displayToString());
        a(a(a2.getUdn()), a2);
        return true;
    }

    private int a(String str) {
        ArrayList arrayList = new ArrayList(this.i.getDevice_list());
        for (int i = 0; i < arrayList.size(); i++) {
            if (((MiTVDevices.DeviceListBean) arrayList.get(i)).getUdn().equals(str)) {
                L.dlna.i("%s discovery device index: %s, device info: %s", "[MiTvManager]: ", Integer.valueOf(i), ((MiTVDevices.DeviceListBean) arrayList.get(i)).displayToString());
                return i;
            }
        }
        L.dlna.i("%s not discovery device", "[MiTvManager]: ");
        return -1;
    }

    @SuppressLint({"CheckResult"})
    private void a(final int i, final MiTVDevices.DeviceListBean deviceListBean) {
        L.dlna.i("%s refreshMiTvDeviceList index: %s", "[MiTvManager]: ", Integer.valueOf(i));
        ApiManager.rawService.getMiTvActionDeviceInfo(deviceListBean.getHost()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.mitv.-$$Lambda$MiTvManager$s3Lwt0CguEKSnsHGFy6ufkbrhao
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiTvManager.this.a(deviceListBean, i, (ResponseBody) obj);
            }
        }, $$Lambda$MiTvManager$rTqgaf9J3hklwszrhojYHW7UHk.INSTANCE);
    }

    public /* synthetic */ void a(MiTVDevices.DeviceListBean deviceListBean, int i, ResponseBody responseBody) throws Exception {
        String string = responseBody.string();
        L.dlna.i("%s getMiTvActionDeviceInfo host=%s resultStr: %s", "[MiTvManager]: ", deviceListBean.getHost(), string);
        ActionDeviceInfo actionDeviceInfo = (ActionDeviceInfo) Gsons.getGson().fromJson(string, (Class<Object>) ActionDeviceInfo.class);
        if (actionDeviceInfo != null) {
            ActionDeviceInfo.DataBean data = actionDeviceInfo.getData();
            if (data != null) {
                String bluetooth_mac = data.getBluetooth_mac();
                ActionDeviceInfo.DataBean.AbilityBean ability = data.getAbility();
                if (bluetooth_mac != null && ability != null) {
                    deviceListBean.setBluetooth_mac(bluetooth_mac);
                    deviceListBean.setVoice(ability.getVoice());
                } else if (data.getBluetooth_mac() == null) {
                    L.dlna.e("%s getBluetooth_mac is null", "[MiTvManager]: ");
                } else if (data.getAbility() == null) {
                    L.dlna.e("%s getAbility is null", "[MiTvManager]: ");
                }
                if (i == -1) {
                    if (this.i.getDevice_list().size() == 0) {
                        deviceListBean.setSelected(1);
                    }
                    this.i.getDevice_list().add(deviceListBean);
                    L.dlna.i("%s getMiTvActionDeviceInfo new MiTv Device, save into sp", "[MiTvManager]: ");
                    a();
                } else if (i >= 0) {
                    MiTVDevices.DeviceListBean deviceListBean2 = this.i.getDevice_list().get(i);
                    if (a(deviceListBean, deviceListBean2)) {
                        L.dlna.i("%s isNeedUpdate, ", "[MiTvManager]: ");
                        deviceListBean.setSelected(deviceListBean2.getSelected());
                        this.i.getDevice_list().remove(i);
                        this.i.getDevice_list().add(deviceListBean);
                        a();
                    } else {
                        deviceListBean2.setOnline(1);
                        L.dlna.i("%s not refresh device: %s", "[MiTvManager]: ", deviceListBean.getName());
                    }
                }
            } else {
                L.dlna.e("%s data is null", "[MiTvManager]: ");
                return;
            }
        }
        l();
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.dlna.e("%s ==== %s", "[MiTvManager]: ", th.getMessage());
    }

    private boolean a(MiTVDevices.DeviceListBean deviceListBean, MiTVDevices.DeviceListBean deviceListBean2) {
        boolean z;
        if (!TextUtils.isEmpty(deviceListBean2.getName())) {
            if (!deviceListBean2.getName().equals(deviceListBean.getName())) {
                z = true;
            }
            z = false;
        } else {
            if (!TextUtils.isEmpty(deviceListBean.getName())) {
                z = true;
            }
            z = false;
        }
        if (!z) {
            if (!TextUtils.isEmpty(deviceListBean2.getHost())) {
                if (!deviceListBean2.getHost().equals(deviceListBean.getHost())) {
                    z = true;
                }
            } else if (!TextUtils.isEmpty(deviceListBean.getHost())) {
                z = true;
            }
        }
        if (!z && deviceListBean2.getVoice() != deviceListBean.getVoice()) {
            z = true;
        }
        if (!z) {
            if (!TextUtils.isEmpty(deviceListBean2.getBluetooth_mac())) {
                if (!deviceListBean2.getBluetooth_mac().equals(deviceListBean.getBluetooth_mac())) {
                    return true;
                }
            } else if (!TextUtils.isEmpty(deviceListBean.getBluetooth_mac())) {
                return true;
            }
        }
        return z;
    }

    public String miTvGetLookupList() {
        return a(0);
    }

    private void a() {
        SharedPreferences.Editor edit = this.c.getSharedPreferences("mitv_devices", 0).edit();
        String a2 = a(1);
        L.dlna.i("%s saveMiTvConfig2Sp dev info: %s", "[MiTvManager]: ", a2);
        edit.clear();
        edit.putString("dev", a2);
        edit.apply();
    }

    public boolean hasTvDeviceListInSp() {
        String string = this.c.getSharedPreferences("mitv_devices", 0).getString("dev", null);
        L.dlna.i("hasTvDeviceListInSp,dev:%s", string);
        return !TextUtils.isEmpty(string);
    }

    private String a(int i) {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        List<MiTVDevices.DeviceListBean> device_list = this.i.getDevice_list();
        L.dlna.i("%s lookUpList type: %s, list size: %s", "[MiTvManager]: ", Integer.valueOf(i), Integer.valueOf(device_list.size()));
        for (MiTVDevices.DeviceListBean deviceListBean : device_list) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("udn", (Object) deviceListBean.getUdn());
            jSONObject2.put("name", (Object) deviceListBean.getName());
            jSONObject2.put(com.xiaomi.onetrack.api.b.E, (Object) deviceListBean.getHost());
            jSONObject2.put("selected", (Object) Integer.valueOf(deviceListBean.getSelected()));
            if (i == 0) {
                jSONObject2.put("online", (Object) Integer.valueOf(deviceListBean.getOnline()));
                if (deviceListBean.getVoice() == 2) {
                    jSONObject2.put("wakeup_en", (Object) 1);
                } else {
                    jSONObject2.put("wakeup_en", (Object) 0);
                }
            } else if (i == 1) {
                jSONObject2.put("voice", (Object) Integer.valueOf(deviceListBean.getVoice()));
                jSONObject2.put("bluetooth_mac", (Object) deviceListBean.getBluetooth_mac());
            }
            jSONArray.add(jSONObject2);
        }
        jSONObject.put("device_list", (Object) jSONArray);
        jSONObject.put("enable", Integer.valueOf(this.d));
        if (i == 1) {
            jSONObject.put("tv_push", Integer.valueOf(this.e));
        }
        return jSONObject.toString();
    }

    public int selectMiTvDevice(String str) {
        for (MiTVDevices.DeviceListBean deviceListBean : this.i.getDevice_list()) {
            if (deviceListBean.getUdn().equals(str)) {
                deviceListBean.setSelected(1);
            } else {
                deviceListBean.setSelected(0);
            }
        }
        a();
        return 0;
    }

    public void offLineMiTvDevice(RemoteDevice remoteDevice) {
        MiTVDevices.DeviceListBean a2 = a(remoteDevice);
        if (a2 != null) {
            L.dlna.i("%s offLineMiTvDevice: %s", "[MiTvManager]: ", a2.displayToString());
            int a3 = a(a2.getUdn());
            if (a3 >= 0) {
                L.dlna.i("offLineMiTvDevice,set offline for index:%d", Integer.valueOf(a3));
                this.i.getDevice_list().get(a3).setOnline(0);
                a();
            }
            l();
        }
    }

    private int b() {
        List<MiTVDevices.DeviceListBean> device_list = this.i.getDevice_list();
        if (device_list == null) {
            return -1;
        }
        for (int i = 0; i < device_list.size(); i++) {
            if (device_list.get(i).getSelected() == 1) {
                L.dlna.i("%s discovery selected device num: %s", "[MiTvManager]: ", Integer.valueOf(i));
                return i;
            }
        }
        return -1;
    }

    private MiTVDevices.DeviceListBean c() {
        List<MiTVDevices.DeviceListBean> device_list = this.i.getDevice_list();
        if (device_list == null) {
            return null;
        }
        for (int i = 0; i < device_list.size(); i++) {
            if (device_list.get(i).getSelected() == 1) {
                L.dlna.i("%s discovery selected device num: %s", "[MiTvManager]: ", Integer.valueOf(i));
                return device_list.get(i);
            }
        }
        return null;
    }

    public TVController.State getTVControllerState() {
        TVController.State state = new TVController.State();
        boolean z = true;
        L.dlna.i("miTvGetTvStatus:%d", Integer.valueOf(miTvGetTvStatus()));
        if (miTvGetTvStatus() <= 0) {
            z = false;
        }
        state.setTvBinded(z);
        MiTVDevices.DeviceListBean c2 = c();
        if (c2 != null) {
            state.setBluetoothMac(c2.getBluetooth_mac());
            state.setName(c2.getName());
        }
        state.setTvNum(miTvGetTvStatus());
        return state;
    }

    public void queryMiTv(String str, String str2) {
        L.dlna.i("%s queryMiTv info: %s, action: %s", "[MiTvManager]: ", str, str2);
        d dVar = this.m;
        if (dVar != null && !dVar.a()) {
            L.dlna.w("%s ignore query %s %s", "[MiTvManager]: ", str, str2);
        } else if (a || str != null) {
            String m = m();
            if (!TextUtils.isEmpty(m)) {
                this.m = new d(m, str);
                ThreadUtil.getIoThreadPool().submit(this.m);
                return;
            }
            L.dlna.d("%s can't get selected device !!", "[MiTvManager]: ");
        } else {
            throw new AssertionError();
        }
    }

    public void d() {
        Socket socket = this.k;
        if (socket != null && socket.isConnected()) {
            try {
                this.k.shutdownOutput();
            } catch (IOException e2) {
                L.dlna.e("socket shutdownOutput error: ", e2);
            }
            try {
                this.k.close();
            } catch (IOException e3) {
                L.dlna.e("socket close error: ", e3);
            }
            this.k = null;
        }
    }

    public void b(String str) {
        Socket socket = this.k;
        if (socket != null && socket.isConnected() && this.l != null) {
            String c2 = c(str);
            L.dlna.i("%s sendMiTvQueryInfo info：%s", "[MiTvManager]: ", c2);
            DataOutputStream dataOutputStream = new DataOutputStream(this.l);
            L.dlna.i("%s result length: %s", "[MiTvManager]: ", Integer.valueOf(c2.getBytes().length));
            e eVar = new e();
            eVar.b = new TvAidlHeader(f, c2.getBytes().length + 8, g);
            this.j.getClass();
            eVar.a = new a((byte) 2, (byte) 1, (byte) 0, (byte) 5, h);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                new DataOutputStream(byteArrayOutputStream).write(c2.getBytes());
            } catch (IOException e2) {
                L.dlna.e("dataOutputStream write error: ", e2);
            }
            try {
                this.l.write(ArrayUtils.addAll(ArrayUtils.addAll(eVar.b.a(), eVar.a.a()), byteArrayOutputStream.toByteArray()));
            } catch (IOException e3) {
                L.dlna.e("outputStream write error: ", e3);
            }
            try {
                this.l.flush();
                dataOutputStream.flush();
                if (!str.contains("音箱控制电视功能登录引导")) {
                    a("tvSocket", (TVController.TVControllerErrorType) null, true);
                    MicoStatUtils.postStatLog("tvControl", "tvSocket", StatConstants.BIND_SUCCESS);
                }
            } catch (IOException e4) {
                L.dlna.e("outputStream flush error: ", e4);
                if (!str.contains("音箱控制电视功能登录引导")) {
                    a("tvSocket", TVController.TVControllerErrorType.OPEN_TV_SOCKET_FAIL, false);
                    MicoStatUtils.postStatLog("tvControl", "tvSocket", "fail");
                }
            }
        }
    }

    public void a(String str, TVController.TVControllerErrorType tVControllerErrorType, boolean z) {
        if (tVControllerErrorType == null) {
            L.dlna.i("%s sendTvControlEvent,tvControlType:%s", "[MiTvManager]: ", str);
        } else {
            L.dlna.i("%s sendTvControlEvent,tvControlType:%s,error type:%s,isSuccess:%s", "[MiTvManager]: ", str, tVControllerErrorType.toString(), Boolean.valueOf(z));
        }
        TVController.StateReport stateReport = new TVController.StateReport();
        if (tVControllerErrorType != null) {
            stateReport.setControlError(tVControllerErrorType);
        }
        stateReport.setIsSuccess(z);
        SpeechManager.getInstance().sendEventRequest(APIUtils.buildEvent(stateReport));
    }

    private String c(String str) {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        jSONObject3.put("query", (Object) str);
        jSONArray.add(jSONObject3);
        jSONObject2.put("queries", (Object) jSONArray);
        jSONObject.put(com.xiaomi.onetrack.api.b.I, (Object) jSONObject2);
        return jSONObject.toString();
    }

    public void e() {
        L.dlna.i("%s sendMiTvAsrStart++++", "[MiTvManager]: ");
        this.j.getClass();
        b bVar = new b(0, (byte) 2, (byte) 4);
        Socket socket = this.k;
        if (socket != null && socket.isConnected()) {
            a(bVar.c, bVar.b);
        }
    }

    public void f() {
        this.j.getClass();
        b bVar = new b(0, (byte) 2, (byte) 6);
        Socket socket = this.k;
        if (socket != null && socket.isConnected()) {
            L.dlna.i("%s sendMiTvAsrStartWithoutView++++", "[MiTvManager]: ");
            a(bVar.c, bVar.b);
        }
    }

    public void g() {
        this.j.getClass();
        b bVar = new b(0, (byte) 2, (byte) 2);
        Socket socket = this.k;
        if (socket != null && socket.isConnected()) {
            L.dlna.i("%s sendMiTvAsrStop++++", "[MiTvManager]: ");
            a(bVar.c, bVar.b);
        }
    }

    private void a(byte b2, byte b3) {
        if (this.l != null) {
            e eVar = new e();
            eVar.b = new TvAidlHeader(f, 8, g);
            eVar.a = new a(b3, (byte) 1, (byte) 0, b2, h);
            try {
                byte[] addAll = ArrayUtils.addAll(eVar.b.a(), eVar.a.a());
                L.dlna.d("%s tvAidlHeader len: %s, micoHeader len: %s, result len: %s", "[MiTvManager]: ", Integer.valueOf(eVar.b.a().length), Integer.valueOf(eVar.a.a().length), Integer.valueOf(addAll.length));
                this.l.write(addAll);
            } catch (IOException e2) {
                L.dlna.e("outputStream.write error: ", e2);
            }
            try {
                this.l.flush();
            } catch (IOException e3) {
                L.dlna.e("outputStream.flush error: ", e3);
            }
        }
    }

    /* loaded from: classes3.dex */
    public class e {
        a a;
        TvAidlHeader b;

        e() {
            MiTvManager.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public static class a {
        byte a;
        byte b;
        byte c;
        byte d;
        byte[] e;

        a(byte b, byte b2, byte b3, byte b4, byte[] bArr) {
            this.a = b;
            this.b = b2;
            this.c = b3;
            this.d = b4;
            this.e = bArr;
        }

        byte[] a() {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeByte(this.a);
                dataOutputStream.writeByte(this.b);
                dataOutputStream.writeByte(this.c);
                dataOutputStream.writeByte(this.d);
                dataOutputStream.write(this.e);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                L.dlna.e("dataOutputStream.write error: ", e);
                return null;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class TvAidlHeader {
        byte[] a;
        int b;
        byte[] c;

        TvAidlHeader(byte[] bArr, int i, byte[] bArr2) {
            this.a = bArr;
            this.b = i;
            this.c = bArr2;
        }

        byte[] a() {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.write(this.a);
                dataOutputStream.writeInt(this.b);
                dataOutputStream.write(this.c);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                L.dlna.e("dataOutputStream.write error: ", e);
                return null;
            }
        }
    }

    public void wakeUpMiTv(String str) {
        int i;
        L.dlna.i("%s wakeUpMiTv", "[MiTvManager]: ");
        if (TextUtils.isEmpty(str)) {
            L.dlna.i("%s udn is null", "[MiTvManager]: ");
            i = b();
        } else {
            i = a(str);
        }
        b(i);
    }

    private void b(int i) {
        if (i < 0) {
            L.dlna.e("%s num < 0", "[MiTvManager]: ");
            return;
        }
        String bluetooth_mac = this.i.getDevice_list().get(i).getBluetooth_mac();
        if (TextUtils.isEmpty(bluetooth_mac)) {
            L.dlna.e("%s bluetooth_mac is null!", "[MiTvManager]: ");
        } else {
            openMiTv(bluetooth_mac);
        }
    }

    public void openMiTv(String str) {
        String[] split = str.split(Constants.COLON_SEPARATOR);
        String format = String.format("00%s%s%s-%s%s-%s01-00ff-030501020100", split[0], split[1], split[2], split[3], split[4], split[5]);
        L.dlna.i("%s 本地控制打开电视 mac: %s, strArr: %s, uuid: %s", "[MiTvManager]: ", str, Arrays.toString(split), format);
        d(format);
        j();
        MicoStatUtils.postStatLog("tvControl", "openTv", "ble");
    }

    private BluetoothLeAdvertiser h() {
        return BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();
    }

    private void d(String str) {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            L.dlna.i("%s enable BluetoothAdapter", "[MiTvManager]: ");
            BluetoothAdapter.getDefaultAdapter().enable();
        } else {
            L.dlna.d("%s BluetoothAdapter is enabled!", "[MiTvManager]: ");
        }
        if (!BluetoothAdapter.getDefaultAdapter().isLeEnabled()) {
            L.dlna.i("%s enable BLE !", "[MiTvManager]: ");
            BluetoothAdapter.getDefaultAdapter().enableBLE();
        } else {
            L.dlna.d("%s BLE is enabled!", "[MiTvManager]: ");
        }
        L.dlna.d("%s uuid: %s", "[MiTvManager]: ", str);
        L.dlna.i("%s start BleAdvertising", "[MiTvManager]: ");
        this.n = h();
        if (this.n == null) {
            L.dlna.e("%s bleAdvertiser is null", "[MiTvManager]: ");
            return;
        }
        AdvertiseSettings build = new AdvertiseSettings.Builder().setAdvertiseMode(2).setTxPowerLevel(3).setConnectable(true).build();
        AdvertiseData build2 = new AdvertiseData.Builder().setIncludeDeviceName(false).setIncludeTxPowerLevel(false).addServiceUuid(ParcelUuid.fromString(str)).build();
        this.o = false;
        this.n.startAdvertisingWithInterval(build, build2, null, 32, this.r);
    }

    private void i() {
        L.dlna.i("%s stop BleAdvertising", "[MiTvManager]: ");
        BluetoothLeAdvertiser bluetoothLeAdvertiser = this.n;
        if (bluetoothLeAdvertiser != null) {
            bluetoothLeAdvertiser.stopAdvertising(this.r);
        }
    }

    private void j() {
        L.dlna.i("%s setWaitTimer", "[MiTvManager]: ");
        this.p.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.skills.mitv.-$$Lambda$MiTvManager$z74HU1Qa0_y4QVHyH1_YsaEMkOQ
            @Override // java.lang.Runnable
            public final void run() {
                MiTvManager.this.n();
            }
        }, q);
    }

    public /* synthetic */ void n() {
        L.dlna.i("%s waitTimer timeout !", "[MiTvManager]: ");
        i();
    }

    public int miTvGetTvStatus() {
        int size = this.i.getDevice_list().size();
        L.dlna.d("%s MiTvGetTvStatus size: %s", "[MiTvManager]: ", Integer.valueOf(size));
        return size == 0 ? 0 : 1;
    }

    private void k() {
        String string = this.c.getSharedPreferences("mitv_devices", 0).getString("dev", null);
        if (!TextUtils.isEmpty(string)) {
            L.dlna.d("%s 找到本地SP记录的MiTv设备信息 dev=%s", "[MiTvManager]: ", string);
            this.i = (MiTVDevices) JSONObject.parseObject(string, MiTVDevices.class);
            MiTVDevices miTVDevices = this.i;
            if (miTVDevices != null) {
                this.e = miTVDevices.getTv_push();
                this.d = this.i.getEnable();
                return;
            }
            return;
        }
        L.dlna.w("%s 本地SP未找到MiTv设备信息", "[MiTvManager]: ");
    }

    private void l() {
        if (DebugHelper.isDebugVersion()) {
            L.dlna.i("%s display MiTVDevicesList: %s", "[MiTvManager]: ", Gsons.getGson().toJson(this.i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class d implements Runnable {
        static final /* synthetic */ boolean a = !MiTvManager.class.desiredAssertionStatus();
        private String c;
        private String d;
        private volatile boolean e;

        d(String str, String str2) {
            MiTvManager.this = r1;
            this.c = str;
            this.d = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!a && this.c == null) {
                throw new AssertionError();
            } else if (a || this.d != null) {
                this.e = false;
                if (!MiTvManager.this.e(this.c)) {
                    L.dlna.e("%s connect socket failed!", "[MiTvManager]: ");
                    if (!this.d.contains("音箱控制电视功能登录引导")) {
                        MiTvManager.this.a("tvSocket", TVController.TVControllerErrorType.OPEN_TV_SOCKET_FAIL, false);
                        MicoStatUtils.postStatLog("tvControl", "tvSocket", "fail");
                    }
                    this.e = true;
                    MiTvManager.this.d();
                    return;
                }
                if ("音箱控制电视功能登录引导".equals(this.d)) {
                    MiTvManager.this.f();
                } else {
                    MiTvManager.this.e();
                }
                MiTvManager.this.b(this.d);
                MiTvManager.this.g();
                MiTvManager.this.d();
                this.e = true;
            } else {
                throw new AssertionError();
            }
        }

        boolean a() {
            return this.e;
        }
    }

    public boolean e(String str) {
        long millis = TimeUnit.SECONDS.toMillis(2L);
        try {
            try {
                L.dlna.i("%s Try connecting to host: %s, port: %s by Socket", "[MiTvManager]: ", str, 9093);
                this.k = new Socket();
                this.k.connect(new InetSocketAddress(str, 9093), (int) millis);
                if (this.k.isConnected()) {
                    L.dlna.i("%s socket is connected !!", "[MiTvManager]: ");
                    try {
                        this.l = this.k.getOutputStream();
                    } catch (IOException e2) {
                        L.dlna.e("socket.getOutputStream error: ", e2);
                    }
                    return true;
                }
                L.dlna.e("%s socket is not connect !!", "[MiTvManager]: ");
                return false;
            } catch (IOException e3) {
                L.dlna.e("IOException: ", e3);
                return false;
            }
        } catch (IllegalArgumentException e4) {
            L.dlna.e("IllegalArgument: ", e4);
            return false;
        } catch (SocketTimeoutException e5) {
            L.dlna.e("SocketTimeout: ", e5);
            return false;
        } catch (UnknownHostException e6) {
            L.dlna.e("UnknownHost: ", e6);
            return false;
        }
    }

    private String m() {
        int b2 = b();
        if (b2 < 0) {
            return null;
        }
        String host = this.i.getDevice_list().get(b2).getHost();
        if (!TextUtils.isEmpty(host)) {
            return host;
        }
        return null;
    }

    /* loaded from: classes3.dex */
    public class b {
        int a;
        byte b;
        byte c;

        b(int i, byte b, byte b2) {
            MiTvManager.this = r1;
            this.a = i;
            this.b = b;
            this.c = b2;
        }
    }

    /* loaded from: classes3.dex */
    public class c {
        final byte a;
        final byte b;
        final byte c;
        final byte d;

        private c() {
            MiTvManager.this = r1;
            this.a = (byte) 2;
            this.b = (byte) 4;
            this.c = (byte) 5;
            this.d = (byte) 6;
        }
    }
}
