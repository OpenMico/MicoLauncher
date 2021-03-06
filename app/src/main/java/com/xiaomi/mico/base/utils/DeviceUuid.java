package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/* loaded from: classes3.dex */
public class DeviceUuid {
    public static String DEVICE_PREFIX = "and:";
    private static String a;

    @Deprecated
    public static String getUuid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("device_uuid.xml", 0);
        String string = sharedPreferences.getString("device_uuid", null);
        if (string != null) {
            return string;
        }
        String string2 = Settings.Secure.getString(context.getContentResolver(), "android_id");
        if (string2 != null && !"9774d56d682e549c".equals(string2)) {
            string = UUID.nameUUIDFromBytes(string2.getBytes(StandardCharsets.UTF_8)).toString();
        }
        if (TextUtils.isEmpty(string)) {
            string = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            if (string == null) {
                string = UUID.randomUUID().toString();
            }
        }
        if (!TextUtils.isEmpty(string)) {
            sharedPreferences.edit().putString("device_uuid", string).apply();
        }
        return string;
    }

    public static String getSoftUuid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("device_uuid.xml", 0);
        String string = sharedPreferences.getString("device_soft_uuid", null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String uuid = UUID.randomUUID().toString();
        if (!TextUtils.isEmpty(uuid)) {
            sharedPreferences.edit().putString("device_soft_uuid", uuid).apply();
        }
        return uuid;
    }

    public static String hashSoftUuid(Context context) {
        String str;
        SharedPreferences sharedPreferences = context.getSharedPreferences("device_uuid.xml", 0);
        String string = sharedPreferences.getString("device_hash_soft_uuid", null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String softUuid = getSoftUuid(context);
        try {
            str = Base64.encodeToString(MessageDigest.getInstance("SHA1").digest(softUuid.getBytes()), 8).substring(0, 16);
        } catch (NoSuchAlgorithmException unused) {
            str = softUuid;
        }
        if (!TextUtils.isEmpty(softUuid)) {
            sharedPreferences.edit().putString("device_hash_soft_uuid", str).apply();
        }
        return str;
    }

    public static String getHardwareId(Context context) {
        String str;
        if (a == null) {
            String str2 = null;
            try {
                str = Settings.Secure.getString(context.getContentResolver(), "android_id");
            } catch (Throwable unused) {
                str = null;
            }
            if (Build.VERSION.SDK_INT > 8) {
                str2 = Build.SERIAL;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(DEVICE_PREFIX);
            StringBuilder sb2 = new StringBuilder();
            if (TextUtils.isEmpty(str)) {
                str = "";
            }
            sb2.append(str);
            if (TextUtils.isEmpty(str2)) {
                str2 = "";
            }
            sb2.append(str2);
            sb.append(XMStringUtils.getSHA1Digest(sb2.toString()));
            a = sb.toString();
        }
        return a;
    }
}
