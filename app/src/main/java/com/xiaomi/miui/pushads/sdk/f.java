package com.xiaomi.miui.pushads.sdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.xiaomi.miui.pushads.sdk.k;

/* loaded from: classes4.dex */
public class f {
    private static k.a a(int i) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return k.a.MN2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return k.a.MN3G;
            case 13:
                return k.a.MN4G;
            default:
                return k.a.NONE;
        }
    }

    public static k.a a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return k.a.NONE;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) ? k.a.NONE : !connectivityManager.isActiveNetworkMetered() ? k.a.Wifi : a(((TelephonyManager) context.getSystemService("phone")).getNetworkType());
    }

    /* renamed from: a  reason: collision with other method in class */
    public static boolean m749a(Context context) {
        boolean z = k.a.NONE != a(context);
        k.a aVar = k.a.Wifi;
        return z;
    }

    public static boolean a(String str) {
        return TextUtils.isEmpty(str) || str.equals("0");
    }
}
