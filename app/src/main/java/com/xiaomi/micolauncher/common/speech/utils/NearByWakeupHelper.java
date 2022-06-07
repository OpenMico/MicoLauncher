package com.xiaomi.micolauncher.common.speech.utils;

import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiError;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class NearByWakeupHelper {
    private static final int a = (int) TimeUnit.SECONDS.toMillis(2);
    private static DeviceState b = null;

    /* loaded from: classes3.dex */
    public static class LanGroup {
        public List<DeviceState> devices;
        public int lanId;
    }

    /* loaded from: classes3.dex */
    public interface NearbyWakeupListener {
        void notifyStatus(DeviceState deviceState);
    }

    /* loaded from: classes3.dex */
    public static class DeviceState {
        public String deviceId;
        public String hardware;
        public int nearbyStatus;
        public int onlineStatus;
        public String resource;

        public boolean isOn() {
            return this.nearbyStatus == 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DeviceState b(List<LanGroup> list) {
        String deviceID = SystemSetting.getDeviceID();
        L.base.d("loadCurrentDevice.bindId=%s", deviceID);
        if (list == null || list.size() <= 0) {
            return null;
        }
        L.base.json(Gsons.getGson().toJson(list));
        for (LanGroup lanGroup : list) {
            if (lanGroup.devices == null || lanGroup.devices.isEmpty()) {
                break;
            }
            for (DeviceState deviceState : lanGroup.devices) {
                if (deviceID.equalsIgnoreCase(deviceState.deviceId)) {
                    deviceState.hardware = SystemSetting.getHardWareModel();
                    return deviceState;
                }
            }
        }
        return null;
    }

    public static void getNearbyStatus(final NearbyWakeupListener nearbyWakeupListener) {
        L.base.d("getNearbyStatus");
        ApiManager.minaService.lanAwakeGroup().subscribe(new DefaultObserver<List<LanGroup>>() { // from class: com.xiaomi.micolauncher.common.speech.utils.NearByWakeupHelper.1
            /* renamed from: a */
            public void onSuccess(List<LanGroup> list) {
                DeviceState b2 = NearByWakeupHelper.b(list);
                DeviceState unused = NearByWakeupHelper.b = b2;
                if (b2 != null && NearbyWakeupListener.this != null) {
                    SystemSetting.setNearbyWakeup(b2.isOn());
                    NearbyWakeupListener.this.notifyStatus(b2);
                }
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver
            public void onFail(ApiError apiError) {
                super.onFail(apiError);
                L.base.e("getNearbyStatus.onFail.res=%s", apiError);
            }
        });
    }

    public static void setNearbyStatus(boolean z) {
        setNearbyStatus(z, null);
    }

    public static void setNearbyStatus(final boolean z, final NearbyWakeupListener nearbyWakeupListener) {
        if (b == null) {
            b = new DeviceState();
            b.deviceId = SystemSetting.getDeviceID();
            b.hardware = SystemSetting.getHardWareModel();
            DeviceState deviceState = b;
            deviceState.onlineStatus = 1;
            deviceState.resource = "wakeup";
        }
        if (z) {
            b.nearbyStatus = 1;
        } else {
            b.nearbyStatus = 0;
        }
        ApiManager.minaService.updateAwakeStatus(SystemSetting.getDeviceID(), SystemSetting.getHardWareModel(), b.nearbyStatus, b.resource).subscribe(new DefaultObserver<Boolean>() { // from class: com.xiaomi.micolauncher.common.speech.utils.NearByWakeupHelper.2
            /* renamed from: a */
            public void onSuccess(Boolean bool) {
                if (bool.booleanValue()) {
                    SystemSetting.setNearbyWakeup(z);
                } else {
                    if (Mic.getInstance().isMicMute()) {
                        ToastUtil.showToast((int) R.string.setting_nearby_wakeup_tip_mic_mute, NearByWakeupHelper.a);
                    }
                    if (SystemSetting.getNearbyWakeup()) {
                        NearByWakeupHelper.b.nearbyStatus = 1;
                    } else {
                        NearByWakeupHelper.b.nearbyStatus = 0;
                    }
                    NearbyWakeupListener nearbyWakeupListener2 = nearbyWakeupListener;
                    if (nearbyWakeupListener2 != null) {
                        nearbyWakeupListener2.notifyStatus(NearByWakeupHelper.b);
                    }
                }
                L.base.d("setNearbyStatus.onSuccess.enable=%s, res=%s", Boolean.valueOf(z), bool);
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver
            public void onFail(ApiError apiError) {
                super.onFail(apiError);
                if (Mic.getInstance().isMicMute()) {
                    ToastUtil.showToast((int) R.string.setting_nearby_wakeup_tip_mic_mute, NearByWakeupHelper.a);
                }
                if (SystemSetting.getNearbyWakeup()) {
                    NearByWakeupHelper.b.nearbyStatus = 1;
                } else {
                    NearByWakeupHelper.b.nearbyStatus = 0;
                }
                NearbyWakeupListener nearbyWakeupListener2 = nearbyWakeupListener;
                if (nearbyWakeupListener2 != null) {
                    nearbyWakeupListener2.notifyStatus(NearByWakeupHelper.b);
                }
                L.base.e("setNearbyStatus.onFail.res=%s", apiError);
            }
        });
    }
}
