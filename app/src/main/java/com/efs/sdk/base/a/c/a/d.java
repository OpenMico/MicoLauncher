package com.efs.sdk.base.a.c.a;

import android.content.Context;
import androidx.annotation.NonNull;
import com.efs.sdk.base.a.c.a;
import com.efs.sdk.base.a.h.f;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.onetrack.OneTrack;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class d {
    private static final SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);

    public static boolean a(@NonNull String str, @NonNull b bVar) {
        try {
            HashMap hashMap = new HashMap();
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONObject("config");
            int i = jSONObject.getInt("cver");
            if (optJSONObject != null && optJSONObject.length() > 0) {
                JSONObject optJSONObject2 = optJSONObject.optJSONObject(VideoTrackHelper.PAGE_COMMON);
                if (optJSONObject2 != null && optJSONObject2.length() > 0) {
                    Iterator keys = optJSONObject2.keys();
                    while (keys.hasNext()) {
                        String str2 = (String) keys.next();
                        hashMap.put(str2, optJSONObject2.optString(str2, ""));
                    }
                }
                JSONArray optJSONArray = optJSONObject.optJSONArray("app_configs");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                        JSONObject jSONObject2 = (JSONObject) optJSONArray.get(i2);
                        if (jSONObject2 != null && jSONObject2.length() == 2) {
                            JSONArray optJSONArray2 = jSONObject2.optJSONArray("conditions");
                            JSONArray optJSONArray3 = jSONObject2.optJSONArray("actions");
                            if (!(optJSONArray2 == null || optJSONArray3 == null || optJSONArray3.length() <= 0)) {
                                a(hashMap, optJSONArray2, optJSONArray3);
                            }
                        }
                    }
                }
            }
            bVar.a(hashMap);
            bVar.a = i;
            return true;
        } catch (Throwable th) {
            com.efs.sdk.base.a.h.d.b("efs.config", "parseConfig error, data is ".concat(String.valueOf(str)), th);
            return false;
        }
    }

    private static void a(Map<String, String> map, JSONArray jSONArray, JSONArray jSONArray2) {
        String str;
        boolean z = true;
        int i = 0;
        while (true) {
            try {
            } catch (Throwable th) {
                com.efs.sdk.base.a.h.d.b("efs.config", "updateConfigCond error", th);
                return;
            }
            if (i < jSONArray.length()) {
                JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                if (jSONObject == null || jSONObject.length() >= 2) {
                    String optString = jSONObject.optString("fld");
                    String optString2 = jSONObject.optString("val");
                    String optString3 = jSONObject.optString("opc");
                    if (!(optString == null || optString2 == null || optString3 == null)) {
                        a a2 = com.efs.sdk.base.a.d.a.a();
                        Context context = a2.c;
                        Map<String, String> a3 = a2.a();
                        if ("ver".equals(optString)) {
                            str = f.a(context);
                        } else if ("datetime".equals(optString)) {
                            SimpleDateFormat simpleDateFormat = a;
                            com.efs.sdk.base.a.a.a.a();
                            str = simpleDateFormat.format(new Date(com.efs.sdk.base.a.a.a.b()));
                        } else if (OneTrack.Param.PKG.equals(optString)) {
                            str = context.getPackageName();
                        } else {
                            str = (a3.isEmpty() || !a3.containsKey(optString)) ? null : a3.get(optString);
                        }
                        if (str != null) {
                            char c = 65535;
                            switch (optString3.hashCode()) {
                                case -1555538761:
                                    if (optString3.equals("startsWith")) {
                                        c = '\b';
                                        break;
                                    }
                                    break;
                                case -1295482945:
                                    if (optString3.equals("equals")) {
                                        c = 11;
                                        break;
                                    }
                                    break;
                                case -567445985:
                                    if (optString3.equals("contains")) {
                                        c = '\n';
                                        break;
                                    }
                                    break;
                                case 33:
                                    if (optString3.equals("!")) {
                                        c = 5;
                                        break;
                                    }
                                    break;
                                case 60:
                                    if (optString3.equals("<")) {
                                        c = 2;
                                        break;
                                    }
                                    break;
                                case 62:
                                    if (optString3.equals(">")) {
                                        c = 1;
                                        break;
                                    }
                                    break;
                                case 1084:
                                    if (optString3.equals("!=")) {
                                        c = 6;
                                        break;
                                    }
                                    break;
                                case 1921:
                                    if (optString3.equals("<=")) {
                                        c = 4;
                                        break;
                                    }
                                    break;
                                case 1922:
                                    if (optString3.equals("<>")) {
                                        c = 7;
                                        break;
                                    }
                                    break;
                                case 1952:
                                    if (optString3.equals("==")) {
                                        c = 0;
                                        break;
                                    }
                                    break;
                                case 1983:
                                    if (optString3.equals(">=")) {
                                        c = 3;
                                        break;
                                    }
                                    break;
                                case 257797441:
                                    if (optString3.equals("equalsIgnoreCase")) {
                                        c = '\f';
                                        break;
                                    }
                                    break;
                                case 840862003:
                                    if (optString3.equals("matches")) {
                                        c = 14;
                                        break;
                                    }
                                    break;
                                case 1743158238:
                                    if (optString3.equals("endsWith")) {
                                        c = '\t';
                                        break;
                                    }
                                    break;
                                case 2058039875:
                                    if (optString3.equals("isEmpty")) {
                                        c = '\r';
                                        break;
                                    }
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    int a4 = a(optString, str, optString2);
                                    if (a4 == -100) {
                                        if (str.compareTo(optString2) == 0) {
                                            z = true;
                                            break;
                                        } else {
                                            z = false;
                                            break;
                                        }
                                    } else if (a4 == 0) {
                                        z = true;
                                        break;
                                    } else {
                                        z = false;
                                        break;
                                    }
                                case 1:
                                    int a5 = a(optString, str, optString2);
                                    if (a5 == -100) {
                                        if (str.compareTo(optString2) > 0) {
                                            z = true;
                                            break;
                                        } else {
                                            z = false;
                                            break;
                                        }
                                    } else if (a5 > 0) {
                                        z = true;
                                        break;
                                    } else {
                                        z = false;
                                        break;
                                    }
                                case 2:
                                    int a6 = a(optString, str, optString2);
                                    if (a6 == -100) {
                                        if (str.compareTo(optString2) < 0) {
                                            z = true;
                                            break;
                                        } else {
                                            z = false;
                                            break;
                                        }
                                    } else if (a6 < 0) {
                                        z = true;
                                        break;
                                    } else {
                                        z = false;
                                        break;
                                    }
                                case 3:
                                    int a7 = a(optString, str, optString2);
                                    if (a7 == -100) {
                                        if (str.compareTo(optString2) >= 0) {
                                            z = true;
                                            break;
                                        } else {
                                            z = false;
                                            break;
                                        }
                                    } else if (a7 >= 0) {
                                        z = true;
                                        break;
                                    } else {
                                        z = false;
                                        break;
                                    }
                                case 4:
                                    int a8 = a(optString, str, optString2);
                                    if (a8 == -100) {
                                        if (str.compareTo(optString2) <= 0) {
                                            z = true;
                                            break;
                                        } else {
                                            z = false;
                                            break;
                                        }
                                    } else if (a8 <= 0) {
                                        z = true;
                                        break;
                                    } else {
                                        z = false;
                                        break;
                                    }
                                case 5:
                                case 6:
                                case 7:
                                    int a9 = a(optString, str, optString2);
                                    if (a9 == -100) {
                                        if (str.compareTo(optString2) != 0) {
                                            z = true;
                                            break;
                                        } else {
                                            z = false;
                                            break;
                                        }
                                    } else if (a9 != 0) {
                                        z = true;
                                        break;
                                    } else {
                                        z = false;
                                        break;
                                    }
                                case '\b':
                                    z = str.startsWith(optString2);
                                    break;
                                case '\t':
                                    z = str.endsWith(optString2);
                                    break;
                                case '\n':
                                    z = str.contains(optString2);
                                    break;
                                case 11:
                                    z = str.equals(optString2);
                                    break;
                                case '\f':
                                    z = str.equalsIgnoreCase(optString2);
                                    break;
                                case '\r':
                                    z = str.isEmpty();
                                    break;
                                case 14:
                                    z = str.matches(optString2);
                                    break;
                            }
                            if (z) {
                                i++;
                            }
                        } else if (!"isNull".equals(optString3)) {
                            z = false;
                        } else {
                            i++;
                        }
                        com.efs.sdk.base.a.h.d.b("efs.config", "updateConfigCond error", th);
                        return;
                    }
                } else {
                    z = false;
                }
            }
        }
        if (z) {
            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                JSONObject jSONObject2 = (JSONObject) jSONArray2.get(i2);
                if (jSONObject2 != null && jSONObject2.length() >= 2) {
                    String optString4 = jSONObject2.optString("opt");
                    Object opt = jSONObject2.opt(BluetoothConstants.SET);
                    if (!(optString4 == null || opt == null)) {
                        String optString5 = jSONObject2.optString("lt", null);
                        String optString6 = jSONObject2.optString(OneTrack.Param.NET, null);
                        if (optString5 != null) {
                            optString4 = optString4 + "_" + optString5;
                        }
                        if (optString6 != null) {
                            optString4 = optString4 + "_" + optString6;
                        }
                        map.put(optString4, String.valueOf(opt));
                    }
                }
            }
        }
    }

    private static int a(String str, String str2, String str3) {
        if ("ver".equals(str)) {
            return a(str2, str3);
        }
        if ("datetime".equals(str)) {
            return b(str2, str3);
        }
        return -100;
    }

    private static int a(String str, String str2) {
        if (str == null) {
            str = "0";
        }
        if (str2 == null) {
            str2 = "0";
        }
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int max = Math.max(split.length, split2.length);
        int i = 0;
        while (i < max) {
            int parseInt = i < split.length ? Integer.parseInt(split[i]) : 0;
            int parseInt2 = i < split2.length ? Integer.parseInt(split2[i]) : 0;
            if (parseInt < parseInt2) {
                return -1;
            }
            if (parseInt > parseInt2) {
                return 1;
            }
            i++;
        }
        return 0;
    }

    private static int b(String str, String str2) {
        if (str == null) {
            str = "1970/01/01 00:00:00";
        }
        if (str2 == null) {
            str2 = "1970/01/01 00:00:00";
        }
        String[] split = str.split("[:/\\s]");
        String[] split2 = str2.split("[:/\\s]");
        int max = Math.max(split.length, split2.length);
        int i = 0;
        while (i < max) {
            int parseInt = i < split.length ? Integer.parseInt(split[i]) : 0;
            int parseInt2 = i < split2.length ? Integer.parseInt(split2[i]) : 0;
            if (parseInt < parseInt2) {
                return -1;
            }
            if (parseInt > parseInt2) {
                return 1;
            }
            i++;
        }
        return 0;
    }
}
