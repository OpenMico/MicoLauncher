package com.xiaomi.phonenum.utils;

import android.text.TextUtils;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.mipush.sdk.Constants;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class MapUtil {
    public static String joinToQuery(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                if (z) {
                    z = false;
                } else {
                    sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
        }
        return sb.toString();
    }

    public static String join(List<String> list, String str) {
        if (list == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str2 : list) {
            if (!TextUtils.isEmpty(str2)) {
                if (z) {
                    z = false;
                } else {
                    sb.append(str);
                }
                sb.append(str2);
            }
        }
        return sb.toString();
    }

    public static String joinToJsonMap(Map<String, List<String>> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean z = true;
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            String join = join(entry.getValue(), ";");
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(join)) {
                if (z) {
                    z = false;
                } else {
                    sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                }
                sb.append("\"");
                sb.append(key);
                sb.append("\"");
                sb.append(Constants.COLON_SEPARATOR);
                sb.append("\"");
                sb.append(join);
                sb.append("\"");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static JSONObject joinToJson(Map<String, List<String>> map) throws JSONException {
        if (map == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            String join = join(entry.getValue(), ";");
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(join)) {
                jSONObject.put(key, join);
            }
        }
        return jSONObject;
    }

    public static Map<String, String> jsonToMap(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        Iterator keys = jSONObject.keys();
        HashMap hashMap = new HashMap();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            hashMap.put(str, jSONObject.getString(str));
        }
        return hashMap;
    }
}
