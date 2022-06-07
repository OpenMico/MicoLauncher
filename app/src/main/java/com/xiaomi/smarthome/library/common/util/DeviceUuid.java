package com.xiaomi.smarthome.library.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/* loaded from: classes4.dex */
public class DeviceUuid {
    public static String getUuid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("device_uuid.xml", 0);
        String string = sharedPreferences.getString("device_uuid", null);
        if (string != null) {
            return string;
        }
        String string2 = Settings.Secure.getString(context.getContentResolver(), "android_id");
        if (string2 != null && !"9774d56d682e549c".equals(string2)) {
            try {
                string = UUID.nameUUIDFromBytes(string2.getBytes("UTF-8")).toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(string)) {
            string = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            if (string == null) {
                string = UUID.randomUUID().toString();
            }
        }
        if (!TextUtils.isEmpty(string)) {
            sharedPreferences.edit().putString("device_uuid", string).commit();
        }
        return string;
    }
}
