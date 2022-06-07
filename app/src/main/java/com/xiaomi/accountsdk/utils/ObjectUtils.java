package com.xiaomi.accountsdk.utils;

import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ObjectUtils {
    public static Map<String, Object> jsonToMap(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            hashMap.put(str, convertObj(jSONObject.opt(str)));
        }
        return hashMap;
    }

    public static Object convertObjectToJson(Object obj) {
        if (obj instanceof List) {
            JSONArray jSONArray = new JSONArray();
            for (Object obj2 : (List) obj) {
                jSONArray.put(convertObjectToJson(obj2));
            }
            return jSONArray;
        } else if (!(obj instanceof Map)) {
            return obj;
        } else {
            JSONObject jSONObject = new JSONObject();
            Map map = (Map) obj;
            for (Object obj3 : map.keySet()) {
                try {
                    jSONObject.put((String) obj3, convertObjectToJson(map.get(obj3)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jSONObject;
        }
    }

    public static JSONArray jsonArrayStrToJsonArray(String str) {
        if (str == null) {
            return null;
        }
        try {
            return new JSONArray(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Object> jsonArrayStringToList(String str) {
        JSONArray jsonArrayStrToJsonArray = jsonArrayStrToJsonArray(str);
        if (jsonArrayStrToJsonArray == null) {
            return null;
        }
        return convertJsonArrayToList(jsonArrayStrToJsonArray);
    }

    public static List<Object> convertJsonArrayToList(JSONArray jSONArray) {
        Object convertObj = convertObj(jSONArray);
        if (convertObj instanceof List) {
            return (List) convertObj;
        }
        return null;
    }

    public static Map<String, String> listToMap(Map<String, List<String>> map) {
        HashMap hashMap = new HashMap();
        if (map != null) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                if (!(key == null || value == null || value.size() <= 0)) {
                    hashMap.put(key, value.get(0));
                }
            }
        }
        return hashMap;
    }

    public static String flattenMap(Map<?, ?> map) {
        if (map == null) {
            return "null";
        }
        Set<Map.Entry<?, ?>> entrySet = map.entrySet();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<?, ?> entry : entrySet) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            sb.append("(");
            sb.append(key);
            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
            sb.append(value);
            sb.append("),");
        }
        sb.append("}");
        return sb.toString();
    }

    private static Object convertObj(Object obj) {
        if (obj instanceof JSONObject) {
            return jsonToMap((JSONObject) obj);
        }
        if (obj instanceof JSONArray) {
            JSONArray jSONArray = (JSONArray) obj;
            int length = jSONArray.length();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < length; i++) {
                arrayList.add(convertObj(jSONArray.opt(i)));
            }
            return arrayList;
        } else if (obj == JSONObject.NULL) {
            return null;
        } else {
            return obj;
        }
    }
}
