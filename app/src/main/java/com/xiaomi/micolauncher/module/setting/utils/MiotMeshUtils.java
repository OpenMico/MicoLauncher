package com.xiaomi.micolauncher.module.setting.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.DeviceBinding;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.mesh.provision.MiotBindDevicesResult;
import com.xiaomi.mesh.provision.MiotDeviceScanResult;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.SmartHomeRc4Manager;
import com.xiaomi.micolauncher.api.model.Miot;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.miot.defined.property.MeshSwitch;
import com.xiaomi.micolauncher.module.miot.mesh.MeshDevicesShowActivity;
import com.xiaomi.micolauncher.module.miot.mesh.MeshPairState;
import com.xiaomi.micolauncher.module.miot.mesh.MeshScanActivity;
import com.xiaomi.micolauncher.module.miot.mesh.MiotMeshCmdResponseSucceed;
import com.xiaomi.micolauncher.module.miot.mesh.MiotMeshIconInfo;
import com.xiaomi.micolauncher.module.miot.mesh.uievent.MeshScanEvent;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import com.xiaomi.miot.host.manager.MiotHostManager;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes3.dex */
public class MiotMeshUtils {
    private static BasePlayer a;

    public static void playSound(int i, Context context) {
        L.mesh.i("%s playSound!", "MiotMeshUtils");
        if (a == null) {
            a = new BasePlayer(AudioSource.AUDIO_SOURCE_MESH);
        }
        a.setDataSource(i, context);
        a.setLooping(true);
        a.prepareAsync();
    }

    public static void stopSound() {
        if (a != null) {
            L.mesh.i("%s stopSound!", "MiotMeshUtils");
            a.stop();
        }
    }

    public static void updateDeviceBindInfo(MiotBindDevicesResult miotBindDevicesResult, ArrayList<MiotMeshDeviceItem> arrayList) {
        a(miotBindDevicesResult.getParams().getSucceeded(), arrayList);
        a(miotBindDevicesResult.getParams().getFailed(), arrayList);
    }

    private static void a(List<MiotBindDevicesResult.ParamsBean.BaseBean> list, ArrayList<MiotMeshDeviceItem> arrayList) {
        for (MiotBindDevicesResult.ParamsBean.BaseBean baseBean : list) {
            Iterator<MiotMeshDeviceItem> it = arrayList.iterator();
            while (it.hasNext()) {
                MiotMeshDeviceItem next = it.next();
                if (!TextUtils.isEmpty(baseBean.getUuid()) && !TextUtils.isEmpty(next.getUuid()) && TextUtils.equals(baseBean.getUuid(), next.getUuid())) {
                    baseBean.setMiotType(next.getMiotType());
                    baseBean.setWifiMac(next.getWifiMac());
                    next.setDid(baseBean.getDid());
                }
                if (!TextUtils.isEmpty(baseBean.getDevssid()) && !TextUtils.isEmpty(next.getDevssid()) && TextUtils.equals(baseBean.getDevssid(), next.getDevssid())) {
                    baseBean.setMiotType(next.getMiotType());
                    baseBean.setWifiMac(next.getWifiMac());
                    next.setDid(baseBean.getDid());
                }
            }
        }
    }

