package com.xiaomi.accountsdk.hasheddeviceidlib;

import android.content.Context;

/* loaded from: classes2.dex */
public final class MacAddressUtil {
    private static final String TAG = "MacAddressUtil";

    public static String getMacAddress(Context context) {
        return HardwareInfo.getWifiMacAddress(context);
    }
}
