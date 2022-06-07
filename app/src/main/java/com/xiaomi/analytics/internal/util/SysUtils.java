package com.xiaomi.analytics.internal.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.smarthome.library.common.network.Network;

/* loaded from: classes3.dex */
public class SysUtils {
    private static String a;

    private static String a(Context context) {
        if (TextUtils.isEmpty(a)) {
            try {
                a = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            } catch (Exception e) {
                ALog.w("SysUtils", "getIMEI failed!", e);
            }
        }
        return a;
    }

    public static String getHashedIMEI(Context context) {
        String a2 = a(context);
        return !TextUtils.isEmpty(a2) ? Utils.getMd5(a2) : "";
    }

    public static String getHashedMac(Context context) {
        try {
            String macAddress = ((WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI)).getConnectionInfo().getMacAddress();
            return !TextUtils.isEmpty(macAddress) ? Utils.getMd5(macAddress) : "";
        } catch (Exception e) {
            Log.e(ALog.addPrefix("SysUtils"), "getHashedMac e", e);
            return "";
        }
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getAndroidVersionNumber() {
        return SystemProperties.get("ro.build.version.sdk", "");
    }

    public static String getMIUIBuild() {
        return MIUI.isAlphaBuild() ? "A" : MIUI.isStableBuild() ? ExifInterface.LATITUDE_SOUTH : MIUI.isDevBuild() ? HomePageRecordHelper.AREA_D : "";
    }

    public static String getDeviceModel() {
        return SystemProperties.get("ro.build.product", "");
    }

    public static String getRegion() {
        try {
            String str = SystemProperties.get("ro.miui.region", "");
            return TextUtils.isEmpty(str) ? SystemProperties.get("ro.product.locale.region", "") : str;
        } catch (Exception e) {
            ALog.e("SysUtils", "getRegion Exception: ", e);
            return "";
        }
    }

    public static String getMIUIVersion() {
        return Build.VERSION.INCREMENTAL;
    }

    public static void deleteDeviceIdInSpFile(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("_m_rec", 0);
            if (!sharedPreferences.getBoolean("has_deleted_id", false)) {
                sharedPreferences.edit().remove(OneTrack.Param.IMEI_MD5).apply();
                sharedPreferences.edit().putBoolean("has_deleted_id", true).apply();
            }
        } catch (Exception e) {
            ALog.e("SysUtils", "deleteDeviceIdInSpFile exception", e);
        }
    }
}
