package com.xiaomi.mico.settingslib.wifi;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class BlackListWifiStore {
    public static void add(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            Log.d("BlackListWifiStore", "add to black list ssid=" + str);
            String stripSSID = WiFiPasswordStore.stripSSID(str);
            HashMap<String, String> a = a(context);
            if (a == null) {
                a = new HashMap<>();
            }
            a.put(stripSSID, stripSSID);
            a(context, a);
        }
    }

    public static void remove(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            Log.d("BlackListWifiStore", "remove from black list ssid=" + str);
            HashMap<String, String> a = a(context);
            if (a != null) {
                a.remove(WiFiPasswordStore.stripSSID(str));
                a(context, a);
            }
        }
    }

    public static boolean contains(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String stripSSID = WiFiPasswordStore.stripSSID(str);
        HashMap<String, String> a = a(context);
        return a != null && a.containsKey(stripSSID);
    }

    private static HashMap<String, String> a(Context context) {
        if (context == null) {
            return null;
        }
        return (HashMap) Gsons.getGson().fromJson(MicoSettings.getBlackListWifiInfo(context, "{}"), new TypeToken<HashMap<String, String>>() { // from class: com.xiaomi.mico.settingslib.wifi.BlackListWifiStore.1
        }.getType());
    }

    private static void a(Context context, Map<String, ?> map) {
        MicoSettings.setBlackListWifiInfo(context, Gsons.getGson().toJson(map));
    }

    public static void addAll(Context context, Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            HashMap<String, String> a = a(context);
            if (a != null) {
                a.putAll(map);
                map = a;
            }
            a(context, map);
        }
    }
}
