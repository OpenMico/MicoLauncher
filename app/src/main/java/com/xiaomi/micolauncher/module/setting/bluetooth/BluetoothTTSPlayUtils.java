package com.xiaomi.micolauncher.module.setting.bluetooth;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.L;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class BluetoothTTSPlayUtils {
    private static final List<String> a = Arrays.asList("F4:4E:FD");
    private static final List<String> b = Arrays.asList("MIJIA K歌麦克风");

    public static boolean isInTTSPlayBlackList(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            for (String str3 : a) {
                if (str.startsWith(str3)) {
                    return true;
                }
            }
        }
        if (TextUtils.isEmpty(str2)) {
            L.bluetooth.i("%s deviceName is empty , macAddress:%s", "BluetoothTTSPlayUtils", str);
        } else {
            for (String str4 : b) {
                if (str4.equals(str2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
