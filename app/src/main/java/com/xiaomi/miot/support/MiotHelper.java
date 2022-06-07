package com.xiaomi.miot.support;

import android.text.TextUtils;
import java.util.Map;

/* loaded from: classes2.dex */
public class MiotHelper {
    private MiotHelper() {
    }

    public static String createSendMsg(String str, Map<String, String> map) {
        if (TextUtils.isEmpty(str)) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"action\":\"" + str + "\"");
        if (map != null && !map.isEmpty()) {
            sb.append(",\"params\":{");
            for (String str2 : map.keySet()) {
                sb.append("\"");
                sb.append(str2);
                sb.append("\":\"");
                sb.append(map.get(str2));
                sb.append("\",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("}");
        }
        sb.append("}");
        return sb.toString();
    }
}
