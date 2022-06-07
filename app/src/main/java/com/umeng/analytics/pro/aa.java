package com.umeng.analytics.pro;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import xcrash.TombstoneParser;

/* compiled from: DeviceIdSupplier.java */
/* loaded from: classes2.dex */
public class aa {
    public static y a(Context context) {
        String str = Build.BRAND;
        ah.a("Device", TombstoneParser.keyBrand, str);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.equalsIgnoreCase("huawei") || str.equalsIgnoreCase("honor") || str.equalsIgnoreCase("华为")) {
            return new ab();
        }
        if (str.equalsIgnoreCase("xiaomi") || str.equalsIgnoreCase("redmi") || str.equalsIgnoreCase("meitu") || str.equalsIgnoreCase("小米")) {
            return new ag();
        }
        if (str.equalsIgnoreCase("vivo")) {
            return new af();
        }
        if (str.equalsIgnoreCase("oppo") || str.equalsIgnoreCase("oneplus")) {
            return new ad();
        }
        if (str.equalsIgnoreCase("lenovo") || str.equalsIgnoreCase("zuk")) {
            return new ac();
        }
        if (Build.MANUFACTURER.equalsIgnoreCase("SAMSUNG")) {
            return new ae();
        }
        return null;
    }
}
