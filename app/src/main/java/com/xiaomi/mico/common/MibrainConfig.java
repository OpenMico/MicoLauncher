package com.xiaomi.mico.common;

import android.content.Context;

/* loaded from: classes3.dex */
public class MibrainConfig {
    private static MibrainConfig a;
    public final String clientId;
    public final String clientKey;
    public final String deviceModel;
    public final String iotFriendlyName;

    private MibrainConfig(String str, String str2, String str3, String str4) {
        this.clientId = str;
        this.clientKey = str2;
        this.deviceModel = str3;
        this.iotFriendlyName = str4;
    }

    private static void a(Hardware hardware) {
        if (a == null) {
            a = new MibrainConfig(hardware.getClientId(), hardware.getClientKey(), hardware.getMiBrainDeviceModel(), hardware.getIotFriendlyName());
        }
    }

    public static MibrainConfig getMibrainConfig(Context context) {
        a(Hardware.current(context));
        return a;
    }
}
