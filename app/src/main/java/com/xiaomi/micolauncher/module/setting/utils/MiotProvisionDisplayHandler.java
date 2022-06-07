package com.xiaomi.micolauncher.module.setting.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elvishew.xlog.Logger;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.xiaomi.mesh.provision.MiotBindDevicesResult;
import com.xiaomi.mesh.provision.MiotDeviceScanResult;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.NetworkMonitor;
import com.xiaomi.micolauncher.module.miot.mesh.IotDevicesBindNeedWifiActivity;
import com.xiaomi.micolauncher.module.miot.mesh.MeshPairState;
import com.xiaomi.micolauncher.module.miot.mesh.uievent.MeshDeviceShowEvent;
import com.xiaomi.micolauncher.module.miot.mesh.uievent.MeshScanEvent;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import com.xiaomi.micolauncher.skills.openplatform.controller.uievent.BluetoothControlAction;
import com.xiaomi.miot.support.SmartHomeRequestCallback;
import com.xiaomi.miot.support.core.MiotDeviceCore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/* loaded from: classes3.dex */
public class MiotProvisionDisplayHandler {
    public static final int BIND_TIMEOUT_TYPE = 1;
    public static final int SCAN_TIMEOUT_TYPE = 0;
    private Context b;
    private ArrayList<MiotMeshDeviceItem> a = new ArrayList<>();
    private Handler c = new Handler(ThreadUtil.getLightWorkHandler().getLooper()) { // from class: com.xiaomi.micolauncher.module.setting.utils.MiotProvisionDisplayHandler.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            MiotProvisionDisplayHandler.this.a(message);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public MiotProvisionDisplayHandler(Context context) {
        this.b = context;
    }

