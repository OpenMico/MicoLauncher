package com.xiaomi.micolauncher.module.initialize;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import androidx.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.initialize.WiFiPasswordStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public class WiFiPasswordStore {
    private final ConcurrentHashMap<String, String> a = new ConcurrentHashMap<>();

    private String b(String str) {
        return this.a.get(str);
    }

    public void remove(Context context, String str) {
        this.a.remove(str);
        HashMap<String, PwdStored> a = a(context);
        if (a == null) {
            a = new HashMap<>();
        }
        a(str, a);
        MicoSettings.setStoreWifiInfo(context, Gsons.getGson().toJson(a));
        L.wlan.i("WiFiPasswordStore removed ssid %s", str);
        DebugHelper.printStackTrace("removeSsid" + str, L.wlan);
    }

    private void a(String str, HashMap<String, PwdStored> hashMap) {
        hashMap.remove(str);
        hashMap.remove(WiFiUtil.stripSSID(str));
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
            L.init.i("WiFiPasswordStore save wifiInfo, pwd=%s", b);
            a(context, a);
        }
    }

    private void a(Context context, HashMap<String, PwdStored> hashMap) {
        a(context, Gsons.getGson().toJson(hashMap));
    }

    private void a(Context context, String str) {
        MicoSettings.setStoreWifiInfo(context, str);
    }

    private static HashMap<String, PwdStored> a(Context context) {
        if (context == null) {
            return null;
        }
        return (HashMap) Gsons.getGson().fromJson(MicoSettings.getStoreWifiInfo(context, "{}"), new TypeToken<HashMap<String, PwdStored>>() { // from class: com.xiaomi.micolauncher.module.initialize.WiFiPasswordStore.1
        }.getType());
    }

    @Nullable
    public static String loadStoredWiFi(Context context, String str) {
        PwdStored loadStoredWiFiFull = loadStoredWiFiFull(context, str);
        if (loadStoredWiFiFull == null) {
            return null;
        }
        return loadStoredWiFiFull.decryptedPassword();
    }

    public static PwdStored loadStoredWiFiFull(Context context, String str) {
        HashMap<String, PwdStored> a = a(context);
        if (ContainerUtil.isEmpty(str) || ContainerUtil.isEmpty(a)) {
            return null;
        }
        PwdStored pwdStored = a.get(str);
        return pwdStored != null ? pwdStored : a.get(WiFiUtil.stripSSID(str));
    }

    public static List<PwdStored> loadStored(Context context) {
        HashMap<String, PwdStored> a = a(context);
        if (ContainerUtil.isEmpty(a)) {
            return null;
        }
        final ArrayList arrayList = new ArrayList();
        a.forEach(new BiConsumer() { // from class: com.xiaomi.micolauncher.module.initialize.-$$Lambda$WiFiPasswordStore$lNi5oRxSRD5HJVMmPxPJB1pojxc
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
}
