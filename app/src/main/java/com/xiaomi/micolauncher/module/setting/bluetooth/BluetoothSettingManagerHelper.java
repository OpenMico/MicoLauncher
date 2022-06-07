package com.xiaomi.micolauncher.module.setting.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.xiaomi.micolauncher.common.L;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class BluetoothSettingManagerHelper {
    public static final int FEATURE_AUDIO_NONE = 0;
    public static final int FEATURE_AUDIO_SINK = 2;
    public static final int FEATURE_AUDIO_SINK_SOURCE = 3;
    public static final int FEATURE_AUDIO_SOURCE = 1;
    private static final List<String> a = Arrays.asList("F4:4E:FD");
    private static final List<String> b = Arrays.asList("MIJIA K歌麦克风");

    public static boolean isInWhiteList(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            for (String str3 : a) {
                if (str.startsWith(str3)) {
                    return true;
                }
            }
        } else {
            L.bluetooth.i("%s macAddress is empty", "BluetoothSettingManagerHelper");
        }
        if (TextUtils.isEmpty(str2)) {
            L.bluetooth.i("%s deviceName is empty,macAddress:%s", "BluetoothSettingManagerHelper", str);
        } else {
            for (String str4 : b) {
                if (str2.equals(str4)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getSupportFeature(BluetoothDevice bluetoothDevice) {
        ParcelUuid[] uuids = bluetoothDevice.getUuids();
        if (uuids == null || uuids.length <= 0) {
            L.bluetooth.e("device can`t get uuid mac:%s", bluetoothDevice.getAddress());
            return 0;
        }
        int i = 0;
        for (ParcelUuid parcelUuid : uuids) {
            if (BluetoothUuid.isAudioSink(parcelUuid)) {
                i |= 2;
            }
            if (BluetoothUuid.isAudioSource(parcelUuid)) {
                i |= 1;
            }
        }
        return i;
    }
}
