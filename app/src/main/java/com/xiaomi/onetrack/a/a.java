package com.xiaomi.onetrack.a;

import android.text.TextUtils;
import com.xiaomi.onetrack.BuildConfig;
import com.xiaomi.onetrack.f.b;
import com.xiaomi.onetrack.f.c;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.i;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.q;
import com.xiaomi.onetrack.util.x;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {
    public static final String a = "disable_log";
    public static final String b = "event";
    public static final String c = "events";
    public static final String d = "level";
    public static final String e = "sample";
    private static final String f = "AppConfigUpdater";
    private static final long g = 172800000;
    private static final String h = "hash";
    private static final String i = "appId";
    private static final String j = "apps";
    private static final String k = "type";
    private static final String l = "status";
    private static final String m = "deleted";
    private static final String n = "Android";
    private static boolean o = false;
    private static final int p = 0;
    private static final int q = 1;
    private static final int r = 2;
    private static final int s = 100;

    public static void a(String str) {
        i.a(new b(str));
    }

    public static void b(String str) {
        if (c(str)) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            a(arrayList);
            return;
        }
        p.a(f, "AppConfigUpdater Does not meet prerequisites for request");
    }

    private static boolean c(String str) {
        if (!c.a()) {
            p.a(f, "net is not connected!");
            return false;
        }
        j d2 = f.a().d(str);
        if (d2 == null) {
            return true;
        }
        long j2 = d2.c;
        return j2 < System.currentTimeMillis() || j2 - System.currentTimeMillis() > g;
    }

    private static void a(List<String> list) {
        if (!q.a(f) && !o) {
            HashMap hashMap = new HashMap();
            try {
                try {
                    o = true;
                    hashMap.put(k.a, com.xiaomi.onetrack.util.oaid.a.a().a(com.xiaomi.onetrack.e.a.a()));
                    hashMap.put(k.b, q.h());
                    hashMap.put(k.c, q.d());
                    hashMap.put(k.d, q.x() ? "1" : "0");
                    hashMap.put("sv", BuildConfig.SDK_VERSION);
                    hashMap.put(k.m, com.xiaomi.onetrack.e.a.b());
                    hashMap.put(k.f, q.i());
                    hashMap.put(k.g, DeviceUtil.c());
                    hashMap.put(k.h, q.y());
                    hashMap.put(k.i, b(list));
                    hashMap.put(k.j, com.xiaomi.onetrack.e.a.d());
                    hashMap.put("platform", "Android");
                    String c2 = x.a().c();
                    p.a(f, "pullData:" + c2);
                    String b2 = b.b(c2, hashMap, true);
                    p.a(f, "response:" + b2);
                    a(b2, list);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } finally {
                o = false;
            }
        }
    }

    private static String b(List<String> list) {
        JSONArray jSONArray = new JSONArray();
        try {
            JSONObject jSONObject = new JSONObject();
            for (int i2 = 0; i2 < list.size(); i2++) {
                String str = list.get(i2);
                jSONObject.put(i, str);
                jSONObject.put("hash", f.a().c(str));
                jSONArray.put(jSONObject);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jSONArray.toString();
    }

    public static void a(String str, List<String> list) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.optInt("code") == 0) {
                    a(jSONObject.optJSONObject("data").optJSONArray(j), list);
                }
            } catch (Exception e2) {
                p.a(f, "saveAppCloudData: " + e2.toString());
            }
        }
    }

    private static void a(JSONArray jSONArray, List<String> list) throws JSONException {
        long currentTimeMillis = System.currentTimeMillis() + 86400000 + new Random().nextInt(86400000);
        if (jSONArray == null || jSONArray.length() <= 0) {
            a(list, currentTimeMillis);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i2);
            int optInt = optJSONObject != null ? optJSONObject.optInt("type") : -1;
            String optString = optJSONObject != null ? optJSONObject.optString(i) : "";
            if (optInt == 0 || optInt == 1) {
                a(optJSONObject, currentTimeMillis);
            } else if (optInt == 2) {
                b(optJSONObject, currentTimeMillis);
            } else {
                p.a(f, "updateDataToDb do nothing!");
            }
            if (!TextUtils.isEmpty(optString)) {
                arrayList.add(optString);
            }
        }
        a(list, currentTimeMillis, arrayList);
    }

    private static void a(JSONObject jSONObject, long j2) {
        ArrayList<j> arrayList = new ArrayList<>();
        if (jSONObject != null) {
            j jVar = new j();
            jVar.d = jSONObject.optString("hash");
            jVar.a = jSONObject.optString(i);
            jVar.b = a(jSONObject);
            jVar.c = j2;
            if (jSONObject.has("events")) {
                jVar.e = jSONObject;
            }
            arrayList.add(jVar);
        }
        if (!arrayList.isEmpty()) {
            f.a().a(arrayList);
        } else {
            p.a(f, "handleFullOrNoNewData no configuration can be updated!");
        }
    }

    private static void b(JSONObject jSONObject, long j2) {
        ArrayList<j> arrayList = new ArrayList<>();
        if (jSONObject == null || !jSONObject.has("events")) {
            p.a(f, "handleIncrementalUpdate config is not change!");
        } else {
            j jVar = new j();
            jVar.d = jSONObject.optString("hash");
            String optString = jSONObject.optString(i);
            jVar.a = optString;
            jVar.b = a(jSONObject);
            jVar.c = j2;
            jVar.e = a(optString, jSONObject);
            arrayList.add(jVar);
        }
        if (!arrayList.isEmpty()) {
            f.a().a(arrayList);
        } else {
            p.a(f, "handleIncrementalUpdate no configuration can be updated!");
        }
    }

    private static int a(JSONObject jSONObject) {
        try {
            int optInt = jSONObject.optInt(e, 100);
            if (optInt < 0 || optInt > 100) {
                return 100;
            }
            return optInt;
        } catch (Exception e2) {
            p.a(f, "getCommonSample Exception:" + e2.getMessage());
            return 100;
        }
    }

    private static JSONObject a(String str, JSONObject jSONObject) {
        JSONArray jSONArray;
        try {
            j d2 = f.a().d(str);
            if (d2 != null) {
                jSONArray = d2.e.optJSONArray("events");
            } else {
                jSONArray = null;
            }
            jSONObject.put("events", a(jSONArray, jSONObject.optJSONArray("events")));
            return jSONObject;
        } catch (Exception e2) {
            p.b(f, "mergeConfig: " + e2.toString());
            return null;
        }
    }

    private static JSONArray a(JSONArray jSONArray, JSONArray jSONArray2) {
        JSONArray jSONArray3 = jSONArray;
        int i2 = 0;
        while (jSONArray2 != null) {
            try {
                if (i2 >= jSONArray2.length()) {
                    break;
                }
                JSONObject optJSONObject = jSONArray2.optJSONObject(i2);
                String optString = optJSONObject.optString("event");
                int i3 = 0;
                while (true) {
                    if (jSONArray3 == null || i3 >= jSONArray3.length()) {
                        break;
                    } else if (TextUtils.equals(optString, jSONArray3.optJSONObject(i3).optString("event"))) {
                        jSONArray3.remove(i3);
                        break;
                    } else {
                        i3++;
                    }
                }
                if (!optJSONObject.has("status") || (optJSONObject.has("status") && !TextUtils.equals(optJSONObject.optString("status"), m))) {
                    if (jSONArray3 == null) {
                        jSONArray3 = new JSONArray();
                    }
                    jSONArray3.put(optJSONObject);
                }
                i2++;
            } catch (Exception e2) {
                p.b(f, "mergeEventsElement error:" + e2.toString());
            }
        }
        return jSONArray3;
    }

    private static void a(List<String> list, long j2, List<String> list2) {
        try {
            if (list.size() != list2.size()) {
                list.removeAll(list2);
                a(list, j2);
            }
        } catch (Exception e2) {
            p.b(f, "handleInvalidAppIds error:" + e2.toString());
        }
    }

    private static void a(List<String> list, long j2) {
        try {
            ArrayList<j> arrayList = new ArrayList<>();
            for (int i2 = 0; i2 < list.size(); i2++) {
                j jVar = new j();
                jVar.a = list.get(i2);
                jVar.b = 100L;
                jVar.c = j2;
                arrayList.add(jVar);
            }
            f.a().a(arrayList);
        } catch (Exception e2) {
            p.b(f, "handleError" + e2.toString());
        }
    }
}
