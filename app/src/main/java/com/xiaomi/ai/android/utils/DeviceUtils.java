package com.xiaomi.ai.android.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.ai.android.capability.AuthCapability;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.mipush.sdk.Constants;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/* loaded from: classes2.dex */
public final class DeviceUtils {
    private static final Set<String> a = new HashSet();

    static {
        a.add("ad921d60486366258809553a3db49a4a");
    }

    private DeviceUtils() {
    }

    private static String a(String str) {
        String str2;
        String str3;
        if (TextUtils.isEmpty(str) || str.equals("unknown")) {
            return null;
        }
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException unused) {
            str2 = "DeviceUtils";
            str3 = "UnsupportedEncodingException";
            Logger.d(str2, str3);
            return null;
        } catch (NoSuchAlgorithmException unused2) {
            str2 = "DeviceUtils";
            str3 = "NoSuchAlgorithmException";
            Logger.d(str2, str3);
            return null;
        }
    }

    private static boolean b(String str) {
        return "02:00:00:00:00:00".equals(str) || "00:00:00:00:00:00".equals(str) || "ff:ff:ff:ff:ff:ff".equals(str) || "FF:FF:FF:FF:FF:FF".equals(str);
    }

    public static String getDefaultState(String str) {
        return getDefaultState(str, null);
    }

    public static String getDefaultState(String str, String str2) {
        String str3;
        if (TextUtils.isEmpty(str2)) {
            str3 = "d=" + str;
        } else {
            str3 = "d=" + str + "&md=" + str2;
        }
        return b.a("SHA1", str3.getBytes()).replace(Constants.COLON_SEPARATOR, "");
    }

    public static synchronized String getDeviceId(Context context) {
        String deviceId;
        synchronized (DeviceUtils.class) {
            deviceId = getDeviceId(context, null);
        }
        return deviceId;
    }

    public static synchronized String getDeviceId(Context context, AuthCapability authCapability) {
        String a2;
        synchronized (DeviceUtils.class) {
            Logger.a("DeviceUtils", "getDeviceId");
            boolean z = true;
            a2 = d.a(context, "aivs_did.xml", "device_id");
            if (TextUtils.isEmpty(a2) || a.contains(a2)) {
                z = false;
                if (authCapability != null) {
                    Logger.b("DeviceUtils", "getDeviceId: from oaid");
                    a2 = authCapability.getOaid();
                }
                if (TextUtils.isEmpty(a2) && Build.VERSION.SDK_INT >= 26) {
                    try {
                        a2 = a(Build.getSerial());
                        Logger.a("DeviceUtils", "get did from build serial");
                    } catch (Exception e) {
                        Logger.b("DeviceUtils", "getSerial: " + e.getMessage());
                    }
                }
                if (TextUtils.isEmpty(a2) || b(a2)) {
                    a2 = a(UUID.randomUUID().toString());
                    Logger.a("DeviceUtils", "get did from random uuid");
                }
            }
            if (!TextUtils.isEmpty(a2) && !z) {
                d.a(context, "aivs_did.xml", "device_id", a2);
            }
        }
        return a2;
    }
}