    public static void setBindDeviceResultToRoom(final MiotBindDevicesResult miotBindDevicesResult) {
        SmartHomeRc4Manager.getInstance().getCurrentDeviceHomeInfo().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new DefaultObserver<Miot.Home>() { // from class: com.xiaomi.micolauncher.module.setting.utils.MiotMeshUtils.1
            /* renamed from: a */
            public void onSuccess(final Miot.Home home) {
                Logger logger = L.mesh;
                logger.i("音箱所在房间: " + home.name + ", id: " + home.id);
                for (final MiotBindDevicesResult.ParamsBean.BaseBean baseBean : miotBindDevicesResult.getParams().getSucceeded()) {
                    SmartHomeRc4Manager.getInstance().bindDeviceToRome(home.id, baseBean.getDid()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.module.setting.utils.MiotMeshUtils.1.1
                        /* renamed from: a */
                        public void onSuccess(String str) {
                            Logger logger2 = L.mesh;
                            logger2.i(baseBean.getDid() + " bind to " + home.name + " success");
                        }
                    });
                }
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                L.mesh.e("MiotMeshUtilsget RoomInfo error", th);
                super.onError(th);
            }
        });
    }

    public static String getWifiMac(MiotDeviceScanResult.ParamsBean paramsBean) {
        if (paramsBean == null || TextUtils.isEmpty(paramsBean.getMac()) || TextUtils.isEmpty(paramsBean.getWifi_mac())) {
            return "";
        }
        String wifi_mac = paramsBean.getWifi_mac();
        if (wifi_mac.length() == 4) {
            StringBuilder sb = new StringBuilder(wifi_mac);
            sb.insert(2, Constants.COLON_SEPARATOR);
            wifi_mac = sb.toString();
        }
        return paramsBean.getMac().substring(0, paramsBean.getMac().length() - 5) + wifi_mac;
    }

    public static void updateProvisionDeviceBindInfo(MiotBindDevicesResult miotBindDevicesResult, ArrayList<MiotMeshDeviceItem> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator<MiotMeshDeviceItem> it = arrayList.iterator();
        while (it.hasNext()) {
            MiotMeshDeviceItem next = it.next();
            if (next.isPullCloudFinished()) {
                arrayList2.add(next);
            }
        }
        if (arrayList2.size() > 0) {
            Iterator<MiotBindDevicesResult.ParamsBean.BaseBean> it2 = miotBindDevicesResult.getParams().getSucceeded().iterator();
            while (it2.hasNext()) {
                MiotBindDevicesResult.ParamsBean.BaseBean next2 = it2.next();
                Iterator it3 = arrayList2.iterator();
                while (it3.hasNext()) {
                    MiotMeshDeviceItem miotMeshDeviceItem = (MiotMeshDeviceItem) it3.next();
                    if (a(miotMeshDeviceItem, next2).booleanValue()) {
                        next2.setDid(miotMeshDeviceItem.getDid());
                        if (TextUtils.equals(miotMeshDeviceItem.getConnectState(), MiotMeshDeviceItem.CONNECT_STATE_FAILED)) {
                            it2.remove();
                            miotBindDevicesResult.getParams().getFailed().add(next2);
                        }
                    }
                }
            }
            Iterator<MiotBindDevicesResult.ParamsBean.BaseBean> it4 = miotBindDevicesResult.getParams().getFailed().iterator();
            while (it4.hasNext()) {
                MiotBindDevicesResult.ParamsBean.BaseBean next3 = it4.next();
                Iterator it5 = arrayList2.iterator();
                while (it5.hasNext()) {
                    MiotMeshDeviceItem miotMeshDeviceItem2 = (MiotMeshDeviceItem) it5.next();
                    if (a(miotMeshDeviceItem2, next3).booleanValue()) {
                        next3.setDid(miotMeshDeviceItem2.getDid());
                        if (TextUtils.equals(miotMeshDeviceItem2.getConnectState(), MiotMeshDeviceItem.CONNECT_STATE_SUCCEEDED)) {
                            it4.remove();
                            miotBindDevicesResult.getParams().getSucceeded().add(next3);
                        }
                    }
                }
            }
        }
    }

    public static boolean isXiaomiWifiSpeaker(MiotMeshDeviceItem miotMeshDeviceItem) {
        if (miotMeshDeviceItem == null || TextUtils.isEmpty(miotMeshDeviceItem.getModel())) {
            return false;
        }
        return miotMeshDeviceItem.getModel().contains("xiaomi.wifispeaker.") || miotMeshDeviceItem.getModel().contains("onemore.wifispeaker.");
    }

    public static boolean getAckResponse(String str) {
        return !TextUtils.isEmpty(str) && str.contains("result") && JSONObject.parseObject(str).getString("result") != null && ((MiotMeshCmdResponseSucceed) JSONObject.parseObject(str, MiotMeshCmdResponseSucceed.class)) != null;
    }

    public static void reportScanEventToBrainV3(MiotDeviceScanResult miotDeviceScanResult) {
        ArrayList arrayList = new ArrayList();
        for (MiotDeviceScanResult.ParamsBean paramsBean : miotDeviceScanResult.getParams()) {
            DeviceBinding.DeviceResult deviceResult = new DeviceBinding.DeviceResult();
            if (TextUtils.isEmpty(paramsBean.getFriendly_name()) && !TextUtils.isEmpty(paramsBean.getModel())) {
                MiotMeshIconInfo miotMeshIconInfoById = MiotProvisionManagerWrapper.getInstance().getMiotMeshIconInfoById(paramsBean.getModel());
                if (miotMeshIconInfoById == null || miotMeshIconInfoById.result == null || miotMeshIconInfoById.result.getConfigs() == null || miotMeshIconInfoById.result.getConfigs().size() <= 0) {
                    L.mesh.e("从缓存中获取设备名失败");
                } else {
                    paramsBean.setFriendly_name(miotMeshIconInfoById.result.getConfigs().get(0).getName());
                }
            }
            deviceResult.setUuid(paramsBean.getUuid());
            deviceResult.setFriendlyName(paramsBean.getFriendly_name());
            arrayList.add(deviceResult);
        }
        DeviceBinding.ScanDevicesResult scanDevicesResult = new DeviceBinding.ScanDevicesResult();
        scanDevicesResult.setSucceeded(false);
        scanDevicesResult.setDevices(arrayList);
        L.mesh.i("%s reportScanEvent: %s", "MiotMeshUtils", scanDevicesResult);
        SpeechManager.getInstance().sendEventRequest(APIUtils.buildEvent(scanDevicesResult));
    }

    public static void reportMeshSwitchToMijia(int i) {
        ArrayList arrayList = new ArrayList();
        MeshSwitch meshSwitch = new MeshSwitch();
        String str = i == 1 ? "enable" : "disable";
        L.mesh.d("%s reportMeshSwitchToMijia enable: %s, value: %s", "MiotMeshUtils", Integer.valueOf(i), str);
        meshSwitch.setValue(str);
        arrayList.add(meshSwitch);
        try {
            MiotHostManager.getInstance().sendEvent(arrayList, new CompletedListener() { // from class: com.xiaomi.micolauncher.module.setting.utils.MiotMeshUtils.2
                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onSucceed(String str2) {
                    L.mesh.d("%s reportMeshSwitchToMijia onSucceed, result: %s", "MiotMeshUtils", str2);
                }

                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onFailed(MiotError miotError) {
                    L.mesh.e("%s reportMeshSwitchToMijia onFailed: %s", "MiotMeshUtils", miotError.toString());
                }
            });
        } catch (MiotException e) {
            L.mesh.e("MiotMeshUtilsMiotHostManager sendEvent failed", e);
        }
    }

    public static void startMeshScanActivity(MeshScanEvent.MeshScanEnum meshScanEnum, Context context) {
        if (VoipModel.getInstance().isVoipActive()) {
            L.mesh.i("voip is running,not startMeshScanActivity");
            return;
        }
        L.mesh.i("%s 开启MeshScanActivity", "MiotMeshUtils");
        Intent intent = new Intent(context, MeshScanActivity.class);
        intent.putExtra("meshScanState", meshScanEnum);
        context.startActivity(intent);
    }

    public static ArrayList<MiotMeshDeviceItem> startMeshDevicesShowActivity(MiotDeviceScanResult miotDeviceScanResult, Context context) {
        L.mesh.i("%s 开启MeshDevicesShowActivity!", "MiotMeshUtils");
        if (VoipModel.getInstance().isVoipActive()) {
            L.mesh.i("voip is running,not startMeshDevicesShowActivity");
            return null;
        }
        ArrayList<MiotMeshDeviceItem> miotMeshDevices = getMiotMeshDevices(miotDeviceScanResult);
        EventBusRegistry.getEventBus().post(new MeshScanEvent(MeshScanEvent.MeshScanEnum.FINISH));
        Intent intent = new Intent(context, MeshDevicesShowActivity.class);
        intent.putExtra(MeshDevicesShowActivity.EXTRA_KEY_MESH_STATE, MeshPairState.MeshState.MESH_SCAN_COMPLETE);
        intent.putParcelableArrayListExtra(MeshDevicesShowActivity.EXTRA_KEY_MESH_DEVICES, miotMeshDevices);
        context.startActivity(intent);
        return miotMeshDevices;
    }

    @NotNull
    public static ArrayList<MiotMeshDeviceItem> getMiotMeshDevices(MiotDeviceScanResult miotDeviceScanResult) {
        ArrayList<MiotMeshDeviceItem> arrayList = new ArrayList<>();
        for (MiotDeviceScanResult.ParamsBean paramsBean : miotDeviceScanResult.getParams()) {
            MiotMeshDeviceItem miotMeshDeviceItem = new MiotMeshDeviceItem();
            miotMeshDeviceItem.setDeviceName(paramsBean.getFriendly_name());
            miotMeshDeviceItem.setPid(String.valueOf(paramsBean.getPid()));
            miotMeshDeviceItem.setUuid(String.valueOf(paramsBean.getUuid()));
            miotMeshDeviceItem.setDevssid(paramsBean.getDevssid());
            miotMeshDeviceItem.setModel(paramsBean.getModel());
            miotMeshDeviceItem.setMiotType(paramsBean.getType());
            if (paramsBean.getType() == 2) {
                miotMeshDeviceItem.setWifiMac(getWifiMac(paramsBean));
            }
            arrayList.add(miotMeshDeviceItem);
        }
        return arrayList;
    }

    public static void reportBindEventToBrainV3(MiotBindDevicesResult miotBindDevicesResult) {
        L.mesh.i("%s 上传绑定结果Event到BrainV3", "MiotMeshUtils");
        MiotBindDevicesResult.ParamsBean params = miotBindDevicesResult.getParams();
        List<MiotBindDevicesResult.ParamsBean.BaseBean> succeeded = params.getSucceeded();
        List<MiotBindDevicesResult.ParamsBean.BaseBean> failed = params.getFailed();
        DeviceBinding.PairDevicesResult pairDevicesResult = new DeviceBinding.PairDevicesResult();
        pairDevicesResult.setSucceeded(a(succeeded));
        pairDevicesResult.setFailed(a(failed));
        L.mesh.i("reportBindEvent: %s", pairDevicesResult);
        SpeechManager.getInstance().sendEventRequest(APIUtils.buildEvent(pairDevicesResult));
    }

    private static List<DeviceBinding.DeviceResult> a(List<MiotBindDevicesResult.ParamsBean.BaseBean> list) {
        ArrayList arrayList = new ArrayList();
        for (MiotBindDevicesResult.ParamsBean.BaseBean baseBean : list) {
            DeviceBinding.DeviceResult deviceResult = new DeviceBinding.DeviceResult();
            deviceResult.setFriendlyName(TextUtils.isEmpty(baseBean.getFriendly_name()) ? "" : baseBean.getFriendly_name());
            deviceResult.setUuid(baseBean.getUuid());
            arrayList.add(deviceResult);
        }
        return arrayList;
    }

    public static void setMeshDevicesConnectState(MiotBindDevicesResult miotBindDevicesResult, ArrayList<MiotMeshDeviceItem> arrayList) {
        Iterator<MiotMeshDeviceItem> it = arrayList.iterator();
        while (it.hasNext()) {
            MiotMeshDeviceItem next = it.next();
            if (!next.isPullCloudFinished()) {
                if (b(miotBindDevicesResult, next)) {
                    next.setConnectState(MiotMeshDeviceItem.CONNECT_STATE_SUCCEEDED);
                } else if (a(miotBindDevicesResult, next)) {
                    next.setConnectState(MiotMeshDeviceItem.CONNECT_STATE_FAILED);
                } else {
                    L.mesh.e("not match device");
                    next.setConnectState(MiotMeshDeviceItem.CONNECT_STATE_FAILED);
                }
            }
        }
    }

    private static boolean a(MiotBindDevicesResult miotBindDevicesResult, MiotMeshDeviceItem miotMeshDeviceItem) {
        for (MiotBindDevicesResult.ParamsBean.BaseBean baseBean : miotBindDevicesResult.getParams().getFailed()) {
            if (a(miotMeshDeviceItem, baseBean).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private static Boolean a(MiotMeshDeviceItem miotMeshDeviceItem, MiotBindDevicesResult.ParamsBean.BaseBean baseBean) {
        boolean z = true;
        boolean z2 = !TextUtils.isEmpty(miotMeshDeviceItem.getUuid()) && TextUtils.equals(miotMeshDeviceItem.getUuid(), baseBean.getUuid());
        if (TextUtils.isEmpty(miotMeshDeviceItem.getDevssid()) || !TextUtils.equals(miotMeshDeviceItem.getDevssid(), baseBean.getDevssid())) {
            z = false;
        }
        return Boolean.valueOf(z2 | z);
    }

    private static boolean b(MiotBindDevicesResult miotBindDevicesResult, MiotMeshDeviceItem miotMeshDeviceItem) {
        for (MiotBindDevicesResult.ParamsBean.BaseBean baseBean : miotBindDevicesResult.getParams().getSucceeded()) {
            if (a(miotMeshDeviceItem, baseBean).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    /* loaded from: classes3.dex */
    public static class WiFiPasswordInfo {
        public String password;
        public String ssid;

        public String getSsid() {
            return this.ssid;
        }

        public void setSsid(String str) {
            this.ssid = str;
        }

        public String getPassword() {
            return this.password;
        }

        public WiFiPasswordInfo(String str, String str2) {
            this.ssid = str;
            this.password = str2;
        }

        public void setPassword(String str) {
            this.password = str;
        }

        public String toString() {
            return "WiFiPasswordInfo{ssid='" + this.ssid + "', password='" + this.password + "'}";
        }
    }
}