    private static int a() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append("1234567890".charAt(random.nextInt(10)));
        }
        try {
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 12345678;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Message message) {
        int i = message.what;
        MiotProvisionManagerWrapper.getInstance().setMeshState(MeshPairState.MeshState.MESH_IDLE);
        switch (i) {
            case 0:
                L.mesh.e("scan timeout type happen");
                MiotDeviceScanResult miotDeviceScanResult = (MiotDeviceScanResult) JSONObject.parseObject("{\"id\":404,\"method\":\"_local.miot_device_scan_result\", \"params\":[]}", MiotDeviceScanResult.class);
                miotDeviceScanResult.setId(a());
                b(miotDeviceScanResult);
                MiotMeshUtils.startMeshScanActivity(MeshScanEvent.MeshScanEnum.SCANNED_NO_DEVICE, this.b);
                return;
            case 1:
                L.mesh.e("bind timeout type happen");
                ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MiotProvisionDisplayHandler$w-QrHIyPRhlrwn3BdY9PJRZKXQo
                    @Override // java.lang.Runnable
                    public final void run() {
                        MiotProvisionDisplayHandler.this.b();
                    }
                });
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        if (MiotProvisionManagerWrapper.getWifiInfoBeforeBind() == null || TextUtils.isEmpty(MiotProvisionManagerWrapper.getWifiInfoBeforeBind().getSsid())) {
            L.mesh.i("没有可恢复的网络");
        } else {
            Logger logger = L.mesh;
            logger.i("恢复网络: " + MiotProvisionManagerWrapper.getWifiInfoBeforeBind().getSsid());
            WiFiUtil.enableNetwork(this.b, MiotProvisionManagerWrapper.getWifiInfoBeforeBind().getSsid());
            if (MiotProvisionManagerWrapper.isTargetWifiIsConnected(this.b)) {
                Logger logger2 = L.mesh;
                logger2.i("wifi is connected to " + MiotProvisionManagerWrapper.getWifiInfoBeforeBind().getSsid());
            } else {
                L.mesh.e("wifi is connect time out ");
            }
        }
        NetworkMonitor.getInstance().setManuallySettingWifi(false);
        EventBusRegistry.getEventBus().post(new MeshDeviceShowEvent(MeshDeviceShowEvent.MeshDeviceShowEnum.FINISH));
    }

    public ArrayList<MiotMeshDeviceItem> getMeshDevices() {
        return this.a;
    }

    public void setMeshDevices(ArrayList<MiotMeshDeviceItem> arrayList) {
        this.a = arrayList;
    }

    private void a(final Context context, final MiotBindDevicesResult miotBindDevicesResult) {
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MiotProvisionDisplayHandler$-he9GOA4jO8fcxE5EdlMpRNnrOA
            @Override // java.lang.Runnable
            public final void run() {
                MiotProvisionDisplayHandler.this.b(context, miotBindDevicesResult);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Context context, MiotBindDevicesResult miotBindDevicesResult) {
        if (MiotProvisionManagerWrapper.getWifiInfoBeforeBind() == null || TextUtils.isEmpty(MiotProvisionManagerWrapper.getWifiInfoBeforeBind().getSsid())) {
            L.mesh.w("没有可恢复的网络");
        } else {
            Logger logger = L.mesh;
            logger.i("恢复网络: " + MiotProvisionManagerWrapper.getWifiInfoBeforeBind().getSsid());
            WiFiUtil.enableNetwork(context, MiotProvisionManagerWrapper.getWifiInfoBeforeBind().getSsid());
            if (MiotProvisionManagerWrapper.isTargetWifiIsConnected(context)) {
                Logger logger2 = L.mesh;
                logger2.i("wifi is connected to " + MiotProvisionManagerWrapper.getWifiInfoBeforeBind().getSsid());
            } else {
                L.mesh.e("wifi is connect time out ");
            }
        }
        Logger logger3 = L.mesh;
        logger3.i("更新前bind结果:" + miotBindDevicesResult);
        Logger logger4 = L.mesh;
        logger4.i("更新前MeshDevice绑定结果: " + getMeshDevices());
        NetworkMonitor.getInstance().setManuallySettingWifi(false);
        MiotMeshUtils.updateDeviceBindInfo(miotBindDevicesResult, getMeshDevices());
        Logger logger5 = L.mesh;
        logger5.d("拉取前bind结果:" + miotBindDevicesResult);
        Logger logger6 = L.mesh;
        logger6.d("拉取前MeshDevice绑定结果: " + getMeshDevices());
        if (MiotProvisionManagerWrapper.getGlobalError() == MiotProvisionManagerWrapper.GLOBAL_ERROR.SUCCESS) {
            a(context, getMeshDevices());
        } else {
            L.mesh.w("%s checkBindResult setFailedBindResult", "MiotProvisionDisplayHandler");
            a(getMeshDevices());
        }
        MiotMeshUtils.updateProvisionDeviceBindInfo(miotBindDevicesResult, getMeshDevices());
        MiotMeshUtils.setMeshDevicesConnectState(miotBindDevicesResult, getMeshDevices());
        Logger logger7 = L.mesh;
        logger7.i("更新后bind结果:" + miotBindDevicesResult);
        Logger logger8 = L.mesh;
        logger8.i("更新后MeshDevice绑定结果: " + getMeshDevices());
        if (MiotProvisionManagerWrapper.getGlobalError() == MiotProvisionManagerWrapper.GLOBAL_ERROR.SUCCESS || miotBindDevicesResult.getParams().getSucceeded().size() != 0) {
            L.mesh.i("绑定到房间");
            MiotMeshUtils.setBindDeviceResultToRoom(miotBindDevicesResult);
            MiotMeshUtils.reportBindEventToBrainV3(miotBindDevicesResult);
            EventBusRegistry.getEventBus().post(new MeshPairState(MeshPairState.MeshState.MESH_BIND_COMPLETE).setExtDevices(getMeshDevices()));
            return;
        }
        EventBusRegistry.getEventBus().post(new MeshDeviceShowEvent(MeshDeviceShowEvent.MeshDeviceShowEnum.FINISH));
        ActivityLifeCycleManager.startActivityQuietly(new Intent(context.getApplicationContext(), IotDevicesBindNeedWifiActivity.class));
    }

    private void a(ArrayList<MiotMeshDeviceItem> arrayList) {
        Iterator<MiotMeshDeviceItem> it = arrayList.iterator();
        while (it.hasNext()) {
            MiotMeshDeviceItem next = it.next();
            next.setConnectState(MiotMeshDeviceItem.CONNECT_STATE_FAILED);
            next.setPullCloudFinished(true);
        }
    }

    private void a(Context context, ArrayList<MiotMeshDeviceItem> arrayList) {
        Iterator<MiotMeshDeviceItem> it = arrayList.iterator();
        while (it.hasNext()) {
            MiotMeshDeviceItem next = it.next();
            if ((next.getMiotType() == 2 && !TextUtils.isEmpty(next.getWifiMac())) || (next.getMiotType() == 0 && !TextUtils.isEmpty(next.getDid()) && !TextUtils.equals(next.getDid(), "0"))) {
                a(context, next);
            }
        }
        while (!b(arrayList)) {
            try {
                Thread.sleep(1000L);
                L.mesh.i("wait all pull finish");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean b(ArrayList<MiotMeshDeviceItem> arrayList) {
        Iterator<MiotMeshDeviceItem> it = arrayList.iterator();
        boolean z = true;
        while (it.hasNext()) {
            MiotMeshDeviceItem next = it.next();
            if ((next.getMiotType() == 2 && !TextUtils.isEmpty(next.getWifiMac())) || (next.getMiotType() == 0 && !TextUtils.isEmpty(next.getDid()) && !TextUtils.equals(next.getDid(), "0"))) {
                z &= next.isPullCloudFinished();
            }
        }
        return z;
    }

    private void a(Context context, final MiotMeshDeviceItem miotMeshDeviceItem) {
        MiotDeviceCore with = MiotDeviceCore.with(context);
        JSONObject jSONObject = new JSONObject();
        if (miotMeshDeviceItem.getMiotType() == 2 && !TextUtils.isEmpty(miotMeshDeviceItem.getWifiMac())) {
            jSONObject.put("NewDeviceMac", (Object) miotMeshDeviceItem.getWifiMac());
            jSONObject.put("bssid", (Object) WiFiUtil.getBSSID(context));
            jSONObject.put("ssid", (Object) WiFiUtil.getSSID(context));
        } else if (miotMeshDeviceItem.getMiotType() == 0 && !TextUtils.isEmpty(miotMeshDeviceItem.getDid()) && !TextUtils.equals(miotMeshDeviceItem.getDid(), "0")) {
            jSONObject.put("did", (Object) miotMeshDeviceItem.getDid());
        }
        for (int i = 0; i < 10 && !miotMeshDeviceItem.isPullCloudFinished(); i++) {
            String jSONObject2 = jSONObject.toString();
            Logger logger = L.mesh;
            logger.i("send request:" + jSONObject2);
            with.sendSmartHomeRequest("/home/device_new", "POST", jSONObject2, new SmartHomeRequestCallback() { // from class: com.xiaomi.micolauncher.module.setting.utils.MiotProvisionDisplayHandler.2
                @Override // com.xiaomi.miot.support.SmartHomeRequestCallback
                public void onSuccess(String str) {
                    JSONArray jSONArray;
                    Logger logger2 = L.mesh;
                    logger2.i("item: " + miotMeshDeviceItem.getDid() + ", result: " + str);
                    if (str.contains("did") && (jSONArray = JSONObject.parseObject(str).getJSONArray("list")) != null && jSONArray.size() > 0) {
                        miotMeshDeviceItem.setDid(jSONArray.getJSONObject(0).getLong("did").toString());
                        miotMeshDeviceItem.setConnectState(MiotMeshDeviceItem.CONNECT_STATE_SUCCEEDED);
                        miotMeshDeviceItem.setPullCloudFinished(true);
                    }
                }

                @Override // com.xiaomi.miot.support.SmartHomeRequestCallback
                public void onFailed(int i2, String str) {
                    Logger logger2 = L.mesh;
                    logger2.e("pull device_new error:" + str);
                }
            });
            try {
                if (!miotMeshDeviceItem.isPullCloudFinished()) {
                    Thread.sleep(SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!miotMeshDeviceItem.isPullCloudFinished()) {
            miotMeshDeviceItem.setConnectState(MiotMeshDeviceItem.CONNECT_STATE_FAILED);
            miotMeshDeviceItem.setPullCloudFinished(true);
        }
    }

    public void handleBindDeviceResult(final MiotBindDevicesResult miotBindDevicesResult) {
        this.c.post(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MiotProvisionDisplayHandler$P4r8c9nTUdIxUsUrgucA1h9eAXg
            @Override // java.lang.Runnable
            public final void run() {
                MiotProvisionDisplayHandler.this.b(miotBindDevicesResult);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void b(MiotBindDevicesResult miotBindDevicesResult) {
        if (miotBindDevicesResult != null) {
            a(this.b, miotBindDevicesResult);
        } else {
            L.mesh.e("provisionBindDeviceResult is null");
        }
    }

    public void handleBindAck(final String str) {
        this.c.post(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MiotProvisionDisplayHandler$1x2C_HTIzU3bFTpsPLysX2xzhCg
            @Override // java.lang.Runnable
            public final void run() {
                MiotProvisionDisplayHandler.this.d(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void d(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (MiotMeshUtils.getAckResponse(str)) {
                L.mesh.i("开始Bind设备!");
            } else if (str.contains("bind timeout")) {
                triggerTimeoutNow();
            }
            EventBusRegistry.getEventBus().post(new MeshPairState(MeshPairState.MeshState.MESH_BIND_IN_OPERATION));
        }
    }

    public void triggerTimeoutNow() {
        if (this.c.hasMessages(0)) {
            this.c.removeMessages(0);
            this.c.sendMessage(this.c.obtainMessage(0));
            L.mesh.e("finish scan activity now ");
        } else if (this.c.hasMessages(1)) {
            this.c.removeMessages(1);
            this.c.sendMessage(this.c.obtainMessage(1));
            L.mesh.e("finish bind activity now ");
        }
    }

    public void triggerTimeout(int i, long j) {
        cleanTimeout();
        this.c.sendMessageDelayed(this.c.obtainMessage(i), j);
        L.mesh.i("MiotProvisionDisplayHandler trigger timeout=%s", Long.valueOf(j));
    }

    public void cleanTimeout() {
        if (this.c.hasMessages(0)) {
            this.c.removeMessages(0);
            L.mesh.i("MiotProvisionDisplayHandler clean scan timeout ");
        } else if (this.c.hasMessages(1)) {
            this.c.removeMessages(1);
            L.mesh.i("MiotProvisionDisplayHandler clean bind timeout ");
        }
    }

    public void handleScanResult(final MiotDeviceScanResult miotDeviceScanResult) {
        this.c.post(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MiotProvisionDisplayHandler$EKm4lAMW99BzkHB5c1dvgzYKHg0
            @Override // java.lang.Runnable
            public final void run() {
                MiotProvisionDisplayHandler.this.b(miotDeviceScanResult);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void b(MiotDeviceScanResult miotDeviceScanResult) {
        if (miotDeviceScanResult != null) {
            MiotMeshUtils.reportScanEventToBrainV3(miotDeviceScanResult);
            if (miotDeviceScanResult.getParams().size() == 0) {
                MiotMeshUtils.startMeshScanActivity(MeshScanEvent.MeshScanEnum.SCANNED_NO_DEVICE, this.b);
                MiotProvisionManagerWrapper.getInstance().setMeshState(MeshPairState.MeshState.MESH_IDLE);
                return;
            }
            ArrayList<MiotMeshDeviceItem> startMeshDevicesShowActivity = MiotMeshUtils.startMeshDevicesShowActivity(miotDeviceScanResult, this.b);
            if (startMeshDevicesShowActivity == null) {
                startMeshDevicesShowActivity = MiotMeshUtils.getMiotMeshDevices(miotDeviceScanResult);
            }
            setMeshDevices(startMeshDevicesShowActivity);
            return;
        }
        L.mesh.d("provisionScanResult is null");
    }

    public void handleScanAck(final String str) {
        this.c.post(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$MiotProvisionDisplayHandler$09iymqcNowvj9hSPQ2TI1sbS0kU
            @Override // java.lang.Runnable
            public final void run() {
                MiotProvisionDisplayHandler.this.c(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void c(String str) {
        if (!TextUtils.isEmpty(str) && MiotMeshUtils.getAckResponse(str)) {
            L.mesh.i("开始扫描设备!");
            if (BluetoothSettingManager.getInstance() != null) {
                BluetoothSettingManager.getInstance().disconnectBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_DISCONNECT_BY_CODE);
            }
            MiotMeshUtils.startMeshScanActivity(MeshScanEvent.MeshScanEnum.SCANNING, this.b);
        }
    }
}
