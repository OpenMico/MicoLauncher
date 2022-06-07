package com.xiaomi.onetrack;

import android.os.Bundle;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.r;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class DataConverter {
    private static final String a = "DataConverter";

    public static Map fromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        try {
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (r.b(obj)) {
                    hashMap.put(str, obj);
                } else if (obj.getClass().isArray()) {
                    int length = Array.getLength(obj);
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < length; i++) {
                        Object obj2 = Array.get(obj, i);
                        if (r.b(obj2)) {
                            arrayList.add(obj2);
                        }
                    }
                    if (arrayList.size() > 0) {
                        hashMap.put(str, arrayList);
                    }
                } else if (obj instanceof List) {
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj3 : (List) obj) {
                        if (r.b(obj3)) {
                            arrayList2.add(obj3);
                        }
                    }
                    if (arrayList2.size() > 0) {
                        hashMap.put(str, arrayList2);
                    }
                } else if (p.a) {
                    r.a(a, str);
                }
            }
        } catch (Exception e) {
            p.b(a, "convert bundle error:" + e.toString());
        }
        return hashMap;
    }

    public static Map fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                Object obj = jSONObject.get(str);
                if (r.b(obj)) {
                    hashMap.put(str, obj);
                } else if (obj instanceof JSONArray) {
                    hashMap.put(str, a((JSONArray) obj));
                } else if (p.a) {
                    r.a(a, str);
                }
            }
        } catch (Exception e) {
            p.b(a, "convert json to map error:" + e.toString());
        }
        return hashMap;
    }

    private static List a(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            Object obj = jSONArray.get(i);
            if (r.b(obj)) {
                arrayList.add(obj);
            } else if (obj instanceof JSONObject) {
                JSONObject jSONObject = (JSONObject) obj;
                Iterator keys = jSONObject.keys();
                HashMap hashMap = new HashMap();
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    Object obj2 = jSONObject.get(str);
                    if (r.b(obj2)) {
                        hashMap.put(str, obj2);
                    }
                }
                if (hashMap.size() > 0) {
                    arrayList.add(hashMap);
                }
            }
        }
        return arrayList;
    }
}
