package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes3.dex */
public class BluetoothUtil {
    public static String getMacAddress(Context context) {
        if (context == null) {
            return null;
        }
        return Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
    }

    public static byte[] getMacBytes(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        byte[] bArr = new byte[6];
        String[] split = str.split(Constants.COLON_SEPARATOR);
        for (int i = 0; i < split.length; i++) {
            bArr[i] = (byte) Integer.parseInt(split[i], 16);
        }
        return bArr;
    }
}
