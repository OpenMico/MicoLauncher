package com.xiaomi.miot.support.category;

import android.content.Context;
import com.xiaomi.miot.support.MicoSupConstants;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DeviceCategoryHelper {
    private DeviceCategoryHelper() {
    }

    public static Map<String, String> parseCategoryMapping(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        Iterator keys = jSONObject.keys();
        HashMap hashMap = new HashMap();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            hashMap.put(str, jSONObject.optString(str));
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void persistMappingJsonStr(Context context, String str) {
        if (str != null && str.trim().length() != 0 && context != null) {
            context.getSharedPreferences(MicoSupConstants.SPKey.SPKEY_NAME_MICO_SUP, 0).edit().putString(MicoSupConstants.SPKey.SPKEY_CATEGORY_MAPPING, str).apply();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getMappingJsonStrFromSP(Context context) {
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(MicoSupConstants.SPKey.SPKEY_NAME_MICO_SUP, 0).getString(MicoSupConstants.SPKey.SPKEY_CATEGORY_MAPPING, "");
    }
}
