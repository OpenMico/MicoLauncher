package com.xiaomi.micolauncher.module.initialize;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.mqtt.service.iface.IMQTTService;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class MqttProtocolHelper {

    /* loaded from: classes3.dex */
    public static class DeviceIdAndDeviceSecretPair {
        public final String deviceId;
        public final String deviceSecret;

        public DeviceIdAndDeviceSecretPair(String str, String str2) {
            this.deviceId = str;
            this.deviceSecret = str2;
        }

        public String toString() {
            return "DeviceIdAndDeviceSecretPair{deviceId='" + this.deviceId + "', deviceSecret='" + this.deviceSecret + "'}";
        }
    }

    public static DeviceIdAndDeviceSecretPair getDeviceIdAndDeviceSecretPair(@NonNull IMQTTService iMQTTService) {
        try {
            return getDeviceIdAndDeviceSecretPair(iMQTTService.getDeviceIDAndChannelSecret());
        } catch (RemoteException e) {
            L.init.e("getDeviceIdAndDeviceSecretPair", e);
            return null;
        }
    }

    public static DeviceIdAndDeviceSecretPair getDeviceIdAndDeviceSecretPair(String str) {
        if (ContainerUtil.isEmpty(str)) {
            return null;
        }
        String[] split = str.split("\n");
        if (ContainerUtil.getSize(split) == 2) {
            return new DeviceIdAndDeviceSecretPair(split[0], split[1]);
        }
        throw new IllegalStateException("unexpected result of device id and device secret " + str);
    }
}
