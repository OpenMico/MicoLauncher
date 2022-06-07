package com.xiaomi.mico.settingslib.wifi;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.mico.settingslib.wifi.WiFiPasswordStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public class WiFiPasswordStore {
    private volatile ConcurrentHashMap<String, String> a;

    public void putIntoMemory(String str, String str2) {
        if (this.a == null) {
            this.a = new ConcurrentHashMap<>();
        }
        this.a.put(str, str2);
    }

    private String b(String str) {
        if (this.a == null || str == null) {
            return null;
        }
        return this.a.get(str);
    }

    public void save(Context context, String str) {
        String b = b(str);
        if (b != null && context != null) {
            HashMap<String, PwdStored> a = a(context);
            if (a == null) {
                a = new HashMap<>();
            }
            a(str, a);
            a.put(str, new PwdStored(str, d(b)));
            MicoSettings.setStoreWifiInfo(context, Gsons.getGson().toJson(a));
        }
    }

    public void remove(Context context, String str) {
        if (this.a != null) {
            this.a.remove(str);
        }
        HashMap<String, PwdStored> a = a(context);
        if (a == null) {
            a = new HashMap<>();
        }
        a(str, a);
        MicoSettings.setStoreWifiInfo(context, Gsons.getGson().toJson(a));
    }

    private void a(String str, HashMap<String, PwdStored> hashMap) {
        if (hashMap != null) {
            hashMap.remove(str);
            hashMap.remove(stripSSID(str));
        }
    }

    private static HashMap<String, PwdStored> a(Context context) {
        if (context == null) {
            return null;
        }
        return (HashMap) Gsons.getGson().fromJson(MicoSettings.getStoreWifiInfo(context, "{}"), new TypeToken<HashMap<String, PwdStored>>() { // from class: com.xiaomi.mico.settingslib.wifi.WiFiPasswordStore.1
        }.getType());
    }

    public static String loadStoredWiFi(Context context, String str) {
        HashMap<String, PwdStored> a = a(context);
        if (a == null || !a.containsKey(str)) {
            return null;
        }
        return a.get(str).decryptedPassword();
    }

    public static List<PwdStored> loadStored(Context context) {
        HashMap<String, PwdStored> a = a(context);
        if (a == null) {
            return null;
        }
        final ArrayList arrayList = new ArrayList();
        a.forEach(new BiConsumer() { // from class: com.xiaomi.mico.settingslib.wifi.-$$Lambda$WiFiPasswordStore$eQDc-BzGq8RHzKU9s_zH50c1Glc
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                String str = (String) obj;
                arrayList.add((WiFiPasswordStore.PwdStored) obj2);
            }
        });
        return arrayList;
    }

    /* loaded from: classes3.dex */
    public static class PwdStored {
        @SerializedName("identity")
        String a;
        @SerializedName("password")
        String b;

        public PwdStored(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.a.equals(((PwdStored) obj).a);
        }

        public String toString() {
            return "PwdStored{identity='" + this.a + "', decryptedPassword='" + decryptedPassword() + "'}";
        }

        public String getIdentity() {
            return this.a;
        }

        public String decryptedPassword() {
            return WiFiPasswordStore.c(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(String str) {
        return TextUtils.isEmpty(str) ? "" : new String(Base64.decode(str.getBytes(), 0));
    }

    private static String d(String str) {
        return TextUtils.isEmpty(str) ? "" : Base64.encodeToString(str.getBytes(), 0);
    }

    public static String stripSSID(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (str.length() < 3 || !str.startsWith("\"") || !str.endsWith("\"")) ? str : str.substring(1, str.length() - 1);
    }
}
